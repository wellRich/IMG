package com.padis.business.xzqhwh.zxbg.bgdzgl.jlbgdzb;

import com.padis.business.common.data.xzqh.D_xzqh_bgsqd;
import com.padis.business.xzqhwh.common.Common;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.business.common.sequence.IGeneralSequence;
import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;
import ctais.services.sequence.SequenceManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;


/**
 * 创建变更申请单
 * <p>Title: SqdwhManager.java </p>
 * <p>Description:<类的功能描述> </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-9-22
 * @author pengld
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */

public class JlbgdzbManager {

	/**
	 * <p>查询指定区划的变更申请单：</p>
	 * <p>方法描述：</p>
	 * @param qxjg_dm
	 * @param xmldo
	 * @param args
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-22
	 */
	public String querySqb(String xzqh_dm, XMLDataObject xmldo ,long[] args) throws Exception {	
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "8" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("SELECT SQDXH,SQDMC,SQDZT_DM AS SQDZTDM,SQDZT_DM,BZ,SPYJ,SBXZQH_DM||'000000000' AS SBXZQH_MC,SBXZQH_DM,LRSJ FROM XZQH_BGSQD WHERE SBXZQH_DM = '");
		queryBuffer.append(xzqh_dm.substring(0,6));
		queryBuffer.append("' ORDER BY LRSJ DESC");
		DataWindow dw = DataWindow.dynamicCreate(queryBuffer.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rowCount = dw.getRowCount(queryBuffer.toString());
		long ps = Long.parseLong(pageSize);   //一页中包括多少条数据，进行分页准备
		long pageCount = dw.getPageCount(ps);  //一共多少页
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex),pageCount, queryBuffer.toString());
		args[0] = pageCount ; 
		args[1] = rowCount ; 
		String resultXml = "";
		if (cnt > 0){
			Dmmc.convDmmc(dw,"SQDZT_DM", "V_DM_XZQH_SQDZT");
			Dmmc.convDmmc(dw, "SBXZQH_MC","V_DM_XZQH");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	 /**
	  * <p>方法名称：</p>
	  * <p>方法描述：</p>
	  * @param xdo
	  * @param czry_dm
	  * @param jg_dm
	  * @throws Exception
	  * @author pengld
	  * @since 2009-9-22
	  */
	public void addSqd(XMLDataObject xdo,String czry_dm,String jg_dm) throws Exception {
		IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();
		String xzqh_dm = jgService.getXzqhDmByJg(jg_dm);
		D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
		d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		//使用上报行政区划代码与状态查找变更申请单，如果存在符合条件的申请单，则不创建
		d_xzqh_bgsqd.setFilter("SQDZT_DM < "+Common.XZQH_SQDZT_GJYSH+" AND SBXZQH_DM = '"+xzqh_dm.substring(0,6)+"'");
		long rows = d_xzqh_bgsqd.retrieve();
		if(rows <= 0L)
		{
			d_xzqh_bgsqd.insert(-1);
			IGeneralSequence iseq = (IGeneralSequence) SequenceManager.getSequenceFactory();
			d_xzqh_bgsqd.setItemString(0, "SQDXH", iseq.get("SEQ_XZQH_BGSQD_XL"));//序号
			d_xzqh_bgsqd.setItemString(0, "SQDMC",StringEx.sNull(xdo.getItemValue("SQDMC")).trim() );//名称
			d_xzqh_bgsqd.setItemString(0, "BZ",StringEx.sNull(xdo.getItemValue("BZ")).trim() );//备注
			d_xzqh_bgsqd.setItemString(0, "SQDZT_DM", Common.XZQH_SQDZT_WTJ);//状态
			d_xzqh_bgsqd.setItemString(0, "LRR_DM",czry_dm );//录入人代码
			d_xzqh_bgsqd.setItemString(0, "LRSJ", XtDate.getDBTime());//录入时间
			d_xzqh_bgsqd.setItemString(0, "LRJG_DM", jg_dm);//录入机构代码
			d_xzqh_bgsqd.setItemString(0, "SBXZQH_DM",xzqh_dm.substring(0,6) );//上报的行政区划的区划代码
			d_xzqh_bgsqd.setTransObject(new UserTransaction());
			d_xzqh_bgsqd.update(true);
		}
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>查询未发布以前的数据条数</p>
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-25
	 */
	
	public String querySqdzt(String xzqh_dm) throws Exception
	{
		D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
		d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		d_xzqh_bgsqd.setFilter("SBXZQH_DM ='"+xzqh_dm.substring(0,6)+"' AND SQDZT_DM <"+Common.XZQH_SQDZT_YFB);
		long rows = d_xzqh_bgsqd.retrieve();
		if(rows >0L)
		{
			return String.valueOf(rows);
		}else
		{
			return "0";
		}
	}
}
