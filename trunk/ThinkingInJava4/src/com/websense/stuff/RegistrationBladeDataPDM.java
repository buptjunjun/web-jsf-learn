package com.websense.appmng.client.host.pdm;

import org.jdom.Document;
import org.jdom.Element;


/**
 * This object will store the data of one blade which used for register Triton manager.
 * the structure in xml format shows bellow:
<blade>	<!-- one or more of these -->
   <slot-id></slot-id>
   <hostname></hostname>
   <guid></guid>
   <ip></ip> <!-- for now return the P1 interface -->
   <description></description>
   <hw-platform></hw-platform>
   <sw-version></sw-version>
   <deployed>[true/false]</deployed>
   <provisioning>
      		<email>disabled</email> <!¡ª- added for future expansion -->
			<wcg>false</wcg>
			<wsga>false</wsga>
			<web>
	   			<limitedpolicy>enable</limitedpolicy>
	   			<filteringonly>disabled</filteringonly>
	   			<naonly>disabled</naonly>
	   <!¡ª- added for future expansion -->
			</web>
   </provisioning>
</blade>

 * @author Andy 2012-9-19
 */
public class RegistrationBladeDataPDM 
{
	String slot_id = null;
	String hostname = null;
	String ip = null;
	String description = null;
	String hw_platform = null;
	String sw_version = null;
	String provisionEmail = null;
	String provisionWebLimitedpolicy = null;
	String provisionWebFilteringonly = null;
	String provisionWebNaonly = null;
	String provisionWebWse = null;
	String provisionWebWsg = null;
	String provisionWebWsga = null; 
	
	public RegistrationBladeDataPDM(
									String slot_id,
									String hostname,
									String ip,
									String description,
									String hw_platform,
									String sw_version,
									String provisionEmail,
									String provisionWebLimitedpolicy,
									String provisionWebFilteringonly,
									String provisionWebNaonly,
									String provisionWebWse,
									String provisionWebWsg,
									String provisionWebWsga  ) 
	{
		this.slot_id = slot_id;
		this.hostname = hostname;
		this.ip = ip;
		this.description = description;
		this.hw_platform = hw_platform;
		this.sw_version = sw_version;
		this.provisionEmail  =  provisionEmail ;
		this.provisionWebLimitedpolicy  =  provisionWebLimitedpolicy ;
		this.provisionWebFilteringonly  =  provisionWebFilteringonly ;
		this.provisionWebNaonly  =  provisionWebNaonly ;
		this.provisionWebWse  =  provisionWebWse ;
		this.provisionWebWsg  =  provisionWebWsg ;
		this.provisionWebWsga = provisionWebWsga;
	}
	
	
	/**
	 * Generate XML document for appliance register information of one blade
	 * @return Document type registration information
	 */
	public Document getRegistrationBladeXml()
	{
		Document doc = new Document();
		
		Element root = new Element("blade");
			Element slotElement = new Element("slot-id");
			Element hostnameElement = new Element("hostname");
			Element ipElement = new Element("ip");
			Element descriptionElement = new Element("description");
			Element hw_platformElement = new Element("hw-platform");
			Element sw_version = new Element("sw-version");
			Element deployedElement = new Element("deployed");
			
			Element provisioningElement = new Element("provisioning");
				Element emailElement = new Element("email");
				Element wcgElement = new Element("wcg");
				Element wsgaElement = new Element("wsga");
				
				Element webElement = new Element("web");
					Element limitedpolicyElement = new Element("limitedpolicy");
					Element filteringonlyElement = new Element("filteringonly");
					Element naonlyElement = new Element("naonly");
			
		doc.setRootElement(root);
		root.addContent(slotElement);
		root.addContent(hostnameElement);
		root.addContent(ipElement);
		root.addContent(descriptionElement);
		root.addContent(hw_platformElement);
		root.addContent(sw_version);
		root.addContent(deployedElement);
		root.addContent(provisioningElement);
		
		provisioningElement.addContent(emailElement);
		provisioningElement.addContent(wcgElement);
		provisioningElement.addContent(wsgaElement);
		provisioningElement.addContent(webElement);
		
		webElement.addContent(limitedpolicyElement);
		webElement.addContent(filteringonlyElement);
		webElement.addContent(naonlyElement);
		
		return doc;
	}
	
	public static void main(String [] args)
	{
		RegistrationBladeDataPDM blade = new RegistrationBladeDataPDM (" slot_id",
														" hostname",
														" ip",
														" description",
														" hw_platform",
														" sw_version",
														" provisionEmail",
														" provisionWebLimitedpolicy",
														" provisionWebFilteringonly",
														" provisionWebNaonly",
														" provisionWebWse",
														" provisionWebWsg",
														" provisionWebWsga");
		
		Document doc  = blade.getRegistrationBladeXml();
		
		System.out.println(doc.toString());
	}
	
}
