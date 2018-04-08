package com.padis.business.xzqhwh.sjjzbg.cysjbj;


import com.padis.business.common.data.xzqh.D_xzqh_jzbgzip;
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

public class CysjbjManager {
	
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
	public String queryZip(XMLDataObject xdo , long[] args, String sjxzqh_dm) throws Exception {
		String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM")); //每页的数据条数
		String rq = StringEx.sNull(xdo.getItemValue("RQ")); //日期
	
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer sql= new StringBuffer("");	
		StringBuffer queryBuffer = new StringBuffer("SELECT ZIPXH,XZQH_DM,RQ,JZBGZT_DM,WJM,LRSJ,BZ,JZBGZT_DM AS JZBGZT FROM XZQH_JZBGZIP");
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
		queryBuffer.append(" ORDER BY RQ DESC");
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
	public String queryDif(XMLDataObject xdo ,long[] args,String jg_dm) throws Exception {

		String zipxh = StringEx.sNull(xdo.getItemValue("ZIPXH")); 
//		1 代码一致名称不一致,2 完全一样,3 省级存在PADIS不存在,4 省级不存在PADIS存在
		String cylx = StringEx.sNull(xdo.getItemValue("CYLX"));
		if(cylx.equals(""))
		{
			cylx ="1";
		}
		String xzqh = "";
		
		D_xzqh_jzbgzip d_xzqh_jzbgzip=new D_xzqh_jzbgzip();
		d_xzqh_jzbgzip.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		d_xzqh_jzbgzip.setFilter(" ZIPXH ='"+zipxh+"'");
		long rows = d_xzqh_jzbgzip.retrieve();
		if(rows >0L)
		{
			xzqh=StringEx.sNull(d_xzqh_jzbgzip.getItemAny(0,"XZQH_DM"));
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.xzqh_dm as XZQHDM,a.xzqh_mc as XZQHMC,b.xzqh_dm as P_XZQHDM,b.xzqh_mc as P_XZQHMC from ");
		sql.append(" (select xzqh_dm,xzqh_mc from xzqh_jzbgsj_temp where zipxh='"+zipxh+"') a ");
		sql.append(",");
		sql.append(" (select xzqh_dm,xzqh_mc from v_dm_xzqh_ylsj where xzqh_dm like '"+Common.getJbdm(xzqh+"000000000")+"%') b ");
		
		if(cylx.equals("1"))
		{
			sql.append(" where a.xzqh_dm = b.xzqh_dm and a.xzqh_mc <> b.xzqh_mc");
			
		}else if(cylx.equals("2"))
		{
			sql.append(" where a.xzqh_dm = b.xzqh_dm and a.xzqh_mc = b.xzqh_mc");
		}else if(cylx.equals("3"))
		{
			sql.append(" where a.xzqh_dm = b.xzqh_dm(+) and b.xzqh_dm is null");
		}else if(cylx.equals("4"))
		{
			sql.append(" where a.xzqh_dm(+) = b.xzqh_dm and a.xzqh_dm is null");
		}
		sql.append(" order by a.xzqh_dm ");
		
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "20" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
	
		DataWindow dw = DataWindow.dynamicCreate(sql.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rowCount = dw.getRowCount(sql.toString());
		long ps = Long.parseLong(pageSize);   //一页中包括多少条数据，进行分页准备
		long pageCount = dw.getPageCount(ps);  //一共多少页
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex),pageCount, sql.toString());
		args[0] = pageCount ; 
		args[1] = rowCount ; 
		String resultXml = "";
		if (cnt > 0){
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
}
