

class BlockThread extends Thread
{ 
	private int  i;
    
	/**
	 * 同步方法 返回i的值
	 * @return
	 */
	public synchronized int  read()
	{
		return i;
	}
	/**
	 * 不断地增加i
	 */
	public synchronized void run() {
		// TODO Auto-generated method stub
		while(true)
		{	
			try {
				// wait会释放锁所以read可以读取
				wait(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
			this.notifyAll();
			System.out.println("i = " + i);
		}
	}
}



public class BlockTest 
{
   public static void main(String [] args)
   {
	   BlockThread t = new BlockThread();
	   //启动t线程，t获取了锁，在方法run中while循环中 永远不会释放锁
	   t.start();
	  
	   while(true)
	   {
		   try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		   //会阻塞在这里应为run方法获取的锁，且不会释放
		   System.out.println("read:" +t.read());
	   }
   }
}
