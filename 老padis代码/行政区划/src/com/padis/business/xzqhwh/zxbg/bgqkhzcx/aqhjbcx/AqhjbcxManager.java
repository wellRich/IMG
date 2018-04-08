package com.padis.business.xzqhwh.zxbg.bgqkhzcx.aqhjbcx;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;

/**
 * <p>Title: AqhjbcxManager.java </p>
 * <p>Description:按区划级别汇总区划变更情况的Manager类 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2010-05-07
 * @author lijl
 * @version 1.0
 */

public class AqhjbcxManager {

	/**
	 * <p>方法名称：queryMxb</p>
	 * <p>方法描述：按区划级别汇总区划变更情况</p>
	 * @return 汇总结果
	 * @throws Exception
	 * @author lijl
	 * @since 2010-05-07
	 */
	public String queryMxb(String xzqh_dm, String hzmc) throws Exception {	
		StringBuffer sql = new StringBuffer("SELECT A.XZQH_MC,A.BGS_SHENG,A.BGS_SHI,A.BGS_XIAN,A.BGS_XIANG,A.BGS_CUN,A.JC_DM,A.XZQH_DM FROM XZQH_JBHZB A WHERE ");
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
	 * <p>方法名称：groupByHzmc</p>
	 * <p>方法描述：</p>
	 * @return 汇总结果
	 * @throws Exception
	 * @author lijl
	 * @since 2010-05-07
	 */
	public String groupByHzmc() throws Exception {	

		DataWindow dw = DataWindow.dynamicCreate("SELECT B.HZMC FROM XZQH_JBHZB B GROUP BY B.HZMC ORDER BY B.HZMC");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = dw.retrieve(); 
		String resultXml = "";
		if (count > 0){
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
}
