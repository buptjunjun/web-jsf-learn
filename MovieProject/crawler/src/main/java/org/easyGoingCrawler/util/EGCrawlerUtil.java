package org.easyGoingCrawler.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;

public class EGCrawlerUtil
{	
	/**
	 * http://movie.douban.com/subject/5954626 to 5954626
	 * @param url
	 * @return
	 */
	static public String getMovieIDFromUrl(String url)
	{
		String ret = null;
		if(StringUtil.isBlank(url))
			return ret;
		
		ret = url.replace("http://movie.douban.com/subject/", "");
		
		return ret;
	}
	
	/**
	 * 获得最原始的的电影名字
	 * @param movie
	 * @return
	 */
	static public String generateQueryOriginal(String name)
	{
	
		if(name!=null)
		{
			name = name.trim();
			/*String [] chineseName = name.split(" ");
			if(chineseName != null && chineseName.length > 1)
				name = chineseName[0];
			*/
			name = name.replaceAll("&", " ");
			name = name.replaceAll("#", " ");
			name = name.replaceAll("\\?", " ");
			name = name.replaceAll("\\:", " ");
			name = name.replaceAll("：", " ");
			name = name.replaceAll("·", " ");
			
			
		}
		
		
		return name;
	}
	

	/**
	 * 获得最电影中文名字 去除韩文 日文 英文
	 * @param movie
	 * @return
	 */
	static Pattern p1 = Pattern.compile("[([a-zA-Z]+\\s*)]+[0-9]*");
	static public String generateQueryOnlyChinese(String name)
	{
		
		name = generateQueryOriginal(name);
		
		if(name == null || name.length()==0)
			return null;
		
		String firstStr = name.substring(0, 1);
		if(isChinese(firstStr))
		{
			name = replaceGreeceNum(name);
			int firstNonChinese=name.length();
			for(int i = 0;i < name.length();i++)
			{
				String subStr = name.substring(i, i+1);
				//System.out.println();
				if(!isChinese(subStr) && !isNum(subStr) && !StringUtils.isBlank(subStr)&& !isSpecialChar(subStr))
				{
					firstNonChinese = i;
					break;
				}
			}
			
			name = name.substring(0,firstNonChinese);
			
		}
		else
		{
			int j = 0;
			while(j<name.length() )
			{
				String tmp = name.substring(j, j+1);
				if(isChinese(tmp))
					break;
				j++;
			}
			
			int firstNonChinese=j;
			for(int i = j;i < name.length();i++)
			{
				String subStr = name.substring(i, i+1);
				//System.out.println();
				if(!isChinese(subStr) && !isNum(subStr) && !StringUtils.isBlank(subStr)&&!isSpecialChar(subStr))
				{
					firstNonChinese = i;
					break;
				}
			}
			name = name.substring(0,firstNonChinese);
		}
		
		return name;
	}
	
	/**
	 * 获取电影上映的时间
	 * @param movie
	 * @return
	 */
	static public String generateMovieDate(Date date)
	{
		
		String dateStr = date!=null ? 1900+date.getYear()+"":"";		
		return dateStr;
	}
	
	// 根据Unicode编码完美的判断中文汉字和符号
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 完整的判断中文汉字和符号
	public static boolean isChinese(String strName) {
		if(strName==null)
			return false;
		
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str.trim()).find();
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByName(String str) {
		if (str == null) {
			return false;
		}
		// 大小写不同：\\p 表示包含，\\P 表示不包含
		// \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
		String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
		Pattern pattern = Pattern.compile(reg);
		return pattern.matcher(str.trim()).find();
	}
	
	// 判断字符串中是否含有韩文或者日文
	private static Pattern pattern = Pattern.compile("[\\u0800-\\u4e00\\u1100-\\u11ff\\uac00-\\ud7af\\u3130–\\u318F\\u3200–\\u32FF\\uA960–\\uA97F\\uD7B0–\\uD7FF\\uFF00–\\uFFEF]+");
	private static Pattern pattern2 = Pattern.compile("[\\u0800-\\u4e00\\u1100-\\u11ff\\uac00-\\ud7af\\u3130–\\u318F\\u3200–\\u32FF\\uA960–\\uA97F\\uD7B0–\\uD7FF\\uFF00–\\uFFEF]");
	public static boolean isJapaneseAndKoreaByREG(String str)
	{		
		return pattern.matcher(str.trim()).find();
	}
	/**
	 * 字符串中第一个韩文或日文出现的位置
	 * @param str
	 * @return
	 */
	public static int firstElemOfJapaneseAndKoreaByREG(String str)
	{
		Matcher m = pattern2.matcher(str);
		if(m.find())
		{
			String s = m.group();
			int location = str.indexOf(s);
			return location;
		}
		else
		{
			return -1;
		}
	}
	
	static Pattern pNum = Pattern.compile("[0-9]+");
	public static boolean isNum(String num)
	{
		return pNum.matcher(num).matches();
	}
	
	static Pattern pSpecialChar = Pattern.compile("[\\(\\)]+");
	public static boolean isSpecialChar(String c)
	{
		return pSpecialChar.matcher(c).matches();
	}
	
	/**
	 * 替换罗马数字
	 * @param name
	 * @return
	 */
	public static String replaceGreeceNum(String name)
	{
		name = name.replace("II", "2");
		name = name.replace("III", "3");
		name = name.replace("IV", "4");
		return name;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
/*		 String[] strArr = new String[] { "www.micmiu.com", "!@#$%^&*()_+{}[]|\"'?/:;<>,.", "！￥……（）——：；“”‘’《》，。？、", "不要啊", "やめて", "韩佳人", "???" };
	        for (String str : strArr) {
	            System.out.println("===========> 测试字符串：" + str);
	            System.out.println("正则判断结果：" + isChineseByREG(str) + " -- " + isChineseByName(str));
	            System.out.println("Unicode判断结果 ：" + isChinese(str));
	            System.out.println("详细判断列表：");
	            char[] ch = str.toCharArray();
	            for (int i = 0; i < ch.length; i++) {
	                char c = ch[i];
	                System.out.println(c + " --> " + (isChinese(c) ? "是" : "否"));
	            }
	        }*/
	        
	        
	        String [] testQuerys = {"ぱにぽにだっしゅ！","007：大破天幕杀机 Skyfall ","钢铁侠3 Iron Man3","dsaf 乌云背后的幸福线12 : Silver Linings Playbook 12 ","乌云背后的幸福线 122","aaaaasdddd","32 你好やめて12"};
	        for(String test: testQuerys)
	        {       
	        	System.out.println(test+" ==> "+generateQueryOnlyChinese(test));
	        }

	    /*    String test = "不可思议的教室 OVA 아시아 주 断而敢行 鬼神避之 ぱにぽにだっしゅ！OVA 「断じて行えば鬼神もこれを避く」";
	        System.out.println(test + "ischinese:"+isChinese(test));
	        for(int i = 0;i < test.length()-1;i++)
	        {
	        	String s = test.substring(i,i+1);
	        	System.out.println(s+" " +"  is Jpan or Krea:"+isJapaneseAndKoreaByREG(s));
	        }*/
	        
	        
	        
	}

}
