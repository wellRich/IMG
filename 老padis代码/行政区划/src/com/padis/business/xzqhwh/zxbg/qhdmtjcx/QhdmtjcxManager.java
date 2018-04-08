package com.padis.business.xzqhwh.zxbg.qhdmtjcx;

import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;


/**
 * <p>Title: QhdmtjcxManager.java </p>
 * <p>Description:<��Ĺ�������> </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2010-07-22
 * @author lijl
 * @version 1.0
 * �޸���ʷ�� 
 * �޸���
 * �޸�ʱ��(yyyy/mm/dd)
 * �޸�����
 * �汾��
 */

public class QhdmtjcxManager {	
	/**
	 * <p>�������ƣ�queryXzqh</p>
	 * <p>��������������������ѯ����������Ϣ</p>
	 * @param xdo
	 * @param args
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2010-07-22
	 */
	public String queryXzqh(XMLDataObject xdo,long[] args) throws Exception {
		String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM"));
		String xzqh_mc = StringEx.sNull(xdo.getItemValue("XZQH_MC"));
		String jcdm = StringEx.sNull(xdo.getItemValue("JCDM"));
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //ҳ������
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		
		StringBuffer sql = new StringBuffer("SELECT XZQH_DM,XZQH_MC,XZQH_QC,DECODE(JCDM,'0','���Ҽ�','1','ʡ��','2','�м�','3','�ؼ�','4','�缶','5','�弶','�鼶') JCMC,SJ_XZQH_DM SJ_XZQH_MC,SJ_XZQH_DM FROM V_DM_XZQH WHERE ");
		if(!xzqh_dm.equals("")){
			sql.append(" XZQH_DM like '").append(xzqh_dm.trim()).append("%'");
		}
		if(!xzqh_mc.equals("")){
			sql.append(" XZQH_MC like '%").append(xzqh_mc.trim()).append("%'");
		}
		if(!jcdm.equals("")){
			sql.append(" AND JCDM ='").append(jcdm).append("'");
		}
		sql.append(" ORDER BY XZQH_DM");
		DataWindow dw = DataWindow.dynamicCreate(sql.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rowCount = dw.getRowCount(sql.toString());
		long ps = Long.parseLong(pageSize);   //һҳ�а������������ݣ����з�ҳ׼��
		long pageCount = dw.getPageCount(ps);  //һ������ҳ
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex),pageCount, sql.toString());
		args[0] = pageCount ; 
		args[1] = rowCount ; 

		if (cnt > 0){
			Dmmc.convDmmc(dw,"SJ_XZQH_MC","V_DM_XZQH");
			return dw.toXML().toString();
		}else {
			return "";
		}
	}
}
