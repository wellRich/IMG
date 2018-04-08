package com.padis.business.xzqhwh.batch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.padis.business.common.data.xt.D_xt_xzqhfb;
import com.padis.business.common.data.xzqh.D_xzqh_bgsqd;
import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.FileUtils;
import com.padis.business.xzqhwh.common.ZipUtil;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.XtSvc;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.business.common.sequence.IGeneralSequence;
import ctais.business.common.xtservice.batch.BatchFactory;
import ctais.business.common.xtservice.batch.BatchTask;
import ctais.business.common.xtservice.batch.DbHelper;
import ctais.business.common.xtservice.batch.IBatchable;
import ctais.business.common.xtservice.batch.IManagerCommand;
import ctais.business.common.xtservice.batch.IScheduleable;
import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.or.ORManager;
import ctais.services.or.UserTransaction;
import ctais.services.sequence.SequenceManager;
import ctais.services.xml.Response;
import ctais.services.xml.XMLDataObject;
import ctais.services.xml.XMLDataObjectHelper;
import ctais.services.xml.XMLParser;
import ctais.util.StringEx;

/**
 * 
 * <p>
 * Title:导出行政区划扫描程序
 * </P>
 * <p>
 * Description:扫描全国的行政区划以及最近变更的行政区划，扫描后导出行政区划
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: DigitalChina Co.Ltd
 * </p>
 * 
 * @auther lijl
 * @version 1.0
 * @since 2009-08-14 修改人： 修改时间： 修改内容： 修改版本号：
 */

public class ExpxzqhBatch implements IManagerCommand, IBatchable {


	private int progressPercent = 0;

	private StringBuffer sbProcessMsg = new StringBuffer();

	/**
	 * 批任务的调度单元
	 */
	private IScheduleable schedUnit;

	public ExpxzqhBatch() {
	}

	/**
	 * 取得当前处理状态
	 * 
	 * @return 处理状态信息
	 */
	public String getProcessMsg() {
		return this.sbProcessMsg.toString();
	}

	/**
	 * 返回处理进度百分比
	 * 
	 * @return 百分比(0-100)
	 */
	public int getProgressPercent() {
		return this.progressPercent;
	}

	public void setSchedUnit(IScheduleable forSchedUnit) {
		this.schedUnit = forSchedUnit;
	}

	public void execute() throws Exception {
		String argXml = null; // 任务参数
		try {
			Object[] enviroment = (Object[]) this.schedUnit.getEnviroment();
			if ((enviroment != null) && (enviroment.length >= 2)) {
				argXml = (String) enviroment[1];
			}

			argXml = StringEx.sNull(argXml.trim());
			if (argXml.length() > 0) {
				XMLDataObject arg;
				if (!argXml.startsWith("<?xml")) {
					arg = new XMLDataObject(
							(new XMLParser()).parseXMLStr("<?xml version=\"1.0\" encoding=\"GBK\"?>"+ argXml));
				} else {
					arg = new XMLDataObject((new XMLParser()).parseXMLStr(argXml));
				}

				// 重置XMLDataObject的当前位置
				arg.rootScrollTo("Service");
				arg.retrieve();
				System.out.println("开始执行任务[" + (String) enviroment[0]+ "]......");
				String endtime = XMLDataObjectHelper.getXDOValue(arg,"COMMAND", "");
				LogManager.getLogger().info("任务执行的结束时间【" + endtime + "】");
				this.zipXzqhFile();

				System.out.println("执行完成任务[" + (String) enviroment[0] + "]。");

				this.progressPercent = 100;
				this.sbProcessMsg.append("执行任务[" + StringEx.sNull(argXml)
						+ "]成功。");
			} else {
				throw new IllegalArgumentException("任务参数不应为空");
			}
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			String strMsg = "执行任务[" + StringEx.sNull(argXml) + "]失败："
					+ sw.toString();
			this.sbProcessMsg.append(strMsg);
			LogManager.getLogger().error(strMsg);
			throw ex;
		}
	}

