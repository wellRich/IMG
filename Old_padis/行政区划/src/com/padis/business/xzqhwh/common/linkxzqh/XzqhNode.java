package com.padis.business.xzqhwh.common.linkxzqh;

import com.padis.business.xzqhwh.common.XzqhbgBean;

public class XzqhNode {

	private XzqhbgBean xb;
	public XzqhNode nextXzqhNode;
	public XzqhNode(XzqhbgBean xb){
		this.xb = xb;
	}
	public XzqhbgBean getXzqhbgBean(){
		return xb;
	}
	
	public boolean equals(XzqhNode obj){
		XzqhbgBean objxb = obj.getXzqhbgBean();
		if(this.xb.getSrcXzqhdm().equals(objxb.getSrcXzqhdm()) && this.xb.getDestXzqhdm().equals(objxb.getDestXzqhdm()))
			return true;
		return false;
		
	}
	
}

