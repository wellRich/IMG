package com.padis.business.xzqhwh.delxzqh;

public class DelxzqhBean {

	private String xzqhdm;//行政区划代码
	private String ywxtdm;//业务系统代码	("J", "奖励扶助")  ("T", "特别扶助");	("S", "少生快富");	("L", "育龄妇女流动人口");	("K", "快速调查");	("Z", "事业统计");
	private String ywbmc;//业务表名称
	private String ywbxzqhmc;//业务表对应行政区划的字段名称
	private int sjl;//所含数据量
	private String bz;//备注信息
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public int getSjl() {
		return sjl;
	}
	public void setSjl(int sjl) {
		this.sjl = sjl;
	}
	public String getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	public String getYwbmc() {
		return ywbmc;
	}
	public void setYwbmc(String ywbmc) {
		this.ywbmc = ywbmc;
	}
	public String getYwbxzqhmc() {
		return ywbxzqhmc;
	}
	public void setYwbxzqhmc(String ywbxzqhmc) {
		this.ywbxzqhmc = ywbxzqhmc;
	}
	public String getYwxtdm() {
		return ywxtdm;
	}
	public void setYwxtdm(String ywxtdm) {
		this.ywxtdm = ywxtdm;
	}
	
}
