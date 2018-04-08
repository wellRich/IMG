package com.padis.business.xzqhwh.impl;

import java.util.List;

import com.padis.business.xzqhwh.common.XzqhbgBean;
import com.padis.common.jgservice.JgInterfaceFactory;

import ctais.util.StringEx;

/**
 * <p>
 * Title: 接口com.padis.business.xzqhwh.common.IXzqhYwsjbg奖扶系统实现类
 * </P>
 * <p>
 * Description:实现行政区划调整时流管子系统业务数据的变更<br>
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
 * @since 2009-7-10 修改人： 修改时间： 修改内容： 修改版本号：-- buildSqlList
 */
public class LgXzqhYwsjbgImp extends AbstractXzqhYwsjbg {
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
				String bglxdm = xzqhbgBean.getBglxdm();// 变更类型代码
				String jcdm = xzqhbgBean.getJcdm(); // 级次代码
				if(bglxdm.equals("31")||bglxdm.equals("41")){
					lgYwsjBgExistsJg(xzqhbgBean); //5555555555555555
				}else{
					lgYwsjBg(xzqhbgBean);
					if(jcdm !=null && Integer.parseInt(jcdm)<=4)
					{
/*************************** 对于统计、监控相关的报表，为了能够查询到历史数据，所以不进行行政区划变更，
                         *** 查询历史统计、监控的历史数据时，可根据行政区划的有效期起止查询往年行政区划下的报表数据
//						lgjkYwsjBg(xzqhbgBean);
//						lgtjYwsjBg(xzqhbgBean);
***************************/			
					}
				}
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////业务系统数据变更Begin////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/**
	 * lgYwsjBg() 方法描述:实现流动管理模块业务数据表行政区划变更,需要记录日志
	 * 
	 * @param xzqhbgBean
	 */

