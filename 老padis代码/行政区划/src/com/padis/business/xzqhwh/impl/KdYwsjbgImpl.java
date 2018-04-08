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
 * Description:���ٵ��������������ʱ���ҵ������Ǩ�Ƶĳ���<br>
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
public class KdYwsjbgImpl extends AbstractXzqhYwsjbg  implements IXzqhYwsjbg {

	public static final String DM_YWXT_KSDC = "K";//���ٵ���
	
	/*
	 *  ������Ҫ�޸ĵı�ļ��ϣ�Ϊ��ά����
	 *  �����еĵ�һά��ʾ��Ҫ�޸ĵ������ű�����ɸ��ֶ�
	 *  �����еĵڶ�ά��ʾÿ���޸ĵ����ݣ��ֶ�˳��Ϊ������,����,���δ���,����������,����������,��־��¼��ʽ,ִ��˳��
	 */
	
	String[][] xzqhbgArray = {
			//{"CYGL_YBSJ","XZQH_DM","","CYGL_YBSJ","YBSJXL","1","2"}, 
			{"KD_CGJC_HZ_NCSQK","XZQH_DM","4","KD_CGJC_HZ_NCSQK","ND,XZQH_DM","2","1"},
			{"KD_CGJC_HZ_NCSQK","SJXZQH_DM","4","KD_CGJC_HZ_NCSQK","ND,XZQH_DM","2","1"}, 
			{"KD_CGJC_HZ_NDJYQK","XZQH_DM","4","KD_CGJC_HZ_NDJYQK","ND,XZQH_DM","2","1"},
			{"KD_CGJC_HZ_NDJYQK","SJXZQH_DM","4","KD_CGJC_HZ_NDJYQK","ND,XZQH_DM","2","1"}, 
			{"KD_CGJC_NDJYQK","XZQH_DM","4","KD_CGJC_NDJYQK","XH","1","2"},
			{"KD_CGJC_RKJHSYTJ","XZQH_DM","4","KD_CGJC_RKJHSYTJ","XH","1","2"}, 
			//{"KD_JCCXX","XZQH_DM","5","KD_JCCXX","XZQH_DM","1","2"},
			{"KD_JCDXX","XZQH_DM","3","KD_JCDXX","XZQH_DM","1","2"}, 
			{"KD_XZQHFZ_FZCY","XZQH_DM","","KD_XZQHFZ_FZCY","XZQH_DM,XZQHFZ_DM","1","2"}
		};
	
/*	String[][] xzqhbgArray = {
	{"LIWEI_TEST","XZQH_DM","4","LIWEI_TEST","ND","1","2"}
		};*/
	
	/**
	 * �ύ��ʽΪ�������ύ
	 */
	public KdYwsjbgImpl(){
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
				//������δ��벻Ϊ���Ҽ��δ�����ڵ��ڴ���Ĵ�����ƴдsql����֮��ƴдsql
				if(!arr[2].equals("")) {
					if(jcDm <= Integer.parseInt(arr[2])){
						if(arr[6].equals("1")){
							this.addYwsjbgFirstSql(buildUpdateSql(arr[0], arr[1], xzqhbgBean
									.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
											arr[0], arr[1], xzqhbgBean, DM_YWXT_KSDC, arr[0], arr[4],Integer.parseInt(arr[5])));
						}
						if(arr[6].equals("2")){
							this.addYwsjlogFirstSql(buildUpdateSql(arr[0], arr[1], xzqhbgBean
									.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
											arr[0], arr[1], xzqhbgBean, DM_YWXT_KSDC, arr[0], arr[4],Integer.parseInt(arr[5])));
						}
					}
				}else {
					if(arr[6].equals("1")){
						this.addYwsjbgFirstSql(buildUpdateSql(arr[0], arr[1], xzqhbgBean
								.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
										arr[0], arr[1], xzqhbgBean, DM_YWXT_KSDC, arr[0], arr[4],Integer.parseInt(arr[5])));
					}
					if(arr[6].equals("2")){
						this.addYwsjlogFirstSql(buildUpdateSql(arr[0], arr[1], xzqhbgBean
								.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
										arr[0], arr[1], xzqhbgBean, DM_YWXT_KSDC, arr[0], arr[4],Integer.parseInt(arr[5])));
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
				//������δ��벻Ϊ���Ҽ��δ�����ڵ��ڴ���Ĵ�����ƴдsql����֮��ƴдsql
				if(!arr[2].equals("")) {
					if(jcDm <= Integer.parseInt(arr[2])){
						YwsjBgSqlBean ywsjBgSqlBean = new YwsjBgSqlBean();
						ywsjBgSqlBean.setTableName(arr[0]);
						ywsjBgSqlBean.setXzqhColumnName(arr[1]);
						ywsjBgSqlBean.setSrcXzqhdm(xzqhbgBean.getSrcXzqhdm());
						ywsjBgSqlBean.setDestXzqhdm(xzqhbgBean.getDestXzqhdm());
						ywsjBgSqlBean.setYwxtDm(DM_YWXT_KSDC);
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
				}else {
					YwsjBgSqlBean ywsjBgSqlBean = new YwsjBgSqlBean();
					ywsjBgSqlBean.setTableName(arr[0]);
					ywsjBgSqlBean.setXzqhColumnName(arr[1]);
					ywsjBgSqlBean.setSrcXzqhdm(xzqhbgBean.getSrcXzqhdm());
					ywsjBgSqlBean.setDestXzqhdm(xzqhbgBean.getDestXzqhdm());
					ywsjBgSqlBean.setYwxtDm(DM_YWXT_KSDC);
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
		xzqhbgBean.setDestXzqhdm("130102002000000");
		xzqhbgBean.setDestXzqhmc("destXzqhmc");
		xzqhbgBean.setBglxdm("bglxdm");
		xzqhbgBean.setJcdm("3");
		List list = new ArrayList();
		list.add(xzqhbgBean);
		
		KdYwsjbgImpl kd = new KdYwsjbgImpl();
		kd.buildSqlList(list);
		System.out.println(kd.ywsjbgFirstSqlList.size());
		for (int i = 0; i < kd.ywsjbgFirstSqlList.size(); i++) {
			YwsjBgSqlBean bean = (YwsjBgSqlBean)kd.ywsjbgFirstSqlList.get(i);
			if (!bean.getYwsjBgSql().equals("")) {
				System.out.println(bean.getYwsjBgSql() + ";");
			}
			if (!bean.getYwsjBgLogSql().equals("")) {
				System.out.println(bean.getYwsjBgLogSql() + ";");
			}
		}
	}

}
