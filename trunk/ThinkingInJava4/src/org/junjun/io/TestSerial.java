package org.junjun.io;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * 使用 serialVersionUid来处理持久化时候的版本控制
 * @author andyWebsense
 *
 */
 class TestSerial1 implements Serializable
{
	// public static final long serialVersionUID = 11111111L;
	 
     int id;   
     String name;   
     public  String score;
     public TestSerial1(int id, String name)
     {   
        this.id = id;   
        this.name = name;   
     }   
    
    public String toString()
    {   
        return "DATA: " + id + " " +name;   
    }   
    
    //序列化
    public void save()
   {
   	TestSerial1 SerialVersionUID =new TestSerial1(1,"hrbeu");   
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
   
   //反序列化
    public void load()
   {
   	 try{    
   		    TestSerial1 object2;    
            FileInputStream fis=new FileInputStream("SerialVersionUIDTest.txt");    
            ObjectInputStream ois=new ObjectInputStream(fis);    
            object2=(TestSerial1)ois.readObject();    
            ois.close();    
            System.out.println("object deSerialVersionUID:"+object2);    
           }catch(Exception e){    
               System.out.println("Exception:"+e);    
           }    

   }
  
}
 

public class TestSerial {

	public static void main(String [] args)
    {
		TestSerial1 t = new TestSerial1(0, "test");
	//	t.save();
		t.load();
    }
}
