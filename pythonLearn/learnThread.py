'''
Created on 2012-8-4
python thread test
@author: junjun
'''

import time
import threading 

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
    
def test():
    thread1 = timer(1,1)
    thread2 = timer(2,2)

    thread1.start()
    thread2.start()
    
    time.sleep(10)
    
    thread1.stop()
    thread2.stop()
test()
    
        
        