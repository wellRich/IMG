package com.padis.business.xzqhwh.zxbg.bgdzgl.bgdzblr;

import java.util.ArrayList;
import java.util.List;

import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.XzqhManager;
import com.padis.business.xzqhwh.common.XzqhbgBean;
import com.padis.business.xzqhwh.common.linkxzqh.LinkedXzqh;
import com.padis.business.xzqhwh.common.linkxzqh.XzqhNode;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

public class CheckSqd {

	/**
	 * <p>方法名称：isExistsXzqh</p>
	 * <p>方法描述：查询行政区划代码和名称是否已存在</p>
	 * @param xzqh_dm 行政区划代码
	 * @param xzqh_mc 行政区划名称
	 * @return 目录节点信息
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-24
	 */
	public boolean isExistsXzqh(String xzqh_dm,String xzqh_mc) throws Exception{
		boolean flag=true;
		StringBuffer sql = new StringBuffer("SELECT X.XZQH_DM,X.XZQH_MC FROM V_DM_XZQH_YLSJ X WHERE X.XZQH_DM='");
		sql.append(xzqh_dm);
		sql.append("'");
		if(xzqh_mc!=null&&!xzqh_mc.equals("")){
			sql.append(" AND X.XZQH_MC='");
			sql.append(xzqh_mc);
			sql.append("'");
		}
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		if(count<1){
			flag=false;
		}
		return flag;
	}
	
	/**
	 * <p>方法名称：checkBgdmSqd</p>
	 * <p>方法描述：校验代码变更申请表单</p>
	 * @param xmldo 申请表单数据
	 * @param bglx_dm 变更类型
	 * @return 校验结果
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-24
	 */
	public void checkBgdmSqd(XMLDataObject xmldo) throws Exception{
		xmldo.rootScrollTo("XZQH");
		List dataList = new ArrayList();
		String errorInfo ="";
		for(int i=0; i<xmldo.getRowCount();i++){
			//原区划代码
			String ysxzqh_dm = StringEx.sNull(xmldo.getItemAny(i,"YSXZQH_DM"));

			//现区划代码
			String mbxzqh_dm = StringEx.sNull(xmldo.getItemAny(i,"MBXZQH_DM"));

			//区划代码变更类型
			String bglx_dm =  StringEx.sNull(xmldo.getItemAny(i,"BGLX_DM"));

			XzqhbgBean xzqhBean = new XzqhbgBean();
			xzqhBean.setDestXzqhdm(mbxzqh_dm);
			xzqhBean.setSrcXzqhdm(ysxzqh_dm);
			xzqhBean.setBglxdm(bglx_dm);
			XzqhNode xzqhNode = new XzqhNode(xzqhBean);
			dataList.add(xzqhNode);
		}
		if(!this.checkDataType(dataList)){
			errorInfo = "此表单中非环状数据的目标代码已存在！";
			throw new Exception(errorInfo);
		}
	}
	
	/**
	 * <p>方法名称：checkDataType</p>
	 * <p>方法描述：判断是否是环状数据</p>
	 * @param xzqh_dm 行政区划代码
	 * @return TRUE或FALSE
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-24
	 */
	public boolean checkDataType(List dataList) throws Exception{
		boolean flag=true;		
		LinkedXzqh linked = new LinkedXzqh();
		linked.data = dataList;
		int count=1;
		while(linked.data.size()>0) {
			if(count>1){
				throw new Exception("只能录入一个环!");
			}
			XzqhNode xn = (XzqhNode) linked.data.get(0);
			List result = new ArrayList();
			result.add(xn);
			linked.group(xn,result);
			if(result.size()>1){
				XzqhNode startNode = (XzqhNode)result.get(0);
				XzqhNode endNode = (XzqhNode)result.get(result.size()-1);
				String srcxzqhDm = startNode.getXzqhbgBean().getSrcXzqhdm();
				String destxzqhdm = endNode.getXzqhbgBean().getDestXzqhdm();
				if(!srcxzqhDm.equals(destxzqhdm)){
					if(this.isExistsXzqh(destxzqhdm, "")){
						flag=false;
						throw new Exception("解析环状数据错误：目标代码“"+destxzqhdm+"”已存在!");
					}
				}
			}else{			
				XzqhNode node = (XzqhNode)result.get(0);
				String mbXzqhdm = node.getXzqhbgBean().getDestXzqhdm();
				String bglx_dm = node.getXzqhbgBean().getBglxdm();
				if(!bglx_dm.equals(Common.MERGE)){
					if(this.isExistsXzqh(mbXzqhdm, "")){
						flag=false;
						throw new Exception("解析环状数据错误：目标代码“"+mbXzqhdm+"”已存在!");
					}
				}else{
					if(!this.isExistsXzqh(mbXzqhdm, "")){
						flag=false;
						throw new Exception("解析环状数据错误：目标代码“"+mbXzqhdm+"”不存在!");
					}	
				}
			}
			count++;
		}
		return flag;
	}
	
