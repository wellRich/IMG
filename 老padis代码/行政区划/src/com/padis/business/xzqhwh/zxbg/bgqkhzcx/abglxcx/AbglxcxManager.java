package com.padis.business.xzqhwh.zxbg.bgqkhzcx.abglxcx;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;


/**
 * <p>Title: AbglxcxManager.java </p>
 * <p>Description::��������ͻ���������������Manager�� </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2010-05-07
 * @author lijl
 * @version 1.0
 */

public class AbglxcxManager {

	/**
	 * <p>�������ƣ�</p>
	 * <p>����������</p>
	 * @return ���ܽ��
	 * @throws Exception
	 * @author lijl
	 * @since 2010-05-07
	 */
	public String queryMxb(String xzqh_dm,String hzmc) throws Exception {	
		StringBuffer sql = new StringBuffer("SELECT A.XZQH_MC,A.XZQH_DM,A.XINZENG,A.BIANGENG,A.QIANYI,A.BINGRU,A.JC_DM,A.XZQH_DM FROM XZQH_BGLXHZB A WHERE");
		sql.append(" A.SJ_XZQH_DM='").append(xzqh_dm).append("'");
		sql.append(" AND A.HZMC='").append(hzmc).append("' ORDER BY A.XZQH_DM");
		DataWindow dw = DataWindow.dynamicCreate(sql.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = dw.retrieve(); 
		String resultXml = "";
		if (count > 0){
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>�������ƣ�groupByHzmc</p>
	 * <p>����������</p>
	 * @return ���ܽ��
	 * @throws Exception
	 * @author lijl
	 * @since 2010-05-07
	 */
	public String groupByHzmc() throws Exception {	

		DataWindow dw = DataWindow.dynamicCreate("SELECT B.HZMC FROM XZQH_BGLXHZB B GROUP BY B.HZMC ORDER BY B.HZMC");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = dw.retrieve(); 
		String resultXml = "";
		if (count > 0){
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
}
