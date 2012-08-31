import re
#file1 = []
#for line in open("C:\\Users\\andyWebsense\\Desktop\\name1.txt"):
#    file1.append(line)
#
#
#file2 = []
#for line in open("C:\\Users\\andyWebsense\\Desktop\\name2.txt"):
#    file2.append(line)
#
#str1=""
#str2=""
#file3 =  open("C:\\Users\\andyWebsense\\Desktop\\name3.txt","w+")
#for i in range(len(file1)):
#    name1 = file1[i].strip("\n").lower()
#    name2 = file2[i].strip("\n").lower()
#    
#    str1+=name1+"\n"
#    str2+=name2+"\n"
#    name = name1 +"="+ name2
#    print(name,end="\n")
#    file3.write(name)
#print(str1)
#print(str2)
#
import xlwt3

wbk = xlwt3.Workbook()
sheet = wbk.add_sheet('sheet 1') 
#Now that the sheet is created, it’s very easy to write data to it.

# indexing is zero based, row then column sheet.write(0,1,'test text') 
#When you’re done, save the workbook (you don’t have to close it like you do with a file object)

wbk.save('test.xls') 

file =  open("C:\\Users\\andyWebsense\\Desktop\\name3.txt")
spliter = re.compile("=")
str1 = ""
str2 = ""
i = 0
for line in file:
    name1 = line.strip("\n")
    splitTxt = spliter.split(name1)
    sheet.write(i,2,splitTxt[0]) 
    sheet.write(i,3,splitTxt[1]) 
    i+=1
wbk.save('test.xls') 

