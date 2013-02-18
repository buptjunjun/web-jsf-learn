package org.easyGoingCrawler.fetcher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tokenizer.ChineseTokenizer;
import static org.junit.Assert.*;

public class HtmlUnitTest {

    private String webUrl;

    private WebClient webClient;

    @Before
    public void setUp() {
        webUrl = System.getProperty("integration.base.url");
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
        webClient.closeAllWindows();
    }

    /**
     * Test /index.html
     */
    @Test
    public void testIndexHtml() throws Exception {
//        System.out.println("Connecting to: " + webUrl);
//        webUrl = "http://www.cnblogs.com/58top/archive/2012/12/28/how-to-generate-unique-promotion-discount-codes-in-php.html";
    	FileReader f = new FileReader("result.txt");
    	BufferedReader bf = new BufferedReader(f);
    	String line = null;
    	String str = "";
    	while((line = bf.readLine())!= null)
    	{
    		str+=line;
    	}
    	final List<String> querys =ChineseTokenizer.getWords(str);
    	
    	while(true)
    	{
    		for(int i = 0; i < 20; i++) 	
	    	{
	    		
	    		new Thread()
	    		{
	    			@Override
	    			public void run()
	    			{
	
	    	    	    String query = "";
	    	    		int m = new Random().nextInt(querys.size());
	    	    		int n = new Random().nextInt(querys.size());
	    	    		if(m!=n)
	    	    		{
	    	    			query+=querys.get(m)+" "+querys.get(n);
	    	    		}
	    	    		else
	    	    		{
	    	    			query+=querys.get(m);
	    	    		}
	    				try
						{
							testPost(query);
						} catch (FailingHttpStatusCodeException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    			}
	    		}.start();
	    	}
    		TimeUnit.SECONDS.sleep(3);
    	}
       
    }
    
    public void testPost(String query) throws FailingHttpStatusCodeException, IOException 
    {
    		URL url = new URL("http://localhost:8080/searchTech/pages/search.jsp");
    	   final WebClient webClient = new WebClient();

    	    // Get the first page
    	    final HtmlPage page1 = webClient.getPage(url);

    	    // Get the form that we are dealing with and within that form, 
    	    // find the submit button and the field that we want to change.
    	    final HtmlForm form = page1.getFormByName("form");

    	    final HtmlSubmitInput button = form.getInputByName("submit");
    	    final HtmlTextInput textField = form.getInputByName("searchwords");

    	    // Change the value of the text field
    	    textField.setValueAttribute(query);

    	    // Now submit the form by clicking the button and get back the second page.
    	    final HtmlPage page2 = button.click();
    	   // FileWriter f = new FileWriter("result.txt");
    	    System.out.println(page2.asXml());
    }
}