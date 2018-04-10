package com.padis.business.xzqhwh.common;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title: </P>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @auther dongzga
 * @version 1.0
 * @since 2008-6-30
 */
public class StringEx {

	public final static String YEAR_MONTH_DAY = "^(([0-9]{2})|((19|20)[0-9]{2}))[-]"
			+ "((1[012])|([1-9])|(0[1-9]))[-/]((0[1-9])|"
			+ "([1-9])|(1[0-9])|(2[0-9])|(3[1]))$";

	public final static String POSITIVE_INTERGER = "^[0-9]+$";

	public StringEx() {
	}

	/**
	 * 字符串是否符合指定格式
	 * 
	 * @param str
	 * @param regx:正则表达式
	 * @return
	 */
	public static boolean match(String str, String regx) {
		Pattern p = Pattern.compile(regx);
		if (p.matcher(str).matches())
			return true;
		return false;
	}

	/**
	 * 日期格式较验:YYYY-MM-DD
	 * 
	 * @param date:日期字符串
	 * @return
	 */
	public static boolean isDate(String date) {
		return match(date, StringEx.YEAR_MONTH_DAY);
	}

	/**
	 * 较验字符串是否为正整数数字字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDigitStr(String str) {
		return match(str, StringEx.POSITIVE_INTERGER);
	}

	/**
	 * 将字符串@str中的旧串@oldStr替换为新串@newStr
	 * @param str  源字符串
	 * @param oldStr  旧子串
	 * @param newStr  新子串
	 * @return
	 */
	public static String replace(String str, String oldStr, String newStr) {
		int idx, len;

		String pre, post;
		len = oldStr.length();
		post = str;
		str = "";
		while ((idx = post.indexOf(oldStr)) != -1) {
			pre = post.substring(0, idx);
			post = post.substring(idx + len);
			str = str + pre + newStr;
		}
		return str + post;
	}

	/**
	 * 将字符串按分割符分割
	 * @param str  字符串
	 * @param splitter 分割符
	 * @return 分割后的字符串数组,
	 *         NULL - 空
	 */
	public static String[] splitString(String str, String splitter) {
		int p = str.indexOf(splitter);
		int prev = 0;
		if (p < 0) {
			String[] ss = new String[1];
			ss[0] = str;
			return ss;
		}

		ArrayList found = new ArrayList();
		String s = null;

		while (p >= 0) {
			if (prev == p) {
				s = "";
			} else {
				s = str.substring(prev, p);
			}
			found.add(s);

			prev = p + 1;
			p = str.indexOf(splitter, prev);
		}
		if (prev < str.length())
			found.add(str.substring(prev, str.length()));

		return (String[]) found.toArray(new String[1]);
	}

	/**
	 * 取得匹配串在字符串中的出现次数
	 * @param str 字符串
	 * @param s  匹配串
	 * @return 个数
	 */
	public int occurence(String str, String s) {
		int p = str.indexOf(s);
		int cnt = 0;
		if (p < 0)
			return cnt;
		int len = s.length();
		cnt++;
		while (p < str.length()) {
			p = p + len;
			p = str.indexOf(s, p);
			if (p < 0)
				return cnt;
			cnt++;
		}
		return cnt++;
	}

	/**
	 * 处理obj==null函数
	 */
	public static String sNull(Object obj) {
		//当obj==null时，如果使用toString()，将会出错！
		return obj == null ? "" : obj.toString();
	}

	public static String sNull(Object obj, boolean isconvert) {
		//当obj==null时，如果使用toString()，将会出错！
		if (isconvert) {
			return obj == null ? "" : obj.toString();
		} else
			return obj == null ? null : obj.toString();
	}

	 public static final boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	 
	/**
	 * 将字符集ISO-8859-1的字符串转换成为字符集GBK
	 * 
	 * @param arg
	 * @return
	 */
	public static String convGbk(String arg) throws Exception {
		String s = null;
		byte[] b;

		b = arg.getBytes("ISO-8859-1");
		s = new String(b, "GBK");
		return s;
	}

	/**
	 * 将GBK字符集的字符串转换成为字符集ISO-8859-1
	 * @param arg
	 * @return
	 */
	public static String reverseGbk(String arg) throws Exception {
		String s = null;
		byte[] b;

		b = arg.getBytes("GBK");
		s = new String(b, "ISO-8859-1");
		return s;
	}

	/**
	 * 将source字符集的字符串转换成为字符集target
	 * @param arg
	 * @return 字符集
	 */
	public static String convertCharSet(String arg, String source, String target)
			throws Exception {
		String s = null;
		byte[] b;

		b = arg.getBytes(source);
		s = new String(b, target);
		return s;
	}

	public static String replMark(String s) {
		//<>是XML的Mark,当字符中存在<>时,会导致错误
		return s.replace('<', '[').replace('>', ']');
	}


	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	public static String numberToStr(long _lValue, int _length, char _chrFill){
        String sValue = String.valueOf(_lValue);
        return expandStr(sValue, _length, _chrFill, true);
    }
	
	public static String expandStr(String _string, int _length, char _chrFill, boolean _bFillOnLeft){
        int nLen = _string.length();
        if(_length <= nLen)
            return _string;
        String sRet = _string;
        for(int i = 0; i < _length - nLen; i++)
            sRet = _bFillOnLeft ? _chrFill + sRet : sRet + _chrFill;

        return sRet;
    }
}
