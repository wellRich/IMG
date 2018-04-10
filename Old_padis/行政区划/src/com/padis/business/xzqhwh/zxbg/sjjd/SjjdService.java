package com.padis.business.xzqhwh.zxbg.sjjd;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;

import ctais.services.log.LogManager;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;


/**
 * <p>Title: SjjdService.java </p>
 * <p>Description:�м��ල��Service��</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2011-01-19
 * @author lijl
 * @version 1.0
 * �޸���ʷ�� 
 * �޸���
 * �޸�ʱ��(yyyy/mm/dd)
 * �޸�����
 * �汾��
 */
public class SjjdService extends XtwhBaseService {
	
	/**
	 * 
	 */
	SjjdManager mgr;

	/**
	 * �޲ι��췽��
	 *
	 */
	public SjjdService() {
		mgr = new SjjdManager();
	}

	/**
	 * <p>�������ƣ�initSqd</p>
	 * <p>������������ʼ��ҳ��</p>
	 * @author lijl
	 * @since 2011-01-19
	 */
	public void initSjjd() {
		try {
			IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();
			String qxjg_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(qxjg_dm));
			String wdList = mgr.queryYtj(xzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT"); 
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("YTJITEMS", wdList);
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
	 * <p>�������ƣ�queryWtj</p>
	 * <p>����������δ�ύ������</p>
	 * @author lijl
	 * @since 2011-01-19
	 */
	public void queryWtj() {
		try {
			IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();
			String qxjg_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(qxjg_dm));
			String wtjSqd =mgr.queryWtj(xzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("WTJITEMS",wtjSqd);
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
