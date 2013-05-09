import chardet
tt=open('c:\\wsFolderInfo.xml','rb') 
ff=tt.readline()#这里试着换成read(5)也可以，但是换成readlines()后报错 
enc=chardet.detect(ff) 
print(enc['encoding']) 
tt.close() 