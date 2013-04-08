x=[(3,3),(4,3),(1,1)]
y=[1,1,-1]


def perception(x,y):
    '''
            感知机模型
           使用 随机梯度下降
    '''
    #w,b为初始值
    w = [0,0]
    b = 0    
    
    step = 1     #学习率 
    flag = False;
    train_set = zip(x,y)
    train_set = list(train_set)
    while True:
        flag = False
        for xi ,yi in train_set:
            if (xi[0]*w[0]+xi[1]*w[1]+b)*yi <= 0:
                w[0] += step*yi*xi[0]
                w[1] += step*yi*xi[1]
                b += step*yi
                flag = True
        #当没有误分点的时候，推出循环
        if flag == False: 
            break;

    print("the model is:")
    if b<=0:
        print('f(x) = %d x1 + %d x2  %d' %(w[0],w[1],b))
    else:
         print('f(x) = %d x1 + %d x2 + %d' %(w[0],w[1],b))

if __name__=='__main__':
    perception(x,y)   