	private void lgYwsjBg(XzqhbgBean xzqhbgBean) {

		// LG_HJXX :XZQH_DM 主键：HJXX_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_HJXX", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_HJXX", "XZQH_DM", xzqhbgBean, "L", "LG_HJXX", "HJXX_XL", 1));
		// LG_HJXX :QRDXZQH_DM 主键：HJXX_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_HJXX", "QRDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_HJXX", "QRDXZQH_DM", xzqhbgBean, "L", "LG_HJXX", "HJXX_XL",
				1));
		// LG_JBXX :HJDXZQH_DM 主键：RKDZDAH
		addYwsjlogFirstSql(buildUpdateSql("LG_JBXX", "HJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_JBXX", "HJDXZQH_DM", xzqhbgBean, "L", "LG_JBXX", "RKDZDAH",
				1));
		// LG_JBXX :ZFXJDXZQH_DM 主键：RKDZDAH
		addYwsjlogFirstSql(buildUpdateSql("LG_JBXX", "ZFXJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_JBXX", "ZFXJDXZQH_DM", xzqhbgBean, "L", "LG_JBXX",
				"RKDZDAH", 1));
		// LG_JBXX :ZFHJDXZQH_DM 主键：RKDZDAH
		addYwsjlogFirstSql(buildUpdateSql("LG_JBXX", "ZFHJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_JBXX", "ZFHJDXZQH_DM", xzqhbgBean, "L", "LG_JBXX",
				"RKDZDAH", 1));
		// LG_JBXX :XJZDXZQH_DM 主键：RKDZDAH
		addYwsjlogFirstSql(buildUpdateSql("LG_JBXX", "XJZDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_JBXX", "XJZDXZQH_DM", xzqhbgBean, "L", "LG_JBXX",
				"RKDZDAH", 1));
		// LG_LDDJ :HJDXZQH_DM 主键：YFLDDJ_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_LDDJ", "HJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_LDDJ", "HJDXZQH_DM", xzqhbgBean, "L", "LG_LDDJ",
				"YFLDDJ_XL", 1));
		// LG_LDDJ :XJZDXZQH_DM 主键：YFLDDJ_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_LDDJ", "XJZDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_LDDJ", "XJZDXZQH_DM", xzqhbgBean, "L", "LG_LDDJ",
				"YFLDDJ_XL", 1));
		// LG_LDDJ :ZFXJDXZQH_DM 主键：YFLDDJ_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_LDDJ", "ZFXJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_LDDJ", "ZFXJDXZQH_DM", xzqhbgBean, "L", "LG_LDDJ",
				"YFLDDJ_XL", 1));
		// LG_LDDJ :ZFHJDXZQH_DM 主键：YFLDDJ_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_LDDJ", "ZFHJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_LDDJ", "ZFHJDXZQH_DM", xzqhbgBean, "L", "LG_LDDJ",
				"YFLDDJ_XL", 1));
		// LG_LX_SCWJXX :XZQH_DM 主键：SCWJ_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_LX_SCWJXX", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_LX_SCWJXX", "XZQH_DM", xzqhbgBean, "L", "LG_LX_SCWJXX",
				"SCWJ_XL", 1));
		// LG_LX_XZWJXX :XZQH_DM 主键：
		addYwsjbgFirstSql(buildUpdateSql("LG_LX_XZWJXX", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_RSXX :XJDXZQH_DM 主键：RSXX_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_RSXX", "XJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_RSXX", "XJDXZQH_DM", xzqhbgBean, "L", "LG_RSXX", "RSXX_XL",
				1));
		// LG_RSXX :HJDXZQH_DM 主键：RSXX_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_RSXX", "HJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_RSXX", "HJDXZQH_DM", xzqhbgBean, "L", "LG_RSXX", "RSXX_XL",
				1));
		// LG_SCXX :HJD_XZQHDM 主键：SCXX_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_SCXX", "HJD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_SCXX", "HJD_XZQHDM", xzqhbgBean, "L", "LG_SCXX", "SCXX_XL",
				1));
		// LG_SCXX :XJD_XZQHDM 主键：SCXX_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_SCXX", "XJD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_SCXX", "XJD_XZQHDM", xzqhbgBean, "L", "LG_SCXX", "SCXX_XL",
				1));
		// LG_SHFYFZS :XJD_XZQHDM 主键：SHFYFZS_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_SHFYFZS", "XJD_XZQHDM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_SHFYFZS", "XJD_XZQHDM", xzqhbgBean, "L",
						"LG_SHFYFZS", "SHFYFZS_XL", 1));
		// LG_SHFYFZS :HJD_XZQHDM 主键：SHFYFZS_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_SHFYFZS", "HJD_XZQHDM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_SHFYFZS", "HJD_XZQHDM", xzqhbgBean, "L",
						"LG_SHFYFZS", "SHFYFZS_XL", 1));
		// LG_SSFW :HJD_XZQHDM 主键：SSFW_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_SSFW", "HJD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_SSFW", "HJD_XZQHDM", xzqhbgBean, "L", "LG_SSFW", "SSFW_XL",
				1));
		// LG_SSFW :XJD_XZQHDM 主键：SSFW_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_SSFW", "XJD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_SSFW", "XJD_XZQHDM", xzqhbgBean, "L", "LG_SSFW", "SSFW_XL",
				1));
		// LG_SYQK :XJD_XZQHDM 主键：SYQK_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_SYQK", "XJD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_SYQK", "XJD_XZQHDM", xzqhbgBean, "L", "LG_SYQK", "SYQK_XL",
				1));
		// LG_SYQK :HJD_XZQHDM 主键：SYQK_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_SYQK", "HJD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_SYQK", "HJD_XZQHDM", xzqhbgBean, "L", "LG_SYQK", "SYQK_XL",
				1));
		// LG_TBFK :JSD_XZQHDM 主键：TBH
		addYwsjlogFirstSql(buildUpdateSql("LG_TBFK", "JSD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_TBFK", "JSD_XZQHDM", xzqhbgBean, "L", "LG_TBFK", "TBH", 1));
		// LG_TBFK :FSD_XZQHDM 主键：TBH
		addYwsjlogFirstSql(buildUpdateSql("LG_TBFK", "FSD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_TBFK", "FSD_XZQHDM", xzqhbgBean, "L", "LG_TBFK", "TBH", 1));
		// LG_XCFK :FSD_XZQHDM 主键：XCH
		addYwsjlogFirstSql(buildUpdateSql("LG_XCFK", "FSD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_XCFK", "FSD_XZQHDM", xzqhbgBean, "L", "LG_XCFK", "XCH", 1));
		// LG_XCFK :JSD_XZQHDM 主键：XCH
		addYwsjlogFirstSql(buildUpdateSql("LG_XCFK", "JSD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_XCFK", "JSD_XZQHDM", xzqhbgBean, "L", "LG_XCFK", "XCH", 1));
		// SCWJGL_WJJBXX :XZQH_DM 主键：WJXL
		addYwsjlogFirstSql(buildUpdateSql("SCWJGL_WJJBXX", "XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("SCWJGL_WJJBXX", "XZQH_DM", xzqhbgBean, "L",
						"SCWJGL_WJJBXX", "WJXL", 1));
