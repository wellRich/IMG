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
 * <p>Description:申请单查询的Service类 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-10-13
 * @author pengld
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */
public class SqdcxService extends XtwhBaseService {
	
	SqdcxManager mgr;
	
	/**
	 * 无参构造方法
	 */
	public SqdcxService()
	{
		mgr = new SqdcxManager();
	}
	
	/**
	 * <p>方法名称：initSqdcx</p>
	 * <p>方法描述：为页面初始化机构</p>
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "初始化页面成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("初始化页面失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：querySqd</p>
	 * <p>方法描述：申请单查询</p>
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "初始化页面成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("初始化页面失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：queryMxb</p>
	 * <p>方法描述：申请单明显查询</p>
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询明细表成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询明细表失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：expSqd</p>
	 * <p>方法描述：导出申请单详细信息</p>
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
			this.setResponse(CommonConstants.RTN_SUCCESS, CommonConstants.RTNMSG_SUCCESS, "跳转页面成功！", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("跳转页面失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_FAIL, 2005, "跳转页面失败！", e.toString());
		}
	}
	

}
