package com.padis.business.xzqhwh.zxbg.gjssbg;





import com.padis.business.common.data.xzqh.D_xzqh_bgsqd;
import com.padis.business.xzqhwh.common.Common;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;

import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;


/**
 * <p>Title: SqdwhManager.java </p>
 * <p>Description:<��Ĺ�������> </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-9-22
 * @author pengld
 * @version 1.0
 * �޸���ʷ�� 
 * �޸���
 * �޸�ʱ��(yyyy/mm/dd)
 * �޸�����
 * �汾��
 */

public class GjssbgManager {

	/**
	 * <p>�������ƣ�querySqb</p>
	 * <p>������������ѯ�¼����뵥״̬Ϊ���ύ�����뵥</p>
	 * @param qxjg_dm
	 * @param xmldo
	 * @param args
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-24
	 */
	public String querySqb(XMLDataObject xmldo,long[] args) throws Exception {	

		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //ҳ������
		pageSize = pageSize.equals("") ? "8" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("SELECT SQDXH,SQDMC,SQDZT_DM,BZ,SBXZQH_DM,SBXZQH_DM||'000000000' AS SBXZQH FROM XZQH_BGSQD WHERE SBXZQH_DM ='");
		queryBuffer.append("000000");
		queryBuffer.append("' ORDER BY SQDZT_DM");
		DataWindow dw = DataWindow.dynamicCreate(queryBuffer.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rowCount = dw.getRowCount(queryBuffer.toString());
		long ps = Long.parseLong(pageSize);   //һҳ�а������������ݣ����з�ҳ׼��
		long pageCount = dw.getPageCount(ps);  //һ������ҳ
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex),pageCount, queryBuffer.toString());
		args[0] = pageCount ; 
		args[1] = rowCount ; 
		String resultXml = "";
		if (cnt > 0){
			Dmmc.convDmmc(dw,"SQDZT_DM", "V_DM_XZQH_SQDZT");
			Dmmc.convDmmc(dw,"SBXZQH","V_DM_XZQH");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>�������ƣ�queryMxb</p>
	 * <p>����������</p>
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
	 * <p>�������ƣ�gjssbg</p>
	 * <p>��������������ʵʱ��ˣ����ϱ���������Ϊ��000000�����������뵥״̬С��40����������ˣ������뵥״̬����Ϊ40</p>
	 * @param sqdxh 
	 * @param jg_dm
	 * @param czry_dm
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-15
	 */
	
	public void gjssbg(String sqdxh,String jg_dm,String czry_dm) throws Exception
	{
		if(sqdxh.endsWith(","))
		{
			sqdxh=sqdxh.substring(0,sqdxh.length()-1);
		}
		
		D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
		d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		d_xzqh_bgsqd.setFilter("SBXZQH_DM ='000000' AND SQDZT_DM < "+Common.XZQH_SQDZT_GJYSH+" AND SQDXH IN ("+sqdxh+")");
		int rows = Integer.parseInt(String.valueOf(d_xzqh_bgsqd.retrieve()));
	
		for (int i=0;i<rows;i++)
		{
			d_xzqh_bgsqd.setItemString(i,"SQDZT_DM",Common.XZQH_SQDZT_GJYSH);
			d_xzqh_bgsqd.setItemString(i,"SPR_DM",czry_dm);
			d_xzqh_bgsqd.setItemString(i,"XGJG_DM",jg_dm);
			d_xzqh_bgsqd.setItemString(i,"XGR_DM",czry_dm);
			d_xzqh_bgsqd.setItemString(i,"XGSJ",XtDate.getDBTime());
			d_xzqh_bgsqd.setTransObject(new UserTransaction());
			d_xzqh_bgsqd.update(true);
		}
		
	}



}
