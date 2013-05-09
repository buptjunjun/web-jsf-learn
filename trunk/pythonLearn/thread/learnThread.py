'''
 python 线程
'''
import threading
import time


class timer(threading.Thread):#从threading.Thread继承的
    def __init__(self,num,interval):
        threading.Thread.__init__(self)
        self.thread_num = num
        self.interval = interval
        self.thread_stop = False
    def run(self):#overwrite run method put what you want to do here
        while not self.thread_stop:
            print('Thread Object(%d) , Time:%s\n' %(self.thread_num,time.ctime()));
            time.sleep(self.interval)
    def stop(self):
        self.thread_stop = True
        
def test():
    t = [];
    for i in range(0,3):
        thread = timer(i,2);
        t.append(thread)
    
    for thread in t:
        thread.start()
    time.sleep(20)
    
    for thread in t:
        thread.stop()

if __name__=="__main__":
    test()