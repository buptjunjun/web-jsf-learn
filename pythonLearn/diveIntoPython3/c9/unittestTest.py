#
#    python 的测试用例
#

import unittest
from case import hello
class testCase(unittest.TestCase):
    def testhello(self):
        h = hello()
        self.assertTrue(h.func1(1)==True, '')
        
        
if __name__ == '__main__':   
     unittest.main()