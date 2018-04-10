package com.padis.business.xzqhwh.qhdmbgqkhztj;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;


/**
 * <p>
 * Description:  汇总统计区划变更情况的Service类
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @Date 2010-06-11
 * @author lijl
 * @version 1.0
 */
public class QhdmbgqkhztjService extends XtwhBaseService {
	
	/**
	 * QhdmbgqkhztjService的manager对象
	 */
	QhdmbgqkhztjManager mgr;

	/**
	 * 无参构造方法
	 *
	 */
	public QhdmbgqkhztjService() {
		mgr = new QhdmbgqkhztjManager();
	}
	
	/**
	 * <p>方法名称：initHztj</p>
	 * <p>方法描述：获取汇总名称分组信息</p>
	 * @author lijl
	 * @since 2010-05-07
	 */
	public void initHztj() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String hzsjq = StringEx.sNull(xmldo.getItemValue("HZSJQ"));
			String hzsjz = StringEx.sNull(xmldo.getItemValue("HZSJZ"));
			String hzmc = StringEx.sNull(xmldo.getItemValue("HZMC"));
			String hzmcList = mgr.groupByHzmc();
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("HZSJQ", hzsjq);
			xsBuf.append("HZSJZ", hzsjz);
			xsBuf.append("HZMC", hzmc);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("HZMCLIST", hzmcList);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "获取汇总名称分组信息成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("获取汇总名称分组信息失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：sumQhbgqk()，查询行政区划发布表</p>
	 * <p>方法说明：查询行政区划发布表的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param 无
	 * @Date 2010-06-11
	 * @author lijl
	 * @return 无
	 * @exception 无
	 */
	public void sumQhbgqk() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String hzsjq = StringEx.sNull(xmldo.getItemValue("HZSJQ"));
			String hzsjz = StringEx.sNull(xmldo.getItemValue("HZSJZ"));
			String hztj = StringEx.sNull(xmldo.getItemValue("HZTJ"));
			String hzmc = StringEx.sNull(xmldo.getItemValue("HZMC"));
			if(hztj.equals("1")){
				mgr.queryHzbByQhjb(hzsjq, hzsjz, hzmc);
			}else if(hztj.equals("2")){
				mgr.queryHzbByBglx(hzsjq, hzsjz, hzmc);
			}else if(hztj.equals("3")){
				mgr.queryHzbByJbLx(hzsjq, hzsjz, hzmc);
			}
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("HZSJQ", hzsjq);
			xsBuf.append("HZSJZ", hzsjz);
			xsBuf.append("HZMC", hzmc);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "汇总统计成功!",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("汇总统计失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：deleteQhbgqk()/p>
	 * <p>方法说明：删除汇总信息。</p>
	 * @param 无
	 * @Date 2010-06-12
	 * @author lijl
	 * @return 无
	 * @exception 无
	 */
	public void deleteQhbgqk() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String hzmc = StringEx.sNull(xmldo.getItemValue("HZMC"));
			mgr.deleteHzbByHzmc(hzmc);
			operLogSuccess(CommonConstants.ACTION_DELETE,"删除汇总信息成功！");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "删除汇总信息成功!","");
		} catch (Exception e) {
			LogManager.getLogger().error("删除汇总信息失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			operLogError(CommonConstants.ACTION_DELETE,"删除汇总信息失败！");
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
}
