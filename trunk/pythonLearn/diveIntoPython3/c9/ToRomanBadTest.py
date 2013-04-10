#
#    python 的测试用例
#

import unittest
import roman1
class ToRomanBadInput(unittest.TestCase):
    def test_too_large(self):
        self.assertRaises(roman1.OutOfRangeError,roman1.to_roman, 4000)
    def test_zero(self):            
        self.assertRaises(roman1.OutOfRangeError, roman1.to_roman, 0)
   
    def test_not_integer(self):            
        self.assertRaises(roman1.NotIntegerError, roman1.to_roman, 'aaa')
        
        
if __name__ == '__main__':   
     unittest.main()