/**
 * 
 */
package com.padis.business.xzqhwh.zxbg.bgdzcx;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.XmlStringBuffer;

/**
 * <p>Title: SqdcxService.java </p>
 * <p>Description:变更对照查询的Service类 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-11-18
 * @author pengld
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */
public class BgdzcxService extends XtwhBaseService {
	
	BgdzcxManager mgr;
	
	/**
	 * 无参构造方法
	 */
	public BgdzcxService()
	{
		mgr = new BgdzcxManager();
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
			String qxjg_dm = uwa.getQx_swjg_dm();
			XMLDataObject xmldo = uwa.getArgXml();
			long[] args = new long[]{-1,0};
			String rzList = "";
			rzList = mgr.queryMxb(xmldo,qxjg_dm,args);
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
}
