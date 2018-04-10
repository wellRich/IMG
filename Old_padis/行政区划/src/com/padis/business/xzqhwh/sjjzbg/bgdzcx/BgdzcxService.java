package com.padis.business.xzqhwh.sjjzbg.bgdzcx;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;
import com.padis.common.xzqhservice.XzqhInterfaceFactory;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.XmlStringBuffer;


/**
 * <p>
 * Description: 变更日志管理的Service类
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @Date 2009-07-10
 * @author 李靖亮
 * @version 1.0
 */
public class BgdzcxService extends XtwhBaseService {
	
	/**
	 * XtwhBaseService的manager对象
	 */
	BgdzcxManager mgr;

	/**
	 * 无参构造方法
	 *
	 */
	public BgdzcxService() {
		mgr = new BgdzcxManager();
	}

	/**
	 * <p>方法名称：queryXzqhfb()，查询行政区划发布表</p>
	 * <p>方法说明：查询行政区划发布表的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param 无
	 * @Date 2009-08-14
	 * @author 李靖亮
	 * @return 无
	 * @exception 无
	 */
	public void queryZip() {
		try {
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm = uwa.getSwjg_dm();
			String xzqh_dm = jgService.getXzqhDmByJg(swjg_dm);
			String jb_dm = XzqhInterfaceFactory.getInstance().getInterfaceImp().getJbdm(xzqh_dm);
			XMLDataObject xdo = uwa.getArgXml();
			long[] args = new long[]{-1,0};
			String sjxzqh_dm = "";
			if(!xzqh_dm.equals("")){
				sjxzqh_dm = xzqh_dm.substring(0, 2);
			}
			String zipList = mgr.queryZip(xdo,args,sjxzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("JB_DM",jb_dm);
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("ZIPLIST", zipList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询ZIP文件记录成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询ZIP文件记录失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：queryXzqhfb()，查询行政区划发布表</p>
	 * <p>方法说明：查询行政区划发布表的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param 无
	 * @Date 2009-08-14
	 * @author 李靖亮
	 * @return 无
	 * @exception 无
	 */
	public void queryDzb() {
		try {

			XMLDataObject xdo = uwa.getArgXml();
			long[] args = new long[]{-1,0};
			String zipList = mgr.queryDzb(xdo,args,uwa.getQx_swjg_dm());
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("ZIPLIST", zipList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询ZIP文件记录成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询ZIP文件记录失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}


}
