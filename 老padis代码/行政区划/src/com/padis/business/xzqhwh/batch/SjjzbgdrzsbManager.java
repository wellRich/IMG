package com.padis.business.xzqhwh.batch;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.padis.business.common.data.xzqh.D_xzqh_bggroup;
import com.padis.business.common.data.xzqh.D_xzqh_bgmxb;
import com.padis.business.common.data.xzqh.D_xzqh_bgsqd;
import com.padis.business.common.data.xzqh.D_xzqh_jzbgzip;
import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.XzqhbgBean;
import com.padis.business.xzqhwh.common.XzqhbgCommon;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.business.common.sequence.IGeneralSequence;
import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.or.ORManager;
import ctais.services.or.UserTransaction;
import ctais.services.sequence.SequenceManager;
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
public class SjjzbgdrzsbManager {
	
	//HM_modify
	String groupxh1 = "";
	String bglx_dm1 = "";
	String pxh1 = "";
	String ysxzqh_dm1 = "";
	String ysxzqh_mc1 = "";
	String mbxzqh_dm1 = "";
	String mbxzqh_mc1 = "";
	
	//HM_modify
	List groupList = new ArrayList();
	List groupmcList = new ArrayList();
	String bh2 = "";
	String groupmc2 = "";
	String sqdxh2 = "";
	String czry_dm2 = "";
	String pxh2 = "";
	String sql1_tmp = "";
	String sql_tmp = "";
	int j = 0;
	
	public void drzsb(String zipxh) throws Exception {

		D_xzqh_jzbgzip zipdw = new D_xzqh_jzbgzip();	
		zipdw.setFilter("ZIPXH='"+zipxh+"'");
		long count = zipdw.retrieve();
		if(count<=0)
			return;
		String zipxl = StringEx.sNull(zipdw.getItemAny(0L, "ZIPXH"));
		String xzqh_dm = StringEx.sNull(zipdw.getItemAny(0L, "XZQH_DM"));
		String czry_dm = StringEx.sNull(zipdw.getItemAny(0L, "LRR_DM"));
		String qxjg_dm = StringEx.sNull(zipdw.getItemAny(0L, "LRJG_DM"));
		this.importFormalTable(zipxl,czry_dm,qxjg_dm,xzqh_dm);

	}

