package com.padis.business.xzqhwh.zxbg.qhdmcx;



import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;


/**
 * <p>Title: QhdmcxManager.java </p>
 * <p>Description:<类的功能描述> </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-9-24
 * @author pengld
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */

public class QhdmcxManager {
		
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param forXzqh
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-17
	 */
	public XMLDataObject queryXzqh(String forXzqh,String tableName) throws Exception {
		String sql = "SELECT * FROM "+tableName+" WHERE XZQH_DM='"
				+ forXzqh + "'";
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql);
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// 设置连接池
		long count = xzqhDw.retrieve();
		if (count > 0) {
			Dmmc.convDmmc(xzqhDw,"SJ_XZQH_DM","V_DM_XZQH");
			Dmmc.convDmmc(xzqhDw,"DWLSGX_DM","V_DM_DWLSGX");
			Dmmc.convDmmc(xzqhDw,"XZQHLX_DM","V_DM_XZQHLX");
			return xzqhDw.toXDO();
		} else {
			return null;
		}
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param forXzqh
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-17
	 */
	public String isFb() throws Exception {
		String sql = "SELECT COUNT(*) FBZS FROM XZQH_BGSQD WHERE SQDZT_DM<50";
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql);
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// 设置连接池
		long count = xzqhDw.retrieve();
		String fbzs="0";
		if (count > 0) {
			fbzs = StringEx.sNull(xzqhDw.getItemAny(0L, "FBZS"));
			if(fbzs.equals("")){
				fbzs="0";
			}
		}
		
		return fbzs;
	}
}
