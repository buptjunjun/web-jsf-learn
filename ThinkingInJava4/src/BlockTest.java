

class BlockThread extends Thread
{ 
	private int  i;
    
	/**
	 * ͬ������ ����i��ֵ
	 * @return
	 */
	public synchronized int  read()
	{
		return i;
	}
	/**
	 * ���ϵ�����i
	 */
	public synchronized void run() {
		// TODO Auto-generated method stub
		while(true)
		{	
			try {
				// wait���ͷ�������read���Զ�ȡ
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
	   //����t�̣߳�t��ȡ�������ڷ���run��whileѭ���� ��Զ�����ͷ���
	   t.start();
	  
	   while(true)
	   {
		   try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		   //������������ӦΪrun������ȡ�������Ҳ����ͷ�
		   System.out.println("read:" +t.read());
	   }
   }
}
