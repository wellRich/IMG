package com.padis.business.xzqhwh.sjjzbg.bgdzcx;


import com.padis.business.xzqhwh.common.Common;

import com.padis.common.dmservice.Dmmc;


import com.padis.common.xtservice.connection.ConnConfig;


import ctais.services.data.DataWindow;

import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p> 
 * Description: �����־�����ࣨBgrzglService����manager,�ṩBgrzglService�ײ㴦��ķ���
 * </p> 
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @since 2009-07-10
 * @author ���
 * @version 1.0
 */

public class BgdzcxManager {
	
	/**
	 * <p>�������ƣ�queryXzqhjzbgzip()��ʡ�������������б���ļ���¼</p>
	 * <p>����˵����ʡ�������������б���ļ���¼�ķ�����������������XT_XZQHFB���м�����������¼��</p>
	 * @param xmldo		��ѯ����
	 * @param args		��ҳ��ѯ����
	 * @since 2009-08-14
	 * @author ���
	 * String ���������ĵ����ʼ��б�
	 * @throws Exception
	 */
	public String queryZip(XMLDataObject xdo ,long[] args,String sjxzqh_dm) throws Exception {
		String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM")); 
		String rq = StringEx.sNull(xdo.getItemValue("RQ")); //����
		rq = rq.replaceAll("-", "");
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //ҳ������
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("SELECT ZIPXH,XZQH_DM,RQ,JZBGZT_DM,WJM,LRSJ,BZ FROM XZQH_JZBGZIP");
		StringBuffer sql= new StringBuffer("");	
		if(!sjxzqh_dm.equals("00")&&!sjxzqh_dm.equals("")){
			queryBuffer.append(" WHERE ").append(" substr(XZQH_DM,0,2)='").append(sjxzqh_dm).append("'");
		}
		if(!xzqh_dm.equals("")){
			sql.append(" AND XZQH_DM='").append(xzqh_dm).append("'");
		}
		if(!rq.equals("")){
			sql.append(" AND RQ='").append(rq.replaceAll("-","")).append("'");
		}
		if(sql.length()>0){
			if(queryBuffer.indexOf("WHERE")<0){
				queryBuffer.append(" WHERE ").append(sql.substring(5));
			}else{
				queryBuffer.append(sql.toString());
			}
		}
		queryBuffer.append(" ORDER BY RQ DESC ");
		DataWindow dw = DataWindow.dynamicCreate(queryBuffer.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rowCount = dw.getRowCount(queryBuffer.toString());
		long ps = Long.parseLong(pageSize);   //һҳ�а������������ݣ����з�ҳ׼��
		long pageCount = dw.getPageCount(ps);  //һ������ҳ
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex),pageCount, queryBuffer.toString());
		args[0] = pageCount ; 
		args[1] = rowCount ; 
		String resultXml = "";
		if (cnt > 0){
			Dmmc.convDmmc(dw,"JZBGZT_DM", "V_DM_XZQH_JZBGZT");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>�������ƣ�queryXzqhjzbgzip()��ʡ�������������б���ļ���¼</p>
	 * <p>����˵����ʡ�������������б���ļ���¼�ķ�����������������XT_XZQHFB���м�����������¼��</p>
	 * @param xdo		��ѯ����
	 * @param args		��ҳ��ѯ����
	 * @since 2009-08-14
	 * @author ���
	 * String ���������ĵ����ʼ��б�
	 * @throws Exception
	 */
	public String queryDzb(XMLDataObject xdo ,long[] args,String jg_dm) throws Exception {
		String zipxh = StringEx.sNull(xdo.getItemValue("ZIPXH")); 
		String bgxzqh_dm =StringEx.sNull(xdo.getItemValue("BGXZQH_DM"));
		String cwsjbz = StringEx.sNull(xdo.getItemValue("CWSJBZ"));
		String bglx_dm = StringEx.sNull(xdo.getItemValue("BGLX_DM"));
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //ҳ������
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer sb = new StringBuffer("SELECT * FROM (");
		StringBuffer queryBuffer = new StringBuffer("SELECT YSXZQH_DM,YSXZQH_MC,BGLX_DM,MBXZQH_DM,MBXZQH_MC,DECODE(CWSJBZ,'N','��ȷ','Y','����') AS CWSJBZ,BZ,CWXX,PXH FROM XZQH_JZBGDZB_TEMP WHERE");
		queryBuffer.append(" ZIPXH='").append(zipxh).append("'");
		if(!cwsjbz.equals(""))
		{
			queryBuffer.append(" AND CWSJBZ ='"+cwsjbz+"' ");
		}
		if(bglx_dm.equals(Common.ADD))
		{
			queryBuffer.append(" AND BGLX_DM ='"+Common.ADD+"' ");
			if(!bgxzqh_dm.equals(""))
			{
				queryBuffer.append(" AND MBXZQH_DM LIKE '"+Common.getJbdm(bgxzqh_dm)+"%'");
			}
			
		}
		else
		{
			if(!bglx_dm.equals(""))
			{
				queryBuffer.append(" AND BGLX_DM ='"+bglx_dm+"' ");
			}
			
			if(!bgxzqh_dm.equals(""))
			{
				queryBuffer.append(" AND YSXZQH_DM LIKE '"+Common.getJbdm(bgxzqh_dm)+"%'");
			}
			
		}
		
		StringBuffer queryBuffer1 = new StringBuffer("SELECT YSXZQH_DM,YSXZQH_MC,BGLX_DM,MBXZQH_DM,MBXZQH_MC,DECODE(CWSJBZ,'N','��ȷ','Y','����') AS CWSJBZ,BZ,CWXX,PXH FROM XZQH_JZBGKLJDZB_TEMP WHERE");
		queryBuffer1.append(" ZIPXH='").append(zipxh).append("'");
		if(!cwsjbz.equals(""))
		{
			queryBuffer1.append(" AND CWSJBZ ='"+cwsjbz+"' ");
		}
		if(bglx_dm.equals(Common.ADD))
		{
			queryBuffer1.append(" AND BGLX_DM ='"+Common.ADD+"' ");
			if(!bgxzqh_dm.equals(""))
			{
				queryBuffer1.append(" AND MBXZQH_DM LIKE '"+Common.getJbdm(bgxzqh_dm)+"%'");
			}
			
		}
		else
		{
			if(!bglx_dm.equals(""))
			{
				queryBuffer1.append(" AND BGLX_DM ='"+bglx_dm+"' ");
			}
			
			if(!bgxzqh_dm.equals(""))
			{
				queryBuffer1.append(" AND YSXZQH_DM LIKE '"+Common.getJbdm(bgxzqh_dm)+"%'");
			}
			
		}

		sb.append(queryBuffer.toString());
		sb.append(" union all ");
		sb.append(queryBuffer1.toString()).append(") A");
		sb.append(" ORDER BY A.PXH ");

		DataWindow dw = DataWindow.dynamicCreate(sb.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rowCount = dw.getRowCount(sb.toString());
		long ps = Long.parseLong(pageSize);   //һҳ�а������������ݣ����з�ҳ׼��
		long pageCount = dw.getPageCount(ps);  //һ������ҳ
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex),pageCount, sb.toString());
		args[0] = pageCount ; 
		args[1] = rowCount ; 
		String resultXml = "";
		if (cnt > 0){
			Dmmc.convDmmc(dw,"BGLX_DM", "V_DM_XZQH_BGLX");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
}
