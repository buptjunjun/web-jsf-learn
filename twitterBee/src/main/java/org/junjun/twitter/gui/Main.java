package org.junjun.twitter.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.Tag;
import org.junjun.controller.logic.PicServicesMongo;
import org.junjun.mongo.DAOMongo;
import org.junjun.twitter.ReportThead;
import org.junjun.twitter.ResourceProcessor;
import org.junjun.twitter.StatusFetcher;
import org.junjun.twitter.TwitterUtils;
import org.junjun.twitter.bean.*;


public class Main extends JFrame
{
	private String host = "http://localhost:8888";
	private int height =668;
	private int width=1240;
	private int leftsize=400;
	private int tagheight = 400;
	private JPanel left = new JPanel();
	private JPanel right = new JPanel();
	private JPanel tagpanel=new JPanel();
	private JScrollPane namepanel=new JScrollPane();
	private JPanel jpanel = new JPanel();//user name
	private JPanel actionpanel=new JPanel();
	
	private Map<String,Tag2User> tags= new HashMap<String,Tag2User>();
	private List<JCheckBox> listtags=new ArrayList<JCheckBox>();
	
	private Map<String,TwitUser> users = new HashMap<String,TwitUser>();
	private List<JCheckBox> listusers =new ArrayList<JCheckBox>();
	
	private List<TwitResources>  currentResource = new ArrayList<TwitResources> ();  // resources
	private int currentResPosition=0;  // current resources;

	private List<TwitResources>  choosenResource = new ArrayList<TwitResources> ();  // resources I selected
	
/*	private JButton toggleTag = new JButton("toggle");
	private JButton toggleUser = new JButton("toggle");*/
	private JButton ObtainResUser = new JButton("grabRes");
	private JButton downloadResUser = new JButton("downloadRes");
	private JButton nextRes = new JButton("next");
	private JButton preRes = new JButton("previous");
	private JButton getDownloadedRes = new JButton("getDownloadedRes");
	private JButton getSelectedRes = new JButton("getSelectedRes");
	private JButton getResByTag = new JButton("getResByTag");
	private JCheckBox select = new JCheckBox("select it");
	private JButton selectBtn = new JButton("selectButton");
	private JButton stop = new JButton("stop");
	private JButton save2webbtn = new JButton("save2webbtn");
	
	private JButton autoSelectBtn = new JButton("AutoSelectBtn");
	
	JTextArea jhost = new JTextArea("host");
	private JTextArea status = new JTextArea("status:-------------------------------------------------------------------------------------------------------------------------------------");
	private DateChooser dc1 = new DateChooser("yyyy-MM-dd");
	private DateChooser dc2 =new DateChooser("yyyy-MM-dd");	
	final private DAOMongo daomongo = new DAOMongo("twitter");
	final StatusFetcher sf = new StatusFetcher();
	final ResourceProcessor rp = new ResourceProcessor();
	