/************************************************************************************/	
//		LG_BYJYBGD : HJDXZQH_DM  主键 ：BGDXH
		addYwsjlogFirstSql(buildUpdateSql("LG_BYJYBGD", "HJDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_BYJYBGD", "HJDXZQH_DM", xzqhbgBean, "L",
						"LG_BYJYBGD", "BGDXH", 1));
//		LG_BYJYBGD : XJZDXZQH_DM 主键 ：BGDXH
		addYwsjlogFirstSql(buildUpdateSql("LG_BYJYBGD", "XJZDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_BYJYBGD", "XJZDXZQH_DM", xzqhbgBean, "L",
						"LG_BYJYBGD", "BGDXH", 1));
//		LG_BYJYBGD_TBXX : FSDXZQH_DM  主键 ：TBXH
		addYwsjlogFirstSql(buildUpdateSql("LG_BYJYBGD_TBXX", "FSDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_BYJYBGD_TBXX", "FSDXZQH_DM", xzqhbgBean, "L",
						"LG_BYJYBGD_TBXX", "TBXH", 1));
//		LG_BYJYBGD_TBXX : JSDXZQH_DM  主键 ：TBXH
		addYwsjlogFirstSql(buildUpdateSql("LG_BYJYBGD_TBXX", "JSDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_BYJYBGD_TBXX", "JSDXZQH_DM", xzqhbgBean, "L",
						"LG_BYJYBGD_TBXX", "TBXH", 1));
//		LG_YHSYFWDJD : NVFHJDXZQH_DM  主键 ：DJDXH
		addYwsjlogFirstSql(buildUpdateSql("LG_YHSYFWDJD", "NVFHJDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_YHSYFWDJD", "NVFHJDXZQH_DM", xzqhbgBean, "L",
						"LG_YHSYFWDJD", "DJDXH", 1));
//		LG_YHSYFWDJD : NVFXJZDXZQH_DM  主键 ：DJDXH
		addYwsjlogFirstSql(buildUpdateSql("LG_YHSYFWDJD", "NVFXJZDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_YHSYFWDJD", "NVFXJZDXZQH_DM", xzqhbgBean, "L",
						"LG_YHSYFWDJD", "DJDXH", 1));
//		LG_YHSYFWDJD : NFHJDXZQH_DM  主键 ：DJDXH
		addYwsjlogFirstSql(buildUpdateSql("LG_YHSYFWDJD", "NFHJDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_YHSYFWDJD", "NFHJDXZQH_DM", xzqhbgBean, "L",
						"LG_YHSYFWDJD", "DJDXH", 1));
//		LG_YHSYFWDJD : NFXJZDXZQH_DM  主键 ：DJDXH
		addYwsjlogFirstSql(buildUpdateSql("LG_YHSYFWDJD", "NFXJZDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_YHSYFWDJD", "NFXJZDXZQH_DM", xzqhbgBean, "L",
						"LG_YHSYFWDJD", "DJDXH", 1));		
//		LG_YHSYFWDJD_TBXX : FSDXZQH_DM  主键 ：TBXH
		addYwsjlogFirstSql(buildUpdateSql("LG_YHSYFWDJD_TBXX", "FSDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_YHSYFWDJD_TBXX", "FSDXZQH_DM", xzqhbgBean, "L",
						"LG_YHSYFWDJD_TBXX", "TBXH", 1));
//		LG_YHSYFWDJD_TBXX : JSDXZQH_DM  主键 ：TBXH
		addYwsjlogFirstSql(buildUpdateSql("LG_YHSYFWDJD_TBXX", "JSDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_YHSYFWDJD_TBXX", "JSDXZQH_DM", xzqhbgBean, "L",
						"LG_YHSYFWDJD_TBXX", "TBXH", 1));
