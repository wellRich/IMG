package com.padis.business.xzqhwh.batch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.padis.business.common.data.xzqh.D_xzqh_jzbgdzb_temp;
import com.padis.business.common.data.xzqh.D_xzqh_jzbgzip;
import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.XzqhbgBean;
import com.padis.business.xzqhwh.common.XzqhbgCommon;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.or.ORManager;
import ctais.services.or.UserTransaction;
import ctais.util.StringEx;

/**
 * 
 * <p>
 * Title:
 * </P>
 * <p>
 * Description: 集中变更导入临时表
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: DigitalChina Co.Ltd
 * </p>
 * 
 * @auther dongzga
 * @version 1.0
 * @since 2009-10-23 修改人： 修改时间： 修改内容： 修改版本号：
 */
public class SjjzbgljclManager {

	/**
	 * <p>
	 * 方法名称：checkLogic()
	 * </p>
	 * <p>
	 * 方法描述：逻辑校验。
	 * </p>
	 * 
	 * @param zipxls
	 *            压缩文件序号
	 * @return 无
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-11
	 */
	public void checkLogic(String zipxl) throws Exception {
		XzqhbgCommon bgCommon = new XzqhbgCommon();
		StringBuffer sql = new StringBuffer("select * from (");
				sql.append("select DZBXH,YSXZQH_DM,YSXZQH_MC,BGLX_DM,MBXZQH_DM,MBXZQH_MC,BZ,LRR_DM,LRJG_DM,PXH from XZQH_JZBGDZB_TEMP where ");
				sql.append("ZIPXH='").append(zipxl).append("'");
				sql.append(" union all ");
				sql.append("select DZBXH,YSXZQH_DM,YSXZQH_MC,BGLX_DM,MBXZQH_DM,MBXZQH_MC,BZ,LRR_DM,LRJG_DM,PXH from XZQH_JZBGKLJDZB_TEMP where ")
				.append("ZIPXH='").append(zipxl).append("'");
				sql.append(") a order by a.PXH");
		ResultSet resultSet= null;
		Connection conn = ORManager.getInstance().getConnectionPool("ctais").getConnection("0");
		Statement stmt = conn.createStatement();
		resultSet = stmt.executeQuery(sql.toString());
		
		////DataWindow tmpDw = DataWindow.dynamicCreate(sql.toString());
		//long count = tmpDw.retrieve();
		
		String errorInfo = "";
		String dzbxh = "";
		UserTransaction ut = new UserTransaction();
		//ut.setTransactionTimeout(10800);
		List list = new ArrayList(); 
		List kljList = new ArrayList();
		
		//int counts=0;
		if(resultSet!=null){
			try {
				ut.begin();
				while (resultSet.next()) {
					String sysTime = XtDate.getDBTime();
					HashMap map1 = new HashMap();
					HashMap map2 = new HashMap();
					dzbxh = StringEx.sNull(resultSet.getString("DZBXH"));
					String ysxzqh_dm = StringEx.sNull(resultSet.getString("YSXZQH_DM"));
					String ysxzqh_mc = StringEx.sNull(resultSet.getString("YSXZQH_MC"));
					String bglx_dm = StringEx.sNull(resultSet.getString("BGLX_DM"));
					String mbxzqh_dm = StringEx.sNull(resultSet.getString("MBXZQH_DM"));
					String mbxzqh_mc = StringEx.sNull(resultSet.getString("MBXZQH_MC"));
					String bz = StringEx.sNull(resultSet.getString("BZ"));
					String czry_dm = StringEx.sNull(resultSet.getString("LRR_DM"));
					String qxjg_dm = StringEx.sNull(resultSet.getString("LRJG_DM"));
					String pxh = StringEx.sNull(resultSet.getString("PXH"));	
					
					try {
						if(!Common.getSjxzqhdm(Common.getSjxzqhdm(ysxzqh_dm))
								.equals(Common.getSjxzqhdm(Common.getSjxzqhdm(mbxzqh_dm)))
								&&(bglx_dm.equals(Common.MERGE)||bglx_dm.equals(Common.MOVE))){
							//kljList.add(dzbxh);
							map2.put("DZBXH", dzbxh);
							bgCommon.logicVerifyKljXzqh(ysxzqh_dm, ysxzqh_mc, bglx_dm,mbxzqh_dm, mbxzqh_mc, "DM_XZQH_YLSJ",false,czry_dm);
						}else {
							//list.add(dzbxh);
							map1.put("DZBXH", dzbxh);
							bgCommon.logicVerifyXzqh(ysxzqh_dm, ysxzqh_mc, bglx_dm,mbxzqh_dm, mbxzqh_mc, "DM_XZQH_YLSJ",false,czry_dm);
						}
						XzqhbgBean xzqhBean = new XzqhbgBean();
						xzqhBean.setDestXzqhdm(mbxzqh_dm);
						xzqhBean.setDestXzqhmc(mbxzqh_mc);
						xzqhBean.setSrcXzqhmc(ysxzqh_mc);
						xzqhBean.setSrcXzqhdm(ysxzqh_dm);
						xzqhBean.setBglxdm(bglx_dm);
						xzqhBean.setLrr_dm(czry_dm);
						xzqhBean.setQx_jgdm(qxjg_dm);
						xzqhBean.setSqbxh(zipxl);
						xzqhBean.setSqbmxXh(dzbxh);
						xzqhBean.setBz(bz);
						bgCommon.saveXzqh(xzqhBean, sysTime,"dm_xzqh_ylsj", ut);
					} catch (Exception e) {
						errorInfo = "第" + pxh + "行逻辑错误:" + e.getMessage();
						//counts++;
						map1.put("CWXX", errorInfo);
						map2.put("CWXX", errorInfo);
						//throw new Exception(errorInfo);
					}
					list.add(map1);
					kljList.add(map2);
					

				}

			} catch (Exception ex) {
				errorInfo = ex.getMessage();
				ex.printStackTrace();
			} finally {
				try {
	            	if(resultSet!=null){
	            		resultSet.close();
	            	}
	            	if(stmt!=null){
	            		stmt.close();
	            	}
	            	if(conn!=null){
	            		conn.close();
	            	}
				}catch (Exception e) {
	                e.printStackTrace();
	            }
				
				ut.rollback();
			}
		}else{
				return;
		}

		boolean flag=false;
		// 如果出现逻辑校验错误信息，更新zip文件表（提示信息要详细）
		boolean flag1 = this.updateXzqhjzbgdzb(zipxl, list);
		boolean flag2 = this.updateKljXzqhjzbgdzb(zipxl, kljList);
		if(flag1||flag2){
			flag=true;
		}
		this.updateJzbgzip(zipxl, flag);
	}
	
