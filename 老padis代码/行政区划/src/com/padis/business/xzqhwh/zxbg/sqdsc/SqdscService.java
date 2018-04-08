package com.padis.business.xzqhwh.zxbg.sqdsc;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;

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
public class SqdscService extends XtwhBaseService {
	
	/**
	 * 
	 */
	SqdscManager mgr;

	/**
	 * �޲ι��췽��
	 *
	 */
	public SqdscService() {
		mgr = new SqdscManager();
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
			XMLDataObject xmldo = uwa.getArgXml();
			String qxjg_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(qxjg_dm));
			
			long[] args = new long[]{-1,0};
			String sqdList = mgr.querySqb(xzqh_dm,xmldo,args);
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
	 * <p>�������ƣ�deleteSqd</p>
	 * <p>����������ɾ�����뵥</p>
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-22
	 */
	public void deleteSqd() throws Exception {
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			xmldo.rootScrollTo("map");
			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
			mgr.deleteSqd(sqdxh);
			operLogSuccess(CommonConstants.ACTION_DELETE,"ɾ�����뵥�ɹ�!");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "ɾ�����뵥�ɹ���", "");
		} catch (Exception e) {
			LogManager.getLogger().error("ɾ�����뵥ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			operLogError(CommonConstants.ACTION_DELETE,"ɾ�����뵥ʧ�ܣ�");
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2008, e.getMessage(),"");
		}
	}
	
	/**
	 * <p>�������ƣ�getCount</p>
	 * <p>������������ȡ���뵥�����е���ϸ��������</p>
	 * @author lijl
	 * @since 2010-01-28
	 */
	public void getCount() {
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			xmldo.rootScrollTo("map");
			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
			String count = mgr.getCount(sqdxh);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("COUNT", String.valueOf(count));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ѯ���뵥�ɹ���", xsBuf.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("��ѯ���뵥ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2009, e.getMessage(),"");
		}
	}

}
