package org.junjun.twitter.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private JPanel namepanel=new JPanel();
	private JPanel actionpanel=new JPanel();
	
	public Main() {
		// TODO Auto-generated constructor stub
		
		this.setSize(width, height);
		this.setVisible(true);
		this.setLayout(new GridLayout(1, 2)); 
		left.setSize(leftsize,height);
		left.setBackground(Color.red);
		
		right.setSize(width-leftsize-10,height);
		right.setBackground(Color.gray);
		
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS)); 
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS)); 
		
		addTag();
		addTwitName();
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
		
		
		tagpanel.setSize(tagpanel.getPreferredSize());
		tagpanel.setLayout(new FlowLayout());
		JButton jbtn = new JButton("toggle");
		tagpanel.add(jbtn);
		for(int i = 0; i<16;i++)
		{
			
			JCheckBox jbox = new JCheckBox("111"); 
			tagpanel.add(jbox);
		}
		
		this.left.add(tagpanel);
		
		
	}
	
	private void addTwitName()
	{
		
		namepanel.setSize(namepanel.getPreferredSize());
		namepanel.setLayout(new FlowLayout());
		
		JButton jbtn = new JButton("toggle");
		namepanel.add(jbtn);
		for(int i = 0; i<26;i++)
		{
			
			JCheckBox jbox = new JCheckBox("111"); 
			jbox.setSelected(true);
			namepanel.add(jbox);
		}
		
		this.left.add(namepanel);

	}
	
	private void addAction()
	{
		actionpanel.setSize(actionpanel.getPreferredSize());
		actionpanel.setLayout(new FlowLayout());
		for(int i = 0; i<6;i++)
		{
			
			JButton jbutton = new JButton("111"); 
			actionpanel.add(jbutton);
		}
		this.left.add(actionpanel);
	}	
	
	private void addTxt()
	{
	
		JTextArea jta = new JTextArea("dddddddddddddddddd");
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
}
