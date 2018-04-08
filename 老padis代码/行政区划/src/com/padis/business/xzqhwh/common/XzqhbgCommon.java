package com.padis.business.xzqhwh.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.padis.business.xzqhwh.common.linkxzqh.LinkedXzqh;
import com.padis.business.xzqhwh.common.linkxzqh.XzqhNode;
import com.padis.business.xzqhwh.zxbg.bgdzgl.bgdzblr.CheckSqd;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.business.common.sequence.IGeneralSequence;
import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;
import ctais.services.sequence.SequenceManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

public class XzqhbgCommon {

	/**
	 * 
	 * <p>
	 * 方法名称：splitRingData
	 * </p>
	 * <p>
	 * 方法描述：根据申请表类型和申请表的业务清单，检查业务业务是否正确，如果是代码变更或者代码名称变更申请表，如果存在闭环，需要解开
	 * </p>
	 * 
	 * @param xzqhbgmx
	 *            存储的是XzqhbgBean
	 * @param sqblx_dm
	 *            申请表类型代码
	 * @return List 如果是闭环，返回解环后的业务清单，例如 1->2;2->3;3->1 ；最后返回 3->A 2->3 1->2 A->1
	 * @throws Exception
	 *             如果检查业务清单有逻辑错误，需要抛出异常
	 * @author dongzga
	 * @since 2009-7-8
	 */
	public List splitRingData(List xzqhbgmx) throws Exception {
		CheckSqd check = new CheckSqd();
		List list = new ArrayList();
		LinkedXzqh linked = new LinkedXzqh();
		linked.data = xzqhbgmx;
		int count = 0;
		while (linked.data.size() > 0) {
			XzqhNode xn = (XzqhNode) linked.data.get(0);
			List result = new ArrayList();
			result.add(xn);
			linked.group(xn, result);
			// 判断每组数据量是否大于1，如果大于则是环或是链
			if (result.size() > 1) {
				XzqhNode startNode = (XzqhNode) result.get(0);
				XzqhNode endNode = (XzqhNode) result.get(result.size() - 1);
				String srcxzqhDm = startNode.getXzqhbgBean().getSrcXzqhdm();
				String destxzqhdm = endNode.getXzqhbgBean().getDestXzqhdm();
				// 判断数据链的第一个节点的源是否和最后一个节点的目标相等，相等则是环状数据，否则链状数据
				if (srcxzqhDm.equals(destxzqhdm)) {
					count++;// 记录第几个环
						String temp = "";// 中间行政区划代码
					String newxzqhdm = "";// 最后一个节点的目标代码
					String newxzqhmc = "";
					String bglx_dm = "";
					String lrr_dm = "";
					String qx_jgdm = "";
					String sqbxh = "";
					String sqbmxXh = "";
					String srcXzqhTemp_mc = "";
					for (int i = (result.size() - 1); i >= 0; i--) {
						XzqhNode xzqhNode = (XzqhNode) result.get(i);
						if (i == (result.size() - 1)) {
							String srcxzqhdm = xzqhNode.getXzqhbgBean()
									.getSrcXzqhdm();
							String jbdm = Common.getJbdm(srcxzqhdm);
							// 环数不能大于9
							if (count < 10) {
								if (jbdm.length() <= 6 && jbdm.length() >= 2) {
									temp = jbdm.substring(0, jbdm.length() - 2)
											+ "L" + count;
								} else if (jbdm.length() > 6
										&& jbdm.length() <= 15) {
									temp = jbdm.substring(0, jbdm.length() - 3)
											+ "L0" + count;
								}
							} else {
								throw new Exception("代码变更环数超过9个！");
							}
							if (temp.length() == 4) {
								temp = temp + "00000000000";
							}
							if (temp.length() == 6) {
								temp = temp + "000000000";
							}
							if (temp.length() == 9) {
								temp = temp + "000000";
							}
							if (temp.length() == 12) {
								temp = temp + "000";
							}
							int tempCount = count;
							while (check.isExistsXzqh(temp, "")) {
								tempCount++;
								int index = temp.indexOf("L");
								String str = "";
								if (index > -1 && temp.indexOf("L") < 6) {
									str = temp.substring(index, index + 2);
									if (tempCount < 10) {
										temp = temp.replaceAll(str, "L"
												+ (tempCount));
									} else {
										throw new Exception("代码变更环数超过9个！");
									}
								}
								if (index >= 6 && temp.indexOf("L") < 15) {
									str = temp.substring(index, index + 3);
									if (tempCount < 10) {
										temp = temp.replaceAll(str, "L0"
												+ (tempCount));
									} else {
										temp = temp.replaceAll(str, "L"
												+ (tempCount));
									}
								}
							}
							srcXzqhTemp_mc = xzqhNode.getXzqhbgBean()
									.getSrcXzqhmc();
							newxzqhdm = xzqhNode.getXzqhbgBean()
									.getDestXzqhdm();
							newxzqhmc = xzqhNode.getXzqhbgBean()
									.getDestXzqhmc();
							bglx_dm = xzqhNode.getXzqhbgBean().getBglxdm();
							lrr_dm = xzqhNode.getXzqhbgBean().getLrr_dm();
							qx_jgdm = xzqhNode.getXzqhbgBean().getQx_jgdm();
							sqbxh = xzqhNode.getXzqhbgBean().getSqbxh();
							sqbmxXh = xzqhNode.getXzqhbgBean().getSqbmxXh();
							XzqhbgBean xzqhBean = new XzqhbgBean();
							xzqhBean.setDestXzqhdm(temp);
							xzqhBean.setDestXzqhmc(xzqhNode.getXzqhbgBean()
									.getSrcXzqhmc());
							xzqhBean.setSrcXzqhmc(srcXzqhTemp_mc);
							xzqhBean.setSrcXzqhdm(srcxzqhdm);
							xzqhBean.setBglxdm(bglx_dm);
							xzqhBean.setLrr_dm(lrr_dm);
							xzqhBean.setQx_jgdm(qx_jgdm);
							xzqhBean.setSqbxh(sqbxh);
							xzqhBean.setSqbmxXh(sqbmxXh);
							xzqhBean.setBz(xzqhNode.getXzqhbgBean().getBz());
							list.add(xzqhBean);
						} else {
							list.add(xzqhNode.getXzqhbgBean());
						}
					}
					IGeneralSequence iseq = (IGeneralSequence) SequenceManager
							.getSequenceFactory();
					String mxbxh = iseq.get("SEQ_XZQH_BGMXB_XL");
					// 新增加一个把临时区划变成目标行政区划的节点
					XzqhbgBean xzqhBean1 = new XzqhbgBean();
					xzqhBean1.setDestXzqhdm(newxzqhdm);
					xzqhBean1.setDestXzqhmc(newxzqhmc);
					xzqhBean1.setSrcXzqhmc(srcXzqhTemp_mc);
					xzqhBean1.setSrcXzqhdm(temp);
					xzqhBean1.setBglxdm(bglx_dm);
					xzqhBean1.setLrr_dm(lrr_dm);
					xzqhBean1.setQx_jgdm(qx_jgdm);
					xzqhBean1.setSqbxh(sqbxh);
					xzqhBean1.setSqbmxXh(mxbxh);
					xzqhBean1.setBz("中间变量");
					list.add(xzqhBean1);
				} else {
					String endSrcdm = endNode.getXzqhbgBean().getSrcXzqhdm();
					// 如果最后一个节点的目标代码已存在，则此链不符合业务要求
					if (check.isExistsXzqh(destxzqhdm, "")) {
						throw new Exception("原行政区划代码由:" + endSrcdm + "变更为："
								+ destxzqhdm + "时出错，目标代码已存在！");
					} else {
						for (int i = (result.size() - 1); i >= 0; i--) {
							XzqhNode xzqhNode = (XzqhNode) result.get(i);
							list.add(xzqhNode.getXzqhbgBean());
						}
					}
				}
			} else {
				// 单个节点
				XzqhNode node = (XzqhNode) result.get(0);
				String mbxzqhdm = node.getXzqhbgBean().getDestXzqhdm();
				String ysxzqhdm = node.getXzqhbgBean().getSrcXzqhdm();
				String bglx_dm = node.getXzqhbgBean().getBglxdm();

				if (!bglx_dm.equals(Common.MERGE)) {
					if (check.isExistsXzqh(mbxzqhdm, "")) {
						throw new Exception("原行政区划代码由:" + ysxzqhdm + "变更为："
								+ mbxzqhdm + "时出错，目标代码已存在！");
					}
				} else {
					if (!check.isExistsXzqh(mbxzqhdm, "")) {
						throw new Exception("原行政区划代码由:" + ysxzqhdm + "合并到："
								+ mbxzqhdm + "时出错，目标代码不存在！");
					}
				}
				list.add(node.getXzqhbgBean());
			}
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * 方法名称：convBgmxToZl
	 * </p>
	 * <p>
	 * 方法描述：将一个业务记录转化成指令，例如A县变更B县，最后需要将A下所有的乡和村都要列举出来返回
	 * </p>
	 * 
	 * @param xb
	 * @return
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-7-8
	 */
	public List convBgmxToZl(String viewTable, XzqhbgBean xb) throws Exception {
		List list = new ArrayList();
		if (xb == null) {
			return null;
		}
		viewTable = StringEx.sNull(viewTable).toUpperCase().indexOf("V_") < 0 ? "V_"
				+ viewTable
				: viewTable;
		String sqbmxXh = xb.getSqbmxXh();
		String sqbxh = xb.getSqbxh();
		String bglx_dm = xb.getBglxdm();
		String qx_jgdm = xb.getQx_jgdm();
		String czry_dm = xb.getLrr_dm();
		String ysXzqh_dm = xb.getSrcXzqhdm();
		String mbXzqh_dm = xb.getDestXzqhdm();
		String ysjbdm = Common.getJbdm(ysXzqh_dm);
		String mbjbdm = Common.getJbdm(mbXzqh_dm);
		if (bglx_dm.equals(Common.ADD) || bglx_dm.equals(Common.MERGE)) {
			list.add(xb);
		} else if (bglx_dm.equals(Common.CHANGE) || bglx_dm.equals(Common.MOVE)) {
			StringBuffer sql = new StringBuffer(
					"SELECT X.XZQH_DM,X.XZQH_MC,X.JCDM FROM " + viewTable
							+ " X WHERE X.JBDM LIKE '");
			sql.append(ysjbdm);
			sql.append("%' order by X.XZQH_DM");
			DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
			xzqhDw.setConnectionName(ConnConfig.getConnectionName(this
					.getClass()));
			long count = xzqhDw.retrieve();
			for (long j = (count - 1); j >= 0; j--) {
				String xzqhdm = StringEx.sNull(xzqhDw.getItemAny(j, "XZQH_DM"));
				String xzqhmc = StringEx.sNull(xzqhDw.getItemAny(j, "XZQH_MC"));
				String jcdm = StringEx.sNull(xzqhDw.getItemAny(j, "JCDM"));
				XzqhbgBean bean = new XzqhbgBean();
				bean.setSqbmxXh(sqbmxXh);
				bean.setSqbxh(sqbxh);
				bean.setBglxdm(bglx_dm);
				bean.setDestXzqhdm(xzqhdm.replaceFirst(ysjbdm, mbjbdm));
				if (j == 0) {
					bean.setDestXzqhmc(xb.getDestXzqhmc());
				} else {
					bean.setDestXzqhmc(xzqhmc);
				}
				bean.setSrcXzqhdm(xzqhdm);
				bean.setSrcXzqhmc(xzqhmc);
				bean.setQx_jgdm(qx_jgdm);
				bean.setLrr_dm(czry_dm);
				bean.setJcdm(jcdm);
				list.add(bean);
			}
		}

		return list;
	}

	/**
	 * 
	 * <p>
	 * 方法名称：updateXzqhDm
	 * </p>
	 * <p>
	 * 方法描述：更新行政区划
	 * </p>
	 * 
	 * @param dw
	 *            行政区划数据对象
	 * @param xzqhBean
	 *            新行政区划bean对象
	 * @param sjSrcXzqhmc
	 *            原始行政区划上级名称
	 * @param sjDestXzqhmc
	 *            目标行政区划上级名称
	 * @param ut
	 *            用户事物
	 * @author lijl
	 * @since 2009-7-13
	 */
	private void updateXzqhDm(XzqhbgBean xzqhBean, String sjSrcXzqhmc,
			String sjDestXzqhmc, String sysTime, String tableName,
			UserTransaction ut) throws Exception {
		tableName = StringEx.sNull(tableName).toLowerCase();
		if (tableName.indexOf("V_") >= 0)
			throw new Exception("区划传入的必须是表名称，不能是视图名称");
		String d_object = Common.ORMAP_PACKAGE + ".D_" + tableName;

		String srcXzqh_dm = xzqhBean.getSrcXzqhdm();
		String destXzqhdm = xzqhBean.getDestXzqhdm();
		//String bglx_dm = xzqhBean.getBglxdm();
		String destXzqhmc = xzqhBean.getDestXzqhmc();
		// String srcXzqhmc = xzqhBean.getSrcXzqhmc();
		String qx_jgdm = xzqhBean.getQx_jgdm();
		String lrr_dm = xzqhBean.getLrr_dm();

		DataWindow dw = (DataWindow) Class.forName(d_object).newInstance();
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		StringBuffer sql1 = new StringBuffer(
				"XYBZ = 'Y' AND YXBZ = 'Y' and XZQH_DM='");
		sql1.append(srcXzqh_dm);
		sql1.append("'");
		dw.setFilter(sql1.toString());
		long counts1 = dw.retrieve();
		if (counts1 < 1) {
			throw new Exception("原始行政区划代码：" + srcXzqh_dm + "不存在！");
		}
		String xzqh_qc = (String) dw.getItemAny(0L, "XZQH_QC");
		if (Common.checkMcbg(srcXzqh_dm, destXzqhdm)) {
			StringBuffer sqlSb = new StringBuffer("update ");
			sqlSb.append(tableName);
			sqlSb.append(" y set y.XZQH_MC = '");
			sqlSb.append(destXzqhmc);
			sqlSb.append("', y.XZQH_JC='");
			sqlSb.append(destXzqhmc);
			sqlSb.append("', y.XZQH_QC=replace(y.XZQH_QC,'");
			sqlSb.append(sjSrcXzqhmc);
			sqlSb.append("','").append(sjDestXzqhmc).append("')");
			sqlSb.append(" where y.xzqh_dm='").append(srcXzqh_dm).append("'");
			DataWindow updateDw = DataWindow.dynamicCreate(sqlSb.toString());
			updateDw.setTransObject(ut);
			updateDw.update(false);
		} else {
			dw.setItemString(0L, "YXQ_Z", sysTime);
			dw.setItemString(0L, "XYBZ", "N");
			dw.setItemString(0L, "YXBZ", "N");
			dw.setItemString(0L, "XGSJ", sysTime);
			dw.setTransObject(ut);
			dw.update(false);

			DataWindow xzqhDw = (DataWindow) Class.forName(d_object)
					.newInstance();
			xzqhDw.setConnectionName(ConnConfig.getConnectionName(this
					.getClass()));
			xzqhDw.insert(-1);

			xzqh_qc = xzqh_qc.replaceAll(sjSrcXzqhmc, sjDestXzqhmc);
			String jcdm = (String) dw.getItemAny(0L, "JCDM");
			String oldjbdm = (String) dw.getItemAny(0L, "JBDM");
			String newjbdm = destXzqhdm.substring(0, oldjbdm.length());
			String dwlsgx_dm = (String) dw.getItemAny(0L, "DWLSGX_DM");
			String xzqhlx_dm = StringEx.sNull(dw.getItemAny(0L, "XZQHLX_DM"));
			xzqhDw.setItemString(0L, "XZQH_DM", destXzqhdm);
			xzqhDw.setItemString(0L, "XZQH_MC", destXzqhmc);
			xzqhDw.setItemString(0L, "XZQH_JC", destXzqhmc);
			xzqhDw.setItemString(0L, "XZQH_QC", xzqh_qc);
			xzqhDw.setItemString(0L, "JCDM", jcdm);
			xzqhDw.setItemString(0L, "JBDM", newjbdm);
			xzqhDw.setItemString(0L, "SJ_XZQH_DM", Common
					.getSjxzqhdm(destXzqhdm));
			xzqhDw.setItemString(0L, "XYBZ", "Y");
			xzqhDw.setItemString(0L, "YXBZ", "Y");
			xzqhDw.setItemString(0L, "DWLSGX_DM", dwlsgx_dm);
			xzqhDw.setItemString(0L, "OLD_XZQH_DM", srcXzqh_dm);
			xzqhDw.setItemString(0L, "YXQ_Q", sysTime);
			xzqhDw.setItemString(0L, "QX_JGDM", qx_jgdm);
			xzqhDw.setItemString(0L, "LRR_DM", lrr_dm);
			xzqhDw.setItemString(0L, "LRSJ", sysTime);
			xzqhDw.setItemString(0L, "XGR_DM", lrr_dm);
			xzqhDw.setItemString(0L, "XGSJ", sysTime);
			xzqhDw.setItemString(0L, "XZQHLX_DM", xzqhlx_dm);
			xzqhDw.setTransObject(ut);
			xzqhDw.update(false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：addXzqh
	 * </p>
	 * <p>
	 * 方法描述：新增行政区划
	 * </p>
	 * 
	 * @param dw
	 *            行政区划数据对象
	 * @param destXzqhmc
	 *            新行政区划名称
	 * @param ut
	 *            用户事物
	 * @author lijl
	 * @since 2009-7-13
	 */
	private void addXzqh(XzqhbgBean xzqhBean, String dwlsgx_dm, String jcdm,
			String xzqh_qc, String sysTime, String tableName, UserTransaction ut)
			throws Exception {
		String srcXzqhdm = xzqhBean.getSrcXzqhdm();
		String destXzqhdm = xzqhBean.getDestXzqhdm();
		String destXzqhmc = xzqhBean.getDestXzqhmc();
		String qx_jgdm = xzqhBean.getQx_jgdm();
		String lrr_dm = xzqhBean.getLrr_dm();
		String xjjcdm = String.valueOf(Integer.parseInt(jcdm) + 1);
		tableName = StringEx.sNull(tableName).toLowerCase();
		if (tableName.indexOf("V_") >= 0)
			throw new Exception("区划传入的必须是表名称，不能是视图名称");
		String d_object = Common.ORMAP_PACKAGE + ".D_" + tableName;
		DataWindow xzqhDw = (DataWindow) Class.forName(d_object).newInstance();
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		xzqhDw.insert(-1);
		xzqhDw.setItemString(0L, "XZQH_DM", destXzqhdm);
		xzqhDw.setItemString(0L, "XZQH_MC", destXzqhmc);
		xzqhDw.setItemString(0L, "XZQH_JC", destXzqhmc);
		xzqhDw.setItemString(0L, "XZQH_QC", xzqh_qc + destXzqhmc);
		xzqhDw.setItemString(0L, "JCDM", xjjcdm);
		xzqhDw.setItemString(0L, "JBDM", Common.getJbdm(destXzqhdm));
		xzqhDw.setItemString(0L, "SJ_XZQH_DM", srcXzqhdm);
		xzqhDw.setItemString(0L, "XYBZ", "Y");
		xzqhDw.setItemString(0L, "YXBZ", "Y");
		if (dwlsgx_dm.equals("20")) {
			xzqhDw.setItemString(0L, "DWLSGX_DM", "40");
		}
		if (dwlsgx_dm.equals("40")) {
			xzqhDw.setItemString(0L, "DWLSGX_DM", "50");
		}
		if (dwlsgx_dm.equals("50")) {
			xzqhDw.setItemString(0L, "DWLSGX_DM", "60");
		}
		if (dwlsgx_dm.equals("60")) {
			xzqhDw.setItemString(0L, "DWLSGX_DM", "70");
		}
		if (dwlsgx_dm.equals("70")) {
			xzqhDw.setItemString(0L, "DWLSGX_DM", "80");
		}
		xzqhDw.setItemString(0L, "YXQ_Q", sysTime);
		xzqhDw.setItemString(0, "QX_JGDM", qx_jgdm);
		xzqhDw.setItemString(0, "LRR_DM", lrr_dm);
		xzqhDw.setItemString(0, "LRSJ", sysTime);
		xzqhDw.setItemString(0, "XGR_DM", lrr_dm);
		xzqhDw.setItemString(0, "XGSJ", sysTime);
		xzqhDw.setItemString(0, "XZQHLX_DM", "11");
		xzqhDw.setTransObject(ut);
		xzqhDw.update(false);
	}

	/**
	 * 
	 * <p>
	 * 方法名称：getSjxzqhMc
	 * </p>
	 * <p>
	 * 方法描述：获取行政区划名称
	 * </p>
	 * 
	 * @param xzqh_dm
	 *            行政区划代码
	 * @author dongzga
	 * @since 2009-7-8
	 */
	public String getSjxzqhMc(String viewName, String xzqh_dm) throws Exception {
		viewName = viewName != null && viewName.toUpperCase().indexOf("V_") < 0 ? "V_"
				+ viewName
				: viewName;
		String xzqh_qc = "";
		StringBuffer sql = new StringBuffer("SELECT XZQH_QC FROM " + viewName.toUpperCase()
				+ " WHERE XZQH_DM='");
		sql.append(xzqh_dm);
		sql.append("'");
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		if (count > 0) {
			xzqh_qc = (String) xzqhDw.getItemAny(0L, "XZQH_QC");
		}
		return xzqh_qc;
	}

	/**
	 * 
	 * <p>
	 * 方法名称：sysTime
	 * </p>
	 * <p>
	 * 方法描述：保存行政区划
	 * </p>
	 * 
	 * @param xzqhBean
	 *            行政区划bean
	 * @param sysTime
	 *            系统时间
	 * @param ut
	 *            事物
	 * @author dongzga
	 * @since 2009-7-8
	 */
	public void saveXzqh(XzqhbgBean xzqhBean, String sysTime, String tableName,
			UserTransaction ut) throws Exception {

		String bglx_dm = StringEx.sNull(xzqhBean.getBglxdm());
		String mbxzqh_dm = StringEx.sNull(xzqhBean.getDestXzqhdm());
		String sjxzqh_dm = "";
		tableName = StringEx.sNull(tableName).toLowerCase();
		if (tableName.indexOf("V_") >= 0)
			throw new Exception("区划传入的必须是表名称，不能是视图名称");

		if (bglx_dm.equals(Common.ADD)) {
			sjxzqh_dm = Common.getSjxzqhdm(mbxzqh_dm);
			DataWindow dw = DataWindow
					.dynamicCreate("select XZQH_MC,XZQH_QC,JCDM,DWLSGX_DM from V_"
							+ tableName.toUpperCase() + " where xzqh_dm='" + sjxzqh_dm + "'");
			dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
			if (dw.retrieve() < 1) {
				throw new Exception("上级区划代码：" + sjxzqh_dm + "不存在！");
			}
			String xzqh_mc = (String) dw.getItemAny(0L, "XZQH_MC");
			String xzqh_qc = (String) dw.getItemAny(0L, "XZQH_QC");
			String jcdm = (String) dw.getItemAny(0L, "JCDM");
			String dwlsgx_dm = (String) dw.getItemAny(0L, "DWLSGX_DM");
			xzqhBean.setSrcXzqhmc(xzqh_mc);
			xzqhBean.setSrcXzqhdm(sjxzqh_dm);
			//查看是否存在，防止重复数据报错
			DataWindow dw1 = DataWindow
			.dynamicCreate("select XZQH_MC,XZQH_QC,JCDM,DWLSGX_DM from V_"
					+ tableName.toUpperCase() + " where xzqh_dm='" + mbxzqh_dm + "'");
			dw1.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
			if (dw1.retrieve() >0) {
				throw new Exception("区划代码：" + mbxzqh_dm + "已存在！");
			}
			this.addXzqh(xzqhBean, dwlsgx_dm, jcdm, xzqh_qc, sysTime,
					tableName, ut);
		}
		if (bglx_dm.equals(Common.MERGE)) {

			String srcXzqh_dm = StringEx.sNull(xzqhBean.getSrcXzqhdm());
			StringBuffer sqlSb2 = new StringBuffer("update " + tableName.toUpperCase()
					+ " y set y.XYBZ='N',y.YXBZ='N',");
			sqlSb2.append("y.YXQ_Z=to_date('").append(sysTime).append(
					"','YYYY-MM-DD HH24:mi:ss'),");
			sqlSb2.append("y.XGSJ =to_date('").append(sysTime).append(
					"','YYYY-MM-DD HH24:mi:ss')");
			sqlSb2
					.append(" where y.XYBZ = 'Y' AND y.YXBZ = 'Y' and y.XZQH_DM = '");
			sqlSb2.append(srcXzqh_dm);
			sqlSb2.append("'");
			DataWindow updateDw = DataWindow.dynamicCreate(sqlSb2.toString());
			updateDw.setTransObject(ut);
			updateDw.update(false);

		}
		if (bglx_dm.equals(Common.CHANGE) || bglx_dm.equals(Common.MOVE)) {

			List zlList = this.convBgmxToZl(tableName, xzqhBean);
			if (zlList == null || zlList.size() <= 0) {
				return;
			}
			XzqhbgBean sjBean = (XzqhbgBean) zlList.get(zlList.size() - 1);
			String sjDestXzqhdm = sjBean.getDestXzqhdm();
			String sjDestXzqhmc = sjBean.getDestXzqhmc();
			String sjSrcXzqhdm = sjBean.getSrcXzqhdm();
			String sjSrcXzqhmc = sjBean.getSrcXzqhmc();
			if (bglx_dm.equals(Common.MOVE)) {
				sjDestXzqhmc = this.getSjxzqhMc(tableName, Common
						.getSjxzqhdm(sjDestXzqhdm))
						+ sjBean.getDestXzqhmc();
				sjSrcXzqhmc = this.getSjxzqhMc(tableName, Common
						.getSjxzqhdm(sjSrcXzqhdm))
						+ sjBean.getSrcXzqhmc();
			}
			for (int m = 0; m < zlList.size(); m++) {
				XzqhbgBean bean1 = (XzqhbgBean) zlList.get(m);
				this.updateXzqhDm(bean1, sjSrcXzqhmc, sjDestXzqhmc, sysTime,
						tableName, ut);
			}
		}
	}


	/**
	 * 
	 * <p>
	 * 方法名称：
	 * </p>
	 * <p>
	 * 方法描述：逻辑校验所有变更类型
	 * </p>
	 * 
	 * @param yxzqhdm 原区划代码
	 * @param yxzqhmc 原区划名称
	 * @param bglx_dm 变更类型
	 * @param mbxzqhdm 现区划代码
	 * @param mbxzqhmc 现区划名称
	 * @param tablename 区划预览表名
	 * @param rinfFlag 是否是循环变更 ，如果为true是循环变更，不考虑重用代码情况，否则不是循环变更，要判断是否重用代码
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-11-3
	 */
	public void logicVerifyXzqh(String ysxzqh_dm, String ysxzqh_mc,
			String bglx_dm, String mbxzqh_dm, String mbxzqh_mc, String tablename,boolean ringFlag, String czry_dm)
			throws Exception {
		String viewName = tablename != null
				&& tablename.toUpperCase().indexOf("V_") < 0 ? "V_" + tablename
				: tablename;
		if (bglx_dm.equals(Common.ADD)) {
			String sjxzqh_dm = Common.getSjxzqhdm(mbxzqh_dm);
			if (!this.isExistsXzqh(sjxzqh_dm, "", viewName)) {
				throw new Exception("新增的区划代码[" + mbxzqh_dm + "]的上级区划代码["
						+ sjxzqh_dm + "]不存在！");
			} else if (this.isExistsXzqh(mbxzqh_dm, "", viewName)) {
				throw new Exception("新增的现区划代码[" + mbxzqh_dm + "]已存在！");
			}else if (this.isExistsYjyXzqh(mbxzqh_dm, viewName)&&Integer.parseInt(Common.getJcdm(mbxzqh_dm))<4&&!czry_dm.equals("00000000000")) {//只有村级可以使用禁用代码
				throw new Exception("新增的现区划代码[" + mbxzqh_dm + "]在系统中已经被使用过，不能重复使用！");
			}
		} else if (bglx_dm.equals(Common.CHANGE)) {
			if (!this.isExistsXzqh(ysxzqh_dm, "", viewName)) {
				throw new Exception("变更的原区划代码[" + ysxzqh_dm + "]不存在！");
			} else if (this.isExistsXzqh(mbxzqh_dm, "", viewName)) {
				if (!ysxzqh_dm.equals(mbxzqh_dm))
					throw new Exception("变更的现区划代码[" + mbxzqh_dm + "]已存在！");
				else if (ysxzqh_dm.equals(mbxzqh_dm)
						&& ysxzqh_mc.equals(mbxzqh_mc)) {
					throw new Exception("变更的原区划代码、名称和现区划代码、名称完全相同！");
				}else if(!Common.getJcdm(ysxzqh_dm).equals(Common.getJcdm(mbxzqh_dm))){
					throw new Exception("变更的原区划代码和现区划代码不是一个级别！");
				}
			}else if (ringFlag==false && this.isExistsYjyXzqh(mbxzqh_dm, viewName)&&Integer.parseInt(Common.getJcdm(mbxzqh_dm))<4&&!czry_dm.equals("00000000000")) {//非环状变更情况要考虑重复代码重用，环状情况不考虑
				throw new Exception("变更的现区划代码[" + mbxzqh_dm + "]在系统中已经被使用过，不能重复使用！");
			}
		} else if (bglx_dm.equals(Common.MERGE)) {
			if (!this.isExistsXzqh(ysxzqh_dm, "", viewName)) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm + "]不存在！");
			} else if (!this.isExistsXzqh(mbxzqh_dm, "", viewName)) {
				throw new Exception("并入的现行政区划代码[" + mbxzqh_dm + "]不存在！");
			} else if (ysxzqh_dm.equals(mbxzqh_dm)) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]相等！");
			} else if (!Common.getJcdm(ysxzqh_dm).equals(
					Common.getJcdm(mbxzqh_dm))) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]不是同级区划！");
			} else if (!Common.getSjxzqhdm(Common.getSjxzqhdm(ysxzqh_dm))
					.equals(Common.getSjxzqhdm(Common.getSjxzqhdm(mbxzqh_dm)))) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]属于跨两级的并入，不符合业务规范！");
			} else if (this.getXjXzqh(ysxzqh_dm, viewName) != null) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm
						+ "]有下级区划不能并入，请先将下级区划进行迁移！");
			}
		} else if (bglx_dm.equals(Common.MOVE)) {
			String yssjxzqh_dm = Common.getSjxzqhdm(ysxzqh_dm);
			String mbsjxzqh_dm = Common.getSjxzqhdm(mbxzqh_dm);
			if (!this.isExistsXzqh(ysxzqh_dm, "", viewName)) {
				throw new Exception("迁移的原区划代码“" + ysxzqh_dm + "”不存在！");
			} else if (this.isExistsXzqh(mbxzqh_dm, "", viewName)) {
				throw new Exception("迁移的现区划代码“" + mbxzqh_dm + "”已存在！");
			} else if (!Common.getJcdm(ysxzqh_dm).equals(
					Common.getJcdm(mbxzqh_dm))) {
				throw new Exception("迁移的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]不是同级区划！");
			} else if (yssjxzqh_dm.equals(mbsjxzqh_dm)) {
				throw new Exception("迁移的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]的上级区划相同，必须跨级迁移！");
			} else if (!Common
					.getSjxzqhdm(Common.getSjxzqhdm(yssjxzqh_dm))
					.equals(Common.getSjxzqhdm(Common.getSjxzqhdm(mbsjxzqh_dm)))) {
				throw new Exception("迁移的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]不能跨两级以上区划迁移！");
			}else if (this.isExistsYjyXzqh(mbxzqh_dm, viewName)&&Integer.parseInt(Common.getJcdm(mbxzqh_dm))<4&&!czry_dm.equals("00000000000")) {
				throw new Exception("迁移的现区划代码[" + mbxzqh_dm + "]在系统中已经被使用过，不能重复使用！");
			}
		} else {
			throw new Exception("区划变更类型代码[" + bglx_dm
					+ "]不是系统规定的[11 新增,21 变更,31 并入,41 迁移] 变更类型，无法完成变更！");
		}

	}
	
	/**
	 * 
	 * <p>
	 * 方法名称：
	 * </p>
	 * <p>
	 * 方法描述：逻辑校验所有变更类型
	 * 跨两级变更
	 * </p>
	 * 
	 * @param yxzqhdm
	 * @param yxzqhmc
	 * @param bglx_dm
	 * @param mbxzqhdm
	 * @param mbxzqhmc
	 * @param tablename
	 * @param rinfFlag 是否是循环变更 ，如果为true是循环变更，不考虑重用代码情况，否则不是循环变更，要判断是否重用代码
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-11-3
	 */
	public void logicVerifyKljXzqh(String ysxzqh_dm, String ysxzqh_mc,
			String bglx_dm, String mbxzqh_dm, String mbxzqh_mc, String tablename,boolean ringFlag, String czry_dm)
			throws Exception {
		String viewName = tablename != null
				&& tablename.toUpperCase().indexOf("V_") < 0 ? "V_" + tablename
				: tablename;
		if (bglx_dm.equals(Common.ADD)) {
			String sjxzqh_dm = Common.getSjxzqhdm(mbxzqh_dm);
			if (!this.isExistsXzqh(sjxzqh_dm, "", viewName)) {
				throw new Exception("新增的区划代码[" + mbxzqh_dm + "]的上级区划代码["
						+ sjxzqh_dm + "]不存在！");
			} else if (this.isExistsXzqh(mbxzqh_dm, "", viewName)) {
				throw new Exception("新增的现区划代码[" + mbxzqh_dm + "]已存在！");
			}else if (this.isExistsYjyXzqh(mbxzqh_dm, viewName)&&Integer.parseInt(Common.getJcdm(mbxzqh_dm))<4&&!czry_dm.equals("00000000000")) {//只有村级可以使用禁用代码
				throw new Exception("新增的现区划代码[" + mbxzqh_dm + "]在系统中已经被使用过，不能重复使用！");
			}
		} else if (bglx_dm.equals(Common.CHANGE)) {
			if (!this.isExistsXzqh(ysxzqh_dm, "", viewName)) {
				throw new Exception("变更的原区划代码[" + ysxzqh_dm + "]不存在！");
			} else if (this.isExistsXzqh(mbxzqh_dm, "", viewName)) {
				if (!ysxzqh_dm.equals(mbxzqh_dm))
					throw new Exception("变更的现区划代码[" + mbxzqh_dm + "]已存在！");
				else if (ysxzqh_dm.equals(mbxzqh_dm)
						&& ysxzqh_mc.equals(mbxzqh_mc)) {
					throw new Exception("变更的原区划代码、名称和现区划代码、名称完全相同！");
				}else if(!Common.getJcdm(ysxzqh_dm).equals(Common.getJcdm(mbxzqh_dm))){
					throw new Exception("变更的原区划代码和现区划代码不是一个级别！");
				}
			}else if (ringFlag==false && this.isExistsYjyXzqh(mbxzqh_dm, viewName)&&Integer.parseInt(Common.getJcdm(mbxzqh_dm))<4&&!czry_dm.equals("00000000000")) {//非环状变更情况要考虑重复代码重用，环状情况不考虑
				throw new Exception("变更的现区划代码[" + mbxzqh_dm + "]在系统中已经被使用过，不能重复使用！");
			}
		} else if (bglx_dm.equals(Common.MERGE)) {
			if (!this.isExistsXzqh(ysxzqh_dm, "", viewName)) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm + "]不存在！");
			} else if (!this.isExistsXzqh(mbxzqh_dm, "", viewName)) {
				throw new Exception("并入的现行政区划代码[" + mbxzqh_dm + "]不存在！");
			} else if (ysxzqh_dm.equals(mbxzqh_dm)) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]相等！");
			} else if (!Common.getJcdm(ysxzqh_dm).equals(
					Common.getJcdm(mbxzqh_dm))) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]不是同级区划！");
			} 
