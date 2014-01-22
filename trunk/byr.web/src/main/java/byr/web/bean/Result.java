package byr.web.bean;

public class Result 
{
	private boolean success = false;        // if sucess
	private String errorMessge = "";        // if failed , the errorMessage
	private Object data = null;             // the data return to the web.
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorMessge() {
		return errorMessge;
	}
	public void setErrorMessge(String errorMessge) {
		this.errorMessge = errorMessge;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
