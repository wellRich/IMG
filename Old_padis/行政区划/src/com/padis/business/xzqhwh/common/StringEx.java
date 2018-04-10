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
	 * �ַ����Ƿ����ָ����ʽ
	 * 
	 * @param str
	 * @param regx:������ʽ
	 * @return
	 */
	public static boolean match(String str, String regx) {
		Pattern p = Pattern.compile(regx);
		if (p.matcher(str).matches())
			return true;
		return false;
	}

	/**
	 * ���ڸ�ʽ����:YYYY-MM-DD
	 * 
	 * @param date:�����ַ���
	 * @return
	 */
	public static boolean isDate(String date) {
		return match(date, StringEx.YEAR_MONTH_DAY);
	}

	/**
	 * �����ַ����Ƿ�Ϊ�����������ַ���
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDigitStr(String str) {
		return match(str, StringEx.POSITIVE_INTERGER);
	}

	/**
	 * ���ַ���@str�еľɴ�@oldStr�滻Ϊ�´�@newStr
	 * @param str  Դ�ַ���
	 * @param oldStr  ���Ӵ�
	 * @param newStr  ���Ӵ�
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
	 * ���ַ������ָ���ָ�
	 * @param str  �ַ���
	 * @param splitter �ָ��
	 * @return �ָ����ַ�������,
	 *         NULL - ��
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
	 * ȡ��ƥ�䴮���ַ����еĳ��ִ���
	 * @param str �ַ���
	 * @param s  ƥ�䴮
	 * @return ����
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
	 * ����obj==null����
	 */
	public static String sNull(Object obj) {
		//��obj==nullʱ�����ʹ��toString()���������
		return obj == null ? "" : obj.toString();
	}

	public static String sNull(Object obj, boolean isconvert) {
		//��obj==nullʱ�����ʹ��toString()���������
		if (isconvert) {
			return obj == null ? "" : obj.toString();
		} else
			return obj == null ? null : obj.toString();
	}

	 public static final boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	 
	/**
	 * ���ַ���ISO-8859-1���ַ���ת����Ϊ�ַ���GBK
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
	 * ��GBK�ַ������ַ���ת����Ϊ�ַ���ISO-8859-1
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
	 * ��source�ַ������ַ���ת����Ϊ�ַ���target
	 * @param arg
	 * @return �ַ���
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
		//<>��XML��Mark,���ַ��д���<>ʱ,�ᵼ�´���
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
