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
		// ʵ����ҵ�����
		jfImp = new JfXzqhYwsjbgImp();
		kdImp = new KdYwsjbgImpl();
		lgImp = new LgXzqhYwsjbgImp();
		stImp = new StYwsjbgImpl();
	}

	/**
	 * 
	 * <p>
	 * �������ƣ�updateXzqh
	 * </p>
	 * <p>
	 * ������������������������ͬʱ����ҵ��ӿ�
	 * </p>
	 * 
	 * @param zqhbgBean���͵�����
	 * @author dongzga
	 * @since 2009-7-8
	 */
	public abstract void updateXzqh(XzqhbgBean xb) throws Exception;

	/**
	 * 
	 * <p>
	 * �������ƣ�start
	 * </p>
	 * <p>
	 * ������������ʼ����ҵ�����뵥�ķ���
	 * </p>
	 * 
	 * @param sqbxh
	 *            ��������
	 * @return ��
	 * @throws Exception
	 *             ������ҵ���嵥���߼�������Ҫ�׳��쳣
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
		// ������ϸ��
//		DataWindow updateSqdDw = DataWindow
//		.dynamicCreate("UPDATE XZQH_BGMXB SET CLZT_DM='20' WHERE MXBXH='" + mxbxh+ "' AND CLZT_DM='10'");
//		updateSqdDw.setTransObject(new UserTransaction());
//		updateSqdDw.update(true);
//		int updateCount = updateSqdDw.getUpdatedRowNum();
//		if(updateCount<=0){
//			return;
//		}
		mxbDw.setItemString(0L, "CLZT_DM", Common.XZQH_CLZT_CLZ);// ������״̬
		mxbDw.setItemString(0L, "CLJG", "�����У�");
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
		xb.setSqbxh(groupxh);// ������Ŷ�Ӧ��ǰ�ɰ汾�ĵ���������
		xb.setSqbmxXh(mxbxh);// ���ڵ���ϸ����Ŷ�Ӧ��ǰ�������ϸ���
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
			mxbDw.setItemString(0L, "CLJG", "����ɹ���");
		} catch (Exception e) {
			mxbDw.setItemString(0L, "CLZT_DM", Common.XZQH_CLZT_CLSB);
			mxbDw.setItemString(0L, "CLJG", "����ʧ�ܣ�" + e.getMessage());
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
	 * �������ƣ�getYwsjbgZl
	 * </p>
	 * <p>
	 * �����������õ�����ҵ��ִ�б��ָ���Ҫ�� ���� �� ���� �����ָ����ܵ�ָ������޳�����Ϊ���������Ʊ������ҪǨ��ҵ������
	 * </p>
	 * 
	 * @param bgmxzl
	 * @return
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-7-15
	 */
	protected List getYwsjbgZl(List bgmxzl) throws Exception {
		// �鿴ҵ���Ƿ��������
		List ywsjBgzl = new ArrayList();// ҵ�����ݱ��ָ��,��Ҫ���˵����������Ʊ����ָ��
		for (int i = 0; bgmxzl != null && i < bgmxzl.size(); i++) {
			XzqhbgBean bean = (XzqhbgBean) bgmxzl.get(i);
			String bglxdm = bean.getBglxdm();
			String ysxzqh_dm = StringEx.sNull(bean.getSrcXzqhdm());
			String mbxzqh_dm = StringEx.sNull(bean.getDestXzqhdm());
			if (bglxdm.equals(Common.ADD)
					|| Common.checkMcbg(ysxzqh_dm, mbxzqh_dm)||(!Common.getSjxzqhdm(Common.getSjxzqhdm(ysxzqh_dm))
							.equals(Common.getSjxzqhdm(Common.getSjxzqhdm(mbxzqh_dm)))
							&&(bglxdm.equals(Common.MERGE)||bglxdm.equals(Common.MOVE))))// ���˵�
																		// ����
																		// �����Ʊ����ָ��
				continue;
			ywsjBgzl.add(bean);
		}
		return ywsjBgzl;

	}

}
