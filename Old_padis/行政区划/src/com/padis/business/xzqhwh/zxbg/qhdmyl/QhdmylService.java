package com.padis.business.xzqhwh.zxbg.qhdmyl;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;
import com.padis.common.xzqhservice.IXzqhService;
import com.padis.common.xzqhservice.XzqhInterfaceFactory;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.XmlStringBuffer;

/**
 * <p>
 * Description: 行政区划变更的Service类
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @Date 2009-06-11
 * @author 李靖亮
 * @version 1.0
 */
public class QhdmylService extends XtwhBaseService{
	
	/**
	 * <p>方法名称： initXzqh()，初始化行政区划页面</p>
	 * <p>方法说明：初始化行政区划页面的方法。</p>
	 * @param 无
	 * @Date 2009-06-17
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
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
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
	
}