//=============================================================================

	}
	/**
	 * 
	 * <p>方法名称：lgYwsjBgExistsJg</p>
	 * <p>方法描述：流管业务数据变更包含机构变更</p>
	 * @param xzqhbgBean
	 * @throws Exception
	 * @author User
	 * @since 2009-9-15
	 */
	private void lgYwsjBgExistsJg(XzqhbgBean xzqhbgBean) throws Exception {
		// LG_HJXX :XZQH_DM 主键：HJXX_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_HJXX", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QCD_JGDM"), buildLogSql(
				"LG_HJXX", "XZQH_DM", xzqhbgBean, "L", "LG_HJXX", "HJXX_XL", 1));
		// LG_HJXX :QRDXZQH_DM 主键：HJXX_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_HJXX", "QRDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QRD_JGDM"), buildLogSql(
				"LG_HJXX", "QRDXZQH_DM", xzqhbgBean, "L", "LG_HJXX", "HJXX_XL",
				1));
		// LG_JBXX :HJDXZQH_DM 主键：RKDZDAH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_JBXX", "HJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"HJD_JGDM"), buildLogSql(
				"LG_JBXX", "HJDXZQH_DM", xzqhbgBean, "L", "LG_JBXX", "RKDZDAH",
				1));
		// LG_JBXX :ZFXJDXZQH_DM 主键：RKDZDAH
		addYwsjlogFirstSql(buildUpdateSql("LG_JBXX", "ZFXJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_JBXX", "ZFXJDXZQH_DM", xzqhbgBean, "L", "LG_JBXX",
				"RKDZDAH", 1));
		// LG_JBXX :ZFHJDXZQH_DM 主键：RKDZDAH
		addYwsjlogFirstSql(buildUpdateSql("LG_JBXX", "ZFHJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_JBXX", "ZFHJDXZQH_DM", xzqhbgBean, "L", "LG_JBXX",
				"RKDZDAH", 1));
		// LG_JBXX :XJZDXZQH_DM 主键：RKDZDAH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_JBXX", "XJZDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"), buildLogSql(
				"LG_JBXX", "XJZDXZQH_DM", xzqhbgBean, "L", "LG_JBXX",
				"RKDZDAH", 1));
		// LG_LDDJ :HJDXZQH_DM 主键：YFLDDJ_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_LDDJ", "HJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"HJD_JGDM"), buildLogSql(
				"LG_LDDJ", "HJDXZQH_DM", xzqhbgBean, "L", "LG_LDDJ",
				"YFLDDJ_XL", 1));
		// LG_LDDJ :XJZDXZQH_DM 主键：YFLDDJ_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_LDDJ", "XJZDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"), buildLogSql(
				"LG_LDDJ", "XJZDXZQH_DM", xzqhbgBean, "L", "LG_LDDJ",
				"YFLDDJ_XL", 1));
		// LG_LDDJ :ZFXJDXZQH_DM 主键：YFLDDJ_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_LDDJ", "ZFXJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_LDDJ", "ZFXJDXZQH_DM", xzqhbgBean, "L", "LG_LDDJ",
				"YFLDDJ_XL", 1));
		// LG_LDDJ :ZFHJDXZQH_DM 主键：YFLDDJ_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_LDDJ", "ZFHJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_LDDJ", "ZFHJDXZQH_DM", xzqhbgBean, "L", "LG_LDDJ",
				"YFLDDJ_XL", 1));
		// LG_LX_SCWJXX :XZQH_DM 主键：SCWJ_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_LX_SCWJXX", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"), buildLogSql(
				"LG_LX_SCWJXX", "XZQH_DM", xzqhbgBean, "L", "LG_LX_SCWJXX",
				"SCWJ_XL", 1));
		// LG_LX_XZWJXX :XZQH_DM 主键：
		addYwsjbgFirstSql(buildUpdateSqlExistsJg("LG_LX_XZWJXX", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"), "");
		// LG_RSXX :XJDXZQH_DM 主键：RSXX_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_RSXX", "XJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_RSXX", "XJDXZQH_DM", xzqhbgBean, "L", "LG_RSXX", "RSXX_XL",
				1));
		// LG_RSXX :HJDXZQH_DM 主键：RSXX_XL
		addYwsjlogFirstSql(buildUpdateSql("LG_RSXX", "HJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LG_RSXX", "HJDXZQH_DM", xzqhbgBean, "L", "LG_RSXX", "RSXX_XL",
				1));
		// LG_SCXX :HJD_XZQHDM 主键：SCXX_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_SCXX", "HJD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"HJD_JGDM"), buildLogSql(
				"LG_SCXX", "HJD_XZQHDM", xzqhbgBean, "L", "LG_SCXX", "SCXX_XL",
				1));
		// LG_SCXX :XJD_XZQHDM 主键：SCXX_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_SCXX", "XJD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"), buildLogSql(
				"LG_SCXX", "XJD_XZQHDM", xzqhbgBean, "L", "LG_SCXX", "SCXX_XL",
				1));
		// LG_SHFYFZS :XJD_XZQHDM 主键：SHFYFZS_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_SHFYFZS", "XJD_XZQHDM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"),
				buildLogSql("LG_SHFYFZS", "XJD_XZQHDM", xzqhbgBean, "L",
						"LG_SHFYFZS", "SHFYFZS_XL", 1));
		// LG_SHFYFZS :HJD_XZQHDM 主键：SHFYFZS_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_SHFYFZS", "HJD_XZQHDM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"HJD_JGDM"),
				buildLogSql("LG_SHFYFZS", "HJD_XZQHDM", xzqhbgBean, "L",
						"LG_SHFYFZS", "SHFYFZS_XL", 1));
		// LG_SSFW :HJD_XZQHDM 主键：SSFW_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_SSFW", "HJD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"HJD_JGDM"), buildLogSql(
				"LG_SSFW", "HJD_XZQHDM", xzqhbgBean, "L", "LG_SSFW", "SSFW_XL",
				1));
		// LG_SSFW :XJD_XZQHDM 主键：SSFW_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_SSFW", "XJD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"), buildLogSql(
				"LG_SSFW", "XJD_XZQHDM", xzqhbgBean, "L", "LG_SSFW", "SSFW_XL",
				1));
		// LG_SYQK :XJD_XZQHDM 主键：SYQK_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_SYQK", "XJD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"), buildLogSql(
				"LG_SYQK", "XJD_XZQHDM", xzqhbgBean, "L", "LG_SYQK", "SYQK_XL",
				1));
		// LG_SYQK :HJD_XZQHDM 主键：SYQK_XL
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_SYQK", "HJD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"HJD_JGDM"), buildLogSql(
				"LG_SYQK", "HJD_XZQHDM", xzqhbgBean, "L", "LG_SYQK", "SYQK_XL",
				1));
		// LG_TBFK :JSD_XZQHDM 主键：TBH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_TBFK", "JSD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"JS_JGDM"), buildLogSql(
				"LG_TBFK", "JSD_XZQHDM", xzqhbgBean, "L", "LG_TBFK", "TBH", 1));
		// LG_TBFK :FSD_XZQHDM 主键：TBH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_TBFK", "FSD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"), buildLogSql(
				"LG_TBFK", "FSD_XZQHDM", xzqhbgBean, "L", "LG_TBFK", "TBH", 1));
		// LG_XCFK :FSD_XZQHDM 主键：XCH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_XCFK", "FSD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"), buildLogSql(
				"LG_XCFK", "FSD_XZQHDM", xzqhbgBean, "L", "LG_XCFK", "XCH", 1));
		// LG_XCFK :JSD_XZQHDM 主键：XCH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_XCFK", "JSD_XZQHDM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"JS_JGDM"), buildLogSql(
				"LG_XCFK", "JSD_XZQHDM", xzqhbgBean, "L", "LG_XCFK", "XCH", 1));
		// SCWJGL_WJJBXX :XZQH_DM 主键：WJXL
		addYwsjlogFirstSql(buildUpdateSql("SCWJGL_WJJBXX", "XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("SCWJGL_WJJBXX", "XZQH_DM", xzqhbgBean, "L",
						"SCWJGL_WJJBXX", "WJXL", 1));
