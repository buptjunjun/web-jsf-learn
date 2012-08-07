// ProducerConsumer.java
//
// @author Ҷ��
//
// ���Ǹ�����Ҫ��Thread���ӡ���Ҫע����ǣ�
// wait() ������synchronized �������ߴ��������
// wait()�����Ѿ����synchronized �������ߴ�������Ȩ��Thread��ʱ��Ϣ������ɥʧ����Ȩ
// ���ʱ�����ڸ��߳�ɥʧ����Ȩ���ҽ���ȴ��������߳̾���ȡ�ÿ���Ȩ���������ʵ�����µ���notifyAll()������wait()���̡߳�
// ��Ҫע����ǣ������ѵ��߳������Ѿ�ɥʧ�˿���Ȩ��������Ҫ�ȴ����������߳̽����������Ӷ��������»�ÿ���Ȩ��
//
// ����wait()��ȷ�������õ�ǰ�߳�ɥʧ����Ȩ���������߳̿��Գ�����롣
//
// ����wait()��ʹ�ã��������2�������̣߳����ұ����ڲ�ͬ�������»���wait()�е��̡߳�
//
//
// ���µ����ӣ�
// ProductStack ��һ�������߸������߹����ͬ�����ƣ�������ƾ�����ʲô���������Ҫwait()��ʲô���������Ҫwait()
// ���԰�ProductStack����һ����Ʒ�ֿ⡣����Ʒ�ֿ�����ʱ���������߳���Ҫwait()���Ӷ������Բ�Ʒ�ֿ�Ŀ��ơ�
// ���ʱ���������߳̾Ϳ��Խ����˶�ȡ�òֿ�Ŀ���Ȩ��һ�������������˲�Ʒ����ô�ֿ�Ͳ����ˡ�
// ���ʱ���������߳̾�ҪnotifyAll()�������̣߳��õȴ����������̻߳��ѡ�
// ���������߱����Ѻ������Ͻ�����������Ϊ����wait()��ʱ���Ѿ�ɥʧ�˶Բֿ�Ŀ���Ȩ�����Ծ���Ҫ�ȴ��������߳̽���������
// ��������ȡ�òֿ�Ŀ���Ȩ���ٽ���������
//
// �����ر�ע����ǣ�notifyAll()�������õ�ǰ�߳������ó�����Ȩ����ֻ��������wait()���е��̻߳��Ѷ��ѣ�
// ���ԶԲ��𣬾����һ����㣬������뻹��Ҫ��������ֿ���ܽ����������������
//
// �෴���ֿ�����յ�ʱ���������߳̾ͻ�wait()��Ȼ��ȴ��������߳���������Ʒ�������߽��̳����������������߳�������Ʒ
// ���һ����������̡߳�������������������ˡ�
//
///

package com.buptjunjun.concurrent;

public class ProducerConsumer {
      public static void main(String[] args) {
           ProductStack ps = new ProductStack();
           Producer p1 = new Producer(ps, "������1");
           Producer p2 = new Producer(ps, "������2");
           Consumer c1 = new Consumer(ps, "������1");
           Consumer c2 = new Consumer(ps, "������2");
           Consumer c3 = new Consumer(ps, "������3");
           Consumer c4 = new Consumer(ps, "������4");
           new Thread(p1).start();
           new Thread(p2).start();
           new Thread(c1).start();
           new Thread(c2).start();
           new Thread(c3).start();
           new Thread(c4).start();
      }
}
 
class Product {
      int id;
 
      private String producedBy = "N/A";
 
      private String consumedBy = "N/A";
 
      // ���캯����ָ����ƷID�Լ����������֡�
      Product(int id, String producedBy) {
           this.id = id;
           this.producedBy = producedBy;
      }
 
      // ���ѣ���Ҫָ������������
      public void consume(String consumedBy) {
           this.consumedBy = consumedBy;
      }
 
      public String toString() {
           return "Product : " + id + ", produced by " + producedBy
                      + ", consumed by " + consumedBy;
      }
 
