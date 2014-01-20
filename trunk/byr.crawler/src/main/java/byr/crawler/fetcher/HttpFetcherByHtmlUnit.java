package byr.crawler.fetcher;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


import byr.crawler.framework.CrawlURI;
import byr.crawler.framework.Fetcher;
import byr.crawler.framework.Url;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HttpFetcherByHtmlUnit extends Fetcher
{

    Logger logger = Logger.getLogger(HttpFetcherByHtmlUnit.class);
    
    private WebClient webClient = null;
    private String defaultEncode = "UTF-8";
    private boolean enableJs = true;                // enable javascript or not
    private int jsTimeout = 10;                      // timeout of javascript by second
    private int conTimeout = 20;                    // timeout of connection to server  by second 
    
    public HttpFetcherByHtmlUnit()
    {
            // TODO Auto-generated constructor stub
            this(true);
    }
    
    public HttpFetcherByHtmlUnit(boolean enableJs)
    {
            // TODO Auto-generated constructor stub
            this(enableJs ,10,20);
    }

    public HttpFetcherByHtmlUnit(boolean enableJs, int jsTimeout, int conTimeout)
    {
            this.enableJs = enableJs;
            if(jsTimeout >0 && conTimeout > 0)
            {
                    this.jsTimeout = jsTimeout;
                    this.conTimeout = conTimeout;
            }

            /* set the htmlunit client*/
            webClient = new WebClient(BrowserVersion.FIREFOX_17);
            webClient.getCache().setMaxSize(0);   
            webClient.getOptions().setActiveXNative(false);
    		webClient.getOptions().setCssEnabled(false);  		
    		webClient.getOptions().setPopupBlockerEnabled(true);
    		webClient.getOptions().setRedirectEnabled(true);
    		webClient.getOptions().setThrowExceptionOnScriptError(false);
    		webClient.getOptions().setTimeout(conTimeout*1000);
    		webClient.getOptions().setAppletEnabled(false);
    		
            if(this.enableJs)
            {             
            	webClient.setAjaxController(new NicelyResynchronizingAjaxController()); 
        		webClient.getOptions().setJavaScriptEnabled(true);
                webClient.setJavaScriptTimeout(jsTimeout*1000);
                    
            }
    }
    
	@Override
	public void fetch(CrawlURI curl)
	{
		String url = curl.getUrl().getUrl();
		if (url == null)
		{
			curl.setStatus(CrawlURI.STATUS_FETCH_ERROR);
			return;
		}
		HtmlPage page = null;
		 try
		{
	  	
       	 	// fetch html from the server
             page = webClient.getPage(url);
            
               
             WebResponse webresponse = page.getWebResponse();
             TimeUnit.SECONDS.sleep(5);
             // the content of the page
             String res = page.asXml();
             curl.setContent(res);
             
             // http status 
             int status = webresponse.getStatusCode();                             
             String encoding = webresponse.getContentCharsetOrNull();                        

           
             // if encoding is empty we change the default encoding(utf-8)
             if(!StringUtils.isEmpty(encoding))
            	 curl.setEncode(encoding);
             
             // if the the httpstatus is 200 , it means we get what we desire and return the content of the html page.
             curl.setHttpstatus(status);  
             //System.out.println(page.asText());
			 System.out.println(Thread.currentThread().getName()+"-"+ "##HttpFetcherByHtmlUnit: curl="+curl);
			
			
		}
		catch (Exception e)
		{
			curl.setStatus(CrawlURI.STATUS_FETCH_ERROR);
			curl.setContent(null);
			curl.setReserve(null);
			logger.error(Thread.currentThread().getName()+"-"+"##HttpFetcherByHtmlUnit:"+e.getMessage());
			e.printStackTrace();
			return;
		}		
		 finally
		 {
			 page = null;
			 webClient.closeAllWindows();
		 }
	}
	
	
	static public void main(String [] args)
    {
    	String keywords = "±±” ";
    	String url = "http://bbs.byr.cn/default";
    	Url u = new Url();
    	u.setUrl(url);
    	CrawlURI curl = new CrawlURI();
    	curl.setStatus(-1);
    	curl.setUrl(u);
    	new HttpFetcherByHtmlUnit(true).fetch(curl);
    	//System.out.println(curl.getContent());
/*    	Analyzer sha = new SinaHtmlAnalyzer();
    	SearchResult srid = sha.analyze(ab);
    	WeiboMysql weiboh2 = new WeiboMysql();  	
		weiboh2.insert(srid);
    	System.out.println(srid.toString());*/
    
    }

}
