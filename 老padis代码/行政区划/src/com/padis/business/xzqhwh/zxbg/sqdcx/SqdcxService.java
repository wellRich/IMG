/**
 * 
 */
package com.padis.business.xzqhwh.zxbg.sqdcx;


import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;
import com.padis.common.xzqhservice.XzqhInterfaceFactory;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.XmlStringBuffer;

/**
 * <p>Title: SqdcxService.java </p>
 * <p>Description:���뵥��ѯ��Service�� </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-10-13
 * @author pengld
 * @version 1.0
 * �޸���ʷ�� 
 * �޸���
 * �޸�ʱ��(yyyy/mm/dd)
 * �޸�����
 * �汾��
 */
public class SqdcxService extends XtwhBaseService {
	
	SqdcxManager mgr;
	
	/**
	 * �޲ι��췽��
	 */
	public SqdcxService()
	{
		mgr = new SqdcxManager();
	}
	
	/**
	 * <p>�������ƣ�initSqdcx</p>
	 * <p>����������Ϊҳ���ʼ������</p>
	 * @author pengld
	 * @since 2009-10-14
	 */
	public void initSqdcx()
	{
		try {
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm = uwa.getSwjg_dm();
			String xzqh_dm = jgService.getXzqhDmByJg(swjg_dm);
			String jb_dm = XzqhInterfaceFactory.getInstance().getInterfaceImp().getJbdm(xzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("JB_DM", jb_dm);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
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
	 * <p>�������ƣ�querySqd</p>
	 * <p>�������������뵥��ѯ</p>
	 * @author pengld
	 * @since 2009-10-14
	 */
	public void querySqd()
	{
		try {
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			XMLDataObject xdo = uwa.getArgXml();;
			long[] args = new long[]{-1,0};
			String swjg_dm = uwa.getSwjg_dm();
			//String qxjg_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = jgService.getXzqhDmByJg(swjg_dm);
			//String sbxzqh_dm = Common.getJbdm(xzqh_dm);
			String sqdList = mgr.querySqd(xdo,args,xzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
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
	 * <p>�������������뵥���Բ�ѯ</p>
	 * @author pengld
	 * @since 2009-10-14
	 */
	public void queryMxb()
	{
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			long[] args = new long[]{-1,0};
			String rzList = "";
			rzList = mgr.queryMxb(xmldo,args);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
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
	

}
