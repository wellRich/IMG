package com.padis.business.xzqhwh.delxzqh;


import java.util.ArrayList;
import java.util.List;

import com.padis.business.common.data.dm.D_dm_xzqh;
import com.padis.business.common.data.dm.D_dm_xzqh_ylsj;
import com.padis.business.xzqhwh.common.Common;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;
import ctais.util.StringEx;

/**
 * <p> 
 * Description: 行政区划删除服务类（DelxzqhService）的manager,提供DelxzqhService底层处理的方法
 * </p> 
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @since 2009-07-16
 * @author 李靖亮
 * @version 1.0
 */

public class DelxzqhManager {
	
	/**
	 * <p>方法名称：deleteMxb()，删除明细表数据</p>
	 * <p>方法说明：删除明细表数据的方法，本方法将会从表XT_XZQHBGMXB中删除多条记录。</p>
	 * @param mxbxhs
	 *             明细表序号
	 * @since 2009-07-16
	 * @author 李靖亮
	 * @return 无
	 * @exception Exception
	 */
	public List deleteXzqh(String xzqh_dm, String delFlag) throws Exception{
		UserTransaction ut = new UserTransaction();
		String jbdm = Common.getJbdm(xzqh_dm);
		StringBuffer sql = new StringBuffer("SELECT X.XZQH_DM FROM V_DM_XZQH X WHERE X.JBDM LIKE '");
		sql.append(jbdm);
		sql.append("%'");
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rowCount = xzqhDw.retrieve();
		List xzqhList = new ArrayList();
		boolean flag = true;
		if(rowCount<=0){
			return null;
		}
		String bz ="";
		for(long i=(rowCount-1); i>=0; i--) {
			String xzqhdm = StringEx.sNull(xzqhDw.getItemAny(i, "XZQH_DM"));
			xzqhList.add(xzqhdm);
		}
		List allMessage = new ArrayList();
		//查看奖励扶助能否删除
		DelxzqhImp jfImp = new JfDelXzqhImp();
		boolean tmpflag = jfImp.isAllowDelXzqh(xzqhList);
		if(tmpflag==false){
			bz="奖励扶助存在业务数据;";
			allMessage.addAll(jfImp.getReturnMessage());				
		}
		flag = flag & tmpflag;
		//查看流动人口管理能否删除
		DelxzqhImp lgImp = new LgDelXzqhImp();
		tmpflag = lgImp.isAllowDelXzqh(xzqhList);
		if(tmpflag==false){
			bz= bz+"流动人口管理存在业务数据;";
			allMessage.addAll(lgImp.getReturnMessage());				
		}
		flag = flag & tmpflag;
		//查看快速调查能否删除
		DelxzqhImp kdImp = new KdDelXzqhImp();
		tmpflag = kdImp.isAllowDelXzqh(xzqhList);
		if(tmpflag==false){
			bz= bz+"快速调查存在业务数据;";
			allMessage.addAll(kdImp.getReturnMessage());				
		}
		flag = flag & tmpflag;
		//查看事业统计能否删除
		DelxzqhImp stImp = new StDelXzqhImp();
		tmpflag = stImp.isAllowDelXzqh(xzqhList);
		if(tmpflag==false){
			bz= bz+"事业统计存在业务数据";
			allMessage.addAll(stImp.getReturnMessage());				
		}
		flag = flag & tmpflag;
		if(delFlag.equals("1")){
			try {
				ut.begin();
				if(rowCount>0){
					for(long i=(rowCount-1); i>=0; i--) {
						String time = XtDate.getDBTime();
						String xzqhdm = StringEx.sNull(xzqhDw.getItemAny(i, "XZQH_DM"));
						//在此调用业务接口
						D_dm_xzqh dw = new D_dm_xzqh();
						dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
						StringBuffer sql1 = new StringBuffer(
								"XYBZ = 'Y' AND YXBZ = 'Y' and XZQH_DM='");
						sql1.append(xzqhdm);
						sql1.append("'");
						dw.setFilter(sql1.toString());
						long cnt = dw.retrieve();
						String ysxzqh_dm = "";
						String ysxzqh_mc = "";
						if(cnt>0){
							ysxzqh_dm = StringEx.sNull(dw.getItemAny(0L, "XZQH_DM"));
							ysxzqh_mc = StringEx.sNull(dw.getItemAny(0L, "XZQH_MC"));
							dw.setItemString(0L, "YXQ_Z", time);
							dw.setItemString(0L, "XYBZ", "N");
							dw.setItemString(0L, "YXBZ", "N");
							dw.setItemString(0L, "XGSJ", time);
							dw.setTransObject(ut);
							dw.update(false);
						}
//						删除行政区划预览数据代码表
						D_dm_xzqh_ylsj ylsjDw = new D_dm_xzqh_ylsj();
						ylsjDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
						StringBuffer sql2 = new StringBuffer(
								"XYBZ = 'Y' AND YXBZ = 'Y' and XZQH_DM='");
						sql2.append(xzqhdm);
						sql2.append("'");
						ylsjDw.setFilter(sql2.toString());
						long cnt1 = ylsjDw.retrieve();
						if(cnt1>0){
							ylsjDw.setItemString(0L, "YXQ_Z", time);
							ylsjDw.setItemString(0L, "XYBZ", "N");
							ylsjDw.setItemString(0L, "YXBZ", "N");
							ylsjDw.setItemString(0L, "XGSJ", time);
							ylsjDw.setTransObject(ut);
							ylsjDw.update(false);
						}
						if(allMessage.size()>0){
							insertXzqhwclsjrzb(ysxzqh_dm,ysxzqh_mc,bz,ut);
						}
					}
					ut.commit();
				}
			} catch (Exception e) {
				ut.rollback();
				throw e;
			}
			return null;
		}else if(delFlag.equals("0")){		
			if(flag){
				try {
					ut.begin();
					if(rowCount>0){
						for(long i=(rowCount-1); i>=0; i--) {
							String xzqhdm = StringEx.sNull(xzqhDw.getItemAny(i, "XZQH_DM"));
							String time = XtDate.getDBTime();
							//在此调用业务接口
							D_dm_xzqh dw = new D_dm_xzqh();
							dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
							StringBuffer sql1 = new StringBuffer(
									"XYBZ = 'Y' AND YXBZ = 'Y' and XZQH_DM='");
							sql1.append(xzqhdm);
							sql1.append("'");
							dw.setFilter(sql1.toString());
							long cnt = dw.retrieve();
							if(cnt>0){
								dw.setItemString(0L, "YXQ_Z", time);
								dw.setItemString(0L, "XYBZ", "N");
								dw.setItemString(0L, "YXBZ", "N");
								dw.setItemString(0L, "XGSJ", time);
								dw.setTransObject(ut);
								dw.update(false);
							}
							//删除行政区划预览数据代码表
							D_dm_xzqh_ylsj ylsjDw = new D_dm_xzqh_ylsj();
							ylsjDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
							StringBuffer sql2 = new StringBuffer(
									"XYBZ = 'Y' AND YXBZ = 'Y' and XZQH_DM='");
							sql2.append(xzqhdm);
							sql2.append("'");
							ylsjDw.setFilter(sql2.toString());
							long cnt1 = ylsjDw.retrieve();
							if(cnt1>0){
								ylsjDw.setItemString(0L, "YXQ_Z", time);
								ylsjDw.setItemString(0L, "XYBZ", "N");
								ylsjDw.setItemString(0L, "YXBZ", "N");
								ylsjDw.setItemString(0L, "XGSJ", time);
								ylsjDw.setTransObject(ut);
								ylsjDw.update(false);
							}
						}
						ut.commit();
					}
				} catch (Exception e) {
					ut.rollback();
					throw e;
				}
			}
		}
		return allMessage;
	}
	
