package com.padis.business.xzqhwh.common;

import java.io.File;

import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;
import com.padis.common.xtservice.XtSvc;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.or.UserTransaction;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

public class Common {
	
	/***********变更类型*******************/
	public static final String ADD = "11";
	public static final String CHANGE = "21";
	public static final String MERGE = "31";
	public static final String MOVE = "41";
	
	/***********申请单状态*******************/
	public static final String XZQH_SQDZT_WTJ = "10";
	public static final String XZQH_SQDZT_YTJ = "11";
	public static final String XZQH_SQDZT_SHTG = "20";
	public static final String XZQH_SQDZT_SHBTG = "21";
	public static final String XZQH_SQDZT_YQR = "30";
	public static final String XZQH_SQDZT_GJYSH = "40";
	public static final String XZQH_SQDZT_YFB = "50";
	public static final String XZQH_SQDZT_SJYQY = "60";
	public static final String XZQH_SQDZT_SJCLZ = "61";
	
	/***********集中变更状态*******************/
	public static final String XZQH_JZBGZT_YSC = "10";
	public static final String XZQH_JZBGZT_DRLSBCG = "20";
	public static final String XZQH_JZBGZT_DRLSBSB = "21";
	public static final String XZQH_JZBGZT_DRLSBCLZ = "22";
	public static final String XZQH_JZBGZT_LJJYCG = "30";
	public static final String XZQH_JZBGZT_LJJYSB = "31";
	public static final String XZQH_JZBGZT_LJJYCLZ = "32";
	public static final String XZQH_JZBGZT_SQDSQCG = "40";
	public static final String XZQH_JZBGZT_SQDSQSB = "41";
	public static final String XZQH_JZBGZT_SQDSQCLZ = "42";
	
	/***********明细表处理状态*******************/
	public static final String XZQH_CLZT_DCL = "10";//等待处理
	public static final String XZQH_CLZT_CLZ = "20";//处理中
	public static final String XZQH_CLZT_CLCG = "30";//处理成功
	public static final String XZQH_CLZT_CLSB = "31";//处理失败
	
	public static final String ORMAP_PACKAGE = "com.padis.business.common.data.dm";


	/**
	 * 
	 * <p>方法名称：getJbdm</p>
	 * <p>方法描述：根据行政区划代码获取相应级别代码</p>
	 * @param xzqh_dm 行政区划代码
	 * @author lijl
	 * @since 2009-7-10
	 */
	public static String getJbdm(String xzqh_dm) throws Exception{
		if(xzqh_dm==null||xzqh_dm.equals("")||xzqh_dm.length()!=15){
			return "";
		}
		String jbdm="";
		if(xzqh_dm.substring(0, 2).equals("00")){
			return "";
		}else if(xzqh_dm.substring(2, 4).equals("00")){
			jbdm = xzqh_dm.substring(0, 2);
		}else if(xzqh_dm.substring(4, 6).equals("00")){
			jbdm = xzqh_dm.substring(0, 4);
		}else if(xzqh_dm.substring(6, 9).equals("000")){
			jbdm = xzqh_dm.substring(0, 6);
		}else if(xzqh_dm.substring(9, 12).equals("000")){
			jbdm = xzqh_dm.substring(0, 9);
		}else if(xzqh_dm.substring(12, 15).equals("000")){
			jbdm = xzqh_dm.substring(0, 12);
		}else{
			jbdm = xzqh_dm;
		}
		return jbdm;
	}
	
	/**
	 * 
	 * <p>方法名称：getSjxzqhdm</p>
	 * <p>方法描述：根据行政区划代码获取相上级行政区划代码</p>
	 * @param xzqh_dm 行政区划代码
	 * @author lijl
	 * @since 2009-7-10
	 */
	public static String getSjxzqhdm(String xzqh_dm) throws Exception{
		if(xzqh_dm==null||xzqh_dm.equals("")||xzqh_dm.length()!=15){
			return "";
		}
		String sjxzqhdm="";
		if(xzqh_dm.substring(0, 2).equals("00")){
			return "";
		}else if(xzqh_dm.substring(2, 4).equals("00")){
			sjxzqhdm = "000000000000000";
		}else if(xzqh_dm.substring(4, 6).equals("00")){
			sjxzqhdm = xzqh_dm.substring(0, 2)+"0000000000000";
		}else if(xzqh_dm.substring(6, 9).equals("000")){
			sjxzqhdm = xzqh_dm.substring(0, 4)+"00000000000";
		}else if(xzqh_dm.substring(9, 12).equals("000")){
			sjxzqhdm = xzqh_dm.substring(0, 6)+"000000000";
		}else if(xzqh_dm.substring(12, 15).equals("000")){
			sjxzqhdm = xzqh_dm.substring(0, 9)+"000000";
		}else{
			sjxzqhdm = xzqh_dm.substring(0, 12)+"000";
		}
		return sjxzqhdm;
	}
	
