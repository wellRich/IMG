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
 * <p>Description:<类的功能描述> </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-9-24
 * @author pengld
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */
public class XzqhManager {
	/*
	 * 设置所要操作的表
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
	 * 方法名称：getShengOptions
	 * </p>
	 * <p>
	 * 方法描述：取得省级行政区划列表
	 * </p>
	 * 
	 * @param forXzqh
	 *            机构对应的行政区划
	 * @param forDwlsgx
	 *            机构所对应的单位隶属关系
	 * @param initValue
	 *            初始的行政区划代码
	 * @param isInit
	 *            是否要初始化
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
			// 10国家级、20省级、40市级、50县级、60乡级、70村级
			return sbf.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>
	 * 方法名称：getShiOptions
	 * </p>
	 * <p>
	 * 方法描述：取得市级行政区划列表
	 * </p>
	 * 
	 * @param forXzqh
	 *            机构对应的行政区划
	 * @param forDwlsgx
	 *            机构所对应的单位隶属关系
	 * @param initValue
	 *            初始的行政区划代码
	 * @param isInit
	 *            是否要初始化
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
	 * 方法名称：getXianOptions
	 * </p>
	 * <p>
	 * 方法描述：取得县级行政区划列表
	 * </p>
	 * 
	 * @param forXzqh
	 *            机构对应的行政区划
	 * @param forDwlsgx
	 *            机构所对应的单位隶属关系
	 * @param initValue
	 *            初始的行政区划代码
	 * @param isInit
	 *            是否要初始化
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
	 * 方法名称：getXiangOptions
	 * </p>
	 * <p>
	 * 方法描述：取得乡级行政区划列表
	 * </p>
	 * 
	 * @param forXzqh
	 *            机构对应的行政区划
	 * @param forDwlsgx
	 *            机构所对应的单位隶属关系
	 * @param initValue
	 *            初始的行政区划代码
	 * @param isInit
	 *            是否要初始化
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
	 * 方法名称：getCunOptions
	 * </p>
	 * <p>
	 * 方法描述：取得村级行政区划列表
	 * </p>
	 * 
	 * @param forXzqh
	 *            机构对应的行政区划
	 * @param forDwlsgx
	 *            机构所对应的单位隶属关系
	 * @param initValue
	 *            初始的行政区划代码
	 * @param isInit
	 *            是否要初始化
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
	 * 方法名称：getCunOptions
	 * </p>
	 * <p>
	 * 方法描述：取得村级行政区划列表
	 * </p>
	 * 
	 * @param forXzqh
	 *            机构对应的行政区划
	 * @param forDwlsgx
	 *            机构所对应的单位隶属关系
	 * @param initValue
	 *            初始的行政区划代码
	 * @param isInit
	 *            是否要初始化
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
	 * 方法名称：getXjXzqh()
	 * </p>
	 * <p>
	 * 方法描述：初始化联动下拉列表中的省级以下行政区划
	 * </p>
	 * 
	 * @param forSjXzqh
	 *            行政区划代码
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
		xx.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// 设置连接池
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
	 * <p>方法名称：getXjXzqh()</p>
	 * <p>方法描述：获取下级行政区划信息</p>
	 * @param forXzqh 行政区划代码
	 * @return	XMLDataObject对象
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-16
	 */
	public  XMLDataObject getXjXzqh(String forXzqh) throws Exception {
		String sql = "SELECT XZQH_DM,XZQH_MC FROM "+getDb()+" WHERE SJ_XZQH_DM='"
				+ forXzqh + "' order by XZQH_DM";
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql);
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// 设置连接池
		long count = xzqhDw.retrieve();
		if (count > 0) {
			return xzqhDw.toXDO();
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * <p>方法名称：getJcdmByXzqh()</p>
	 * <p>方法描述：通过级次代码获得行政区划代码</p>
	 * @param forXzqh 行政区划代码
	 * @return	行政区划代码
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-16
	 */
	public String getJcdmByXzqh(String forXzqh) throws Exception {
		String sql = "SELECT XZQH_DM,XZQH_MC FROM "+getDb()+" WHERE XZQH_DM='"+forXzqh+"'";
		DataWindow dw = DataWindow.dynamicCreate(sql, true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// 设置连接池
		long count = dw.retrieve();
		String jc_dm = "";
		if(count>0){
			jc_dm = StringEx.sNull(dw.getItemAny(0L, "JCDM"));
		}
		return jc_dm;
	}
	/**
	 * 
	 * <p>方法名称：getShengXdo()</p>
	 * <p>方法描述：获得省级政区划代码</p>
	 * @return XMLDataObject对象
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-16
	 */
	private XMLDataObject getShengXdo() throws Exception {
		String sql = "SELECT XZQH_DM,XZQH_MC FROM "+getDb()+" WHERE JCDM='1' ORDER BY XZQH_DM";
		DataWindow shengDw = DataWindow.dynamicCreate(sql, true);
		shengDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// 设置连接池
		shengDw.retrieve();
		return shengDw.toXDO();
	}
	
	/**
	 * 
	 * <p>方法名称：getXzqhMcByDm()</p>
	 * <p>方法描述：通过行政区划代码获取名称</p>
	 * @param xzqh_dm 行政区划代码
	 * @return 行政区划名称
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-16
	 */
	private String getXzqhMcByDm(String xzqh_dm) throws Exception {
		String sql = "SELECT XZQH_DM,XZQH_MC FROM "+getDb()+" WHERE XZQH_DM='"
				+ xzqh_dm + "'";
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql);
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// 设置连接池
		long count = xzqhDw.retrieve();
		String xzqh_mc = "";
		if (count > 0) {
			xzqh_mc = (String) xzqhDw.getItemAny(0, "XZQH_MC");
		}
		return xzqh_mc;
	}
	
	

}
