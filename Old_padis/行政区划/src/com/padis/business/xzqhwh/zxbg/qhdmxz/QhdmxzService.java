package com.padis.business.xzqhwh.zxbg.qhdmxz;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;


/**
 * <p>
 * Description:  发布行政区划的Service类
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @Date 2009-08-14
 * @author 李靖亮
 * @version 1.0
 */
public class QhdmxzService extends XtwhBaseService {
	
	/**
	 * XtwhBaseService的manager对象
	 */
	QhdmxzManager mgr;

	/**
	 * 无参构造方法
	 *
	 */
	public QhdmxzService() {
		mgr = new QhdmxzManager();
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
	public void queryXzqhfb() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String sqbxh = StringEx.sNull(xmldo.getItemValue("SQBXH"));
			long[] args = new long[]{-1,0};
			String wdList = mgr.queryXzqhfb(xmldo,args);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("SQBXH", sqbxh);
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("RZLIST", wdList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询申请表信息成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询申请表信息失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：getFilePath()，获取文件路径</p>
	 * <p>方法说明：获取文件路径的方法。</p>
	 * @Date 2009-08-14
	 * @author 李靖亮
	 * @return 无
	 * @exception 无
	 */
	public void getFilePath() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String dcxh = StringEx.sNull(xmldo.getItemValue("DCXH"));
			String filePath = mgr.getFilePath(dcxh);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("PATH", filePath);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询路径成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询路径失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	/**
	 * <p>方法名称：exportFile()，导出全国区划表和行政区划变更表</p>
	 * <p>方法说明：导出全国区划表和行政区划变更表的方法。</p>
	 * @param 无
	 * @Date 2009-08-14
	 * @author 李靖亮
	 * @return 无
	 * @exception 无
	 */
	public void exportFile() {
		try {
			mgr.zipXzqhFile();
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "导出成功!","");
		} catch (Exception e) {
			LogManager.getLogger().error("查询申请表信息失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
}
