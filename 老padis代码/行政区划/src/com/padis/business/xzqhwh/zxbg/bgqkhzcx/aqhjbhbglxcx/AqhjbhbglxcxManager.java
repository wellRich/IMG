package com.padis.business.xzqhwh.zxbg.bgqkhzcx.aqhjbhbglxcx;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;


/**
 * <p>Title: AqhjbhbglxcxManager.java </p>
 * <p>Description:汇总全国区划代码变更情况的Manager类 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2010-05-07
 * @author lijl
 * @version 1.0
 */

public class AqhjbhbglxcxManager {

	/**
	 * <p>方法名称：queryMxb</p>
	 * <p>方法描述：汇总全国区划变更情况</p>
	 * @return 汇总结果
	 * @throws Exception
	 * @author lijl
	 * @since 2010-05-07
	 */
	public String queryMxb(String hzmc) throws Exception {	
		StringBuffer sql = new StringBuffer("SELECT BGLX_MC,BGS_SHENG,BGS_SHI,BGS_XIAN,BGS_XIANG,BGS_CUN,BGS_XIAOJI FROM XZQH_QHJBHBGLXHZB WHERE ");
		sql.append(" HZMC='").append(hzmc).append("' ORDER BY BGLX_DM");
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

		DataWindow dw = DataWindow.dynamicCreate("SELECT B.HZMC FROM XZQH_QHJBHBGLXHZB B GROUP BY B.HZMC ORDER BY B.HZMC");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = dw.retrieve(); 
		String resultXml = "";
		if (count > 0){
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
}
