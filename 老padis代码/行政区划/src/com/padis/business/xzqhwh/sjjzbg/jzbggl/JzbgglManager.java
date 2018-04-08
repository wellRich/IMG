package com.padis.business.xzqhwh.sjjzbg.jzbggl;

import com.padis.business.common.data.xzqh.D_xzqh_jzbgdzb_temp;
import com.padis.business.common.data.xzqh.D_xzqh_jzbgzip;
import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.XzqhbgBean;
import com.padis.business.xzqhwh.common.XzqhbgCommon;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;
import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p>
 * Description: ���б����������ࣨBgrzglService����manager,�ṩBgrzglService�ײ㴦��ķ���
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @since 2009-09-10
 * @author ���
 * @version 1.0
 */

public class JzbgglManager {

	/**
	 * <p>
	 * �������ƣ�queryXzqhjzbgzip()��ʡ�������������б���ļ���¼
	 * </p>
	 * <p>
	 * ����˵����ʡ�������������б���ļ���¼�ķ�����������������XT_XZQHFB���м�����������¼��
	 * </p>
	 * 
	 * @param xmldo
	 *            ��ѯ����
	 * @param args
	 *            ��ҳ��ѯ����
	 * @since 2009-09-10
	 * @author ��� String ���������ĵ����ʼ��б�
	 * @throws Exception
	 */
	public String queryZip(XMLDataObject xmldo, long[] args, String sjxzqh_dm) throws Exception {
		String xzqh_dm = StringEx.sNull(xmldo.getItemValue("XZQH_DM")); // ÿҳ����������
		String rq = StringEx.sNull(xmldo.getItemValue("RQ")); // ҳ������
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); // ÿҳ����������
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); // ҳ������
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer sql = new StringBuffer("");
		StringBuffer queryBuffer = new StringBuffer(
				"SELECT Z.ZIPXH,Z.XZQH_DM,Z.RQ,Z.WJM,Z.JZBGZT_DM,JZBGZT_DM JZBGZTDM,"
						+ "to_char(Z.LRSJ,'YYYY-MM-DD HH24:mi:ss') LRSJ FROM XZQH_JZBGZIP Z");
		if(!sjxzqh_dm.equals("00")&&!sjxzqh_dm.equals("")){
			queryBuffer.append(" WHERE ").append(" substr(Z.XZQH_DM,0,2)='").append(sjxzqh_dm).append("'");
		}
		if (!xzqh_dm.equals("")) {
			sql.append(" AND Z.XZQH_DM='").append(xzqh_dm).append("'");
		}
		if (!rq.equals("")) {
			sql.append(" AND Z.RQ='").append(rq).append("'");
		}
		if (sql.length() > 0) {
			if(queryBuffer.indexOf("WHERE")<0){
				queryBuffer.append(" WHERE ").append(sql.substring(5));
			}else{
				queryBuffer.append(sql.toString());
			}
		}
		queryBuffer.append(" order by Z.LRSJ desc");
		DataWindow dw = DataWindow.dynamicCreate(queryBuffer.toString(), true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// �������ӳ�
		long rowCount = dw.getRowCount(queryBuffer.toString());
		long ps = Long.parseLong(pageSize); // һҳ�а������������ݣ����з�ҳ׼��
		long pageCount = dw.getPageCount(ps); // һ������ҳ
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex), pageCount,
				queryBuffer.toString());
		args[0] = pageCount;
		args[1] = rowCount;
		String resultXml = "";
		if (cnt > 0) {
			Dmmc.convDmmc(dw, "JZBGZT_DM", "V_DM_XZQH_JZBGZT");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}

	/**
	 * <p>
	 * �������ƣ�importTables()
	 * </p>
	 * <p>
	 * ��������������Ȩ�޻�����������Ϣ�ķ���������������ӱ�QX_JG���м�����������¼��
	 * </p>
	 * 
	 * @param uwa
	 *            ����������
	 * @return �������ı���
	 * @throws Exception
	 * @author wangjz
	 * @since Jan 21, 2008
	 */
	public void userOperate(String zipxl, String bgzl_dm) throws Exception {
		D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
		zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		StringBuffer sql = new StringBuffer();
		sql.append("ZIPXH = '").append(zipxl).append("'");
		zipDw.setFilter(sql.toString());
		long count = zipDw.retrieve();
		try {
			String jzbgzt_dm = "";
			if (count > 0) {
				jzbgzt_dm = StringEx.sNull(zipDw.getItemAny(0L, "JZBGZT_DM"));
				// ������ʱ��
				if (bgzl_dm.equals("1")) {
					jzbgzt_dm = "22";
				}
				// �߼�У��
				if (bgzl_dm.equals("2")) {
					jzbgzt_dm = "32";
					// this.checkLogic(zipxl);
				}
				zipDw.setItemString(0L, "JZBGZT_DM", jzbgzt_dm);
				zipDw.setItemString(0L, "BGZL_DM", bgzl_dm);
				zipDw.setTransObject(new UserTransaction());
				zipDw.update(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}



}
