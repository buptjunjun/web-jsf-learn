package org.junjun.analyse.analyzerImpl;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.util.Converter;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junjun.analyse.analyzer.Analyzer;
import org.junjun.analyse.analyzer.bean.BResource;
import org.junjun.analyse.analyzer.bean.Movie;
import org.junjun.analyse.dao.DAOapi;
import org.junjun.analyse.dao.mongo.MongoDAOapi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ResourceAnalyzerYunfan implements Analyzer<List<BResource>>
{	
	public static String host="movie.douban.com";
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM");
	private SimpleDateFormat formater2 = new SimpleDateFormat("yyyy");
	private Pattern patternInt = Pattern.compile("\\d+");
	private Pattern patternURL = Pattern.compile("http://movie.douban.com/subject/\\d+");
	private Pattern patternDate = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d");
	private Pattern patternDate1 = Pattern.compile("\\d\\d\\d\\d-\\d\\d");
	private Pattern patternDate2 = Pattern.compile("\\d\\d\\d\\d");
	
	 static String director = "导演";
	 static String actor = "主演";
	 static String type = "类型"; 
	 static String loc = "制片国家/地区: ";
	 static String language = "语言:";
	 static String date = "日期:"; 
	 static String length = "片长:";
	 static String aname = "又名:";
	 static String imdb = "IMDb";
	 static String seperator =  "/";
	
	 static int lengthLoc = loc.length();
	 static int lengthDate = date.length(); 
	 static int lengthLength = length.length();
	 static int lengthAname = aname.length();
	 private Logger logger = Logger.getLogger(ResourceAnalyzerYunfan.class);
	 private static final int HTMLERROR= 2; 
	 private static final int UPDATE_MOVIE_RESOURCE= 1000;
	 private Set updateMovieMagicNum=new HashSet<String>();
	 DAOapi dao = null;
	public ResourceAnalyzerYunfan()
	{
		 dao = new MongoDAOapi();
		 updateMovieMagicNum.add("magicNum");
	}
	
	public List<BResource> analyze(String movieid,String data_id,String content)
	{
		// TODO Auto-generated method stub
		List<BResource> rets = new ArrayList<BResource>();
		String str;
		try
		{				
			Document doc = Jsoup.parse(content);
			Element e = doc.getElementsByAttributeValue("data-id", data_id).first();
			Elements eMovies = e.getElementsByClass("lb_ly");   // movies
			Elements eSerials = e.getElementsByClass("lb_ly1"); // serials
			List<BResource> listResources = new ArrayList<BResource>();
			if(eMovies!=null && eMovies.size()>0)
			{
				Element linkDivEle = eMovies.first();
				Elements linksEle = linkDivEle.select("a[href]");
				if(linksEle!=null)
				{
					for(Element a:linksEle)
					{
						String url = a.attr("href");
						String type = a.attr("source");
						if (url!=null && !url.startsWith("javascript"))
						{
							BResource r = new BResource();
							String host = "";
							try
							{
								host = new URI(url).getHost();
							}
							catch(Exception ee)
							{
								ee.printStackTrace();
							}
							r.setHost(host);
							r.setResourceType(type);
							r.setResourceURL(url);
							r.setMovieId(movieid);
							rets.add(r);
						}
					}
				}
			}
			else if(eSerials!=null && eSerials.size()>0)
			{
				Element linkDivEle = eSerials.first();
				Elements ulElements = linkDivEle.select("ul");
				if(ulElements != null)
				{
					for(Element ul:ulElements)
					{
						Elements linksEle = ul.select("a[href]");
						if(linksEle!=null)
						{
							for(Element a:linksEle)
							{
								String url = a.attr("href");
								String type = a.attr("source");
								if (url!=null && !url.startsWith("javascript"))
								{
									BResource r = new BResource();
									String host = "";
									try
									{
										host = new URI(url).getHost();
									}
									catch(Exception eee)
									{
										eee.printStackTrace();
									}
									r.setHost(host);
									r.setResourceURL(url);
									r.setMovieId(movieid);
									r.setResourceType(type);
									
									rets.add(r);
								}
							}
						}
						
					}
				}
			}
			
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString());
			
		}
		
		return rets;
	}
	
	public List<Movie> getMovies(String html)
	{
		List<Movie> lmovie = new ArrayList<Movie>();
		try
		{				
			Document doc = Jsoup.parse(html);
			Elements moviesElems = doc.getElementsByClass("film_lb");
			
			//get movie info 
			Iterator i = moviesElems.iterator();
			while(i.hasNext())
			{
				try
				{
					Movie movie = new Movie();
					Element movieElem = (Element)i.next();
					
					String data_id = movieElem.attr("data-id");
					//save data-id to id
					movie.setId(data_id);
					
					String name = movieElem.getElementsByClass("bt_t").first().text();
					movie.setName(name);
					Elements liElem = movieElem.getElementsByClass("bt_js").first().getElementsByTag("li");
					
					
					
					Map<String,String> infos = new HashMap<String,String>();						
					// get the pieces of  information
					String text = "1";
					Iterator ili = liElem.iterator();
					while(ili.hasNext())
					{
						Element li = (Element) ili.next();
						text = li.text();
						
						String [] splits = text.split("：");
						if(splits!=null && splits.length >=2)
						infos.put(splits[0].trim(), splits[1].trim());
					
					}
					
				
					for(Entry<String,String> entry:infos.entrySet())
					{
						String key =  entry.getKey();
						String value = entry.getValue();
						
						try
						{
							if(key.contains("导演"))
							{
								String []arrs = value.split(seperator);
								movie.setDirector(Arrays.asList(arrs));
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
							logger.error(e.toString());
						}
	
						try
						{
							if(key.contains("主演"))
							{
								String []arrs = value.split(seperator);
								movie.setActors(Arrays.asList(arrs));
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
							logger.error(e.toString());
						}
						
						
						try
						{
							if(key.contains("类型"))
							{
								String []arrs = value.split(seperator);
								movie.setType(Arrays.asList(arrs));
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
							logger.error(e.toString());
						}
						
						try
						{
							if(key.contains("国家") || key.contains("地区"))
							{
								String []arrs = value.split(seperator);
								movie.setLocation(Arrays.asList(arrs));
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
							logger.error(e.toString());
						}
						
						if(key.contains("年份")|| key.contains("首播"))
						{
							Matcher mdate = patternDate2.matcher(value);
							if(mdate.find())
							{
								String date = mdate.group();
								Date d = formater2.parse(date);
								movie.setDate(d);
							}
						}
						
						try
						{
							if(key.contains("简介"))
							{
								movie.setDescription(value);
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
							logger.error(e.toString());
						}
						
					}
					
					lmovie.add(movie);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString());
			
		}
		
		return lmovie;
	}

	
	private boolean compareMovies(Movie douban,Movie yunfan)
	{
		boolean actorsBool = false;
		boolean direcBool = false;
		boolean yearBool = false;
		boolean descriptionBool=false;
		
		List<String> actors = douban.getActors();
		if(actors!=null&& actors.size()>0 && yunfan.getActors()!=null&& yunfan.getActors().size()>0)
		{
			String actorStr = yunfan.getActors().toString();
			for(String actor:actors)
			{
				if(actorStr.contains(actor))
				{
					actorsBool = true;
					break;
				}
			}		
		}
		else
		{
			actorsBool = true;
		}
		
		List<String> directors = douban.getDirector();
		if(directors!=null&& directors.size()>0&&yunfan.getDirector()!=null &&yunfan.getDirector().size() >0)
		{
			String directorStr = yunfan.getDirector().toString();
			for(String direc:directors)
			{
				if(directorStr.contains(direc))
				{
					direcBool = true;
					break;
				}
			}			
		}
		else
		{
			direcBool = true;
		}
		
		Date date1 = douban.getDate();
		Date date2 = yunfan.getDate();
		if(date1!=null && date2!=null&& Math.abs(date1.getYear()-date2.getYear())<=0)
		{
			yearBool=true;
		}
			
		String doubandesc= douban.getDescription();
		String yunfanDesc = yunfan.getDescription();
		if(!StringUtils.isBlank(doubandesc)&& !StringUtils.isBlank(yunfanDesc))
		{
			int random = new Random().nextInt(3)+2;
			if(random<0) random = 0-random;
			
			doubandesc = doubandesc.trim();
			yunfanDesc = yunfanDesc.trim();
			int length = doubandesc.length()>yunfanDesc.length()?yunfanDesc.length():doubandesc.length();
			String subDouban = doubandesc.substring(length/(random+5), length/random);
			if(yunfanDesc.contains(subDouban))
				descriptionBool = true;
			else
			{
				descriptionBool = false;
			}
		}
		else
		{
			descriptionBool = false;
		}
		return  ((actorsBool||direcBool) && yearBool)|| descriptionBool||(actorsBool && direcBool);
	}
	
	public List<BResource> analyze(Html html)
	{
		List<BResource> rets = this.analyze(html.getHost(), html.getEncode(), html.getHtml());
		if(rets==null)
			return null;
		
		return rets;
	}
	
	public void analyse()
	{		
		Set<String> updateFields = new HashSet<String>();
		updateFields.add("magicNum");
		Html initHtml = new Html();
		initHtml.setHost("www.yunfan.com");
		Date date = new Date();
		date.setYear(10);
		int limit = 50;
		initHtml.setCrawledDate(date);	
		List<Html> htmls = dao.getNextHtmls(initHtml,limit);
		int count = 0;
		int statistics = 0;
		Set<String> updateField = new HashSet<String>();
		updateField.add("magicNum");
		
		Html pre = initHtml;
		int countRepeat = 0;
		while(htmls!=null)
		{
			Html store = null;
			for(Html html:htmls)
			{
				count++;
				String movieid = html.getId().replace("yunfan", "");
				Movie douban = this.dao.getMovie(movieid);
				
				// extract movie from yunfan html.
				List<Movie> yunfans = this.getMovies(html.getHtml());
				
				System.out.println(count+"--douban"+douban);
				System.out.println(count+"++html"+html);
				logger.info(count+"--douban"+douban);
				logger.info(count+"++ html"+html);
				
				// the most similar movie .
				Movie selected = null;
				if (yunfans!=null && yunfans.size()>0)
				{
					for(Movie m:yunfans)			
					{
						boolean result = this.compareMovies(m, douban);
						if(result == true)
						{
												
							System.out.println(count+"==yunfan：statistics:"+statistics+"  "+m+"\n");
							logger.info(count+"==yunfan：statistics:"+statistics+"  "+m+"\n");
							statistics++;
							selected=m;
							break;
						}
					}
				}
				
				if(selected!=null)
				{
					List<BResource> rets = analyze(douban.getId(),selected.getId(),html.getHtml());
					if(rets!=null && rets.size() > 0)
					{
						selected.setMagicNum(this.UPDATE_MOVIE_RESOURCE);
						selected.setId(douban.getId());
						this.dao.updateMovie(selected, updateMovieMagicNum);
						for(BResource res:rets)
							this.dao.insertResource(res);
					}
				}
				
				if(html.getCrawledDate()!=null)
					store = html;
			}
			if(pre.equals(store))
			{
				countRepeat++;
			}
			else
			{
				countRepeat = 0;
			}
			
			if(countRepeat > 3)
				break;

			pre = store;
			
			htmls = dao.getNextHtmls(store,limit);
		}
	}
	
	public void test(String id)
	{
		//String id = Converter.urlEncode(url);
		Html html  = dao.getHtml(id);
		String movieid = html.getId().replace("yunfan", "");
		Movie douban = this.dao.getMovie(movieid);
		List<Movie> yunfans = this.getMovies(html.getHtml());
		int count = 0,statistics=0 ;
		System.out.println(count+"--douban"+douban);
		System.out.println(count+"++html"+html);
		logger.info(count+"--douban"+douban);
		logger.info(count+"++html"+html);
		Movie selected=null;
		if (yunfans!=null && yunfans.size()>0)
			for(Movie m:yunfans)
			{
				boolean result = this.compareMovies(m, douban);
				if(result == true)
				{
										
					System.out.println(count+"==yunfan：statistics:"+statistics+"  "+m+"\n");
					logger.info(count+"==yunfan：statistics:"+statistics+"  "+m+"\n");
					statistics++;
					selected = m;				
					break;
				}
			}
		if(selected !=null )
		{
			analyze(douban.getId(),selected.getId(),html.getHtml());
		}
		
		//System.out.println(m);
	}
	static public void main(String [] args)
	{
		String log4jfile = "src\\main\\resources\\log4j.properties";
		PropertyConfigurator.configure(log4jfile);
	
		ResourceAnalyzerYunfan yunfan = new ResourceAnalyzerYunfan();
		//test serials 
		//yunfan.test("1001cce1a1db6d35d76bd4d7248e1369yunfan");
		//test movies 
		//yunfan.test("3b034c7df85abbd61be7aab8725014b9yunfan");
		yunfan.analyse();
	}

	public List<BResource> analyze(Object obj)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
