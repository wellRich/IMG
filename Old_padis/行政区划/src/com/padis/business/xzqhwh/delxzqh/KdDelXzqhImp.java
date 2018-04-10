package com.padis.business.xzqhwh.delxzqh;

import java.util.List;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

public class KdDelXzqhImp extends DelxzqhImp{
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>����������</p>
	 * @param xzqhList
	 * @return
	 * @author zhanglw
	 * @throws Exception 
	 * @since 2009-8-4
	 */
	public boolean isAllowDelXzqh(List xzqhList) {
		boolean temp = true;
		try{
			for(int i=0;i<xzqhList.size();i++){
				String xzqhDm = StringEx.sNull(xzqhList.get(i));
				//��ѯ���ٵ��鳣���ⱨ����Ϣ
				if(!this.queryRkjhsytj(xzqhDm)){
					temp = false;
				}
				if(!this.queryNdjyqk(xzqhDm)){
					temp = false;
				}
				if(!this.queryCgjcJgxx(xzqhDm)){
					temp = false;
				}
				//��ѯ���ٵ��������Ϣ
				if(!this.queryKdjcdxx(xzqhDm)){
					temp = false;
				}
				//��ѯ���ٵ�����������������Ϣ
				if(!this.queryKdfzxx(xzqhDm)){
					temp = false;
				}
			}
		}catch(Exception e){
			temp = false;
			LogManager.getLogger().log(e);
		}
		return temp;
	}
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>����������</p>
	 * @param xzqhDm
	 * @return
	 * @author zhanglw
	 * @throws Exception 
	 * @since 2009-8-4
	 */
	private boolean queryRkjhsytj(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select ND, count(1) COUNTS");
		sqlStr.append(" from KD_CGJC_RKJHSYTJ");
		sqlStr.append(" where XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("' group by ND");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rows = dw.retrieve();
		if(rows != 0L){
			int count = 0;
			XMLDataObject xdo = dw.toXDO();
			StringBuffer bz = new StringBuffer();
			bz.append("������-�˿���������ͳ�Ʊ�����");
			for(int i = 0;i<rows;i++){
				String nd = StringEx.sNull(xdo.getItemAny(i, "ND"));
				String counts = StringEx.sNull(xdo.getItemAny(i, "COUNTS"));
				bz.append(nd);
				bz.append("��ȵ�����");
				bz.append(counts);
				bz.append("��");
				if(i != rows-1){
					bz.append(",");
				}
				count = count + Integer.parseInt(counts);
			}
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("KD_CGJC_RKJHSYTJ");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("K");
			delXzqhBean.setSjl(count);
			delXzqhBean.setBz(bz.toString());
			this.addReturnMessage(delXzqhBean);
			temp = false;
		}
		return temp;
	}
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>����������</p>
	 * @param xzqhDm
	 * @return
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-8-4
	 */
	private boolean queryNdjyqk(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select ND, count(1) COUNTS");
		sqlStr.append(" from KD_CGJC_NDJYQK");
		sqlStr.append(" where XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("' group by ND");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rows = dw.retrieve();
		if(rows != 0L){
			int count = 0;
			XMLDataObject xdo = dw.toXDO();
			StringBuffer bz = new StringBuffer();
			bz.append("������-�������ͳ�Ʊ�����");
			for(int i = 0;i<rows;i++){
				String nd = StringEx.sNull(xdo.getItemAny(i, "ND"));
				String counts = StringEx.sNull(xdo.getItemAny(i, "COUNTS"));
				bz.append(nd);
				bz.append("��ȵ�����");
				bz.append(counts);
				bz.append("��");
				if(i != rows-1){
					bz.append(",");
				}
				count = count + Integer.parseInt(counts);
			}
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("KD_CGJC_NDJYQK");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("K");
			delXzqhBean.setSjl(count);
			delXzqhBean.setBz(bz.toString());
			this.addReturnMessage(delXzqhBean);
			temp = false;
		}
		return temp;
	}
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>����������</p>
	 * @param xzqhDm
	 * @return
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-8-4
	 */
	private boolean queryCgjcJgxx(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select T.ND, count(1) COUNTS");
		sqlStr.append(" from KD_CGJC_TBXX T, RS_JGXX J");
		sqlStr.append(" where T.QX_JGDM = J.JG_DM");
		sqlStr.append(" and J.XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("' and J.YXBZ = 'Y'");
		sqlStr.append(" group by T.ND");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rows = dw.retrieve();
		if(rows != 0L){
			int count = 0;
			XMLDataObject xdo = dw.toXDO();
			StringBuffer bz = new StringBuffer();
			bz.append("������-�õ�����");
			for(int i = 0;i<rows;i++){
				String nd = StringEx.sNull(xdo.getItemAny(i, "ND"));
				String counts = StringEx.sNull(xdo.getItemAny(i, "COUNTS"));
				bz.append(nd);
				bz.append("��������");
				bz.append(counts);
				bz.append("��");
				if(i != rows-1){
					bz.append(",");
				}
				count = count + Integer.parseInt(counts);
			}
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("KD_CGJC_TBXX,RS_JGXX");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("K");
			delXzqhBean.setSjl(count);
			delXzqhBean.setBz(bz.toString());
			this.addReturnMessage(delXzqhBean);
			temp = false;
		}
		return temp;
	}
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>����������</p>
	 * @param xzqhDm
	 * @return
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-8-4
	 */
	private boolean queryKdjcdxx(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select count(1) COUNTS");
		sqlStr.append(" from KD_JCDXX");
		sqlStr.append(" where XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("'");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		dw.retrieve();
		String counts = StringEx.sNull(dw.getItemAny(0L, "COUNTS"));
		if(Integer.parseInt(counts) != 0L){
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("KD_JCDXX");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("K");
			delXzqhBean.setSjl(Integer.parseInt(counts));
			delXzqhBean.setBz("�����������ڼ���-����ر��д�������"+Integer.parseInt(counts)+"��");
			this.addReturnMessage(delXzqhBean);
			temp = false;
		}
		return temp;
	}
	
	/**
	 * <p>�������ƣ�</p>
	 * <p>����������</p>
	 * @param xzqhDm
	 * @return
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-8-4
	 */
	private boolean queryKdfzxx(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select count(1) COUNTS");
		sqlStr.append(" from KD_XZQHFZ_FZCY");
		sqlStr.append(" where XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("'");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		dw.retrieve();
		String counts = StringEx.sNull(dw.getItemAny(0L, "COUNTS"));
		if(Integer.parseInt(counts) != 0L){
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("KD_XZQHFZ_FZCY");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("K");
			delXzqhBean.setSjl(Integer.parseInt(counts));
			delXzqhBean.setBz("�������������������������Ա��Ϣ���д���"+Integer.parseInt(counts)+"������");
			this.addReturnMessage(delXzqhBean);
			temp = false;
		}
		return temp;
	}
}
