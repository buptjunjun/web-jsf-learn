my_data=[['slashdot','USA','yes',18,'None'],
        ['google','France','yes',23,'Premium'],
        ['digg','USA','yes',24,'Basic'],
        ['kiwitobes','France','yes',23,'Basic'],
        ['google','UK','no',21,'Premium'],
        ['(direct)','New Zealand','no',12,'None'],
        ['(direct)','UK','no',21,'Basic'],
        ['google','USA','no',24,'Premium'],
        ['slashdot','France','yes',19,'None'],
        ['digg','USA','no',18,'None'],
        ['google','UK','no',18,'None'],
        ['kiwitobes','UK','no',19,'None'],
        ['digg','New Zealand','yes',12,'Basic'],
        ['slashdot','UK','no',21,'None'],
        ['google','UK','yes',18,'Basic'],
        ['kiwitobes','France','yes',19,'Basic']]

class decisionnode:
    def __init__(self,col=-1,value=None,results=None,tb=None,fb=None):
        '''
        col is the index of the criteria
        value is the value that the column must  match to get a true result
        tb = true+b  fb = false+b
        result stores a dictionary of results for this branch  
        '''
        self.col = col
        self.value=value
        self.results = results
        self.tb = tb
        self.fb = fb
        
def diviest(rows, column,value):
    split_function=None
    if isinstance(value,int) or isinstance(value,float):
        split_function = lambda row:row[column] >= value 
    else:
        split_function = lambda row:row[column] == value
        
    #devide the rows into two sets and return them
    set1 = [row for row in rows if split_function(row)]
    set2 = [row for row in rows if not split_function(row)]
    
    return (set1,set2)

    
(s1,s2) = diviest(my_data,2,'yes')
print(s1)
print(s2)
            
               