package jsf.chapter6.BB;

import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

import org.apache.myfaces.taglib.core.ValidatorImplTag;

public class IpValidatorTag extends ValidatorImplTag 
{
	 private static final long serialVersionUID = 1L;   
	 private String ipVersion;
	 
	 public IpValidatorTag() {
		// TODO Auto-generated constructor stub
		 super();
	}
	 

	protected Validator createValidator() throws JspException {
		 System.out.println("before create a invalidator : " + FacesContext.getCurrentInstance().getCurrentPhaseId());
		 this.setValidatorIdString("IpValidator");
		 IpValidator iv = (IpValidator)super.createValidator();
		 iv.setIpVersion(this.getIpVersion());
		// TODO Auto-generated method stub
		 return iv;
		 
	}
	 
	public String getIpVersion() {
		return ipVersion;
	}
	public void setIpVersion(String ipVersion) {
		this.ipVersion = ipVersion;
	}

}
