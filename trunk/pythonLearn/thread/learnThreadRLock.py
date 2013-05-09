'''
 python thread and lock
'''
#for python2.x use  import thread instead
import threading
import time
mylock = threading.RLock()  # allocate a lock
num=0                             #shared resource

class myThread(threading.Thread): # derive from threading.Thread
    def __init__(self,name):
        threading.Thread.__init__(self)
        self.t_name = name
    def run(self):
        global num
           
        while True:
            mylock.acquire() # get the lock
            print("thread %s locked! num=%d"%(self.t_name,num))
            if num > 10 :
                print("thread %s released ! num=%d"%(self.t_name,num))
                mylock.release();
                break;
            num+=1
            print("thread %s released ! num=%d"%(self.t_name,num))
            mylock.release(); #release the lock
            
        
def test():
    threadA = myThread("A")
    threadB = myThread("B")
    threadA.start()
    threadB.start()
    time.sleep(1)

if __name__=="__main__":
    test()