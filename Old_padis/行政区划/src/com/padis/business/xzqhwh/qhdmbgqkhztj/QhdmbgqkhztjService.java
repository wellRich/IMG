package com.padis.business.xzqhwh.qhdmbgqkhztj;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;


/**
 * <p>
 * Description:  ����ͳ��������������Service��
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @Date 2010-06-11
 * @author lijl
 * @version 1.0
 */
public class QhdmbgqkhztjService extends XtwhBaseService {
	
	/**
	 * QhdmbgqkhztjService��manager����
	 */
	QhdmbgqkhztjManager mgr;

	/**
	 * �޲ι��췽��
	 *
	 */
	public QhdmbgqkhztjService() {
		mgr = new QhdmbgqkhztjManager();
	}
	
	/**
	 * <p>�������ƣ�initHztj</p>
	 * <p>������������ȡ�������Ʒ�����Ϣ</p>
	 * @author lijl
	 * @since 2010-05-07
	 */
	public void initHztj() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String hzsjq = StringEx.sNull(xmldo.getItemValue("HZSJQ"));
			String hzsjz = StringEx.sNull(xmldo.getItemValue("HZSJZ"));
			String hzmc = StringEx.sNull(xmldo.getItemValue("HZMC"));
			String hzmcList = mgr.groupByHzmc();
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("HZSJQ", hzsjq);
			xsBuf.append("HZSJZ", hzsjz);
			xsBuf.append("HZMC", hzmc);
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
	
	/**
	 * <p>�������ƣ�sumQhbgqk()����ѯ��������������</p>
	 * <p>����˵������ѯ��������������ķ�����������������XT_XZQHFB���м�����������¼��</p>
	 * @param ��
	 * @Date 2010-06-11
	 * @author lijl
	 * @return ��
	 * @exception ��
	 */
	public void sumQhbgqk() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String hzsjq = StringEx.sNull(xmldo.getItemValue("HZSJQ"));
			String hzsjz = StringEx.sNull(xmldo.getItemValue("HZSJZ"));
			String hztj = StringEx.sNull(xmldo.getItemValue("HZTJ"));
			String hzmc = StringEx.sNull(xmldo.getItemValue("HZMC"));
			if(hztj.equals("1")){
				mgr.queryHzbByQhjb(hzsjq, hzsjz, hzmc);
			}else if(hztj.equals("2")){
				mgr.queryHzbByBglx(hzsjq, hzsjz, hzmc);
			}else if(hztj.equals("3")){
				mgr.queryHzbByJbLx(hzsjq, hzsjz, hzmc);
			}
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("HZSJQ", hzsjq);
			xsBuf.append("HZSJZ", hzsjz);
			xsBuf.append("HZMC", hzmc);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "����ͳ�Ƴɹ�!",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("����ͳ��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>�������ƣ�deleteQhbgqk()/p>
	 * <p>����˵����ɾ��������Ϣ��</p>
	 * @param ��
	 * @Date 2010-06-12
	 * @author lijl
	 * @return ��
	 * @exception ��
	 */
	public void deleteQhbgqk() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String hzmc = StringEx.sNull(xmldo.getItemValue("HZMC"));
			mgr.deleteHzbByHzmc(hzmc);
			operLogSuccess(CommonConstants.ACTION_DELETE,"ɾ��������Ϣ�ɹ���");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "ɾ��������Ϣ�ɹ�!","");
		} catch (Exception e) {
			LogManager.getLogger().error("ɾ��������Ϣʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			operLogError(CommonConstants.ACTION_DELETE,"ɾ��������Ϣʧ�ܣ�");
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
}
