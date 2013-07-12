package org.easyGoingCrawler.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.easyGoingCrawler.docWriter.Movie;
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
	
	static public String generateQueryOriginal(Movie movie)
	{
		String name = movie.getName();
		
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
			name = name.replaceAll(":", " ");
		}
		
		
		return name;
	}
	

	static Pattern p1 = Pattern.compile("[([a-zA-Z]+\\s*)]+[0-9]*");
	static public String generateQueryOnlyChinese(Movie movie)
	{
		
		String name = generateQueryOriginal(movie);
		
		if(name!=null)
		{
			
			if(isChinese(name))
			{
				Matcher m = p1.matcher(name);
				String  groupStr = "";
				while(m.find())
				{
					 groupStr = m.group();
					
				}
				name = name.replace(groupStr, "");
			}
		}    
		return name;
	}
	
	static public String generateMovieDate(Movie movie)
	{
		Date date = movie.getDate();
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
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		 String[] strArr = new String[] { "www.micmiu.com", "!@#$%^&*()_+{}[]|\"'?/:;<>,.", "！￥……（）——：；“”‘’《》，。？、", "不要啊", "やめて", "韩佳人", "???" };

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

	        }
	        
	        
	        String [] testQuerys = {"007：大破天幕杀机 Skyfall ","钢铁侠3 Iron Man 3","dsaf 乌云背后的幸福线12 : Silver Linings Playbook 12 ","乌云背后的幸福线 122","aaaaasdddd","やめて12"};
	        for(String test: testQuerys)
	        {
	        	Movie movie = new Movie();
	        	movie.setName(test);
	        	System.out.println(test+" ==> "+generateQueryOnlyChinese(movie));
	        }

	}

}