//			else if (!Common.getSjxzqhdm(Common.getSjxzqhdm(ysxzqh_dm))
//					.equals(Common.getSjxzqhdm(Common.getSjxzqhdm(mbxzqh_dm)))) {
//				throw new Exception("并入的原区划代码[" + ysxzqh_dm + "]和现区划代码["
//						+ mbxzqh_dm + "]属于跨两级的并入，不符合业务规范！");
//			} 
			else if (this.getXjXzqh(ysxzqh_dm, viewName) != null) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm
						+ "]有下级区划不能并入，请先将下级区划进行迁移！");
			}
		} else if (bglx_dm.equals(Common.MOVE)) {
			String yssjxzqh_dm = Common.getSjxzqhdm(ysxzqh_dm);
			String mbsjxzqh_dm = Common.getSjxzqhdm(mbxzqh_dm);
			if (!this.isExistsXzqh(ysxzqh_dm, "", viewName)) {
				throw new Exception("迁移的原区划代码“" + ysxzqh_dm + "”不存在！");
			} else if (this.isExistsXzqh(mbxzqh_dm, "", viewName)) {
				throw new Exception("迁移的现区划代码“" + mbxzqh_dm + "”已存在！");
			} else if (!Common.getJcdm(ysxzqh_dm).equals(
					Common.getJcdm(mbxzqh_dm))) {
				throw new Exception("迁移的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]不是同级区划！");
			} else if (yssjxzqh_dm.equals(mbsjxzqh_dm)) {
				throw new Exception("迁移的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]的上级区划相同，必须跨级迁移！");
			}
//			else if (!Common
//					.getSjxzqhdm(Common.getSjxzqhdm(yssjxzqh_dm))
//					.equals(Common.getSjxzqhdm(Common.getSjxzqhdm(mbsjxzqh_dm)))) {
//				throw new Exception("迁移的原区划代码[" + ysxzqh_dm + "]和现区划代码["
//						+ mbxzqh_dm + "]不能跨两级以上区划迁移！");
//			}
			else if (this.isExistsYjyXzqh(mbxzqh_dm, viewName)&&Integer.parseInt(Common.getJcdm(mbxzqh_dm))<4&&!czry_dm.equals("00000000000")) {
				throw new Exception("迁移的现区划代码[" + mbxzqh_dm + "]在系统中已经被使用过，不能重复使用！");
			}
		} else {
			throw new Exception("区划变更类型代码[" + bglx_dm
					+ "]不是系统规定的[11 新增,21 变更,31 并入,41 迁移] 变更类型，无法完成变更！");
		}

	}
	
	/**
	 * 
	 * <p>
	 * 方法名称：
	 * </p>
	 * <p>
	 * 方法描述：逻辑校验所有变更类型
	 * </p>
	 * 
	 * @param yxzqhdm
	 * @param yxzqhmc
	 * @param bglx_dm
	 * @param mbxzqhdm
	 * @param mbxzqhmc
	 * @param tablename
	 * @param rinfFlag 是否是循环变更 ，如果为true是循环变更，不考虑重用代码情况，否则不是循环变更，要判断是否重用代码
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-11-3
	 */
	public void logicVerifyXzqh2(String ysxzqh_dm, String ysxzqh_mc,
			String bglx_dm, String mbxzqh_dm, String mbxzqh_mc, String tablename,boolean ringFlag, String czry_dm)
			throws Exception {
		String viewName = tablename != null
				&& tablename.toUpperCase().indexOf("V_") < 0 ? "V_" + tablename
				: tablename;
		if (bglx_dm.equals(Common.ADD)) {
			String sjxzqh_dm = Common.getSjxzqhdm(mbxzqh_dm);
			if (!this.isExistsXzqh(sjxzqh_dm, "", viewName)) {
				throw new Exception("新增的区划代码[" + mbxzqh_dm + "]的上级区划代码["
						+ sjxzqh_dm + "]不存在！");
			} else if (this.isExistsXzqh(mbxzqh_dm, "", viewName)) {
				throw new Exception("新增的现区划代码[" + mbxzqh_dm + "]已存在！");
			}else if (this.isExistsYjyXzqh(mbxzqh_dm, viewName)&&Integer.parseInt(Common.getJcdm(mbxzqh_dm))<4&&!czry_dm.equals("00000000000")) {//只有村级可以使用禁用代码
				throw new Exception("新增的现区划代码[" + mbxzqh_dm + "]在系统中已经被使用过，不能重复使用！");
			}
		} else if (bglx_dm.equals(Common.CHANGE)) {
			if (!this.isExistsXzqh(ysxzqh_dm, "", viewName)) {
				throw new Exception("变更的原区划代码[" + ysxzqh_dm + "]不存在！");
			} else if (this.isExistsXzqh(mbxzqh_dm, "", viewName)) {
				if (!ysxzqh_dm.equals(mbxzqh_dm))
					throw new Exception("变更的现区划代码[" + mbxzqh_dm + "]已存在！");
				else if (ysxzqh_dm.equals(mbxzqh_dm)
						&& ysxzqh_mc.equals(mbxzqh_mc)) {
					throw new Exception("变更的原区划代码、名称和现区划代码、名称完全相同！");
				}
			}else if (ringFlag==false && this.isExistsYjyXzqh(mbxzqh_dm, viewName)&&Integer.parseInt(Common.getJcdm(mbxzqh_dm))<4&&!czry_dm.equals("00000000000")) {//非环状变更情况要考虑重复代码重用，环状情况不考虑
				throw new Exception("变更的现区划代码[" + mbxzqh_dm + "]在系统中已经被使用过，不能重复使用！");
			}
		} else if (bglx_dm.equals(Common.MERGE)) {
			if (!this.isExistsXzqh(ysxzqh_dm, "", viewName)) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm + "]不存在！");
			} else if (!this.isExistsXzqh(mbxzqh_dm, "", viewName)) {
				throw new Exception("并入的现行政区划代码[" + mbxzqh_dm + "]不存在！");
			} else if (ysxzqh_dm.equals(mbxzqh_dm)) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]相等！");
			} else if (!Common.getJcdm(ysxzqh_dm).equals(
					Common.getJcdm(mbxzqh_dm))) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]不是同级区划！");
			} else if (!Common.getSjxzqhdm(Common.getSjxzqhdm(ysxzqh_dm))
					.equals(Common.getSjxzqhdm(Common.getSjxzqhdm(mbxzqh_dm)))) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]属于跨两级的并入，不符合业务规范！");
			} else if (this.getXjXzqh(ysxzqh_dm, viewName) != null) {
				throw new Exception("并入的原区划代码[" + ysxzqh_dm
						+ "]有下级区划不能并入，请先将下级区划进行迁移！");
			}
		} else if (bglx_dm.equals(Common.MOVE)) {
			String yssjxzqh_dm = Common.getSjxzqhdm(ysxzqh_dm);
			String mbsjxzqh_dm = Common.getSjxzqhdm(mbxzqh_dm);
			if (!this.isExistsXzqh(ysxzqh_dm, "", viewName)) {
				throw new Exception("迁移的原区划代码“" + ysxzqh_dm + "”不存在！");
			} else if (this.isExistsXzqh(mbxzqh_dm, "", viewName)) {
				throw new Exception("迁移的现区划代码“" + mbxzqh_dm + "”已存在！");
			} else if (!Common.getJcdm(ysxzqh_dm).equals(
					Common.getJcdm(mbxzqh_dm))) {
				throw new Exception("迁移的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]不是同级区划！");
			} else if (yssjxzqh_dm.equals(mbsjxzqh_dm)) {
				throw new Exception("迁移的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]的上级区划相同，必须跨级迁移！");
			} else if (!Common
					.getSjxzqhdm(Common.getSjxzqhdm(yssjxzqh_dm))
					.equals(Common.getSjxzqhdm(Common.getSjxzqhdm(mbsjxzqh_dm)))) {
				throw new Exception("迁移的原区划代码[" + ysxzqh_dm + "]和现区划代码["
						+ mbxzqh_dm + "]不能跨两级以上区划迁移！");
			}else if (this.isExistsYjyXzqh(mbxzqh_dm, viewName)&&Integer.parseInt(Common.getJcdm(mbxzqh_dm))<4&&!czry_dm.equals("00000000000")) {
				throw new Exception("迁移的现区划代码[" + mbxzqh_dm + "]在系统中已经被使用过，不能重复使用！");
			}
		} else {
			throw new Exception("区划变更类型代码[" + bglx_dm
					+ "]不是系统规定的[11 新增,21 变更,31 并入,41 迁移] 变更类型，无法完成变更！");
		}

	}

	/**
	 * 
	 * <p>
	 * 方法名称：
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @param xzqh_dm
	 * @param tableName
	 *            行政区划表的视图名称
	 * @return
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-11-3
	 */
	public boolean isExistsXzqh(String xzqh_dm, String xzqh_mc, String viewName)
			throws Exception {
		boolean flag = true;
		viewName = viewName != null && viewName.toUpperCase().indexOf("V_") < 0 ? "V_"
				+ viewName
				: viewName;
		StringBuffer sql = new StringBuffer("SELECT X.XZQH_DM,X.XZQH_MC FROM "
				+ viewName + " X WHERE X.XZQH_DM='");
		sql.append(xzqh_dm);
		sql.append("'");
		if (xzqh_mc != null && !xzqh_mc.equals("")) {
			sql.append(" AND X.XZQH_MC='");
			sql.append(xzqh_mc);
			sql.append("'");
		}
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		if (count < 1) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 
	 * <p>方法名称：是否存在已禁用的区划代码，也就是该区划代码曾经被使用过</p>
	 * <p>方法描述：是否存在已禁用的区划代码，也就是该区划代码曾经被使用过</p>
	 * @param xzqh_dm
	 * @param tableName
	 * @return
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-11-13
	 */
	public boolean isExistsYjyXzqh(String xzqh_dm, String tableName)
			throws Exception {
		boolean flag = true;
		tableName = tableName != null && tableName.toUpperCase().indexOf("V_") > -1 ? tableName.substring(2):tableName;
		StringBuffer sql = new StringBuffer("SELECT X.XZQH_DM,X.XZQH_MC FROM "
				+ tableName + " X WHERE X.YXBZ='N' and X.XZQH_DM='");
		sql.append(xzqh_dm);
		sql.append("'");		
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		if (count < 1) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * <p>
	 * 方法名称：获取下级区划
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @param forXzqh
	 * @param viewName
	 *            不同的视图 V_DM_XZQH_YLSJ 或者 V_DM_XZQH
	 * @return
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-11-3
	 */
	public XMLDataObject getXjXzqh(String forXzqh, String viewName)
			throws Exception {
		viewName = viewName != null && viewName.toUpperCase().indexOf("V_") < 0 ? "V_"
				+ viewName
				: viewName;
		String sql = "SELECT XZQH_DM,XZQH_MC FROM " + viewName
				+ " WHERE SJ_XZQH_DM='" + forXzqh + "' order by XZQH_DM";
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql);
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// 设置连接池
		long count = xzqhDw.retrieve();
		if (count > 0) {
			return xzqhDw.toXDO();
		} else {
			return null;
		}
	}
	
	public String getXzqhview(String dateStr) throws Exception{
		if(dateStr==null||dateStr.equals("")){
			return "";
		}
		StringBuffer sql = new StringBuffer("SELECT VIEWNAME FROM XZQH_VIEW WHERE ");
		sql.append("(YXQ_Q<=TO_DATE('").append(dateStr).append("','YYYY-MM-DD')");
		sql.append(" AND YXQ_Z>=TO_DATE('").append(dateStr).append("','YYYY-MM-DD'))");
		sql.append(" or (YXQ_Z>=TO_DATE('").append(dateStr).append("','YYYY-MM-DD') and YXQ_Q is null)");
		sql.append(" or (YXQ_Q<=TO_DATE('").append(dateStr).append("','YYYY-MM-DD') and YXQ_Z is null)");

		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		String viewName = "";
		if (count > 0) {
			viewName = StringEx.sNull(xzqhDw.getItemAny(0L, "VIEWNAME"));
		}
		return viewName;
    }
	
}
