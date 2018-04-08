package com.padis.business.xzqhwh.zxbg.gjsh;

import com.padis.business.common.data.xzqh.D_xzqh_bgsqd;
import com.padis.business.xzqhwh.common.Common;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.XtSvc;
import com.padis.common.xtservice.connection.ConnConfig;
import com.padis.common.xzqhservice.IXzqhService;
import com.padis.common.xzqhservice.XzqhInterfaceFactory;

import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;


/**
 * <p>Title: GjshManager </p>
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

public class GjshManager {

	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-20
	 */
	public String queryYtj() throws Exception {	
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUBSTR(SBXZQH_DM,1,2)||'0000000000000' AS XZQH_DM,SUBSTR(SBXZQH_DM,1,2)||'0000000000000' AS XZQH_MC,decode(SUM(CASE WHEN SQDZT_DM<");
		sql.append(Common.XZQH_SQDZT_YQR);
		sql.append(" THEN 1 ELSE 0 END),0,'已确认','未确认') FLAG,'' YSB,'' SSB FROM XZQH_BGSQD WHERE SQDZT_DM < ");
		sql.append(Common.XZQH_SQDZT_GJYSH);
		sql.append(" GROUP BY SUBSTR(SBXZQH_DM,1,2) order by SUBSTR(SBXZQH_DM,1,2)");
		
		//System.out.println("wwwwwwwwwwww==="+sql.toString());
		DataWindow dw = DataWindow.dynamicCreate(sql.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rows = dw.retrieve();
		String resultXml = "";
		if (rows > 0L){
			for(int i=0; i<rows; i++){
				String xzqh_dm = String.valueOf(dw.getItemAny(i, "XZQH_DM"));
				StringBuffer sql1 = new StringBuffer();
//				sql1.append("select count(*) YSB from v_dm_xzqh d where d.JCDM = '3'")
//				.append(" and xzqh_dm like '").append(xzqh_dm.substring(0, 2)).append("%'")
//				.append(" and d.XYBZ='Y' and d.YXBZ='Y' and d.XZQHLX_DM not in ('51')");
				
				
				sql1.append("SELECT count(*) YSB FROM (SELECT SUBSTR(XZQH_DM, 1, 6) XZQH_DM, XZQH_QC FROM V_DM_XZQH_YLSJ ")
				.append("WHERE JCDM = '3' AND XZQHLX_DM NOT IN ('41', '42', '43', '51', '52')") 
				.append(" and xzqh_dm like '").append(xzqh_dm.substring(0, 2)).append("%'")
				.append(" INTERSECT SELECT SUBSTR(XZQH_DM, 1, 6) XZQH_DM, XZQH_QC ")
				.append("FROM V_DM_XZQH WHERE JCDM = '3'")
				.append("AND XZQHLX_DM NOT IN ('41', '42', '43', '51', '52')")
				.append(" and xzqh_dm like '").append(xzqh_dm.substring(0, 2)).append("%'")
				.append(") XZQH WHERE XZQH.XZQH_DM NOT IN ")
				.append("(SELECT SBXZQH_DM FROM XZQH_BGSQD WHERE SQDZT_DM < 40)");
//				.append(" ORDER BY XZQH.XZQH_DM");
          
//				System.out.println("@@@@@@@@@@@@@@@@@@@@==="+sql1.toString());
           
           
				
				
				//select * from v_dm_xzqh d where d.JCDM='3' and d.XZQH_DM like '40%' and d.XYBZ='Y' and d.YXBZ='Y'
				DataWindow dw1 = DataWindow.dynamicCreate(sql1.toString());
				dw1.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
				long rows1 = dw1.retrieve();
				if(rows1>0){
					dw.setItemAny(i, "YSB",String.valueOf(dw1.getItemAny(0, "YSB")));
				}
				StringBuffer sql2 = new StringBuffer();
				sql2.append("select count(*) SSB from xzqh_bgsqd s where substr(s.sbxzqh_dm, 1, 2)='");
				sql2.append(xzqh_dm.substring(0, 2)).append("' and s.SQDZT_DM<=40");
				DataWindow dw2 = DataWindow.dynamicCreate(sql2.toString());
				dw2.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
				long rows2 = dw2.retrieve();
				if(rows2>0){
					dw.setItemAny(i,"SSB",String.valueOf(dw2.getItemAny(0, "SSB")));
				}
			}
			Dmmc.convDmmc(dw,"XZQH_MC","V_DM_XZQH");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}

	public String querySqd(XMLDataObject xmldo,long[] args) throws Exception {	
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "8" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		String sqdmc = StringEx.sNull(xmldo.getItemValue("SQDMC"));
		String sqdzt_dm =StringEx.sNull(xmldo.getItemValue("SQDZT_DM"));
		String xzqh_dm = StringEx.sNull(xmldo.getItemValue("XZQH_DM"));
		
		StringBuffer queryBuffer = new StringBuffer("SELECT SQDXH,SQDMC,SQDZT_DM AS SQDZTDM,SQDZT_DM,BZ,SPYJ,SBXZQH_DM,SBXZQH_DM||'000000000'  AS SBXZQH_MC,SBXZQH_DM,LRSJ FROM XZQH_BGSQD WHERE  ");
		queryBuffer.append("SQDMC LIKE '%"+sqdmc+"%'");
		if(xzqh_dm.equals("000000000000000")){
			queryBuffer.append(" AND SBXZQH_DM = '000000'");
		}else{
			queryBuffer.append(" AND SBXZQH_DM LIKE '"+Common.getJbdm(xzqh_dm)+"%'");
		}
		if(!sqdzt_dm.equals(""))
		{
			queryBuffer.append(" AND SQDZT_DM = '"+sqdzt_dm+"'");			
		}else
		{
			queryBuffer.append(" AND SQDZT_DM <="+Common.XZQH_SQDZT_GJYSH);
		}

		queryBuffer.append(" ORDER BY SQDZT_DM");
		
		//System.out.println("!!!!!!!!!!!!!!!!!!!sqdSQL="+queryBuffer.toString());
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
			Dmmc.convDmmc(dw,"SBXZQH_MC","V_DM_XZQH");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}

	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param sqdxh
	 * @param flag
	 * @param sqyj
	 * @param jg_dm
	 * @param czry_dm
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-25
	 */
	
	public void gjsh(String xzqh_dm,String qxjg_dm,String czry_dm) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE XZQH_BGSQD SET SQDZT_DM ='"+Common.XZQH_SQDZT_GJYSH+"'");
		sql.append(",XGR_DM = '").append(czry_dm).append("'");
		sql.append(",XGJG_DM ='").append(qxjg_dm).append("'");
		sql.append(",XGSJ = SYSDATE");
		sql.append(" WHERE SQDZT_DM ='"+Common.XZQH_SQDZT_YQR+"'");
		if(xzqh_dm!=null&&!xzqh_dm.equals("")){
			String[] xzqhArray = xzqh_dm.split(",");
			if(xzqhArray.length>0){
				sql.append(" AND (");
				for(int i=0; i<xzqhArray.length; i++){				
					sql.append("SBXZQH_DM LIKE '").append(xzqhArray[i].substring(0, 2)).append("%'");
					if(i<xzqhArray.length-1){
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}
		DataWindow dw = DataWindow.dynamicCreate(sql.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		dw.setTransObject(new UserTransaction());
		dw.update(true);
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param sqdxh
	 * @param flag
	 * @param sqyj
	 * @param jg_dm
	 * @param czry_dm
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-25
	 */
	
	public void back(String sqdxh,String spyj,String jg_dm,String czry_dm) throws Exception
	{
		if(sqdxh==null||sqdxh.length()<1){
			return;
		}
		UserTransaction ut = new UserTransaction();
		String[] sqdArr = sqdxh.split(",");
		if(sqdArr.length>0){
			try{
				ut.begin();
				for(int j=0; j<sqdArr.length;j++){			
					D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
					d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
					d_xzqh_bgsqd.setFilter("SQDXH = '"+sqdArr[j]+"'");
					long rows = d_xzqh_bgsqd.retrieve();		
					for (int i=0;i<rows;i++)
					{
						d_xzqh_bgsqd.setItemString(i,"SQDZT_DM",Common.XZQH_SQDZT_SHBTG);
						d_xzqh_bgsqd.setItemString(i,"SPYJ", StringEx.sNull(spyj).trim());
						d_xzqh_bgsqd.setItemString(i,"XGJG_DM",jg_dm);
						d_xzqh_bgsqd.setItemString(i,"XGR_DM",czry_dm);
						d_xzqh_bgsqd.setItemString(i,"XGSJ",XtDate.getDBTime());
						d_xzqh_bgsqd.setTransObject(ut);
						d_xzqh_bgsqd.update(false);
					}
				}
				ut.commit();
			}catch(Exception e){
				ut.rollback();
				throw e;
			}
		}
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param xzqh_dm
	 * 			行政区划代码
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-25
	 */
	
	public String querySqdzt(String xzqh_dm) throws Exception
	{
		D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
		d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		StringBuffer sql = new StringBuffer("");
		sql.append("SQDZT_DM <").append(Common.XZQH_SQDZT_YQR);
		if(xzqh_dm!=null&&!xzqh_dm.equals("")){
			String[] xzqhArray = xzqh_dm.split(",");
			if(xzqhArray.length>0){
				sql.append(" AND (");
				for(int i=0; i<xzqhArray.length; i++){				
					sql.append("SBXZQH_DM LIKE '").append(xzqhArray[i].substring(0, 2)).append("%'");
					if(i<xzqhArray.length-1){
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}
		d_xzqh_bgsqd.setFilter(sql.toString());
		long rows = d_xzqh_bgsqd.retrieve();
		if(rows >0L)
		{
			String sbxzqh_dm = StringEx.sNull(d_xzqh_bgsqd.getItemAny(0, "SBXZQH_DM"));
			String xzqh_mc = "";
			if(!sbxzqh_dm.equals("")){
				String sjxzqh_dm  = sbxzqh_dm.substring(0, 2)+"0000000000000";
				IXzqhService xzqhService = XzqhInterfaceFactory.getInstance().getInterfaceImp();
				xzqh_mc = xzqhService.getXzqhName(sjxzqh_dm);
			}
			return xzqh_mc+"下存在未确认的申请单，请先确认！";
		}else
		{
			return "通过";
		}
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-12
	 */
	public String initSqdzt() throws Exception
	{
		String sql = "SELECT SQDZT_DM,SQDZT_MC FROM V_DM_XZQH_SQDZT WHERE SQDZT_DM <40";
		DataWindow dw = DataWindow.dynamicCreate(sql);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rows= dw.retrieve();
		
		XMLDataObject xdo = dw.toXDO();
		//即使把下标设置为-1，还是插入到最后一行
		xdo.insert(0,"row", new String[]{"SQDZT_DM","SQDZT_MC"});
		
		xdo.setItemAny(rows, "SQDZT_DM", "");
		xdo.setItemAny(rows, "SQDZT_MC", "========请选择========");
		return xdo.toXML().toString();
	}
	
	
	/**
	 * <p>方法名称：bgsqqr</p>
	 * <p>方法描述：变更申请单确认操作，更改申请单状态</p>
	 * @param xzqh_dm
	 * @param czry_dm
	 * @param jg_dm
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-12
	 */
	
	public void bgsqqr(String sqdxh) throws Exception
	{
		if(sqdxh==null||sqdxh.length()<1){
			return;
		}
		UserTransaction ut = new UserTransaction();
		String[] sqdArr = sqdxh.split(",");
		if(sqdArr.length>0){
			try{
				ut.begin();
				for(int j=0; j<sqdArr.length;j++){			
					D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
					d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
					d_xzqh_bgsqd.setFilter("SQDXH = '"+sqdArr[j]+"'");
					long rows = d_xzqh_bgsqd.retrieve();		
					for (int i=0;i<rows;i++)
					{
						d_xzqh_bgsqd.setItemString(i,"SQDZT_DM",Common.XZQH_SQDZT_YQR);
						d_xzqh_bgsqd.setItemString(i,"XGSJ",XtDate.getDBTime());
						d_xzqh_bgsqd.setTransObject(ut);
						d_xzqh_bgsqd.update(false);
					}
				}
				ut.commit();
			}catch(Exception e){
				ut.rollback();
				throw e;
			}
		}
				
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-12
	 */
	public String queryWtj() throws Exception
	{
//		获取系统参数:不提交申请的的省份区划
		String xzqh = StringEx.sNull(XtSvc.getXtcs("97002", null, null));
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  XZQH_DM,XZQH_MC FROM V_DM_XZQH ");
		sql.append(" WHERE XZQH_DM NOT IN (SELECT DISTINCT(SUBSTR(SBXZQH_DM,1,2))||'0000000000000' FROM XZQH_BGSQD WHERE SQDZT_DM<"+Common.XZQH_SQDZT_YFB+") AND JCDM ='1' ");
		if(!xzqh.equals(""))
		{
			sql.append(" AND SUBSTR(XZQH_DM,1,2) NOT IN ("+xzqh+") ");
		}
		sql.append(" ORDER BY XZQH_DM ");
		DataWindow dw = DataWindow.dynamicCreate(sql.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rows = dw.retrieve();
		String resultXml = "";
		if (rows > 0){	
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	


}
