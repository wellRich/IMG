package com.padis.business.xzqhwh.sjjzbg.bgdzcx;


import com.padis.business.xzqhwh.common.Common;

import com.padis.common.dmservice.Dmmc;


import com.padis.common.xtservice.connection.ConnConfig;


import ctais.services.data.DataWindow;

import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p> 
 * Description: 变更日志服务类（BgrzglService）的manager,提供BgrzglService底层处理的方法
 * </p> 
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @since 2009-07-10
 * @author 李靖亮
 * @version 1.0
 */

public class BgdzcxManager {
	
	/**
	 * <p>方法名称：queryXzqhjzbgzip()，省级行政区划集中变更文件记录</p>
	 * <p>方法说明：省级行政区划集中变更文件记录的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param xmldo		查询条件
	 * @param args		分页查询参数
	 * @since 2009-08-14
	 * @author 李靖亮
	 * String 符合条件的电子邮件列表
	 * @throws Exception
	 */
	public String queryZip(XMLDataObject xdo ,long[] args,String sjxzqh_dm) throws Exception {
		String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM")); 
		String rq = StringEx.sNull(xdo.getItemValue("RQ")); //日期
		rq = rq.replaceAll("-", "");
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("SELECT ZIPXH,XZQH_DM,RQ,JZBGZT_DM,WJM,LRSJ,BZ FROM XZQH_JZBGZIP");
		StringBuffer sql= new StringBuffer("");	
		if(!sjxzqh_dm.equals("00")&&!sjxzqh_dm.equals("")){
			queryBuffer.append(" WHERE ").append(" substr(XZQH_DM,0,2)='").append(sjxzqh_dm).append("'");
		}
		if(!xzqh_dm.equals("")){
			sql.append(" AND XZQH_DM='").append(xzqh_dm).append("'");
		}
		if(!rq.equals("")){
			sql.append(" AND RQ='").append(rq.replaceAll("-","")).append("'");
		}
		if(sql.length()>0){
			if(queryBuffer.indexOf("WHERE")<0){
				queryBuffer.append(" WHERE ").append(sql.substring(5));
			}else{
				queryBuffer.append(sql.toString());
			}
		}
		queryBuffer.append(" ORDER BY RQ DESC ");
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
			Dmmc.convDmmc(dw,"JZBGZT_DM", "V_DM_XZQH_JZBGZT");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>方法名称：queryXzqhjzbgzip()，省级行政区划集中变更文件记录</p>
	 * <p>方法说明：省级行政区划集中变更文件记录的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param xdo		查询条件
	 * @param args		分页查询参数
	 * @since 2009-08-14
	 * @author 李靖亮
	 * String 符合条件的电子邮件列表
	 * @throws Exception
	 */
	public String queryDzb(XMLDataObject xdo ,long[] args,String jg_dm) throws Exception {
		String zipxh = StringEx.sNull(xdo.getItemValue("ZIPXH")); 
		String bgxzqh_dm =StringEx.sNull(xdo.getItemValue("BGXZQH_DM"));
		String cwsjbz = StringEx.sNull(xdo.getItemValue("CWSJBZ"));
		String bglx_dm = StringEx.sNull(xdo.getItemValue("BGLX_DM"));
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer sb = new StringBuffer("SELECT * FROM (");
		StringBuffer queryBuffer = new StringBuffer("SELECT YSXZQH_DM,YSXZQH_MC,BGLX_DM,MBXZQH_DM,MBXZQH_MC,DECODE(CWSJBZ,'N','正确','Y','错误') AS CWSJBZ,BZ,CWXX,PXH FROM XZQH_JZBGDZB_TEMP WHERE");
		queryBuffer.append(" ZIPXH='").append(zipxh).append("'");
		if(!cwsjbz.equals(""))
		{
			queryBuffer.append(" AND CWSJBZ ='"+cwsjbz+"' ");
		}
		if(bglx_dm.equals(Common.ADD))
		{
			queryBuffer.append(" AND BGLX_DM ='"+Common.ADD+"' ");
			if(!bgxzqh_dm.equals(""))
			{
				queryBuffer.append(" AND MBXZQH_DM LIKE '"+Common.getJbdm(bgxzqh_dm)+"%'");
			}
			
		}
		else
		{
			if(!bglx_dm.equals(""))
			{
				queryBuffer.append(" AND BGLX_DM ='"+bglx_dm+"' ");
			}
			
			if(!bgxzqh_dm.equals(""))
			{
				queryBuffer.append(" AND YSXZQH_DM LIKE '"+Common.getJbdm(bgxzqh_dm)+"%'");
			}
			
		}
		
		StringBuffer queryBuffer1 = new StringBuffer("SELECT YSXZQH_DM,YSXZQH_MC,BGLX_DM,MBXZQH_DM,MBXZQH_MC,DECODE(CWSJBZ,'N','正确','Y','错误') AS CWSJBZ,BZ,CWXX,PXH FROM XZQH_JZBGKLJDZB_TEMP WHERE");
		queryBuffer1.append(" ZIPXH='").append(zipxh).append("'");
		if(!cwsjbz.equals(""))
		{
			queryBuffer1.append(" AND CWSJBZ ='"+cwsjbz+"' ");
		}
		if(bglx_dm.equals(Common.ADD))
		{
			queryBuffer1.append(" AND BGLX_DM ='"+Common.ADD+"' ");
			if(!bgxzqh_dm.equals(""))
			{
				queryBuffer1.append(" AND MBXZQH_DM LIKE '"+Common.getJbdm(bgxzqh_dm)+"%'");
			}
			
		}
		else
		{
			if(!bglx_dm.equals(""))
			{
				queryBuffer1.append(" AND BGLX_DM ='"+bglx_dm+"' ");
			}
			
			if(!bgxzqh_dm.equals(""))
			{
				queryBuffer1.append(" AND YSXZQH_DM LIKE '"+Common.getJbdm(bgxzqh_dm)+"%'");
			}
			
		}

		sb.append(queryBuffer.toString());
		sb.append(" union all ");
		sb.append(queryBuffer1.toString()).append(") A");
		sb.append(" ORDER BY A.PXH ");

		DataWindow dw = DataWindow.dynamicCreate(sb.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rowCount = dw.getRowCount(sb.toString());
		long ps = Long.parseLong(pageSize);   //一页中包括多少条数据，进行分页准备
		long pageCount = dw.getPageCount(ps);  //一共多少页
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex),pageCount, sb.toString());
		args[0] = pageCount ; 
		args[1] = rowCount ; 
		String resultXml = "";
		if (cnt > 0){
			Dmmc.convDmmc(dw,"BGLX_DM", "V_DM_XZQH_BGLX");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
}
