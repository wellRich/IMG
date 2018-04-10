package com.padis.business.xzqhwh.lgywsjsc;


import java.util.ArrayList;
import java.util.List;

import com.padis.business.common.data.dm.D_dm_xzqh;
import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.delxzqh.DelxzqhBean;
import com.padis.business.xzqhwh.delxzqh.DelxzqhImp;
import com.padis.business.xzqhwh.delxzqh.JfDelXzqhImp;
import com.padis.business.xzqhwh.delxzqh.LgDelXzqhImp;

import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;
import com.padis.common.xzqhservice.XzqhInterfaceFactory;

import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;

/**
 * <p> 
 * Description: ��������ɾ�������ࣨDelxzqhService����manager,�ṩDelxzqhService�ײ㴦��ķ���
 * </p> 
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @since 2009-07-16
 * @author ���
 * @version 1.0
 */

public class LgywsjscManager {
	
	/**
	 * <p>
	 * �������ƣ�getShengOptions
	 * </p>
	 * <p>
	 * ����������ȡ��ʡ�����������б�
	 * </p>
	 * 
	 * @param forXzqh
	 *            ������Ӧ����������
	 * @param forDwlsgx
	 *            ��������Ӧ�ĵ�λ������ϵ
	 * @param initValue
	 *            ��ʼ��������������
	 * @param isInit
	 *            �Ƿ�Ҫ��ʼ��
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-14
	 */
	public String getShengOptions(String forXzqh, int forDwlsgx,
			String initValue, boolean isInit) {
		try {
			StringBuffer sbf = new StringBuffer();
			XMLDataObject xdo = getShengXdo();
			if ((initValue == null || initValue.equals("")) && !isInit) {
				forDwlsgx = 10;
			}
			if (forDwlsgx == 10) {
				for (int i = 0; i < xdo.getRowCount(); i++) {
					String xzqh_dm = xdo.getItemAny(i, "XZQH_DM").toString();
					sbf.append("<option value=\"").append(xzqh_dm).append("\">");
					sbf.append(xdo.getItemAny(i, "XZQH_MC").toString())
					.append(" ").append(xzqh_dm.substring(0, 2)).append("</option>");
				}
			} else if (forDwlsgx > 10) {
				String sheng = forXzqh.substring(0, 2) + "0000000000000";
				if (isInit) {
					sbf.append("<option value=\"").append(sheng).append("\">")
							.append(this.getXzqhMcByDm(sheng)).append(" ")
							.append(forXzqh.substring(0, 2));
					sbf.append("</option>");
				} else {
					if (initValue != null && !initValue.equals("")) {
						sheng = initValue.substring(0, 2) + "0000000000000";
					}
					for (int i = 0; i < xdo.getRowCount(); i++) {
						String xzqh_dm = xdo.getItemAny(i, "XZQH_DM")
								.toString();
						sbf.append("<option value=\"").append(
								xdo.getItemAny(i, "XZQH_DM").toString())
								.append("\"");
						if (sheng.equals(xzqh_dm)) {
							sbf.append("selected");
						}
						sbf.append(">").append(
								xdo.getItemAny(i, "XZQH_MC").toString())
								.append(" ").append(xzqh_dm.substring(0, 2))
								.append("</option>");
					}
				}
			}
			// 10���Ҽ���20ʡ����40�м���50�ؼ���60�缶��70�弶
			return sbf.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>
	 * �������ƣ�getShiOptions
	 * </p>
	 * <p>
	 * ����������ȡ���м����������б�
	 * </p>
	 * 
	 * @param forXzqh
	 *            ������Ӧ����������
	 * @param forDwlsgx
	 *            ��������Ӧ�ĵ�λ������ϵ
	 * @param initValue
	 *            ��ʼ��������������
	 * @param isInit
	 *            �Ƿ�Ҫ��ʼ��
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-14
	 */
	public String getShiOptions(String forXzqh, int forDwlsgx,
			String initValue, boolean isInit) {
		if ((initValue == null || initValue.equals("")) && !isInit) {
			return null;
		}
		try {
			StringBuffer sbf = new StringBuffer();
			String shi = forXzqh.substring(0, 4) + "00000000000";
			String sheng = forXzqh.substring(0, 2) + "0000000000000";
			if (forDwlsgx == 20) {
				if (initValue != null && !initValue.equals("")) {
					shi = initValue.substring(0, 4) + "00000000000";
					sheng = initValue.substring(0, 2) + "0000000000000";
				}
				XMLDataObject xdo = getXjXzqh(sheng);
				for (int i = 0; i < xdo.getRowCount(); i++) {
					String xzqh_dm = xdo.getItemAny(i, "XZQH_DM").toString();
					sbf.append("<option value=\"").append(
							xdo.getItemAny(i, "XZQH_DM").toString()).append(
							"\"");
					if (shi.equals(xzqh_dm)) {
						sbf.append("selected");
					}
					sbf.append(">").append(
							xdo.getItemAny(i, "XZQH_MC").toString())
							.append(" ").append(xzqh_dm.substring(2, 4))
							.append("</option>");
				}
			} else if (forDwlsgx > 20) {
				if (isInit) {
					sbf.append("<option value=\"").append(shi).append("\">")
							.append(this.getXzqhMcByDm(shi)).append(" ")
							.append(shi.substring(2, 4));
					sbf.append("</option>");
				} else {
					if (initValue != null && !initValue.equals("")) {
						shi = initValue.substring(0, 4) + "00000000000";
						sheng = initValue.substring(0, 2) + "0000000000000";
					}
					XMLDataObject xdo = getXjXzqh(sheng);
					for (int i = 0; i < xdo.getRowCount(); i++) {
						String xzqh_dm = xdo.getItemAny(i, "XZQH_DM")
								.toString();
						sbf.append("<option value=\"").append(xzqh_dm).append(
								"\"");
						if (shi.equals(xzqh_dm)) {
							sbf.append("selected");
						}
						sbf.append(">").append(
								xdo.getItemAny(i, "XZQH_MC").toString())
								.append(" ").append(xzqh_dm.substring(2, 4))
								.append("</option>");
					}
				}
			}
			return sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * �������ƣ�getXianOptions
	 * </p>
	 * <p>
	 * ����������ȡ���ؼ����������б�
	 * </p>
	 * 
	 * @param forXzqh
	 *            ������Ӧ����������
	 * @param forDwlsgx
	 *            ��������Ӧ�ĵ�λ������ϵ
	 * @param initValue
	 *            ��ʼ��������������
	 * @param isInit
	 *            �Ƿ�Ҫ��ʼ��
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-14
	 */
	public String getXianOptions(String forXzqh, int forDwlsgx,
			String initValue, boolean isInit) {
		if ((initValue == null || initValue.equals("")) && !isInit) {
			return null;
		}
		try {
			StringBuffer sbf = new StringBuffer();
			String shi = forXzqh.substring(0, 4) + "00000000000";
			String xian = forXzqh.substring(0, 6) + "000000000";
			if (forDwlsgx == 40) {
				if (initValue != null && !initValue.equals("")) {
					shi = initValue.substring(0, 4) + "00000000000";
					xian = initValue.substring(0, 6) + "000000000";
				}
				XMLDataObject xdo = getXjXzqh(shi);
				for (int i = 0; i < xdo.getRowCount(); i++) {
					String xzqh_dm = xdo.getItemAny(i, "XZQH_DM").toString();
					sbf.append("<option value=\"").append(xzqh_dm).append("\"");
					if (xian.equals(xdo.getItemAny(i, "XZQH_DM").toString())) {
						sbf.append("selected");
					}
					sbf.append(">").append(
							xdo.getItemAny(i, "XZQH_MC").toString())
							.append(" ").append(xzqh_dm.substring(4, 6))
							.append("</option>");
				}
			} else if (forDwlsgx > 40) {
				if (isInit) {
					sbf.append("<option value=\"").append(xian).append("\">")
							.append(this.getXzqhMcByDm(xian)).append(" ")
							.append(xian.substring(4, 6));
					sbf.append("</option>");
				} else {
					if (initValue != null && !initValue.equals("")) {
						shi = initValue.substring(0, 4) + "00000000000";
						xian = initValue.substring(0, 6) + "000000000";
					}
					XMLDataObject xdo = getXjXzqh(shi);
					for (int i = 0; i < xdo.getRowCount(); i++) {
						String xzqh_dm = xdo.getItemAny(i, "XZQH_DM")
								.toString();
						sbf.append("<option value=\"").append(xzqh_dm).append(
								"\"");
						if (xian.equals(xzqh_dm)) {
							sbf.append("selected");
						}
						sbf.append(">").append(
								xdo.getItemAny(i, "XZQH_MC").toString())
								.append(" ").append(xzqh_dm.substring(4, 6))
								.append("</option>");
					}
				}
			} else {
				if (initValue != null && !initValue.equals("")) {
					int jcdm = Integer.parseInt(getJcdmByXzqh(initValue));
					if (jcdm > 2) {
						shi = initValue.substring(0, 4) + "00000000000";
						xian = initValue.substring(0, 6) + "000000000";
						XMLDataObject xdo = getXjXzqh(shi);
						for (int i = 0; i < xdo.getRowCount(); i++) {
							String xzqh_dm = xdo.getItemAny(i, "XZQH_DM")
									.toString();
							sbf.append("<option value=\"").append(xzqh_dm)
									.append("\"");
							;
							if (xian.equals(xzqh_dm)) {
								sbf.append("selected");
							}
							sbf.append(">").append(
									xdo.getItemAny(i, "XZQH_MC").toString())
									.append(" ")
									.append(xzqh_dm.substring(4, 6)).append(
											"</option>");
						}
					}
				}
			}
			return sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * �������ƣ�getXiangOptions
	 * </p>
	 * <p>
	 * ����������ȡ���缶���������б�
	 * </p>
	 * 
	 * @param forXzqh
	 *            ������Ӧ����������
	 * @param forDwlsgx
	 *            ��������Ӧ�ĵ�λ������ϵ
	 * @param initValue
	 *            ��ʼ��������������
	 * @param isInit
	 *            �Ƿ�Ҫ��ʼ��
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-14
	 */
	public String getXiangOptions(String forXzqh, int forDwlsgx,
			String initValue, boolean isInit) {
		if ((initValue == null || initValue.equals("")) && !isInit) {
			return null;
		}
		try {
			if (initValue != null && !initValue.equals("") && isInit) {
				if (initValue.indexOf(forXzqh.substring(0, 6)) == -1) {
					initValue = null;
				}
			}
			StringBuffer sbf = new StringBuffer();
			String xian = forXzqh.substring(0, 6) + "000000000";
			String xiang = forXzqh.substring(0, 9) + "000000";
			if (forDwlsgx == 50) {
				if (initValue != null && !initValue.equals("")) {
					xian = initValue.substring(0, 6) + "000000000";
					xiang = initValue.substring(0, 9) + "000000";
				}
				XMLDataObject xdo = getXjXzqh(xian);
				for (int i = 0; i < xdo.getRowCount(); i++) {
					String xzqh_dm = xdo.getItemAny(i, "XZQH_DM").toString();
					sbf.append("<option value=\"").append(xzqh_dm).append("\"");
					if (xiang.equals(xzqh_dm)) {
						sbf.append("selected");
					}
					sbf.append(">").append(
							xdo.getItemAny(i, "XZQH_MC").toString())
							.append(" ").append(xzqh_dm.substring(6, 9))
							.append("</option>");
				}
			} else if (forDwlsgx > 50) {
				if (isInit) {
					sbf.append("<option value=\"").append(xiang).append("\">")
							.append(this.getXzqhMcByDm(xiang)).append(" ")
							.append(xiang.substring(6, 9));
					sbf.append("</option>");
				} else {
					if (initValue != null && !initValue.equals("")) {
						xian = initValue.substring(0, 6) + "000000000";
						xiang = initValue.substring(0, 9) + "000000";
					}
					XMLDataObject xdo = getXjXzqh(xian);
					for (int i = 0; i < xdo.getRowCount(); i++) {
						String xzqh_dm = xdo.getItemAny(i, "XZQH_DM")
								.toString();
						sbf.append("<option value=\"").append(xzqh_dm).append(
								"\"");
						;
						if (xiang.equals(xzqh_dm)) {
							sbf.append("selected");
						}
						sbf.append(">").append(
								xdo.getItemAny(i, "XZQH_MC").toString())
								.append(" ").append(xzqh_dm.substring(6, 9))
								.append("</option>");
					}
				}
			} else {
				if (initValue != null && !initValue.equals("")) {
					int jcdm = Integer.parseInt(getJcdmByXzqh(initValue));
					if (jcdm >= 3) {
						xian = initValue.substring(0, 6) + "000000000";
						xiang = initValue.substring(0, 9) + "000000";
						XMLDataObject xdo = getXjXzqh(xian);
						for (int i = 0; i < xdo.getRowCount(); i++) {
							String xzqh_dm = xdo.getItemAny(i, "XZQH_DM")
									.toString();
							sbf.append("<option value=\"").append(xzqh_dm)
									.append("\"");
							;
							if (xiang.equals(xzqh_dm)) {
								sbf.append("selected");
							}
							sbf.append(">").append(
									xdo.getItemAny(i, "XZQH_MC").toString())
									.append(" ")
									.append(xzqh_dm.substring(6, 9)).append(
											"</option>");
						}
					}
				}
			}
			return sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * �������ƣ�getCunOptions
	 * </p>
	 * <p>
	 * ����������ȡ�ô弶���������б�
	 * </p>
	 * 
	 * @param forXzqh
	 *            ������Ӧ����������
	 * @param forDwlsgx
	 *            ��������Ӧ�ĵ�λ������ϵ
	 * @param initValue
	 *            ��ʼ��������������
	 * @param isInit
	 *            �Ƿ�Ҫ��ʼ��
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-14
	 */
	public String getCunOptions(String forXzqh, int forDwlsgx,
			String initValue, boolean isInit) throws Exception {
		if ((initValue == null || initValue.equals("")) && !isInit) {
			return null;
		}
		try {
			if (initValue != null && !initValue.equals("") && isInit) {
				if (initValue.indexOf(forXzqh.substring(0, 6)) == -1) {
					return null;
				} else {
					if (initValue.indexOf(forXzqh.substring(0, 9)) == -1) {
						initValue = null;
					}
				}
			}
			StringBuffer sbf = new StringBuffer();
			String xiang = forXzqh.substring(0, 9) + "000000";
			String cun = forXzqh;
			if (forDwlsgx >= 60) {
				if (initValue != null && !initValue.equals("")) {
					xiang = initValue.substring(0, 9) + "000000";
					cun = initValue;
				}
				XMLDataObject xdo = getXjXzqh(xiang);
				for (int i = 0; i < xdo.getRowCount(); i++) {
					String xzqh_dm = xdo.getItemAny(i, "XZQH_DM").toString();
					sbf.append("<option value=\"").append(xzqh_dm).append("\"");
					if (cun.equals(xzqh_dm)) {
						sbf.append("selected");
					}
					sbf.append(">").append(
							xdo.getItemAny(i, "XZQH_MC").toString())
							.append(" ").append(xzqh_dm.substring(9, 12))
							.append("</option>");
				}
			} else {
				if (initValue != null && !initValue.equals("")) {
					int jcdm = Integer.parseInt(getJcdmByXzqh(initValue));
					if (jcdm >= 4) {
						xiang = initValue.substring(0, 9) + "000000";
						cun = initValue;
						XMLDataObject xdo = getXjXzqh(xiang);
						for (int i = 0; i < xdo.getRowCount(); i++) {
							String xzqh_dm = xdo.getItemAny(i, "XZQH_DM")
									.toString();
							sbf.append("<option value=\"").append(xzqh_dm)
									.append("\"");
							if (cun.equals(xzqh_dm)) {
								sbf.append("selected");
							}
							sbf.append(">").append(
									xdo.getItemAny(i, "XZQH_MC").toString())
									.append(xzqh_dm.substring(9, 12)).append(
											"</option>");
						}
					}
				}
			}
			return sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * �������ƣ�getCunOptions
	 * </p>
	 * <p>
	 * ����������ȡ�ô弶���������б�
	 * </p>
	 * 
	 * @param forXzqh
	 *            ������Ӧ����������
	 * @param forDwlsgx
	 *            ��������Ӧ�ĵ�λ������ϵ
	 * @param initValue
	 *            ��ʼ��������������
	 * @param isInit
	 *            �Ƿ�Ҫ��ʼ��
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-14
	 */
	public String getZuOptions(String forXzqh, int forDwlsgx,
			String initValue, boolean isInit) throws Exception {
		if ((initValue == null || initValue.equals("")) && !isInit) {
			return null;
		}
		try {
			if (initValue != null && !initValue.equals("") && isInit) {
				if (initValue.indexOf(forXzqh.substring(0, 6)) == -1) {
					return null;
				} else if (initValue.indexOf(forXzqh.substring(0, 9)) == -1) {
					initValue = null;
				}else if (initValue.indexOf(forXzqh.substring(0, 12)) == -1) {
					initValue = null;
				}
				
			}
			StringBuffer sbf = new StringBuffer();
			String cun = forXzqh.substring(0, 12) + "000";
			String zu = forXzqh;
			if (forDwlsgx >= 70) {
				if (initValue != null && !initValue.equals("")) {
					cun = initValue.substring(0, 12) + "000";
					zu = initValue;
				}
				XMLDataObject xdo = getXjXzqh(cun);
				for (int i = 0; i < xdo.getRowCount(); i++) {
					String xzqh_dm = xdo.getItemAny(i, "XZQH_DM").toString();
					sbf.append("<option value=\"").append(xzqh_dm).append("\"");
					if (zu.equals(xzqh_dm)) {
						sbf.append("selected");
					}
					sbf.append(">").append(
							xdo.getItemAny(i, "XZQH_MC").toString())
							.append(" ").append(xzqh_dm.substring(12, 15))
							.append("</option>");
				}
			} else {
				if (initValue != null && !initValue.equals("")) {
					int jcdm = Integer.parseInt(getJcdmByXzqh(initValue));
					if (jcdm >= 5) {
						cun = initValue.substring(0, 12) + "000";
						zu = initValue;
						XMLDataObject xdo = getXjXzqh(cun);
						for (int i = 0; i < xdo.getRowCount(); i++) {
							String xzqh_dm = xdo.getItemAny(i, "XZQH_DM").toString();
							sbf.append("<option value=\"").append(xzqh_dm).append("\"");
							if (zu.equals(xzqh_dm)) {
								sbf.append("selected");
							}
							sbf.append(">").append(
									xdo.getItemAny(i, "XZQH_MC").toString())
									.append(xzqh_dm.substring(12, 15)).append(
											"</option>");
						}
					}
				}
			}
			return sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * <p>�������ƣ�getXjXzqh()</p>
	 * <p>������������ȡ�¼�����������Ϣ</p>
	 * @param forXzqh ������������
	 * @return	XMLDataObject����
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-16
	 */
	public XMLDataObject getXjXzqh(String forXzqh) throws Exception {
		String sql = "SELECT XZQH_DM,XZQH_MC FROM V_DM_XZQH WHERE SJ_XZQH_DM='"
				+ forXzqh + "'";
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql);
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// �������ӳ�
		long count = xzqhDw.retrieve();
		if (count > 0) {
			return xzqhDw.toXDO();
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * <p>�������ƣ�getJcdmByXzqh()</p>
	 * <p>����������ͨ�����δ�����������������</p>
	 * @param forXzqh ������������
	 * @return	������������
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-16
	 */
	public String getJcdmByXzqh(String forXzqh) throws Exception {
		return XzqhInterfaceFactory.getInstance().getInterfaceImp().getJcdm(
				forXzqh);
	}
	/**
	 * 
	 * <p>�������ƣ�getShengXdo()</p>
	 * <p>�������������ʡ������������</p>
	 * @return XMLDataObject����
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-16
	 */
	public XMLDataObject getShengXdo() throws Exception {
		String sql = "SELECT XZQH_DM,XZQH_MC FROM V_DM_XZQH WHERE JCDM='1' ORDER BY XZQH_DM";
		DataWindow shengDw = DataWindow.dynamicCreate(sql, true);
		shengDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// �������ӳ�
		shengDw.retrieve();
		return shengDw.toXDO();
	}
	
	/**
	 * 
	 * <p>�������ƣ�getXzqhMcByDm()</p>
	 * <p>����������ͨ���������������ȡ����</p>
	 * @param xzqh_dm ������������
	 * @return ������������
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-16
	 */
	public String getXzqhMcByDm(String xzqh_dm) throws Exception {
		String sql = "SELECT XZQH_DM,XZQH_MC FROM V_DM_XZQH WHERE XZQH_DM='"
				+ xzqh_dm + "'";
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql);
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// �������ӳ�
		long count = xzqhDw.retrieve();
		String xzqh_mc = "";
		if (count > 0) {
			xzqh_mc = (String) xzqhDw.getItemAny(0, "XZQH_MC");
		}
		return xzqh_mc;
	}

	/**
	 * 
	 * <p>
	 * �������ƣ�getXjXzqh()
	 * </p>
	 * <p>
	 * ������������ʼ�����������б��е�ʡ��������������
	 * </p>
	 * 
	 * @param forSjXzqh
	 *            ������������
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-14
	 */
	public String getXjXzqhdm(String forSjXzqh, String fhjd) throws Exception {

		String resultXml = "";
		String sql = "SELECT XZQH_DM ID,XZQH_MC NAME FROM V_DM_XZQH WHERE SJ_XZQH_DM='"
				+ forSjXzqh + "' ORDER BY XZQH_DM";
		DataWindow xx = DataWindow.dynamicCreate(sql, true);
		xx.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// �������ӳ�
		long num = xx.retrieve();
		if (num != 0L) {
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			for (int i = 0; i < num; i++) {
				String id = (String) xx.getItemAny(i, "ID");
				String name = (String) xx.getItemAny(i, "NAME");
				xsBuf.appendHead("ITEM");
				xsBuf.append("ID", id);
				if (fhjd.equals("XZQH_SHENG")) {
					xsBuf.append("NAME", (name + " " + id.substring(0, 2)));
				}
				if (fhjd.equals("XZQH_SHI")) {
					xsBuf.append("NAME", (name + " " + id.substring(2, 4)));
				}
				if (fhjd.equals("XZQH_XIAN")) {
					xsBuf.append("NAME", (name + " " + id.substring(4, 6)));
				}
				if (fhjd.equals("XZQH_XIANG")) {
					xsBuf.append("NAME", (name + " " + id.substring(6, 9)));
				}
				if (fhjd.equals("XZQH_CUN")) {
					xsBuf.append("NAME", (name + " " + id.substring(9, 12)));
				}
				if (fhjd.equals("XZQH_ZU")) {
					xsBuf.append("NAME", (name + " " + id.substring(12, 15)));
				}
				xsBuf.appendTail("ITEM");
			}
			resultXml = xsBuf.toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>�������ƣ�deleteMxb()��ɾ����ϸ������</p>
	 * <p>����˵����ɾ����ϸ�����ݵķ���������������ӱ�XT_XZQHBGMXB��ɾ��������¼��</p>
	 * @param mxbxhs
	 *             ��ϸ�����
	 * @since 2009-07-16
	 * @author ���
	 * @return ��
	 * @exception Exception
	 */
	public List deleteXzqh(String xzqh_dm) throws Exception{
		UserTransaction ut = new UserTransaction();
		String jbdm = Common.getJbdm(xzqh_dm);
		StringBuffer sql = new StringBuffer("SELECT X.XZQH_DM FROM V_DM_XZQH X WHERE X.JBDM LIKE '");
		sql.append(jbdm);
		sql.append("%'");
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rowCount = xzqhDw.retrieve();
		List xzqhList = new ArrayList();
		boolean flag = true;
		if(rowCount<=0){
			return null;
		}
		for(long i=(rowCount-1); i>=0; i--) {
			String xzqhdm = StringEx.sNull(xzqhDw.getItemAny(i, "XZQH_DM"));
			xzqhList.add(xzqhdm);
		}
		List allMessage = new ArrayList();
		//�鿴�����˿ڹ����ܷ�ɾ��
		DelxzqhImp lgImp = new LgDelXzqhImp();
		boolean tmpflag = lgImp.isAllowDelXzqh(xzqhList);
		if(tmpflag==false){
			allMessage.addAll(lgImp.getReturnMessage());				
		}
		flag = flag & tmpflag;
		try {			
			
			if(flag){
				ut.begin();
				if(rowCount>0){
					for(long i=(rowCount-1); i>=0; i--) {
						String xzqhdm = StringEx.sNull(xzqhDw.getItemAny(i, "XZQH_DM"));
						//�ڴ˵���ҵ��ӿ�					
						D_dm_xzqh dw = new D_dm_xzqh();
						dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
						StringBuffer sql1 = new StringBuffer(
								"XYBZ = 'Y' AND YXBZ = 'Y' and XZQH_DM='");
						sql1.append(xzqhdm);
						sql1.append("'");
						dw.setFilter(sql1.toString());
						long cnt = dw.retrieve();
						if(cnt>0){
							dw.setItemString(0L, "YXQ_Z", XtDate.getDBTime());
							dw.setItemString(0L, "XYBZ", "N");
							dw.setItemString(0L, "YXBZ", "N");
							dw.setItemString(0L, "XGSJ", XtDate.getDBTime());
							dw.setTransObject(ut);
							dw.update(false);
						}
					}
				}
				ut.commit();
			}	
		} catch (Exception e) {
			ut.rollback();
			throw e;
		}
		return allMessage;
	}
	
	/**
	 * <p>�������ƣ�messageXml()��ɾ����ϸ������</p>
	 * <p>����˵����ɾ����ϸ�����ݵķ���������������ӱ�XT_XZQHBGMXB��ɾ��������¼��</p>
	 * @param mxbxhs
	 *             ��ϸ�����
	 * @since 2009-07-16
	 * @author ���
	 * @return ��
	 * @exception Exception
	 */
	public String messageXml(List massageList) throws Exception{
		if(massageList==null||massageList.size()<=0){
			return "";
		}
		StringBuffer strsb = new StringBuffer();
		for(int i=0;i<massageList.size();i++){
			DelxzqhBean bean = (DelxzqhBean)massageList.get(i);
			String ywxtdm = bean.getYwxtdm();
			strsb.append("<ITEM>");
			strsb.append("<XZQHDM>");
			strsb.append(bean.getXzqhdm());		
			strsb.append("</XZQHDM>");
			strsb.append("<YWBMC>");
			strsb.append(bean.getYwbmc());		
			strsb.append("</YWBMC>");
			strsb.append("<YWXTDM>");
			if(ywxtdm.equals("J")){
				strsb.append("��������");
			}else if(ywxtdm.equals("T")){
				strsb.append("�ر����");
			}else if(ywxtdm.equals("S")){
				strsb.append("�����츻");
			}else if(ywxtdm.equals("L")){
				strsb.append("���举Ů�����˿�");
			}else if(ywxtdm.equals("K")){
				strsb.append("���ٵ���");
			}else if(ywxtdm.equals("Z")){
				strsb.append("��ҵͳ��");
			}
			strsb.append("</YWXTDM>");
			strsb.append("<SJL>");
			strsb.append(bean.getSjl());
			strsb.append("</SJL>");
			strsb.append("<YWBXZQHMC>");
			strsb.append(bean.getYwbxzqhmc());
			strsb.append("</YWBXZQHMC>");
			strsb.append("<BZ>");
			strsb.append(bean.getBz());
			strsb.append("</BZ>");
			strsb.append("</ITEM>");
		}
		return strsb.toString();
	}
	
	public String queryGaxx(XMLDataObject forXdo, long[] args, String xzqh_dm)
			throws Exception {
		String forRet = "";
		String queryCase = "select a.rkdzdah,a.xm,a.sfzjhm,a.hjdxxdz,a.xjzdxxdz from "
				+ " lg_jbxx a where a.hjdxzqh_dm  = '" + xzqh_dm + "'"
				+ " or a.xjzdxzqh_dm ='" + xzqh_dm + "'"
				+ " or a.zfhjdxzqh_dm = '" + xzqh_dm + "'"
				+ " or a.zfxjdxzqh_dm = '" + xzqh_dm + "'";
		DataWindow xx = DataWindow.dynamicCreate(queryCase, true);
		xx.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// �������ӳ�
		long num = xx.retrieve();
		if(num != 0){
			forRet = xx.toXML().toString();
		}
		return forRet.replaceAll("<row>", "<ITEM>").replaceAll("</row>",
				"</ITEM>").replaceAll("<rows>", "").replaceAll("</rows>", "");
	}

	public void deleteGaxx(XMLDataObject xmldo) throws Exception {
		String gaxxList = xmldo.getItemValue("RKDZDAH_LIST");
		UserTransaction ut = new UserTransaction() ;	
		try {
			ut.begin() ;
				bakGaxx(gaxxList,ut);
				delGaxx(gaxxList,ut);
			ut.commit() ;
		} catch (Exception e) {
			ut.rollback();
			e.printStackTrace();
		}
	}

	private void delGaxx(String gaxxList,UserTransaction ut) {
		List sqllist = new ArrayList();
		StringBuffer sb1 = new StringBuffer(); 
		String gaxx_list = "'" + gaxxList.replaceAll(",", "','" ) + "'" ;
		sb1.append( " delete from lg_jbxx a ") ;
		sb1.append( " where exists (select 1  from lg_yunwei b ");
		sb1.append( " where a.rkdzdah = b.rkdzdah and b.rkdzdah in ("+gaxx_list+"))") ;
		sqllist.add(sb1);
		
		StringBuffer sb2 = new StringBuffer(); 
		sb2.append( " delete from lg_lddj a ") ;
		sb2.append( " where exists (select 1  from lg_yunwei b ");
		sb2.append( " where a.rkdzdah = b.rkdzdah and b.rkdzdah in ("+gaxx_list+"))") ;
		sqllist.add(sb2);
		
		StringBuffer sb3 = new StringBuffer(); 
		sb3.append( " delete from lg_znxx a ") ;
		sb3.append( " where exists (select 1  from lg_yunwei b ");
		sb3.append( " where a.rkdzdah = b.rkdzdah and b.rkdzdah in ("+gaxx_list+"))") ;
		sqllist.add(sb3);

		StringBuffer sb4 = new StringBuffer(); 
		sb4.append( " delete from lg_rsxx a ") ;
		sb4.append( " where exists (select 1  from lg_yunwei b ");
		sb4.append( " where a.rkdzdah = b.rkdzdah and b.rkdzdah in ("+gaxx_list+"))") ;
		sqllist.add(sb4);

		StringBuffer sb5 = new StringBuffer(); 
		sb5.append( " delete from lg_syqk a ") ;
		sb5.append( " where exists (select 1  from lg_yunwei b ");
		sb5.append( " where a.rkdzdah = b.rkdzdah and b.rkdzdah in ("+gaxx_list+"))") ;
		sqllist.add(sb5);

		StringBuffer sb6 = new StringBuffer(); 
		sb6.append( " delete from lg_shfyfzs a ") ;
		sb6.append( " where exists (select 1  from lg_yunwei b ");
		sb6.append( " where a.rkdzdah = b.rkdzdah and b.rkdzdah in ("+gaxx_list+"))") ;
		sqllist.add(sb6);

		StringBuffer sb7 = new StringBuffer(); 
		sb7.append( " delete from lg_ssfw a ") ;
		sb7.append( " where exists (select 1  from lg_yunwei b ");
		sb7.append( " where a.rkdzdah = b.rkdzdah and b.rkdzdah in ("+gaxx_list+"))") ;
		sqllist.add(sb7);

		StringBuffer sb8 = new StringBuffer(); 
		sb8.append( " delete from lg_scxx a ") ;
		sb8.append( " where exists (select 1  from lg_yunwei b ");
		sb8.append( " where a.rkdzdah = b.rkdzdah and b.rkdzdah in ("+gaxx_list+"))") ;
		sqllist.add(sb8);

		StringBuffer sb9 = new StringBuffer(); 
		sb9.append( " delete from lg_xcfk a ") ;
		sb9.append( " where exists (select 1  from lg_yunwei b ");
		sb9.append( " where a.rkdzdah = b.rkdzdah and b.rkdzdah in ("+gaxx_list+"))") ;
		sqllist.add(sb9);

		StringBuffer sb10 = new StringBuffer(); 
		sb10.append( " delete from lg_tbfk a ") ;
		sb10.append( " where exists (select 1  from lg_yunwei b ");
		sb10.append( " where a.rkdzdah = b.rkdzdah and b.rkdzdah in ("+gaxx_list+"))") ;
		sqllist.add(sb10);
		
		StringBuffer sb11 = new StringBuffer(); 
		sb11.append( " delete message_extension where message_id in(select id from lg_yunwei_message) ") ;
		sqllist.add(sb11);
		
		StringBuffer sb12 = new StringBuffer(); 
		sb12.append( " delete message where id in (select id from lg_yunwei_message) ") ;
		sqllist.add(sb12);
		
		try {
			for(int i = 0 ; i< sqllist.size();i++){
				DataWindow	dwZn = DataWindow.dynamicCreate(sqllist.get(i).toString());
				dwZn.setTransObject( ut );
				dwZn.update(false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void bakGaxx(String gaxxList,UserTransaction ut) {
		List sqllist = new ArrayList();
		StringBuffer sb1 = new StringBuffer(); 
		String gaxx_list = "'" + gaxxList.replaceAll(",", "','" ) + "'" ;
		sb1.append( " insert into lg_yunwei a " ) ;
		sb1.append( " select b.xm,b.sfzjhm,b.rkdzdah,'Y',sysdate,'' " ) ;
		sb1.append( " from lg_jbxx b where  " ) ;
		sb1.append( " b.rkdzdah in ("+gaxx_list+") " ) ;
		sqllist.add(sb1);
		//����lg_jbxx
		StringBuffer sb2 = new StringBuffer(); 
		sb2.append( " insert into lg_yunwei_jbxx ") ;
		sb2.append( " select a.* from lg_jbxx a,lg_yunwei b ") ;
		sb2.append(	" where a.rkdzdah =b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb2);
		//����lg_lddj
		StringBuffer sb3 = new StringBuffer(); 
		sb3.append( " insert into lg_yunwei_lddj ") ;
		sb3.append( " select a.* from lg_lddj a,lg_yunwei b ") ;
		sb3.append(	" where a.rkdzdah =b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb3);
		//����lg_znxx
		StringBuffer sb4 = new StringBuffer(); 
		sb4.append( " insert into lg_yunwei_znxx ") ;
		sb4.append( " select a.* from lg_znxx a,lg_yunwei b ") ;
		sb4.append(	" where a.rkdzdah =b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb4);
		//����lg_rsxx
		StringBuffer sb5 = new StringBuffer(); 
		sb5.append( " insert into lg_yunwei_rsxx ") ;
		sb5.append( " select a.* from lg_rsxx a,lg_yunwei b ") ;
		sb5.append(	" where a.rkdzdah =b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb5);
		//����lg_syqk
		StringBuffer sb6 = new StringBuffer(); 
		sb6.append( " insert into lg_yunwei_syqk ") ;
		sb6.append( " select a.* from lg_syqk a,lg_yunwei b ") ;
		sb6.append(	" where a.rkdzdah =b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb6);
		//����lg_shfyfzs
		StringBuffer sb7 = new StringBuffer(); 
		sb7.append( " insert into lg_yunwei_shfyfzs ") ;
		sb7.append( " select a.* from lg_shfyfzs a,lg_yunwei b ") ;
		sb7.append(	" where a.rkdzdah =b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb7);
		//����lg_ssfw
		StringBuffer sb8 = new StringBuffer(); 
		sb8.append( " insert into lg_yunwei_ssfw ") ;
		sb8.append( " select a.* from lg_ssfw a,lg_yunwei b ") ;
		sb8.append(	" where a.rkdzdah =b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb8);
		//����lg_scxx
		StringBuffer sb9 = new StringBuffer(); 
		sb9.append( " insert into lg_yunwei_scxx ") ;
		sb9.append( " select a.* from lg_scxx a,lg_yunwei b ") ;
		sb9.append(	" where a.rkdzdah =b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb9);
		//����lg_xcfk
		StringBuffer sb10 = new StringBuffer(); 
		sb10.append( " insert into lg_yunwei_xcfk ") ;
		sb10.append( " select a.* from lg_xcfk a,lg_yunwei b ") ;
		sb10.append(	" where a.rkdzdah =b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb10);
		//����lg_tbfk
		StringBuffer sb11 = new StringBuffer(); 
		sb11.append( " insert into lg_yunwei_tbfk ") ;
		sb11.append( " select a.* from lg_tbfk a,lg_yunwei b ") ;
		sb11.append(	" where a.rkdzdah =b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb11);
		//����message
		StringBuffer sb12 = new StringBuffer(); 
		sb12.append( " insert into lg_yunwei_message ") ;
		sb12.append( " select a.* from message a ,lg_yunwei b ") ;
		sb12.append(	" where SUBSTR(a.TOPIC_URL,100,20) = b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb12);
		//����message
		StringBuffer sb13 = new StringBuffer(); 
		sb13.append( " insert into lg_yunwei_message ") ;
		sb13.append( " select a.* from message a ,lg_yunwei b ") ;
		sb13.append(	" where SUBSTR(a.TOPIC_URL,88,20) = b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb13);
		//����message
		StringBuffer sb14 = new StringBuffer(); 
		sb14.append( " insert into lg_yunwei_message ") ;
		sb14.append( " select a.* from message a ,lg_yunwei b ") ;
		sb14.append(	" where SUBSTR(a.TOPIC_URL,103,20) = b.rkdzdah and b.rkdzdah in ("+gaxx_list+")") ;
		sqllist.add(sb14);
			
		try {
			for(int i = 0 ; i< sqllist.size();i++){
				DataWindow	dwZn = DataWindow.dynamicCreate(sqllist.get(i).toString());
				dwZn.setTransObject( ut );
				dwZn.update(false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