	/**
	 * <p>方法名称：mergerTjXzqh</p>
	 * <p>方法描述：查询和上级同级的行政区划代码、名称</p>
	 * @param xzqh_dm 行政区划代码
	 * @param bglx_dm 变更类型代码
	 * @return 行政区划字符串
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-24
	 */
	public String mergerXzqh(XMLDataObject xdo) throws Exception{
		String ysxzqh_dm = StringEx.sNull(xdo.getItemValue("YSXZQH_DM"));
		String bglx_dm = StringEx.sNull(xdo.getItemValue("XZQHBGLX_DM"));
		String mbxzqh_dm = StringEx.sNull(xdo.getItemValue("MBXZQH_DM"));		
		String mbjb_dm = Common.getJbdm(mbxzqh_dm);
		String ysjb_dm = Common.getJbdm(ysxzqh_dm);
		String sjXzqhDm1 = Common.getSjxzqhdm(ysxzqh_dm);
		String sjXzqhDm2 = Common.getSjxzqhdm(sjXzqhDm1);
		String sjXzqhdm = ysxzqh_dm;
		if(bglx_dm.equals("41")){
			sjXzqhdm = sjXzqhDm2;
		}
		StringBuffer sql = new StringBuffer("SELECT X.XZQH_DM,X.XZQH_MC FROM V_DM_XZQH_YLSJ X WHERE X.SJ_XZQH_DM='");
		sql.append(sjXzqhdm);
		sql.append("' order by X.XZQH_DM");	
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		StringBuffer sb = new StringBuffer("");
		if(count>0){
			for(int i=0; i<count;i++){
				String xzqhdm = StringEx.sNull(xzqhDw.getItemAny(i,"XZQH_DM"));
				String xzqhmc = StringEx.sNull(xzqhDw.getItemAny(i,"XZQH_MC"));
				if(bglx_dm.equals("41")){
					if(!xzqhdm.equals(sjXzqhDm1)){
						sb.append("<ITEM>");
						sb.append("<XZQH_DM>");
						sb.append(xzqhdm);
						sb.append("</XZQH_DM>");
						sb.append("<XZQH_MC>");
						sb.append(xzqhmc);
						sb.append("</XZQH_MC>");
						sb.append("</ITEM>");
					}
				}else if(bglx_dm.equals("31")){
					xdo.rootScrollTo("XZQH");
					long rows = xdo.getRowCount();
					boolean flag=true;
					try{
						if(rows>0){
							for(int j=0; j<rows;j++){
								String srcxzqh_dm = StringEx.sNull(xdo.getItemAny(j,"YSXZQH_DM"));
								String bglxdm = StringEx.sNull(xdo.getItemAny(j,"BGLX_DM"));
								if(srcxzqh_dm.equals(xzqhdm)&&bglxdm.equals("41")){
									flag=false;
									break;
								}
							}
						}
					}catch(Exception e){
						flag=true;
					}
					if(flag){
						sb.append("<ITEM>");
						sb.append("<YSXZQH_DM>");
						sb.append(xzqhdm);
						sb.append("</YSXZQH_DM>");
						sb.append("<YSXZQH_MC>");
						sb.append(xzqhmc);
						sb.append("</YSXZQH_MC>");
						sb.append("<MBXZQH_DM>");
						sb.append(mbjb_dm);		
						sb.append("</MBXZQH_DM>");
						sb.append("<MBXZQH_MC>");
						sb.append(xzqhmc);
						sb.append("</MBXZQH_MC>");
						sb.append("<BJQH_DM>");
						sb.append("</BJQH_DM>");
						sb.append("</ITEM>");
					}
				}
			}
		}
		return sb.toString();
	}
	
	
	/**
	 * <p>方法名称：mergerTjXzqh</p>
	 * <p>方法描述：查询和上级同级的行政区划代码、名称</p>
	 * @param xzqh_dm 行政区划代码
	 * @param bglx_dm 变更类型代码
	 * @return 行政区划字符串
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-24
	 */
	public String getTjXzqh(String ysxzqh_dm) throws Exception{		
		String sjXzqhDm = Common.getSjxzqhdm(ysxzqh_dm);
		StringBuffer sql = new StringBuffer("SELECT X.XZQH_DM,X.XZQH_MC FROM V_DM_XZQH_YLSJ X WHERE X.SJ_XZQH_DM='");
		sql.append(sjXzqhDm);
		sql.append("' order by X.XZQH_DM");	
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		StringBuffer sb = new StringBuffer("");
		if(count>0){
			for(int i=0; i<count;i++){
				String xzqhdm = StringEx.sNull(xzqhDw.getItemAny(i,"XZQH_DM"));
				String xzqhmc = StringEx.sNull(xzqhDw.getItemAny(i,"XZQH_MC"));
				if(!xzqhdm.equals(ysxzqh_dm)){
					sb.append("<ITEM>");
					sb.append("<XZQH_DM>");
					sb.append(xzqhdm);
					sb.append("</XZQH_DM>");
					sb.append("<XZQH_MC>");
					sb.append(xzqhmc);
					sb.append("</XZQH_MC>");
					sb.append("</ITEM>");
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * <p>方法名称：checkTj</p>
	 * <p>方法描述：查询变是否同级</p>
	 * @param ysxzqh_dm 原始行政区划代码
	 * @param mbxzqh_dm 目标行政区划代码
	 * @return 目录节点信息
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-24
	 */
	public String checkTj(String ysxzqh_dm, String mbxzqh_dm) throws Exception{
		XzqhManager xzqhMgr = new XzqhManager();
		xzqhMgr.setDb("V_DM_XZQH_YLSJ");
		String ysjcdm = xzqhMgr.getJcdmByXzqh(ysxzqh_dm);
		String mbjcdm = xzqhMgr.getJcdmByXzqh(mbxzqh_dm);
		if(Integer.parseInt(ysjcdm)-Integer.parseInt(mbjcdm)==0){
			return "true";
		}else{
			return "false";
		}
		
	}
	
	/**
	 * <p>方法名称：checkSxj</p>
	 * <p>方法描述：查询是否是上下级</p>
	 * @param ysxzqh_dm 原始行政区划代码
	 * @param mbxzqh_dm 目标行政区划代码
	 * @return 目录节点信息
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-24
	 */
	public String checkSxj(String ysxzqh_dm, String mbxzqh_dm) throws Exception{
		XzqhManager xzqhMgr = new XzqhManager();
		xzqhMgr.setDb("V_DM_XZQH_YLSJ");
		String ysjcdm = xzqhMgr.getJcdmByXzqh(ysxzqh_dm);
		String mbjcdm = xzqhMgr.getJcdmByXzqh(mbxzqh_dm);
		if(Integer.parseInt(mbjcdm)-Integer.parseInt(ysjcdm)==-1){
			return "true";
		}else{
			return "false";
		}
		
	}
	
	/**
	 * <p>方法名称：isExistsXzqhdm</p>
	 * <p>方法描述：查询行政区划是否存在</p>
	 * @param xzqh_dm 行政区划代码
	 * @return 目录节点信息
	 * @throws Exception
	 * @author lijl
	 * @since 2009-06-24
	 */
	public String isExistsXzqhdm(String xzqh_dm) throws Exception{
		String message="校验通过";
		StringBuffer sql = new StringBuffer("SELECT X.XZQH_DM,X.XZQH_MC FROM V_DM_XZQH_YLSJ X WHERE X.XZQH_DM='");
		sql.append(xzqh_dm);
		sql.append("'");	
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		if(count>0){
			String xzqh_mc = (String)xzqhDw.getItemAny(0, "XZQH_MC");
			message=xzqh_mc;
		}
		return message;
	}
	
	/**
	 * <p>方法名称：hasChildXzqh()，查询是否是有下级行政区划</p>
	 * <p>方法说明：查询是否存是有下级行政区划的方法。</p>
	 * @param 无
	 * @Date 2009-07-28
	 * @author 李靖亮
	 * @return 字符串
	 */
	public String hasChildXzqh(String forXzqh) throws Exception {
		String sql = "SELECT XZQH_DM,XZQH_MC FROM V_DM_XZQH_YLSJ WHERE SJ_XZQH_DM='"
				+ forXzqh + "'";
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql);
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// 设置连接池
		long count = xzqhDw.retrieve();
		if (count > 0) {
			return String.valueOf(count);
		} else {
			return "0";
		}
	}
}
