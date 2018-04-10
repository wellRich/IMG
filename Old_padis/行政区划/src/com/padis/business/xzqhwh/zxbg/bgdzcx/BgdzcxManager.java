/**
 * 
 */
package com.padis.business.xzqhwh.zxbg.bgdzcx;

import com.padis.common.dmservice.Dmmc;
import com.padis.common.qxservice.IQxService;
import com.padis.common.qxservice.QxInterfaceFactory;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p>Title: SqdcxManager.java </p>
 * <p>Description:申请单查询的Manager类 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-10-13
 * @author pengld
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */
public class BgdzcxManager {
	
	/**
	 * <p>方法名称：querySqd</p>
	 * <p>方法描述：根据相应条件查询申请单</p>
	 * @param xdo
	 * @param args
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-14
	 */
	public String queryMxb(XMLDataObject xdo, String qxjg_dm, long[] args) throws Exception
	{
		String ysxzqh_dm = StringEx.sNull(xdo.getItemValue("YSXZQH_DM"));
		String ysxzqh_mc = StringEx.sNull(xdo.getItemValue("YSXZQH_MC"));
		String bglx_dm = StringEx.sNull(xdo.getItemValue("BGLX_DM"));
		String mbxzqh_dm = StringEx.sNull(xdo.getItemValue("MBXZQH_DM"));
		String mbxzqh_mc = StringEx.sNull(xdo.getItemValue("MBXZQH_MC"));
		String bgsjq = StringEx.sNull(xdo.getItemValue("BGSJQ"));
		String bgsjz = StringEx.sNull(xdo.getItemValue("BGSJZ"));
		

		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer conditions = new StringBuffer("");
		StringBuffer queryBuffer = new StringBuffer("SELECT YSXZQH_DM,YSXZQH_MC,BGLX_DM,MBXZQH_DM,MBXZQH_MC,BZ,LRSJ FROM XZQH_BGMXB");
		if(!ysxzqh_dm.equals(""))
		{
			conditions.append(" AND YSXZQH_DM LIKE '"+ysxzqh_dm+"%' ");
		}
		if(!ysxzqh_mc.equals(""))
		{
			conditions.append(" AND YSXZQH_MC LIKE '%"+ysxzqh_mc+"%'");
		}
		if(!bglx_dm.equals(""))
		{
			conditions.append(" AND BGLX_DM='"+bglx_dm+"'");
		}
		if(!mbxzqh_dm.equals(""))
		{
			conditions.append(" AND MBXZQH_DM LIKE '"+mbxzqh_dm+"%' ");
		}
		if(!mbxzqh_mc.equals(""))
		{
			conditions.append(" AND MBXZQH_MC LIKE '%"+mbxzqh_mc+"%'");
		}
		if(!bgsjq.equals(""))
		{
			conditions.append(" AND LRSJ >= TO_DATE('"+bgsjq+"','YYYY-MM-DD') ");
		}
		if(!bgsjz.equals(""))
		{
			conditions.append(" AND LRSJ < TO_DATE('"+bgsjz+"','YYYY-MM-DD') ");
		}
		if(conditions.length()>0){
			queryBuffer.append(" WHERE ").append(conditions.substring(5));
		}
		if(!qxjg_dm.equals("000000000000000")){
			IQxService qxService= QxInterfaceFactory.getInstance().getInterfaceImp();
			String qxjgCondition = StringEx.sNull(qxService.getQXCondition("LRJG_DM", qxjg_dm));
			if(!qxjgCondition.equals("")){
				if(queryBuffer.indexOf("WHERE")<0){
					queryBuffer.append(" WHERE ");
				}else{
					queryBuffer.append(" AND ");
				}
				queryBuffer.append(qxjgCondition);
			}
		}
		queryBuffer.append(" ORDER BY LRSJ DESC");
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
			Dmmc.convDmmc(dw,"BGLX_DM", "V_DM_XZQH_BGLX");	
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
}
