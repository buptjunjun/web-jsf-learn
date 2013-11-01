package org.junjun.bean.part1;

public class ResponseData
{
	private boolean status = true;
	private Object data = null;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
