package com.padis.business.xzqhwh.xzqhbg;

import java.util.List;

import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.XzqhbgBean;
import com.padis.business.xzqhwh.common.XzqhbgCommon;
import com.padis.common.xtservice.XtDate;

import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.or.UserTransaction;
import ctais.util.StringEx;

public class XzqhbgService extends XzqhbgAbstractService {
	XzqhbgCommon bgCommon = new XzqhbgCommon();

	/**
	 * 
	 * <p>
	 * �������ƣ�updateXzqh
	 * </p> 
	 * <p>
	 * ������������������������ͬʱ����ҵ��ӿ�
	 * </p>
	 * 
	 * @param xzqhbgzl
	 *            �����������ָ�ÿ��List����洢 XzqhbgBean���͵�����
	 * @author dongzga
	 * @since 2009-7-8
	 */
	public void updateXzqh(XzqhbgBean xb) throws Exception {
		List xzqhbgzl = bgCommon.convBgmxToZl("V_DM_XZQH", xb);
		if (xzqhbgzl == null || xzqhbgzl.size() == 0) 
			return;

		UserTransaction ut = new UserTransaction();
		try {
			ut.begin();
			this.insertZl(xzqhbgzl, ut);// ��¼ָ��
			String sysTime = XtDate.getDBTime();
			String bglx_dm = StringEx.sNull(xb.getBglxdm());
			String mbxzqh_dm = StringEx.sNull(xb.getDestXzqhdm());
			String mbxzqh_mc = StringEx.sNull(xb.getDestXzqhmc());
			String ysxzqh_dm = StringEx.sNull(xb.getSrcXzqhdm());
			String ysxzqh_mc = StringEx.sNull(xb.getSrcXzqhmc());
			if(!Common.getSjxzqhdm(Common.getSjxzqhdm(ysxzqh_dm))
					.equals(Common.getSjxzqhdm(Common.getSjxzqhdm(mbxzqh_dm)))
					&&(bglx_dm.equals(Common.MERGE)||bglx_dm.equals(Common.MOVE))){
				bgCommon.logicVerifyKljXzqh(ysxzqh_dm, ysxzqh_mc, bglx_dm, mbxzqh_dm,
						mbxzqh_mc, "DM_XZQH",xb.getRingFlag(),xb.getLrr_dm());
			}else {
				bgCommon.logicVerifyXzqh(ysxzqh_dm, ysxzqh_mc, bglx_dm, mbxzqh_dm,
						mbxzqh_mc, "DM_XZQH",xb.getRingFlag(),xb.getLrr_dm());
			}
			//bgCommon.logicVerifyXzqh(ysxzqh_dm, ysxzqh_mc, bglx_dm, mbxzqh_dm,
			//		mbxzqh_mc, "DM_XZQH",xb.getRingFlag(),xb.getLrr_dm());
			bgCommon.saveXzqh(xb, sysTime, "dm_xzqh", ut);
			// ����ҵ��ӿڣ��ȴ����ؽ�������ΪTRUE���ύ�������ع�
			this.updateYwsj(xzqhbgzl, ut);  //33333333333333333
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.getLogger().log(e);
			ut.rollback();
			throw e;
		}
	}

