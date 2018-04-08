package com.padis.business.xzqhwh.zxbg.qhdmtjcx;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.XmlStringBuffer;

/**
 * <p>
 * Title: QhdmtjcxService.java
 * </p>
 * <p>
 * Description:<��Ĺ�������>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: DigitalChina Co.Ltd
 * </p>
 * 
 * @date 2010-07-22
 * @author lijl
 * @version 1.0 
 */
public class QhdmtjcxService extends XtwhBaseService {

	/**
	 * XtwhBaseService��manager����
	 */
	QhdmtjcxManager mgr;

	/**
	 * �޲ι��췽��
	 * 
	 */
	public QhdmtjcxService() {
		mgr = new QhdmtjcxManager();
	}
	
	/**
	 * <p>�������ƣ�queryXzqhxx</p>
	 * <p>��������������������ѯ����������Ϣ</p>
	 * @return ��
	 * @throws ��
	 * @author lijl
	 * @since 2010-07-22
	 */
	public void queryXzqhxx() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			long[] args = new long[]{-1,0};
			String xzqhList = mgr.queryXzqh(xdo,args);// ��ѯ���
			XmlStringBuffer xsBuf = new XmlStringBuffer();// ���ظ�ǰ̨������
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("XZQHLIST", xzqhList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			System.out.println("xsBuf--1340-----"+xsBuf);
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯʡ����������������������ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS, 2009, "", "");
		}
	}

}
