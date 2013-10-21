package org.junjun.twitter.gui;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

class ImageScroller extends JScrollPane   
{  
	 JPanel p = new JPanel(new BorderLayout());
     public ImageScroller(Icon icon)   
     {  
          super(new JLabel(icon));  
         // Panel to hold the icon image   
         p.add(new JLabel(icon), BorderLayout.CENTER);   
         getViewport().add(p);  
    
         JScrollBar vsb = getVerticalScrollBar();   
         JScrollBar hsb = getHorizontalScrollBar();   
    
         vsb.setValue(icon.getIconHeight());   
         hsb.setValue(icon.getIconWidth()/10);   
     }//end of constructor  
     
     
     
     public void add(Icon icon)
     {
    	 p.add(new JLabel(icon), BorderLayout.CENTER);   
    	 p.validate();
     }
     public void clear()
     {
    	 p.removeAll();
     }
     
}//end of class ImageScroller  