package com.padis.business.xzqhwh.zxbg.gjsh;

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
 * <p>Title: GjshService </p>
 * <p>Description:变更申请单申请审批的Service类 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-9-18
 * @author pengld
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */
public class GjshService extends XtwhBaseService {
	
	/**
	 * 
	 */
	GjshManager mgr;

	/**
	 * 无参构造方法
	 *
	 */
	public GjshService() {
		mgr = new GjshManager();
	}

	/**
	 * <p>方法名称：initSqd</p>
	 * <p>方法描述：</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void queryYtj() {
		try {
			String wdList = mgr.queryYtj();
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("YTJSQD", wdList);
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
	 * <p>方法描述：查询变更申请单</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void querySqd(){
		try
		{
			XMLDataObject xmldo = uwa.getArgXml();
			long[] args = new long[]{-1,0};
			String sqdList = mgr.querySqd(xmldo,args);
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
	 * <p>方法名称：gjsh</p>
	 * <p>方法描述：变更申请审批</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void gjsh() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM"));
			mgr.gjsh(xzqh_dm,uwa.getQx_swjg_dm(),uwa.getCzry_dm());
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询明细表成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询明细表失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询明细表成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询明细表失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @author pengld
	 * @since 2009-9-25
	 */
	public void querySqdzt() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM"));
			String count = mgr.querySqdzt(xzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("COUNT", count);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
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
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @author pengld
	 * @since 2009-10-12
	 */
	public void initSqdzt()
	{
		try {
			String sqdztXml = mgr.initSqdzt();
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendHead("SQDZT_DM");
			xsBuf.append(sqdztXml.replaceAll("row","ITEM").replaceAll("SQDZT_DM","ID").replaceAll("SQDZT_MC","NAME"));
			xsBuf.appendTail("SQDZT_DM");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
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
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @author pengld
	 * @since 2009-10-12
	 */
	public void queryWtj(){
		try {
			String wdList =mgr.queryWtj();
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("WTJ", wdList.equals("")?"0":"1");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("WTJSQD",wdList);
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
	 * <p>方法名称：bgsqqr</p>
	 * <p>方法描述：变更申请单确认操作，更改申请单状态</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void bgsqqr() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			String sqdxh = StringEx.sNull(xdo.getItemValue("SQDXH"));
			mgr.bgsqqr(sqdxh);
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询明细表成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询明细表失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}

}
