package com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb;

import com.padis.business.xzqhwh.common.Common;
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
 * 
 * <p>Title: SqdwhService.java </p>
 * <p>Description:������뵥ά����Service�� </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-9-18
 * @author pengld
 * @version 1.0
 * �޸���ʷ�� 
 * �޸���
 * �޸�ʱ��(yyyy/mm/dd)
 * �޸�����
 * �汾��
 */
public class WhbgdzbService extends XtwhBaseService {
	
	/**
	 * 
	 */
	WhbgdzbManager mgr;

	/**
	 * �޲ι��췽��
	 *
	 */
	public WhbgdzbService() {
		mgr = new WhbgdzbManager();
	}

	/**
	 * <p>�������ƣ�initSqb</p>
	 * <p>������������ʼ������</p>
	 * @author pengld
	 * @since 2009-9-18
	 */
	public void initSqd() {
		try {
			IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();
			IXzqhService xzqhService = XzqhInterfaceFactory.getInstance().getInterfaceImp();
			XMLDataObject xmldo = uwa.getArgXml();
			String qxjg_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(qxjg_dm));
			String xzqh_mc = StringEx.sNull(xzqhService.getXzqhName(xzqh_dm));
			
			long[] args = new long[]{-1,0};
			String sqdList = mgr.querySqb(xzqh_dm,xmldo,args);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("COUNT",mgr.querySqdzt(xzqh_dm));
			xsBuf.append("MC",xzqh_mc);
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("SQDLIST", sqdList);
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
	 * <p>�������ƣ�queryMxb</p>
	 * <p>������������ѯ���뵥��ص���ϸ��Ϣ</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void queryMxb() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			long[] args = new long[]{-1,0};
			String rzList = "";
			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
			String sqdzt_dm = StringEx.sNull(xmldo.getItemValue("SQDZT_DM"));
			String sbxzqh_dm = StringEx.sNull(xmldo.getItemValue("SBXZQH_DM"));
			String flag ="false";
			if(sqdzt_dm.equals(Common.XZQH_SQDZT_WTJ)||sqdzt_dm.equals(Common.XZQH_SQDZT_SHBTG))
			{
				flag="true";
			}
			if(!sqdxh.equals(""))
			{
				rzList = mgr.queryMxb(xmldo,args);
			}
			String sfltj ="true";//�Ƿ����ύ
			if(!rzList.equals("")){
				sfltj ="false";
			}
			
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("SFLTJ",sfltj);
			xsBuf.append("FLAG",flag);
			xsBuf.append("SQDXH", sqdxh);
			xsBuf.append("SBXZQH_DM", sbxzqh_dm);
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("ITEMS", rzList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ѯ��ϸ��ɹ�!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ��ϸ��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	

	 /**
	  * <p>�������ƣ�deleteMxb</p>
	  * <p>����������ɾ�����뵥��ص���ϸ��Ϣ</p>
	  * @throws Exception
	  * @author pengld
	  * @since 2009-9-24
	  */
	public void deleteMxb() throws Exception {
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			String groupxh = StringEx.sNull(xmldo.getItemValue("GROUPXH"));
			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
			String sbxzqh_dm = StringEx.sNull(xmldo.getItemValue("SBXZQH_DM"));
			mgr.deleteMxb(groupxh,sqdxh,sbxzqh_dm);			
			operLogSuccess(CommonConstants.ACTION_DELETE,"ɾ����ϸ���ݳɹ�!");
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "ɾ����ϸ���ݳɹ���", "");
		} catch (Exception e) {
			LogManager.getLogger().error("ɾ����ϸ����ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			operLogError(CommonConstants.ACTION_DELETE,"ɾ����ϸ����ʧ�ܣ�");
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2008, e.getMessage(),"");
		}
	}
	
