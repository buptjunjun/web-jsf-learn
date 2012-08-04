'''
Created on 2012-8-4
python thread test
@author: junjun
'''

import time
import threading

# create a lock
mylock = threading.RLock()

# critial resource   
num = 0
# crate a class inherited from threading.Thread
class timer(threading.Thread):
    def __init__(self,num, interval):
        # must init parent class
        threading.Thread.__init__(self)
        
        self.thread_num = num
        self.interval = interval
        self.thread_stop = False
    #define run function , like java    
    def run(self):
        while not self.thread_stop:
            print("Thread Object(%d) ,Time:%s" %(self.thread_num,time.ctime()))
            time.sleep(self.interval)
    
    def stop(self):
        self.thread_stop = True
 
# test synchronize
class MyThread(threading.Thread):
   
    def __init__(self,name):
        # must init parent class ,set nama as the thread's name
        threading.Thread.__init__(self)
        self.t_name = name
    
    def run(self):   
        global num   
        while True:   
            # get the clock
            mylock.acquire()   
            print ('\nThread(%s) locked, Number: %d'%(self.t_name, num))
            if num%10 >= 9:   
                mylock.release()   
                print ('\nThread(%s) released, Number: %d'%(self.t_name, num))   
                break   
            num+=1   
            print ('\nThread(%s) released, Number: %d'%(self.t_name, num))
            try:
                mylock.release()
            except:
                print("exception ")  
            time.sleep(1)    
            
       
def test():
    thread1 = MyThread("A")
    thread2 = MyThread("B")
    thread1.start()
    thread2.start()

test()
    
        
        