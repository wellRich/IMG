package com.padis.business.xzqhwh.zxbg.bgdzgl.bgdzblr;

import java.util.ArrayList;
import java.util.List;

import com.padis.business.common.data.xzqh.D_xzqh_bggroup;
import com.padis.business.common.data.xzqh.D_xzqh_bgmxb;
import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.XzqhbgBean;
import com.padis.business.xzqhwh.common.XzqhbgCommon;
import com.padis.business.xzqhwh.common.linkxzqh.XzqhNode;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.business.common.sequence.IGeneralSequence;
import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.or.UserTransaction;
import ctais.services.sequence.SequenceManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p>
 * Description: ����������������ࣨXzqhbgsqbService����manager,�ṩXzqhbgsqbService�ײ㴦��ķ���
 * </p> 
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @since 2009-06-11
 * @author ���
 * @version 1.0
 */
public class BgdzblrManager {
	
	/**
	 * 
	 * <p>�������ƣ�addXzqhbgsqb()</p>
	 * <p>�������������������������������¼</p>
	 * @param xmldo
	 *            ��������xml���ݶ���
	 * @param czry_dm
	 *            ������Ա����
	 * @param qx_dm
	 *            Ȩ�޻�������
	 * @param xzqh_dm
	 * 			  ����������������   
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-16
	 */
	public void addXzqhbgsqb(XMLDataObject xmldo, String czry_dm, String qxjg_dm, String xzqh_dm) throws Exception {
		String sqbmc = StringEx.sNull(xmldo.getItemValue("SQBMC"));
		String sqdxh = StringEx.sNull(xmldo.getItemValue("SQDXH"));
		System.out.println("-------------------------------------------------");
		String ringFlag = StringEx.sNull(xmldo.getItemValue("RINGFLAG"));
		DataWindow dw = DataWindow.dynamicCreate("select SQDXH,SQDZT_DM from XZQH_BGSQD where SQDXH='"+sqdxh+"'");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long cnt = dw.retrieve();
		if(cnt>0){
			String sqdzt_dm = StringEx.sNull(dw.getItemAny(0L, "SQDZT_DM"));
			if(!sqdzt_dm.equals(Common.XZQH_SQDZT_WTJ)&&!sqdzt_dm.equals(Common.XZQH_SQDZT_SHBTG)){
				throw new Exception("ֻ�ܱ���״̬Ϊ��δ�ύ�����ߡ���˲�ͨ���������뵥��");
			}
		}else{
			throw new Exception("����������뵥��");
		}
		
		xmldo.rootScrollTo("XZQH");
		D_xzqh_bggroup groupDw = new D_xzqh_bggroup();
		groupDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		groupDw.insert(-1);
		XzqhbgCommon bgCommon= new XzqhbgCommon();
		IGeneralSequence iseq = (IGeneralSequence) SequenceManager.getSequenceFactory();
		String groupxh = iseq.get("SEQ_XZQH_BGGROUP_XL");
		UserTransaction ut = new UserTransaction();
		String bh ="";
		String maxbh = this.getMaxSqbh();
		String date = XtDate.getDBdate();
		String sysTime = XtDate.getDBTime();
		List mxList = new ArrayList();
		List noRingList = new ArrayList();
		List mcbgList = new ArrayList();
		if(maxbh.equals("")){
			bh = xzqh_dm.substring(0, 6)+date.replaceAll("-", "")+"001";
		}else{
			bh = xzqh_dm.substring(0, 6)+date.replaceAll("-", "")+maxbh;
		}
		try {			
			ut.begin();				
			groupDw.setItemString(0, "GROUPXH", groupxh);
			groupDw.setItemString(0, "GROUPMC", sqbmc);
			groupDw.setItemString(0, "SQDXH", sqdxh);
			groupDw.setItemString(0, "BH", bh);
			groupDw.setItemString(0, "LRR_DM", czry_dm);
			groupDw.setItemString(0, "LRSJ", sysTime);			
			groupDw.setItemString(0, "LRJG_DM", qxjg_dm);
			groupDw.setItemString(0, "PXH", new Common().getMax("XZQH_BGGROUP",ut));
			groupDw.setTransObject(ut);
			groupDw.update(false);
			long count = xmldo.getRowCount();
			for(int i=0; i<count;i++){
				String mxbxh = iseq.get("SEQ_XZQH_BGMXB_XL");
				String ysxzqh_dm = StringEx.sNull(xmldo.getItemAny(i,"YSXZQH_DM"));
				String ysxzqh_mc = StringEx.sNull(xmldo.getItemAny(i,"YSXZQH_MC"));
				String mbxzqh_dm = StringEx.sNull(xmldo.getItemAny(i,"MBXZQH_DM"));
				String mbxzqh_mc = StringEx.sNull(xmldo.getItemAny(i,"MBXZQH_MC"));
				String bglx_dm = StringEx.sNull(xmldo.getItemAny(i,"BGLX_DM"));
				String bz = StringEx.sNull(xmldo.getItemAny(i,"BZ"));
				XzqhbgBean xzqhBean = new XzqhbgBean();
				xzqhBean.setDestXzqhdm(mbxzqh_dm);
				xzqhBean.setDestXzqhmc(mbxzqh_mc);			
				xzqhBean.setSrcXzqhmc(ysxzqh_mc);
				xzqhBean.setSrcXzqhdm(ysxzqh_dm);			
				xzqhBean.setBglxdm(bglx_dm);
				xzqhBean.setLrr_dm(czry_dm);
				xzqhBean.setQx_jgdm(qxjg_dm);
				xzqhBean.setSqbxh(groupxh);
				xzqhBean.setSqbmxXh(mxbxh);
				xzqhBean.setBz(bz);
				noRingList.add(xzqhBean);

				//�ж��Ƿ�������Ʊ��
				if(Common.checkMcbg(ysxzqh_dm, mbxzqh_dm)){
					mcbgList.add(xzqhBean);//���Ʊ��
				}else{//��������������
					XzqhNode node = new XzqhNode(xzqhBean);
					mxList.add(node);
				}
			}
			/*
			* ������״����
			* */
			List ringList = new ArrayList();
			boolean flag = false;
			if(ringFlag.equals("1")){
				flag = true;
				ringList = bgCommon.splitRingData(mxList);
				ringList.addAll(mcbgList);
			}else{
				ringList = noRingList;
			}

			if(ringList==null||ringList.size()<=0){
				throw new Exception("������״���ݳ���");
			}
			for(int j=0; j<ringList.size();j++){
				
				XzqhbgBean bean = (XzqhbgBean) ringList.get(j);
				String bglx_dm = StringEx.sNull(bean.getBglxdm());
				String mbxzqh_dm = StringEx.sNull(bean.getDestXzqhdm());
				String mbxzqh_mc = StringEx.sNull(bean.getDestXzqhmc());
				String ysxzqh_dm = StringEx.sNull(bean.getSrcXzqhdm());
				String ysxzqh_mc = StringEx.sNull(bean.getSrcXzqhmc());
				//���浽��ϸ��
				addXzqhMxb(bean,czry_dm,qxjg_dm,j,sysTime,ringFlag,ut);
				//�߼�У��
				bgCommon.logicVerifyXzqh(ysxzqh_dm,ysxzqh_mc,bglx_dm,mbxzqh_dm,mbxzqh_mc,"DM_XZQH_YLSJ",flag,czry_dm);
				//����������Ԥ����
				bgCommon.saveXzqh(bean,sysTime,"dm_xzqh_ylsj",ut);
				//��¼���б����ϸ��ʷ����
				this.insertBgmxlsjl(bean,czry_dm,sysTime,ut);
			}
			ut.commit();		
		}catch (Exception e) {
			ut.rollback();
			throw e;
		}
	}
	
