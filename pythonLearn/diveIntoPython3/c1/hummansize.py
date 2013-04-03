SUFFIXES = {1000:['KB',"MB",'GB','TB'],
            1024:['KiB',"MiB",'GiB','TiB']}
def approximate_size(size,a_kilobyte_is_1024_bytes=True):
    ''' Convert a file size to human-readable form
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
