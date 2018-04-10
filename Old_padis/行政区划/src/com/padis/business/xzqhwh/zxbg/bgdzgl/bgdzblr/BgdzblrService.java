package com.padis.business.xzqhwh.zxbg.bgdzgl.bgdzblr;

import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.Tree;
import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.business.xzqhwh.common.XzqhbgCommon;
import com.padis.common.constants.CommonConstants;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;
import com.padis.common.xzqhservice.IXzqhService;
import com.padis.common.xzqhservice.XzqhInterfaceFactory;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;

/**
 * <p>
 * Description: 行政区划变更的Service类
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @Date 2009-06-11
 * @author 李靖亮
 * @version 1.0
 */
public class BgdzblrService extends XtwhBaseService{

	/**
	 * XzqhbgsqbService类的Manager
	 */

	BgdzblrManager mgr;
	CheckSqd checkSqd;

	/**
	 * 构造函数
	 */
	public BgdzblrService() {
		mgr = new BgdzblrManager();
		checkSqd = new CheckSqd();
	}
	
	/** 保存区划变更明细
	 * <p>方法名称：addXzqhgbdzb()，增加行政区划</p>
	 * <p>方法说明：增加行政区划的方法，本方法将会往DM_XZQH_WH,XT_YWXTWH_XZQHBGDZB表中各插入一条记录。</p>
	 * @param 无
	 * @Date 2009-06-16
	 * @author 李靖亮
	 * @return 无
	 */
	public void addXzqhbgsqb() {
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			String czry_dm = uwa.getCzry_dm();
			String jg_dm = uwa.getSwjg_dm();
			String qxjg_dm = uwa.getQx_swjg_dm();
			
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String xzqh_dm = jgService.getXzqhDmByJg(jg_dm);
			mgr.addXzqhbgsqb(xmldo,czry_dm,qxjg_dm,xzqh_dm);
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"增加行政区划成功！","");
			
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("增加行政区划失败:" + e.getMessage());
			LogManager.getLogger().log(e);			
			this.setResponse(CommonConstants.RTN_SUCCESS, 2006, e.getMessage(), e.getMessage());
		}
	}
	
	/**
	 * <p>方法名称： initXzqh()，初始化行政区划页面</p>
	 * <p>方法说明：初始化行政区划页面的方法。</p>
	 * @param 无
	 * @Date 2009-06-17
	 * @author 李靖亮
	 * @return 无
	 */
	public void initXzqh() {
		try {
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			IXzqhService xzqhService =XzqhInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String czry_dm = uwa.getCzry_dm();
			String qxjgz_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = jgService.getXzqhDmByJg(swjg_dm);
			XMLDataObject xdo = xzqhService.getXzqhXxxx(xzqh_dm);		
			String dwlsgx_dm = xdo.getItemValue("DWLSGX_DM");	
			
			String resultXml = mgr.getSqdxx(xzqh_dm);
			System.out.println("sqdxx代码是---------------------"+resultXml);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			String jb_dm = xzqhService.getJbdm(qxjgz_dm);
			String jc_dm = xzqhService.getJcdm(xzqh_dm);
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("CZRY_DM",czry_dm);//录入人代码
			xsBuf.append("JC_DM",jc_dm);//级次代码
			xsBuf.append("XZQH_DM",xzqh_dm);//行政区划代码
			xsBuf.append("DWLSGX_DM",dwlsgx_dm);//单位隶属关系代码
			xsBuf.append("XZQHJB_DM",jb_dm);//级别代码
			xsBuf.append(resultXml);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"跳转到初始化页面成功！",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("初始化页面失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS, 2006, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：checkSqd()，校验申请表单</p>
	 * <p>方法说明：校验申请表单的方法。</p>
	 * @param 无
	 * @Date 2009-06-26
	 * @author 李靖亮
	 * @return 无
	 */
	public void checkSqd(){
		try {	
			XMLDataObject xmldo = uwa.getArgXml();
			checkSqd.checkBgdmSqd(xmldo);
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"校验申请表单成功！","");
		} catch (Exception e) {
			LogManager.getLogger().error("校验申请表单失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.getMessage(), "");
		}
	}
	
	/**
	 * <p>方法名称：checkTj()，查询是否是同级行政区划</p>
	 * <p>方法说明：查询是否存是同级行政区划的方法。</p>
	 * @param 无
	 * @Date 2009-06-16
	 * @author 李靖亮
	 * @return 无
	 */
	public void checkTj(){
		try {	
			XMLDataObject xdo = uwa.getArgXml();
			String ysxzqh_dm = StringEx.sNull(xdo.getItemValue("YSXZQH_DM"));
			String mbxzqh_dm = StringEx.sNull(xdo.getItemValue("MBXZQH_DM"));
			String bglx_dm = StringEx.sNull(xdo.getItemValue("XZQHBGLX_DM"));
			
			String resultXml = checkSqd.checkTj(ysxzqh_dm,mbxzqh_dm);
			if(bglx_dm.equals("31")||bglx_dm.equals("32")){
				resultXml = checkSqd.checkSxj(ysxzqh_dm,mbxzqh_dm);
			}
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("FLAG",resultXml);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"查询行政区划变更类型代码成功！",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询行政区划变更类型代码失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：isExistsXzqh()，查询行政区划是否存在</p>
	 * <p>方法说明：查询行政区划是否存在的方法，本方法将会从表V_DM_XZQH查询出相关信息记录。</p>
	 * @param 无
	 * @Date 2009-06-16
	 * @author 李靖亮
	 * @return 无
	 */
	public void isExistsXzqh(){
		try {	
			XMLDataObject xdo = uwa.getArgXml();
			String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM"));
			String resultXml = checkSqd.isExistsXzqhdm(xzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("FLAG",resultXml);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"查询行政区划变更类型代码成功！",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询行政区划变更类型代码失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：isExistsYjyXzqh()，是否存在已禁用的区划代码，也就是该区划代码曾经被使用过</p>
	 * <p>方法说明：查是否存在已禁用的区划代码，也就是该区划代码曾经被使用过的方法，本方法将会从表V_DM_XZQH查询出相关信息记录。</p>
	 * @param 无
	 * @Date 2009-11-13
	 * @author 李靖亮
	 * @return 无
	 */
	public void isExistsYjyXzqh(){
		try {	
			XMLDataObject xdo = uwa.getArgXml();
			String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM"));
			XzqhbgCommon bgCommon = new XzqhbgCommon();
			boolean flag = bgCommon.isExistsYjyXzqh(xzqh_dm, "DM_XZQH_YLSJ");
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("FLAG",String.valueOf(flag));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"查询行政区划变更类型代码成功！",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询行政区划变更类型代码失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：mergerTjXzqh()，查询和上级同级的行政区划代码、名称</p>
	 * <p>方法说明：查询和上级同级的行政区划代码、名称的方法，本方法将会从表V_DM_XZQH查询出相关信息记录。</p>
	 * @param 无
	 * @Date 2009-06-16
	 * @author 李靖亮
	 * @return 无
	 */
	public void mergerXzqh(){
		try {	
			XMLDataObject xdo = uwa.getArgXml();
			String dbrxzqh ="";
			dbrxzqh = checkSqd.mergerXzqh(xdo);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("ITEMS",dbrxzqh);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"查询行政区划变更类型代码成功！",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询行政区划变更类型代码失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	
	/**
	 * <p>方法名称：mergerTjXzqh()，查询和上级同级的行政区划代码、名称</p>
	 * <p>方法说明：查询和上级同级的行政区划代码、名称的方法，本方法将会从表V_DM_XZQH查询出相关信息记录。</p>
	 * @param 无
	 * @Date 2009-06-16
	 * @author 李靖亮
	 * @return 无
	 */
	public void getTjXzqh(){
		try {	
			XMLDataObject xdo = uwa.getArgXml();
			String tjxzqh ="";
			String ysxzqh_dm = StringEx.sNull(xdo.getItemValue("YSXZQH_DM"));
			tjxzqh = checkSqd.getTjXzqh(ysxzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("ITEMS",tjxzqh);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"查询行政区划变更类型代码成功！",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询行政区划变更类型代码失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>方法名称：hasChildXzqh()，查询是否是有下级行政区划</p>
	 * <p>方法说明：查询是否存是有下级行政区划的方法。</p>
	 * @param 无
	 * @Date 2009-07-28
	 * @author 李靖亮
	 * @return 无
	 */
	public void hasChildXzqh(){
		try {	
			XMLDataObject xdo = uwa.getArgXml();
			String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM"));
			String resultXml = checkSqd.hasChildXzqh(xzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("FLAG",resultXml);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"查询行政区划变更类型代码成功！",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("查询行政区划变更类型代码失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * 初始化弹出树，查询表为（WDCJ_MLFL）。
	 * 
	 * @param 无
	 * @Date 2008-09-25
	 * @author 李靖亮
	 * @return 无
	 * @exception 无
	 */
	public void createSjXzqhTree() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String xzqhDm = jgService.getXzqhDmByJg(swjg_dm);
			Tree tree = new Tree();
			String xzqh_dm = xdo.getItemValue("XZQH_DM");
			String jc_dm = xdo.getItemValue("JC_DM");
			String ysXzqh_dm = "";
			if(xzqh_dm==null){
				ysXzqh_dm = xdo.getItemValue("YSXZQH_DM");
				xzqh_dm = ysXzqh_dm;
				jc_dm = Common.getJcdm(xzqh_dm);
				String sjXzhq_dm ="";
				if(Integer.parseInt(jc_dm)>3){
					if(Integer.parseInt(jc_dm)-Integer.parseInt(Common.getJcdm(xzqhDm))>2){
						sjXzhq_dm  = Common.getSjxzqhdm(Common.getSjxzqhdm(Common.getSjxzqhdm(xzqh_dm)));
					}else{
						sjXzhq_dm  = Common.getSjxzqhdm(Common.getSjxzqhdm(xzqh_dm));
					}
				}else{
					sjXzhq_dm = xzqhDm.substring(0, 2)+"0000000000000";
				}
				xzqh_dm = sjXzhq_dm;
			}else{
				ysXzqh_dm = xdo.getItemValue("SRCXZQH_DM");
			}
			String subTree = tree.createMoreTree(xzqh_dm,ysXzqh_dm,jc_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendHead("TREE");
			StringBuffer sbuf = new StringBuffer("<![CDATA[");
			sbuf.append(subTree);
			sbuf.append("]]>");
			xsBuf.append(sbuf.toString());
			xsBuf.appendTail("TREE");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "电子文档目录分类加载成功！", xsBuf.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("电子文档目录分类加载失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTN_FAIL, "电子文档目录分类加载失败！", e.toString());
		}
	}
	
	
	/**
	 * 初始化弹出树，查询表为（WDCJ_MLFL）。
	 * 
	 * @param 无
	 * @Date 2008-09-25
	 * @author 李靖亮
	 * @return 无
	 * @exception 无
	 */
	public void createTjXzqhTree() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String xzqhDm = jgService.getXzqhDmByJg(swjg_dm);
			Tree tree = new Tree();
			String xzqh_dm = xdo.getItemValue("XZQH_DM");
			String jc_dm = xdo.getItemValue("JC_DM");
			String ysXzqh_dm = "";
			if(xzqh_dm==null){
				ysXzqh_dm = xdo.getItemValue("YSXZQH_DM");
				xzqh_dm = ysXzqh_dm;
				jc_dm = Common.getJcdm(xzqh_dm);
				String sjXzhq_dm ="";
				if(Integer.parseInt(jc_dm)>2){
					if(Integer.parseInt(jc_dm)-Integer.parseInt(Common.getJcdm(xzqhDm))>1){
						sjXzhq_dm  = Common.getSjxzqhdm(Common.getSjxzqhdm(xzqh_dm));
					}else{
						sjXzhq_dm  = Common.getSjxzqhdm(xzqh_dm);
					}
				}else{
					sjXzhq_dm = xzqhDm.substring(0, 2)+"0000000000000";
				}
				xzqh_dm = sjXzhq_dm;
			}else{
				ysXzqh_dm = xdo.getItemValue("SRCXZQH_DM");
			}
			String subTree = tree.creatMergeTree(xzqh_dm,ysXzqh_dm,jc_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendHead("TREE");
			StringBuffer sbuf = new StringBuffer("<![CDATA[");
			sbuf.append(subTree);
			sbuf.append("]]>");
			xsBuf.append(sbuf.toString());
			xsBuf.appendTail("TREE");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "电子文档目录分类加载成功！", xsBuf.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("电子文档目录分类加载失败:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTN_FAIL, "电子文档目录分类加载失败！", e.toString());
		}
	}
}
