package com.padis.business.xzqhwh.impl;

import java.util.List;

import com.padis.business.xzqhwh.common.XzqhbgBean;

/**
 * <p>
 * Title: �ӿ�com.padis.business.xzqhwh.common.IXzqhYwsjbg����ϵͳʵ����
 * </P>
 * <p>
 * Description:ʵ�ֽ����������ر�����������츻������ϵͳҵ�����ݵı��<br>
 * ����ϵͳ�����漰���ݱ�(3�ű�)��JF_JFNLPZ(XZQH_DM)<br>
 * ���������漰���ݱ�(4�ű�)��LNJF_JB(XZQH_DM),LNJF_JFDX(XZQH_DM),LNJF_JFDX_BG(XZQH_DM),LNJF_JFDX_TJ(XZQH_DM)<br>
 * �ر�����漰���ݱ�(4�ű�)��TBFZ_JB(XZQH_DM),TBFZ_JFDX(XZQH_DM),TBFZ_JFDX_BG(XZQH_DM),TBFZ_JFDX_TJ(XZQH_DM)<br>
 * �����츻�漰���ݱ�(3�ű�)��SSKF_JB(XZQH_DM),SSKF_JFDX(QZ_XZQH_DM,ZF_XZQH_DM),SSKF_JFDX_TJ(ZF_XZQH_DM,QZ_XZQH_DM)<br>
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
 * @since 2009-7-10 �޸��ˣ� �޸�ʱ�䣺 �޸����ݣ� �޸İ汾�ţ�
 */
public class JfXzqhYwsjbgImp extends AbstractXzqhYwsjbg {

	protected void buildSqlList(List xzqhbgxx) throws Exception {
		setBatchMode(true);
		if (xzqhbgxx != null || xzqhbgxx.size() > 0) {
			for (int i = 0; i < xzqhbgxx.size(); i++) {
				XzqhbgBean xzqhbgBean = (XzqhbgBean) xzqhbgxx.get(i);
				// String sqbxh = xzqhbgBean.getSqbxh();// �������ţ������������
				// String sqbmxXh = xzqhbgBean.getSqbmxXh();// �������ϸ������ţ���ϸ������
				// String srcXzqhdm = xzqhbgBean.getSrcXzqhdm();// ԭ��������
				// String srcXzqhmc = xzqhbgBean.getSrcXzqhmc();// ԭ��������
				// String destXzqhdm = xzqhbgBean.getDestXzqhdm();// Ŀ����������
				// String destXzqhmc = xzqhbgBean.getDestXzqhmc();// Ŀ����������
				// String bglxdm = xzqhbgBean.getBglxdm();// ������ʹ���
				// String jcdm = xzqhbgBean.getJcdm(); // ���δ���
				jfYwsjBg(xzqhbgBean);
				lnjfYwsjBg(xzqhbgBean);
				tbfzYwsjBg(xzqhbgBean);
				sskfYwsjBg(xzqhbgBean);
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////ҵ��ϵͳ���ݱ��Begin////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////

	/*
	 * ʵ�ֹ���ģ��������������޸�
	 */
	private void jfYwsjBg(XzqhbgBean xzqhbgBean) {
		// JF_JFNLPZ
		addYwsjbgFirstSql(buildUpdateSql("JF_JFNLPZ", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");

	}

	/*
	 * ʵ�����꽱��ģ��������������޸�
	 */
	private void lnjfYwsjBg(XzqhbgBean xzqhbgBean) {
		// LNJF_JB,��Ҫ��¼��־
		addYwsjlogFirstSql(buildUpdateSql("LNJF_JB", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LNJF_JB", "XZQH_DM", xzqhbgBean, "J", "LNJF_JB", "JB_XH", 1));
		// LNJF_JFDX,��Ҫ��¼��־
		addYwsjlogFirstSql(buildUpdateSql("LNJF_JFDX", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"LNJF_JFDX", "XZQH_DM", xzqhbgBean, "J", "LNJF_JFDX",
				"JFDX_XH", 1));
		// LNJF_JFDX_BG,����¼��־
		addYwsjbgFirstSql(buildUpdateSql("LNJF_JFDX_BG", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// LNJF_JFDX_TJ,����¼��־
		addYwsjbgFirstSql(buildUpdateSql("LNJF_JFDX_TJ", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
	}

	/*
	 * ʵ���ر����ģ��������������޸�
	 */
	private void tbfzYwsjBg(XzqhbgBean xzqhbgBean) {
		// TBFZ_JB,��Ҫ��¼��־
		addYwsjlogFirstSql(buildUpdateSql("TBFZ_JB", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"TBFZ_JB", "XZQH_DM", xzqhbgBean, "J", "TBFZ_JB", "JB_XH", 1));
		// TBFZ_JFDX,��Ҫ��¼��־
		addYwsjlogFirstSql(buildUpdateSql("TBFZ_JFDX", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"TBFZ_JFDX", "XZQH_DM", xzqhbgBean, "J", "TBFZ_JFDX",
				"JFDX_XH", 1));
		// TBFZ_JFDX_BG,����¼��־
		addYwsjbgFirstSql(buildUpdateSql("TBFZ_JFDX_BG", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		// TBFZ_JFDX_TJ,����¼��־
		addYwsjbgFirstSql(buildUpdateSql("TBFZ_JFDX_TJ", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
	}

	/*
	 * ʵ�������츻ģ��������������޸�
	 * SSKF_JFDX(QZ_XZQH_DM,ZF_XZQH_DM),SSKF_JFDX_TJ(ZF_XZQH_DM,QZ_XZQH_DM)<br>
	 */
	private void sskfYwsjBg(XzqhbgBean xzqhbgBean) {
		// SSKF_JB,��Ҫ��¼��־
		addYwsjlogFirstSql(buildUpdateSql("SSKF_JB", "XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"SSKF_JB", "XZQH_DM", xzqhbgBean, "J", "SSKF_JB", "JB_XH", 1));
		// SSKF_JFDX,��Ҫ��¼��־
		addYwsjlogFirstSql(buildUpdateSql("SSKF_JFDX", "QZ_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"SSKF_JFDX", "QZ_XZQH_DM", xzqhbgBean, "J", "SSKF_JFDX",
				"JFDX_XH", 1));
		addYwsjlogFirstSql(buildUpdateSql("SSKF_JFDX", "ZF_XZQH_DM", xzqhbgBean
				.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), buildLogSql(
				"SSKF_JFDX", "ZF_XZQH_DM", xzqhbgBean, "J", "SSKF_JFDX",
				"JFDX_XH", 1));
		// SSKF_JFDX_TJ,����¼��־
		addYwsjbgFirstSql(buildUpdateSql("SSKF_JFDX_TJ", "QZ_XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
		addYwsjbgFirstSql(buildUpdateSql("SSKF_JFDX_TJ", "ZF_XZQH_DM",
				xzqhbgBean.getSrcXzqhdm(), xzqhbgBean.getDestXzqhdm()), "");
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////ҵ��ϵͳ���ݱ��End////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////
}
