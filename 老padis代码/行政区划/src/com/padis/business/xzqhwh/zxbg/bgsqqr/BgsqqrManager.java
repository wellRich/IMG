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
 * ���뵥ȷ��
 * <p>Title: BgsqqrManager.java </p>
 * <p>Description:���뵥ȷ�ϵ�Manager�� </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-10-12
 * @author pengld
 * @version 1.0
 * �޸���ʷ�� 
 * �޸���
 * �޸�ʱ��(yyyy/mm/dd)
 * �޸�����
 * �汾��
 */

public class BgsqqrManager {

	/**
	 * <p>�������ƣ�querySqb</p>
	 * <p>������������ѯ�¼����뵥</p>
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
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
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
	 * <p>�������ƣ�queryMxb</p>
	 * <p>�����������������뵥��Ų鿴�����ϸ</p>
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
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rows = dw.retrieve();
	 
		if(rows>0L){
			Dmmc.convDmmc(dw,"BGLX_DM", "V_DM_XZQH_BGLX");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>�������ƣ�bgsqqr</p>
	 * <p>����������������뵥ȷ�ϲ������������뵥״̬</p>
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
	 * <p>�������ƣ�back</p>
	 * <p>�����������������ͨ�������뵥</p>
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
	 * <p>�������ƣ�querySqdzt</p>
	 * <p>������������ѯ���뵥״̬Ϊδ�ύ�����ύ�����δͨ���ļ�¼��</p>
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
	 * <p>�������ƣ�queryXjsqd</p>
	 * <p>������������ȡ�¼���λ������δ�ύ���뵥������</p>
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
//		��ȡϵͳ����:�Ƿ����걨
		String sflsb = StringEx.sNull(XtSvc.getXtcs("97003", null, null));
		if(sflsb.equalsIgnoreCase("N"))
		{
			return "";
		}
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //ҳ������
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
		long ps = Long.parseLong(pageSize);   //һҳ�а������������ݣ����з�ҳ׼��
		long pageCount = dw.getPageCount(ps);  //һ������ҳ
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
