import re

def build_match_and_apply_functions(pattern,search,replace):
    def matches_rule(word):
        return re.search(pattern,word)
    def apply_rule(word):
        return re.sub(search,replace,word)
    return (matches_rule,apply_rule)

class Fib:
    '''生成斐波拉契数列的迭代器'''
    rules_filename="rules.txt"
    
    def __init__(self,max=10):
        self.pattern_file = open(self.rules_filename,encoding="utf-8")
        self.cache=[]
   
    def __iter__(self):
        self.cache_index = 0
        return self
   
    def __next__(self):
       self.cache_index+=1
       if len(self.cache) >= self.cache_index:
           return self.cache[self.cache_index-1]
       if self.pattern_file.closed:
           raise StopIteration
       
       line = self.pattern_file.readline()
       if not line:
           self.pattern_file.close();
           raise StopIteration
       
       pattern,search,replace=line.split(None,3)
       funcs = build_match_and_apply_functions(pattern,search,replace)
       self.cache.append(funcs)
       return funcs
   
    
    
if __name__=='__main__':
    fib = Fib()
    print(fib)
    print(fib.__class__)
    print(fib.__doc__)
    print(fib.max)   
    for n in fib:
        print(n)