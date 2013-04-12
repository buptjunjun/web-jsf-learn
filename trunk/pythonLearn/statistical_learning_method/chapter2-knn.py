import random

def findMedians(nums,begin,end,mid,dim):
    '''
     params:
     nums -- list of numbers
     return median
    '''
    position =  partition(nums,begin,end,dim);
    middle = (int)((begin+end)/2)
    if position == mid:
        return position
    elif position > mid:
        return findMedians(nums,begin,position-1,mid,dim)
    else :
        return  findMedians(nums,position+1,end,mid,dim)
    
    
    
def partition (nums,begin,end,dim):
    '''
    params
    nums -- list of number
    begin,end -- partition
    return pivot
    '''
    
    tmpPos = (int)(random.uniform(begin,end))
    
    tmp = nums[tmpPos]
    nums[tmpPos] = nums[begin]
    nums[begin] = tmp;
    
    save = nums[begin]
    pivot = nums[begin][dim]
    low = begin
    high = end
    while low < high:
        while low < high and nums[high][dim] >= pivot:
            high = high - 1
        nums[low] = nums[high]
        while low < high and nums[low][dim] <= pivot:
            low = low + 1
        nums[high] = nums[low]
    nums[low] = save
    
    return low

class kdtreeNode:
      parent = None;
      lchild = None;
      rchild = None;
      data = None;
      
      def __init__(self):
          self.lchild = kdtreeNode
          self.rchild = kdtreeNode
          self.lchild.parent = self
          self.rchild.parent = self
      
class kdtree:
    data = []
    root = kdtreeNode()
    dim = 0
    def __init__(self,originalData,dimention):
       self.root = kdtreeNode()
       self.data = [e for e in originalData]
       self.dim = dimention;
    
    def createKDTree(self):
        '''
         create a kd tree
         param:
             data -- a list of tuple ,like [(1,2,2,1),(2,33,1,22),...]
             partent  -- parent node
             dim  -- current dimention
        '''
        if self.data == None or len(self.data)==0:
            return
        print(self.data)
        self.createKDTree_private(self.data,0,0,len(self.data)-1,self.root)
        
       
    def createKDTree_private(self,data,currentDim,start,end,pointer):
        if start < 0 :
            return
        if end >= len(data):
            return
        if start > end:
            return
        
        if start == end:       
             print(data)
             print(data[start])
             pointer.data = data[start];
             return
        middle = (start+end)/2
        midPos = findMedians(data,start,end,(int)(middle),currentDim)
        print(data)
        print(data[midPos])
        pointer.data = data[midPos];
        
        currentDim = (currentDim+1)%self.dim
        low = 0
        high = 0
        low = midPos - 1
        high = midPos + 1
        pointer.lchild = kdtreeNode()
        pointer.rchild = kdtreeNode()
        self.createKDTree_private(data,currentDim,start,low,pointer.lchild)
        self.createKDTree_private(data,currentDim,high,end,pointer.rchild) 
        
    def printtree(self):
        self.printtree1(self.root)
    
    def printtree1(self,node):
        if node == None or node.data==None: 
            return
        print(node.data)
        self.printtree1(node.lchild)
        self.printtree1(node.rchild)
        
        
        
        
if __name__ == '__main__':
    nums = [(1,21,2),(1,28,7),(91,21,11),(1,2,0),(13,23,8),(12,21,3)]
#    mid = findMedians(nums,0,len(nums)-1,(int)((len(nums)-1)/2),1)
#    print(nums)
#    print(mid)
#    print(nums[mid])
    
    tree = kdtree(nums,3)
    tree.createKDTree()
    tree.printtree()