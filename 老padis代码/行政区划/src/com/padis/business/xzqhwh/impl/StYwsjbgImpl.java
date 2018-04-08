package com.padis.business.xzqhwh.impl;

import java.util.ArrayList;
import java.util.List;

import com.padis.business.xzqhwh.common.IXzqhYwsjbg;
import com.padis.business.xzqhwh.common.XzqhbgBean;

import ctais.util.StringEx;


/**
 * <p>
 * Title: �ӿ�com.padis.business.xzqhywsjbg.AbstractXzqhYwsjbg ҵ�����ݱ������ʵ����
 * </P>
 * <p>
 * Description:��ҵͳ�������������ʱ���ҵ������Ǩ�Ƶĳ���<br>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: DigitalChina Co.Ltd
 * </p>
 * 
 * @auther maliang
 * @version 1.0
 * @since 2009-7-13
 * �޸��ˣ� 
 * �޸�ʱ�䣺 
 * �޸����ݣ� 
 * �޸İ汾�ţ�
 */
public class StYwsjbgImpl extends AbstractXzqhYwsjbg  implements IXzqhYwsjbg {

	public static final String DM_YWXT_SYTJ = "Z";//��ҵͳ��
	
	/*
	 *  ������Ҫ�޸ĵı�ļ��ϣ�Ϊ��ά����
	 *  �����еĵ�һά��ʾ��Ҫ�޸ĵ������ű�����ɸ��ֶ�
	 *  �����еĵڶ�ά��ʾÿ���޸ĵ����ݣ��ֶ�˳��Ϊ������,����,���δ���,����������,����������,��־��¼��ʽ,ִ��˳��
	 */
	
	String[][] xzqhbgArray = {
			{"FWZ_DJXX","XZQH_DM","4","FWZ_DJXX","FWZ_DM","1","2"}, 
			{"RS_BBCS","XZQH_DM","3","RS_BBCS","CSXH","1","2"},
			{"RS_JGXX","XZQH_DM","4","RS_JGXX","JG_DM","1","2"}, 
			{"ZJ_CZTRQKB","XZQH_DM","3","ZJ_CZTRQKB","XH","1","2"},
			{"ZJ_JBQKB","XZQH_DM","3","ZJ_JBQKB","XH","1","2"}, 
			{"ZJ_JFSZZB","XZQH_DM","3","ZJ_JFSZZB","XH","1","2"},
			{"ZJ_SWZCB","XZQH_DM","3","ZJ_SWZCB","XH","1","2"}, 
			{"ZJ_SYFTRQKB","XZQH_DM","3","ZJ_SYFTRQKB","XH","1","2"},
			{"ZJ_ZYZXJFTRQKB","XZQH_DM","3","ZJ_ZYZXJFTRQKB","XH","1","2"}
		};
	/*String[][] xzqhbgArray = {
			{"LIWEI_TEST","XZQH_DM","4","LIWEI_TEST","ND","1","2"}
		};*/
	
