package com.junjun.algorithm.charpter5;
import java.util.*;
import java.util.Map.Entry;

/**
 * 二分查找类
 * @author junjun
 *
 */
public class Random 
{
	private java.util.Random  r = new java.util.Random();
	/**
	 * 得到一个等概率  0,1
	 * @return
	 */
	public int random01()
	{
		return r.nextInt(2);
	}
	
	/**
	 * 仅仅调用 random01() 得到random 0 n 
	 * 算法：
	 * 1、求的n可以使用多少个二进制位表示，比如 8可以使用3位表示，10可以使用4位表示
	 * 2、对每一位进行 调用一次random01,得到一个二进制数列，例如 1101,0100
	 * 3、将二进制数列转换为十进制数 ret，比如1101 得到的是ret=11,0100得到的是ret=2
	 * 4、如果得道的ret大于n 重新回到第二步 知道ret属于[0,n]为止
	 * 5、返回 ret
	 * @param n
	 * @return 等概率返回[0,n]
	 */
	public int random0n(int n)
	{
	    if (n < 1)
	    {
	    	System.out.println("a should not larger than b");
	    	return Integer.MAX_VALUE;
	    }
	    
	    // 计算n可以由多少个二进制位表示
	    int countBit = 0;
	    int tmpn = n;
	    while( tmpn > 0)
	    {
	    	tmpn /= 2;
	    	countBit++;
	    }
	    
	    // total 是要生成的随机数
	   int total = 0; 
	   while(true)
	   {
		    //计算 2^0+2^1+...+2^countBit
	    	for( int i = 0; i < countBit; i++)
	    	{    
	    		//如果random01为1相当于滴i个比特为1，需要加上2^i
	    		if(random01() == 1)
		    		total += Math.pow(2,i);
	    	}    
	    	// 如果total不在[0,n]范围内 继续循环直到满足属于[0,n]为止
	    	if(total > n)
	    		total = 0;
	    	else 
	    		break;
	   }
	   return total;
	    
	}
	
	/**
	 * 仅仅调用 random01() 得到random 0 n 
	 * randomab 问题可以最终转化为random0n问题  [a,b] = [0+a,(b-a)+a]
	 * 所以只需要计算 random(0,b-a)得到一个随机数再加上a即可
	 * @param a 下限
	 * @param b 上限
	 * @return 返回满足[a,b]的随机数
	 */
	public int randomab(int a, int b)
	{
		return random0n(b-a)+a;
	}
	
	
	public static void main(String[] args)
	{
	}
}
