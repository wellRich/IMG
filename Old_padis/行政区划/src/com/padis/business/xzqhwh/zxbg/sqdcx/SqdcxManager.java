/**
 * 
 */
package com.padis.business.xzqhwh.zxbg.sqdcx;

import com.padis.business.xzqhwh.common.Common;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.qxservice.IQxService;
import com.padis.common.qxservice.QxInterfaceFactory;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p>Title: SqdcxManager.java </p>
 * <p>Description:申请单查询的Manager类 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-10-13
 * @author pengld
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */
public class SqdcxManager {
	
	/**
	 * <p>方法名称：querySqd</p>
	 * <p>方法描述：根据相应条件查询申请单</p>
	 * @param xdo
	 * @param args
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-14
	 */
	public String querySqd(XMLDataObject xdo,long[] args,String xzqh_dm) throws Exception
	{
		String sqdmc = StringEx.sNull(xdo.getItemValue("SQDMC"));
		String sqdzt_dm = StringEx.sNull(xdo.getItemValue("SQDZT_DM"));
		String bgsjq = StringEx.sNull(xdo.getItemValue("BGSJQ"));
		String bgsjz = StringEx.sNull(xdo.getItemValue("BGSJZ"));
		String tzsm = StringEx.sNull(xdo.getItemValue("TZSM"));
		String bgxzqh_dm = StringEx.sNull(xdo.getItemValue("BGXZQH_DM"));
		String jb_dm ="";
		if(!bgxzqh_dm.equals(""))
		{
			jb_dm = Common.getJbdm(bgxzqh_dm);
			if(jb_dm.length()>6){
				jb_dm = jb_dm.substring(0, 6);
			}
		}
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "8" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("SELECT distinct SQD.SQDXH,SQDMC,SQDZT_DM AS SQDZTDM,SQDZT_DM,BZ,SPYJ,SBXZQH_DM,SBXZQH_DM||'000000000' AS SBXZQH_MC,SQD.LRSJ FROM XZQH_BGSQD SQD,XZQH_BGGROUP GRO WHERE ");
		queryBuffer.append(" SQD.SQDXH = GRO.SQDXH(+)");	
		if(!sqdmc.equals(""))
		{
			queryBuffer.append(" AND SQD.SQDMC LIKE '%"+sqdmc+"%' ");
		}
		if(!sqdzt_dm.equals(""))
		{
			queryBuffer.append(" AND SQD.SQDZT_DM = '"+sqdzt_dm+"'");
		}
		if(!tzsm.equals(""))
		{
			queryBuffer.append(" AND GRO.GROUPMC LIKE '%"+tzsm+"%'");
		}
		if(!bgsjq.equals(""))
		{
			queryBuffer.append(" AND SQD.LRSJ >= TO_DATE('"+bgsjq+"','YYYY-MM-DD') ");
		}
		if(!bgsjz.equals(""))
		{
			queryBuffer.append(" AND SQD.LRSJ < TO_DATE('"+bgsjz+"','YYYY-MM-DD') ");
		}
		
		String jbDm = Common.getJbdm(xzqh_dm);
		if(!xzqh_dm.equals("000000000000000")){
//			IQxService qxService= QxInterfaceFactory.getInstance().getInterfaceImp();
//			String qxjgCondition = StringEx.sNull(qxService.getQXCondition("SQD.LRJG_DM", qxjg_dm));
//			if(!qxjgCondition.equals("")){
				if(queryBuffer.indexOf("WHERE")<0){
					queryBuffer.append(" WHERE ");
				}else{
					queryBuffer.append(" AND SQD.LRJG_DM like '").append(jbDm).append("%'");
				}
//				queryBuffer.append(qxjgCondition);
//			}
		}
		queryBuffer.append(" ORDER BY SQD.LRSJ DESC");

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
			Dmmc.convDmmc(dw,"SBXZQH_MC","V_DM_XZQH");
			Dmmc.convDmmc(dw,"SQDZT_DM", "V_DM_XZQH_SQDZT");		
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>方法名称：queryMxb</p>
	 * <p>方法描述：查询申请单的明细</p>
	 * @param xdo
	 * @param args
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-14
	 */
	
	public String queryMxb(XMLDataObject xdo,long args []) throws Exception
	{
		String sqdxh = StringEx.sNull(xdo.getItemValue("SQDXH"));
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		String resultXml = "";
		StringBuffer sql = new StringBuffer(
				"SELECT A.GROUPXH,A.GROUPMC,B.YSXZQH_DM,B.YSXZQH_MC,B.BGLX_DM,B.MBXZQH_DM,B.MBXZQH_MC,B.CLZT_DM,B.CLJG,B.BZ,B.XGSJ FROM XZQH_BGGROUP A,XZQH_BGMXB B WHERE A.GROUPXH=B.GROUPXH AND A.SQDXH = '");
		sql.append(sqdxh);
		sql.append("' ORDER BY A.LRSJ ,B.PXH");	
		DataWindow dw = DataWindow.dynamicCreate(sql.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rowCount = dw.getRowCount(sql.toString());
		long ps = Long.parseLong(pageSize);   //一页中包括多少条数据，进行分页准备
		long pageCount = dw.getPageCount(ps);  //一共多少页
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex),pageCount, sql.toString());
		args[0] = pageCount ; 
		args[1] = rowCount ; 
		if(cnt>0){
			Dmmc.convDmmc(dw,"BGLX_DM", "V_DM_XZQH_BGLX");
			Dmmc.convDmmc(dw,"CLZT_DM", "V_DM_XZQH_CLZT");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param groupxh
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-22
	 */
	public String expSqd(String sqdxh) throws Exception{
		StringBuffer sql= new StringBuffer("SELECT A.GROUPXH,A.GROUPMC,B.YSXZQH_DM,B.YSXZQH_MC,B.BGLX_DM,B.MBXZQH_DM,B.MBXZQH_MC,B.BZ FROM XZQH_BGGROUP A,XZQH_BGMXB B WHERE A.GROUPXH = B.GROUPXH AND A.SQDXH = '");
		sql.append(sqdxh);
		sql.append("' ORDER BY A.LRSJ,B.PXH");
		DataWindow dw = DataWindow.dynamicCreate(sql.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rows = dw.retrieve();
		if(rows >0)
		{
			Dmmc.convDmmc(dw,"BGLX_DM", "V_DM_XZQH_BGLX");
			return dw.toXML().toString();
		}
		return "";
	}


}
