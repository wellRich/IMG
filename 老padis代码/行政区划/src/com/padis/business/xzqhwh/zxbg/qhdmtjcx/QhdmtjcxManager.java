package com.padis.business.xzqhwh.zxbg.qhdmtjcx;

import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;


/**
 * <p>Title: QhdmtjcxManager.java </p>
 * <p>Description:<类的功能描述> </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2010-07-22
 * @author lijl
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */

public class QhdmtjcxManager {	
	/**
	 * <p>方法名称：queryXzqh</p>
	 * <p>方法描述：根据条件查询行政区划信息</p>
	 * @param xdo
	 * @param args
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2010-07-22
	 */
	public String queryXzqh(XMLDataObject xdo,long[] args) throws Exception {
		String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM"));
		String xzqh_mc = StringEx.sNull(xdo.getItemValue("XZQH_MC"));
		String jcdm = StringEx.sNull(xdo.getItemValue("JCDM"));
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		
		StringBuffer sql = new StringBuffer("SELECT XZQH_DM,XZQH_MC,XZQH_QC,DECODE(JCDM,'0','国家级','1','省级','2','市级','3','县级','4','乡级','5','村级','组级') JCMC,SJ_XZQH_DM SJ_XZQH_MC,SJ_XZQH_DM FROM V_DM_XZQH WHERE ");
		if(!xzqh_dm.equals("")){
			sql.append(" XZQH_DM like '").append(xzqh_dm.trim()).append("%'");
		}
		if(!xzqh_mc.equals("")){
			sql.append(" XZQH_MC like '%").append(xzqh_mc.trim()).append("%'");
		}
		if(!jcdm.equals("")){
			sql.append(" AND JCDM ='").append(jcdm).append("'");
		}
		sql.append(" ORDER BY XZQH_DM");
		DataWindow dw = DataWindow.dynamicCreate(sql.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rowCount = dw.getRowCount(sql.toString());
		long ps = Long.parseLong(pageSize);   //一页中包括多少条数据，进行分页准备
		long pageCount = dw.getPageCount(ps);  //一共多少页
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex),pageCount, sql.toString());
		args[0] = pageCount ; 
		args[1] = rowCount ; 

		if (cnt > 0){
			Dmmc.convDmmc(dw,"SJ_XZQH_MC","V_DM_XZQH");
			return dw.toXML().toString();
		}else {
			return "";
		}
	}
}
