package com.padis.business.xzqhwh.zxbg.bgdzgl.cxbgdzb;

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
public class CxbgdzbService extends XtwhBaseService {
	
	/**
	 * 
	 */
	CxbgdzbManager mgr;

	/**
	 * �޲ι��췽��
	 *
	 */
	public CxbgdzbService() {
		mgr = new CxbgdzbManager();
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
	 * <p>�������ƣ�revokeSqd</p>
	 * <p>�����������ύ���뵥</p>
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void revokeSqd() throws Exception
	{
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			xmldo.rootScrollTo("map");
			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
			mgr.revokeSqd(sqdxh,uwa.getCzry_dm());
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "����������ձ�״̬�ɹ���", "");
		} catch (Exception e) {
			LogManager.getLogger().error("����������ձ�ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2009, e.toString(),"");
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
			XMLDataObject xmldo = this.uwa.getArgXml();
			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
			String sqdzt_dm = mgr.querySqdzt(sqdxh);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("SQDZT_DM",sqdzt_dm);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS, CommonConstants.RTNMSG_SUCCESS, "��ѯ���뵥״̬�ɹ���", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ���뵥״̬ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_FAIL, 2005, "��ѯ���뵥״̬ʧ�ܣ�", e.toString());
		}
	}
}
