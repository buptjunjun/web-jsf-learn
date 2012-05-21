package jsf.chapter1.BB;

import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class HelloBean 
{
	private int numControls;
	private HtmlPanelGrid controlPanel; //在jsp中绑定的
	private List<String> websites;      //怎么在backbean初始化一个website
	private Map<String,String> map;     //怎么在backbean中初始化一个map
	
	
	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public List<String> getWebsites() {
		return websites;
	}

	public void setWebsites(List<String> websites) {
		this.websites = websites;
	}

	public void addControls(ActionEvent actionEvent)
	{
		Application application = FacesContext.getCurrentInstance().getApplication();
		List children = controlPanel.getChildren();
		children.clear();
		
		for(int count = 0; count < this.numControls; count ++)
		{
			HtmlOutputText output = (HtmlOutputText)application.createComponent(HtmlOutputText.COMPONENT_TYPE);
			output.setValue(""+count);
			output.setStyle("color:blue");
			children.add(output);
		}
	}
	
	public String goodbye()
	{
		return "success";
	}
	
	public int getNumControls() {
		System.out.println("call controls get");
		return numControls;
	}
	public void setNumControls(int numControls) {
		System.out.println("call controls set");
		this.numControls = numControls;
	}
	public HtmlPanelGrid getControlPanel() {
		return controlPanel;
	}
	public void setControlPanel(HtmlPanelGrid controlPanel) {
		this.controlPanel = controlPanel;
	}
	
	
	

}