/***************************************************************************************/
//		LG_BYJYBGD : HJDXZQH_DM  主键 ：BGDXH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_BYJYBGD", "HJDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"HJD_JGDM"),
				buildLogSql("LG_BYJYBGD", "HJDXZQH_DM", xzqhbgBean, "L",
						"LG_BYJYBGD", "BGDXH", 1));
//		LG_BYJYBGD : XJZDXZQH_DM 主键 ：BGDXH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_BYJYBGD", "XJZDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"),
				buildLogSql("LG_BYJYBGD", "XJZDXZQH_DM", xzqhbgBean, "L",
						"LG_BYJYBGD", "BGDXH", 1));
//		LG_BYJYBGD_TBXX : FSDXZQH_DM  主键 ：TBXH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_BYJYBGD_TBXX", "FSDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"),
				buildLogSql("LG_BYJYBGD_TBXX", "FSDXZQH_DM", xzqhbgBean, "L",
						"LG_BYJYBGD_TBXX", "TBXH", 1));
//		LG_BYJYBGD_TBXX : JSDXZQH_DM  主键 ：TBXH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_BYJYBGD_TBXX", "JSDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"JS_JGDM"),
				buildLogSql("LG_BYJYBGD_TBXX", "JSDXZQH_DM", xzqhbgBean, "L",
						"LG_BYJYBGD_TBXX", "TBXH", 1));
