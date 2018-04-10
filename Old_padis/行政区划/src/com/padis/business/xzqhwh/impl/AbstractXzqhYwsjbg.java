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
 * Title: �ӿ�com.padis.business.xzqhwh.common.IXzqhYwsjbg ҵ�����ݱ������ʵ����
 * </P>
 * <p>
 * Description:ʵ��ҵ�����ݱ������ҵ�����<br>
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
 * @since 2009-7-10 �޸��ˣ� �޸�ʱ�䣺 �޸����ݣ� �޸İ汾�ţ�
 */
public abstract class AbstractXzqhYwsjbg implements IXzqhYwsjbg {

	/*
	 * ����ҵ�����ݱ��ִ��SQL����,��ִ��ҵ�����ݱ��,�ټ�¼ҵ����־
	 */
	protected List ywsjbgFirstSqlList = new ArrayList();

	/*
	 * ����ҵ�����ݱ��ִ��SQL����,�ȼ�¼ҵ����־,��ִ��ҵ�����ݱ��
	 */
	protected List ywsjlogFirstSqlList = new ArrayList();

	private int batchSize = 100; // �ݶ�ÿ100���ύһ��

	private boolean batchMode = false; // ���Ϊtrue�������ύ��false��Ϊȫ���ύ��Ĭ��Ϊfalse

	private static Map map = new HashMap();

	static {
		map.put("J", "��������");
		map.put("T", "�ر����");
		map.put("S", "�����츻");
		map.put("L", "���举Ů�����˿�");
		map.put("K", "���ٵ���");
		map.put("Z", "��ҵͳ��");
	}

	/*
	 * ���������ύ��¼��
	 */
	public void setBatchSize(int size) {
		this.batchSize = size;
	}

	/*
	 * ȡ�������ύ��¼��
	 */
	public int getBatchSize() {
		return this.batchSize;
	}

