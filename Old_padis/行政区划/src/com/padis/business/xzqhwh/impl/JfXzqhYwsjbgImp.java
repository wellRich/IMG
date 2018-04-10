package com.padis.business.xzqhwh.impl;

import java.util.List;

import com.padis.business.xzqhwh.common.XzqhbgBean;

/**
 * <p>
 * Title: 接口com.padis.business.xzqhwh.common.IXzqhYwsjbg奖扶系统实现类
 * </P>
 * <p>
 * Description:实现奖励扶助、特别扶助、少生快富三个子系统业务数据的变更<br>
 * 奖扶系统公用涉及数据表(3张表)：JF_JFNLPZ(XZQH_DM)<br>
 * 奖励扶助涉及数据表(4张表)：LNJF_JB(XZQH_DM),LNJF_JFDX(XZQH_DM),LNJF_JFDX_BG(XZQH_DM),LNJF_JFDX_TJ(XZQH_DM)<br>
 * 特别扶助涉及数据表(4张表)：TBFZ_JB(XZQH_DM),TBFZ_JFDX(XZQH_DM),TBFZ_JFDX_BG(XZQH_DM),TBFZ_JFDX_TJ(XZQH_DM)<br>
 * 少生快富涉及数据表(3张表)：SSKF_JB(XZQH_DM),SSKF_JFDX(QZ_XZQH_DM,ZF_XZQH_DM),SSKF_JFDX_TJ(ZF_XZQH_DM,QZ_XZQH_DM)<br>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: DigitalChina Co.Ltd
 * </p>
 * 
 * @auther admin
 * @version 1.0
 * @since 2009-7-10 修改人： 修改时间： 修改内容： 修改版本号：
 */
public class JfXzqhYwsjbgImp extends AbstractXzqhYwsjbg {

	protected void buildSqlList(List xzqhbgxx) throws Exception {
		setBatchMode(true);
		if (xzqhbgxx != null || xzqhbgxx.size() > 0) {
			for (int i = 0; i < xzqhbgxx.size(); i++) {
				XzqhbgBean xzqhbgBean = (XzqhbgBean) xzqhbgxx.get(i);
				// String sqbxh = xzqhbgBean.getSqbxh();// 申请表序号（申请表主键）
				// String sqbmxXh = xzqhbgBean.getSqbmxXh();// 申请表明细数据序号（明细主键）
				// String srcXzqhdm = xzqhbgBean.getSrcXzqhdm();// 原区划代码
				// String srcXzqhmc = xzqhbgBean.getSrcXzqhmc();// 原区划名称
				// String destXzqhdm = xzqhbgBean.getDestXzqhdm();// 目标区划代码
				// String destXzqhmc = xzqhbgBean.getDestXzqhmc();// 目标区划名称
				// String bglxdm = xzqhbgBean.getBglxdm();// 变更类型代码
				// String jcdm = xzqhbgBean.getJcdm(); // 级次代码
				jfYwsjBg(xzqhbgBean);
				lnjfYwsjBg(xzqhbgBean);
				tbfzYwsjBg(xzqhbgBean);
				sskfYwsjBg(xzqhbgBean);
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////业务系统数据变更Begin////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/*
	 * 实现公共模块的行政区划的修改
	 */
	private void jfYwsjBg(XzqhbgBean xzqhbgBean) {
		// JF_JFNLPZ
		addYwsjbgFirstSql(buildUpdateSql("JF_JFNLPZ", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");

	}

	/*
	 * 实现老年奖扶模块的行政区划的修改
	 */
	private void lnjfYwsjBg(XzqhbgBean xzqhbgBean) {
		// LNJF_JB,需要记录日志
		addYwsjlogFirstSql(buildUpdateSql("LNJF_JB", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LNJF_JB", "XZQH_DM", xzqhbgBean, "J", "LNJF_JB", "JB_XH", 1));
		// LNJF_JFDX,需要记录日志
		addYwsjlogFirstSql(buildUpdateSql("LNJF_JFDX", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LNJF_JFDX", "XZQH_DM", xzqhbgBean, "J", "LNJF_JFDX",
				"JFDX_XH", 1));
		// LNJF_JFDX_BG,不记录日志
		addYwsjbgFirstSql(buildUpdateSql("LNJF_JFDX_BG", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LNJF_JFDX_TJ,不记录日志
		addYwsjbgFirstSql(buildUpdateSql("LNJF_JFDX_TJ", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
	}

	/*
	 * 实现特别扶助模块的行政区划的修改
	 */
	private void tbfzYwsjBg(XzqhbgBean xzqhbgBean) {
		// TBFZ_JB,需要记录日志
		addYwsjlogFirstSql(buildUpdateSql("TBFZ_JB", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"TBFZ_JB", "XZQH_DM", xzqhbgBean, "J", "TBFZ_JB", "JB_XH", 1));
		// TBFZ_JFDX,需要记录日志
		addYwsjlogFirstSql(buildUpdateSql("TBFZ_JFDX", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"TBFZ_JFDX", "XZQH_DM", xzqhbgBean, "J", "TBFZ_JFDX",
				"JFDX_XH", 1));
		// TBFZ_JFDX_BG,不记录日志
		addYwsjbgFirstSql(buildUpdateSql("TBFZ_JFDX_BG", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// TBFZ_JFDX_TJ,不记录日志
		addYwsjbgFirstSql(buildUpdateSql("TBFZ_JFDX_TJ", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
	}

	/*
	 * 实现少生快富模块的行政区划的修改
	 * SSKF_JFDX(QZ_XZQH_DM,ZF_XZQH_DM),SSKF_JFDX_TJ(ZF_XZQH_DM,QZ_XZQH_DM)<br>
	 */
	private void sskfYwsjBg(XzqhbgBean xzqhbgBean) {
		// SSKF_JB,需要记录日志
		addYwsjlogFirstSql(buildUpdateSql("SSKF_JB", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"SSKF_JB", "XZQH_DM", xzqhbgBean, "J", "SSKF_JB", "JB_XH", 1));
		// SSKF_JFDX,需要记录日志
		addYwsjlogFirstSql(buildUpdateSql("SSKF_JFDX", "QZ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"SSKF_JFDX", "QZ_XZQH_DM", xzqhbgBean, "J", "SSKF_JFDX",
				"JFDX_XH", 1));
		addYwsjlogFirstSql(buildUpdateSql("SSKF_JFDX", "ZF_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"SSKF_JFDX", "ZF_XZQH_DM", xzqhbgBean, "J", "SSKF_JFDX",
				"JFDX_XH", 1));
		// SSKF_JFDX_TJ,不记录日志
		addYwsjbgFirstSql(buildUpdateSql("SSKF_JFDX_TJ", "QZ_XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		addYwsjbgFirstSql(buildUpdateSql("SSKF_JFDX_TJ", "ZF_XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////业务系统数据变更End////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////
}