//		LG_YHSYFWDJD : NVFHJDXZQH_DM  主键 ：DJDXH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_YHSYFWDJD", "NVFHJDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"),
				buildLogSql("LG_YHSYFWDJD", "NVFHJDXZQH_DM", xzqhbgBean, "L",
						"LG_YHSYFWDJD", "DJDXH", 1));
//		LG_YHSYFWDJD : NVFXJZDXZQH_DM  主键 ：DJDXH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_YHSYFWDJD", "NVFXJZDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"HJD_JGDM"),
				buildLogSql("LG_YHSYFWDJD", "NVFXJZDXZQH_DM", xzqhbgBean, "L",
						"LG_YHSYFWDJD", "DJDXH", 1));
//		LG_YHSYFWDJD : NFHJDXZQH_DM  主键 ：DJDXH
		addYwsjlogFirstSql(buildUpdateSql("LG_YHSYFWDJD", "NFHJDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_YHSYFWDJD", "NFHJDXZQH_DM", xzqhbgBean, "L",
						"LG_YHSYFWDJD", "DJDXH", 1));
//		LG_YHSYFWDJD : NFXJZDXZQH_DM  主键 ：DJDXH
		addYwsjlogFirstSql(buildUpdateSql("LG_YHSYFWDJD", "NFXJZDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),
				buildLogSql("LG_YHSYFWDJD", "NFXJZDXZQH_DM", xzqhbgBean, "L",
						"LG_YHSYFWDJD", "DJDXH", 1));		
//		LG_YHSYFWDJD_TBXX : FSDXZQH_DM  主键 ：TBXH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_YHSYFWDJD_TBXX", "FSDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"QX_JGDM"),
				buildLogSql("LG_YHSYFWDJD_TBXX", "FSDXZQH_DM", xzqhbgBean, "L",
						"LG_YHSYFWDJD_TBXX", "TBXH", 1));
