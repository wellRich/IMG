package com.padis.business.xzqhwh.impl;

import java.util.ArrayList;
import java.util.List;

import com.padis.business.xzqhwh.common.IXzqhYwsjbg;
import com.padis.business.xzqhwh.common.XzqhbgBean;

import ctais.util.StringEx;


/**
 * <p>
 * Title: 接口com.padis.business.xzqhywsjbg.AbstractXzqhYwsjbg 业务数据变更公共实现类
 * </P>
 * <p>
 * Description:事业统计行政区划变更时完成业务数据迁移的程序<br>
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
 * 修改人： 
 * 修改时间： 
 * 修改内容： 
 * 修改版本号：
 */
public class StYwsjbgImpl extends AbstractXzqhYwsjbg  implements IXzqhYwsjbg {

	public static final String DM_YWXT_SYTJ = "Z";//事业统计
	
	/*
	 *  定义需要修改的表的集合，为二维数组
	 *  数组中的第一维表示需要修改的若干张表的若干个字段
	 *  数组中的第二维表示每次修改的内容，字段顺序为：表名,列名,级次代码,表中文名称,表主键名称,日志记录方式,执行顺序
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
	 * 提交方式为：批量提交
	 */
	public StYwsjbgImpl(){
		this.setBatchMode(true);
	}
	/**
	 * <p>方法名称：buildSqlList</p>
	 * <p>方法描述：</p>
	 * @param xzqhbgxx
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-7-14
	 */
	protected void buildSqlList(List xzqhbgxx) throws Exception{
		buildSqlList2(xzqhbgxx);
	}
	
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：直接拼写sql，放入执行队列</p>
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
				//如果级次代码不为空或级次代码大于等于传入的代码则拼写sql，反之则不拼写sql
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
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
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
				//如果级次代码不为空或级次代码大于等于传入的代码则拼写sql，反之则不拼写sql
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
		// String sqbxh = xzqhbgBean.getSqbxh();// 申请表序号（申请表主键）
		// String sqbmxXh = xzqhbgBean.getSqbmxXh();// 申请表明细数据序号（明细主键）
		// String srcXzqhdm = xzqhbgBean.getSrcXzqhdm();// 原区划代码
		// String srcXzqhmc = xzqhbgBean.getSrcXzqhmc();// 原区划名称
		// String destXzqhdm = xzqhbgBean.getDestXzqhdm();// 目标区划代码
		// String destXzqhmc = xzqhbgBean.getDestXzqhmc();// 目标区划名称
		// String bglxdm = xzqhbgBean.getBglxdm();// 变更类型代码
		// String jcdm = xzqhbgBean.getJcdm(); // 级次代码
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
