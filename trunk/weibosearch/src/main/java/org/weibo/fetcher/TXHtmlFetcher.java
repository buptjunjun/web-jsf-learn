package org.weibo.fetcher;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.imageio.ImageReader;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.weibo.analyzer.Analyzer;
import org.weibo.analyzer.SinaHtmlAnalyzer;
import org.weibo.analyzer.TXHtmlAnalyzer;
import org.weibo.common.AnalyzeBean;
import org.weibo.common.Constants;
import org.weibo.common.FetchBean;
import org.weibo.common.ParamStore;
import org.weibo.common.SearchResult;
import org.weibo.common.WeiboMysql;
import org.weibo.proxy.Proxy;
import org.weibo.proxy.ProxyManager;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * fetch the html form the specified url.
 * @author junjun
 *
 */
public class TXHtmlFetcher implements Fetcher
{
    Logger logger = Logger.getLogger(TXHtmlFetcher.class);
    
    private WebClient webClient = null;
    private String defaultEncode = "UTF-8";
    private boolean enableJs = true;                // enable javascript or not
    private int jsTimeout = 10;                      // timeout of javascript by second
    private int conTimeout = 20;                    // timeout of connection to server  by second 
    
    public TXHtmlFetcher()
    {
            // TODO Auto-generated constructor stub
            this(false);
    }
    
    public TXHtmlFetcher(boolean enableJs)
    {
            // TODO Auto-generated constructor stub
            this(enableJs ,3,10);
    }

    public TXHtmlFetcher(boolean enableJs, int jsTimeout, int conTimeout)
    {
            this.enableJs = enableJs;
            if(jsTimeout >0 && conTimeout > 0)
            {
                    this.jsTimeout = jsTimeout;
                    this.conTimeout = conTimeout;
            }

            /* set the htmlunit client*/
            webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
            webClient.getCache().setMaxSize(100);
            webClient.setCookieManager(new CookieManager());
            webClient.setRedirectEnabled(true);
            webClient.setTimeout(conTimeout*1000);
            webClient.setThrowExceptionOnFailingStatusCode(false);
            webClient.setJavaScriptEnabled(enableJs);
            webClient.setThrowExceptionOnScriptError(false);
            webClient.setCssEnabled(true);
            
            if(this.enableJs)
            {
                    webClient.setAjaxController(new NicelyResynchronizingAjaxController());         
                    webClient.setJavaScriptTimeout(jsTimeout*1000);
                    webClient.waitForBackgroundJavaScript(jsTimeout*10*1000);
            }
            
            this.login();
    }
    
    public AnalyzeBean fetch(FetchBean ft)
    {
    		AnalyzeBean ab = new AnalyzeBean();
    		ab.setKeyword(ft.getKeyword());
    		ab.setType(ft.getType());
    		
            String url = ft.getUrl();
            if (url == null)            
                    return ab;
            
            HtmlPage page = null;
            try
            {
            	// use proxy 
            	if(ParamStore.getMessage("useproxy").equalsIgnoreCase("true"))
            	{	
            		Proxy p =  ProxyManager.getInstance().getOneAvailableProxy();
	    			this.webClient.getProxyConfig().setProxyHost(p.getIp());
	    			this.webClient.getProxyConfig().setProxyPort(p.getPort());
	    			logger.info("HtmlFetcher prepare fetch task , using proxy:"+p);
            	}
    		
    			
    			logger.info("HtmlFetcher start fetch task: " + (ft.getType()==1?"sian":"tx") + ", keyword:"+ft.getKeyword());
    			
        	 	// fetch html from the server
                 page = webClient.getPage(url);
                //System.out.println(page.asXml());
                
                WebResponse webresponse = page.getWebResponse();
                
                // the content of the page
                String res = webresponse.getContentAsString(page.getWebResponse().getContentCharset());         
                
                // http status 
                int status = webresponse.getStatusCode();                             
                String encoding = webresponse.getContentCharsetOrNull();                        

                
                
                /* following section we set the AnalyzeBean for analyzer*/
                ab.setContent(res);
                
                // if encoding is empty we change the default encoding(utf-8)
                if(!StringUtils.isEmpty(encoding))
                	ab.setEncode(encoding);
                
                // if the the httpstatus is 200 , it means we get what we desire and return the content of the html page.
                ab.setHttpstatus(status);  
                
                logger.info("HtmlFetcher start fetch task: " + (ft.getType()==1?"sian":"tx") + ", keyword:"+ft.getKeyword()+", httpstatus:"+status+",url="+url);
            }
            catch (Exception e) 
            {                 
            	  /* following section we set the AnalyzeBean for analyzer*/
                ab.setContent(null);
                
                // if the the httpstatus is 200 , it means we get what we desire and return the content of the html page.
                ab.setHttpstatus(-1);     
                logger.error("HtmlFetcher # "+ (ft.getType()==1?"sian":"tx") + ft.getKeyword()+" httpstatus:"+-1+ ": "+e.getMessage());                   
            } 
            finally
            {
            	page=null;
            }
             
            return ab;
    }
    
