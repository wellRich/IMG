package com.padis.business.xzqhwh.lgywsjsc;

import java.util.List;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;
import com.padis.common.xzqhservice.IXzqhService;
import com.padis.common.xzqhservice.XzqhInterfaceFactory;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;


/**
 * <p>
 * Description: ��������ɾ����Service��
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @Date 2009-07-31
 * @author lijl
 * @version 1.0
 */
public class LgywsjscService extends XtwhBaseService {
	
	/**
	 * XtwhBaseService��manager����
	 */
	LgywsjscManager mgr;

	/**
	 * �޲ι��췽��
	 *
	 */
	public LgywsjscService() {
		mgr = new LgywsjscManager();
	}

	/**
	 * <p>�������ƣ� initXzqh()����ʼ����������ҳ��</p>
	 * <p>����˵������ʼ����������ҳ��ķ�����</p>
	 * @param ��
	 * @Date 2009-07-31
	 * @author ���
	 * @return ��
	 */
	public void initXzqh() {
		try {
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			IXzqhService xzqhService =XzqhInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String xzqh_dm = jgService.getXzqhDmByJg(swjg_dm);
			XMLDataObject xdo = xzqhService.getXzqhXxxx(xzqh_dm);		
			String dwlsgx_dm = xdo.getItemValue("DWLSGX_DM");
			String jc_dm = xzqhService.getJcdm(xzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("JC_DM",jc_dm);
			xsBuf.append("XZQH_DM",xzqh_dm);
			xsBuf.append("DWLSGX_DM",dwlsgx_dm);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"��ת����ʼ��ҳ��ɹ���",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ʼ��ҳ��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS, 2006, e.toString(), "");
		}
	}
	/**
	 * 
	 * <p>�������ƣ�getXjXzqh����</p>
	 * <p>����������ȡ��ʡ������������������</p>
	 * @author lijl
	 * @since 2009-07-31
	 */
	public void getXjXzqh(){
		try {
			XMLDataObject xdo = uwa.getArgXml();
			String sjxzqh = xdo.getItemValue("SJXZQH");
			String fhjd = xdo.getItemValue("FHJD");
			String resultXml = mgr.getXjXzqhdm(sjxzqh,fhjd);// ��ѯ���
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
	
	 /**
	 * <p>�������ƣ�deleteXzqh()��ɾ����������</p>
	 * <p>����˵����ɾ�����������ķ���������������ӱ�DM_XZQH��ɾ��������¼��</p>
	 * @param ��
	 * @Date 2009-07-31
	 * @author ���
	 * @return ��
	 */
	public void deleteXzqh() throws Exception {
		XmlStringBuffer xsBuf = new XmlStringBuffer();// ���ظ�ǰ̨������
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			String xzqh_dm = StringEx.sNull(xmldo.getItemValue("XZQH_DM"));
			List message = mgr.deleteXzqh(xzqh_dm);
			String msgXml = mgr.messageXml(message);
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("MESSAGES",msgXml);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			operLogSuccess(CommonConstants.ACTION_DELETE,"ɾ�����������ɹ�!");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "ɾ�����������ɹ���", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("ɾ����������ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			operLogError(CommonConstants.ACTION_DELETE,"ɾ����������ʧ�ܣ�");
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2008, e.toString(), "");
		}
	}
	
	
	 /**
	 * <p>�������ƣ�queryGaxx()����ѯ������Ϣ</p>
	 * @param ��
	 * @Date 2009-08-24
	 * @author ����
	 * @return ��
	 */
	public void queryGaxx() throws Exception {
		XmlStringBuffer xsBuf = new XmlStringBuffer();// ���ظ�ǰ̨������
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			long[] args = new long[2] ; //ǰ̨��ҳҪ�õĲ���
			String xzqh_dm = StringEx.sNull(xmldo.getItemValue("XZQH_DM"));
			String forRet = this.mgr.queryGaxx(xmldo , args  , xzqh_dm);
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			if( !forRet.equals("") ){
				xsBuf.appendHead("ITEMS");
				xsBuf.append(forRet);
				xsBuf.appendTail("ITEMS");
			}
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ѯ�ɹ���", xsBuf.toString());
		} catch (Exception e) {
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2008, e.toString(), "");
		}
	}
	
	
	public void deleteGaxx() throws Exception {
		XmlStringBuffer xsBuf = new XmlStringBuffer();// ���ظ�ǰ̨������
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			mgr.deleteGaxx(xmldo);
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "ɾ���ɹ���", xsBuf.toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2008, e.toString(), "");
		}
	}
}
