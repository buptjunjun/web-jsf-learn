package jsf.chapter6.BB;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class IpValidator implements Validator,  javax.faces.component.StateHolder
{
	public String ipVersion;
	
	public IpValidator()
	{
		System.out.println("--------------------ipValidator created--------------------");
	}

	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		// TODO Auto-generated method stub
		String ip = (String)arg2;
		System.out.println(arg0.getCurrentInstance().getCurrentPhaseId() +" version = " + this.getIpVersion() +" ip = " + ip);
		if(ipVersion!=null && ipVersion.equals("4"))
		{
			if(!this.isIPv4(ip))
			{
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, arg1.getClientId()+"valid ipv4 address",arg1.getClientId()+"valid ipv4 address");				
				throw( new ValidatorException(msg));
			}
		}
		
	}
	
	
 	/**
	 * Validate IPv4 format
	 * 
	 * @return the ip
	 */
	public  boolean isIPv4(String address)
	{
		Pattern pattern = Pattern
		        .compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
		Matcher matcher = pattern.matcher(address);
		return matcher.matches();
	}
	

	public String getIpVersion() {
		return ipVersion;
	}

	public void setIpVersion(String ipVersion) {
		this.ipVersion = ipVersion;
	}
	
	public boolean isTransient() {
		// TODO Auto-generated method stub
		return false;
	}

	public void restoreState(FacesContext arg0, Object arg1) {
		// TODO Auto-generated method stub
		System.out.println("restoreState");
		this.setIpVersion((String)arg1);
		
	}

	public Object saveState(FacesContext arg0) {
		// TODO Auto-generated method stub
		System.out.println("saveState");
		return this.getIpVersion();
	}

	public void setTransient(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	 

}
