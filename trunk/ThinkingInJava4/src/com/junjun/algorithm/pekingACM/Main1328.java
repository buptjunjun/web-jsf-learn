package com.junjun.algorithm.pekingACM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
*   
*   ֪ʶ��:̰���㷨,����
*   �㷨: ����һ������ �״ﷶΧΪd, ÿһ������Ϊһ����p(i,j)
*         1�����յ���ĺ�����i�Ե����������,��С����.
*         2�����ĳһ�����������(�뺣���߾������d) �����޽�
*         3������ÿһ������p,��dΪ�뾶�뺣���ߵ�(x��)�Ľ��㣨������,����һ��)����������x���ϵ�����Ϊfield[a,b]
*         4������ߵ�һ���㿪ʼ,���н�����fields,����p1,p2,p3��filed�н�����p4��field��ǰ��ĵ�û�н�������ôp1,p2,p3�Ϳ�����һ���״︲��
*            ����������,���ҵ�һ�������ļ��Ͼ���Ҫһ���״�,��󷵻���Ҫ���״���Ŀ��
*    http://poj.org/problem?id=1328    
*         
*/

/**
 * ���� ������
 */
class Point implements Comparable
{
	public double x = 0;
	public double y = 0;
	public Point(double x,double y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString()
	{
		return x+" "+y;
	}
	
	@Override
	public boolean equals(Object arg0)
	{
		// TODO Auto-generated method stub
		Point p = (Point)arg0;
		if(this.x == p.x && this.y == p.y)
			return true;
		return false;
	}
	
	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return (int)(this.x+this.y);
	}
	@Override
	public int compareTo(Object arg0)
	{
		Point p = (Point)arg0;
		if(this.x == p.x)
			return 0;
		else if (this.x > p.x)
			return 1;
		else return -1;
		
	}
}


public class Main1328
{
	/**
	 * ������Ҫ���״���Ŀ
	 * ����������Ҫ���ٸ��״�ĸ���
	 * @param ilands ��ʾ ���������
	 * @param d d��ʾ�״�����뾶
	 */
	public static int countRadar(Point [] islands,Double d)
	{
		//�Ե��찴��x��������
		Arrays.sort(islands);
		int count = 0;
	
		 //һ�����찴��dΪ�뾶�� x���ཻ������
		double [] baseField = null;
		
		for(int i = 0;i < islands.length;i++)
		{
			Point p = islands[i];
			
			// ���p.y���ڰ뾶���island ,�޽�
			if(p.y >d)
				return -1;
			
			 //һ�����찴��dΪ�뾶�� x���ཻ������
			double[] field = caculate(p,d);
			
			// ���baseFieldΪ�ձ�ʾһ���µļ��Ͽ�ʼ
			if(baseField == null)
			{
				 baseField =field ;
				 count++;
			}
			else
			{
				// ������������ �Ľ���
				baseField = intersect(baseField,field);
				
				// ��� baseField[0] == Double.MIN_VALUE ��ʾû�н���
				if (baseField[0] == Double.MIN_VALUE)
				{
					//���  ��ʾû�н���,����һ�����ϵ��µĿ�ʼ ����count��һ,field��Ϊ��ǰ������.
					count++;
					baseField = field;
				}
			}		
		}
	
		return count;
	}
	
	/**
	 * һ�����찴��dΪ�뾶�� x���ཻ������
	 * @param p ��������
	 * @param d �״�뾶
	 * @return �������� ���û�н��� ���� {Double.MIN_VALUE,Double.MIN_VALUE};
	 */
	public static double[] caculate(Point p,double d)
	{   
		double [] ret = {Double.MIN_VALUE,Double.MIN_VALUE};
		if(p.y > d)
			return ret;
		
		double width = Math.sqrt(d*d-p.y*p.y);
		ret[0] = p.x-width;
		ret[1] = p.x+width;

		return ret;
	}
	
	/**
	 * ��������������ཻ������
	 * @param range1
	 * @param range2
	 * @return ���ؽ�����������뽻����{Double.MIN_VALUE,Double.MIN_VALUE};
	 */
	public static double[] intersect(double [] range1,double [] range2)
	{
		double [] ret = {Double.MIN_VALUE,Double.MIN_VALUE};
		if(range1[0]>range2[1] || range1[1]<range2[0])
			return ret;
		
		double left = 0;
		if(range1[0]<=range2[0])
			left = range2[0];
		else left = range1[0];
		
		double right = 0;
		if(range1[1]>=range2[1])
			right = range2[1];
		else right = range1[1];
		
		ret[0] = left;
		ret[1] = right;
		
		return ret;
	}
	
	
	/**
	 * ɾ���ظ��ĵ�������
	 * @param p
	 * @return
	 */
	public static Point [] removeDuplicate(Point [] p)
	{
		Set<Point> set = new HashSet<Point>();
		set.addAll(Arrays.asList(p));
		return set.toArray(new Point[set.size()]);
	}
	
	
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		
		//���ÿһ��case���������
		List<Point []> datas = new ArrayList<Point []>();
		
		//���ÿһ��case���״�İ뾶
		List<Double> ds = new ArrayList<Double>();
		
		//ȡ������
		String line = scanner.nextLine();
		while(!line.startsWith("0 0"))
		{
			
			String [] nums = line.split(" ");			
			double n = Double.valueOf(nums[0]);
			double d = Double.valueOf(nums[1]);
			
			List<Point> listPoint = new ArrayList<Point>();
			for(int i = 0; i<n; i++)
			{
				line = scanner.nextLine();
				nums = line.split(" ");			
				double x = Double.valueOf(nums[0]);
				double y = Double.valueOf(nums[1]);
				
				//if(y<0) continue;
				Point p = new Point(x,y);
				listPoint.add(p);
			}
			
			Point [] list = listPoint.toArray(new Point[listPoint.size()]);
			
			// ȥ���ظ��ĵ�
			list = removeDuplicate(list);
			datas.add(list);
			ds.add(d);
			
			line = scanner.nextLine();		
			line = scanner.nextLine();
		}
		scanner.close();
		
		//��������
		int caseCount = 0;
		for(Point []list:datas)
		{	
			//��ǰcase�״�İ뾶
			double d = ds.get(caseCount);
			//������Ҫ���ٸ��״�
			int count = countRadar(list,d);			
			System.out.println("Case "+(caseCount+1)+": "+count);
			caseCount++;
		}
		
	}
}