	/**
	 * �ύ��ʽΪ�������ύ
	 */
	public StYwsjbgImpl(){
		this.setBatchMode(true);
	}
	/**
	 * <p>�������ƣ�buildSqlList</p>
	 * <p>����������</p>
	 * @param xzqhbgxx
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-7-14
	 */
	protected void buildSqlList(List xzqhbgxx) throws Exception{
		buildSqlList2(xzqhbgxx);
	}
	
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>����������ֱ��ƴдsql������ִ�ж���</p>
	 * @param xzqhbgxx
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-7-16
	 */
	private void buildSqlList1(List xzqhbgxx) throws Exception{
		for (int i = 0; i < xzqhbgxx.size(); i++) {
			XzqhbgBean xzqhbgBean = (XzqhbgBean) xzqhbgxx.get(i);
			int jcDm = Integer.parseInt(StringEx.sNull(xzqhbgBean.getJcdm()));
			for(int j=0;j<xzqhbgArray.length;j++){
				String arr[] = xzqhbgArray[j];
				//������δ��벻Ϊ�ջ򼶴δ�����ڵ��ڴ���Ĵ�����ƴдsql����֮��ƴдsql
				if(!arr[2].equals("")) {
					if(jcDm <= Integer.parseInt(arr[2])){
						if(arr[6].equals("1")){
							this.addYwsjbgFirstSql(buildUpdateSql(arr[0], arr[1], xzqhbgBean
								.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
										arr[0], arr[1], xzqhbgBean, DM_YWXT_SYTJ, arr[0], arr[4],Integer.parseInt(arr[5])));
						}
						if(arr[6].equals("2")){
							this.addYwsjlogFirstSql(buildUpdateSql(arr[0], arr[1], xzqhbgBean
								.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
										arr[0], arr[1], xzqhbgBean, DM_YWXT_SYTJ, arr[0], arr[4],Integer.parseInt(arr[5])));
						}
					}
				}else{
					if(arr[6].equals("1")){
						this.addYwsjbgFirstSql(buildUpdateSql(arr[0], arr[1], xzqhbgBean
								.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
										arr[0], arr[1], xzqhbgBean, DM_YWXT_SYTJ, arr[0], arr[4],Integer.parseInt(arr[5])));
					}
					if(arr[6].equals("2")){
						this.addYwsjlogFirstSql(buildUpdateSql(arr[0], arr[1], xzqhbgBean
								.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
										arr[0], arr[1], xzqhbgBean, DM_YWXT_SYTJ, arr[0], arr[4],Integer.parseInt(arr[5])));
					}
					
				}
			}
		}
	}
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>����������</p>
	 * @param xzqhbgxx
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-7-16
	 */
	private void buildSqlList2(List xzqhbgxx) throws Exception{
		for (int i = 0; i < xzqhbgxx.size(); i++) {
			XzqhbgBean xzqhbgBean = (XzqhbgBean) xzqhbgxx.get(i);
			int jcDm = Integer.parseInt(StringEx.sNull(xzqhbgBean.getJcdm()));
			for(int j=0;j<xzqhbgArray.length;j++){
				String arr[] = xzqhbgArray[j];
				//������δ��벻Ϊ�ջ򼶴δ�����ڵ��ڴ���Ĵ�����ƴдsql����֮��ƴдsql
				if(!arr[2].equals("")) {
					if(jcDm <= Integer.parseInt(arr[2])){
						YwsjBgSqlBean ywsjBgSqlBean = new YwsjBgSqlBean();
						ywsjBgSqlBean.setTableName(arr[0]);
						ywsjBgSqlBean.setXzqhColumnName(arr[1]);
						ywsjBgSqlBean.setSrcXzqhdm(xzqhbgBean.getSrcXzqhdm());
						ywsjBgSqlBean.setDestXzqhdm(xzqhbgBean.getDestXzqhdm());
						ywsjBgSqlBean.setYwxtDm(DM_YWXT_SYTJ);
						ywsjBgSqlBean.setYwbMc(arr[0]);
						ywsjBgSqlBean.setYwbZjmc(arr[4]);
						ywsjBgSqlBean.setLogType(Integer.parseInt(arr[5]));
						ywsjBgSqlBean.setSqlBuildType(2);
						ywsjBgSqlBean.setXzqhbgBean(xzqhbgBean);
						if(arr[6].equals("1")){
							this.addYwsjbgFirstSql(ywsjBgSqlBean);
						}
						if(arr[6].equals("2")){
							this.addYwsjlogFirstSql(ywsjBgSqlBean);
						}
					}
				}else{
					YwsjBgSqlBean ywsjBgSqlBean = new YwsjBgSqlBean();
					ywsjBgSqlBean.setTableName(arr[0]);
					ywsjBgSqlBean.setXzqhColumnName(arr[1]);
					ywsjBgSqlBean.setSrcXzqhdm(xzqhbgBean.getSrcXzqhdm());
					ywsjBgSqlBean.setDestXzqhdm(xzqhbgBean.getDestXzqhdm());
					ywsjBgSqlBean.setYwxtDm(DM_YWXT_SYTJ);
					ywsjBgSqlBean.setYwbMc(arr[0]);
					ywsjBgSqlBean.setYwbZjmc(arr[4]);
					ywsjBgSqlBean.setLogType(Integer.parseInt(arr[5]));
					ywsjBgSqlBean.setSqlBuildType(2);
					ywsjBgSqlBean.setXzqhbgBean(xzqhbgBean);
					if(arr[6].equals("1")){
						this.addYwsjbgFirstSql(ywsjBgSqlBean);
					}
					if(arr[6].equals("2")){
						this.addYwsjlogFirstSql(ywsjBgSqlBean);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		// String sqbxh = xzqhbgBean.getSqbxh();// �������ţ������������
		// String sqbmxXh = xzqhbgBean.getSqbmxXh();// �������ϸ������ţ���ϸ������
		// String srcXzqhdm = xzqhbgBean.getSrcXzqhdm();// ԭ��������
		// String srcXzqhmc = xzqhbgBean.getSrcXzqhmc();// ԭ��������
		// String destXzqhdm = xzqhbgBean.getDestXzqhdm();// Ŀ����������
		// String destXzqhmc = xzqhbgBean.getDestXzqhmc();// Ŀ����������
		// String bglxdm = xzqhbgBean.getBglxdm();// ������ʹ���
		// String jcdm = xzqhbgBean.getJcdm(); // ���δ���
		XzqhbgBean xzqhbgBean = new XzqhbgBean();
		xzqhbgBean.setSqbxh("sqbxh0000001");
		xzqhbgBean.setSqbmxXh("sqbmxXh000000002");
		xzqhbgBean.setSrcXzqhdm("130102010201000");
		xzqhbgBean.setSrcXzqhmc("srcXzqhmc");
		xzqhbgBean.setDestXzqhdm("130102001000000");
		xzqhbgBean.setDestXzqhmc("destXzqhmc");
		xzqhbgBean.setBglxdm("bglxdm");
		xzqhbgBean.setJcdm("3");
		List list = new ArrayList();
		list.add(xzqhbgBean);
		xzqhbgBean = new XzqhbgBean();
		xzqhbgBean.setSqbxh("sqbxh0000003");
		xzqhbgBean.setSqbmxXh("sqbmxXh000000004");
		xzqhbgBean.setSrcXzqhdm("130102010202000");
		xzqhbgBean.setSrcXzqhmc("srcXzqhmc");
		xzqhbgBean.setDestXzqhdm("130102002000000");
		xzqhbgBean.setDestXzqhmc("destXzqhmc");
		xzqhbgBean.setBglxdm("bglxdm");
		xzqhbgBean.setJcdm("3");
		list.add(xzqhbgBean);
		xzqhbgBean = new XzqhbgBean();
		xzqhbgBean.setSqbxh("sqbxh0000005");
		xzqhbgBean.setSqbmxXh("sqbmxXh000000006");
		xzqhbgBean.setSrcXzqhdm("130102010203000");
		xzqhbgBean.setSrcXzqhmc("srcXzqhmc");
		xzqhbgBean.setDestXzqhdm("130102003000000");
		xzqhbgBean.setDestXzqhmc("destXzqhmc");
		xzqhbgBean.setBglxdm("bglxdm");
		xzqhbgBean.setJcdm("3");
		list.add(xzqhbgBean);
		StYwsjbgImpl st = new StYwsjbgImpl();
		st.buildSqlList(list);
		System.out.println(st.ywsjbgFirstSqlList.size());
		for (int i = 0; i < st.ywsjbgFirstSqlList.size(); i++) {
			YwsjBgSqlBean bean = (YwsjBgSqlBean)st.ywsjbgFirstSqlList.get(i);
			if (!bean.getYwsjBgSql().equals("")) {
				System.out.println(bean.getYwsjBgSql() + ";");
			}
			if (!bean.getYwsjBgLogSql().equals("")) {
				System.out.println(bean.getYwsjBgLogSql() + ";");
			}
		}
	}
}
