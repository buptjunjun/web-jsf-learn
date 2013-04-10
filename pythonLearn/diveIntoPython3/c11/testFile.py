#a_file=open('rules.txt',encoding="gbk")
#print(a_file.name)
#print(a_file.encoding)
#print(a_file.mode)
#print(a_file.read(7))
#print(a_file.seek(0))
#print(a_file.read(7))
#
#a_file.close()

with open('rules.txt',encoding="gbk") as a_file:
    line_num = 0
    for line in a_file:
        line_num+=1
        print('{} {}'.format(line_num,line))
        
        
with open('rules.txt',mode='a',encoding="gbk") as a_file:
    a_file.write('test write ok')

with open('rules.txt',mode='r',encoding="gbk") as a_file:
    print(a_file.read())
    
with open('ml.png',mode='rb') as a_file:
   # print(a_file.encoding)
   pass
    
import io

# 把String当做一个文件来处理，
a_string = "test string"
a_file = io.StringIO(a_string)
print(a_file.read())
    
    
#把zip当做一个流来读写
import gzip
with gzip.open('out.log.gz', mode='wb') as z_file:
    z_file.write('A nine mile walk is no joke, especially in the rain.'.encode('gbk'))
    
#重定向
import sys
with open('rules.txt',mode='a',encoding="gbk") as a_file:
    sys.stdout = a_file
    print('\nto stdout')

