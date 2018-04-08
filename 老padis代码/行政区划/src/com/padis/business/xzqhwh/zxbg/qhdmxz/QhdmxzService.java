package com.padis.business.xzqhwh.zxbg.qhdmxz;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;


/**
 * <p>
 * Description:  ��������������Service��
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @Date 2009-08-14
 * @author ���
 * @version 1.0
 */
public class QhdmxzService extends XtwhBaseService {
	
	/**
	 * XtwhBaseService��manager����
	 */
	QhdmxzManager mgr;

	/**
	 * �޲ι��췽��
	 *
	 */
	public QhdmxzService() {
		mgr = new QhdmxzManager();
	}
	
	/**
	 * <p>�������ƣ�queryXzqhfb()����ѯ��������������</p>
	 * <p>����˵������ѯ��������������ķ�����������������XT_XZQHFB���м�����������¼��</p>
	 * @param ��
	 * @Date 2009-08-14
	 * @author ���
	 * @return ��
	 * @exception ��
	 */
	public void queryXzqhfb() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String sqbxh = StringEx.sNull(xmldo.getItemValue("SQBXH"));
			long[] args = new long[]{-1,0};
			String wdList = mgr.queryXzqhfb(xmldo,args);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("SQBXH", sqbxh);
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("RZLIST", wdList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ѯ�������Ϣ�ɹ�!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ�������Ϣʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>�������ƣ�getFilePath()����ȡ�ļ�·��</p>
	 * <p>����˵������ȡ�ļ�·���ķ�����</p>
	 * @Date 2009-08-14
	 * @author ���
	 * @return ��
	 * @exception ��
	 */
	public void getFilePath() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String dcxh = StringEx.sNull(xmldo.getItemValue("DCXH"));
			String filePath = mgr.getFilePath(dcxh);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("PATH", filePath);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ѯ·���ɹ�!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ·��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	/**
	 * <p>�������ƣ�exportFile()������ȫ����������������������</p>
	 * <p>����˵��������ȫ����������������������ķ�����</p>
	 * @param ��
	 * @Date 2009-08-14
	 * @author ���
	 * @return ��
	 * @exception ��
	 */
	public void exportFile() {
		try {
			mgr.zipXzqhFile();
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "�����ɹ�!","");
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ�������Ϣʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
}
