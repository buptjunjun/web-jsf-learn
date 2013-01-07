
package com.buptjunjun.stuff;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ö÷ÌâÐÍÍøÒ³ÕýÎÄ³éÈ¡£¬±È½ÏÊÊºÏÓÚÐÂÎÅºÍBlogµÄÕýÎÄ³éÈ¡.<br />
 * ²ÉÓÃ¡¶»ùÓÚÐÐ¿é·Ö²¼º¯ÊýµÄÍ¨ÓÃÍøÒ³ÕýÎÄ³éÈ¡¡·µÄËã·¨£¬¸ÃËã·¨Ê±¼ä¸´ÔÓ¶ÈÎªÏßÐÔ£¬
 * ²»ÐèÒª½¨Á¢DOMÊ÷£¬ÇÒ²»ÒÀÀµHTML±êÇ©¡£<br />
 * Ê×ÏÈ½«ÍøÒ³ÕýÎÄ³éÈ¡ÎÊÌâ×ª»¯ÎªÇóÒ³ÃæµÄÐÐ¿é·Ö²¼º¯Êý£¬ÕâÖÖ·½·¨²»ÓÃ½¨Á¢DomÊ÷£¬
 * ²»±»²¡Ì¬HTMLËùÀÛ£¨ÊÂÊµÉÏÓëHTML±êÇ©ÍêÈ«ÎÞ¹Ø£©¡£
 * Í¨¹ýÔÚÏßÐÔÊ±¼äÄÚ½¨Á¢µÄÐÐ¿é·Ö²¼º¯ÊýÍ¼£¬Ö±½Ó×¼È·¶¨Î»ÍøÒ³ÕýÎÄ¡£
 * 
 * @author Fandy Wang(lfwang@ir.hit.edu.cn)
 * @date 2010.08.12
 */
public class TextExtractor {

  /** ÐÐ¿é´óÐ¡. */
  private static Integer _block = 3;

  /** The Constant _titlePattern. */
  private final static String _titlePattern = "<title>(.*?)</title>";
  