//		LG_YHSYFWDJD_TBXX : JSDXZQH_DM  主键 ：TBXH
		addYwsjlogFirstSql(buildUpdateSqlExistsJg("LG_YHSYFWDJD_TBXX", "JSDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm(),"JS_JGDM"),
				buildLogSql("LG_YHSYFWDJD_TBXX", "JSDXZQH_DM", xzqhbgBean, "L",
						"LG_YHSYFWDJD_TBXX", "TBXH", 1));

	}
	/**
	 * 
	 * <p>方法名称：buildUpdateSqlExistsJg</p>
	 * <p>方法描述：</p>
	 * @param tableName
	 * @param xzqhColumnName
	 * @param srcXzqhdm
	 * @param destXzqhdm
	 * @param jgColumnName
	 * @return
	 * @throws Exception
	 * @author User
	 * @since 2009-9-15
	 */
	public String buildUpdateSqlExistsJg(String tableName, String xzqhColumnName,
			String srcXzqhdm, String destXzqhdm,String jgColumnName)throws Exception{
//		如果机构代码为空，这机构代码不进行变更
		String jgdm = this.getJgDmByXzqh(destXzqhdm);
		String sql="";
		if(jgdm.equals(""))
		{
			sql = "UPDATE " + tableName + " SET " + xzqhColumnName + "='"
			+ destXzqhdm + "'   WHERE " + xzqhColumnName + "='" + srcXzqhdm
			+ "'";		
		}else
		{
			sql = "UPDATE " + tableName + " SET " + xzqhColumnName + "='"
			+ destXzqhdm + "'," + jgColumnName + "='" + jgdm + "'  WHERE " + xzqhColumnName + "='" + srcXzqhdm
			+ "'";		
		}

		return sql;
	}
	/**
	 * <p>方法名称：getJgDmByXzqh</p>
	 * <p>方法描述：根据行政区划取机构</p>
	 * @param forXzqhDm
	 * @return
	 * @throws Exception
	 * @author User
	 * @since 2009-9-15
	 */
	public String getJgDmByXzqh(String forXzqhDm)throws Exception{

		String jgdm = StringEx.sNull(JgInterfaceFactory.getInstance().getInterfaceImp().getJgdmByXzqhdm(forXzqhDm.substring(0,9)+"000000"));
		if(jgdm.equals("")){
			jgdm = StringEx.sNull(JgInterfaceFactory.getInstance().getInterfaceImp().getJgdmByXzqhdm(forXzqhDm.substring(0,6)+"000000000"));
		}
		return jgdm;
	}
	
	/**
	 * lgjkYwsjBg() 方法描述:实现流动管理模块监控表行政区划变更
	 * 
	 * @param xzqhbgBean
	 */
	private void lgjkYwsjBg(XzqhbgBean xzqhbgBean) {
		// LG_JK_CWCRYY :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_CWCRYY", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_CWCRYY :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_CWCRYY", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_HJDDJ :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_HJDDJ", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_HJDDJ :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_HJDDJ", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_HJDFK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_HJDFK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_HJDFK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_HJDFK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_HJDFW :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_HJDFW", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_HJDFW :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_HJDFW", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_HJDHS :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_HJDHS", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_HJDHS :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_HJDHS", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_HJDJS :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_HJDJS", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_HJDJS :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_HJDJS", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_HJDXC :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_HJDXC", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_HJDXC :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_HJDXC", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_PTYYQK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_PTYYQK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_PTYYQK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_PTYYQK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_XJDDJ :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_XJDDJ", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_XJDDJ :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_XJDDJ", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_XJDFK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_XJDFK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_XJDFK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_XJDFK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_XJDFW :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_XJDFW", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_XJDFW :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_XJDFW", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_XJDHS :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_XJDHS", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_XJDHS :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_XJDHS", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_XJDJS :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_XJDJS", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_XJDJS :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_XJDJS", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_XJDXC :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_XJDXC", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_JK_XJDXC :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_JK_XJDXC", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
	}

	/**
	 * lgtjYwsjBg() 方法描述:实现流动管理模块统计表行政区划变更
	 * 
	 * @param xzqhbgBean
	 */

	private void lgtjYwsjBg(XzqhbgBean xzqhbgBean) {
		// LG_TJ_BYZK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_BYZK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_BYZK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_BYZK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_DSZLZQK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_DSZLZQK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_DSZLZQK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_DSZLZQK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_GB :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_GB", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_HJDLXFB :XJDXZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_HJDLXFB", "XJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_HJDLXFB :HJDXZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_HJDLXFB", "HJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_HJDLXFB :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_HJDLXFB", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_HJDLXFB :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_HJDLXFB", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_HKXZ :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_HKXZ", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_HKXZ :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_HKXZ", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_HYZK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_HYZK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_HYZK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_HYZK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_HYZMQK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_HYZMQK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_HYZMQK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_HYZMQK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_JYQJCQK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_JYQJCQK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_JYQJCQK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_JYQJCQK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_MZ :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_MZ", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_MZ :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_MZ", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_NLFB :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_NLFB", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_NLFB :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_NLFB", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_RSXX :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_RSXX", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_RSXX :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_RSXX", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_SHFYFZS :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_SHFYFZS", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_SHFYFZS :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_SHFYFZS", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_SSFWQK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_SSFWQK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_SSFWQK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_SSFWQK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_SYQK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_SYQK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_SYQK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_SYQK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_SZJKQK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_SZJKQK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_SZJKQK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_SZJKQK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_XJDLXFB :HJDXZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_XJDLXFB", "HJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_XJDLXFB :XJDXZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_XJDLXFB", "XJDXZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_XJDLXFB :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_XJDLXFB", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_XJDLXFB :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_XJDLXFB", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_XYZNQK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_XYZNQK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_XYZNQK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_XYZNQK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_YJQK :SJ_XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_YJQK", "SJ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_TJ_YJQK :XZQH_DM
		addYwsjbgFirstSql(buildUpdateSql("LG_TJ_YJQK", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		
/***********************************************************************************/
		// LG_BYJYBGD_JSTBQKTJ : XZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_BYJYBGD_JSTBQKTJ", "XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_BYJYBGD_JSTBQKTJ : SJ_XZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_BYJYBGD_JSTBQKTJ", "SJ_XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_BYJYBGD_TBQKFSTJ : XZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_BYJYBGD_TBQKFSTJ", "XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_BYJYBGD_TBQKFSTJ : SJ_XZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_BYJYBGD_TBQKFSTJ", "SJ_XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_BYJYBGD_TBQKFSTJ : HJDXZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_BYJYBGD_TBQKFSTJ", "HJDXZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_BYJYBGD_TBQKTJ : XZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_BYJYBGD_TBQKTJ", "XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_BYJYBGD_TBQKTJ : SJ_XZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_BYJYBGD_TBQKTJ", "SJ_XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_YHSYFWDJD_JSTBQKTJ : XZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_YHSYFWDJD_JSTBQKTJ", "XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_YHSYFWDJD_JSTBQKTJ : SJ_XZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_YHSYFWDJD_JSTBQKTJ",
				"SJ_XZQH_DM", xzqhbgBean.getSrcXzqhdm(), xzqhbgBean
						.getDestXzqhdm()), "");
		// LG_YHSYFWDJD_TBQKFSTJ : XZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_YHSYFWDJD_TBQKFSTJ", "XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_YHSYFWDJD_TBQKFSTJ : SJ_XZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_YHSYFWDJD_TBQKFSTJ",
				"SJ_XZQH_DM", xzqhbgBean.getSrcXzqhdm(), xzqhbgBean
						.getDestXzqhdm()), "");
		// LG_YHSYFWDJD_TBQKFSTJ : HJDXZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_YHSYFWDJD_TBQKFSTJ",
				"HJDXZQH_DM", xzqhbgBean.getSrcXzqhdm(), xzqhbgBean
						.getDestXzqhdm()), "");
		// LG_YHSYFWDJD_TBQKTJ : XZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_YHSYFWDJD_TBQKTJ", "XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LG_YHSYFWDJD_TBQKTJ : SJ_XZQH_DM 主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_YHSYFWDJD_TBQKTJ", "SJ_XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");	
