package com.padis.business.xzqhwh.impl;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.padis.business.xzqhwh.common.IXzqhYwsjbg;
import com.padis.business.xzqhwh.common.XzqhbgBean;
import com.padis.common.xtservice.connection.ConnConfig;
import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.or.ORManager;
import ctais.services.or.UserTransaction;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p>
 * Title: 接口com.padis.business.xzqhwh.common.IXzqhYwsjbg 业务数据变更公共实现类
 * </P>
 * <p>
 * Description:实现业务数据变更公共业务操作<br>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: DigitalChina Co.Ltd
 * </p>
 * 
 * @auther wujiangb
 * @version 1.0
 * @since 2009-7-10 修改人： 修改时间： 修改内容： 修改版本号：
 */
public abstract class AbstractXzqhYwsjbg implements IXzqhYwsjbg {

	/*
	 * 定义业务数据变更执行SQL集合,先执行业务数据变更,再记录业务日志
	 */
	protected List ywsjbgFirstSqlList = new ArrayList();

	/*
	 * 定义业务数据变更执行SQL集合,先记录业务日志,再执行业务数据变更
	 */
	protected List ywsjlogFirstSqlList = new ArrayList();

	private int batchSize = 100; // 暂定每100条提交一次

	private boolean batchMode = false; // 如果为true则批量提交，false则为全部提交，默认为false

	private static Map map = new HashMap();

	static {
		map.put("J", "奖励扶助");
		map.put("T", "特别扶助");
		map.put("S", "少生快富");
		map.put("L", "育龄妇女流动人口");
		map.put("K", "快速调查");
		map.put("Z", "事业统计");
	}

	/*
	 * 设置批量提交记录数
	 */
	public void setBatchSize(int size) {
		this.batchSize = size;
	}

	/*
	 * 取得批量提交记录数
	 */
	public int getBatchSize() {
		return this.batchSize;
	}

