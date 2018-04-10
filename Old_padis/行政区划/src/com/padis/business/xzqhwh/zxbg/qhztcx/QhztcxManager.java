package com.padis.business.xzqhwh.zxbg.qhztcx;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p>
 * Description: ������־�����ࣨCzrzcxService����manager,�ṩCzrzcxService�ײ㴦��ķ���
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @since 2008-01-12
 * @author ���
 * @version 1.0
 * @�޸��ˣ��Ž��
 * @�޸�ʱ�䣺2007-02-25
 * @�޸����ݣ�ɾ���ĵ��ķ��������⣬������Ϊ��ʱ�����򱨴�
 */

public class QhztcxManager {

	/**
	 * <p>�������ƣ�queryXjXzqh()����ѯ�¼����������б�</p>
	 * <p>����˵������ѯ�¼����������б�ķ�����������������DM_XZQH_YLSJ���м�����������¼��</p>
	 * @since 2009-11-13
	 * @author LIJL
	 * String ���������б�
	 * @throws Exception
	 */
	public String queryXjXzqh(XMLDataObject xmldo ,long[] args) throws Exception {
		String xzqh_dm = StringEx.sNull(xmldo.getItemValue("XZQH_DM"));
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //ҳ������
		pageSize = pageSize.equals("") ? "20" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("select XZQH_DM,XZQH_MC,decode(YXBZ,'Y','��Ч','N','����') YXBZ from DM_XZQH_YLSJ where SJ_XZQH_DM ='");
		queryBuffer.append(xzqh_dm);
		queryBuffer.append("' and instr(XZQH_DM,'L')<1 order by XZQH_DM asc");
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
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
}
