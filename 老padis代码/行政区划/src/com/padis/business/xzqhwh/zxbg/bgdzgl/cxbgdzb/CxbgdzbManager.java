package com.padis.business.xzqhwh.zxbg.bgdzgl.cxbgdzb;

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

public class CxbgdzbManager {

	/**
	 * <p>�������ƣ�</p>
	 * <p>����������</p>
	 * @param qxjg_dm
	 * @param xmldo
	 * @param args
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-22
	 */
	public String querySqb(String xzqh_dm, XMLDataObject xmldo ,long[] args) throws Exception {	
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //ҳ������
		pageSize = pageSize.equals("") ? "8" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("SELECT SQDXH,SQDMC,SQDZT_DM AS SQDZTDM,SQDZT_DM,BZ,SPYJ,SBXZQH_DM||'000000000' AS SBXZQH_MC,SBXZQH_DM,LRSJ FROM XZQH_BGSQD WHERE SBXZQH_DM = '");
		queryBuffer.append(xzqh_dm.substring(0,6));
		queryBuffer.append("' ORDER BY LRSJ DESC");
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
			Dmmc.convDmmc(dw, "SBXZQH_MC","V_DM_XZQH");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>����������</p>
	 * @param xdo
	 * @param czry_dm
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-22
	 */
	public void revokeSqd(String sqdxh,String czry_dm) throws Exception 
	{
		
		D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
		d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		d_xzqh_bgsqd.setFilter("SQDXH = "+sqdxh+" AND SQDZT_DM ='"+Common.XZQH_SQDZT_YTJ+"'");
		long rows = d_xzqh_bgsqd.retrieve();
		if(rows>0L)
		{
			d_xzqh_bgsqd.setItemString(0,"SQDZT_DM",Common.XZQH_SQDZT_WTJ);
			d_xzqh_bgsqd.setItemString(0,"XGR_DM",czry_dm);
			d_xzqh_bgsqd.setItemString(0, "XGSJ",XtDate.getDBTime());
			d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
			d_xzqh_bgsqd.setTransObject(new UserTransaction());
			d_xzqh_bgsqd.update(true);
		}
	}
	
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>����������</p>
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-25
	 */
	
	public String querySqdzt(String sqdxh) throws Exception
	{
		String xzqh_mc = "";
		StringBuffer sql = new StringBuffer("SELECT SQDZT_DM FROM xzqh_bgsqd WHERE SQDXH='");
		sql.append(sqdxh);
		sql.append("'");	
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		if(count>0){
			xzqh_mc = (String)xzqhDw.getItemAny(0, "SQDZT_DM");
		}
		return xzqh_mc;
	}
}