	/**
	 * <p>�������ƣ�addSqd</p>
	 * <p>����������������뵥</p>
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void addSqd() throws Exception {
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			xmldo.rootScrollTo("map");
			mgr.addSqd(xmldo,uwa.getCzry_dm(),uwa.getQx_swjg_dm());
			XmlStringBuffer xsBuf = new XmlStringBuffer();
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
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "��ѯ�������ϸ��Ϣ�ɹ���", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ�������ϸ��Ϣʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2009, e.toString(),"");
		}
	}
	
	/**
	 * <p>�������ƣ�updateSqd</p>
	 * <p>�����������������뵥</p>
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void updateSqd() throws Exception {
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			xmldo.rootScrollTo("map");
			mgr.updateSqd(xmldo,uwa.getCzry_dm(),uwa.getQx_swjg_dm());
			XmlStringBuffer xsBuf = new XmlStringBuffer();
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
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "��ѯ�������ϸ��Ϣ�ɹ���", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ�������ϸ��Ϣʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2009, e.toString(),"");
		}
	}
	
//	/**
//	 * <p>�������ƣ�deleteSqd</p>
//	 * <p>����������ɾ�����뵥</p>
//	 * @throws Exception
//	 * @author pengld
//	 * @since 2009-9-22
//	 */
//	public void deleteSqd() throws Exception {
//		try {
//			XMLDataObject xmldo = this.uwa.getArgXml();
//			xmldo.rootScrollTo("map");
//			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
//			mgr.deleteSqd(sqdxh);
//			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ѯ�������ϸ��Ϣ�ɹ���", "");
//		} catch (Exception e) {
//			e.printStackTrace();
//			LogManager.getLogger().error("��ѯ�������ϸ��Ϣʧ��:" + e.getMessage());
//			LogManager.getLogger().log(e);
//			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2009, e.toString(),"");
//		}
//	}
	
	/**
	 * <p>�������ƣ�commitSqd</p>
	 * <p>�����������ύ���뵥</p>
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void commitSqd() throws Exception
	{
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			xmldo.rootScrollTo("map");
			mgr.commitSqd(xmldo,uwa.getCzry_dm());
			XmlStringBuffer xsBuf = new XmlStringBuffer();
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
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "��ѯ�������ϸ��Ϣ�ɹ���", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ�������ϸ��Ϣʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2009, e.toString(),"");
		}
		
	}
	
	/**
	 * <p>�������ƣ�expSqd</p>
	 * <p>�����������������뵥��ϸ��Ϣ</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void expSqd(){
		try {
			XMLDataObject xdo = uwa.getArgXml();
			String wjxx = mgr.expSqd(xdo.getItemValue("SQDXH"));
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("SQDMC",xdo.getItemValue("SQDMC"));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("MXBLIST",wjxx);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS, CommonConstants.RTNMSG_SUCCESS, "��תҳ��ɹ���", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��תҳ��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_FAIL, 2005, "��תҳ��ʧ�ܣ�", e.toString());
		}
	}
	
	/**
	 * <p>�������ƣ�checkScsj</p>
	 * <p>�����������鿴�Ƿ���ʡ���ϱ�������</p>
	 * @author lijl
	 * @since 2009-10-21
	 */
	public void checkScsj(){
		try {
			XMLDataObject xdo = uwa.getArgXml();
			String flag = mgr.checkScsj(StringEx.sNull(xdo.getItemValue("SQDXH")));
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("FLAG",flag);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");	
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS, CommonConstants.RTNMSG_SUCCESS, "У��ɹ���", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("У��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_FAIL, 2005, "У��ʧ�ܣ�", e.toString());
		}
	}
	
	/**
	 * <p>�������ƣ�querySqdzt</p>
	 * <p>������������ѯδ������ǰ����������</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void querySqdzt(){
		try {
			IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();
			String qxjg_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(qxjg_dm));
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("COUNT",mgr.querySqdzt(xzqh_dm));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS, CommonConstants.RTNMSG_SUCCESS, "��תҳ��ɹ���", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��תҳ��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_FAIL, 2005, "��תҳ��ʧ�ܣ�", e.toString());
		}
	}
}
