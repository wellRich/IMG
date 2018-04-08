package com.padis.business.xzqhwh.zxbg.bgqkhzcx.aqhjbhbglxcx;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;


import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;


/**
 * 
 * <p>Title: AqhjbhbglxcxService.java </p>
 * <p>Description:����ȫ�����������������Service�� </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2010-05-07
 * @author lijl
 * @version 1.0
 */
public class AqhjbhbglxcxService extends XtwhBaseService {
	
	/**
	 * 
	 */
	AqhjbhbglxcxManager mgr;

	/**
	 * �޲ι��췽��
	 *
	 */
	public AqhjbhbglxcxService() {
		mgr = new AqhjbhbglxcxManager();
	}

	/**
	 * <p>�������ƣ�queryMxb</p>
	 * <p>��������������ȫ������������</p>
	 * @author lijl
	 * @since 2010-05-07
	 */
	public void queryMxb() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String hzmc = StringEx.sNull(xmldo.getItemValue("HZMC"));
			String sqdList = mgr.queryMxb(hzmc);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("ITEMS", sqdList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ʼ��ҳ��ɹ�!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ʼ��ҳ��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}

	/**
	 * <p>�������ƣ�exportQhbgqkhzb</p>
	 * <p>��������������ȫ���������������ܱ�</p>
	 * @author lijl
	 * @since 2010-05-07
	 */
	public void exportQhbgqkhzb() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String hzmc = StringEx.sNull(xmldo.getItemValue("HZMC"));
			String sqdList = mgr.queryMxb(hzmc);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("MXBLIST", sqdList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ʼ��ҳ��ɹ�!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ʼ��ҳ��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>�������ƣ�groupByHzmc</p>
	 * <p>������������ȡ�������Ʒ�����Ϣ</p>
	 * @author lijl
	 * @since 2010-05-07
	 */
	public void getHzmc() {
		try {
			String hzmcList = mgr.groupByHzmc();
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("HZMCLIST", hzmcList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ȡ�������Ʒ�����Ϣ�ɹ�!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ȡ�������Ʒ�����Ϣʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
}
