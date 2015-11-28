package com.feinno.commons;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符处理工具
 * 
 * @author SHOUSHEN LUAN
 * 
 */
@SuppressWarnings("unchecked")
public class StringUtils {
  /***************************************************************************
   * javaScript代码中文转换，将其中的中转换为\u3434这样的格式
   * 
   * @param szIn
   * @return
   */
  public static String JS(String szIn) {
    if (null == szIn)
      return null;
    char[] arrChar = szIn.trim().toCharArray();
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < arrChar.length; i++) {
      if ((0x7F < arrChar[i] || 0xa > arrChar[i]) && 9 != arrChar[i])
        buf.append("\\u").append(Integer.toHexString((int) arrChar[i]));
      else
        buf.append(arrChar[i]);
    }
    return buf.toString();
  }

  /**
   * 截取传入字符从制定长度到制定长度字符
   * 
   * @param str
   * @param beginIndex
   * @param endIndex
   * @return
   */
  public static String substring(String str, int beginIndex, int endIndex) {
    if (isNotEmpty(str)) {
      if (str.length() > endIndex && str.length() > beginIndex) {
        return str.substring(beginIndex, endIndex);
      }
    }
    return str;
  }

  /**
   * 截取字符指定长度
   * 
   * @param str 字符
   * @param length 长度（从前面截取）
   * @return
   */
  public static String subStringByLength(String str, int length) {
    if (isNotEmpty(str) && str.length() > length) {
      return str.substring(0, length);
    }
    return str;
  }

  /**
   * 截取字符指定长度
   * 
   * @param str 字符
   * @param length 长度（从前面截取）
   * @param lastAppendStr 如果需要截取的话讲在之后追加字符
   * @return
   */
  public static String subStringByLength(String str, int length, String lastAppendStr) {
    if (isNotEmpty(str) && str.length() > length) {
      return str.substring(0, length) + lastAppendStr;
    }
    return str;
  }

  /**
   * 截取制定长度字符
   * 
   * @param str
   * @param length
   * @return
   */
  public static String intercept(String str, int length) {
    if (isNotEmpty(str)) {
      if (str.length() > length) {
        return str.substring(0, length);
      }
    }
    return str;
  }

  /**
   * 是否是邮箱地址
   * 
   * @param email
   * @return
   */
  public static boolean isEmail(String email) {
    String reg = "[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
    return Pattern.matches(reg, email);
  }

  /**
   * 剔除回车|换行|回车并换行|字表符
   * 
   * @param str
   * @return
   */
  public static String clearInvalid(String str) {
    if (!isNotEmpty(str)) {
      return str.replaceAll("\r\n|\r|\n|\t", "");
    } else {
      return str;
    }
  }

  public static boolean isEmpty(String str) {
    return !isNotEmpty(str);
  }

  public static boolean isNotEmpty(String str) {
    if (str != null && str.trim().length() > 0) {
      return true;
    }
    return false;
  }

  public static boolean isNotEmpty(String... str) {
    for (int i = 0; i < str.length; i++) {
      if (isNotEmpty(str[i])) {
        continue;
      } else {
        return false;
      }
    }
    return true;
  }

  /**
   * 剔除字符中所有空格
   * 
   * @param str
   * @return
   */
  public final static String clearSapce(String str) {
    Pattern p = Pattern.compile("\\s*");
    Matcher m = p.matcher(str);
    return m.replaceAll("");
  }

  /**
   * 判断是否是中文
   * 
   * @param chat
   * @return
   */
  public final static boolean isChinese(char chat) {
    return (Character.toString(chat).matches("[\\u4E00-\\u9FA5]+"));
  }

  /**
   * 判断是否为数字
   * 
   * @param chat
   * @return
   */
  public final static boolean isNum(char chat) {
    return chat > 47 && chat < 58;
  }

  /**
   * 清除无效字符:清除除：字母,数字,中文,_以外的字符
   * 
   * @param fileName
   * @return
   */
  public static String clearValidChat(String fileName) {
    String reg = "[^a-z|A-Z|0-9|_|\\u4E00-\\u9FA5|\\.]+";
    Pattern pattern = Pattern.compile(reg);
    Matcher matcher = pattern.matcher(fileName);
    if (matcher.find())
      return matcher.replaceAll("");
    return fileName;
  }

  /**
   * 验证移动手机号码
   * 
   * @param mobileNo
   * @return
   */
  public static boolean checkYDMobile(String mobileNo) {
    Pattern p = Pattern.compile("^1(3[4-9]|47|5[012789]|8[782])\\d{8}$");
    Matcher matcher = p.matcher(mobileNo);
    return matcher.matches();
  }

  /**
   * 将字符串拆分，将首字母和下划线的下一个字母改为大写字母并且去掉下划线
   * 
   * @param str
   * @return
   */
  public static String processStr_(String str) {
    if (str.indexOf("_") != -1) {
      String fieldStrs[] = str.split("_");
      if (fieldStrs != null) {
        for (int i = 0; i < fieldStrs.length; i++) {
          if (fieldStrs[i] != null && fieldStrs[i].length() > 0) {
            if (is_a_z(fieldStrs[i].charAt(0))) {
              fieldStrs[i] = ((char) (fieldStrs[i].charAt(0) - 32)) + fieldStrs[i].substring(1);
            }
          }
        }
      }
      str = "";
      for (int i = 0; i < fieldStrs.length; i++) {
        str += fieldStrs[i];
      }
    } else {
      if (is_a_z(str.charAt(0))) {
        return ((char) (str.charAt(0) - 32)) + str.substring(1);
      }
    }
    return str;
  }

  public static char a_zToA_Z(char chat) {
    if (is_a_z(chat)) {
      return ((char) (chat - 32));
    }
    return chat;
  }

  public static char A_ZToa_z(char chat) {
    if (isA_Z(chat)) {
      return ((char) (chat + 32));
    }
    return chat;
  }

  public static boolean is_a_z(char chat) {
    if (chat >= 97 && chat <= 122) {
      return true;
    }
    return false;
  }

  public static boolean isA_Z(char chat) {
    if (chat >= 65 && chat <= 90) {
      return true;
    }
    return false;
  }

  public static boolean isNumber(char c) {
    if (c >= 48 && c <= 57)
      return true;
    return false;
  }

  public static String getFieldName(String column) {
    String str = StringUtils.processStr_(column);
    if (StringUtils.is_a_z(str.charAt(0))) {
      return str;
    } else if (StringUtils.isA_Z(str.charAt(0))) {
      return ((char) (str.charAt(0) + 32)) + str.substring(1);
    }
    return str;
  }

  private final static Pattern pattern = Pattern.compile("<([^>]*)>");

  public static String filterHtml(String str) {
    Matcher matcher = pattern.matcher(str);
    StringBuffer sb = new StringBuffer();
    boolean result1 = matcher.find();
    while (result1) {
      matcher.appendReplacement(sb, "");
      result1 = matcher.find();
    }
    matcher.appendTail(sb);
    return sb.toString();
  }

  public static String subStringAndFilterHTMLTag(String str, int length) {
    if (isNotEmpty(str)) {
      str = filterHtml(str);
      if (str.length() > length) {
        str = str.substring(0, length) + "...";
      }
    }
    return str;
  }

  public static String format(Date date, String pattern) {
    return new SimpleDateFormat(pattern).format(date);
  }

  /**
   * 过滤掉字母数字以外所有的字符
   * 
   * @param str
   * @return
   */
  public static String filterNotLetterOrNumber(String str) {
    if (isNotEmpty(str)) {
      return str.replaceAll("[^a-zA-Z0-9]", "");
    }
    return "";
  }
}