	/**
	 * <p>方法名称：createQgFile()，将全国行政区划写入XZQH_JSW.TXT</p>
	 * <p>方法说明：将全国行政区划写入XZQH_JSW.TXT的方法，本方法将会往V_DM_XZQH表中检索出全部记录。</p>
	 * @since 2009-07-21
	 * @author 李靖亮
	 * @throws Exception
	 */
	private void createQgFile() throws Exception{
		String date = XtDate.getDBdate();
		date = date.replaceAll("-", "");
		String rootDir = XtSvc.getXtcs("97001", null, null);
		if (rootDir == null) {
			LogManager.getLogger().error("系统没有设置文件上传目录");
			return ;
		}
		rootDir = rootDir.trim().replaceAll("\\\\", "/");
		date = date.replaceAll("-", "");
		File fileUploadDir = new File(rootDir+"/exportxzqh/bgsj",date);
		if (!fileUploadDir.exists()) {
			if (!fileUploadDir.mkdirs()) {
				LogManager.getLogger().error("无法创建文件上传目录");
				return ;
			}
		}
		//获取总条数
		DataWindow dw = DataWindow.dynamicCreate("select count(*) ZS from v_dm_xzqh_ylsj where XZQH_DM<>'000000000000000' AND JCDM<6");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rows = dw.retrieve();
		String rowcount ="0";
		if(rows>0){
			rowcount = StringEx.sNull(dw.getItemAny(0L, "ZS"));
		}
		
		String exportPath = fileUploadDir.getAbsolutePath();
		exportPath = exportPath.replaceAll("\\\\", "/");
		String sql="select XZQH_DM,XZQH_MC from v_dm_xzqh_ylsj where XZQH_DM<>'000000000000000' AND JCDM<6 order by XZQH_DM";
		ResultSet resultSet= null;
		Connection conn = ORManager.getInstance().getConnectionPool("ctais").getConnection("0");
		Statement stmt = conn.createStatement();
		resultSet = stmt.executeQuery(sql);
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(exportPath+"/QGQHDM_"+date+".TXT"),"UTF-8");
		out.write(rowcount+"\r\n");
		out.write("区划名称,区划代码\r\n");
		if(resultSet!=null){
			try {			
				while (resultSet.next()) {
					String xzqh_dm = StringEx.sNull(resultSet.getString("XZQH_DM"));
					String xzqh_mc = StringEx.sNull(resultSet.getString("XZQH_MC"));
					String str = xzqh_mc+","+xzqh_dm+"\r\n";	
					out.write(str);
				}
			}catch (Exception e) {
	            throw e;
	        }
			finally {
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
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
		}
		out.flush();
		out.close();
	}
	
