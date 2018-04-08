/**
 * 
 */
package com.padis.business.xzqhwh.common;

import com.padis.common.BaseService;
import com.padis.common.constants.CommonConstants;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.XmlStringBuffer;

/**
 * <p>Title: xzqhService.java </p>
 * <p>Description:<��Ĺ�������> </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-9-24
 * @author pengld
 * @version 1.0
 * �޸���ʷ�� 
 * �޸���
 * �޸�ʱ��(yyyy/mm/dd)
 * �޸�����
 * �汾��
 */
public class XzqhService extends BaseService{
	
	/**
	 * XzqhbgsqbService���Manager
	 */

	XzqhManager mgr;


	/**
	 * ���캯��
	 */
	public XzqhService() {
		mgr = new XzqhManager();

	}
	
	/**
	 * 
	 * <p>
	 * �������ƣ�getXjXzqh����
	 * </p>
	 * <p>
	 * ����������ȡ��ʡ������������������
	 * </p>
	 * 
	 * @author lijl
	 * @since 2009-07-31
	 */
	public void getXjXzqh() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			String sjxzqh = xdo.getItemValue("SJXZQH");
			String fhjd = xdo.getItemValue("FHJD");
			String db = xdo.getItemValue("DB");
			mgr.setDb(db);
			String resultXml = mgr.getXjXzqhdm(sjxzqh, fhjd);// ��ѯ���
			XmlStringBuffer xsBuf = new XmlStringBuffer();// ���ظ�ǰ̨������
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendHead(fhjd);
			xsBuf.append(resultXml);
			xsBuf.appendTail(fhjd);
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
