package com.padis.business.xzqhwh.sjjzbg.drzsb;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;


/**
 * <p>
 * Description: 导入正式表的Service类
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @Date 2009-09-10
 * @author lijl
 * @version 1.0
 */
public class DrzsbService extends XtwhBaseService {
	
	/**
	 * XtwhBaseService的manager对象
	 */
	DrzsbManager mgr;

	/**
	 * 无参构造方法
	 *
	 */
	public DrzsbService() {
		mgr = new DrzsbManager();
	}

	/**
	 * <p>方法名称：queryZip()，查询行政区划发布表</p>
	 * <p>方法说明：查询行政区划发布表的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param 无
	 * @Date 2009-09-10
	 * @author lijl
	 * @return 无
	 * @exception 无
	 */
	public void queryZip() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm = uwa.getSwjg_dm();
			String xzqh_dm = jgService.getXzqhDmByJg(swjg_dm);
			String sjxzqh_dm = "";
			if(!xzqh_dm.equals("")){
				sjxzqh_dm = xzqh_dm.substring(0, 2);
			}
			long[] args = new long[]{-1,0};
			String zipList = mgr.queryZip(xmldo,args,sjxzqh_dm);
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
	
	/**
	 * <p>方法名称：userOperate()，查询行政区划发布表</p>
	 * <p>方法说明：查询行政区划发布表的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param 无
	 * @Date 2009-09-10
	 * @author lijl
	 * @return 无
	 * @exception 无
	 */
	public void userOperate() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String zipxls = StringEx.sNull(xmldo.getItemValue("ZIPXH"));
			String bgzl_dm = StringEx.sNull(xmldo.getItemValue("BGZL_DM"));
			mgr.userOperate(zipxls,bgzl_dm);
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "操作成功！", "");
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("查询ZIP文件记录失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}

}