	/**
	 * 
	 * <p>
	 * �������ƣ�insertZl
	 * </p>
	 * <p>
	 * ������������¼ָ��
	 * </p>
	 * 
	 * @param xzqhbgzl
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-7-21
	 */
	public void insertZl(List xzqhbgzl, UserTransaction ut) throws Exception {

		for (int i = 0; xzqhbgzl != null && i < xzqhbgzl.size(); i++) {
			
			XzqhbgBean bean = (XzqhbgBean) xzqhbgzl.get(i);
			String insert_zlsql = "INSERT INTO XZQHBG_ZL (SQBXH,SQBRZXH,SRCXZQHDM,SRCXZQHMC,BGLX_DM,DESTXZQHDM,DESTXZQHMC,LOG_SJ) VALUES('"
					+ StringEx.sNull(bean.getSqbxh())
					+ "','"
					+ StringEx.sNull(bean.getSqbmxXh())
					+ "','"
					+ StringEx.sNull(bean.getSrcXzqhdm())
					+ "','"
					+ StringEx.sNull(bean.getSrcXzqhmc())
					+ "','"
					+ StringEx.sNull(bean.getBglxdm())
					+ "','"
					+ StringEx.sNull(bean.getDestXzqhdm())
					+ "','"
					+ StringEx.sNull(bean.getDestXzqhmc()) + "',sysdate)";
			DataWindow insertdw = DataWindow.dynamicCreate(insert_zlsql);
			insertdw.setTransObject(ut);
			insertdw.update(false);
		}
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
	public void insertWclsjrzb(List xzqhbgzl, String ywxtmc, String bz,
			UserTransaction ut) throws Exception {
		// UserTransaction ut = new UserTransaction();
		try {
			// ut.begin();
			for (int i = 0; xzqhbgzl != null && i < xzqhbgzl.size(); i++) {
				XzqhbgBean bean = (XzqhbgBean) xzqhbgzl.get(i);
				String insert_rzsql = "INSERT INTO XT_XZQHWCLSJRZB (MXXH,SQBXH,YWXTMC,YSXZQH_DM,YSXZQH_MC,BGLX_DM,MBXZQH_DM,MBXZQH_MC,BZ,DELBZ,LRSJ) VALUES('"
						+ StringEx.sNull(bean.getSqbmxXh())
						+ "','"
						+ StringEx.sNull(bean.getSqbxh())
						+ "','"
						+ ywxtmc
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
						+ bz
						+ "','2',sysdate)";
				DataWindow insertdw = DataWindow.dynamicCreate(insert_rzsql);
				insertdw.setTransObject(ut);
				insertdw.update(false);
			}
			// ut.commit();
		} catch (Exception e) {
			// ut.rollback();
			LogManager.getLogger().log(e);
			throw e;
		}

	}

	/**
	 * 
	 * <p>
	 * �������ƣ�updateYwsj
	 * </p>
	 * <p>
	 * ��������������ҵ�����ݽӿ�
	 * </p>
	 * 
	 * @param xzqhbgxx
	 * @param ut
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-7-14
	 */
	private void updateYwsj(List xzqhbgxx, UserTransaction ut) throws Exception {
		List ywsjBgzl = this.getYwsjbgZl(xzqhbgxx);
		if (ywsjBgzl == null || ywsjBgzl.size() <= 0)
			return;

		String bglx_dm = ((XzqhbgBean) ywsjBgzl.get(0)).getBglxdm();
		LogManager.getLogger().info("��ʼ�������ܵ�ҵ������......");
		lgImp.updateYwsj(ywsjBgzl, ut);
//		if (!lgImp.updateYwsj(ywsjBgzl, ut))
//			throw new Exception("���ܵ�ҵ�����ݴ���ʧ��");

		if (!bglx_dm.equals("31") && !bglx_dm.equals("41")) {
			LogManager.getLogger().info("��ʼ������������ҵ������......");
			jfImp.updateYwsj(ywsjBgzl, ut);
			LogManager.getLogger().info("��ʼ������ٵ����ҵ������......");
			kdImp.updateYwsj(ywsjBgzl, ut);
			LogManager.getLogger().info("��ʼ������ҵͳ�Ƶ�ҵ������......");
			stImp.updateYwsj(ywsjBgzl, ut);

//			if (!jfImp.updateYwsj(ywsjBgzl, ut))
//				throw new Exception("����������ҵ�����ݴ���ʧ��");

//			LogManager.getLogger().info("��ʼ������ٵ����ҵ������......");
//			if (!kdImp.updateYwsj(ywsjBgzl, ut))
//				throw new Exception("���ٵ����ҵ�����ݴ���ʧ��");

//			LogManager.getLogger().info("��ʼ������ҵͳ�Ƶ�ҵ������......");
//			if (!stImp.updateYwsj(ywsjBgzl, ut))
//				throw new Exception("��ҵͳ�Ƶ�ҵ�����ݴ���ʧ��");
		} else {
			this.insertWclsjrzb(ywsjBgzl, "��������,���ٵ���,��ҵͳ��", "����ϵͳ������δǨ��", ut);
		}
	}

}