    private boolean login()
    {
    	WebClient webclient = this.webClient;
		String url="http://ui.ptlogin2.qq.com/cgi-bin/login?appid=46000101&style=13&lang=&low_login=1&hide_title_bar=1&hide_close_icon=1&self_regurl=http%3A//reg.t.qq.com/index.php&s_url=http%3A%2F%2Ft.qq.com&daid=6";
		try {
			HtmlPage page1 = webclient.getPage(url);
			//System.out.println(page1.asXml());
			page1.getElementById("u").setAttribute("value", ParamStore.getMessage("TXName"));
			page1.getElementById("p").setAttribute("value", ParamStore.getMessage("TXPassWord"));
			
			HtmlPage page2 = page1.getElementById("login_button").click();
		//	System.out.println(page2.asXml());
			
			HtmlImage image = (HtmlImage) page2.getElementById("verifyimg");
            ImageReader imageReader = image.getImageReader();
            BufferedImage bufferedImage = imageReader.read(0);

            JFrame f2 = new JFrame("captcha");
            JLabel l = new JLabel();
            l.setIcon(new ImageIcon(bufferedImage));
            f2.getContentPane().add(l);
            f2.setSize(200, 200);
            f2.setTitle("img");
            f2.setVisible(true);
            String ret = JOptionPane.showInputDialog("please inpu the Captcha:");
            f2.setVisible(false);
            
            page2.getElementById("verifycode").setAttribute("value",ret);
            HtmlPage page3 = page2.getElementById("login_button").click();
            URL u = page3.getUrl();
            if( u!=null )
            {
            	String urlString = u.toString();
            	if(urlString.contains("ui.ptlogin2.qq.com"))
            	{
            		loginAgain();   			
            	}
            }
            else
            {
            	loginAgain();   		
            }
            
            //System.out.println(u.toString());
            
  /* //         Thread.sleep(1000*10);
            //String page3Str = page3.asXml();
            //System.out.println(page3Str);
            
            String urlsearch = "http://search.t.qq.com/index.php?k=%E7%88%B8%E7%88%B8%E5%8E%BB%E5%93%AA%E5%84%BF&pos=174&s_source=";
            HtmlPage searchpage = webclient.getPage(urlsearch);
            URL searchHtm = searchpage.getUrl();*/
            //System.out.println(searchHtm);
            
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
		
    }
    
    private void loginAgain()
    {
    	int choice = JOptionPane.showConfirmDialog(null, "login failed ,are you going to do it agon?");
		if(choice== JOptionPane.YES_OPTION)
			this.login();
    }
    static public void main(String [] args) throws IOException
    {
  	String keywords = "北邮";
    	FetchBean fb = new FetchBean(keywords,Constants.TX);
    	TXHtmlFetcher sf = new TXHtmlFetcher(true);
    	AnalyzeBean ab = sf.fetch(fb);
    	   	String file="a.txt"; 
    	//FileUtils.writeStringToFile(new File(file), ab.getContent(), false);
    	
  	
  		//String file="a.txt"; 
    	//AnalyzeBean ab = new AnalyzeBean();
    	ab.setKeyword(keywords);
    	ab.setHttpstatus(200);
    	ab.setType(Constants.TX);
    	String content = FileUtils.readFileToString(new File(file));
    	ab.setContent(content);
    	
    	System.out.println(ab.getContent());
    	Analyzer sha = new TXHtmlAnalyzer();
    	SearchResult srid = sha.analyze(ab);
    	System.out.println();
    
    }
	
}
