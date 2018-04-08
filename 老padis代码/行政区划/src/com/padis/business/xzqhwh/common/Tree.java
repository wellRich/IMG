package com.padis.business.xzqhwh.common;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.util.XmlStringBuffer;

/**
 * <p>Title: Tree</P>
 * <p>Description: 行政区划公共树</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @auther lijl
 * @version 1.0
 * @since 2009-11-10
 * 修改人：
 * 修改时间：
 * 修改内容：
 * 修改版本号：
 */
public class Tree {
	
	/**
	 * <p>方法名称：createMoreTree</p>
	 * <p>方法描述：查询跨级迁移的区划</p>
	 * @param xzhq_dm 当前节点
	 * @param jc_dm 级次代码
	 * @return 子节点内容
	 * @throws Exception
	 * @author lijl
	 * @since 2009-11-10
	 */
	public String createXzqhTree(String xzqh_dm, String jc_dm) throws Exception{
		
		XmlStringBuffer xsb = new XmlStringBuffer();
		DataWindow dw = getSubtree(xzqh_dm);
		long cnt = dw.getRowCount();
		if(cnt > 0L){
			for(int i = 0; i < (int)cnt; i++){
				boolean flag = false;
				String xzqh_mc = (String) dw.getItemAny(i, "XZQH_MC");				//子节点名称
				String xzqhDm = (String)(dw.toXDO()).getItemAny(i, "XZQH_DM");
				DataWindow tmpDw = getSubtree(xzqhDm);
				if(tmpDw.getRowCount() > 0L){				//判断是否有子节点
					flag = true;
				}
				xsb.append(createNode1(xzqh_mc, xzqhDm,flag));
			}
		}else{
			xsb.append("");
		}
		XmlStringBuffer xsbrtn = new XmlStringBuffer();
		xsbrtn.append("<tree>");
		xsbrtn.append(xsb.toString());
		xsbrtn.append("</tree>");
		return xsbrtn.toString();
	}
	
	/**
	 * <p>方法名称：createMoreTree</p>
	 * <p>方法描述：查询跨级迁移的区划</p>
	 * @param xzhq_dm 当前节点
	 * @param jc_dm 级次代码
	 * @return 子节点内容
	 * @throws Exception
	 * @author lijl
	 * @since 2009-11-10
	 */
	public String createMoreTree(String xzqh_dm, String ysXzqh_dm, String jc_dm) throws Exception{
		
		XmlStringBuffer xsb = new XmlStringBuffer();
		DataWindow dw = getSubtree(xzqh_dm);
		long cnt = dw.getRowCount();
		if(cnt > 0L){
			for(int i = 0; i < (int)cnt; i++){
				boolean disabled = false;
				boolean flag = false;
				String xzqh_mc = (String) dw.getItemAny(i, "XZQH_MC");				//子节点名称
				String xzqhDm = (String)(dw.toXDO()).getItemAny(i, "XZQH_DM");		
				if(ysXzqh_dm.equals(xzqhDm)||Common.getSjxzqhdm(ysXzqh_dm).equals(xzqhDm)){
					continue;
				}
				DataWindow tmpDw = getSubtree(xzqhDm);
				String jcDm =  Common.getJcdm(xzqhDm);
				if(Integer.parseInt(jc_dm)-Integer.parseInt(jcDm)>1){
					disabled = true;
				}else if(Integer.parseInt(jc_dm)-Integer.parseInt(jcDm)<=0){
					break;
				}
				if(tmpDw.getRowCount() > 0L&&Integer.parseInt(jc_dm)-Integer.parseInt(jcDm)>1){				//判断是否有子节点
					flag = true;
				}
				xsb.append(createNode(xzqh_mc, xzqhDm,jc_dm, ysXzqh_dm, flag, disabled));
			}
		}else{
			xsb.append("");
		}
		XmlStringBuffer xsbrtn = new XmlStringBuffer();
		xsbrtn.append("<tree>");
		xsbrtn.append(xsb.toString());
		xsbrtn.append("</tree>");
		return xsbrtn.toString();
	}
	
