'''
 python _thread and lock
 _thread is a lower level module than threading module.
 and threading is built based on _thread
 in python2.x you should import thread not _thread
'''
#for python2.x use  import thread instead
import _thread
import time
mylock = _thread.allocate_lock()  # allocate a lock
num=0                             #shared resource

def add_num(name):
    global num
    while True:
        mylock.acquire() # get the lock
        print("thread %s locked! num=%d"%(name,num))
        if num > 10 :
            print("thread %s released ! num=%d"%(name,num))
            mylock.release();
            _thread.exit_thread()
        num+=1
        print("thread %s released ! num=%d"%(name,num))
        mylock.release(); #release the lock
        
def test():
    _thread.start_new_thread(add_num,('A',))
    _thread.start_new_thread(add_num,('B',))
    _thread.start_new_thread(add_num,('C',))
    time.sleep(1)

if __name__=="__main__":
    test()