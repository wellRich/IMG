package com.padis.business.xzqhwh.zxbg.qhdmtjcx;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.XmlStringBuffer;

/**
 * <p>
 * Title: QhdmtjcxService.java
 * </p>
 * <p>
 * Description:<类的功能描述>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: DigitalChina Co.Ltd
 * </p>
 * 
 * @date 2010-07-22
 * @author lijl
 * @version 1.0 
 */
public class QhdmtjcxService extends XtwhBaseService {

	/**
	 * XtwhBaseService的manager对象
	 */
	QhdmtjcxManager mgr;

	/**
	 * 无参构造方法
	 * 
	 */
	public QhdmtjcxService() {
		mgr = new QhdmtjcxManager();
	}
	
	/**
	 * <p>方法名称：queryXzqhxx</p>
	 * <p>方法描述：根据条件查询行政区划信息</p>
	 * @return 无
	 * @throws 无
	 * @author lijl
	 * @since 2010-07-22
	 */
	public void queryXzqhxx() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			long[] args = new long[]{-1,0};
			String xzqhList = mgr.queryXzqh(xdo,args);// 查询结果
			XmlStringBuffer xsBuf = new XmlStringBuffer();// 返回给前台的数据
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("XZQHLIST", xzqhList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			System.out.println("xsBuf--1340-----"+xsBuf);
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询省级以下行政区划代码名称失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS, 2009, "", "");
		}
	}

}