	/**
	 * <p>
	 * 方法名称：ywsjBgExecute
	 * </p>
	 * <p>
	 * 方法描述：执行sql语句
	 * </p>
	 * 
	 * @param sql
	 * @param ut
	 * @throws Exception
	 * @author admin
	 * @since 2009-7-13
	 */
	protected boolean ywsjBgExecute(UserTransaction ut) throws Exception {
		if(ywsjbgFirstSqlList.size() == 0 && ywsjlogFirstSqlList.size() == 0){
			return true;
		}
		boolean result = false;
		long updateRowNum = 0;
		long insertRowNum = 0;
		String ywsjBgSql = "";
		String ywsjBgLogSql = "";
		// 先执行业务数据变更，再记录变更日志
		if (ywsjbgFirstSqlList != null && ywsjbgFirstSqlList.size() > 0) {
			try {
				LogManager.getLogger().info("-------------------------业务数据变更单条执行sql开始打印----------------------");
				for (int i = 0; i < ywsjbgFirstSqlList.size(); i++) {
					YwsjBgSqlBean bean = (YwsjBgSqlBean) ywsjbgFirstSqlList
							.get(i);
					if(bean.getSqlBuildType() == 1){
						ywsjBgSql = bean.getYwsjBgSql();
					}
					if(bean.getSqlBuildType() == 2){
						ywsjBgSql = bean.getBuildYwsjBgSql();
					}
					if (ywsjBgSql != null && !ywsjBgSql.equals("")) {
						LogManager.getLogger().info(ywsjBgSql);
						DataWindow dw = DataWindow.dynamicCreate(ywsjBgSql);
						dw.setConnectionName(ConnConfig.getConnectionName(this
								.getClass()));// 设置连接池
						dw.setTransObject(ut);
						dw.update(false);
						updateRowNum = dw.getUpdatedRowNum();
					}
					ywsjBgSql = "";
					// 如果修改业务数据记录条数为零则不执行logSql
					if (updateRowNum > 0L) {
						if(bean.getSqlBuildType() == 1){
							ywsjBgLogSql = bean.getYwsjBgLogSql();
						}
						if(bean.getSqlBuildType() == 2){
							bean.setBgsjl((int)updateRowNum);
							ywsjBgLogSql = bean.getBuildYwsjBgLogSql();
						}
						if (ywsjBgLogSql != null && !ywsjBgLogSql.equals("")) {
							LogManager.getLogger().info(ywsjBgLogSql);
							DataWindow dw = DataWindow
									.dynamicCreate(ywsjBgLogSql);
							dw.setConnectionName(ConnConfig
									.getConnectionName(this.getClass()));// 设置连接池
							dw.setTransObject(ut);
							dw.update(false);
						}
					}
					ywsjBgLogSql = "";
				}
				LogManager.getLogger().info("-------------------------业务数据变更单条执行sql结束打印----------------------");
			} catch (Exception e) {
				LogManager.getLogger().log(e);
				result = false;
				throw new Exception("业务系统行政区划变更失败:" + ywsjBgSql + ywsjBgLogSql
						+ ";" + e.getMessage());
			}
			result = true;
		}
		// 先记录变更日志，再执行业务数据变更
		if (ywsjlogFirstSqlList != null && ywsjlogFirstSqlList.size() > 0) {
			try {
				LogManager.getLogger().info("-------------------------业务数据变更单条执行sql开始打印----------------------");
				for (int i = 0; i < ywsjlogFirstSqlList.size(); i++) {
					YwsjBgSqlBean bean = (YwsjBgSqlBean) ywsjlogFirstSqlList
							.get(i);
					if(bean.getSqlBuildType() == 1){
						ywsjBgLogSql = bean.getYwsjBgLogSql();
					}
					if(bean.getSqlBuildType() == 2){
						ywsjBgLogSql = bean.getBuildYwsjBgLogSql();
					}
					if (ywsjBgLogSql != null && !ywsjBgLogSql.equals("")) {
						LogManager.getLogger().info(ywsjBgLogSql);
						DataWindow dw = DataWindow.dynamicCreate(ywsjBgLogSql);
						dw.setConnectionName(ConnConfig.getConnectionName(this
								.getClass()));
						dw.setTransObject(ut);
						dw.update(false);
						insertRowNum = dw.getUpdatedRowNum();
					}
					ywsjBgLogSql = "";
					// 如果日志记录条数为零则不执行ywsjSql
					if (insertRowNum > 0L) {
						if(bean.getSqlBuildType() == 1){
							ywsjBgSql = bean.getYwsjBgSql();
						}
						if(bean.getSqlBuildType() == 2){
							ywsjBgSql = bean.getBuildYwsjBgSql();
						}
						if (ywsjBgSql != null && !ywsjBgSql.equals("")) {
							LogManager.getLogger().info(ywsjBgSql);
							DataWindow dw = DataWindow.dynamicCreate(ywsjBgSql);
							dw.setConnectionName(ConnConfig
									.getConnectionName(this.getClass()));
							dw.setTransObject(ut);
							dw.update(false);
						}
					}
					ywsjBgSql = "";
				}
				LogManager.getLogger().info("-------------------------业务数据变更单条执行sql结束打印----------------------");
			} catch (Exception e) {
				LogManager.getLogger().log(e);
				result = false;
				throw new Exception("业务系统行政区划变更失败:" + ywsjBgSql + ywsjBgLogSql
						+ ";" + e.getMessage());
			}
			result = true;
		}
		return result;
	}

