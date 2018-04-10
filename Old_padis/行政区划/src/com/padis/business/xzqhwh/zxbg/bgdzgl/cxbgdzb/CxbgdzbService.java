package com.padis.business.xzqhwh.zxbg.bgdzgl.cxbgdzb;

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
 * <p>Title: SqdwhService.java </p>
 * <p>Description:变更申请单维护的Service类 </p>
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
public class CxbgdzbService extends XtwhBaseService {
	
	/**
	 * 
	 */
	CxbgdzbManager mgr;

	/**
	 * 无参构造方法
	 *
	 */
	public CxbgdzbService() {
		mgr = new CxbgdzbManager();
	}

	/**
	 * <p>方法名称：initSqb</p>
	 * <p>方法描述：初始化数据</p>
	 * @author pengld
	 * @since 2009-9-18
	 */
	public void initSqd() {
		try {
			IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();
			IXzqhService xzqhService = XzqhInterfaceFactory.getInstance().getInterfaceImp();
			XMLDataObject xmldo = uwa.getArgXml();
			String qxjg_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(qxjg_dm));
			String xzqh_mc = StringEx.sNull(xzqhService.getXzqhName(xzqh_dm));
			
			long[] args = new long[]{-1,0};
			String sqdList = mgr.querySqb(xzqh_dm,xmldo,args);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("COUNT",mgr.querySqdzt(xzqh_dm));
			xsBuf.append("MC",xzqh_mc);
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
	 * <p>方法名称：revokeSqd</p>
	 * <p>方法描述：提交申请单</p>
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void revokeSqd() throws Exception
	{
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			xmldo.rootScrollTo("map");
			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
			mgr.revokeSqd(sqdxh,uwa.getCzry_dm());
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "撤消变更对照表状态成功！", "");
		} catch (Exception e) {
			LogManager.getLogger().error("撤消变更对照表失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2009, e.toString(),"");
		}
		
	}
	
	/**
	 * <p>方法名称：querySqdzt</p>
	 * <p>方法描述：查询未发布以前的数据条数</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void querySqdzt(){
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
			String sqdzt_dm = mgr.querySqdzt(sqdxh);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("SQDZT_DM",sqdzt_dm);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS, CommonConstants.RTNMSG_SUCCESS, "查询申请单状态成功！", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询申请单状态失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_FAIL, 2005, "查询申请单状态失败！", e.toString());
		}
	}
}