	/**
	 * <p>
	 * �������ƣ�ywsjBgExecute
	 * </p>
	 * <p>
	 * ����������ִ��sql���
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
		// ��ִ��ҵ�����ݱ�����ټ�¼�����־
		if (ywsjbgFirstSqlList != null && ywsjbgFirstSqlList.size() > 0) {
			try {
				LogManager.getLogger().info("-------------------------ҵ�����ݱ������ִ��sql��ʼ��ӡ----------------------");
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
								.getClass()));// �������ӳ�
						dw.setTransObject(ut);
						dw.update(false);
						updateRowNum = dw.getUpdatedRowNum();
					}
					ywsjBgSql = "";
					// ����޸�ҵ�����ݼ�¼����Ϊ����ִ��logSql
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
									.getConnectionName(this.getClass()));// �������ӳ�
							dw.setTransObject(ut);
							dw.update(false);
						}
					}
					ywsjBgLogSql = "";
				}
				LogManager.getLogger().info("-------------------------ҵ�����ݱ������ִ��sql������ӡ----------------------");
			} catch (Exception e) {
				LogManager.getLogger().log(e);
				result = false;
				throw new Exception("ҵ��ϵͳ�����������ʧ��:" + ywsjBgSql + ywsjBgLogSql
						+ ";" + e.getMessage());
			}
			result = true;
		}
		// �ȼ�¼�����־����ִ��ҵ�����ݱ��
		if (ywsjlogFirstSqlList != null && ywsjlogFirstSqlList.size() > 0) {
			try {
				LogManager.getLogger().info("-------------------------ҵ�����ݱ������ִ��sql��ʼ��ӡ----------------------");
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
					// �����־��¼����Ϊ����ִ��ywsjSql
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
				LogManager.getLogger().info("-------------------------ҵ�����ݱ������ִ��sql������ӡ----------------------");
			} catch (Exception e) {
				LogManager.getLogger().log(e);
				result = false;
				throw new Exception("ҵ��ϵͳ�����������ʧ��:" + ywsjBgSql + ywsjBgLogSql
						+ ";" + e.getMessage());
			}
			result = true;
		}
		return result;
	}

	/**
	 * <p>
	 * �������ƣ�
	 * </p>
	 * <p>
	 * ����������
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
		// ��ִ��ҵ�����ݱ�����ټ�¼�����־
		if (ywsjbgFirstSqlList != null && ywsjbgFirstSqlList.size() > 0) {
			System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
			Statement stmt = conn.createStatement();
			try {
				System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
				LogManager.getLogger().info("-------------------------ҵ�����ݱ������ִ��sql��ʼ��ӡ----------------------");
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
						throw new Exception("ywsjbgFirstSqlList���ڿյ�ҵ�����ݱ��Sql!");
					}
					if (batchCount % batchSize == 0) {
						int s = batchCount / batchSize;
						count = stmt.executeBatch();
						LogManager.getLogger().info("-------------------------ҵ�����ݱ��һ�����ύ----------------------");
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
						LogManager.getLogger().info("-------------------------ҵ�����ݱ��һ�����ύ----------------------");
					}
				}

				if (batchCount % batchSize != 0) {
					int s = batchCount / batchSize;
					count = stmt.executeBatch();
					LogManager.getLogger().info("-------------------------ҵ�����ݱ��һ�����ύ----------------------");
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
					LogManager.getLogger().info("-------------------------ҵ�����ݱ������ִ��sql������ӡ----------------------");
				}
			} catch (Exception e) {
				LogManager.getLogger().log(e);
				result = false;
				throw new Exception("ҵ��ϵͳ�����������ʧ��1:" + e.getMessage());
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
			result = true;
		}
		batchCount = 0;
		// �ȼ�¼�����־����ִ��ҵ�����ݱ��
		if (ywsjlogFirstSqlList != null && ywsjlogFirstSqlList.size() > 0) {
			Statement stmt = conn.createStatement();
			try {
				LogManager.getLogger().info("-------------------------ҵ�����ݱ������ִ��sql��ʼ��ӡ----------------------");
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
						throw new Exception("ywsjlogFirstSqlList���ڿյļ�¼�����־Sql!");
					}
					LogManager.getLogger().info("ssqqll2222============================"+ywsjBgLogSql);
					System.out.println("ssqqll============================"+ywsjBgLogSql);
					if (batchCount % batchSize == 0) {
						int s = batchCount / batchSize;
						count = stmt.executeBatch();
						LogManager.getLogger().info("-------------------------ҵ�����ݱ��һ�����ύ----------------------");
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
						LogManager.getLogger().info("-------------------------ҵ�����ݱ��һ�����ύ----------------------");
					}
				}
				if (batchCount % batchSize != 0) {
					int s = batchCount / batchSize;
					count = stmt.executeBatch();
					LogManager.getLogger().info("-------------------------ҵ�����ݱ��һ�����ύ----------------------");
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
					LogManager.getLogger().info("-------------------------ҵ�����ݱ������ִ��sql������ӡ----------------------");
				}
			} catch (Exception e) {
				LogManager.getLogger().log(e);
				result = false;
				throw new Exception("ҵ��ϵͳ�����������ʧ��2:" + e.getMessage());
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
	 * �ӿڷ���isAllowBatchUpdateʵ��
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
						.getClass()));// �������ӳ�
				long rows = dw.retrieve();
				if (rows > 0) {
					XMLDataObject xdo = dw.toXDO();
					StringBuffer sb = new StringBuffer();
					sb.append("��ҵ��ϵͳ");
					for (int j = 0; j < rows; j++) {
						String ywxtMc = StringEx.sNull(map.get(xdo.getItemAny(
								j, "YWLX_DM")));
						sb.append("��");
						sb.append(ywxtMc);
						sb.append("��");
						if (j < rows - 1) {
							sb.append(",");
						}
					}
					sb.append("�д��ڶ��´��롾");
					sb.append(destXzqhDm);
					sb.append("�������ã���˲������������������롾");
					sb.append(srcXzqhDm);
					sb.append("���޸�Ϊ�������������롾");
					sb.append(destXzqhDm);
					sb.append("��");
					throw new Exception(sb.toString());
				}
			}
			temp = true;
		}
		return temp;
	}

	/*
	 * �ӿڷ���updateYwsjʵ��
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
			// ƴװ���е�sql
			this.buildSqlList(xzqhbgxx);   //4444444444444
			// ִ��sql��䣬���batchModeΪtrue�������ύ��false��Ϊȫ���ύ
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
	 * �������ƣ�buildSqlList
	 * </p>
	 * <p>
	 * ����������ƴװsql
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
	 * �������ƣ�addYwsjbgSql
	 * </p>
	 * <p>
	 * ����������������漰��sql�����뵽List��,�ȱ��ҵ������
	 * </p>
	 * 
	 * @param ywsjBgSql
	 *            ���ҵ�����ݵ�sql���
	 * @param ywsjBgLogSql
	 *            ��¼���ҵ�����ݵ���־sql���
	 * @author admin
	 * @since 2009-7-13
	 */
	protected void addYwsjbgFirstSql(String ywsjBgSql, String ywsjBgLogSql) {
		ywsjbgFirstSqlList.add(new YwsjBgSqlBean(ywsjBgSql, ywsjBgLogSql));
	}
	