	/**
	 * <p>方法名称：createBgFile()，将变更的行政区划写入XZQH_BGDZ.TXT</p>
	 * <p>方法说明：将变更的行政区划写入XZQH_BGDZ.TXT的方法，本方法将会往XT_XZQHBGMXB表中检索出全部记录。</p>
	 * @since 2009-07-21
	 * @author 李靖亮
	 * @throws Exception
	 */
	private void createQggsFile(String qlwxzqh_dm) throws Exception{
		String date = XtDate.getDBdate();
		date = date.replaceAll("-", "");
		String rootDir = XtSvc.getXtcs("97001", null, null);
		if (rootDir == null) {
			LogManager.getLogger().error("系统没有设置文件上传目录");
			return ;
		}
		rootDir = rootDir.trim().replaceAll("\\\\", "/");
		date = date.replaceAll("-", "");
		File fileUploadDir = new File(rootDir+"/exportxzqh/bgsj",date);
		if (!fileUploadDir.exists()) {
			if (!fileUploadDir.mkdirs()) {
				LogManager.getLogger().error("无法创建文件上传目录");
				return ;
			}
		}
		
		String exportPath = fileUploadDir.getAbsolutePath();
		exportPath = exportPath.replaceAll("\\\\", "/");
		ResultSet resultSet= null;
		Connection conn = ORManager.getInstance().getConnectionPool("ctais").getConnection("0");
		Statement stmt = conn.createStatement();
		String sql="select XZQH_DM,XZQH_MC from v_dm_xzqh_ylsj where XZQH_DM like '"+qlwxzqh_dm+"%' AND JCDM<6 order by XZQH_DM";
		String fileName = "QHDM_"+qlwxzqh_dm+"0000_"+date+".TXT";
		resultSet = stmt.executeQuery(sql);
		File expDir = new File(exportPath,qlwxzqh_dm+"0000");
		if (!expDir.exists()) {
			if (!expDir.mkdirs()) {
				LogManager.getLogger().error("无法创建文件上传目录");
				return ;
			}
		}
		//获取总条数
		DataWindow dw2 = DataWindow.dynamicCreate("select count(*) ZS from v_dm_xzqh_ylsj where XZQH_DM like '"+qlwxzqh_dm+"%' AND JCDM<6");
		dw2.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rows2 = dw2.retrieve();
		String rowcounts ="0";
		if(rows2>0){
			rowcounts = StringEx.sNull(dw2.getItemAny(0L, "ZS"));
		}			
		String sjDir = expDir.getAbsolutePath().replaceAll("\\\\", "/");
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(sjDir+"/"+fileName),"UTF-8");				
		out.write(rowcounts+"\r\n");
		out.write("区划名称,区划代码\r\n");
		if(resultSet!=null){
			try {					
				while (resultSet.next()) {
					String xzqh_dm = StringEx.sNull(resultSet.getString("XZQH_DM"));
					String xzqh_mc = StringEx.sNull(resultSet.getString("XZQH_MC"));
					String qlwXzqh_dm = xzqh_dm.substring(0,6);
					if(xzqh_dm.substring(6).equals("000000000")&&!xzqh_dm.substring(4).equals("00000000000")){					
						String xjFileName = "QHDM_"+qlwXzqh_dm+"_"+date+".TXT";
						String sql1="select XZQH_DM,XZQH_MC from v_dm_xzqh_ylsj where XZQH_DM like '"+qlwXzqh_dm+"%' AND JCDM<6 order by XZQH_DM";
						DataWindow dw1 = DataWindow.dynamicCreate(sql1);
						dw1.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
						long rowCount = dw1.retrieve();
						if(rowCount>0){
							OutputStreamWriter out1 = new OutputStreamWriter(new FileOutputStream(sjDir+"/"+xjFileName),"UTF-8");
							out1.write(rowCount+"\r\n");
							out1.write("区划名称,区划代码\r\n");
							for(int j=0; j<rowCount ;j++){
								String xjxzqh_dm = StringEx.sNull(dw1.getItemAny(j, "XZQH_DM"));
								String xjxzqh_mc = StringEx.sNull(dw1.getItemAny(j, "XZQH_MC"));
								String str1 = xjxzqh_mc+","+xjxzqh_dm+"\r\n";	
								out1.write(str1);
							}
							out1.flush();
							out1.close();
						}							
					}
					String str = xzqh_mc+","+xzqh_dm+"\r\n";	
					out.write(str);
				}
			}catch (Exception e) {
	            throw e;
	        }
			finally {
	            try {
	            	if(resultSet!=null){
	            		resultSet.close();
	            	}
	            } catch (Exception e) {
	            	throw e;
	            }
	        }
		}
				