	final List<TwitResources> lres  = new ArrayList<TwitResources>();
	static int currPosition = -1;
	private  boolean flag = false;
	Thread currentThread = null;
	
	
	public Main() {
		// TODO Auto-generated constructor stub
		
		this.setSize(width, height);
		this.setVisible(true);
		this.setLayout(new GridLayout(1, 2)); 
		left.setSize(leftsize,height);
	
		right.setSize(width-leftsize-10,height);
		right.setBackground(Color.gray);
		
		left.setLayout(new GridLayout(4,1)); 
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS)); 
		
		addTag();
		addName();
		addAction();
		addStatus();

		
		this.add(left);
		this.add(right);		
		
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void addTag()
	{	
		tagpanel.setLayout(new FlowLayout());		
		
		List<Tag2User> lt = new ArrayList<Tag2User>();
		lt = TwitterUtils.init("taguser.json");
	/*	toggleTag.addActionListener(new TagToggleListener());	
		tagpanel.add(toggleTag);*/
		
		for(Tag2User tu:lt)
		{		
			tags.put(tu.getTag(), tu);
		}
		
		for(Entry entry:this.tags.entrySet())
		{
			JCheckBox jbox = new JCheckBox((String)entry.getKey()); 
			jbox.addActionListener(new TagListener());
			jbox.setSelected(false);
			this.listtags.add(jbox);
			tagpanel.add(jbox);
		}
		
		
		this.left.add(tagpanel);
		
		
	}

	private void addStatus()
	{
		JScrollPane jsp = new JScrollPane();
		JPanel jpanel = new JPanel();
		jpanel.add(status);
		status.setSize(leftsize-10, height/4-10);
		jsp.setViewportView(jpanel);
		
		this.left.add(jsp);
	}
	private void addName()
	{
		namepanel.setViewportView(jpanel);
		this.left.add(namepanel);
	}
	private void addAction()
	{
		actionpanel.setLayout(new GridLayout(10,2));
		this.actionpanel.add(this.jhost);
		this.actionpanel.add(dc1);
		this.actionpanel.add(dc2);
		
		actionpanel.setSize(actionpanel.getPreferredSize());
		actionpanel.setLayout(new FlowLayout());
		
	
		actionpanel.add(this.ObtainResUser);
		actionpanel.add(downloadResUser);
		actionpanel.add(getDownloadedRes);
		actionpanel.add(getResByTag);
		actionpanel.add(getSelectedRes);
		actionpanel.add(this.preRes);
		actionpanel.add(this.nextRes);
		actionpanel.add(this.select);
		actionpanel.add(this.selectBtn);
		actionpanel.add(stop);
		actionpanel.add(save2webbtn);
		actionpanel.add(this.autoSelectBtn);
		
		this.save2webbtn.addActionListener(new Save2WebListener());
		this.selectBtn.addActionListener(new SelectListener());
		this.ObtainResUser.addActionListener(new GrabListener());
		this.downloadResUser.addActionListener(new DownloadListener());
		this.getDownloadedRes.addActionListener(new GetDownloadedResListener() );
		this.getSelectedRes.addActionListener(new GetSelectedResListener());
		this.nextRes.addActionListener(new NextListener());
		this.preRes.addActionListener(new PreListener());
		this.stop.addActionListener(new StopListener());
		this.getResByTag.addActionListener(new GetResByTag());
		this.autoSelectBtn.addActionListener(new AutoSelectByNameListener());
		
		this.left.add(actionpanel);
	}	
	
	private void addTxt()
	{
	
		JTextArea jta = new JTextArea("dddddddddddddddddd\nsdfsdf");
		this.right.add(jta);
		
	}
	
	private void addPic()
	{
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("test.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Icon icon = new ImageIcon(myPicture);
		ImageScroller is = new ImageScroller(icon);

		this.right.add(is);
	}
	
	static public void main(String [] args)
	{
		new Main();
	}
	
	// action listener for Tag toggle
	class TagToggleListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			Set<TwitUser> ltu = new HashSet<TwitUser>();
			jpanel.removeAll();
			for(JCheckBox jcb:listtags)
			{
				if(!jcb.isSelected())
					continue;
				
				Tag2User tu= tags.get(jcb.getText());
				Set<TwitUser> l = tu.getTwuser();
				ltu.addAll(ltu);
			}
			int size=ltu.size();
			
			int rows = size/4+1;
			jpanel.setLayout(new GridLayout(rows,5));
		/*	jpanel.add(toggleUser);*/
			
			for(TwitUser twiu:ltu)
			{		
				JCheckBox jbox = new JCheckBox(twiu.getName()); 
				jbox.setSelected(true);
				jpanel.add(jbox);
			}
			namepanel.setViewportView(jpanel);
			left.add(namepanel);
			
		}
	}
	
	// action listener for Tag toggle
	class TagListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			jpanel.removeAll();
			users.clear();
			listusers.clear();
			
			
			Set<TwitUser> ltu = new HashSet<TwitUser>();
			
			jpanel.removeAll();
			for(JCheckBox jcb:listtags)
			{
				if(!jcb.isSelected())
					continue;
				
				Tag2User tu= tags.get(jcb.getText());
				Set<TwitUser> l = tu.getTwuser();
				ltu.addAll(l);
			}
			int size=ltu.size();
			
			int rows = size/4+1;
			jpanel.setLayout(new GridLayout(rows,5));
		/*	jpanel.add(toggleUser);*/
			
			for(TwitUser twiu:ltu)
			{		
				JCheckBox jbox = new JCheckBox(twiu.getName()); 
				jbox.setSelected(true);
				jpanel.add(jbox);
				listusers.add(jbox);
				users.put(twiu.getName(), twiu);
			}
			
			left.validate();
		}
	}

	
	// action listener for User toggle
	class GrabListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			
			System.out.println(event);
			final List<TwitUser> selectedUser = new ArrayList<TwitUser>();
			for(JCheckBox jcb:listusers)
			{
				if(jcb.isSelected())
				{
					TwitUser twu = users.get(jcb.getText());
					if(twu!=null)
						selectedUser.add(twu);
				}
			}
			
			System.out.println("run");
			SwingUtilities.invokeLater(new Runnable(){


				public void run() {
					// TODO Auto-generated method stub
					flag = true;
					for(TwitUser twu:selectedUser)
					{
						if(flag == false)
							break;
						try
						{
							System.out.println("start Fetching at "+new Date().toLocaleString());
							status.append("\n"+"start Fetching at "+new Date().toLocaleString());
							List<TwitStatus> lts = sf.fetch(twu);
							System.out.println("Fetched  "+ (lts == null ? 0:lts.size())+" TwitStatus from "+twu.getName()+"("+twu.getTag()+") "+new Date().toLocaleString());
							status.append("\n"+"Fetched  "+ (lts == null ? 0:lts.size())+" TwitStatus from "+twu.getName()+"("+twu.getTag()+") "+new Date().toLocaleString());
							if(lts!=null)
							{
								for(TwitStatus ts:lts)
								{
									try
									{						
										daomongo.insert(ts);
										List<TwitResources> lres = rp.process(ts);
										for(TwitResources tres:lres)
											daomongo.insert(tres);
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
							}
							TimeUnit.SECONDS.sleep(2);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						
						
					}
					
					System.out.println("end Fetching at "+new Date().toLocaleString());
					status.append("\n"+"end Fetching at "+new Date().toLocaleString());
				}
				
			});
			
			
		}
	}


	class StopListener  implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			flag = false;
			if(currentThread!=null)
				currentThread.stop();
		}
	}
		class DownloadListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				
				System.out.println("run");
				currentThread = new Thread(){

					
					public void run() 
					{					
						flag = true;
						ReportThead.getInstance().clear();
						String tag = getTag();
						List reses = getResourceFromDB(TwiConstant.Undownloaded,tag);
						lres.clear();
						lres.addAll(reses);
						for(TwitResources tres:lres)
						{
							if(flag == false)
								break;
							if(TwiConstant.TypePhoto.equals(tres.getType()))
							{
								String url = tres.getUrl();
								String path = TwiConstant.base+tres.getPath();
								Report report = new Report();
								try {
									System.out.println("start to download "+ url +" from "+tres.getUserName());
									TwitterUtils.downloadFile(url, path);
									report.setSucess(true);
									tres.setMagicNum(TwiConstant.Downloaded);
									report.setDescription("success to download "+ url+ " from "+tres.getUserName());
									System.out.println("success to download "+ url +" from "+tres.getUserName());
									status.append("\n"+"success to download "+ url +" from "+tres.getUserName());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									report.setSucess(false);
									report.setDescription("fail to download "+ url+"  error:"+e.getMessage());
									System.out.println("fail to download "+ url+" from "+tres.getUserName()+"  error:"+e.getMessage());
									status.append("\n"+"fail to download "+ url+" from "+tres.getUserName()+"  error:"+e.getMessage());
								}
								ReportThead.getInstance().addReport(report);
								update(tres,daomongo);
							}
						}
						System.out.println(" donwnload finished");
						status.append("\n"+" donwnload finished");
						// get the report answer;
						ReportThead.getInstance().report();
					}
					
				};
				currentThread.start();
				
				
			}
		}

		
		class NextListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
		
				currPosition++;
				System.out.println("currPosition  = "+currPosition);
				status.append("\n"+" currPosition  = "+currPosition);
				TwitResources tr = lres.get(currPosition);
				String txt = "score:"+tr.getScore() +"\ntext:"+ tr.getTxt();
				String path = TwiConstant.base+tr.getPath();
				path=path.replaceAll("//", "/");
				showResources(txt,path);	
			}
		}

		class PreListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				
				

				currPosition--;
				System.out.println("currPosition  = "+currPosition);
				status.append("\n"+" currPosition  = "+currPosition);
				if(currPosition<0)
				{
					currPosition = -1;
					return;
				}
				TwitResources tr = lres.get(currPosition);
				String txt = "score:"+tr.getScore() +"\ntext:"+ tr.getTxt();
				String path = TwiConstant.base+tr.getPath();
				path=path.replaceAll("//", "/");
				showResources(txt,path);		
			
		
			}
		}
		

		class SelectListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				System.out.println("currPosition  = "+currPosition);
				if(currPosition<0)
				{
					currPosition = -1;
					return;
				}
				TwitResources tr = lres.get(currPosition);
				
				if(select.isSelected())
				{
					tr.setMagicNum(TwiConstant.Selected);
					System.out.println("select "+tr.getUserName() +" > "+tr.getPath());
					status.append("\n"+"select "+tr.getUserName() +" > "+tr.getPath());
				}
				
				update(tr,daomongo);	
			
		
			}
		}
		
		class Save2WebListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				String tag1 = getTag();
				List reses = getResourceFromDB(TwiConstant.Selected,tag1);
				lres.clear();
				lres.addAll(reses);		
				save2web(lres);
				
				List<Tag> ltags=new ArrayList<Tag>();
				for(JCheckBox jck:listtags)
				{
					String tag= jck.getText();
					Tag t =new Tag();
					t.setId(tag);
					t.setDesc(tag);
					t.setName(tag);
					t.setType(tag);
					ltags.add(t);
				}
				
				String host= jhost.getText().trim();
				String urlTag= host+"/api/addTags";
				TwitterUtils.postItems2Server(urlTag, ltags);
			}
		}

		class GetDownloadedResListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				
				System.out.println("run");
				SwingUtilities.invokeLater(new Runnable()
				{				
					public void run() 
					{
						String tag = getTag();
						List reses = getResourceFromDB(TwiConstant.Downloaded,tag);
						lres.clear();
						lres.addAll(reses);
					}
					
				});
				
				
			}
		}
				
		
		// only deal with one tag
		class GetResByTag implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				
				String tag = null;
				
				for(JCheckBox jcb:listtags)
				{
					if(jcb.isSelected())
					{
						tag = jcb.getText();
						break;
					}					
				}
				
				if(tag == null) return;
				
				List reses = getResourceFromDB(TwiConstant.Selected,tag);
				lres.clear();
				lres.addAll(reses);
				currentResPosition = -1;
			}
		}
		
		// only deal with one tag
		class AutoSelectByNameListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				currentResPosition = -1;
				String name = null;
				Set<String> names = new HashSet<String>();
				for(JCheckBox jcb:listusers)
				{
					if(jcb.isSelected())
					{
						name = jcb.getText();
						names.add(name);
					}					
				}
				
				
				Date date = dc1.getDate();
				String filename = TwitterUtils.fileNameFromDate(date);
				
				List<TwitStatus> lts = TwitterUtils.getTwitStatusFromJson(filename);
				List<TwitResources> tmp = new ArrayList<TwitResources>();
				for(TwitStatus ts:lts)
				{
					List<TwitResources> tmpTrs = rp.process(ts);
					if(tmpTrs!=null)
						tmp.addAll(tmpTrs);
				}
				
				lres.clear();
				for(TwitResources tr:tmp)
				{
					if(names.contains(tr.getUserName()))
						lres.add(tr);
						
				}
				tmp.clear();
				tmp = null;
				currentResPosition = -1;
			}
		}
		
		
		class GetSelectedResListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				
				System.out.println("run");
				SwingUtilities.invokeLater(new Runnable()
				{				
					public void run() 
					{
						String tag = getTag();
						List reses = getResourceFromDB(TwiConstant.Selected,tag);
						lres.clear();
						lres.addAll(reses);
						currentResPosition = -1;
					}
					
				});
				
				
			}
		}
		
		private void showResources(String txt, String path)
		{
			right.removeAll();
			
			JTextArea jta = new JTextArea(txt);
			
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(new File(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Icon icon = new ImageIcon(myPicture);
			ImageScroller is = new ImageScroller(icon);

			right.add(jta);
			right.add(is);
			right.validate();
			validateTree();
		}
		
		
		private void update(TwitResources tr ,DAOMongo daomongo)
		{
			if(tr == null || daomongo == null)
				return;
			
			HashMap<String,Object> constrains = new HashMap<String,Object>();
			constrains.put("id", tr.getId());
			daomongo.update(tr, constrains);
		}
		
		public List<TwitResources> getResourceFromDB(int maginNum ,String tag)
		{
			Date date1 = dc1.getDate();
			Date date2 = dc2.getDate();
			date1.setHours(0);
			date2.setHours(0);
			System.out.println("time span from - to:"+ date1.toLocaleString()+"-"+date2.toLocaleString());
			
			HashMap<String,Object> constrainEQ = new HashMap<String ,Object>();
			HashMap<String,Object> constrainGT = new HashMap<String ,Object>();
			HashMap<String,Object> constrainLT = new HashMap<String ,Object>();
			
			// date between date1 and date2
			constrainGT.put("processDate", date1);		
			
			constrainEQ.put("magicNum",maginNum);
			if(!StringUtils.isEmpty(tag))
				constrainEQ.put("tag",tag);
				
			
			List<TwitResources> reses = daomongo.search(constrainLT, constrainGT, constrainEQ, "score", DAOMongo.DESCENDING, Integer.MAX_VALUE, TwitResources.class);
			System.out.println("get "+reses.size() +" resources");
			return reses;
		}
		
		
		
		
		
		public void save2web(List<TwitResources> ltr)
		{
			 if(ltr == null)
			 {
				 System.out.println("save2web : ltr == null");
				 return;
			 }
			 
			 DAOMongo daoweb = new  DAOMongo("42.96.143.59",27017,PicServicesMongo.dbname);
			 
			
			 List<Item> litems = new ArrayList<Item>();
			 for(TwitResources tr : ltr)
			 {
				 Item item = TwitResources2Item(tr);
				 System.out.println(item);
				 litems.add(item);
				 //daoweb.insert(item);
			 }
			 
				String host= jhost.getText().trim();
				String url= host+"/api/addResources";
			    TwitterUtils.postItems2Server(url, litems);
		}
		
		static public Item TwitResources2Item(TwitResources tr)
		{
			Item item = new Item();
			item.setId(tr.getId()+"");
			item.setUrl(tr.getUrl());
			item.setUrl1(tr.getUrl());
		
			item.setDesc(tr.getTxt());
			item.setTag(tr.getTag());
			item.setCata(tr.getType());
			
			Date date = tr.getProcessDate();
			item.setDate(date);
			
			return item;
		}
		
		private String getTag()
		{
			String tag = null;
			
			for(JCheckBox jcb:listtags)
			{
				if(jcb.isSelected())
				{
					tag = jcb.getText();
					break;
				}					
			}
			return tag;
		}
}
