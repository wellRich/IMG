package com.padis.business.xzqhwh.sjjzbg.xzqhsjsc;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;

/**
 * <p>Title: XzqhsjscService</P>
 * <p>Description: ʡ���ϱ����ݵ�service��</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @auther lijl
 * @version 1.0
 * @since 2009-09-10
 * �޸��ˣ�
 * �޸�ʱ�䣺
 * �޸����ݣ�
 * �޸İ汾�ţ�
 */
public class XzqhsjscService extends XtwhBaseService{
	XzqhsjscManager mgr;
	/**
	 * ���캯��
	 */
	public XzqhsjscService() {
		mgr = new XzqhsjscManager();
	}
	
	/**
	 * <p>�������ƣ� initSjsc()����ʼ����������ҳ��</p>
	 * <p>����˵������ʼ����������ҳ��ķ�����</p>
	 * @param ��
	 * @Date 2009-06-17
	 * @author ���
	 * @return ��
	 */
	public void initSjsc() {
		try {
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(swjg_dm));
			if(xzqh_dm.length()==15){
				xzqh_dm = xzqh_dm.substring(0, 6);
			}
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("XZQH_DM",xzqh_dm);
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
	 * <p>�������ƣ�uploadSjxzqhsj()���ϴ�ʡ���������</p>
	 * <p>����˵������ѯ��������������ķ�����������������XT_XZQHFB���м�����������¼��</p>
	 * @param ��
	 * @Date 2009-09-10
	 * @author lijl
	 * @return ��
	 * @exception ��
	 */
	public void uploadSjbgsj() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String czry_dm = uwa.getCzry_dm();
			String qxjg_dm = uwa.getQx_swjg_dm();
			mgr.uploadXzqhjzbgzip(xmldo,czry_dm,qxjg_dm);
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ѯ�������Ϣ�ɹ�!", "");
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("��ѯ�������Ϣʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	
	/**
	 * <p>�������ƣ�queryXzqhfb()����ѯ��������������</p>
	 * <p>����˵������ѯ��������������ķ�����������������XT_XZQHFB���м�����������¼��</p>
	 * @param ��
	 * @Date 2009-09-10
	 * @author lijl
	 * @return ��
	 * @exception ��
	 */
	public void queryXzqhjzbgzip() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(swjg_dm));
			String sjxzqh_dm = "";
			if(!xzqh_dm.equals("")){
				sjxzqh_dm = xzqh_dm.substring(0, 2);
			}
			long[] args = new long[]{-1,0};
			String zipList = mgr.queryXzqhjzbgzip(xmldo,args,sjxzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("SZXZQH_DM", sjxzqh_dm);
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("ZIPLIST", zipList);
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
	 * <p>�������ƣ�queryXzqhfb()����ѯ��������������</p>
	 * <p>����˵������ѯ��������������ķ�����������������XT_XZQHFB���м�����������¼��</p>
	 * @param ��
	 * @Date 2009-09-10
	 * @author lijl
	 * @return ��
	 * @exception ��
	 */
	public void deleteZip(){
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String zipxhs = xmldo.getItemValue("ZIPXH");
			mgr.deleteZip(zipxhs);
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ѯ�������Ϣ�ɹ�!", "");
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ�������Ϣʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>�������ƣ�queryXzqhfb()����ѯ��������������</p>
	 * <p>����˵������ѯ��������������ķ�����������������XT_XZQHFB���м�����������¼��</p>
	 * @param ��
	 * @Date 2009-09-10
	 * @author lijl
	 * @return ��
	 * @exception ��
	 */
	public void isUpload(){
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String xzqh_dm = xmldo.getItemValue("XZQH_DM");
			String fileName = xmldo.getItemValue("WJM");
			String flag = mgr.isUpload(xzqh_dm,fileName);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("FLAG", flag);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
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
	 * <p>�������ƣ�getZipzt()����ȡZIP�ļ�״̬</p>
	 * <p>����˵������ѯ��������������ķ�����������������XT_XZQHFB���м�����������¼��</p>
	 * @param ��
	 * @Date 2009-09-10
	 * @author lijl
	 * @return ��
	 * @exception ��
	 */
	public void getZipzt(){
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String zipxh = xmldo.getItemValue("ZIPXH");
			String msg = mgr.getZipzt(zipxh);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("MSG", msg);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
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
}