	/** 
	 * <p>�������ƣ�querySqb</p>
	 * <p>������������ѯ������ʹ��������</p>
	 * @param bgml_dm ���Ŀ¼����
	 * @return Ŀ¼�ڵ���Ϣ
	 * @throws Exception
	 * @author lijl
	 * @since 2008-06-24
	 */
	public DataWindow querySqb(String bgml_dm) throws Exception{
		StringBuffer sql = new StringBuffer("SELECT M.YSXZQH_DM,M.YSXZQH_MC,M.MBXZQH_DM,M.MBXZQH_MC FROM XT_XZQHBGMXB M,XT_XZQHBGSQB B WHERE M.SQBXH=B.SQBXH ");
		sql.append("AND B.BGLX_DM ='");
		sql.append(bgml_dm);
		sql.append("'");
		DataWindow mxbDw = DataWindow.dynamicCreate(sql.toString());
		mxbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long cnt = mxbDw.retrieve();
		
		if(cnt>0){
			return mxbDw;
		}else{
			return null;
		}
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
	//�õ�������ı��������
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
	
	/**
	 * 
	 * <p>
	 * �������ƣ�addXzqh
	 * </p>
	 * <p>
	 * ����������������������
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
	private void addXzqhMxb(XzqhbgBean bean, String czry_dm, String qxjg_dm, int j,String sysTime,String ringFlag, UserTransaction ut) throws Exception {
		
		String groupxh = StringEx.sNull(bean.getSqbxh());
		String mxbxh = StringEx.sNull(bean.getSqbmxXh());
		String ysxzqh_dm = StringEx.sNull(bean.getSrcXzqhdm());
		String ysxzqh_mc = StringEx.sNull(bean.getSrcXzqhmc());
		String mbxzqh_dm = StringEx.sNull(bean.getDestXzqhdm());
		String mbxzqh_mc = StringEx.sNull(bean.getDestXzqhmc());
		String bglx_dm = StringEx.sNull(bean.getBglxdm());
		String bz = StringEx.sNull(bean.getBz());
		D_xzqh_bgmxb mxbDw = new D_xzqh_bgmxb();
		mxbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		mxbDw.insert(-1);
		mxbDw.setItemString(0, "MXBXH", mxbxh);
		mxbDw.setItemString(0, "GROUPXH", groupxh);
		mxbDw.setItemString(0, "BGLX_DM", bglx_dm);
		mxbDw.setItemString(0, "PXH", String.valueOf((j+1)));
		mxbDw.setItemString(0, "YSXZQH_DM", ysxzqh_dm);
		mxbDw.setItemString(0, "YSXZQH_MC", ysxzqh_mc);
		mxbDw.setItemString(0, "MBXZQH_DM", mbxzqh_dm);
		mxbDw.setItemString(0, "MBXZQH_MC", mbxzqh_mc);
		mxbDw.setItemString(0, "BZ", bz);
		mxbDw.setItemString(0, "RINGFLAG", ringFlag);
		mxbDw.setItemString(0, "CLZT_DM", "10");
		mxbDw.setItemString(0, "LRR_DM", czry_dm);
		mxbDw.setItemString(0, "LRSJ", sysTime);			
		mxbDw.setItemString(0, "LRJG_DM", qxjg_dm);
		mxbDw.setItemString(0, "XGR_DM", czry_dm);
		mxbDw.setItemString(0, "XGSJ", sysTime);			
		mxbDw.setItemString(0, "XGJG_DM", qxjg_dm);
		mxbDw.setTransObject(ut);
		mxbDw.update(false);

	}
	
	/**
	 * 
	 * <p>
	 * �������ƣ�getSqdxx
	 * </p>
	 * <p>
	 * ������������ȡ���뵥��Ϣ
	 * </p>
	 * 
	 * @param lrjg_dm
	 *            ������������
	 * @author lijl
	 * @since 2009-7-8
	 */
	public String getSqdxx(String xzqh_dm) throws Exception{
		String lwxzqh_dm = "";
		if(xzqh_dm.length()>6){
			lwxzqh_dm = xzqh_dm.substring(0, 6);
		}
		String resultXml = "";
		DataWindow dw = DataWindow.dynamicCreate("select SQDXH,SQDZT_DM,SQDZT_DM SQDZTDM from XZQH_BGSQD where SBXZQH_DM='"+lwxzqh_dm+"' and SQDZT_DM<50");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long cnt = dw.retrieve();
		if(cnt>0){
			Dmmc.convDmmc(dw,"SQDZT_DM", "V_DM_XZQH_SQDZT");		
			resultXml = dw.toXML().toString();
			resultXml = resultXml.replaceAll("<ITEM>", "").replaceAll("</ITEM>", "");
		}
		return resultXml;
	}
	
	/**
	 * 
	 * <p>
	 * �������ƣ�insertZl
	 * </p>
	 * <p>
	 * ������������¼δ����������־��
	 * </p>
	 * 
	 * @param xzqhbgzl
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-7-21
	 */
	public void insertBgmxlsjl(XzqhbgBean bean, String czry_dm,String sysTime,
			UserTransaction ut) throws Exception {
		try {
			
			String insert_rzsql = "INSERT INTO XZQH_BGMXLSJL (MXBXH,GROUPXH,YSXZQH_DM,YSXZQH_MC,BGLX_DM,MBXZQH_DM,MBXZQH_MC,LRR_DM,LRSJ) VALUES('"
					+ StringEx.sNull(bean.getSqbmxXh())
					+ "','"
					+ StringEx.sNull(bean.getSqbxh())
					+ "','"
					+ StringEx.sNull(bean.getSrcXzqhdm())
					+ "','"
					+ StringEx.sNull(bean.getSrcXzqhmc())
					+ "','"
					+ StringEx.sNull(bean.getBglxdm())
					+ "','"
					+ StringEx.sNull(bean.getDestXzqhdm())
					+ "','"
					+ StringEx.sNull(bean.getDestXzqhmc())
					+ "','"
					+ StringEx.sNull(czry_dm)
					+ "',"
					+ "to_date('"+StringEx.sNull(sysTime)+"','yyyy-mm-dd hh24:mi:ss')"
					+ ")";
			DataWindow insertdw = DataWindow.dynamicCreate(insert_rzsql);
			insertdw.setTransObject(ut);
			insertdw.update(false);
			
		} catch (Exception e) {
			LogManager.getLogger().log(e);
			throw e;
		}
	}
}
