class Fib:
    '''生成斐波拉契数列的迭代器'''
    def __init__(self,max=10):
        self.max = max
    def __iter__(self):
        self.a = 0
        self.b = 1
        return self
    def __next__(self):
        fib = self.a
        if fib > self.max:
            raise StopIteration
        self.a , self.b = self.b, self.a+self.b
        return fib
    
    
if __name__=='__main__':
    fib = Fib()
    print(fib)
    print(fib.__class__)
    print(fib.__doc__)
    print(fib.max)   
    for n in fib:
        print(n)