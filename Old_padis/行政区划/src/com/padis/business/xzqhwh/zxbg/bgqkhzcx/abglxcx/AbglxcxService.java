package com.padis.business.xzqhwh.zxbg.bgqkhzcx.abglxcx;

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
 * <p>Title: AbglxcxService.java </p>
 * <p>Description:按变更类型汇总区划变更情况的Service类 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2010-05-07
 * @author lijl
 * @version 1.0
 */
public class AbglxcxService extends XtwhBaseService {
	
	/**
	 * 
	 */
	AbglxcxManager mgr;

	/**
	 * 无参构造方法
	 *
	 */
	public AbglxcxService() {
		mgr = new AbglxcxManager();
	}

	/**
	 * <p>方法名称：queryMxb</p>
	 * <p>方法描述：按变更类型汇总区划变更情况</p>
	 * @author lijl
	 * @since 2010-05-07
	 */
	public void queryMxb() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();	
			String xzqh_dm = StringEx.sNull(xmldo.getItemValue("XZQH_DM"));
			String hzmc = StringEx.sNull(xmldo.getItemValue("HZMC"));
			String sjxzqh_dm = "";
			if(xzqh_dm.equals("")){
				IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();	
				String qxjg_dm = uwa.getQx_swjg_dm();
				xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(qxjg_dm));
			}
			IXzqhService xzqhService = XzqhInterfaceFactory.getInstance().getInterfaceImp();
			
			if(!xzqh_dm.equals("000000000000000")){
				sjxzqh_dm =xzqhService.getSjXzqhDm(xzqh_dm);
			}else{
				sjxzqh_dm = "000000000000000";
			}
			String sqdList = mgr.queryMxb(xzqh_dm,hzmc);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("XZQHDM", xzqh_dm);
			xsBuf.append("SJXZQH_DM", sjxzqh_dm);
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
	 * <p>方法描述：导出按区划级别分的区划变更情况汇总表</p>
	 * @author lijl
	 * @since 2010-05-07
	 */
	public void exportQhbgqkhzb() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();	
			String xzqh_dm = StringEx.sNull(xmldo.getItemValue("XZQH_DM"));
			String hzmc = StringEx.sNull(xmldo.getItemValue("HZMC"));
			String sqdList = mgr.queryMxb(xzqh_dm,hzmc);
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
	 * <p>方法名称：getHzmc</p>
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
