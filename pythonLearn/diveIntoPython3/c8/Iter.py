import itertools
def tt(name):
    count = 0
    for s in name:
        count+=ord(s)
    
    if count > 160:
        return True    
    return False
    
    
if __name__=='__main__':
    names = ['Dora', 'Ethan', 'Wesley', 'John', 'Anne', 'Mike', 'Chris', 'Sarah', 'Alex', 'Lizzie']
    groups = itertools.groupby(names, tt)
    for name_length, name_iter in groups:
        print(name_length)
        for name in name_iter:
            print(name)