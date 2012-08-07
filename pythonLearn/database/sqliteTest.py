import MySQLdb

#连接
cxn = MySQLdb.Connect(host = '127.0.0.1', user = 'root', passwd = '')
#游标
cur = cxn.cursor()
try:  
    cur.execute("DROP DATABASE searchengine")
except Exception as e:  
    print(e)
finally:    pass
#创建数据库
cur.execute("CREATE DATABASE searchengine")
cur.execute("USE txw1958")
#创建表
cur.execute("CREATE TABLE users (id INT, name VARCHAR(8))")
#插入
cur.execute("INSERT INTO users VALUES(1, 'www'),(2, 'cnblogs'),(3, 'com'),(4, 'txw1958')")
#查询
cur.execute("SELECT * FROM users")
for row in cur.fetchall():  
  print('%s\t%s' %row)
#关闭
cur.close()
cxn.commit()
cxn.close()