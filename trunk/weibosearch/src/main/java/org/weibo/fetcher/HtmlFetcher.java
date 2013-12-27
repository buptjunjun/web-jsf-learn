package org.weibo.fetcher;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.weibo.common.AnalyzeBean;
import org.weibo.common.FetchBean;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * fetch the html form the specified url.
 * @author junjun
 *
 */
public class HtmlFetcher implements Fetcher
{
    Logger logger = Logger.getLogger(HtmlFetcher.class);
    
    private WebClient webClient = null;
    private String defaultEncode = "UTF-8";
    private boolean enableJs = true;                // enable javascript or not
    private int jsTimeout = 6;                      // timeout of javascript by second
    private int conTimeout = 20;                     // timeout of connection to server  by second 
    
    public HtmlFetcher()
    {
            // TODO Auto-generated constructor stub
            this(false);
    }
    
    public HtmlFetcher(boolean enableJs)
    {
            // TODO Auto-generated constructor stub
            this(enableJs ,3,10);
    }

    public HtmlFetcher(boolean enableJs, int jsTimeout, int conTimeout)
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
    }
    
    public AnalyzeBean fetch(FetchBean ft)
    {
    		AnalyzeBean ab = new AnalyzeBean();
    		
            String url = ft.getUrl();
            if (url == null)
            {              
                    return ab;
            }
            
             try
             {
            	 	// fetch html from the server
                    HtmlPage page = webClient.getPage(url);
                    
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
                    
                    logger.info("HtmlFetcher # "+ft.getKeyword()+" httpstatus:"+status);
            }
            catch (Exception e) 
            {                 
            	  /* following section we set the AnalyzeBean for analyzer*/
                ab.setContent(null);
                
                // if the the httpstatus is 200 , it means we get what we desire and return the content of the html page.
                ab.setHttpstatus(-1);     
                logger.error("HtmlFetcher # "+ft.getKeyword()+" httpstatus:"+-1+ ": "+e.getMessage());                   
            } 
             
            return ab;
    }
	
}
