/**
 * 
 */
package com.padis.business.xzqhwh.common;

import com.padis.common.BaseService;
import com.padis.common.constants.CommonConstants;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.XmlStringBuffer;

/**
 * <p>Title: xzqhService.java </p>
 * <p>Description:<类的功能描述> </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-9-24
 * @author pengld
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */
public class XzqhService extends BaseService{
	
	/**
	 * XzqhbgsqbService类的Manager
	 */

	XzqhManager mgr;


	/**
	 * 构造函数
	 */
	public XzqhService() {
		mgr = new XzqhManager();

	}
	
	/**
	 * 
	 * <p>
	 * 方法名称：getXjXzqh（）
	 * </p>
	 * <p>
	 * 方法描述：取得省级以下行政区划代码
	 * </p>
	 * 
	 * @author lijl
	 * @since 2009-07-31
	 */
	public void getXjXzqh() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			String sjxzqh = xdo.getItemValue("SJXZQH");
			String fhjd = xdo.getItemValue("FHJD");
			String db = xdo.getItemValue("DB");
			mgr.setDb(db);
			String resultXml = mgr.getXjXzqhdm(sjxzqh, fhjd);// 查询结果
			XmlStringBuffer xsBuf = new XmlStringBuffer();// 返回给前台的数据
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendHead(fhjd);
			xsBuf.append(resultXml);
			xsBuf.appendTail(fhjd);
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