		try {
        	if(stmt!=null){
        		stmt.close();
        	}
        	if(conn!=null){
        		conn.close();
        	}
        	if(out!=null){
	        	out.flush();
	    		out.close();
        	}
        } catch (Exception e) {
        	throw e;
        }
	}
	
	/**
	 * <p>方法名称：createGsBgdzbFile()，将变更的行政区划写入XZQH_BGDZ.TXT</p>
	 * <p>方法说明：将变更的行政区划写入XZQH_BGDZ.TXT的方法，本方法将会往XT_XZQHBGMXB表中检索出全部记录。</p>
	 * @since 2009-07-21
	 * @author 李靖亮
	 * @throws Exception
	 */
	private boolean createBgdzbFile(String sjxzqh_dm) throws Exception{
		boolean flag=true;
		String date = XtDate.getDBdate();
		date = date.replaceAll("-", "");
		String rootDir = XtSvc.getXtcs("97001", null, null);
		if (rootDir == null) {
			LogManager.getLogger().error("系统没有设置文件上传目录");
			return false;
		}
		rootDir = rootDir.trim().replaceAll("\\\\", "/");
		date = date.replaceAll("-", "");
		File fileUploadDir = new File(rootDir+"/exportxzqh/bgsj",date);
		if (!fileUploadDir.exists()) {
			if (!fileUploadDir.mkdirs()) {
				LogManager.getLogger().error("无法创建文件上传目录");
				return false;
			}
		}	
		String exportPath = fileUploadDir.getAbsolutePath();
		exportPath = exportPath.replaceAll("\\\\", "/");
		ResultSet resultSet= null;
		StringBuffer sb = new StringBuffer("SELECT G.BH,G.GROUPMC,M.YSXZQH_MC,M.YSXZQH_DM,M.BGLX_DM,M.MBXZQH_MC,M.MBXZQH_DM " +
				"FROM XZQH_BGGROUP G,XZQH_BGMXB M,XZQH_BGSQD S WHERE S.SQDXH=G.SQDXH AND G.GROUPXH=M.GROUPXH" +
				" AND S.SQDZT_DM='40'");
		if(sjxzqh_dm!=null&&!sjxzqh_dm.equals("")){
			sb.append(" AND S.SBXZQH_DM LIKE '").append(sjxzqh_dm).append("%'");
		}
		sb.append(" ORDER BY G.PXH,M.PXH");
		
//		获取总条数
		StringBuffer sb1 = new StringBuffer("SELECT COUNT(*) ZS " +
				"FROM XZQH_BGGROUP G,XZQH_BGMXB M,XZQH_BGSQD S WHERE S.SQDXH=G.SQDXH AND G.GROUPXH=M.GROUPXH" +
				" AND S.SQDZT_DM='40'");
		if(sjxzqh_dm!=null&&!sjxzqh_dm.equals("")){
			sb1.append(" AND S.SBXZQH_DM LIKE '").append(sjxzqh_dm).append("%'");
		}
		DataWindow dw = DataWindow.dynamicCreate(sb1.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rows = dw.retrieve();
		String rowcount ="0";
		if(rows>0){
			rowcount = StringEx.sNull(dw.getItemAny(0L, "ZS"));
		}
		
		Connection conn = ORManager.getInstance().getConnectionPool("ctais").getConnection("0");
		Statement stmt = conn.createStatement();
		resultSet = stmt.executeQuery(sb.toString());
		OutputStreamWriter out = null;
		if(resultSet!=null){
			try {
				String sjDir="";
				String fileName = "QHDM_BGDZB_"+sjxzqh_dm+"0000_"+date+".TXT";
				if(sjxzqh_dm==null||sjxzqh_dm.equals("")){
					fileName = "QGQHDM_BGDZB_"+date+".TXT";
					sjDir = exportPath;
				}else{
					File expDir = new File(exportPath,sjxzqh_dm+"0000");
					if (!expDir.exists()) {
						if (!expDir.mkdirs()) {
							LogManager.getLogger().error("无法创建文件上传目录");
							return false;
						}
					}
					sjDir = expDir.getAbsolutePath().replaceAll("\\\\", "/");
				}
				out = new OutputStreamWriter(new FileOutputStream(sjDir+"/"+fileName),"UTF-8");				
				out.write(rowcount+"\r\n");
				out.write("调整编号,调整说明,原区划名称,原区划代码,调整类型,现区划名称,现区划代码\r\n");	
				while (resultSet.next()) {
					String bh = StringEx.sNull(resultSet.getString("BH"));
					String groupmc = StringEx.sNull(resultSet.getString("GROUPMC"));
					String ysxzqh_mc = StringEx.sNull(resultSet.getString("YSXZQH_MC"));
					String ysxzqh_dm = StringEx.sNull(resultSet.getString("YSXZQH_DM"));
					String bglx_dm = StringEx.sNull(resultSet.getString("BGLX_DM"));
					String mbxzqh_mc = StringEx.sNull(resultSet.getString("MBXZQH_MC"));
					String mbxzqh_dm = StringEx.sNull(resultSet.getString("MBXZQH_DM"));
					out.write(bh+","+groupmc+","+ysxzqh_mc+","+ysxzqh_dm+","+bglx_dm+","+mbxzqh_mc+","+mbxzqh_dm+"\r\n");
				}
				out.flush();
				out.close();
			}catch (Exception e) {
				out.flush();
				out.close();
	            throw e;
	        }
			finally {
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
	            } catch (Exception e) {
	                e.printStackTrace();
	                throw e;
	            }
	        }
		}
		return flag;
	}
	
	/**
	 * <p>方法名称：addXzqhfb()， 保存导出记录</p>
	 * <p>方法说明：保存导出记录的方法，本方法将会往XT_XZQHFB表中保存一条记录。</p>
	 * @param wjm  文件名
	 * @param wjlj 文件路径
	 * @param wjdx 文件大小
	 * @since 2009-08-14
	 * @author 李靖亮
	 * @throws Exception
	 */
	private void addXzqhfb(String wjm, String wjlj, String wjdx,UserTransaction ut)throws Exception{
		D_xt_xzqhfb xzqhfbDw = new D_xt_xzqhfb();
		xzqhfbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		xzqhfbDw.insert(-1);
		IGeneralSequence iseq = (IGeneralSequence) SequenceManager.getSequenceFactory();
		String dcxh = iseq.get("SEQ_YWXTWH_XZQHFB_XL");
		xzqhfbDw.setItemString(0, "DCXH", dcxh);
		xzqhfbDw.setItemString(0, "WJM", wjm);
		xzqhfbDw.setItemString(0, "WJLJ", wjlj);
		xzqhfbDw.setItemString(0, "WJDX", wjdx);
		xzqhfbDw.setItemString(0, "DCSJ", XtDate.getDBTime());
		xzqhfbDw.setTransObject(ut);
		xzqhfbDw.update(false);
	}
	
	/**
	 * <p>方法名称：zipXzqhFile()， 将导出的TXT文件压缩成ZIP文件</p>
	 * <p>方法说明：将导出的TXT文件压缩成ZIP文件的方法。</p>
	 * @since 2009-08-14
	 * @author 李靖亮
	 * @throws Exception
	 */
	public void zipXzqhFile()throws Exception{
		
		String date = XtDate.getDBdate();
		String rootDir = XtSvc.getXtcs("97001", null, null);
		if (rootDir == null) {
			LogManager.getLogger().error("系统没有设置文件上传目录");
			return ;
		}
		rootDir = rootDir.trim().replaceAll("\\\\", "/");
		date = date.replaceAll("-", "");
		File fileUploadDir = new File(rootDir+"/exportxzqh/bgsj",date);
		if (!fileUploadDir.exists()) {
			if (!fileUploadDir.mkdirs()) {
				LogManager.getLogger().error("无法创建文件上传目录");
				return ;
			}
		}else{
			throw new Exception("这个“"+date+"”目录已存在，无法再生成文件！");
		}

		String exportPath = fileUploadDir.getAbsolutePath();
		exportPath = exportPath.replaceAll("\\\\", "/");
		
		String fbSql = "select to_char(b.DCSJ,'yyyy-mm-dd') DCSJ from XT_XZQHFB b order by b.DCSJ desc";
		DataWindow fbDw = DataWindow.dynamicCreate(fbSql);
		fbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		String dcsj = "";
		long rows = fbDw.retrieve();
		if(rows>0){
			dcsj= StringEx.sNull(fbDw.getItemAny(0, "DCSJ"));
		}
		if(dcsj.replaceAll("-", "").equals(date)){
			throw new Exception("每天只能发布一次，如需发布多次，请先清除数据库记录！"); 
		}
		
		//查看是否有未提交申请单的省份
		if(this.queryWtj()){
			throw new Exception("存在未提交申请单的省份，请查看！");
		}
		
		//查看是否有未审核的数据
		DataWindow sqdDw = DataWindow.dynamicCreate("select count(*) WSHSJ from XZQH_BGSQD where SQDZT_DM<40");
		sqdDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		String wshsj = "";
		long rows1 = sqdDw.retrieve();
		if(rows1>0){
			wshsj= StringEx.sNull(sqdDw.getItemAny(0, "WSHSJ"));
			if(!wshsj.equals("0")&&!wshsj.equals("")){
				throw new Exception("存在未审核的申请单！");
			}
		}
		//查看审核的数据
		DataWindow sqdDw1 = DataWindow.dynamicCreate("select SQDXH,SBXZQH_DM from XZQH_BGSQD where SQDZT_DM='40'");
		sqdDw1.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rows2 = sqdDw1.retrieve();
		if(rows2<=0){
			throw new Exception("不存在已审核的申请单！");
		}
		

		//导出各省区划和对照表
		DataWindow sqdDw2 = DataWindow.dynamicCreate("select substr(s.SBXZQH_DM,0,2) SJXZQH_DM from XZQH_BGSQD s where SQDZT_DM='40' group by substr(s.SBXZQH_DM,0,2) order by  substr(s.SBXZQH_DM,0,2)");
		sqdDw2.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rows3 = sqdDw2.retrieve();
		UserTransaction ut = new UserTransaction();
		try{
			ut.begin();
			//导出全国区划和对照表
			if(this.createBgdzbFile("")){
				this.createQgFile();
			}			
			for(int i=0; i<rows3; i++){
				String sjxzqh_dm = StringEx.sNull(sqdDw2.getItemAny(i, "SJXZQH_DM"));
				if(this.createBgdzbFile(sjxzqh_dm)){
					this.createQggsFile(sjxzqh_dm);
				}
				//将申请单状态置为已发布
				D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
				d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
				StringBuffer sql = new StringBuffer();
				sql.append("SBXZQH_DM like '").append(sjxzqh_dm).append("%' AND SQDZT_DM='40'");
				d_xzqh_bgsqd.setFilter(sql.toString());
				long counts = d_xzqh_bgsqd.retrieve();	
				for(int j=0; j<counts; j++){
					d_xzqh_bgsqd.setItemString(j, "SQDZT_DM","50");
					d_xzqh_bgsqd.setTransObject(ut);
					d_xzqh_bgsqd.update(false);
				}
			}
	
		
			//删除旧的ZIP文件
			File oldZipfile = new File(exportPath+"/QGQHDM_"+date+".ZIP");
			if(oldZipfile.exists())
				oldZipfile.delete();
			//添加到压缩文件
			ZipUtil zip = new ZipUtil(rootDir+"/exportxzqh/bgsj/QGQHDM_"+date+".ZIP");
			zip.addFile(exportPath,"",false);
			zip.zipDone();
			FileUtils.moveFile(rootDir+"/exportxzqh/bgsj/QGQHDM_"+date+".ZIP",exportPath+"/QGQHDM_"+date+".ZIP");
			File newZipFile = new File(exportPath+"/QGQHDM_"+date+".ZIP");
			String wjdx="0";
			if(newZipFile.exists()){
				wjdx = String.valueOf(newZipFile.length());
			}
			String savePath = exportPath.substring(rootDir.length(), exportPath.length());			
			//保存导出记录
			addXzqhfb(date+"全国各省区划数据和变更对照表",savePath+"/QGQHDM_"+date+".ZIP",wjdx,ut);
			ut.commit();
		}catch(Exception e){
			ut.rollback();
			FileUtils.deleteDir(exportPath);
			throw e;
		}	
	}
	
	/**
	 * <p>方法名称：queryWtj</p>
	 * <p>方法描述：查询未提交申请单的省份</p>
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-12-02
	 */
	private boolean queryWtj() throws Exception
	{
		boolean flag = false;
//		获取系统参数:不提交申请的的省份区划
		String xzqh = StringEx.sNull(XtSvc.getXtcs("97002", null, null));
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  XZQH_DM,XZQH_MC FROM V_DM_XZQH ");
		sql.append(" WHERE XZQH_DM NOT IN (SELECT DISTINCT(SUBSTR(SBXZQH_DM,1,2))||'0000000000000' FROM XZQH_BGSQD XZQH_BGSQD WHERE SQDZT_DM<"+Common.XZQH_SQDZT_YFB+") AND JCDM ='1' ");
		if(!xzqh.equals(""))
		{
			sql.append(" AND SUBSTR(XZQH_DM,1,2) NOT IN ("+xzqh+") ");
		}
		sql.append(" ORDER BY XZQH_DM ");
		
		DataWindow dw = DataWindow.dynamicCreate(sql.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rows = dw.retrieve();
		if (rows > 0){	
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 事件:任务加入队列
	 * 
	 * @throws Exception
	 *             错误
	 */
	public void queueEntered() throws Exception {
		LogManager.getLogger().debug(
				this.schedUnit.getTaskDescription() + "： 任务加入队列。");
	}

	/**
	 * 任务取消
	 * 
	 * @throws Exception
	 *             取消失败
	 */
	public void canceled() throws Exception {
		LogManager.getLogger().debug(
				this.schedUnit.getTaskDescription() + "： 任务取消。");
	}

	/**
	 * 任务执行关闭
	 */
	public void closed() {
		LogManager.getLogger().debug(
				this.schedUnit.getTaskDescription() + "： 任务执行关闭。");
	}

	/**
	 * 任务出队列
	 * 
	 * @throws Exception
	 *             取消失败
	 */
	public void queueOut() throws Exception {
		LogManager.getLogger().debug(
				this.schedUnit.getTaskDescription() + "： 任务出队列。");
	}

	XMLDataObject xdoArg = null; // 命令参数

	/**
	 * initCommand
	 * 
	 * @param forArgXdo
	 *            XMLDataObject
	 */
	public void initCommand(XMLDataObject xdoArg) {
		this.xdoArg = xdoArg;
	}

	/**
	 * doCommand
	 * 
	 * @return Response
	 */
	public Response doCommand() {
		try {
			// 重置XMLDataObject的当前位置
			this.xdoArg.rootScrollTo("Service");
			this.xdoArg.retrieve();

			String cmd = XMLDataObjectHelper.getXDOValue(this.xdoArg,
					"COMMAND", "");

			BatchTask bt = new BatchTask();
			bt.setId(this.getClass().getName());
			bt.setGroupTitle("导出行政区划");
			bt.setGroupId(this.getClass().getName() + "_GROUP");
			bt.setGroupYxjb(IScheduleable.DEFAULT_GROUP_PRIORITY);
			bt.setPriority(1);
			bt.setBz(this.getClass().getName());
			bt.setRedo("N");
			bt.setBPersistent(true);
			bt.setPh(DbHelper.newSequence());
			bt.setDdh(DbHelper.newSequence());
			bt.setTaskDescription("导出行政区划[" + this.getClass().getName()
					+ "]");
			bt.setTaskExecutor(this.getClass().getName());

			// 设置批处理参数
			StringBuffer sbXml = new StringBuffer("<CtaisSession><Service>");
			sbXml.append("<COMMAND>" + cmd + "</COMMAND>");
			sbXml.append("</Service></CtaisSession>");
			bt.setEnvironment(new Object[] { this.getClass().getName(),
					sbXml.toString() });

			BatchFactory.getTaskScheduler().addTask(bt);

			return new Response(0, 2000, "添加到任务队列成功。", "");
		} catch (Exception ex) {
			return new Response(1, 2005, "添加到任务队列中失败！", "");
		}
	}

}
