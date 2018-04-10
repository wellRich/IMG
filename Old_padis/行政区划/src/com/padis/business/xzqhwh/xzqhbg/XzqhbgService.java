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
	 * 方法名称：updateXzqh
	 * </p> 
	 * <p>
	 * 方法描述：更新行政区划，同时调用业务接口
	 * </p>
	 * 
	 * @param xzqhbgzl
	 *            行政区划变更指令，每个List里面存储 XzqhbgBean类型的数据
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
			this.insertZl(xzqhbgzl, ut);// 记录指令
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
			// 调用业务接口，等待返回结果，结果为TRUE则提交事物，否则回滚
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
	 * 方法名称：insertZl
	 * </p>
	 * <p>
	 * 方法描述：记录指令
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
	 * 方法名称：insertZl
	 * </p>
	 * <p>
	 * 方法描述：记录未处理数据日志表
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
	 * 方法名称：updateYwsj
	 * </p>
	 * <p>
	 * 方法描述：更新业务数据接口
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
		LogManager.getLogger().info("开始处理流管的业务数据......");
		lgImp.updateYwsj(ywsjBgzl, ut);
//		if (!lgImp.updateYwsj(ywsjBgzl, ut))
//			throw new Exception("流管的业务数据处理失败");

		if (!bglx_dm.equals("31") && !bglx_dm.equals("41")) {
			LogManager.getLogger().info("开始处理奖励扶助的业务数据......");
			jfImp.updateYwsj(ywsjBgzl, ut);
			LogManager.getLogger().info("开始处理快速调查的业务数据......");
			kdImp.updateYwsj(ywsjBgzl, ut);
			LogManager.getLogger().info("开始处理事业统计的业务数据......");
			stImp.updateYwsj(ywsjBgzl, ut);

//			if (!jfImp.updateYwsj(ywsjBgzl, ut))
//				throw new Exception("奖励扶助的业务数据处理失败");

//			LogManager.getLogger().info("开始处理快速调查的业务数据......");
//			if (!kdImp.updateYwsj(ywsjBgzl, ut))
//				throw new Exception("快速调查的业务数据处理失败");

//			LogManager.getLogger().info("开始处理事业统计的业务数据......");
//			if (!stImp.updateYwsj(ywsjBgzl, ut))
//				throw new Exception("事业统计的业务数据处理失败");
		} else {
			this.insertWclsjrzb(ywsjBgzl, "奖励扶助,快速调查,事业统计", "以上系统数据尚未迁移", ut);
		}
	}

}