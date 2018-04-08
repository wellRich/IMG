package com.padis.business.xzqhwh.impl;

import com.padis.business.xzqhwh.common.XzqhbgBean;

import ctais.services.log.LogManager;


/**
 * <p>
 * Title:YwsjBgSqlBean
 * </P>
 * <p>
 * Description:包装业务数据变更时的sql语句
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: DigitalChina Co.Ltd
 * </p>
 */
public class YwsjBgSqlBean {
	
	/**
	 * 业务数据行政区划变更sql语句
	 */
	private String ywsjBgSql = "";

	/**
	 * 业务数据行政区划变更日志sql语句
	 */
	private String ywsjBgLogSql = "";

	/**
	 * 构造方法
	 * 
	 * @param ywsjBgSql
	 * @param ywsjBgLogSql
	 */
	public YwsjBgSqlBean(String ywsjBgSql, String ywsjBgLogSql) {
		setYwsjBgLogSql(ywsjBgLogSql);
		setYwsjBgSql(ywsjBgSql);
	}
	
	public YwsjBgSqlBean(){
		
	}

	public String getYwsjBgLogSql() {
		return ywsjBgLogSql;
	}

	public void setYwsjBgLogSql(String ywsjBgLogSql) {
		this.ywsjBgLogSql = ywsjBgLogSql;
	}

	public String getYwsjBgSql() {
		return ywsjBgSql;
	}

	public void setYwsjBgSql(String ywsjBgSql) {
		this.ywsjBgSql = ywsjBgSql;
	}
	
	//为了实现能够在使用SQL时才动态拼写SQL的方法，现增加了以下的属性和操作方法
	
	private String tableName = ""; //表名称
	
	private String xzqhColumnName = "";  //行政区划列名称
	
	private String srcXzqhdm = ""; // 修改前行政区划代码
	
	private String destXzqhdm = ""; //修改后行政区划代码
	
	private String ywxtDm = ""; //业务系统代码
	
	private String ywbMc = ""; //业务表名称
	
	private String ywbZjmc = ""; //业务表主键名称
	
	private int bgsjl = 0; //变更数据量
	
	private int logType = 1; //日志构成方式代码 1：记录修改数据主键 2：记录修改数据数量
	
	private boolean showSQL = false; //是否显示日志信息
	
	private int sqlBuildType = 1 ; //日志构建类型代码：1：表示直接使用拼写完成的SQL 2: 表示使用对象动态拼写SQL
	
	private XzqhbgBean xzqhbgBean = null; //行政区划变更数据对象

	/**
	 * @return the destXzqhdm
	 */
	public String getDestXzqhdm() {
		return destXzqhdm;
	}

	/**
	 * @param destXzqhdm the destXzqhdm to set
	 */
	public void setDestXzqhdm(String destXzqhdm) {
		this.destXzqhdm = destXzqhdm;
	}

	/**
	 * @return the isShowSQL
	 */
	public boolean isShowSQL() {
		return showSQL;
	}

	/**
	 * @param isShowSQL the isShowSQL to set
	 */
	public void setShowSQL(boolean showSQL) {
		this.showSQL = showSQL;
	}

	/**
	 * @return the logType
	 */
	public int getLogType() {
		return logType;
	}

	/**
	 * @param logType the logType to set
	 */
	public void setLogType(int logType) {
		this.logType = logType;
	}

	/**
	 * @return the srcXzqhdm
	 */
	public String getSrcXzqhdm() {
		return srcXzqhdm;
	}

	/**
	 * @param srcXzqhdm the srcXzqhdm to set
	 */
	public void setSrcXzqhdm(String srcXzqhdm) {
		this.srcXzqhdm = srcXzqhdm;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the ywbMc
	 */
	public String getYwbMc() {
		return ywbMc;
	}

	/**
	 * @param ywbMc the ywbMc to set
	 */
	public void setYwbMc(String ywbMc) {
		this.ywbMc = ywbMc;
	}

	/**
	 * @return the ywbZjmc
	 */
	public String getYwbZjmc() {
		return ywbZjmc;
	}

	/**
	 * @param ywbZjmc the ywbZjmc to set
	 */
	public void setYwbZjmc(String ywbZjmc) {
		this.ywbZjmc = ywbZjmc;
	}

	/**
	 * @return the ywxtDm
	 */
	public String getYwxtDm() {
		return ywxtDm;
	}

	/**
	 * @param ywxtDm the ywxtDm to set
	 */
	public void setYwxtDm(String ywxtDm) {
		this.ywxtDm = ywxtDm;
	}

	/**
	 * @return the sqlBuildType
	 */
	public int getSqlBuildType() {
		return sqlBuildType;
	}

	/**
	 * @param sqlBuildType the sqlBuildType to set
	 */
	public void setSqlBuildType(int sqlBuildType) {
		this.sqlBuildType = sqlBuildType;
	}

	/**
	 * @return the xzqhbgBean
	 */
	public XzqhbgBean getXzqhbgBean() {
		return xzqhbgBean;
	}

	/**
	 * @param xzqhbgBean the xzqhbgBean to set
	 */
	public void setXzqhbgBean(XzqhbgBean xzqhbgBean) {
		this.xzqhbgBean = xzqhbgBean;
	}

	/**
	 * @return the xzqhColumnName
	 */
	public String getXzqhColumnName() {
		return xzqhColumnName;
	}

	/**
	 * @param xzqhColumnName the xzqhColumnName to set
	 */
	public void setXzqhColumnName(String xzqhColumnName) {
		this.xzqhColumnName = xzqhColumnName;
	}	

	public int getBgsjl() {
		return bgsjl;
	}

	public void setBgsjl(int bgsjl) {
		this.bgsjl = bgsjl;
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：拼装业务数据变更sql</p>
	 * @return
	 * @author zhanglw
	 * @since 2009-7-16
	 */
	public String getBuildYwsjBgSql(){
		String sql = "UPDATE " + tableName + " SET " + xzqhColumnName + "='"
				+ destXzqhdm + "' WHERE " + xzqhColumnName + "='" + srcXzqhdm
				+ "'";
		//根据条件控制是否将SQL输出到日志文件中
		if(isShowSQL()){
			LogManager.getLogger().info("update sql = "+sql);
		}
		return sql;
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：拼装业务数据变更日志sql</p>
	 * @return
	 * @author zhanglw
	 * @since 2009-7-16
	 */
	public String getBuildYwsjBgLogSql(){
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
			sql = "INSERT INTO XZQHBG_YWSJ_LOG(SQBXH,SQBMXXH,SRCXZQHDM,DESTXZQHDM,LOG_SJ,YWXT_DM,YWB_MC,YWB_ZJMC,YWB_ZJXH,YWB_XZQHMC,BGSJL)" //,BGSJL
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
					+ xzqhColumnName + "',"+bgsjl+")"; //,"+bgsjl+"
		}
		//根据条件控制是否将SQL输出到日志文件中
		if(isShowSQL()){
			LogManager.getLogger().info("update log sql = "+sql);
		}
		
		return sql;
	}
	
}