      public String getProducedBy() {
           return producedBy;
      }
 
      public void setProducedBy(String producedBy) {
           this.producedBy = producedBy;
      }
 
      public String getConsumedBy() {
           return consumedBy;
      }
 
      public void setConsumedBy(String consumedBy) {
           this.consumedBy = consumedBy;
      }
 
}
 
// ���class���ǲֿ⣬�������߸������߹�ͬ�������Ȩ��ͬ����Դ
class ProductStack {
      int index = 0;
 
      Product[] arrProduct = new Product[6];
 
      // pushʹ�����������߷��ò�Ʒ��
      public synchronized void push(Product product) {
           // ����ֿ�����
           while (index == arrProduct.length) // ���ﱾ��������if(),�������catch
                                            // exception������⣬������indexԽ��
           {
                 try {
                      // here, "this" means the thread that is using "push"
                      // so in this case it's a producer thread instance.
                      // the BIG difference between sleep() and wait() is, once
                      // wait(),
                      // the thread won't have the lock anymore
                      // so when a producer wait() here, it will lost the lock of
                      // "push()"
                      // While sleep() is still keeping this lock
                      // Important: wait() and notify() should be in "synchronized"
                      // block
 
                      System.out.println(product.getProducedBy() + " is waiting.");
                      // �ȴ������Ҵ������˳�push()
                      wait();
                 } catch (InterruptedException e) {
                      e.printStackTrace();
                 }
           }
           System.out.println(product.getProducedBy() + " sent a notifyAll().");
 
           // ��Ϊ���ǲ�ȷ����û���߳���wait()���������Ǽ�Ȼ�����˲�Ʒ���ͻ����п��ܵȴ��������ߣ�������������׼������
           notifyAll();
           // ע�⣬notifyAll()�Ժ󣬲�û���˳������Ǽ���ִ��ֱ����ɡ�
           arrProduct[index] = product;
           index++;
           System.out.println(product.getProducedBy() + " ������: " + product+ " remaining "+ index);
      }
 
      // pop������������ȡ����Ʒ��
      public synchronized Product pop(String consumerName) {
           // ����ֿ����
           while (index == 0) {
                 try {
                      // here will be the consumer thread instance will be waiting ,
                      // because empty
                      System.out.println(consumerName + " is waiting.");
                      // �ȴ������Ҵ������˳�pop()
                      wait();
                 } catch (InterruptedException e) {
                      e.printStackTrace();
                 }
           }
 
           System.out.println(consumerName + " sent a notifyAll().");
           // ��Ϊ���ǲ�ȷ����û���߳���wait()���������Ǽ�Ȼ�����˲�Ʒ���ͻ����п��ܵȴ��������ߣ�������������׼������
           notifyAll();
           // ע�⣬notifyAll()�Ժ󣬲�û���˳������Ǽ���ִ��ֱ����ɡ�
           // ȡ����Ʒ
           index--;
           Product product = arrProduct[index];
           product.consume(consumerName);
           System.out.println(product.getConsumedBy() + " ������: " + product +" remaining "+ index);
           return product;
      }
}
 
class Producer implements Runnable {
      String name;
 
      ProductStack ps = null;
 
      Producer(ProductStack ps, String name) {
           this.ps = ps;
           this.name = name;
      }
 
      public void run() {
           for (int i = 0; i < 20; i++) {
                 Product product = new Product(i, name);
                 ps.push(product);
                 try {
                      Thread.sleep((int) (Math.random() * 1000));
                 } catch (InterruptedException e) {
                      e.printStackTrace();
                 }
           }
      }
}
 
class Consumer implements Runnable {
      String name;
 
      ProductStack ps = null;
 
      Consumer(ProductStack ps, String name) {
           this.ps = ps;
           this.name = name;
      }
 
      public void run() {
           for (int i = 0; i < 20; i++) {
                 Product product = ps.pop(name);
                 try {
                      Thread.sleep((int) (Math.random() * 3000));
                 } catch (InterruptedException e) {
                      e.printStackTrace();
                 }
           }
      }
} 
