package com.padis.business.xzqhwh.zxbg.bgsqsp;

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
 * <p>Title: BgsqspService.java </p>
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
public class BgsqspService extends XtwhBaseService {
	
	/**
	 * 
	 */
	BgsqspManager mgr;

	/**
	 * 无参构造方法
	 *
	 */
	public BgsqspService() {
		mgr = new BgsqspManager();
	}

	/**
	 * <p>方法名称：initSqd</p>
	 * <p>方法描述：</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void initSqd() {
		try {
			IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();
			XMLDataObject xmldo = uwa.getArgXml();
			String qxjg_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(qxjg_dm));
			long[] args = new long[]{-1,0};
			String wdList = mgr.querySqb(xzqh_dm,xmldo,args);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
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
	 * <p>方法名称：queryMxb</p>
	 * <p>方法描述：</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void queryMxb() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String rzList = "";
			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
			if(!sqdxh.equals(""))
			{
				rzList = mgr.queryMxb(xmldo);
			}
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("SQDXH", sqdxh);
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
	 * <p>方法名称：bgsqsp</p>
	 * <p>方法描述：变更申请审批</p>
	 * @author pengld
	 * @since 2009-9-24
	 */
	public void bgsqsp() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
			String flag = xmldo.getItemValue("FLAG");
			if(flag.equals("Y"))
			{
				mgr.bgsqsp(sqdxh,true,"审核通过！",uwa.getQx_swjg_dm(),uwa.getCzry_dm());
			}else
			{
				mgr.bgsqsp(sqdxh,false,xmldo.getItemValue("SPYJ"),uwa.getQx_swjg_dm(),uwa.getCzry_dm());
			}

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
			xsBuf.append("SQDMC","申请单");
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