	/**
	 * <p>
	 * �������ƣ�addYwsjbgSql
	 * </p>
	 * <p>
	 * ����������������漰��sql�����뵽List��,�ȱ��ҵ������
	 * </p>
	 * @param ywsjBgSqlBean
	 *            ���ҵ�����ݶ���
	 * @author admin
	 * @since 2009-7-13
	 */
	protected void addYwsjbgFirstSql(YwsjBgSqlBean ywsjBgSqlBean) {
		ywsjbgFirstSqlList.add(ywsjBgSqlBean);
	}
	 
	/**
	 * <p>
	 * �������ƣ�addYwsjbgSql
	 * </p>
	 * <p>
	 * ����������������漰��sql�����뵽List��,�ȼ�¼��־
	 * </p>
	 * 
	 * @param ywsjBgSql
	 *            ���ҵ�����ݵ�sql���
	 * @param ywsjBgLogSql
	 *            ��¼���ҵ�����ݵ���־sql���
	 * @author admin
	 * @since 2009-7-13
	 */
	protected void addYwsjlogFirstSql(String ywsjBgSql, String ywsjBgLogSql) {
		ywsjlogFirstSqlList.add(new YwsjBgSqlBean(ywsjBgSql, ywsjBgLogSql));
	}
	
	/**
	 * <p>
	 * �������ƣ�addYwsjbgSql
	 * </p>
	 * <p>
	 * ����������������漰��sql�����뵽List��,�ȼ�¼��־
	 * </p>
	 * 
	 * @param ywsjBgSqlBean
	 *            ���ҵ�����ݶ���
	 * @author admin
	 * @since 2009-7-13
	 */
	protected void addYwsjlogFirstSql(YwsjBgSqlBean ywsjBgSqlBean) {
		ywsjlogFirstSqlList.add(ywsjBgSqlBean);
	}

	/**
	 * <p>
	 * �������ƣ�buildUpdateSql
	 * </p>
	 * <p>
	 * �������������ݱ�����������Դ����������Ŀ�����������������ҵ�����ݵ�sql���
	 * </p>
	 * 
	 * @param tableName
	 *            ���ݱ���
	 * @param xzqhColumnName
	 *            ������������
	 * @param srcXzqhdm
	 *            Դ��������
	 * @param destXzqhdm
	 *            Ŀ����������
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
	 * �������ƣ�buildLogSql
	 * </p>
	 * <p>
	 * �������������������������ʱҵ����־
	 * </p>
	 * 
	 * @param tableName
	 *            ���ݱ���
	 * @param xzqhColumnName
	 *            ������������
	 * @param xzqhbgBean
	 * @param ywxtDm
	 *            ҵ��ϵͳ����:L,J,S,D,K��
	 * @param ywbMc
	 * @param ywbZjmc
	 * @param logType
	 *            1:������־ʱ����insert into select��ʽ2:������־ʱ����insert into values��ʽ
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

	// /////////////////////////////////ҵ��ϵͳ���ݱ����־��ṹ////////////////////////////
	// CREATE TABLE XZQHBG_YWSJ_LOG
	// (
	// SQBXH VARCHAR2(20 BYTE) NOT NULL, --��������
	// SQBMXXH VARCHAR2(20 BYTE) NOT NULL, --�������ϸ�������
	// SRCXZQHDM CHAR(15) NOT NULL, --ԭ������������
	// DESTXZQHDM CHAR(15) NOT NULL, --Ŀ����������
	// LOG_SJ DATE NOT NULL, --��־ʱ��
	// YWXT_DM CHAR(1) NOT NULL, --ҵ��ϵͳ����:L,J,S,D,K��
	// YWB_MC VARCHAR2(40 BYTE) NOT NULL, --ҵ�������
	// YWB_ZJMC VARCHAR2(40 BYTE), --ҵ�����������
	// YWB_ZJXH VARCHAR2(20 BYTE), --ҵ����������
	// YWB_XZQHMC VARCHAR2(40 BYTE) NOT NULL --ҵ�����������������
	// )
	//
	// COMMENT ON TABLE XZQHBG_YWSJ_LOG IS '�����������ҵ��������־��';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.SQBXH IS '��������';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.SQBMXXH IS '�������ϸ�������';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.SRCXZQHDM IS 'ԭ������������';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.DESTXZQHDM IS 'Ŀ����������';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.LOG_SJ IS '��־ʱ��';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.YWXT_DM IS 'ҵ��ϵͳ����:L,J,S,D,K��';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.YWB_MC IS 'ҵ�������';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.YWB_ZJMC IS 'ҵ�����������';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.YWB_ZJXH IS 'ҵ����������';
	// COMMENT ON COLUMN XZQHBG_YWSJ_LOG.YWB_XZQHMC IS 'ҵ�����������������';

	public boolean isBatchMode() {
		return batchMode;
	}

	public void setBatchMode(boolean batchMode) {
		this.batchMode = batchMode;
	}
	
}
