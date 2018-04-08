package com.padis.business.xzqhwh.sjjzbg.xzqhsjsc;

import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.common.constants.CommonConstants;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;

/**
 * <p>Title: XzqhsjscService</P>
 * <p>Description: 省级上报数据的service类</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @auther lijl
 * @version 1.0
 * @since 2009-09-10
 * 修改人：
 * 修改时间：
 * 修改内容：
 * 修改版本号：
 */
public class XzqhsjscService extends XtwhBaseService{
	XzqhsjscManager mgr;
	/**
	 * 构造函数
	 */
	public XzqhsjscService() {
		mgr = new XzqhsjscManager();
	}
	
	/**
	 * <p>方法名称： initSjsc()，初始化行政区划页面</p>
	 * <p>方法说明：初始化行政区划页面的方法。</p>
	 * @param 无
	 * @Date 2009-06-17
	 * @author 李靖亮
	 * @return 无
	 */
	public void initSjsc() {
		try {
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(swjg_dm));
			if(xzqh_dm.length()==15){
				xzqh_dm = xzqh_dm.substring(0, 6);
			}
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("XZQH_DM",xzqh_dm);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"跳转到初始化页面成功！",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("初始化页面失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS, 2006, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：uploadSjxzqhsj()，上传省级变更数据</p>
	 * <p>方法说明：查询行政区划发布表的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param 无
	 * @Date 2009-09-10
	 * @author lijl
	 * @return 无
	 * @exception 无
	 */
	public void uploadSjbgsj() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String czry_dm = uwa.getCzry_dm();
			String qxjg_dm = uwa.getQx_swjg_dm();
			mgr.uploadXzqhjzbgzip(xmldo,czry_dm,qxjg_dm);
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询申请表信息成功!", "");
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("查询申请表信息失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	
	/**
	 * <p>方法名称：queryXzqhfb()，查询行政区划发布表</p>
	 * <p>方法说明：查询行政区划发布表的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param 无
	 * @Date 2009-09-10
	 * @author lijl
	 * @return 无
	 * @exception 无
	 */
	public void queryXzqhjzbgzip() {
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(swjg_dm));
			String sjxzqh_dm = "";
			if(!xzqh_dm.equals("")){
				sjxzqh_dm = xzqh_dm.substring(0, 2);
			}
			long[] args = new long[]{-1,0};
			String zipList = mgr.queryXzqhjzbgzip(xmldo,args,sjxzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("SZXZQH_DM", sjxzqh_dm);
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
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询申请表信息成功!", xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询申请表信息失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：queryXzqhfb()，查询行政区划发布表</p>
	 * <p>方法说明：查询行政区划发布表的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param 无
	 * @Date 2009-09-10
	 * @author lijl
	 * @return 无
	 * @exception 无
	 */
	public void deleteZip(){
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String zipxhs = xmldo.getItemValue("ZIPXH");
			mgr.deleteZip(zipxhs);
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询申请表信息成功!", "");
		} catch (Exception e) {
			LogManager.getLogger().error("查询申请表信息失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：queryXzqhfb()，查询行政区划发布表</p>
	 * <p>方法说明：查询行政区划发布表的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param 无
	 * @Date 2009-09-10
	 * @author lijl
	 * @return 无
	 * @exception 无
	 */
	public void isUpload(){
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String xzqh_dm = xmldo.getItemValue("XZQH_DM");
			String fileName = xmldo.getItemValue("WJM");
			String flag = mgr.isUpload(xzqh_dm,fileName);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("FLAG", flag);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
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
	 * <p>方法名称：getZipzt()，获取ZIP文件状态</p>
	 * <p>方法说明：查询行政区划发布表的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param 无
	 * @Date 2009-09-10
	 * @author lijl
	 * @return 无
	 * @exception 无
	 */
	public void getZipzt(){
		try {
			XMLDataObject xmldo = uwa.getArgXml();
			String zipxh = xmldo.getItemValue("ZIPXH");
			String msg = mgr.getZipzt(zipxh);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("MSG", msg);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
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
}
