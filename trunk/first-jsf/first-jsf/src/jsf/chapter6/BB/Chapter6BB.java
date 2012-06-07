package jsf.chapter6.BB;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class Chapter6BB 
{
	private Date date ;
	private String ipv4="123.3112";
	
	public String getIpv4() {
		if(ipv4 == null)
			ipv4="123.123.22.11";
		
		return ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public void  ipValidator(FacesContext facesContext, UIComponent uiComponent,  Object newValue)   throws ValidatorException 
	{
		String ip = newValue.toString();
		String version = (String)(uiComponent.getAttributes().get("version"));
	
		System.out.println("--------------------------------");
		System.out.println("version="+version +" ip = " + ip);
		
		if(version!=null && version.equals("4"))
		{
			if(!isIPv4(ip))
			{
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, uiComponent.getClientId()+"valid ipv4 address",uiComponent.getClientId()+"valid ipv4 address");				
				throw( new ValidatorException(msg));
			}
				
		}
		
	}
	
 	/**
	 * Validate IPv4 format
	 * 
	 * @return the ip
	 */
	public static boolean isIPv4(String address)
	{
		Pattern pattern = Pattern
		        .compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
		Matcher matcher = pattern.matcher(address);
		return matcher.matches();
	}
	
	public Date getDate() {
		if(this.date == null)
		this.date = new Date();
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

}
