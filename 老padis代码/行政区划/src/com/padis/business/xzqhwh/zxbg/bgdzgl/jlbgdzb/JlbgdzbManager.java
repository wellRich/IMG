package com.padis.business.xzqhwh.zxbg.bgdzgl.jlbgdzb;

import com.padis.business.common.data.xzqh.D_xzqh_bgsqd;
import com.padis.business.xzqhwh.common.Common;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.business.common.sequence.IGeneralSequence;
import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;
import ctais.services.sequence.SequenceManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;


/**
 * ����������뵥
 * <p>Title: SqdwhManager.java </p>
 * <p>Description:<��Ĺ�������> </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-9-22
 * @author pengld
 * @version 1.0
 * �޸���ʷ�� 
 * �޸���
 * �޸�ʱ��(yyyy/mm/dd)
 * �޸�����
 * �汾��
 */

public class JlbgdzbManager {

	/**
	 * <p>��ѯָ�������ı�����뵥��</p>
	 * <p>����������</p>
	 * @param qxjg_dm
	 * @param xmldo
	 * @param args
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-22
	 */
	public String querySqb(String xzqh_dm, XMLDataObject xmldo ,long[] args) throws Exception {	
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //ҳ������
		pageSize = pageSize.equals("") ? "8" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("SELECT SQDXH,SQDMC,SQDZT_DM AS SQDZTDM,SQDZT_DM,BZ,SPYJ,SBXZQH_DM||'000000000' AS SBXZQH_MC,SBXZQH_DM,LRSJ FROM XZQH_BGSQD WHERE SBXZQH_DM = '");
		queryBuffer.append(xzqh_dm.substring(0,6));
		queryBuffer.append("' ORDER BY LRSJ DESC");
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
			Dmmc.convDmmc(dw,"SQDZT_DM", "V_DM_XZQH_SQDZT");
			Dmmc.convDmmc(dw, "SBXZQH_MC","V_DM_XZQH");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	 /**
	  * <p>�������ƣ�</p>
	  * <p>����������</p>
	  * @param xdo
	  * @param czry_dm
	  * @param jg_dm
	  * @throws Exception
	  * @author pengld
	  * @since 2009-9-22
	  */
	public void addSqd(XMLDataObject xdo,String czry_dm,String jg_dm) throws Exception {
		IJgService jgService = JgInterfaceFactory.getInstance().getInterfaceImp();
		String xzqh_dm = jgService.getXzqhDmByJg(jg_dm);
		D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
		d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		//ʹ���ϱ���������������״̬���ұ�����뵥��������ڷ������������뵥���򲻴���
		d_xzqh_bgsqd.setFilter("SQDZT_DM < "+Common.XZQH_SQDZT_GJYSH+" AND SBXZQH_DM = '"+xzqh_dm.substring(0,6)+"'");
		long rows = d_xzqh_bgsqd.retrieve();
		if(rows <= 0L)
		{
			d_xzqh_bgsqd.insert(-1);
			IGeneralSequence iseq = (IGeneralSequence) SequenceManager.getSequenceFactory();
			d_xzqh_bgsqd.setItemString(0, "SQDXH", iseq.get("SEQ_XZQH_BGSQD_XL"));//���
			d_xzqh_bgsqd.setItemString(0, "SQDMC",StringEx.sNull(xdo.getItemValue("SQDMC")).trim() );//����
			d_xzqh_bgsqd.setItemString(0, "BZ",StringEx.sNull(xdo.getItemValue("BZ")).trim() );//��ע
			d_xzqh_bgsqd.setItemString(0, "SQDZT_DM", Common.XZQH_SQDZT_WTJ);//״̬
			d_xzqh_bgsqd.setItemString(0, "LRR_DM",czry_dm );//¼���˴���
			d_xzqh_bgsqd.setItemString(0, "LRSJ", XtDate.getDBTime());//¼��ʱ��
			d_xzqh_bgsqd.setItemString(0, "LRJG_DM", jg_dm);//¼���������
			d_xzqh_bgsqd.setItemString(0, "SBXZQH_DM",xzqh_dm.substring(0,6) );//�ϱ���������������������
			d_xzqh_bgsqd.setTransObject(new UserTransaction());
			d_xzqh_bgsqd.update(true);
		}
	}
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>��ѯδ������ǰ����������</p>
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-25
	 */
	
	public String querySqdzt(String xzqh_dm) throws Exception
	{
		D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
		d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		d_xzqh_bgsqd.setFilter("SBXZQH_DM ='"+xzqh_dm.substring(0,6)+"' AND SQDZT_DM <"+Common.XZQH_SQDZT_YFB);
		long rows = d_xzqh_bgsqd.retrieve();
		if(rows >0L)
		{
			return String.valueOf(rows);
		}else
		{
			return "0";
		}
	}
}
