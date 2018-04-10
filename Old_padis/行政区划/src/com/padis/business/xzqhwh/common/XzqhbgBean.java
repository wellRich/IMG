package com.padis.business.xzqhwh.common;

public class XzqhbgBean {
	
	private String sqbxh;//�������ţ������������
	private String sqbmxXh;//�������ϸ������ţ���ϸ������
	private String srcXzqhdm;//ԭ��������
	private String srcXzqhmc;//ԭ��������
	private String destXzqhdm;//Ŀ����������
	private String destXzqhmc;//Ŀ����������
	private String bglxdm;//������ʹ���
	private String jcdm;//��������ļ��δ��루ԭ��Ŀ�궼��ͬ����
	private String qx_jgdm;//Ȩ�޻�������
	private String lrr_dm;//¼���˴���
	private String bz;//��ע
	private boolean ringFlag;//�Ƿ�Ϊ��״����
	public String getQx_jgdm() {
		return qx_jgdm;
	}
	public void setQx_jgdm(String qx_jgdm) {
		this.qx_jgdm = qx_jgdm;
	}
	public String getLrr_dm() {
		return lrr_dm;
	}
	public boolean getRingFlag() {
		return ringFlag;
	}
	public void setRingFlag(boolean ringFlag) {
		this.ringFlag = ringFlag;
	}
	public void setLrr_dm(String lrr_dm) {
		this.lrr_dm = lrr_dm;
	}
	public String getBglxdm() {
		return bglxdm;
	}
	public void setBglxdm(String bglxdm) {
		this.bglxdm = bglxdm;
	}
	public String getDestXzqhdm() {
		return destXzqhdm;
	}
	public void setDestXzqhdm(String destXzqhdm) {
		this.destXzqhdm = destXzqhdm;
	}
	public String getDestXzqhmc() {
		return destXzqhmc;
	}
	public void setDestXzqhmc(String destXzqhmc) {
		this.destXzqhmc = destXzqhmc;
	}
	public String getSrcXzqhdm() {
		return srcXzqhdm;
	}
	public void setSrcXzqhdm(String srcXzqhdm) {
		this.srcXzqhdm = srcXzqhdm;
	}
	public String getSrcXzqhmc() {
		return srcXzqhmc;
	}
	public void setSrcXzqhmc(String srcXzqhmc) {
		this.srcXzqhmc = srcXzqhmc;
	}
	public String getSqbmxXh() {
		return sqbmxXh;
	}
	public void setSqbmxXh(String sqbmxXh) {
		this.sqbmxXh = sqbmxXh;
	}
	public String getSqbxh() {
		return sqbxh;
	}
	public void setSqbxh(String sqbxh) {
		this.sqbxh = sqbxh;
	}
	public void setJcdm(String jcdm) {
		this.jcdm = jcdm;
	}	
	
	public String getJcdm(){
		if(this.jcdm!=null) return this.jcdm;
		
		if(this.destXzqhdm==null) return null;
		if(!destXzqhdm.substring(12).equals("000"))
			return "6";
		else if(destXzqhdm.substring(12).equals("000") && !destXzqhdm.substring(9).equals("000000"))
			return "5";
		else if(destXzqhdm.substring(9).equals("000000") && !destXzqhdm.substring(6).equals("000000000"))
			return "4";
		else if(destXzqhdm.substring(6).equals("000000000") && !destXzqhdm.substring(4).equals("00000000000"))
			return "3";		
		else if(destXzqhdm.substring(4).equals("00000000000") && !destXzqhdm.substring(2).equals("0000000000000"))
			return "2";		
		else if(destXzqhdm.substring(2).equals("0000000000000") && !destXzqhdm.equals("000000000000000"))
			return "1";		
		else if (destXzqhdm.equals("000000000000000"))
			return "0";
		return null;
	}
	public static void main(String[] args){
	/*	XzqhbgBean xb = new XzqhbgBean();
		xb.setDestXzqhdm("000000000000000");
		System.out.println(xb.getJcdm());
		xb.setDestXzqhdm("130000000000000");
		System.out.println(xb.getJcdm());		
		xb.setDestXzqhdm("131000000000000");
		System.out.println(xb.getJcdm());	
		xb.setDestXzqhdm("131010000000000");
		System.out.println(xb.getJcdm());
		xb.setDestXzqhdm("131010100000000");
		System.out.println(xb.getJcdm());	
		xb.setDestXzqhdm("131010100100000");
		System.out.println(xb.getJcdm());		
		xb.setDestXzqhdm("131010100100100");
		System.out.println(xb.getJcdm());*/
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
}
