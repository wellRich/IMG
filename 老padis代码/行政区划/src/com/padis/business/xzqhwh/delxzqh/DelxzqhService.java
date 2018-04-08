package com.padis.business.xzqhwh.delxzqh;

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
 * Description: 行政区划删除的Service类
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
public class DelxzqhService extends XtwhBaseService {
	
	/**
	 * XtwhBaseService的manager对象
	 */
	DelxzqhManager mgr;

	/**
	 * 无参构造方法
	 *
	 */
	public DelxzqhService() {
		mgr = new DelxzqhManager();
	}

	/**
	 * <p>方法名称： initXzqh()，初始化行政区划页面</p>
	 * <p>方法说明：初始化行政区划页面的方法。</p>
	 * @param 无
	 * @Date 2009-07-31
	 * @author 李靖亮
	 * @return 无
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"跳转到初始化页面成功！",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("初始化页面失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS, 2006, e.toString(), "");
		}
	}
	
	 /**
	 * <p>方法名称：deleteXzqh()，删除行政区划</p>
	 * <p>方法说明：删除行政区划的方法，本方法将会从表DM_XZQH中删除多条记录。</p>
	 * @param 无
	 * @Date 2009-07-31
	 * @author 李靖亮
	 * @return 无
	 */
	public void deleteXzqh() throws Exception {
		XmlStringBuffer xsBuf = new XmlStringBuffer();// 返回给前台的数据
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			String xzqh_dm = StringEx.sNull(xmldo.getItemValue("XZQH_DM"));
			String delFlag = StringEx.sNull(xmldo.getItemValue("FLAG"));
			List message = mgr.deleteXzqh(xzqh_dm,delFlag);
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
			operLogSuccess(CommonConstants.ACTION_DELETE,"删除行政区划成功!");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "删除行政区划成功！", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("删除行政区划失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			operLogError(CommonConstants.ACTION_DELETE,"删除行政区划失败！");
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2008, e.toString(), "");
		}
	}
}
