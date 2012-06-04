package org.junjun.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

 class TestSerial implements Serializable
{
	private static final long serialVersionUID = 11111111111111L;
    int id;   
    String name;   
    public TestSerial(int id, String name) {   
        this.id = id;   
        this.name = name;   
    }   
    public String toString() {   
        return "DATA: " + id + " " +name;   
  
    }   
    
    public void hello(){};
  
    
}
 
  public class TestSerial1
  {
	  public static void main(String [] args)
	    {
	    	
		  //TestSerial1.save();
		  TestSerial1.load();
	    	
	    }
	    
	  
    static public void save()
    {
    	TestSerial SerialVersionUID =new TestSerial(1,"hrbeu");   
        System.out.println("object SerialVersionUID:"+SerialVersionUID);    
        try{   
            FileOutputStream fos=new FileOutputStream("SerialVersionUIDTest.txt");    
            ObjectOutputStream oos=new ObjectOutputStream(fos);    
            oos.writeObject(SerialVersionUID);    
            oos.flush();    
            oos.close();    
        }catch(Exception e){   
            System.out.println("Exception:"+e);    
        }   

    }
    
    //∑¥–Ú¡–ªØ
   static  public void load()
    {
    	 try{    
    		 TestSerial object2;    
             FileInputStream fis=new FileInputStream("SerialVersionUIDTest.txt");    
             ObjectInputStream ois=new ObjectInputStream(fis);    
             object2=(TestSerial)ois.readObject();    
             ois.close();    
             System.out.println("object deSerialVersionUID:"+object2);    
            }catch(Exception e){    
                System.out.println("Exception:"+e);    
            }    

    }
   

}  

