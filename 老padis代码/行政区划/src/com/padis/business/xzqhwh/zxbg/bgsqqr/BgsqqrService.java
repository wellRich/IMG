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
 * <p>Description:变更申请单省级确认的Service类</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-10-12
 * @author pengld
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */
public class BgsqqrService extends XtwhBaseService {
	
	/**
	 * 
	 */
	BgsqqrManager mgr;

	/**
	 * 无参构造方法
	 *
	 */
	public BgsqqrService() {
		mgr = new BgsqqrManager();
	}

	/**
	 * <p>方法名称：initSqd</p>
	 * <p>方法描述：初始化页面</p>
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "初始化页面成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("初始化页面失败:" + e.getMessage());
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询明细表成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询明细表失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	/**
	 * <p>方法名称：back</p>
	 * <p>方法描述：驳回审核通过的申请单</p>
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
	 * <p>方法名称：queryXjsqd</p>
	 * <p>方法描述：获取下级单位的所有未提交申请单的区划</p>
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询明细表成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询明细表失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
}
