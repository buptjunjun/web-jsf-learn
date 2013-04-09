

def findMedians(nums,begin,end):
    '''
                 寻找中位数
     params:
     nums -- list of numbers
     return median
    '''
    position =  partition(nums,begin,end);
    middle = (begin+end)/2
    if position == middle:
        return position
    elif position > middle:
        return findMedians(nums,begin,middle)
    else :
        return  findMedians(nums,middle,end)
    
    
    
def partition (nums,begin,end):
    '''
            类似于快速排序,选择一个pivot,将比pivot小的调整到 pivot的左边，将比pivot大的调整到pivot的右边，返回pivot的位置
    params
    nums -- list of number
    begin,end -- 需要partition的字串的下标
    return pivot的位置
    '''
    
    pivot = nums[begin]
    
    low = begin
    high = end
    while low < high:
        while low < high and nums[high] >= pivot:
            high = high - 1
        nums[low] = nums[high]
        while low < high and nums[low] <= pivot:
            low = low + 1
        nums[high] = nums[low]
    nums[low] = pivot
    
    return low
    
        
        
if __name__ == '__main__':
    nums = [3,2,1,5,7]
    print(findMedians(nums,0,len(nums)-1))
    print(nums)