//		LG_BYJYBGD_TBXXWJS : FSDXZQH  主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_BYJYBGD_TBXXWJS", "FSDXZQH",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),"");
//		LG_BYJYBGD_TBXXWJS : JSDXZQH  主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_BYJYBGD_TBXXWJS", "JSDXZQH",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),"");
//		LG_YHSYFWDJD_TBXXWJS : FSDXZQH  主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_YHSYFWDJD_TBXXWJS", "FSDXZQH",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),"");
//		LG_YHSYFWDJD_TBXXWJS : JSDXZQH  主键 ：
		addYwsjbgFirstSql(buildUpdateSql("LG_YHSYFWDJD_TBXXWJS", "JSDXZQH",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()),"");
		
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////业务系统数据变更End////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////
//	public static void main(String[] args) {
//		LgXzqhYwsjbgImp obj = new LgXzqhYwsjbgImp();
//		List list  = new ArrayList();
//		XzqhbgBean xzqhbgBean =new XzqhbgBean();
//		xzqhbgBean.setSqbxh("1111");// 申请表序号（申请表主键）
//		xzqhbgBean.setSqbmxXh("2222");// 申请表明细数据序号（明细主键）
//		xzqhbgBean.setSrcXzqhdm("3333");// 原区划代码
//		xzqhbgBean.setSrcXzqhmc("srcmc");// 原区划名称
//		xzqhbgBean.setDestXzqhdm("4444");// 目标区划代码
//		xzqhbgBean.setDestXzqhmc("destmc");// 目标区划名称
//		xzqhbgBean.setBglxdm("");// 变更类型代码
//		xzqhbgBean.setJcdm("4"); // 级次代码
//		list.add(xzqhbgBean);
//		try {
//			obj.buildSqlList(list);
//			 for(int i =0;i<obj.ywsjlogFirstSqlList.size();i++)
//			 {
//				 YwsjBgSqlBean beanTemp = (YwsjBgSqlBean) obj.ywsjlogFirstSqlList.get(i);
//				 System.out.println(beanTemp.getYwsjBgLogSql());
//			 }
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

}
