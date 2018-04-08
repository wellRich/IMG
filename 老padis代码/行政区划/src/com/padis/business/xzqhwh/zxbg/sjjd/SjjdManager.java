package com.padis.business.xzqhwh.zxbg.sjjd;

import com.padis.business.xzqhwh.common.Common;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;


/**
 * <p>Title: SjjdManager.java </p>
 * <p>Description:�м��ල��Manager�� </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-01-19
 * @author lijl
 * @version 1.0
 * �޸���ʷ�� 
 * �޸���
 * �޸�ʱ��(yyyy/mm/dd)
 * �޸�����
 * �汾��
 */

public class SjjdManager {

	
	/**
	 * <p>�������ƣ�queryWtj</p>
	 * <p>������������ȡ�¼���λ���������ύ���뵥������</p>
	 * @param xzqh_dm
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2011-01-19
	 */
	public String queryWtj(String xzqh_dm) throws Exception
	{
		String resultXml ="";
		StringBuffer sql = new StringBuffer("SELECT DISTINCT XZQH_DM,XZQH_MC,SQDZT_DM FROM(SELECT D.XZQH_DM, D.XZQH_MC,'δ����' AS SQDZT_DM");
		sql.append(" FROM V_DM_XZQH D WHERE D.XZQHLX_DM NOT IN ('41', '42', '43', '51', '52')");
		sql.append(" AND D.SJ_XZQH_DM='").append(xzqh_dm).append("'")
		.append("  AND D.XZQH_DM NOT IN (SELECT S.SBXZQH_DM || '000000000'  FROM XZQH_BGSQD S  WHERE S.SQDZT_DM <> '60'  " +
				"AND S.SBXZQH_DM LIKE '").append(Common.getJbdm(xzqh_dm)).append("%')")
		.append(" UNION ");
		sql.append("SELECT D.XZQH_DM,D.XZQH_MC,'δ�ύ' AS SQDZT_DM FROM  XZQH_BGSQD S ,V_DM_XZQH D WHERE D.XZQH_DM=S.SBXZQH_DM||'000000000' AND " +
				"S.SQDZT_DM='10' AND S.SBXZQH_DM IN(SELECT D.JBDM FROM V_DM_XZQH D WHERE  D.SJ_XZQH_DM='").append(xzqh_dm).append("')) ORDER BY XZQH_DM");
		DataWindow dw = DataWindow.dynamicCreate(sql.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));	
		long cnt = dw.retrieve();
		if (cnt > 0){	
			resultXml = dw.toXML().toString();
		}
		return resultXml;

	}
	
	/**
	 * <p>�������ƣ�queryWtj</p>
	 * <p>����������δ�ύ���뵥������</p>
	 * @param xzqh_dm
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2011-01-19
	 */
	public String queryYtj(String xzqh_dm) throws Exception
	{
		String resultXml ="";
		StringBuffer sql = new StringBuffer("SELECT D.XZQH_DM,D.XZQH_MC,S.SQDZT_DM FROM  XZQH_BGSQD S ,V_DM_XZQH D WHERE D.XZQH_DM=S.SBXZQH_DM||'000000000' " +
				"AND S.SQDZT_DM>10 AND S.SQDZT_DM<60 AND S.SBXZQH_DM IN(SELECT D.JBDM FROM V_DM_XZQH D WHERE ");
		sql.append(" D.SJ_XZQH_DM='").append(xzqh_dm).append("') ORDER BY D.XZQH_DM");
		DataWindow dw = DataWindow.dynamicCreate(sql.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));	
		long cnt = dw.retrieve();
		if (cnt > 0){
			Dmmc.convDmmc(dw,"SQDZT_DM", "V_DM_XZQH_SQDZT");
			resultXml = dw.toXML().toString();
		}
		return resultXml;

	}



}
