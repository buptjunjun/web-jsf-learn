def insertsort (data):
    '''
            插入排序 从小到大
    '''
    if data == None:
        return
    
    for j in range(1,len(data)):
        elem = data[j]
        #插入位置
        position = j
        for i in range(j-1,-1,-1):
            if elem < data[i]:
                data[i+1] = data[i]
                position = i
            else: 
                break;

        data[position] = elem;

def insertsortBS (data):
    '''
                插入排序 使用 二分搜索查找插入位置
    '''
    if data == None:
        return
    
    for j in range(1,len(data)):
        elem = data[j]
        if elem >= data[j-1]:
            continue;
        # 插入位置
        position = -1    
        # 二分搜索 确定插入位置
        low = 0
        high = j-1;
        while low < high:
            middle = int((low+high)/2)
            if elem < data[middle]:
                high = middle-1;
            elif elem > data[middle]:
                low = middle+1;
            else:
                position = middle;
                break; 
        # 插入位置
        if position < 0:
            position = low;
        #将插入位置及其以后的元素往后移动
        for i in range(j-1,position-1,-1):
            data[i+1] = data[i] 
        data[position] = elem;

if __name__=="__main__":
    data = [2,3,4,1,-1,0,0,3]
    insertsort(data)
    print(data)
    data = [2,3,4,1,-1,0,0,3]
    insertsortBS(data)
    print(data)
    