	/**
	 * <p>方法名称：messageXml()，删除明细表数据</p>
	 * <p>方法说明：删除明细表数据的方法，本方法将会从表XT_XZQHBGMXB中删除多条记录。</p>
	 * @param mxbxhs
	 *             明细表序号
	 * @since 2009-07-16
	 * @author 李靖亮
	 * @return 无
	 * @exception Exception
	 */
	public String messageXml(List massageList) throws Exception{
		if(massageList==null||massageList.size()<=0){
			return "";
		}
		StringBuffer strsb = new StringBuffer();
		for(int i=0;i<massageList.size();i++){
			DelxzqhBean bean = (DelxzqhBean)massageList.get(i);
			String ywxtdm = bean.getYwxtdm();
			strsb.append("<ITEM>");
			strsb.append("<XZQHDM>");
			strsb.append(bean.getXzqhdm());		
			strsb.append("</XZQHDM>");
			strsb.append("<YWBMC>");
			strsb.append(bean.getYwbmc());		
			strsb.append("</YWBMC>");
			strsb.append("<YWXTDM>");
			if(ywxtdm.equals("J")){
				strsb.append("奖励扶助");
			}else if(ywxtdm.equals("T")){
				strsb.append("特别扶助");
			}else if(ywxtdm.equals("S")){
				strsb.append("少生快富");
			}else if(ywxtdm.equals("L")){
				strsb.append("育龄妇女流动人口");
			}else if(ywxtdm.equals("K")){
				strsb.append("快速调查");
			}else if(ywxtdm.equals("Z")){
				strsb.append("事业统计");
			}
			strsb.append("</YWXTDM>");
			strsb.append("<SJL>");
			strsb.append(bean.getSjl());
			strsb.append("</SJL>");
			strsb.append("<YWBXZQHMC>");
			strsb.append(bean.getYwbxzqhmc());
			strsb.append("</YWBXZQHMC>");
			strsb.append("<BZ>");
			strsb.append(bean.getBz());
			strsb.append("</BZ>");
			strsb.append("</ITEM>");
		}
		return strsb.toString();
	}
	
	/**
	 * 
	 * <p>
	 * 方法名称：insertZl
	 * </p>
	 * <p>
	 * 方法描述：记录指令
	 * </p>
	 * 
	 * @param xzqhbgzl
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-7-21
	 */
	public void insertXzqhwclsjrzb(String xzqh_dm ,String xzqh_mc, String bz, UserTransaction ut) throws Exception {

				String insert_zlsql = "INSERT INTO XT_XZQHWCLSJRZB (YSXZQH_DM,YSXZQH_MC,BZ,DELBZ,LRSJ) VALUES('"
						+ xzqh_dm
						+ "','"+ xzqh_mc+ "','"+bz+"',"
						+ "'1',sysdate)";
				DataWindow insertdw = DataWindow.dynamicCreate(insert_zlsql);
				insertdw.setTransObject(ut);
				insertdw.update(false);
	}
}
