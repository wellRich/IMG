package com.padis.business.xzqhwh.zxbg.bgsqqr;

import com.padis.business.common.data.xzqh.D_xzqh_bgsqd;
import com.padis.business.xzqhwh.common.Common;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.XtSvc;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;

import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;


/**
 * 申请单确认
 * <p>Title: BgsqqrManager.java </p>
 * <p>Description:申请单确认的Manager类 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-10-12
 * @author pengld
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */

public class BgsqqrManager {

	/**
	 * <p>方法名称：querySqb</p>
	 * <p>方法描述：查询下级申请单</p>
	 * @param xzqh_dm
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-24
	 */
	public String querySqb(String xzqh_dm) throws Exception {	
		
		xzqh_dm = Common.getJbdm(xzqh_dm);
		StringBuffer queryBuffer = new StringBuffer("SELECT SQDXH,SQDMC,SQDZT_DM AS SQDZTDM,SQDZT_DM,BZ,SPYJ,SBXZQH_DM,SBXZQH_DM||'000000000' AS SBXZQH_MC,LRSJ FROM XZQH_BGSQD WHERE  SBXZQH_DM LIKE '");
		queryBuffer.append(xzqh_dm);
		queryBuffer.append("%' AND SQDZT_DM <"+Common.XZQH_SQDZT_GJYSH+" ORDER BY SQDZT_DM");
		DataWindow dw = DataWindow.dynamicCreate(queryBuffer.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long cnt = dw.retrieve();
		String resultXml = "";
		if (cnt > 0){
			Dmmc.convDmmc(dw,"SQDZT_DM", "V_DM_XZQH_SQDZT");	
			Dmmc.convDmmc(dw,"SBXZQH_MC","V_DM_XZQH");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>方法名称：queryMxb</p>
	 * <p>方法描述：根据申请单序号查看相关明细</p>
	 * @param xmldo
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-24
	 */
	public String queryMxb(XMLDataObject xmldo) throws Exception {
		String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
		String resultXml = "";
		StringBuffer sql = new StringBuffer(
				"SELECT A.GROUPXH,A.GROUPMC,B.YSXZQH_DM,B.YSXZQH_MC,B.BGLX_DM,B.MBXZQH_DM,B.MBXZQH_MC,B.XGSJ FROM XZQH_BGGROUP A,XZQH_BGMXB B WHERE A.GROUPXH=B.GROUPXH AND A.SQDXH = '");
		sql.append(sqdxh);
		sql.append("' ORDER BY A.GROUPXH DESC ,B.PXH");	
		DataWindow dw = DataWindow.dynamicCreate(sql.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rows = dw.retrieve();
	 
		if(rows>0L){
			Dmmc.convDmmc(dw,"BGLX_DM", "V_DM_XZQH_BGLX");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
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
	
	public void bgsqqr(String xzqh_dm,String czry_dm,String jg_dm) throws Exception
	{
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE XZQH_BGSQD SET ");
		updateSql.append(" SQDZT_DM = '"+Common.XZQH_SQDZT_YQR+"'");
		updateSql.append(",XGJG_DM='"+jg_dm+"'");
		updateSql.append(",XGR_DM='"+czry_dm+"'");
		updateSql.append(",XGSJ=SYSDATE ");
		updateSql.append(" WHERE SBXZQH_DM LIKE '");
		updateSql.append(Common.getJbdm(xzqh_dm));
		updateSql.append("%' AND SQDZT_DM = '"+Common.XZQH_SQDZT_SHTG+"'");
		DataWindow dw = DataWindow.dynamicCreate(updateSql.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		dw.setTransObject(new UserTransaction());
		dw.update(true);
				
	}
	
	/**
	 * <p>方法名称：back</p>
	 * <p>方法描述：驳回审核通过的申请单</p>
	 * @param sqdxh
	 * @param spyj
	 * @param jg_dm
	 * @param czry_dm
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-12
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
	 * <p>方法名称：querySqdzt</p>
	 * <p>方法描述：查询申请单状态为未提交，已提交，审核未通过的记录数</p>
	 * @param xzqh_dm
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-12
	 */
	
	public String querySqdzt(String xzqh_dm) throws Exception
	{
		xzqh_dm=Common.getJbdm(xzqh_dm);
		D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
		d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		d_xzqh_bgsqd.setFilter("SBXZQH_DM LIKE '"+xzqh_dm+"%' AND SQDZT_DM IN ('"+Common.XZQH_SQDZT_WTJ+"','"+Common.XZQH_SQDZT_YTJ+"','"+Common.XZQH_SQDZT_SHBTG+"')");
		long rows = d_xzqh_bgsqd.retrieve();
		if(rows >0L)
		{
			return String.valueOf(rows);
		}else
		{
			return "0";
		}
	}
	
	/**
	 * <p>方法名称：queryXjsqd</p>
	 * <p>方法描述：获取下级单位的所有未提交申请单的区划</p>
	 * @param xdo
	 * @param xzqh_dm
	 * @param args
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-12
	 */
	public String queryWtj(XMLDataObject xdo,String xzqh_dm,long[] args) throws Exception
	{
//		获取系统参数:是否零申报
		String sflsb = StringEx.sNull(XtSvc.getXtcs("97003", null, null));
		if(sflsb.equalsIgnoreCase("N"))
		{
			return "";
		}
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		xzqh_dm = Common.getJbdm(xzqh_dm);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (SELECT SUBSTR(XZQH_DM,1,6) XZQH_DM,XZQH_QC FROM V_DM_XZQH_YLSJ WHERE JCDM ='3' AND XZQHLX_DM NOT IN('41','42','43','51','52') AND XZQH_DM LIKE '");
		sql.append(xzqh_dm);
		sql.append("%' INTERSECT SELECT SUBSTR(XZQH_DM,1,6) XZQH_DM,XZQH_QC FROM V_DM_XZQH WHERE JCDM ='3' AND XZQHLX_DM NOT IN('41','42','43','51','52') AND XZQH_DM LIKE '");
		sql.append(xzqh_dm);
		sql.append("%') XZQH WHERE XZQH.XZQH_DM NOT IN (SELECT SBXZQH_DM FROM XZQH_BGSQD WHERE SQDZT_DM <"+Common.XZQH_SQDZT_GJYSH+") ORDER BY XZQH.XZQH_DM");
		DataWindow dw = DataWindow.dynamicCreate(sql.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
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