	/**
	 * 
	 * <p>方法名称：getSjjgdm</p>
	 * <p>方法描述：获取上级级机构代码</p>
	 * @param qxjg_dm 机构代码
	 * @param dwlsgx_dm 本级单位隶属关系代码
	 * @param sjdwlsgx_dm 上级单位隶属关系代码
	 * @author lijl
	 * @since 2009-7-10
	 */
	public static String getSjjgdm(String qxjg_dm, String dwlsgx_dm,String sjdwlsgx_dm) throws Exception{
		IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
		XMLDataObject jgXdo = null;
		while(Integer.parseInt(dwlsgx_dm)>Integer.parseInt(sjdwlsgx_dm)){
			jgXdo = jgService.getJgXxxx(qxjg_dm);
			dwlsgx_dm = StringEx.sNull(jgXdo.getItemValue("DWLSGX_DM"));
			qxjg_dm = StringEx.sNull(jgXdo.getItemValue("SJ_JG_DM"));
		}
		if(jgXdo==null){
			return "";
		}else{
			String jgdm = StringEx.sNull(jgXdo.getItemValue("JG_DM"));
			return jgdm;
		}
	}
	
	/**
	 * 
	 * <p>方法名称：getJbdm</p>
	 * <p>方法描述：根据行政区划代码获取相应级别代码</p>
	 * @param xzqh_dm 行政区划代码
	 * @author lijl
	 * @since 2009-7-10
	 */
	public static String getJcdm(String xzqh_dm) throws Exception{
		if(xzqh_dm==null||xzqh_dm.equals("")||xzqh_dm.length()!=15){
			return "";
		}
		String jbdm="";
		if(xzqh_dm.substring(0, 2).equals("00")){
			return "0";
		}else if(xzqh_dm.substring(2, 4).equals("00")){
			jbdm = "1";
		}else if(xzqh_dm.substring(4, 6).equals("00")){
			jbdm = "2";
		}else if(xzqh_dm.substring(6, 9).equals("000")){
			jbdm = "3";
		}else if(xzqh_dm.substring(9, 12).equals("000")){
			jbdm = "4";
		}else if(xzqh_dm.substring(12, 15).equals("000")){
			jbdm = "5";
		}else{
			jbdm = "6";
		}
		return jbdm;
	}
	
	/**
	 * <p>方法名称：getUploadPath</p>
	 * <p>方法描述：获取文件上传的目录</p>
	 * @return 文件上传的目录
	 * @author wangjz
	 * @since Jan 21, 2008
	 */
	public static String getExportPath(){
		// 设置保存上传的目录
		String exportDir = XtSvc.getXtcs("97001", null, null);
		exportDir = exportDir.trim();
		if (exportDir == null) {
			LogManager.getLogger().error("系统没有设置文件上传目录");
			return "";
		}
		
		// 创建保存上传的目录
		String date = com.padis.common.xtservice.XtDate.getDBdate();
		date = date.replaceAll("-", "");
		File fileUploadDir = new File(exportDir,date);
		if (!fileUploadDir.exists()) {
			if (!fileUploadDir.mkdirs()) {
				LogManager.getLogger().error("无法创建文件上传目录");
				return "";
			}
		}
		return fileUploadDir.getAbsolutePath();
	}
	/**
	 * <p>方法名称：checkMcbg</p>
	 * <p>方法描述：校验是否是名称变更</p>
	 * @return 文件上传的目录
	 * @author lijl
	 * @since Jan 21, 2008
	 */
	public static boolean checkMcbg(String ysXzqh_dm, String mbXzqh_dm){
		boolean flag=false;
		if(!ysXzqh_dm.equals("")&&!mbXzqh_dm.equals("")){
			if(ysXzqh_dm.equals(mbXzqh_dm)){
				flag=true;
			}
		}
		return flag;
	}
	
	/**
	 * <p>方法名称：getMax</p>
	 * <p>方法描述：获取表中最大值</p>
	 * @return 文件上传的目录
	 * @author lijl
	 * @since 2011-02-26
	 */
	public String getMax(String tableName,UserTransaction ut) throws Exception{
		String maxValue ="1";
		DataWindow dw = DataWindow.dynamicCreate("SELECT TABLE_MAX  FROM XZQH_MAX WHERE TABLE_NAME='"+tableName+"'");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long cnt = dw.retrieve();
		if(cnt>0){
			maxValue = StringEx.sNull(dw.getItemAny(0, "TABLE_MAX"));
			if(maxValue==null ||maxValue.equals("")){
				maxValue ="1";
			}
			DataWindow insertdw = DataWindow.dynamicCreate("UPDATE XZQH_MAX SET TABLE_MAX=TABLE_MAX+1 WHERE TABLE_NAME='"+tableName+"'");
			insertdw.setTransObject(ut);
			insertdw.update(false);
		}
		return maxValue;
	}
}
