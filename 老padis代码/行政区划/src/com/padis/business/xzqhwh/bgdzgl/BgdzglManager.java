/**
 * 
 */
package com.padis.business.xzqhwh.bgdzgl;

import com.padis.business.xzqhwh.common.Common;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.jgservice.JgInterfaceFactory;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p>
 * Title: BgdzqlManager.java
 * </p>
 * <p>
 * Description:<��Ĺ�������>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: DigitalChina Co.Ltd
 * </p>
 * 
 * @date 2009-10-22
 * @author pengld
 * @version 1.0 �޸���ʷ�� �޸��� �޸�ʱ��(yyyy/mm/dd) �޸����� �汾��
 */
public class BgdzglManager {

	public String queryDzb(XMLDataObject xdo, long args[], String jg_dm)
			throws Exception {
		String clzt_dm = StringEx.sNull(xdo.getItemValue("CLZT_DM"));
		String tjsjq = StringEx.sNull(xdo.getItemValue("TJSJQ"));
		String tjsjz = StringEx.sNull(xdo.getItemValue("TJSJZ"));
		String bglx_dm = StringEx.sNull(xdo.getItemValue("BGLX_DM"));
		String bgxzqh_dm = StringEx.sNull(xdo.getItemValue("BGXZQH_DM"));
		if (bgxzqh_dm.equals("")) {
			bgxzqh_dm = JgInterfaceFactory.getInstance().getInterfaceImp()
					.getXzqhDmByJg(jg_dm);
		}
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); // ÿҳ����������
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); // ҳ������
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer sql = new StringBuffer(
				"select sqd.sbxzqh_dm,sqd.sqdmc,gro.groupmc,mxb.ysxzqh_dm,mxb.ysxzqh_mc,mxb.bglx_dm,mxb.mbxzqh_dm,mxb.mbxzqh_mc,mxb.clzt_dm,mxb.cljg,gro.lrsj from xzqh_bgsqd sqd ,xzqh_bggroup gro ,xzqh_bgmxb mxb ");
		sql.append("  where sqd.sqdxh=gro.sqdxh and gro.groupxh=mxb.groupxh ");
		if (!clzt_dm.equals("")) {
			sql.append(" and mxb.clzt_dm='" + clzt_dm + "'");
		}
		if (!tjsjq.equals("")) {
			sql.append(" and gro.lrsj >= TO_DATE('" + tjsjq
					+ "','YYYY-MM-DD') ");
		}
		if (!tjsjz.equals("")) {
			sql
					.append(" and gro.lrsj < TO_DATE('" + tjsjz
							+ "','YYYY-MM-DD') ");
		}
		if (bglx_dm.equals("")) {
			sql.append(" and (mxb.ysxzqh_dm like '" + Common.getJbdm(bgxzqh_dm)
					+ "%' or mxb.mbxzqh_dm like '" + Common.getJbdm(bgxzqh_dm)
					+ "%')");
		} else {
			sql.append(" and bglx_dm='"+bglx_dm+"'");
			if (bglx_dm.equals(Common.ADD)) {
				sql.append(" and mxb.mbxzqh_dm like '"
						+ Common.getJbdm(bgxzqh_dm) + "%'");
			} else {
				sql.append(" and mxb.ysxzqh_dm like '"
						+ Common.getJbdm(bgxzqh_dm) + "%'");
			}
		}
		DataWindow dw = DataWindow.dynamicCreate(sql.toString(), true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// �������ӳ�
		long rowCount = dw.getRowCount(sql.toString());
		long ps = Long.parseLong(pageSize); // һҳ�а������������ݣ����з�ҳ׼��
		long pageCount = dw.getPageCount(ps); // һ������ҳ
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex), pageCount, sql
				.toString());
		args[0] = pageCount;
		args[1] = rowCount;
		String resultXml = "";
		if (cnt > 0) {
			Dmmc.convDmmc(dw, "BGLX_DM", "V_DM_XZQH_BGLX");
			Dmmc.convDmmc(dw, "CLZT_DM", "V_DM_XZQH_CLZT");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}

}
