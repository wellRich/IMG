package com.padis.business.xzqhwh.zxbg.bgsqqr;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;


/**
 * <p>Title: BgsqqrService.java </p>
 * <p>Description:������뵥ʡ��ȷ�ϵ�Service��</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-10-12
 * @author pengld
 * @version 1.0
 * �޸���ʷ�� 
 * �޸���
 * �޸�ʱ��(yyyy/mm/dd)
 * �޸�����
 * �汾��
 */
public class BgsqqrService extends XtwhBaseService {
	
	/**
	 * 
	 */
	BgsqqrManager mgr;

	/**
	 * �޲ι��췽��
	 *
	 */
	public BgsqqrService() {
		mgr = new BgsqqrManager();
	}

	/**
	 * <p>�������ƣ�initSqd</p>
	 * <p>������������ʼ��ҳ��</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void initSqd() {
		try {
			IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();
			String qxjg_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(qxjg_dm));
			String wdList = mgr.querySqb(xzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT"); 
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("COUNT",mgr.querySqdzt(xzqh_dm));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("SQDLIST", wdList);
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
	 * <p>�������ƣ�bgsqqr</p>
	 * <p>����������������뵥ȷ�ϲ������������뵥״̬</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void bgsqqr() {
		try {
			IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();
			String qxjg_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(qxjg_dm));
			mgr.bgsqqr(xzqh_dm,uwa.getCzry_dm(),qxjg_dm);
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ѯ��ϸ��ɹ�!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ��ϸ��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	/**
	 * <p>�������ƣ�back</p>
	 * <p>�����������������ͨ�������뵥</p>
	 * @author pengld
	 * @since 2009-9-25
	 */
	public void back() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			mgr.back(xdo.getItemValue("SQDXH"),xdo.getItemValue("SPYJ"),uwa.getQx_swjg_dm(),uwa.getCzry_dm());
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ѯ��ϸ��ɹ�!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ��ϸ��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>�������ƣ�queryXjsqd</p>
	 * <p>������������ȡ�¼���λ������δ�ύ���뵥������</p>
	 * @author pengld
	 * @since 2009-9-25
	 */
	public void queryWtj() {
		try {
			long[] args = new long[]{-1,0};
			IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();
			String qxjg_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(qxjg_dm));
			String wtjSqd =mgr.queryWtj(uwa.getArgXml(),xzqh_dm,args);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("WTJ", wtjSqd.equals("")?"0":"1");
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("XJSQD",wtjSqd);
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
}
