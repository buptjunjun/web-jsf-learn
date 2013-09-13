package co.aiml;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import bitoflife.chatterbean.AliceBot;

public class Chat extends JFrame implements ActionListener,KeyListener{
	
	public static final String END = "bye";
	// demo frame for testing
	private String text="你好";	
	private JTextArea screen=new JTextArea();
	private JScrollPane jscreen=new JScrollPane(screen);
	private JPanel jpinput=new JPanel();
	private JButton jbinput=new JButton("Say");
	private JTextArea jtinput=new JTextArea();	
	// initing alice bot
	private AliceBotMother xmother = new AliceBotMother();
	private AliceBot xbot;

	
	public Chat() throws Exception{	
		//cofiging the frame
		this.setSize(400,500);
		this.setTitle("Amelie Demo 0.0.1");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setLayout(new BorderLayout());	
		
		screen.setText(text);
		screen.setEditable(false);
		screen.setBackground(Color.black);
		screen.setForeground(Color.white);
		screen.setFont(new Font("Serif",Font.PLAIN,13));
		screen.setLineWrap(true);
		screen.setWrapStyleWord(true);

		
		jpinput.setLayout(new BorderLayout());			
		jpinput.add(jtinput,BorderLayout.CENTER);
		jpinput.add(jbinput,BorderLayout.EAST);
		jtinput.setFont(new Font("Serif",Font.PLAIN,14));
		jtinput.setLineWrap(true);
		jtinput.setWrapStyleWord(true);
		
		this.add(jscreen,BorderLayout.CENTER);
		this.add(jpinput,BorderLayout.SOUTH);
		this.setVisible(true);
		
		jbinput.addActionListener(this);
		jtinput.addKeyListener(this);
		
		//configing the alice bot
		xbot = xmother.newInstance();
		xbot.respond("welcome");
		xmother.setUp(); 
	}


/**
	public static String input()
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("you say>");
		String input = "";
		try 
		{
			input = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;
	}
**/
	
	
	
	public static void main(String[] args) throws Exception
	{
		 Chat ch=new Chat();		
//		 AliceBotMother mother = new AliceBotMother();		  
//	     mother.setUp();
//	     AliceBot bot = mother.newInstance();
//	     System.err.println("Alice>" + bot.respond("welcome"));
//		 while(true)
//		 {
//			 String input = Chat.input();
//			 // while the user saying 'bye'
//			 if(Chat.END.equalsIgnoreCase(input))
//				 break;
//			 // do some respond..
//			 System.err.println("Alice>" + bot.respond(input));
//		 }
		 
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String respon="Alice: error";	  	       
		respon=xbot.respond(jtinput.getText());
		text=text+"\nYou: "+jtinput.getText();
		text=text+"\nAlice: "+respon;
		screen.setText(text);
		jtinput.setText("");
	}

	static private Pattern patternChinese = Pattern.compile("[a-zA-Z0-9]");
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()== KeyEvent.VK_ENTER){
			String respon="Alice: error";	
			
			String request = jtinput.getText();
			StringBuffer requestNew = new StringBuffer(request.length()+2);
			for(int i= 0;i < request.length();i++)
			{				
				String sub = request.substring(i, i+1);
				if(patternChinese.matcher(sub).matches())
					requestNew.append(sub);
				else
					requestNew.append(sub+" ");
			}
			
			String requestTxt = requestNew.toString();
			System.out.println(requestTxt);
			respon=xbot.respond(requestTxt);
			respon=xbot.respond(request);
			text=text+"\nYou: "+jtinput.getText();
			text=text+"\nAlice: "+respon;
			screen.setText(text);
		}		
	}


	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()== KeyEvent.VK_ENTER){
			jtinput.setText("");	
		}
	}


	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