	/**
	 * <p>方法名称：importFormalTable()</p>
	 * <p>方法描述：导入正式表。</p>
	 * @param zipxl 压缩文件序号
	 * @param sjbgxzqh_dm 省级区划代码
	 * @return 无
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-11
	 */
	public void importFormalTable(String zipxl,String czry_dm,String qxjg_dm,String lwxzqh_dm) throws Exception{
		
		LogManager.getLogger().error("importFormalTable======start " +XtDate.getDBTime()+ "1");
		
		UserTransaction ut = new UserTransaction();
		ResultSet resultSet= null;
		Connection conn = null;
		Statement stmt =null;
		XzqhbgCommon bgCommon= new XzqhbgCommon();
//		String sysTime = XtDate.getDBTime();
		D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
		d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		d_xzqh_bgsqd.insert(-1);
		D_xzqh_bggroup groupDw = new D_xzqh_bggroup();
		groupDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		groupDw.insert(-1);
		D_xzqh_bgmxb mxbDw = new D_xzqh_bgmxb();
		mxbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		mxbDw.insert(-1);
		String sqdxh="";
		try{
			ut.begin();
			//新增申请单
			IGeneralSequence iseq = (IGeneralSequence) SequenceManager.getSequenceFactory();
			sqdxh = iseq.get("SEQ_XZQH_BGSQD_XL");
			d_xzqh_bgsqd.setItemString(0, "SQDXH",sqdxh);
			d_xzqh_bgsqd.setItemString(0, "SQDMC",lwxzqh_dm+"集中上报数据");			
			d_xzqh_bgsqd.setItemString(0, "SQDZT_DM", Common.XZQH_SQDZT_YQR);//已确认
			d_xzqh_bgsqd.setItemString(0, "LRR_DM",czry_dm );
			d_xzqh_bgsqd.setItemString(0, "LRSJ", XtDate.getDBTime());
			d_xzqh_bgsqd.setItemString(0, "LRJG_DM", qxjg_dm);
			d_xzqh_bgsqd.setItemString(0, "SBXZQH_DM",lwxzqh_dm);
			d_xzqh_bgsqd.setTransObject(ut);
			d_xzqh_bgsqd.update(false);
			
			//新增组
//			StringBuffer sql1 = new StringBuffer("select BH,GROUPMC from XZQH_JZBGDZB_TEMP WHERE CWSJBZ='N' AND ");
//			sql1.append(" ZIPXH='").append(zipxl).append("' order by PXH");
			StringBuffer sql1 = new StringBuffer("select * from (");
			sql1.append("select BH,GROUPMC,PXH from XZQH_JZBGDZB_TEMP where CWSJBZ='N' AND ");
			sql1.append("ZIPXH='").append(zipxl).append("'");
			sql1.append(" union all ");
			sql1.append("select BH,GROUPMC,PXH from XZQH_JZBGKLJDZB_TEMP where CWSJBZ='N' AND ")
			.append("ZIPXH='").append(zipxl).append("'");
			sql1.append(") a order by a.PXH");
			
			conn = ORManager.getInstance().getConnectionPool("ctais").getConnection("0");
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(sql1.toString());
			
			//HM_modify
//			List groupList = new ArrayList();
//			List groupmcList = new ArrayList();
			
			int rows=0;
			if(resultSet!=null){
				try {
					while (resultSet.next()){
						 
						String bh = StringEx.sNull(resultSet.getString("BH"));
						String groupmc = StringEx.sNull(resultSet.getString("GROUPMC"));
						if(!bh.equals("")){
							if(rows==0){
								groupList.add(bh);
								groupmcList.add(groupmc);
				    		}else{
				    			if(!groupList.contains(bh)){
				    				groupList.add(bh);
				    				groupmcList.add(groupmc);
				    			}else{
				    				continue;
				    			}
				    		}
						}
						rows++;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					throw ex;
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
		                throw e;
		            }
				}
			}			

			for(int n=0; n<groupList.size(); n++){
				
				//HM_modify
				j = n;

				String groupxh = iseq.get("SEQ_XZQH_BGGROUP_XL");
				String bh = StringEx.sNull(groupList.get(n));
				String groupmc = StringEx.sNull(groupmcList.get(n));
				groupDw.setItemString(0, "GROUPXH", groupxh);
				groupDw.setItemString(0, "GROUPMC", groupmc);
				groupDw.setItemString(0, "SQDXH", sqdxh);
				groupDw.setItemString(0, "BH", bh);
				groupDw.setItemString(0, "LRR_DM", czry_dm);
				groupDw.setItemString(0, "LRSJ", XtDate.getDBTime());
				groupDw.setItemString(0, "PXH", new Common().getMax("XZQH_BGGROUP",ut));
				groupDw.setItemString(0, "LRJG_DM", qxjg_dm);
				groupDw.setTransObject(ut);
				groupDw.update(false);
				//将省级集中上报数据拷贝到明细表中
				
				StringBuffer sql = new StringBuffer("select * from (");
				sql.append("select YSXZQH_DM, YSXZQH_MC, BGLX_DM, MBXZQH_DM, MBXZQH_MC,PXH from XZQH_JZBGDZB_TEMP where CWSJBZ='N' AND ");
				sql.append("ZIPXH='").append(zipxl).append("' and BH='").append(bh).append("'");
				sql.append(" union all ");
				sql.append("select YSXZQH_DM, YSXZQH_MC, BGLX_DM, MBXZQH_DM, MBXZQH_MC,PXH from XZQH_JZBGKLJDZB_TEMP where CWSJBZ='N' AND ")
				.append("ZIPXH='").append(zipxl).append("' and BH='").append(bh).append("'");
				sql.append(") a order by a.PXH");
				DataWindow dw1 = DataWindow.dynamicCreate(sql.toString());
				long cnt = dw1.retrieve();
				for(int m=0;m<cnt;m++){					
					String mxbxh = iseq.get("SEQ_XZQH_BGMXB_XL");

					mxbDw.setItemString(0, "MXBXH", mxbxh);
					mxbDw.setItemString(0, "GROUPXH", groupxh);
					mxbDw.setItemString(0, "BGLX_DM", StringEx.sNull(dw1.getItemAny(m, "BGLX_DM")));
					mxbDw.setItemString(0, "PXH", StringEx.sNull(dw1.getItemAny(m, "PXH")));
					mxbDw.setItemString(0, "YSXZQH_DM", StringEx.sNull(dw1.getItemAny(m, "YSXZQH_DM")));
					mxbDw.setItemString(0, "YSXZQH_MC", StringEx.sNull(dw1.getItemAny(m, "YSXZQH_MC")));
					mxbDw.setItemString(0, "MBXZQH_DM", StringEx.sNull(dw1.getItemAny(m, "MBXZQH_DM")));
					mxbDw.setItemString(0, "MBXZQH_MC", StringEx.sNull(dw1.getItemAny(m, "MBXZQH_MC")));
					mxbDw.setItemString(0, "CLZT_DM", Common.XZQH_CLZT_DCL);//待处理
					mxbDw.setItemString(0, "LRR_DM", czry_dm);
					mxbDw.setItemString(0, "LRSJ", XtDate.getDBTime());			
					mxbDw.setItemString(0, "LRJG_DM", qxjg_dm);
					mxbDw.setItemString(0, "XGR_DM", czry_dm);
					mxbDw.setItemString(0, "XGSJ", XtDate.getDBTime());			
					mxbDw.setItemString(0, "XGJG_DM", qxjg_dm);
					
					//HM_modify
					bh2 = StringEx.sNull(groupList.get(n));
					groupmc2 = StringEx.sNull(groupmcList.get(n));
					
					//HM_modify
					groupxh1 = groupxh;
					bglx_dm1 = StringEx.sNull(dw1.getItemAny(m, "BGLX_DM"));
					pxh1 = StringEx.sNull(dw1.getItemAny(m, "PXH"));
					ysxzqh_dm1 = StringEx.sNull(dw1.getItemAny(m, "YSXZQH_DM"));
					ysxzqh_mc1 = StringEx.sNull(dw1.getItemAny(m, "YSXZQH_MC"));
					mbxzqh_dm1 = StringEx.sNull(dw1.getItemAny(m, "MBXZQH_DM"));
					mbxzqh_mc1 = StringEx.sNull(dw1.getItemAny(m, "MBXZQH_MC"));
					sql1_tmp = sql1.toString();
					sql_tmp = sql.toString();
					
					mxbDw.setTransObject(ut);
					mxbDw.update(false);
				}				
			}
			ut.commit();
		}catch(Exception e){
			
			//HM_modify
			LogManager.getLogger().error("importFormalTable====== " + "1");
			LogManager.getLogger().error("importFormalTable======bh2 " +bh2+"   "+ "2");
			LogManager.getLogger().error("importFormalTable======groupmc2 " +groupmc2+"   "+ "3");
			
			//HM_modify
			LogManager.getLogger().error("importFormalTable======groupxh " +groupxh1+"   "+ "4");
			LogManager.getLogger().error("importFormalTable======BGLX_DM " +bglx_dm1+"   "+ "5");
			LogManager.getLogger().error("importFormalTable======PXH " +pxh1+"   "+ "6");
			LogManager.getLogger().error("importFormalTable======YSXZQH_DM " +ysxzqh_dm1+"   "+ "7");
			LogManager.getLogger().error("importFormalTable======YSXZQH_MC " +ysxzqh_mc1+"   "+ "8");
			LogManager.getLogger().error("importFormalTable======MBXZQH_DM " +mbxzqh_dm1+"   "+ "9");
			LogManager.getLogger().error("importFormalTable======MBXZQH_MC " +mbxzqh_mc1+"   "+ "10");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogManager.getLogger().error(sw.toString());
			LogManager.getLogger().error("importFormalTable====== " + "11");
			LogManager.getLogger().error("importFormalTable======sql1_tmp " +sql1_tmp.toString() +"12");
			LogManager.getLogger().error("importFormalTable======sql_tmp " +sql_tmp.toString() +"13");
			LogManager.getLogger().error("importFormalTable======j " +j +"14");
			LogManager.getLogger().error("importFormalTable====== " +XtDate.getDBTime()+ "15");
			
			ut.rollback();
			throw e;
		}
		try{
			StringBuffer querySql = new StringBuffer("select M.MXBXH,M.GROUPXH,M.YSXZQH_DM,M.YSXZQH_MC,M.BGLX_DM,M.MBXZQH_DM,M.MBXZQH_MC,M.BZ,M.LRR_DM,M.LRJG_DM " +
					"from XZQH_BGMXB M,XZQH_BGGROUP G where M.GROUPXH=G.GROUPXH AND G.SQDXH='").append(sqdxh).append("' order by M.PXH");
			conn = ORManager.getInstance().getConnectionPool("ctais").getConnection("0");
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(querySql.toString());
			//int count=1;
			ut = new UserTransaction();
			if(resultSet!=null){
				try {
					ut.begin();
					while (resultSet.next()) {
						
						String groupxh = StringEx.sNull(resultSet.getString("GROUPXH"));
						String mxbxh = StringEx.sNull(resultSet.getString("MXBXH"));
						String ysxzqh_dm = StringEx.sNull(resultSet.getString("YSXZQH_DM"));
						String ysxzqh_mc = StringEx.sNull(resultSet.getString("YSXZQH_MC"));
						String mbxzqh_dm = StringEx.sNull(resultSet.getString("MBXZQH_DM"));
						String mbxzqh_mc = StringEx.sNull(resultSet.getString("MBXZQH_MC"));
						String bglx_dm = StringEx.sNull(resultSet.getString("BGLX_DM"));
						String bz = StringEx.sNull(resultSet.getString("BZ"));

						XzqhbgBean xzqhBean = new XzqhbgBean();
						xzqhBean.setDestXzqhdm(mbxzqh_dm);
						xzqhBean.setDestXzqhmc(mbxzqh_mc);			
						xzqhBean.setSrcXzqhmc(ysxzqh_mc);
						xzqhBean.setSrcXzqhdm(ysxzqh_dm);			
						xzqhBean.setBglxdm(bglx_dm);
						xzqhBean.setLrr_dm(czry_dm);
						xzqhBean.setQx_jgdm(qxjg_dm);
						xzqhBean.setSqbxh(groupxh);
						xzqhBean.setSqbmxXh(mxbxh);
						xzqhBean.setBz(bz);
						bgCommon.saveXzqh(xzqhBean,XtDate.getDBTime(),"dm_xzqh_ylsj",ut);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					throw ex;
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
		                throw e;
		            }
				}
			}
			
			//将申请单序列保存到D_xzqh_jzbgzip表
			D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
			zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
			StringBuffer sql = new StringBuffer();
			sql.append("ZIPXH = '").append(zipxl).append("'");
			zipDw.setFilter(sql.toString());
			long counts = zipDw.retrieve();
			if(counts>0){
				zipDw.setItemString(0L, "SQDXH",sqdxh);
				zipDw.setItemString(0L, "JZBGZT_DM",Common.XZQH_JZBGZT_SQDSQCG);//申请单生成成功
				zipDw.setItemString(0L, "BZ","申请单生成成功");//申请单生成成功
				zipDw.setTransObject(ut);
				zipDw.update(false);
			}
			
			ut.commit();
		}catch(Exception e){
			ut.rollback();
			
			D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
			zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
			StringBuffer sql = new StringBuffer();
			sql.append("ZIPXH = '").append(zipxl).append("'");
			zipDw.setFilter(sql.toString());
			long counts = zipDw.retrieve();
			if(counts>0){
				zipDw.setItemString(0L, "JZBGZT_DM",Common.XZQH_JZBGZT_SQDSQSB);//申请单生成失败
				zipDw.setItemString(0L, "BZ","申请单生成失败："+e.getMessage());//申请单生成失败
				zipDw.setTransObject(new UserTransaction());
				zipDw.update(true);
			}
			
			DataWindow delMxbDw = DataWindow.dynamicCreate("DELETE XZQH_BGMXB M WHERE M.GROUPXH IN (SELECT G.GROUPXH FROM XZQH_BGGROUP G WHERE G.SQDXH='"+sqdxh+"')");
			delMxbDw.setTransObject(new UserTransaction());
			delMxbDw.update(true);
			DataWindow delGroupDw = DataWindow.dynamicCreate("DELETE XZQH_BGGROUP G WHERE G.SQDXH='"+sqdxh+"'");
			delGroupDw.setTransObject(new UserTransaction());
			delGroupDw.update(true);
			DataWindow delSqdDw = DataWindow.dynamicCreate("DELETE XZQH_BGSQD S WHERE S.SQDXH='"+sqdxh+"'");
			delSqdDw.setTransObject(new UserTransaction());
			delSqdDw.update(true);
			throw e;
		}
	}
}