  /** The Constant _titleRegexPattern. */
  private final static Pattern _titleRegexPattern = Pattern.compile(_titlePattern, Pattern.CANON_EQ | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

  /** The _title. */
  private String _title = "";
  
  /** The _text. */
  private String _text = "";

  /**
   * Sets the block.
   * 
   * @param block the new block
   */
  @SuppressWarnings("unused")
  private static void setBlock(Integer block) {
    _block = block;
  }

  /**
   * Extract url.
   * 
   * @param url the url
   */
  public void extractURL(String url) {
    url = url.trim();
    if( isLegalURL(url) ) {
      if( isContentURL(url) ) {
        String htmlText = DownloadURL.downURL(url, "IE8.0");
        extractHTML(htmlText);
      } else {
        _text = "*ÍÆ²âÄúÌá¹©µÄÍøÒ³Îª·ÇÖ÷ÌâÐÍÍøÒ³£¬Ä¿Ç°ÔÝ²»´¦Àí£¡:-)";
      }
    }
    else {
      _title = "*·Ç·¨URL:-)";
    }
  }
  

  /**
   * Extract url.
   * 
   * @param url the url
   * @param encoding the encoding
   */
  public void extractURL(String url, String encoding) {
    url = url.trim();
    if( isLegalURL(url) ) {
      if( isContentURL(url) ) {
        String htmlText = DownloadURL.downURL(url, encoding, "IE8.0");
        extractHTML(htmlText);
      } else {
        _text = "*ÍÆ²âÄúÌá¹©µÄÍøÒ³Îª·ÇÖ÷ÌâÐÍÍøÒ³£¬Ä¿Ç°ÔÝ²»´¦Àí£¡:-)";
      }
    }
    else {
      _title = "*·Ç·¨URL:-)";
    }
  }

  /**
   * Checks if is content url.
   * 
   * @param url the url
   * 
   * @return true, if is content url
   */
  private boolean isContentURL(String url) {
    int count = 0;
    for( int i=0; i < url.length()-1 && count < 3; i++ ) {
      if(url.charAt(i) == '/' )
        count++;
    }
    
    return count > 2;
  }

  /**
   * Checks if is legal url.
   * 
   * @param url the url
   * 
   * @return true, if is legal url
   */
  private boolean isLegalURL(String url) {
    if(url == null || url.length() == 0) {
      return false;
    }
    
    String regEx = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"
      + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
      + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
      + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"
      + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"
      + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
      + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"
      + "[a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";
    Pattern p = Pattern.compile(regEx);
    Matcher matcher = p.matcher(url);
    System.out.println(matcher.matches());
    return matcher.matches();
  }

  /**
   * Extract html.
   * 
   * @param htmlText the html text
   */
  public void extractHTML(String htmlText) {
   // System.out.println(htmlText);
    extractTitle(htmlText);
    htmlText = preProcess(htmlText);
    if( !isContentPage(htmlText) ) {
      _text = "*ÍÆ²âÄúÌá¹©µÄÍøÒ³Îª·ÇÖ÷ÌâÐÍÍøÒ³£¬Ä¿Ç°ÔÝ²»´¦Àí£¡:-)";
      return ;
    }
    //System.out.println(htmlText);

    List<String> lines = Arrays.asList(htmlText.split("\n"));
    List<Integer> indexDistribution = lineBlockDistribute(lines);

    List<String> textList = new ArrayList<String>();
    List<Integer> textBeginList = new ArrayList<Integer>();
    List<Integer> textEndList = new ArrayList<Integer>();

    for (int i = 0; i < indexDistribution.size(); i++) {
      if (indexDistribution.get(i) > 0 ) {
        StringBuilder tmp = new StringBuilder();
        textBeginList.add(i);
        while (i < indexDistribution.size() && indexDistribution.get(i) > 0) {
          tmp.append(lines.get(i)).append("\n");
          i++;
        }
        textEndList.add(i);
        textList.add(tmp.toString());
      }
    }
    
    // Èç¹ûÁ½¿éÖ»²îÁ½¸ö¿ÕÐÐ£¬²¢ÇÒÁ½¿é°üº¬ÎÄ×Ö¾ù½Ï¶à£¬Ôò½øÐÐ¿éºÏ²¢£¬ÒÔÃÖ²¹µ¥´¿³éÈ¡×î´ó¿éµÄÈ±µã
    for (int i=1; i < textList.size(); i++ ) {
      if( textBeginList.get(i) == textEndList.get(i-1)+1 
          && textEndList.get(i) > textBeginList.get(i)+_block 
          && textList.get(i).replaceAll("\\s+", "").length() > 40 ) {
        if( textEndList.get(i-1) == textBeginList.get(i-1)+_block 
            && textList.get(i-1).replaceAll("\\s+", "").length() < 40 ) {
          continue;
        }
        textList.set(i-1, textList.get(i-1) + textList.get(i));
        textEndList.set(i-1, textEndList.get(i));
        
        textList.remove(i);
        textBeginList.remove(i);
        textEndList.remove(i);
        --i;
      }
    }
    
    String result = "";
    for (String text : textList) {
      //System.out.println("text:" + text + "\n" + text.replaceAll("\\s+", "").length());
      if (text.replaceAll("\\s+", "").length() > result.replaceAll("\\s+", "")
          .length())
        result = text;
    }
    
    // ×î³¤¿é³¤¶ÈÐ¡ÓÚ100£¬¹éÎª·ÇÖ÷ÌâÐÍÍøÒ³
    if( result.replaceAll("\\s+", "").length() < 100 )
      _text = "*ÍÆ²âÄúÌá¹©µÄÍøÒ³Îª·ÇÖ÷ÌâÐÍÍøÒ³£¬Ä¿Ç°ÔÝ²»´¦Àí£¡:-)";
    else _text = result;
  }
  
  /**
   * Checks if is content page.
   * 
   * @param htmlText the html text
   * 
   * @return true, if is content page
   */
  private boolean isContentPage(String htmlText) {
    int count = 0;
    for( int i=0; i < htmlText.length() && count < 5; i++ ) {
      if(htmlText.charAt(i) == '£' || htmlText.charAt(i) ==  '£')
        count++;
    }
    
    return count >= 5;
  }

  /**
   * Extract title.
   * 
   * @param htmlText the html text
   */
  private void extractTitle(String htmlText) {
    Matcher m1 = _titleRegexPattern.matcher(htmlText);

    if (m1.find()) {
      _title = replaceSpecialChar(m1.group(1));
    }
    _title = _title.replaceAll("\n+", "");
  }

  /**
   * Gets the title.
   * 
   * @return the title
   */
  public String getTitle() {
    return _title;
  }
  
  /**
   * Gets the text.
   * 
   * @return the text
   */
  public String getText() {
    return _text;
  }
  
  /**
   * Line block distribute.
   * 
   * @param lines the lines
   * 
   * @return the list< integer>
   */
  private List<Integer> lineBlockDistribute(List<String> lines) {
    List<Integer> indexDistribution = new ArrayList<Integer>();

    for (int i = 0; i < lines.size(); i++) {
      indexDistribution.add(lines.get(i).replaceAll("\\s+", "").length());
    }
    // É¾³ýÉÏÏÂ´æÔÚÁ½¸ö¿ÕÐÐµÄÎÄ×ÖÐÐ
    for (int i = 0; i+4 < lines.size(); i++) {
      if( indexDistribution.get(i) == 0 
          && indexDistribution.get(i+1) == 0 
          && indexDistribution.get(i+2) > 0 && indexDistribution.get(i+2) < 40 
          && indexDistribution.get(i+3) == 0
          && indexDistribution.get(i+4) == 0 ) {
        //System.out.println("line:" + lines.get(i+2));
        lines.set(i+2, "");
        indexDistribution.set(i+2, 0);
        i += 3;
      }
    }
  
    for (int i = 0; i < lines.size()-_block; i++) {
      int wordsNum = indexDistribution.get(i);
      for (int j = i+1; j < i + _block && j < lines.size(); j++) {
        wordsNum += indexDistribution.get(j);
      }
      indexDistribution.set(i, wordsNum);
    }

    return indexDistribution;
  }

  /**
   * Pre processing.
   * 
   * @param htmlText the html text
   * 
   * @return the string
   */
  private String preProcess(String htmlText) {
    // DTD
    htmlText = htmlText.replaceAll("(?is)<!DOCTYPE.*?>", "");
    // html comment
    htmlText = htmlText.replaceAll("(?is)<!--.*?-->", "");
    // js
    htmlText = htmlText.replaceAll("(?is)<script.*?>.*?</script>", "");
    // css
    htmlText = htmlText.replaceAll("(?is)<style.*?>.*?</style>", "");
    // html
    htmlText = htmlText.replaceAll("(?is)<.*?>", "");

    return replaceSpecialChar(htmlText);
  }

  /**
   * Replace special char.
   * 
   * @param content the content
   * 
   * @return the string
   */
  private String replaceSpecialChar(String content) {
    String text = content.replaceAll("&quot;", "\"");
    text = text.replaceAll("&ldquo;", "¡°");
    text = text.replaceAll("&rdquo;", "¡±");
    text = text.replaceAll("&middot;", "¡¤");
    text = text.replaceAll("&#8231;", "¡¤");
    text = text.replaceAll("&#8212;", "¡ª¡ª");
    text = text.replaceAll("&#28635;", "÷");
    text = text.replaceAll("&hellip;", "¡­");
    text = text.replaceAll("&#23301;", "Ã");
    text = text.replaceAll("&#27043;", "l");
    text = text.replaceAll("&#8226;", "¡¤");
    text = text.replaceAll("&#40;", "(");
    text = text.replaceAll("&#41;", ")");
    text = text.replaceAll("&#183;", "¡¤");
    text = text.replaceAll("&amp;", "&");
    text = text.replaceAll("&bull;", "¡¤");
    text = text.replaceAll("&lt;", "<");
    text = text.replaceAll("&#60;", "<");
    text = text.replaceAll("&gt;", ">");
    text = text.replaceAll("&#62;", ">");
    text = text.replaceAll("&nbsp;", " ");
    text = text.replaceAll("&#160;", " ");
    text = text.replaceAll("&tilde;", "~");
    text = text.replaceAll("&mdash;", "¡ª");
    text = text.replaceAll("&copy;", "@");
    text = text.replaceAll("&#169;", "@");
    text = text.replaceAll("¡á", "");
    text = text.replaceAll("\r\n|\r", "\n");

    return text;
  }

  /**
   * The main method.
   * 
   * @param args the args
   */
  public static void main(String[] args) {
    // http://baike.baidu.com/view/25215.htm
    // http://hi.baidu.com/handylee/blog/item/6523c4fc35a235fffc037fc5.html
    // http://www.techweb.com.cn/news/2010-08-11/659082.shtml
     // http://hi.baidu.com/fandywang_jlu/blog/item/dfe2f0134907142edd5401d7.html 
      //http://www.ifanr.com/15876
  
    TextExtractor te = new TextExtractor();
    te.extractURL("http://www.techweb.com.cn/news/2010-08-11/659082.shtml");
        System.out.println(te.getTitle());
        System.out.println(te.getTitle());
        System.out.println(te.getText());
  }

}