package com.padis.business.xzqhwh.zxbg.qhdmcx;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;

/**
 * <p>
 * Title: QhdmcxService.java
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
 * @date 2009-9-16
 * @author pengld
 * @version 1.0 �޸���ʷ�� �޸��� �޸�ʱ��(yyyy/mm/dd) �޸����� �汾��
 */
public class QhdmcxService extends XtwhBaseService {

	/**
	 * XtwhBaseService��manager����
	 */
	QhdmcxManager mgr;

	/**
	 * �޲ι��췽��
	 * 
	 */
	public QhdmcxService() {
		mgr = new QhdmcxManager();
	}

	/**
	 * <p>
	 * �������ƣ� initXzqh()����ʼ����������ҳ��
	 * </p>
	 * <p>
	 * ����˵������ʼ����������ҳ��ķ�����
	 * </p>
	 * 
	 * @param ��
	 * @Date 2009-07-31
	 * @author ���
	 * @return ��
	 */
	public void initXzqh() {
		try {	
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			String isFb = mgr.isFb();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("XZQH_DM", "000000000000000");
			xsBuf.append("DWLSGX_DM", "10");
			xsBuf.append("FLAG", isFb);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "��ת����ʼ��ҳ��ɹ���", xsBuf
							.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ʼ��ҳ��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS, 2006, e.toString(),
					"");
		}
	}

	/**
	 * <p>
	 * �������ƣ�
	 * </p>
	 * <p>
	 * ����������
	 * </p>
	 * 
	 * @author pengld
	 * @since 2009-9-17
	 */

	public void queryXzqh() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			String xzqhdm = xdo.getItemValue("XZQHDM");
			String tableName = xdo.getItemValue("DB");
			XMLDataObject xzqhXdo = mgr.queryXzqh(xzqhdm,tableName);// ��ѯ���
			XmlStringBuffer xsBuf = new XmlStringBuffer();// ���ظ�ǰ̨������
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			if (xzqhXdo != null) {
				xsBuf.append("XZQH_DM", xzqhXdo.getItemValue("XZQH_DM"));
				xsBuf.append("XZQH_MC", xzqhXdo.getItemValue("XZQH_MC"));
				xsBuf.append("XZQH_QC", xzqhXdo.getItemValue("XZQH_QC"));
				xsBuf.append("SJ_XZQH_MC", xzqhXdo.getItemValue("SJ_XZQH_DM"));
				xsBuf.append("DWLSGX", xzqhXdo.getItemValue("DWLSGX_DM"));
				xsBuf.append("XZQHLX", xzqhXdo.getItemValue("XZQHLX_DM"));
				if(StringEx.sNull(xzqhXdo.getItemValue("XNJD_BZ")).equals("Y"))
				{
					xsBuf.append("XNJD_BZ","��" );
				}else
				{
					xsBuf.append("XNJD_BZ","��" );
				}
				
			}

			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯʡ����������������������ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS, 2009, "", "");
		}
	}

}