	/**
	 * <p>
	 * 方法名称：
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @param sql
	 * @param ut
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-7-14
	 */
	protected boolean ywsjBgBatchExecute() throws Exception {
		System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
		if(ywsjbgFirstSqlList.size() == 0 && ywsjlogFirstSqlList.size() == 0){
			return true;
		}
		System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		boolean result = false;
		String ywsjBgSql = "";
		String ywsjBgLogSql = "";
		int count[] = null;
		int batchCount = 0;
		Connection conn = ORManager.getInstance().getConnectionPool("ctais")
				.getConnection("0");
		conn.setAutoCommit(false);
		// 先执行业务数据变更，再记录变更日志
		if (ywsjbgFirstSqlList != null && ywsjbgFirstSqlList.size() > 0) {
			System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
			Statement stmt = conn.createStatement();
			try {
				System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
				LogManager.getLogger().info("-------------------------业务数据变更批量执行sql开始打印----------------------");
				for (int i = 0; i < ywsjbgFirstSqlList.size(); i++) {
					System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
					YwsjBgSqlBean bean = (YwsjBgSqlBean) ywsjbgFirstSqlList
							.get(i);
					if(bean.getSqlBuildType() == 1){
						ywsjBgSql = bean.getYwsjBgSql();
					}
					if(bean.getSqlBuildType() == 2){
						ywsjBgSql = bean.getBuildYwsjBgSql();
					}
					
					//ywsjBgSql = bean.getYwsjBgSql();
					if (ywsjBgSql != null && !ywsjBgSql.equals("")) {
						stmt.addBatch(ywsjBgSql);
						LogManager.getLogger().info(ywsjBgSql);
						batchCount++;
					} else {
						throw new Exception("ywsjbgFirstSqlList存在空的业务数据变更Sql!");
					}
					if (batchCount % batchSize == 0) {
						int s = batchCount / batchSize;
						count = stmt.executeBatch();
						LogManager.getLogger().info("-------------------------业务数据变更一百条提交----------------------");
						for (int j = 0; j < count.length; j++) {
							if (count[j] > 0) {
								YwsjBgSqlBean beanTemp = ((YwsjBgSqlBean) ywsjbgFirstSqlList
										.get((s - 1) * batchSize + j));
								if(beanTemp.getSqlBuildType() == 1){
									ywsjBgLogSql = beanTemp.getYwsjBgLogSql();
								}
								if(beanTemp.getSqlBuildType() == 2){
									beanTemp.setBgsjl(count[j]);
									ywsjBgLogSql = beanTemp.getBuildYwsjBgLogSql();
								}								
								if (ywsjBgLogSql != null
										&& !ywsjBgLogSql.equals("")) {
									stmt.addBatch(ywsjBgLogSql);
									LogManager.getLogger().info(ywsjBgLogSql);
								}
							}
							LogManager.getLogger().info("ssqql324324l============================"+ywsjBgLogSql);
						}
						stmt.executeBatch();
						LogManager.getLogger().info("-------------------------业务数据变更一百条提交----------------------");
					}
				}

				if (batchCount % batchSize != 0) {
					int s = batchCount / batchSize;
					count = stmt.executeBatch();
					LogManager.getLogger().info("-------------------------业务数据变更一百条提交----------------------");
					for (int j = 0; j < count.length; j++) {
						if (count[j] > 0) {
							YwsjBgSqlBean beanTemp = ((YwsjBgSqlBean) ywsjbgFirstSqlList
									.get(s * batchSize + j));
								
							if(beanTemp.getSqlBuildType() == 1){
								ywsjBgLogSql = beanTemp.getYwsjBgLogSql();
							}
							if(beanTemp.getSqlBuildType() == 2){
								beanTemp.setBgsjl(count[j]);
								ywsjBgLogSql = beanTemp.getBuildYwsjBgLogSql();
							}
							
							if (ywsjBgLogSql != null && !ywsjBgLogSql.equals("")) {
								stmt.addBatch(ywsjBgLogSql);
								LogManager.getLogger().info(ywsjBgLogSql);
							}
						}
					}
					LogManager.getLogger().info("ssqqll============================"+ywsjBgLogSql);
					System.out.println("ssqqll============================"+ywsjBgLogSql);
					stmt.executeBatch();
					LogManager.getLogger().info("-------------------------业务数据变更批量执行sql结束打印----------------------");
				}
			} catch (Exception e) {
				LogManager.getLogger().log(e);
				result = false;
				throw new Exception("业务系统行政区划变更失败1:" + e.getMessage());
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
			result = true;
		}
		batchCount = 0;
		// 先记录变更日志，再执行业务数据变更
		if (ywsjlogFirstSqlList != null && ywsjlogFirstSqlList.size() > 0) {
			Statement stmt = conn.createStatement();
			try {
				LogManager.getLogger().info("-------------------------业务数据变更批量执行sql开始打印----------------------");
				for (int i = 0; i < ywsjlogFirstSqlList.size(); i++) {
					YwsjBgSqlBean bean = (YwsjBgSqlBean) ywsjlogFirstSqlList
							.get(i);
					
					if(bean.getSqlBuildType() == 1){
						ywsjBgLogSql = bean.getYwsjBgLogSql();
						LogManager.getLogger().info("1111111111111============================"+ywsjBgLogSql);
						System.out.println("ssqqll============================"+ywsjBgLogSql);
					}
					if(bean.getSqlBuildType() == 2){
						ywsjBgLogSql = bean.getBuildYwsjBgLogSql();
						LogManager.getLogger().info("222222222222222============================"+ywsjBgLogSql);
						System.out.println("ssqqll============================"+ywsjBgLogSql);
					}
					
					if (ywsjBgLogSql != null && !ywsjBgLogSql.equals("")) {
						stmt.addBatch(ywsjBgLogSql);
						LogManager.getLogger().info(ywsjBgLogSql);
						LogManager.getLogger().info("33333333333333============================"+ywsjBgLogSql);
						System.out.println("ssqqll============================"+ywsjBgLogSql);
						batchCount++;
					} else {
						throw new Exception("ywsjlogFirstSqlList存在空的记录变更日志Sql!");
					}
					LogManager.getLogger().info("ssqqll2222============================"+ywsjBgLogSql);
					System.out.println("ssqqll============================"+ywsjBgLogSql);
					if (batchCount % batchSize == 0) {
						int s = batchCount / batchSize;
						count = stmt.executeBatch();
						LogManager.getLogger().info("-------------------------业务数据变更一百条提交----------------------");
						for (int j = 0; j < count.length; j++) {
							if (count[j] > 0) {
								YwsjBgSqlBean beanTemp = ((YwsjBgSqlBean) ywsjlogFirstSqlList
										.get((s - 1) * batchSize + j));
								if(beanTemp.getSqlBuildType() == 1){
									ywsjBgSql = beanTemp.getYwsjBgSql();
									LogManager.getLogger().info("444444444444============================"+ywsjBgSql);
									System.out.println("ssqqll============================"+ywsjBgSql);
								}
								if(beanTemp.getSqlBuildType() == 2){
									ywsjBgSql = beanTemp.getBuildYwsjBgSql();
									LogManager.getLogger().info("5555555555555============================"+ywsjBgSql);
									System.out.println("ssqqll============================"+ywsjBgSql);
								}								
								
								if (ywsjBgSql != null && !ywsjBgSql.equals("")) {
									LogManager.getLogger().info("666666666666============================"+ywsjBgSql);
									System.out.println("ssqqll============================"+ywsjBgSql);
									stmt.addBatch(ywsjBgSql);
									LogManager.getLogger().info(ywsjBgSql);
								}
								
							}
						}
						LogManager.getLogger().info("ssqqll333============================"+ywsjBgSql);
						System.out.println("ssqqll============================"+ywsjBgSql);
						stmt.executeBatch();
						LogManager.getLogger().info("-------------------------业务数据变更一百条提交----------------------");
					}
				}
				if (batchCount % batchSize != 0) {
					int s = batchCount / batchSize;
					count = stmt.executeBatch();
					LogManager.getLogger().info("-------------------------业务数据变更一百条提交----------------------");
					for (int j = 0; j < count.length; j++) {
						if (count[j] > 0) {
							YwsjBgSqlBean beanTemp = ((YwsjBgSqlBean) ywsjlogFirstSqlList
									.get(s * batchSize + j));
							if(beanTemp.getSqlBuildType() == 1){
								ywsjBgSql = beanTemp.getYwsjBgSql();
								LogManager.getLogger().info("ssqqll333============================"+ywsjBgSql);
								System.out.println("ssqqll============================"+ywsjBgSql);
							}
							if(beanTemp.getSqlBuildType() == 2){
								ywsjBgSql = beanTemp.getBuildYwsjBgSql();
								LogManager.getLogger().info("ssqqll333============================"+ywsjBgSql);
								System.out.println("ssqqll============================"+ywsjBgSql);
							}			
							
							
							if (ywsjBgSql != null && !ywsjBgSql.equals("")) {
								LogManager.getLogger().info("ssqqll333============================"+ywsjBgSql);
								System.out.println("ssqqll============================"+ywsjBgSql);
								stmt.addBatch(ywsjBgSql);
								LogManager.getLogger().info(ywsjBgSql);
							}
							
						}
					}
					LogManager.getLogger().info("ssqql44444============================"+ywsjBgSql);
					System.out.println("ssqqll============================"+ywsjBgSql);
					stmt.executeBatch();
					LogManager.getLogger().info("-------------------------业务数据变更批量执行sql结束打印----------------------");
				}
			} catch (Exception e) {
				LogManager.getLogger().log(e);
				result = false;
				throw new Exception("业务系统行政区划变更失败2:" + e.getMessage());
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
			result = true;
		}
		return result;
	}

	/*
	 * 接口方法isAllowBatchUpdate实现
	 * 
	 * @see com.padis.business.xzqhwh.common.IXzqhYwsjbg#isAllowBatchUpdate(java.util.List)
	 */
	public boolean isAllowBatchUpdate(List xzqhbgxx) throws Exception {
		boolean temp = false;
		if (xzqhbgxx != null || xzqhbgxx.size() > 0) {
			String srcXzqhDm = "";
			String destXzqhDm = "";
			for (int i = 0; i < xzqhbgxx.size(); i++) {
				XzqhbgBean xzqhbgBean = (XzqhbgBean) xzqhbgxx.get(i);
				srcXzqhDm = StringEx.sNull(xzqhbgBean.getSrcXzqhdm());
				destXzqhDm = StringEx.sNull(xzqhbgBean.getDestXzqhdm());
				String sql = "SELECT DISTINCT(YWLX_DM) AS YWLX_DM FROM XZQHBG_YWSJ_NOTEXISTS_DM_XZQH WHERE XZQH_DM = '"
						+ destXzqhDm + "'";
				DataWindow dw = DataWindow.dynamicCreate(sql);
				dw.setConnectionName(ConnConfig.getConnectionName(this
						.getClass()));// 设置连接池
				long rows = dw.retrieve();
				if (rows > 0) {
					XMLDataObject xdo = dw.toXDO();
					StringBuffer sb = new StringBuffer();
					sb.append("在业务系统");
					for (int j = 0; j < rows; j++) {
						String ywxtMc = StringEx.sNull(map.get(xdo.getItemAny(
								j, "YWLX_DM")));
						sb.append("【");
						sb.append(ywxtMc);
						sb.append("】");
						if (j < rows - 1) {
							sb.append(",");
						}
					}
					sb.append("中存在对新代码【");
					sb.append(destXzqhDm);
					sb.append("】的引用，因此不允许将现行政区划代码【");
					sb.append(srcXzqhDm);
					sb.append("】修改为新行政区划代码【");
					sb.append(destXzqhDm);
					sb.append("】");
					throw new Exception(sb.toString());
				}
			}
			temp = true;
		}
		return temp;
	}

	/*
	 * 接口方法updateYwsj实现
	 * 
	 * @see com.padis.business.xzqhwh.common.IXzqhYwsjbg#updateYwsj(java.util.List,
	 *      ctais.services.or.UserTransaction)
	 */
	public boolean updateYwsj(List xzqhbgxx, UserTransaction ut)
			throws Exception {
		boolean result = false;
		if (xzqhbgxx == null && xzqhbgxx.size() == 0) {
			return false;
		} else {
			// 拼装所有的sql
			this.buildSqlList(xzqhbgxx);   //4444444444444
			// 执行sql语句，如果batchMode为true则批量提交，false则为全部提交
			if (batchMode) {
				result = ywsjBgBatchExecute();
			} else {
				result = ywsjBgExecute(ut);
			}
		}
		return result;
	}

	/**
	 * <p>
	 * 方法名称：buildSqlList
	 * </p>
	 * <p>
	 * 方法描述：拼装sql
	 * </p>
	 * 
	 * @param xzqhbgxx
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-7-14
	 */
	protected abstract void buildSqlList(List xzqhbgxx) throws Exception;

	/**
	 * <p>
	 * 方法名称：addYwsjbgSql
	 * </p>
	 * <p>
	 * 方法描述：将变更涉及的sql语句加入到List中,先变更业务数据
	 * </p>
	 * 
	 * @param ywsjBgSql
	 *            变更业务数据的sql语句
	 * @param ywsjBgLogSql
	 *            记录变更业务数据的日志sql语句
	 * @author admin
	 * @since 2009-7-13
	 */
	protected void addYwsjbgFirstSql(String ywsjBgSql, String ywsjBgLogSql) {
		ywsjbgFirstSqlList.add(new YwsjBgSqlBean(ywsjBgSql, ywsjBgLogSql));
	}
	
	/**
	 * <p>
	 * 方法名称：addYwsjbgSql
	 * </p>
	 * <p>
	 * 方法描述：将变更涉及的sql语句加入到List中,先变更业务数据
	 * </p>
	 * @param ywsjBgSqlBean
	 *            变更业务数据对象
	 * @author admin
	 * @since 2009-7-13
	 */
	protected void addYwsjbgFirstSql(YwsjBgSqlBean ywsjBgSqlBean) {
		ywsjbgFirstSqlList.add(ywsjBgSqlBean);
	}
	 
	/**
	 * <p>
	 * 方法名称：addYwsjbgSql
	 * </p>
	 * <p>
	 * 方法描述：将变更涉及的sql语句加入到List中,先记录日志
	 * </p>
	 * 
	 * @param ywsjBgSql
	 *            变更业务数据的sql语句
	 * @param ywsjBgLogSql
	 *            记录变更业务数据的日志sql语句
	 * @author admin
	 * @since 2009-7-13
	 */
	protected void addYwsjlogFirstSql(String ywsjBgSql, String ywsjBgLogSql) {
		ywsjlogFirstSqlList.add(new YwsjBgSqlBean(ywsjBgSql, ywsjBgLogSql));
	}
	
	/**
	 * <p>
	 * 方法名称：addYwsjbgSql
	 * </p>
	 * <p>
	 * 方法描述：将变更涉及的sql语句加入到List中,先记录日志
	 * </p>
	 * 
	 * @param ywsjBgSqlBean
	 *            变更业务数据对象
	 * @author admin
	 * @since 2009-7-13
	 */
	protected void addYwsjlogFirstSql(YwsjBgSqlBean ywsjBgSqlBean) {
		ywsjlogFirstSqlList.add(ywsjBgSqlBean);
	}

	/**
	 * <p>
	 * 方法名称：buildUpdateSql
	 * </p>
	 * <p>
	 * 方法描述：根据表名、列名、源行政区划、目的行政区划构造更新业务数据的sql语句
	 * </p>
	 * 
	 * @param tableName
	 *            数据表名
	 * @param xzqhColumnName
	 *            行政区划列名
	 * @param srcXzqhdm
	 *            源行政区划
	 * @param destXzqhdm
	 *            目的行政区划
	 * @return
	 * @author admin
	 * @since 2009-7-13
	 */
	protected String buildUpdateSql(String tableName, String xzqhColumnName,
			String srcXzqhdm, String destXzqhdm) {
		String sql = "UPDATE " + tableName + " SET " + xzqhColumnName + "='"
				+ destXzqhdm + "' WHERE " + xzqhColumnName + "='" + srcXzqhdm
				+ "'";
		return sql;
	}

	/**
	 * <p>
	 * 方法名称：buildLogSql
	 * </p>
	 * <p>
	 * 方法描述：构造行政区划变更时业务日志
	 * </p>
	 * 
	 * @param tableName
	 *            数据表名
	 * @param xzqhColumnName
	 *            行政区划列名
	 * @param xzqhbgBean
	 * @param ywxtDm
	 *            业务系统代码:L,J,S,D,K等
	 * @param ywbMc
	 * @param ywbZjmc
	 * @param logType
	 *            1:插入日志时采用insert into select方式2:插入日志时采用insert into values方式
	 * @return
	 * @author admin
	 * @since 2009-7-13
	 */
	protected String buildLogSql(String tableName, String xzqhColumnName,
			XzqhbgBean xzqhbgBean, String ywxtDm, String ywbMc, String ywbZjmc,
			int logType) {
		String sql = "";
		String ywbZjmcCol = "";
		String zjmcArr[] = null;
		
		if (logType == 1) {
			if(ywbZjmc.indexOf(",") > -1){
				zjmcArr = ywbZjmc.split(",");
				for(int i=0;i<zjmcArr.length;i++){
					if(i==zjmcArr.length-1){
						ywbZjmcCol = ywbZjmcCol + zjmcArr[i];
					}else{
						ywbZjmcCol = ywbZjmcCol + zjmcArr[i] + " || '~' ||";
					}
					
				}
			}else{
				ywbZjmcCol = ywbZjmc;
			}
			
			// INSERT INTO XZQHBG_YWSJ_LOG SELECT
			// SQBXH,SQBMXXH,SRCXZQHDM,DESTXZQHDM,LOG_SJ,YWXT_DM,YWB_MC,YWB_ZJMC,YWB_ZJXH,YWB_XZQHMC
			// FROM LNJF_JFDX
			sql = "INSERT INTO XZQHBG_YWSJ_LOG(SQBXH,SQBMXXH,SRCXZQHDM,DESTXZQHDM,LOG_SJ,YWXT_DM,YWB_MC,YWB_ZJMC,YWB_ZJXH,YWB_XZQHMC) " + " SELECT '"
					+ xzqhbgBean.getSqbxh() + "' , '" + xzqhbgBean.getSqbmxXh()
					+ "' ,'" + xzqhbgBean.getSrcXzqhdm() + "' ,'"
					+ xzqhbgBean.getDestXzqhdm() + "' ,sysdate,'" + ywxtDm
					+ "','" + ywbMc + "','" + ywbZjmc + "'," + ywbZjmcCol + ",'"
					+ xzqhColumnName + "' FROM " + tableName + " where "
					+ xzqhColumnName + "='" + xzqhbgBean.getSrcXzqhdm() + "' ";
		} else if (logType == 2) {
			// INSERT INTO XZQHBG_YWSJ_LOG
			// (SQBXH,SQBMXXH,SRCXZQHDM,DESTXZQHDM,LOG_SJ,YWXT_DM,YWB_MC,YWB_ZJMC,YWB_ZJXH,YWB_XZQHMC)
			// VALUES()
			sql = "INSERT INTO XZQHBG_YWSJ_LOG(SQBXH,SQBMXXH,SRCXZQHDM,DESTXZQHDM,LOG_SJ,YWXT_DM,YWB_MC,YWB_ZJMC,YWB_ZJXH,YWB_XZQHMC)"
					+ " values( '"
					+ xzqhbgBean.getSqbxh()
					+ "' , '"
					+ xzqhbgBean.getSqbmxXh()
					+ "' ,'"
					+ xzqhbgBean.getSrcXzqhdm()
					+ "' ,'"
					+ xzqhbgBean.getDestXzqhdm()
					+ "' ,sysdate,'"
					+ ywxtDm
					+ "','"
					+ ywbMc
					+ "','"
					+ ywbZjmc
					+ "',NULL"
					+ ",'"
					+ xzqhColumnName + "')";
		}

		return sql;
	}

	// /////////////////////////////////业务系统数据变更日志表结构////////////////////////////
	// CREATE TABLE XZQHBG_YWSJ_LOG
	// (
	// SQBXH VARCHAR2(20 BYTE) NOT NULL, --申请表序号
	// SQBMXXH VARCHAR2(20 BYTE) NOT NULL, --申请表明细数据序号
	// SRCXZQHDM CHAR(15) NOT NULL, --原行政区划代码
	// DESTXZQHDM CHAR(15) NOT NULL, --目标区划代码
	// LOG_SJ DATE NOT NULL, --日志时间
	// YWXT_DM CHAR(1) NOT NULL, --业务系统代码:L,J,S,D,K等
	// YWB_MC VARCHAR2(40 BYTE) NOT NULL, --业务表名称
	// YWB_ZJMC VARCHAR2(40 BYTE), --业务表主键列名
	// YWB_ZJXH VARCHAR2(20 BYTE), --业务表主键序号
	// YWB_XZQHMC VARCHAR2(40 BYTE) NOT NULL --业务表行政区划列名称
	// )
	//
	// COMMENT ON TABLE XZQHBG_YWSJ_LOG IS '行政区划变更业务数据日志表';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.SQBXH IS '申请表序号';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.SQBMXXH IS '申请表明细数据序号';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.SRCXZQHDM IS '原行政区划代码';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.DESTXZQHDM IS '目标区划代码';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.LOG_SJ IS '日志时间';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.YWXT_DM IS '业务系统代码:L,J,S,D,K等';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.YWB_MC IS '业务表名称';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.YWB_ZJMC IS '业务表主键列名';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.YWB_ZJXH IS '业务表主键序号';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.YWB_XZQHMC IS '业务表行政区划列名称';

	public boolean isBatchMode() {
		return batchMode;
	}

	public void setBatchMode(boolean batchMode) {
		this.batchMode = batchMode;
	}
	
}
