package com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb;

import com.padis.business.xzqhwh.common.Common;

public abstract class CheckXzqhImp implements ICheckXzqh {

	public abstract boolean checkXzqh(String srcXzqh_dm, String destXxzqh_dm, String groupxh, String lrsj ,String sbxzqh_dm) throws Exception;
	//查找上级区划是否和给出的区划代码相同
	public boolean checkSjXzqh(String xjxzqh_dm,String sjxzqh_dm) throws Exception{
		boolean flag=false;
		String yssjXzqh_dm = Common.getSjxzqhdm(xjxzqh_dm);
		while(Integer.parseInt(Common.getJcdm(yssjXzqh_dm))>=1){
			if(yssjXzqh_dm.equals(sjxzqh_dm)){
				flag=true;
				break;			
			}else{
				yssjXzqh_dm = Common.getSjxzqhdm(yssjXzqh_dm);
			}
		}
		return flag;
	}
}
