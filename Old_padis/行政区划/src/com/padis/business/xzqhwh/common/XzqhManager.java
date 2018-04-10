/**
 * 
 */
package com.padis.business.xzqhwh.common;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.xml.XMLDataObject;
import ctais.util.XmlStringBuffer;

/**
 * <p>Title: XzqhManager.java </p>
 * <p>Description:<��Ĺ�������> </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-9-24
 * @author pengld
 * @version 1.0
 * �޸���ʷ�� 
 * �޸���
 * �޸�ʱ��(yyyy/mm/dd)
 * �޸�����
 * �汾��
 */
public class XzqhManager {
	/*
	 * ������Ҫ�����ı�
	 */
	private String db = "V_DM_XZQH";
	
	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

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
		String sql = "SELECT XZQH_DM ID,XZQH_MC NAME FROM "+getDb()+" WHERE SJ_XZQH_DM='"
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
	 * <p>�������ƣ�getXjXzqh()</p>
	 * <p>������������ȡ�¼�����������Ϣ</p>
	 * @param forXzqh ������������
	 * @return	XMLDataObject����
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-16
	 */
	public  XMLDataObject getXjXzqh(String forXzqh) throws Exception {
		String sql = "SELECT XZQH_DM,XZQH_MC FROM "+getDb()+" WHERE SJ_XZQH_DM='"
				+ forXzqh + "' order by XZQH_DM";
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
		String sql = "SELECT XZQH_DM,XZQH_MC FROM "+getDb()+" WHERE XZQH_DM='"+forXzqh+"'";
		DataWindow dw = DataWindow.dynamicCreate(sql, true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// �������ӳ�
		long count = dw.retrieve();
		String jc_dm = "";
		if(count>0){
			jc_dm = StringEx.sNull(dw.getItemAny(0L, "JCDM"));
		}
		return jc_dm;
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
	private XMLDataObject getShengXdo() throws Exception {
		String sql = "SELECT XZQH_DM,XZQH_MC FROM "+getDb()+" WHERE JCDM='1' ORDER BY XZQH_DM";
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
	private String getXzqhMcByDm(String xzqh_dm) throws Exception {
		String sql = "SELECT XZQH_DM,XZQH_MC FROM "+getDb()+" WHERE XZQH_DM='"
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
	
	

}
