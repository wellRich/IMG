package com.padis.business.xzqhwh.zxbg.bgqkhzcx.aqhjbhbglxcx;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;


import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;


/**
 * 
 * <p>Title: AqhjbhbglxcxService.java </p>
 * <p>Description:汇总全国区划代码变更情况的Service类 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2010-05-07
 * @author lijl
 * @version 1.0
 */
public class AqhjbhbglxcxService extends XtwhBaseService {
	
	/**
	 * 
	 */
	AqhjbhbglxcxManager mgr;

	/**
	 * 无参构造方法
	 *
	 */
	public AqhjbhbglxcxService() {
		mgr = new AqhjbhbglxcxManager();
	}

	/**
	 * <p>方法名称：queryMxb</p>
	 * <p>方法描述：汇总全国区划变更情况</p>
	 * @author lijl
	 * @since 2010-05-07
	 */
	public void queryMxb() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String hzmc = StringEx.sNull(xmldo.getItemValue("HZMC"));
			String sqdList = mgr.queryMxb(hzmc);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("ITEMS", sqdList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "初始化页面成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("初始化页面失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}

	/**
	 * <p>方法名称：exportQhbgqkhzb</p>
	 * <p>方法描述：导出全国区划变更情况汇总表</p>
	 * @author lijl
	 * @since 2010-05-07
	 */
	public void exportQhbgqkhzb() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String hzmc = StringEx.sNull(xmldo.getItemValue("HZMC"));
			String sqdList = mgr.queryMxb(hzmc);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("MXBLIST", sqdList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "初始化页面成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("初始化页面失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：groupByHzmc</p>
	 * <p>方法描述：获取汇总名称分组信息</p>
	 * @author lijl
	 * @since 2010-05-07
	 */
	public void getHzmc() {
		try {
			String hzmcList = mgr.groupByHzmc();
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("HZMCLIST", hzmcList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "获取汇总名称分组信息成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("获取汇总名称分组信息失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
}