	/**
	 * <p>
	 * 方法名称：updateXzqhjzbgdzb()
	 * </p>
	 * <p>
	 * 方法描述：更新对照表
	 * </p>
	 * 
	 * @param zipxls
	 *            压缩文件序号
	 * @param errInfo
	 *            错误信息
	 * @param ut
	 *            事物
	 * @return 无
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-11
	 */
	public boolean updateXzqhjzbgdzb(String zipxh, List list)
			throws Exception {
		boolean flag=false;
		UserTransaction ut = new UserTransaction();
		int count = 0;
		try {
			ut.begin();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					String dzbxh = StringEx.sNull(((HashMap)list.get(i)).get("DZBXH"));
					String cwxx = StringEx.sNull(((HashMap)list.get(i)).get("CWXX"));
					if (dzbxh.equals("")) {
						continue;
					}
					D_xzqh_jzbgdzb_temp dzbDw = new D_xzqh_jzbgdzb_temp();
					dzbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
					StringBuffer sql = new StringBuffer();
					sql.append("DZBXH = '").append(dzbxh).append("'");
					dzbDw.setFilter(sql.toString());
					dzbDw.retrieve();
					if (!cwxx.equals("")) {
						count++;
						dzbDw.setItemString(0L, "CWSJBZ", "Y");
						dzbDw.setItemString(0L, "CWXX", cwxx);
					} else {
						dzbDw.setItemString(0L, "CWSJBZ", "N");
					}
					
					dzbDw.setTransObject(ut);
					dzbDw.update(false);
				}
			}
			if (count > 0) {
				flag=true;
			}
			ut.commit();
		} catch (Exception e) {
			ut.rollback();
			throw e;
		}
		return flag;
	}
	
	
	/**
	 * <p>
	 * 方法名称：updateKljXzqhjzbgzip()
	 * </p>
	 * <p>
	 * 方法描述：更新对照表
	 * </p>
	 * 
	 * @param zipxls
	 *            压缩文件序号
	 * @param errInfo
	 *            错误信息
	 * @param ut
	 *            事物
	 * @return 无
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-11
	 */
	public boolean updateKljXzqhjzbgdzb(String zipxh, List list)
			throws Exception {
		boolean flag=false;
		UserTransaction ut = new UserTransaction();
		int count = 0;
		try {
			ut.begin();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					
					String dzbxh = StringEx.sNull(((HashMap)list.get(i)).get("DZBXH"));
					String cwxx = StringEx.sNull(((HashMap)list.get(i)).get("CWXX"));
					String cwsjbz="";
					if (dzbxh.equals("")) {
						continue;
					}
					if (!cwxx.equals("")) {
						cwsjbz="Y";
						count++;
					} else {
						cwsjbz="N";
					}
					StringBuffer sqlSb = new StringBuffer("update XZQH_JZBGKLJDZB_TEMP T ")
					.append(" set T.CWSJBZ='").append(cwsjbz).append("',T.CWXX='").append(cwxx).append("'");
					
					sqlSb.append(" where T.DZBXH='");
					sqlSb.append(dzbxh);
					sqlSb.append("'");
					DataWindow updateDw = DataWindow.dynamicCreate(sqlSb.toString());
					updateDw.setTransObject(ut);
					updateDw.update(false);
				}
			}
			if (count > 0) {
				flag=true;
			}
			
			ut.commit();
		} catch (Exception e) {
			ut.rollback();
			throw e;
		}
		return flag;
	}
	

	/**
	 * <p>
	 * 方法名称：updateKljXzqhjzbgzip()
	 * </p>
	 * <p>
	 * 方法描述：更新对照表
	 * </p>
	 * 
	 * @param zipxls
	 *            压缩文件序号
	 * @param errInfo
	 *            错误信息
	 * @param ut
	 *            事物
	 * @return 无
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-11
	 */
	public void updateJzbgzip(String zipxh, boolean flag)
			throws Exception {

		D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
		zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		StringBuffer zipsql = new StringBuffer();
		zipsql.append("ZIPXH = '").append(zipxh).append("'");
		zipDw.setFilter(zipsql.toString());
		zipDw.retrieve();
		if (flag) {
			zipDw.setItemString(0L, "JZBGZT_DM", "31");// 逻辑校验失败
			zipDw.setItemString(0L, "BZ", "逻辑校验失败");// 逻辑校验失败
			zipDw.setTransObject(new UserTransaction());
			zipDw.update(false);
		} else {
			zipDw.setItemString(0L, "JZBGZT_DM", "30");// 逻辑校验成功
			zipDw.setItemString(0L, "BZ", "逻辑校验成功");// 逻辑校验成功
			zipDw.setTransObject(new UserTransaction());
			zipDw.update(false);

		}
	}
}
