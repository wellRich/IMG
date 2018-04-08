package com.padis.business.xzqhwh.impl;

import com.padis.business.xzqhwh.common.XzqhbgBean;

import ctais.services.log.LogManager;


/**
 * <p>
 * Title:YwsjBgSqlBean
 * </P>
 * <p>
 * Description:��װҵ�����ݱ��ʱ��sql���
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
	 * ҵ�����������������sql���
	 */
	private String ywsjBgSql = "";

	/**
	 * ҵ�������������������־sql���
	 */
	private String ywsjBgLogSql = "";

	/**
	 * ���췽��
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
	
	//Ϊ��ʵ���ܹ���ʹ��SQLʱ�Ŷ�̬ƴдSQL�ķ����������������µ����ԺͲ�������
	
	private String tableName = ""; //������
	
	private String xzqhColumnName = "";  //��������������
	
	private String srcXzqhdm = ""; // �޸�ǰ������������
	
	private String destXzqhdm = ""; //�޸ĺ�������������
	
	private String ywxtDm = ""; //ҵ��ϵͳ����
	
	private String ywbMc = ""; //ҵ�������
	
	private String ywbZjmc = ""; //ҵ�����������
	
	private int bgsjl = 0; //���������
	
	private int logType = 1; //��־���ɷ�ʽ���� 1����¼�޸��������� 2����¼�޸���������
	
	private boolean showSQL = false; //�Ƿ���ʾ��־��Ϣ
	
	private int sqlBuildType = 1 ; //��־�������ʹ��룺1����ʾֱ��ʹ��ƴд��ɵ�SQL 2: ��ʾʹ�ö���̬ƴдSQL
	
	private XzqhbgBean xzqhbgBean = null; //��������������ݶ���

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
	 * <p>�������ƣ�</p>
	 * <p>����������ƴװҵ�����ݱ��sql</p>
	 * @return
	 * @author zhanglw
	 * @since 2009-7-16
	 */
	public String getBuildYwsjBgSql(){
		String sql = "UPDATE " + tableName + " SET " + xzqhColumnName + "='"
				+ destXzqhdm + "' WHERE " + xzqhColumnName + "='" + srcXzqhdm
				+ "'";
		//�������������Ƿ�SQL�������־�ļ���
		if(isShowSQL()){
			LogManager.getLogger().info("update sql = "+sql);
		}
		return sql;
	}
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>����������ƴװҵ�����ݱ����־sql</p>
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
		//�������������Ƿ�SQL�������־�ļ���
		if(isShowSQL()){
			LogManager.getLogger().info("update log sql = "+sql);
		}
		
		return sql;
	}
	
}
