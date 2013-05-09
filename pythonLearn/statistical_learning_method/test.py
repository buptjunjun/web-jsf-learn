class kdtreeNode:
      parent = None;
      lchild = None;
      rchild = None;
      data = None;
      
      def __init__(self):
          self.lchild = kdtreeNode
          self.rchild = kdtreeNode
          
def func(node):
    tmp = kdtreeNode()
    tmp.data=[111]
    node = tmp

root = kdtreeNode()
func(root.lchild)
print(root)

x = [2]
l= [1]
l.append(x)
x.append(22)
print(x)
print(l)