package com.padis.business.xzqhwh.zxbg.qhztcx;

import com.padis.business.xzqhwh.common.Tree;
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
 * Description: �����ĵ�У�Ե�Service��
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @Date 2008-01-14
 * @author ���
 * @version 1.0
 */
public class QhztcxService extends XtwhBaseService {
	
	/**
	 * DzwdjdService��manager����
	 */
	QhztcxManager mgr;

	/**
	 * �޲ι��췽��
	 *
	 */
	public QhztcxService() {
		mgr = new QhztcxManager();
	}

	
	/**
	 * <p>�������ƣ�initTree</p>
	 * <p>��������������������</p>
	 * @author wangjz
	 * @since Jan 21, 2008
	 */
	public void initXzqh() {
		try {
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			IXzqhService xzqhService =XzqhInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String xzqh_dm = jgService.getXzqhDmByJg(swjg_dm);
			XMLDataObject xdo = xzqhService.getXzqhXxxx(xzqh_dm);		
			String xhqh_mc = xdo.getItemValue("XZQH_MC");
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("XZQH_MC", xhqh_mc);
			xsBuf.append("XZQH_DM", xzqh_dm);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "�����ĵ�Ŀ¼������سɹ���", xsBuf
							.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("�����ĵ�Ŀ¼�������ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTN_FAIL, "�����ĵ�Ŀ¼�������ʧ�ܣ�", e.toString());
		}
	}
	/**
	 * <p>�������ƣ�queryWjyWd()���������״̬Ϊ�ѹ鵵������������������</p>
	 * <p>����˵�����������״̬Ϊ�ѹ鵵�����������������ݵķ�����������������WDCJ_WDGL���м�����������¼��</p>
	 * @param ��
	 * @Date 2008-01-14
	 * @author ���
	 * @return ��
	 * @exception ��
	 */
	public void queryXjXzqh() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			long[] args = new long[]{-1,0};
			String wdList = mgr.queryXjXzqh(xmldo,args);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("WDLIST", wdList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "��ѯ�����ĵ���Ϣ�ɹ�!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ�����ĵ���Ϣʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>�������ƣ�initTree</p>
	 * <p>��������������������</p>
	 * @author wangjz
	 * @since Jan 21, 2008
	 */
	public void initTree() {
		try {
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			IXzqhService xzqhService =XzqhInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String xzqhDm = jgService.getXzqhDmByJg(swjg_dm);
			Tree tree = new Tree();
			String xzqh_dm = StringEx.sNull(uwa.getArgXml().getItemValue("XZQH_DM"));		
			if (xzqh_dm.equals("")) {
				xzqh_dm = xzqhDm;
			}
			XMLDataObject xdo = xzqhService.getXzqhXxxx(xzqhDm);		
			String xhqhmc = xdo.getItemValue("XZQH_MC");		
			String subTree = tree.createXzqhTree(xzqh_dm,"");
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("XZQH_MC", xhqhmc);
			xsBuf.appendHead("TREE");
			StringBuffer sbuf = new StringBuffer("<![CDATA[");
			sbuf.append(subTree);
			sbuf.append("]]>");
			xsBuf.append(sbuf.toString());
			xsBuf.appendTail("TREE");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "�����ĵ�Ŀ¼������سɹ���", xsBuf
							.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("�����ĵ�Ŀ¼�������ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTN_FAIL, "�����ĵ�Ŀ¼�������ʧ�ܣ�", e.toString());
		}
	}
}
