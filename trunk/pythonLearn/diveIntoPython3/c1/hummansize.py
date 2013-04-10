SUFFIXES = {1000:['KB',"MB",'GB','TB'],
            1024:['KiB',"MiB",'GiB','TiB']}
def approximate_size(size,a_kilobyte_is_1024_bytes=True):
    ''' 这是 文档字符串(简称 docstring)
     Convert a file size to human-readable form
    keyword arguments:
    size -- file size in bytes
    a_kilobyte_is_1024_bytes -- if True(default) ,use multples of 1024
                                if False(default) ,use multples of 1000      
    '''
    if size < 0:
        raise ValueError('number must be non-negative')
    muliple = 1024 if a_kilobyte_is_1024_bytes else 1000

    for suffix in SUFFIXES[muliple]:
        size /= muliple
        if size < muliple:
            return '{0:.1f} {1}'.format(size,suffix) 
    raise ValueError('number too large')


if __name__=='__main__':
    print(approximate_size(1000000000000, False))
    print(approximate_size(1000000000000))
    
    #使用命名参数
    print(approximate_size(size=1000000000000,a_kilobyte_is_1024_bytes=False))
    
    #交换顺序没有关系
    print(approximate_size(a_kilobyte_is_1024_bytes=False,size=1000000000000))
   
    try:
        approximate_size(-1)
    except:
        print('error')
        
    print(__name__)
    print(approximate_size.__doc__)
        
     # 如果某个参数是命名参数 那么右边的所有参数都必须是命名参数
     #print(approximate_size(a_kilobyte_is_1024_bytes=False,1000000000000))
    