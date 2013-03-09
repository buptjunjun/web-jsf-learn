package org.easyGoingCrawler.util;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageReader;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class FetcherUtil
{
	static public String getEncode(String xml)
	{
		if(xml == null) return null;
		
		String charSet = null;
		
		try
		{		
			String regEx="(?=<meta).*?(?<=charset=[//'|//\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
			Pattern p=Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			Matcher m=p.matcher(xml);
		    boolean result=m.find();
		    if (result == true && m.groupCount() == 1) 
		       charSet = m.group(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	    
	    return charSet;
	}
	public static WebClient getLoginedWebClinet(String loginUrl, String formTag,String nameTag,String passwdTag,String submitTag,String name,String passwd) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);

	    // Get the first page
	    final HtmlPage page1 = webClient.getPage(loginUrl);

	    // Get the form that we are dealing with and within that form, 
	    // find the submit button and the field that we want to change.
	    final HtmlForm form = page1.getFormByName(formTag);
	    final HtmlTextInput nameinput = form.getInputByName(nameTag);
	    final HtmlPasswordInput passwordinput = form.getInputByName(passwdTag);    
	    final HtmlSubmitInput button = form.getInputByName(submitTag);

	    // Change the value of the text field
	    nameinput.setValueAttribute(name);
	    passwordinput.setValueAttribute(passwd);

	    // Now submit the form by clicking the button and get back the second page.
	    final HtmlPage page2 = button.click();
	   
	    
	    return webClient;    	 
	}
	
	public static WebClient getLoginedWebClinet(String file) throws FileNotFoundException, IOException 
	{
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
		
		
		Properties p = new Properties();
		p.load(new FileInputStream(file));
		String loginUrl = p.getProperty("loginUrl"); 
		String formTag = p.getProperty("formTag");
		String nameTag = p.getProperty("nameTag");
		String passwdTag = p.getProperty("passwdTag");
		String submitTag = p.getProperty("submitTag");
		String name = p.getProperty("name");
		String passwd = p.getProperty("passwd");
		String code = p.getProperty("CodeNumberTextBox");
		
	    // Get the first page
	    final HtmlPage page1 = webClient.getPage(loginUrl);

	    // Get the form that we are dealing with and within that form, 
	    // find the submit button and the field that we want to change.
	    final HtmlForm form = page1.getFormByName(formTag);
	    final HtmlTextInput nameinput = form.getInputByName(nameTag);
	    final HtmlPasswordInput passwordinput = form.getInputByName(passwdTag);    
	    final HtmlSubmitInput button = form.getInputByName(submitTag);
	    final HtmlTextInput codeInput = form.getInputByName(code);
	    
	    HtmlImage image = (HtmlImage) form.getElementsByTagName("img").get(0);
	    ImageReader imageReader = image.getImageReader();
		BufferedImage bufferedImage = imageReader.read(0);

		JFrame f2 = new JFrame();
		JLabel l = new JLabel();
		l.setIcon(new ImageIcon(bufferedImage));
		f2.getContentPane().add(l);
		f2.setSize(100, 100);
		f2.setTitle("ÑéÖ¤Âë");
		f2.setVisible(true);
		
	    int count = 0;
	    String ret = JOptionPane.showInputDialog("http://www.jyeoo.com"+image.getAttribute("src"));
		
		codeInput.setValueAttribute(ret);
	    
	    // Change the value of the text field
	    nameinput.setValueAttribute(name);
	    passwordinput.setValueAttribute(passwd);

	    // Now submit the form by clicking the button and get back the second page.
	    final HtmlPage page2 = button.click();
	   System.out.println("##########"+page2.getTitleText()+"#############################");
	   try
		{
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return webClient;
	    
	 
	}


}