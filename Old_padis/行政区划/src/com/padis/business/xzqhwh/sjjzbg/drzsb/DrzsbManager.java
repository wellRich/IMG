package com.padis.business.xzqhwh.sjjzbg.drzsb;

import com.padis.business.common.data.xzqh.D_xzqh_jzbgzip;
import com.padis.business.xzqhwh.common.Common;
import com.padis.common.dmservice.Dmmc;
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

public class DrzsbManager {
	
	/**
	 * <p>�������ƣ�queryXzqhjzbgzip()��ʡ�������������б���ļ���¼</p>
	 * <p>����˵����ʡ�������������б���ļ���¼�ķ�����������������XT_XZQHFB���м�����������¼��</p>
	 * @param xmldo		��ѯ����
	 * @param args		��ҳ��ѯ����
	 * @since 2009-09-10
	 * @author ���
	 * String ���������ĵ����ʼ��б�
	 * @throws Exception
	 */
	public String queryZip(XMLDataObject xmldo ,long[] args, String sjxzqh_dm) throws Exception {
		String xzqh_dm = StringEx.sNull(xmldo.getItemValue("XZQH_DM")); //ÿҳ����������
		String rq = StringEx.sNull(xmldo.getItemValue("RQ")); //ҳ������
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //ҳ������
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer sql = new StringBuffer("");
		StringBuffer queryBuffer = new StringBuffer("SELECT Z.ZIPXH,Z.XZQH_DM,Z.RQ,Z.WJM,Z.JZBGZT_DM,JZBGZT_DM JZBGZTDM," +
				"to_char(Z.LRSJ,'YYYY-MM-DD HH24:mi:ss') LRSJ FROM XZQH_JZBGZIP Z");
		if(!sjxzqh_dm.equals("00")&&!sjxzqh_dm.equals("")){
			queryBuffer.append(" WHERE ").append(" substr(Z.XZQH_DM,0,2)='").append(sjxzqh_dm).append("'");
		}
		if(!xzqh_dm.equals("")){
			sql.append(" AND Z.XZQH_DM='").append(xzqh_dm).append("'");
		}
		if(!rq.equals("")){
			sql.append(" AND Z.RQ='").append(rq).append("'");
		}		
		if(sql.length()>0){
			if(queryBuffer.indexOf("WHERE")<0){
				queryBuffer.append(" WHERE ").append(sql.substring(5));
			}else{
				queryBuffer.append(sql.toString());
			}
		}
		queryBuffer.append(" order by Z.LRSJ desc");
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
	 * <p>�������ƣ�importTables()</p>
	 * <p>��������������Ȩ�޻�����������Ϣ�ķ���������������ӱ�QX_JG���м�����������¼��</p>
	 * @param uwa ����������
	 * @return �������ı���
	 * @throws Exception
	 * @author wangjz
	 * @since Jan 21, 2008
	 */
	public void userOperate(String zipxl, String bgzl_dm) throws Exception{
		D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
		zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		StringBuffer sql = new StringBuffer();
		sql.append("ZIPXH = '").append(zipxl).append("'");
		zipDw.setFilter(sql.toString());
		long count = zipDw.retrieve();
		
		if(count>0){

			zipDw.setItemString(0L, "JZBGZT_DM",Common.XZQH_JZBGZT_SQDSQCLZ);//���ô�����״̬
			zipDw.setItemString(0L, "BGZL_DM","3");
			zipDw.setTransObject(new UserTransaction());
			zipDw.update(true);
		}			
	}
	
	/**
	 * <p>�������ƣ�updateXzqhpadisDm()</p>
	 * <p>��������������XT_XZQHPADIS_TEMP��</p>
	 * @param zipxl ѹ���ļ����
	 * @param ut ����
	 * @return ��
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-11
	 */
	public void updateXzqhpadisDm(String ysxzqh_dm, String mbxzqh_dm, String zipxl, UserTransaction ut) throws Exception{
		String ysjc_dm = Common.getJcdm(ysxzqh_dm);
		String mbjc_dm = Common.getJcdm(mbxzqh_dm);
		StringBuffer sqlSb = new StringBuffer("update XT_XZQHPADIS_TEMP t set t.XZQH_DM=replace(XZQH_DM,'");
		sqlSb.append(ysjc_dm);
		sqlSb.append("','");
		sqlSb.append(mbjc_dm);
		sqlSb.append("') where t.XZQH_DM like '");
		sqlSb.append(ysjc_dm);
		sqlSb.append("%' and t.ZIPXH='");
		sqlSb.append(zipxl);
		sqlSb.append("'");
		DataWindow updatedw = DataWindow.dynamicCreate(sqlSb.toString());
		updatedw.setTransObject(ut);
		updatedw.update(false);
	}
	

	/**
	 * 
	 * <p>
	 * �������ƣ�getMaxSqbh
	 * </p>
	 * <p>
	 * �����������õ����ݿ����������뵥���
	 * </p>
	 * 
	 * @param dw
	 *            �����������ݶ���
	 * @param destXzqhmc
	 *            ��������������
	 * @param ut
	 *            �û�����
	 * @author lijl
	 * @since 2009-7-13
	 */
	public String getMaxSqbh() throws Exception{
		DataWindow dw = DataWindow.dynamicCreate("select MAX(BH) as BH from XZQH_BGGROUP");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long cnt = dw.retrieve();
		String maxbh = "";
		if(cnt>0){
			String bh = StringEx.sNull(dw.getItemAny(0, "BH"));
			if(!bh.equals("")){
				maxbh = bh.substring((bh.length()-3), bh.length());
				if(!maxbh.equals("")){
					maxbh = String.valueOf(Integer.parseInt(maxbh)+1);
				}
				if(maxbh.length()==1){
					maxbh = "00"+maxbh;
				}
				if(maxbh.length()==2){
					maxbh = "0"+maxbh;
				}
			}
		}
		return maxbh;
	}
}
