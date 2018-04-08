package com.padis.business.xzqhwh.sjjzbg.drzsb;

import com.padis.business.common.data.xzqh.D_xzqh_jzbgzip;
import com.padis.business.xzqhwh.common.Common;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.connection.ConnConfig;
import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p> 
 * Description: 集中变更管理服务类（BgrzglService）的manager,提供BgrzglService底层处理的方法
 * </p> 
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @since 2009-09-10
 * @author 李靖亮
 * @version 1.0
 */

public class DrzsbManager {
	
	/**
	 * <p>方法名称：queryXzqhjzbgzip()，省级行政区划集中变更文件记录</p>
	 * <p>方法说明：省级行政区划集中变更文件记录的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param xmldo		查询条件
	 * @param args		分页查询参数
	 * @since 2009-09-10
	 * @author 李靖亮
	 * String 符合条件的电子邮件列表
	 * @throws Exception
	 */
	public String queryZip(XMLDataObject xmldo ,long[] args, String sjxzqh_dm) throws Exception {
		String xzqh_dm = StringEx.sNull(xmldo.getItemValue("XZQH_DM")); //每页的数据条数
		String rq = StringEx.sNull(xmldo.getItemValue("RQ")); //页的索引
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer sql = new StringBuffer("");
		StringBuffer queryBuffer = new StringBuffer("SELECT Z.ZIPXH,Z.XZQH_DM,Z.RQ,Z.WJM,Z.JZBGZT_DM,JZBGZT_DM JZBGZTDM," +
				"to_char(Z.LRSJ,'YYYY-MM-DD HH24:mi:ss') LRSJ FROM XZQH_JZBGZIP Z");
		if(!sjxzqh_dm.equals("00")&&!sjxzqh_dm.equals("")){
			queryBuffer.append(" WHERE ").append(" substr(Z.XZQH_DM,0,2)='").append(sjxzqh_dm).append("'");
		}
		if(!xzqh_dm.equals("")){
			sql.append(" AND Z.XZQH_DM='").append(xzqh_dm).append("'");
		}
		if(!rq.equals("")){
			sql.append(" AND Z.RQ='").append(rq).append("'");
		}		
		if(sql.length()>0){
			if(queryBuffer.indexOf("WHERE")<0){
				queryBuffer.append(" WHERE ").append(sql.substring(5));
			}else{
				queryBuffer.append(sql.toString());
			}
		}
		queryBuffer.append(" order by Z.LRSJ desc");
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
			Dmmc.convDmmc(dw,"JZBGZT_DM", "V_DM_XZQH_JZBGZT");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>方法名称：importTables()</p>
	 * <p>方法描述：检索权限机构表所有信息的方法，本方法将会从表QX_JG表中检索出多条记录。</p>
	 * @param uwa 工作区对象
	 * @return 机构树的报文
	 * @throws Exception
	 * @author wangjz
	 * @since Jan 21, 2008
	 */
	public void userOperate(String zipxl, String bgzl_dm) throws Exception{
		D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
		zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		StringBuffer sql = new StringBuffer();
		sql.append("ZIPXH = '").append(zipxl).append("'");
		zipDw.setFilter(sql.toString());
		long count = zipDw.retrieve();
		
		if(count>0){

			zipDw.setItemString(0L, "JZBGZT_DM",Common.XZQH_JZBGZT_SQDSQCLZ);//设置处理中状态
			zipDw.setItemString(0L, "BGZL_DM","3");
			zipDw.setTransObject(new UserTransaction());
			zipDw.update(true);
		}			
	}
	
	/**
	 * <p>方法名称：updateXzqhpadisDm()</p>
	 * <p>方法描述：更新XT_XZQHPADIS_TEMP表。</p>
	 * @param zipxl 压缩文件序号
	 * @param ut 事物
	 * @return 无
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-11
	 */
	public void updateXzqhpadisDm(String ysxzqh_dm, String mbxzqh_dm, String zipxl, UserTransaction ut) throws Exception{
		String ysjc_dm = Common.getJcdm(ysxzqh_dm);
		String mbjc_dm = Common.getJcdm(mbxzqh_dm);
		StringBuffer sqlSb = new StringBuffer("update XT_XZQHPADIS_TEMP t set t.XZQH_DM=replace(XZQH_DM,'");
		sqlSb.append(ysjc_dm);
		sqlSb.append("','");
		sqlSb.append(mbjc_dm);
		sqlSb.append("') where t.XZQH_DM like '");
		sqlSb.append(ysjc_dm);
		sqlSb.append("%' and t.ZIPXH='");
		sqlSb.append(zipxl);
		sqlSb.append("'");
		DataWindow updatedw = DataWindow.dynamicCreate(sqlSb.toString());
		updatedw.setTransObject(ut);
		updatedw.update(false);
	}
	

	/**
	 * 
	 * <p>
	 * 方法名称：getMaxSqbh
	 * </p>
	 * <p>
	 * 方法描述：得到数据库种最大的申请单编号
	 * </p>
	 * 
	 * @param dw
	 *            行政区划数据对象
	 * @param destXzqhmc
	 *            新行政区划名称
	 * @param ut
	 *            用户事物
	 * @author lijl
	 * @since 2009-7-13
	 */
	public String getMaxSqbh() throws Exception{
		DataWindow dw = DataWindow.dynamicCreate("select MAX(BH) as BH from XZQH_BGGROUP");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long cnt = dw.retrieve();
		String maxbh = "";
		if(cnt>0){
			String bh = StringEx.sNull(dw.getItemAny(0, "BH"));
			if(!bh.equals("")){
				maxbh = bh.substring((bh.length()-3), bh.length());
				if(!maxbh.equals("")){
					maxbh = String.valueOf(Integer.parseInt(maxbh)+1);
				}
				if(maxbh.length()==1){
					maxbh = "00"+maxbh;
				}
				if(maxbh.length()==2){
					maxbh = "0"+maxbh;
				}
			}
		}
		return maxbh;
	}
}