	/**
	 * <p>方法名称：creatMergeTree</p>
	 * <p>方法描述：查询跨级并入的区划</p>
	 * @param xzhq_dm 当前节点
	 * @param jc_dm 级次代码
	 * @return 子节点内容
	 * @throws Exception
	 * @author lijl
	 * @since 2009-11-10
	 */
	public String creatMergeTree(String xzqh_dm, String ysXzqh_dm, String jc_dm) throws Exception{
		
		XmlStringBuffer xsb = new XmlStringBuffer();
		DataWindow dw = getSubtree(xzqh_dm);
		long cnt = dw.getRowCount();
		if(cnt > 0L){
			for(int i = 0; i < (int)cnt; i++){
				boolean disabled = false;
				boolean flag = false;
				String xzqh_mc = (String) dw.getItemAny(i, "XZQH_MC");				//子节点名称
				String xzqhDm = (String)(dw.toXDO()).getItemAny(i, "XZQH_DM");
				if(ysXzqh_dm.equals(xzqhDm)){
					continue;
				}
				DataWindow tmpDw = getSubtree(xzqhDm);
				String jcDm =  Common.getJcdm(xzqhDm);
				if(Integer.parseInt(jc_dm)-Integer.parseInt(jcDm)>=1){
					disabled = true;
				}else if(Integer.parseInt(jc_dm)-Integer.parseInt(jcDm)<0){
					break;
				}
				if(tmpDw.getRowCount() > 0L&&Integer.parseInt(jc_dm)-Integer.parseInt(jcDm)>=1){				//判断是否有子节点
					flag = true;
				}
				xsb.append(createNode(xzqh_mc, xzqhDm,jc_dm,ysXzqh_dm, flag, disabled));
			}
		}else{
			xsb.append("");
		}
		XmlStringBuffer xsbrtn = new XmlStringBuffer();
		xsbrtn.append("<tree>");
		xsbrtn.append(xsb.toString());
		xsbrtn.append("</tree>");
		return xsbrtn.toString();
	}
	
	/**
	 * <p>方法名称：getSubtree</p>
	 * <p>方法描述：根据序列获取下级目录节点信息</p>
	 * @param forCurJddm 目录节点的序列
	 * @return 目录节点信息
	 * @throws Exception
	 * @author lijl
	 * @since 2009-11-10
	 */
	private DataWindow getSubtree(String xzqh_dm) throws Exception{
		/**
		 * 根据当前节点
		 */
		StringBuffer sbXzqh = new StringBuffer("select XZQH_DM,XZQH_MC from V_DM_XZQH_YLSJ " +    //构造查询语句
				"where SJ_XZQH_DM ='");
		sbXzqh.append(xzqh_dm).append("' order by XZQH_DM asc");
		
		DataWindow dw = DataWindow.dynamicCreate(sbXzqh.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		dw.retrieve();
		return dw;
	}
	/**
	 * <p>方法名称：createNode</p>
	 * <p>方法描述：根据序列获取目录节点名称</p>
	 * @param jdmc 目录节点名称
	 * @param jddm 目录节点序列
	 * @param flag 是否有下级的标志
	 * @param disabled 目录节点是否有效的标志
	 * @return 目录节点名称
	 * @throws Exception
	 * @author lijl
	 * @since 2009-11-10
	 */
	private String createNode(String jdmc, String jddm,String jc_dm,String ysXzqh_dm, boolean flag,boolean disabled) {
		StringBuffer sb = new StringBuffer();
		StringBuffer str1 = new StringBuffer("action_prefix_");
		String action = str1.append(jddm).append("','").append(disabled).append("_action_suffix").toString();
		sb.append(" <tree ");
		sb.append(" text= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(jdmc))));		
		sb.append("\"");
		if (flag) {
			StringBuffer str2 = new StringBuffer("src_prefix_");
			StringBuffer str3 = new StringBuffer("&amp;JC_DM=");
			str3.append(jc_dm);
			str3.append("&amp;SRCXZQH_DM=").append(ysXzqh_dm);
			String src = str2.append(jddm).append(str3.toString()).append("_src_suffix").toString();
			sb.append(" src= ");
			sb.append(String.valueOf(String.valueOf((new StringBuffer("\"")).append(src).append("\""))));
		}
		sb.append(" disabled= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(disabled).append("\""))));
		sb.append(" action= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(action).append("\""))));
		sb.append(" key= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(jddm).append("\""))));
		sb.append(" ></tree>");
		return sb.toString();
		
	}
	
	/**
	 * <p>方法名称：createNode</p>
	 * <p>方法描述：根据序列获取目录节点名称</p>
	 * @param jdmc 目录节点名称
	 * @param jddm 目录节点序列
	 * @param flag 是否有下级的标志
	 * @param disabled 目录节点是否有效的标志
	 * @return 目录节点名称
	 * @throws Exception
	 * @author lijl
	 * @since 2009-11-10
	 */
	private String createNode1(String jdmc, String jddm,boolean flag) {
		StringBuffer sb = new StringBuffer();
		StringBuffer str1 = new StringBuffer("action_prefix_");
		String action = str1.append(jddm).append("_action_suffix").toString();
		sb.append(" <tree ");
		sb.append(" text= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(jdmc))));		
		sb.append("\"");
		if (flag) {
			StringBuffer str2 = new StringBuffer("src_prefix_");
			String src = str2.append(jddm).append("_src_suffix").toString();
			sb.append(" src= ");
			sb.append(String.valueOf(String.valueOf((new StringBuffer("\"")).append(src).append("\""))));
		}
		sb.append(" action= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(action).append("\""))));
		sb.append(" key= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(jddm).append("\""))));
		sb.append(" ></tree>");
		return sb.toString();
		
	}
}
