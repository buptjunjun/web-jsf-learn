package com.junjun.algorithm.charpter2;

import com.junjun.algorithm.Sort;

/**
 * 第二章 习题2-4
 * 数组s[0...N]中如果i<j ,s[i]>j就表示有一个逆序对
 * 计算任意数组中的逆序对数
 * @author junjun
 *
 */
public class InversePair extends Sort
{
	//算逆序对的个数
	private static int count = 0;
	
	/**
	 * 使用归并排序求逆序对
	 * @param s
	 */
	public int countInversePair(int [] s)
	{
		count=0;
		this.mergeSort(s,0, s.length-1);
		return count;
	}

	/**
	 * 对数组 s进行归并排序,p<<q<r
	 * 其中p到s[p,q] 和s[q+1,r]都是排好序的，将他们合并成一个排好序的数组并替换s[p,r],在这个过程中找到逆序对的个数
	 * @param s 数组
	 * @param p 
	 * @param q
	 * @param r
	 */
	private void merge(int [] s,int p,int q,int r)
	{
		int n1 = q-p+1; //左边一段s[p,q]的长度
		int n2 = r-q;   //右边一段s[q+1,r]的长度
		int [] L = new int [n1]; 
		int [] R = new int [n2];
		
		for(int i = 0; i < n1;i++)
			L[i] = s[p+i];
		for(int i = 0; i < n2; i++)
			R[i] = s[q+1+i];
	
		//打印当前左边与右边的数组 L和R
		System.out.println("\n----------");
		System.out.print("L:");
		this.print(L);
		System.out.print("\nR:");
		this.print(R);
		System.out.println("");
		
		// 开始归并
		int i =0; 
		int j = 0;
		int k = p;
		while(i < n1 && j < n2)
		{
			// 选择较小的作为插入对象
			if(L[i] <= R[j])
			{
				s[k++] =  L[i++];
			}
			else //如果L[i]>R[j]可能存在逆序对
			{
				/*右边每插入一个数就有n1-i个逆序对存在,因为是右边剩下的每一个元素L[i...n1]都比R[j]更小。
				* 例如L=2,3 R=0,2,7
				* 第一轮：插入R[0]=0, 是L=[2,3] 有2个逆序对 (2,0),(3,0)
				* 第二轮：插入L[0]=2,  不做处理 ,0个逆序对
				* 第三轮：插入R[1]=2,  L=[3],有1个逆序对 (3,2)
				* 第四轮：插入L[1]=3,  不做处理, 0个逆序对
				* 第五轮：插入R[2]=7,  L=[] 0个逆序对 
				* 所以有3个逆序对 
				*/
				count+= (n1-i);		
				//打印逆序对
				for(int l = i;l <n1;l++)
					System.out.print("("+L[l]+","+R[j]+")  ");
				
				s[k++] =  R[j++];
				
			}
		}
		
		//将剩下的插入
		while(i<n1) s[k++] = L[i++];			
		while(j<n2) {s[k++] = R[j++];}	
	}
	
	/**
	 * 递归进行merge排序
	 * @param s
	 * @param p
	 * @param r
	 */
	private void mergeSort(int [] s, int p ,int r)
	{
		if(p>=r) return;
		
		int q = (p+r)/2;
		mergeSort(s,p,q);
		mergeSort(s,q+1,r);
		merge(s,p,q,r);		
	}
	
	
	public static void main(String[] args)
	{
		// 逆序对有5个 (2,1),(3,1),(8,1),(6,1),(8,6)
		int [] s = {2,3,8,6,1};
		
		InversePair ip = new InversePair();
		System.out.println("\n逆序对个数："+ip.countInversePair(s));;
		System.out.println("排序过后的数组：");
		ip.print(s);
	}

}
