package com.padis.business.xzqhwh.zxbg.sqdsc;

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
 * <p>Title: SqdwhService.java </p>
 * <p>Description:变更申请单维护的Service类 </p>
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
public class SqdscService extends XtwhBaseService {
	
	/**
	 * 
	 */
	SqdscManager mgr;

	/**
	 * 无参构造方法
	 *
	 */
	public SqdscService() {
		mgr = new SqdscManager();
	}

	/**
	 * <p>方法名称：initSqb</p>
	 * <p>方法描述：初始化数据</p>
	 * @author pengld
	 * @since 2009-9-18
	 */
	public void initSqd() {
		try {
			IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();
			XMLDataObject xmldo = uwa.getArgXml();
			String qxjg_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = StringEx.sNull(jgService.getXzqhDmByJg(qxjg_dm));
			
			long[] args = new long[]{-1,0};
			String sqdList = mgr.querySqb(xzqh_dm,xmldo,args);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("PAGECOUNT", String.valueOf(args[0]));
			xsBuf.append("TOTALROWCOUNT",String.valueOf(args[1]));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("SQDLIST", sqdList);
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
	 * <p>方法名称：deleteSqd</p>
	 * <p>方法描述：删除申请单</p>
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-22
	 */
	public void deleteSqd() throws Exception {
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			xmldo.rootScrollTo("map");
			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
			mgr.deleteSqd(sqdxh);
			operLogSuccess(CommonConstants.ACTION_DELETE,"删除申请单成功!");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "删除申请单成功！", "");
		} catch (Exception e) {
			LogManager.getLogger().error("删除申请单失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			operLogError(CommonConstants.ACTION_DELETE,"删除申请单失败！");
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2008, e.getMessage(),"");
		}
	}
	
	/**
	 * <p>方法名称：getCount</p>
	 * <p>方法描述：获取申请单下所有的明细数据总数</p>
	 * @author lijl
	 * @since 2010-01-28
	 */
	public void getCount() {
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			xmldo.rootScrollTo("map");
			String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
			String count = mgr.getCount(sqdxh);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("COUNT", String.valueOf(count));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "查询申请单成功！", xsBuf.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("查询申请单失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTNMSG_SUCCESS, 2009, e.getMessage(),"");
		}
	}

}
