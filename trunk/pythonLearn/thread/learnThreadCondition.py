'''
 python thread and condition
'''
#for python2.x use  import thread instead
import threading
import time
con = threading.Condition()
x = 0
class Producer(threading.Thread): # derive from threading.Thread
    def __init__(self,name):
        threading.Thread.__init__(self)
        self.t_name = name
        
    def run(self):
        global x
        
        con.acquire()
        if x > 5:
            print("producer %s wait"+ self.name)
            con.wait()
        else:
            while x<=5:
                x=x+1
                print("producer %s producing...%s" %(self.name ,str(x)))
            con.notifyAll()
        print(x)
        con.release()
    
class Consumer(threading.Thread): # derive from threading.Thread
    def __init__(self,name):
        threading.Thread.__init__(self)
        self.t_name = name
        
    def run(self):
        global x
        con.acquire()
        if x == 0:
            print("consumer %s consumer wait1" %(self.name))
            con.wait()
        else:
            x=x-1
            print("consumer %s consuming..." %(self.name,str(x)))
            con.notifyAll()
        print(x)
        con.release()
        
        
def test():
    print("start consumer1")
    print("start consumer2")
    c1 = Consumer('consumer1')
    c2 = Consumer('consumer2')
    print("start producer")
    p = Producer('producer')
    
    c1.start()
    c2.start()
    p.start()
    
     

    print(x)
    time.sleep(10)

if __name__=="__main__":
    test()