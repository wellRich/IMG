/**
 * 
 */
package com.padis.business.xzqhwh.zxbg.sqdcx;

import com.padis.business.xzqhwh.common.Common;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.qxservice.IQxService;
import com.padis.common.qxservice.QxInterfaceFactory;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p>Title: SqdcxManager.java </p>
 * <p>Description:���뵥��ѯ��Manager�� </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-10-13
 * @author pengld
 * @version 1.0
 * �޸���ʷ�� 
 * �޸���
 * �޸�ʱ��(yyyy/mm/dd)
 * �޸�����
 * �汾��
 */
public class SqdcxManager {
	
	/**
	 * <p>�������ƣ�querySqd</p>
	 * <p>����������������Ӧ������ѯ���뵥</p>
	 * @param xdo
	 * @param args
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-14
	 */
	public String querySqd(XMLDataObject xdo,long[] args,String xzqh_dm) throws Exception
	{
		String sqdmc = StringEx.sNull(xdo.getItemValue("SQDMC"));
		String sqdzt_dm = StringEx.sNull(xdo.getItemValue("SQDZT_DM"));
		String bgsjq = StringEx.sNull(xdo.getItemValue("BGSJQ"));
		String bgsjz = StringEx.sNull(xdo.getItemValue("BGSJZ"));
		String tzsm = StringEx.sNull(xdo.getItemValue("TZSM"));
		String bgxzqh_dm = StringEx.sNull(xdo.getItemValue("BGXZQH_DM"));
		String jb_dm ="";
		if(!bgxzqh_dm.equals(""))
		{
			jb_dm = Common.getJbdm(bgxzqh_dm);
			if(jb_dm.length()>6){
				jb_dm = jb_dm.substring(0, 6);
			}
		}
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //ҳ������
		pageSize = pageSize.equals("") ? "8" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("SELECT distinct SQD.SQDXH,SQDMC,SQDZT_DM AS SQDZTDM,SQDZT_DM,BZ,SPYJ,SBXZQH_DM,SBXZQH_DM||'000000000' AS SBXZQH_MC,SQD.LRSJ FROM XZQH_BGSQD SQD,XZQH_BGGROUP GRO WHERE ");
		queryBuffer.append(" SQD.SQDXH = GRO.SQDXH(+)");	
		if(!sqdmc.equals(""))
		{
			queryBuffer.append(" AND SQD.SQDMC LIKE '%"+sqdmc+"%' ");
		}
		if(!sqdzt_dm.equals(""))
		{
			queryBuffer.append(" AND SQD.SQDZT_DM = '"+sqdzt_dm+"'");
		}
		if(!tzsm.equals(""))
		{
			queryBuffer.append(" AND GRO.GROUPMC LIKE '%"+tzsm+"%'");
		}
		if(!bgsjq.equals(""))
		{
			queryBuffer.append(" AND SQD.LRSJ >= TO_DATE('"+bgsjq+"','YYYY-MM-DD') ");
		}
		if(!bgsjz.equals(""))
		{
			queryBuffer.append(" AND SQD.LRSJ < TO_DATE('"+bgsjz+"','YYYY-MM-DD') ");
		}
		
		String jbDm = Common.getJbdm(xzqh_dm);
		if(!xzqh_dm.equals("000000000000000")){
//			IQxService qxService= QxInterfaceFactory.getInstance().getInterfaceImp();
//			String qxjgCondition = StringEx.sNull(qxService.getQXCondition("SQD.LRJG_DM", qxjg_dm));
//			if(!qxjgCondition.equals("")){
				if(queryBuffer.indexOf("WHERE")<0){
					queryBuffer.append(" WHERE ");
				}else{
					queryBuffer.append(" AND SQD.LRJG_DM like '").append(jbDm).append("%'");
				}
//				queryBuffer.append(qxjgCondition);
//			}
		}
		queryBuffer.append(" ORDER BY SQD.LRSJ DESC");

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
			Dmmc.convDmmc(dw,"SBXZQH_MC","V_DM_XZQH");
			Dmmc.convDmmc(dw,"SQDZT_DM", "V_DM_XZQH_SQDZT");		
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>�������ƣ�queryMxb</p>
	 * <p>������������ѯ���뵥����ϸ</p>
	 * @param xdo
	 * @param args
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-10-14
	 */
	
	public String queryMxb(XMLDataObject xdo,long args []) throws Exception
	{
		String sqdxh = StringEx.sNull(xdo.getItemValue("SQDXH"));
		String pageSize = StringEx.sNull(xdo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xdo.getItemValue("PAGEINDEX")); //ҳ������
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		String resultXml = "";
		StringBuffer sql = new StringBuffer(
				"SELECT A.GROUPXH,A.GROUPMC,B.YSXZQH_DM,B.YSXZQH_MC,B.BGLX_DM,B.MBXZQH_DM,B.MBXZQH_MC,B.CLZT_DM,B.CLJG,B.BZ,B.XGSJ FROM XZQH_BGGROUP A,XZQH_BGMXB B WHERE A.GROUPXH=B.GROUPXH AND A.SQDXH = '");
		sql.append(sqdxh);
		sql.append("' ORDER BY A.LRSJ ,B.PXH");	
		DataWindow dw = DataWindow.dynamicCreate(sql.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rowCount = dw.getRowCount(sql.toString());
		long ps = Long.parseLong(pageSize);   //һҳ�а������������ݣ����з�ҳ׼��
		long pageCount = dw.getPageCount(ps);  //һ������ҳ
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex),pageCount, sql.toString());
		args[0] = pageCount ; 
		args[1] = rowCount ; 
		if(cnt>0){
			Dmmc.convDmmc(dw,"BGLX_DM", "V_DM_XZQH_BGLX");
			Dmmc.convDmmc(dw,"CLZT_DM", "V_DM_XZQH_CLZT");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>����������</p>
	 * @param groupxh
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-22
	 */
	public String expSqd(String sqdxh) throws Exception{
		StringBuffer sql= new StringBuffer("SELECT A.GROUPXH,A.GROUPMC,B.YSXZQH_DM,B.YSXZQH_MC,B.BGLX_DM,B.MBXZQH_DM,B.MBXZQH_MC,B.BZ FROM XZQH_BGGROUP A,XZQH_BGMXB B WHERE A.GROUPXH = B.GROUPXH AND A.SQDXH = '");
		sql.append(sqdxh);
		sql.append("' ORDER BY A.LRSJ,B.PXH");
		DataWindow dw = DataWindow.dynamicCreate(sql.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rows = dw.retrieve();
		if(rows >0)
		{
			Dmmc.convDmmc(dw,"BGLX_DM", "V_DM_XZQH_BGLX");
			return dw.toXML().toString();
		}
		return "";
	}


}
