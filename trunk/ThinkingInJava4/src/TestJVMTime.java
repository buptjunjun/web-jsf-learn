import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * 
 *  test if the jvm time is the same to the System time.
 *
 */
public class TestJVMTime {

    
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		sdf1.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));
		System.out.println("Turkey/Istanbul time: "+sdf1.format(new Date()));
		sdf1.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		System.out.println("Korea/Seoul time:     "+sdf1.format(new Date()));
	}
	
}
