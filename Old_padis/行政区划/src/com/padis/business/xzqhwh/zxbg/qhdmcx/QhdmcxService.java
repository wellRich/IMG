package com.padis.business.xzqhwh.zxbg.qhdmcx;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;

/**
 * <p>
 * Title: QhdmcxService.java
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
 * @date 2009-9-16
 * @author pengld
 * @version 1.0 修改历史： 修改人 修改时间(yyyy/mm/dd) 修改内容 版本号
 */
public class QhdmcxService extends XtwhBaseService {

	/**
	 * XtwhBaseService的manager对象
	 */
	QhdmcxManager mgr;

	/**
	 * 无参构造方法
	 * 
	 */
	public QhdmcxService() {
		mgr = new QhdmcxManager();
	}

	/**
	 * <p>
	 * 方法名称： initXzqh()，初始化行政区划页面
	 * </p>
	 * <p>
	 * 方法说明：初始化行政区划页面的方法。
	 * </p>
	 * 
	 * @param 无
	 * @Date 2009-07-31
	 * @author 李靖亮
	 * @return 无
	 */
	public void initXzqh() {
		try {	
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			String isFb = mgr.isFb();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("XZQH_DM", "000000000000000");
			xsBuf.append("DWLSGX_DM", "10");
			xsBuf.append("FLAG", isFb);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "跳转到初始化页面成功！", xsBuf
							.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("初始化页面失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS, 2006, e.toString(),
					"");
		}
	}

	/**
	 * <p>
	 * 方法名称：
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @author pengld
	 * @since 2009-9-17
	 */

	public void queryXzqh() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			String xzqhdm = xdo.getItemValue("XZQHDM");
			String tableName = xdo.getItemValue("DB");
			XMLDataObject xzqhXdo = mgr.queryXzqh(xzqhdm,tableName);// 查询结果
			XmlStringBuffer xsBuf = new XmlStringBuffer();// 返回给前台的数据
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			if (xzqhXdo != null) {
				xsBuf.append("XZQH_DM", xzqhXdo.getItemValue("XZQH_DM"));
				xsBuf.append("XZQH_MC", xzqhXdo.getItemValue("XZQH_MC"));
				xsBuf.append("XZQH_QC", xzqhXdo.getItemValue("XZQH_QC"));
				xsBuf.append("SJ_XZQH_MC", xzqhXdo.getItemValue("SJ_XZQH_DM"));
				xsBuf.append("DWLSGX", xzqhXdo.getItemValue("DWLSGX_DM"));
				xsBuf.append("XZQHLX", xzqhXdo.getItemValue("XZQHLX_DM"));
				if(StringEx.sNull(xzqhXdo.getItemValue("XNJD_BZ")).equals("Y"))
				{
					xsBuf.append("XNJD_BZ","是" );
				}else
				{
					xsBuf.append("XNJD_BZ","否" );
				}
				
			}

			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTNMSG_SUCCESS, "", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询省级以下行政区划代码名称失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS, 2009, "", "");
		}
	}

}
