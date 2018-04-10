package com.padis.business.xzqhwh.xzqhbg;

import java.util.ArrayList;
import java.util.List;

import com.padis.business.common.data.xzqh.D_xzqh_bgmxb;
import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.IXzqhYwsjbg;
import com.padis.business.xzqhwh.common.XzqhbgBean;
import com.padis.business.xzqhwh.impl.JfXzqhYwsjbgImp;
import com.padis.business.xzqhwh.impl.KdYwsjbgImpl;
import com.padis.business.xzqhwh.impl.LgXzqhYwsjbgImp;
import com.padis.business.xzqhwh.impl.StYwsjbgImpl;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.log.LogManager;
import ctais.services.or.UserTransaction;
import ctais.util.StringEx;

public abstract class XzqhbgAbstractService {

	protected IXzqhYwsjbg jfImp;

	protected IXzqhYwsjbg kdImp;

	protected IXzqhYwsjbg lgImp;

	protected IXzqhYwsjbg stImp;

	public XzqhbgAbstractService() {
		// 实例化业务对象
		jfImp = new JfXzqhYwsjbgImp();
		kdImp = new KdYwsjbgImpl();
		lgImp = new LgXzqhYwsjbgImp();
		stImp = new StYwsjbgImpl();
	}

	/**
	 * 
	 * <p>
	 * 方法名称：updateXzqh
	 * </p>
	 * <p>
	 * 方法描述：更新行政区划，同时调用业务接口
	 * </p>
	 * 
	 * @param zqhbgBean类型的数据
	 * @author dongzga
	 * @since 2009-7-8
	 */
	public abstract void updateXzqh(XzqhbgBean xb) throws Exception;

	/**
	 * 
	 * <p>
	 * 方法名称：start
	 * </p>
	 * <p>
	 * 方法描述：开始处理业务申请单的方法
	 * </p>
	 * 
	 * @param sqbxh
	 *            申请表序号
	 * @return 无
	 * @throws Exception
	 *             如果检查业务清单有逻辑错误，需要抛出异常
	 * @author lijl
	 * @since 2009-7-8
	 */
	public void start(String mxbxh) throws Exception {

		D_xzqh_bgmxb mxbDw = new D_xzqh_bgmxb();
		mxbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		mxbDw.setFilter("MXBXH='" + mxbxh + "'");
		long count = mxbDw.retrieve();
		if (count <= 0)
			return;
		// 更新明细表
//		DataWindow updateSqdDw = DataWindow
//		.dynamicCreate("UPDATE XZQH_BGMXB SET CLZT_DM='20' WHERE MXBXH='" + mxbxh+ "' AND CLZT_DM='10'");
//		updateSqdDw.setTransObject(new UserTransaction());
//		updateSqdDw.update(true);
//		int updateCount = updateSqdDw.getUpdatedRowNum();
//		if(updateCount<=0){
//			return;
//		}
		mxbDw.setItemString(0L, "CLZT_DM", Common.XZQH_CLZT_CLZ);// 处理中状态
		mxbDw.setItemString(0L, "CLJG", "处理中！");
		mxbDw.setTransObject(new UserTransaction());
		mxbDw.update(true);

		XzqhbgBean xb = new XzqhbgBean();
		String groupxh = String.valueOf(mxbDw.getItemAny(0L, "GROUPXH"));
		String bglx_dm = StringEx.sNull(mxbDw.getItemAny(0L, "BGLX_DM"));
		String ysxzqh_dm = StringEx.sNull(mxbDw.getItemAny(0L, "YSXZQH_DM"));
		String ysxzqh_mc = StringEx.sNull(mxbDw.getItemAny(0L, "YSXZQH_MC"));
		String mbxzqh_dm = StringEx.sNull(mxbDw.getItemAny(0L, "MBXZQH_DM"));
		String mbxzqh_mc = StringEx.sNull(mxbDw.getItemAny(0L, "MBXZQH_MC"));
		String czry_dm = StringEx.sNull(mxbDw.getItemAny(0L, "LRR_DM"));
		String qxjg_dm = StringEx.sNull(mxbDw.getItemAny(0L, "LRJG_DM"));
		String ringFlag = StringEx.sNull(mxbDw.getItemAny(0L, "RINGFLAG"));
		xb.setSqbxh(groupxh);// 现在组号对应以前旧版本的的申请表序号
		xb.setSqbmxXh(mxbxh);// 现在的明细表序号对应以前申请表明细序号
		xb.setBglxdm(bglx_dm);
		xb.setSrcXzqhdm(ysxzqh_dm);
		xb.setSrcXzqhmc(ysxzqh_mc);
		xb.setDestXzqhdm(mbxzqh_dm);
		xb.setDestXzqhmc(mbxzqh_mc);
		xb.setQx_jgdm(qxjg_dm);
		xb.setLrr_dm(czry_dm);
		if(ringFlag.equals("1")){
			xb.setRingFlag(true);
		}else{
			xb.setRingFlag(false);
		}
		String sysTime = XtDate.getDBTime();
		try {
			XzqhbgService service = new XzqhbgService();
			service.updateXzqh(xb); //22222222222222
			mxbDw.setItemString(0L, "CLZT_DM", Common.XZQH_CLZT_CLCG);
			mxbDw.setItemString(0L, "XGSJ", sysTime);
			mxbDw.setItemString(0L, "CLJG", "处理成功！");
		} catch (Exception e) {
			mxbDw.setItemString(0L, "CLZT_DM", Common.XZQH_CLZT_CLSB);
			mxbDw.setItemString(0L, "CLJG", "处理失败：" + e.getMessage());
			mxbDw.setItemString(0L, "XGSJ", sysTime);
			e.printStackTrace();
			LogManager.getLogger().log(e);
			throw e;
		} finally {
			mxbDw.setTransObject(new UserTransaction());
			mxbDw.update(true);
		}

	}

	/**
	 * 
	 * <p>
	 * 方法名称：getYwsjbgZl
	 * </p>
	 * <p>
	 * 方法描述：得到所有业务执行变更指令，需要将 新增 和 名称 变更的指令从总的指令表中剔除，因为新增和名称变更不需要迁移业务数据
	 * </p>
	 * 
	 * @param bgmxzl
	 * @return
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-7-15
	 */
	protected List getYwsjbgZl(List bgmxzl) throws Exception {
		// 查看业务是否允许操作
		List ywsjBgzl = new ArrayList();// 业务数据变更指令,需要过滤掉新增和名称变更的指令
		for (int i = 0; bgmxzl != null && i < bgmxzl.size(); i++) {
			XzqhbgBean bean = (XzqhbgBean) bgmxzl.get(i);
			String bglxdm = bean.getBglxdm();
			String ysxzqh_dm = StringEx.sNull(bean.getSrcXzqhdm());
			String mbxzqh_dm = StringEx.sNull(bean.getDestXzqhdm());
			if (bglxdm.equals(Common.ADD)
					|| Common.checkMcbg(ysxzqh_dm, mbxzqh_dm)||(!Common.getSjxzqhdm(Common.getSjxzqhdm(ysxzqh_dm))
							.equals(Common.getSjxzqhdm(Common.getSjxzqhdm(mbxzqh_dm)))
							&&(bglxdm.equals(Common.MERGE)||bglxdm.equals(Common.MOVE))))// 过滤掉
																		// 新增
																		// 和名称变更的指令
				continue;
			ywsjBgzl.add(bean);
		}
		return ywsjBgzl;

	}

}
