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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import org.junjun.twitter.TwitterUtils;
import org.junjun.twitter.bean.*;
public class Main extends JFrame
{
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
	
	private JButton toggleTag = new JButton("toggle");
	private JButton toggleUser = new JButton("toggle");
	private JButton ObtainResTag = new JButton("GetResByTag");
	private JButton ObtainResUser = new JButton("GetResByUser");
	private JButton nextRes = new JButton("next");
	private JButton preRes = new JButton("previous");
	private JCheckBox select = new JCheckBox("select it");
	
	private DateChooser dc1 = new DateChooser("yyyy-MM-dd");
	private DateChooser dc2 =new DateChooser("yyyy-MM-dd");
	public Main() {
		// TODO Auto-generated constructor stub
		
		this.setSize(width, height);
		this.setVisible(true);
		this.setLayout(new GridLayout(1, 2)); 
		left.setSize(leftsize,height);
		left.setBackground(Color.red);
		
		right.setSize(width-leftsize-10,height);
		right.setBackground(Color.gray);
		
		left.setLayout(new GridLayout(3,1)); 
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS)); 
		
		addTag();
		addName();
		addAction();
		
		this.addTxt();
		addPic();
		
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
		toggleTag.addActionListener(new TagToggleListener());	
		tagpanel.add(toggleTag);
		
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

	private void addName()
	{
		namepanel.setViewportView(jpanel);
		this.left.add(namepanel);
	}
	private void addAction()
	{
		this.actionpanel.add(dc1);
		this.actionpanel.add(dc2);
		
		actionpanel.setSize(actionpanel.getPreferredSize());
		actionpanel.setLayout(new FlowLayout());
		
	
		actionpanel.add(this.ObtainResTag);
		actionpanel.add(this.ObtainResUser);
		actionpanel.add(this.preRes);
		actionpanel.add(this.nextRes);
		actionpanel.add(this.select);
	
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
			jpanel.add(toggleUser);
			
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
			jpanel.add(toggleUser);
			
			for(TwitUser twiu:ltu)
			{		
				JCheckBox jbox = new JCheckBox(twiu.getName()); 
				jbox.setSelected(true);
				jpanel.add(jbox);
			}
			
			left.validate();
		}
	}

	
	// action listener for User toggle
	class UserToggleListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			
			System.out.println(event);
			addTag();

		}
	}


}
