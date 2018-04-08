package com.padis.business.xzqhwh.sjjzbg.xzqhsjsc;

import java.io.File;

import com.padis.business.common.data.xzqh.D_xzqh_jzbgzip;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.business.common.sequence.IGeneralSequence;
import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;
import ctais.services.sequence.SequenceManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p>Title: XzqhsjscManager</P>
 * <p>Description: 省级上报数据的Manager类</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @auther lijl
 * @version 1.0
 * @since 2009-09-10
 * 修改人：
 * 修改时间：
 * 修改内容：
 * 修改版本号：
 */
public class XzqhsjscManager {
	
	/**
	 * 
	 * <p>
	 * 方法名称：addBgrzb
	 * </p>
	 * <p>
	 * 方法描述：将一个业务记录转化成指令，例如A县变更B县，最后需要将A下所有的乡和村都要列举出来返回
	 * </p>
	 * 
	 * @param xb
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-10
	 */
	public void uploadXzqhjzbgzip(XMLDataObject xmldo, String czry_dm, String qxjg_dm) throws Exception {
		xmldo.rootScrollTo("FileInfo");
		String filePath = StringEx.sNull(xmldo.getItemAny(0L,"realname"));
		String fileName = StringEx.sNull(xmldo.getItemAny(0L,"filename"));
		D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
		zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		zipDw.insert(-1);
		IGeneralSequence iseq = (IGeneralSequence) SequenceManager.getSequenceFactory();
		String name="";
		if(!filePath.equals("")){
			filePath = filePath.replaceAll("\\\\", "/").replaceAll("//", "/");			
		}else{
			return ;
		}
		int index = fileName.lastIndexOf(".");
		if(index>-1){
			name = fileName.substring(0,index);
		}	
		String[] args =name.split("_");
		if(args==null||args.length!=3){
			return ;
		}
		if(args[1]==null||args[1].equals("")||args[2]==null||args[2].equals("")){
			return ;
		}
		String zipxh = iseq.get("SEQ_XZQH_JZBGZIP_XL");
		zipDw.setItemString(0L, "ZIPXH", zipxh);
		zipDw.setItemString(0L, "XZQH_DM", args[1]);
		zipDw.setItemString(0L, "RQ",args[2]);
		zipDw.setItemString(0L, "JZBGZT_DM","10");
		zipDw.setItemString(0L, "BGZL_DM","0");
		zipDw.setItemString(0L, "WJM", fileName);
		zipDw.setItemString(0L, "WJLJ", filePath);
		zipDw.setItemString(0, "LRR_DM", czry_dm);
		zipDw.setItemString(0, "LRSJ", XtDate.getDBTime());
		zipDw.setItemString(0, "LRJG_DM", qxjg_dm);
		zipDw.setTransObject(new UserTransaction());
		zipDw.update(true);
		
	}	

	/**
	 * <p>
	 * 方法名称：queryXzqhZip
	 * </p>
	 * <p>
	 * 方法描述：查询行政区划代码和名称是否已存在
	 * </p>
	 * 
	 * @param xzqh_dm
	 *            行政区划代码
	 * @param xzqh_mc
	 *            行政区划名称
	 * @return 目录节点信息
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-10
	 */
	public boolean queryXzqhZip(String xzqh_dm, String rq)throws Exception {
		boolean flag = false;
		StringBuffer sql = new StringBuffer("SELECT Z.WJM FROM XZQH_JZBGZIP Z WHERE Z.XZQH_DM='");
		sql.append(xzqh_dm);
		sql.append("'");
		sql.append(" AND Z.RQ='");
		sql.append(rq);
		sql.append("'");
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		if (count >0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * <p>方法名称：queryXzqhjzbgzip()，省级行政区划集中变更文件记录</p>
	 * <p>方法说明：省级行政区划集中变更文件记录的方法，本方法将会往XT_XZQHFB表中检索出多条记录。</p>
	 * @param xmldo		查询条件
	 * @param args		分页查询参数
	 * @since 2009-09-10
	 * @author lijl
	 * String 符合条件的电子邮件列表
	 * @throws Exception
	 */
	public String queryXzqhjzbgzip(XMLDataObject xmldo ,long[] args,String xzqh_dm) throws Exception {
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("SELECT Z.ZIPXH,Z.XZQH_DM,substr(Z.XZQH_DM,0,2) SJXZQH_DM,Z.RQ,Z.WJM,Z.JZBGZT_DM," +
				"to_char(Z.LRSJ,'YYYY-MM-DD HH24:mi:ss') LRSJ FROM XZQH_JZBGZIP Z");
		if(!xzqh_dm.equals("00")){
			queryBuffer.append(" where substr(Z.XZQH_DM,0,2)='").append(xzqh_dm).append("'");
		}
		queryBuffer.append(" order by Z.LRSJ desc");
		DataWindow dw = DataWindow.dynamicCreate(queryBuffer.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//设置连接池
		long rowCount = dw.getRowCount(queryBuffer.toString());
		long ps = Long.parseLong(pageSize);   //一页中包括多少条数据，进行分页准备
		long pageCount = dw.getPageCount(ps);  //一共多少页
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex),pageCount, queryBuffer.toString());
		args[0] = pageCount ; 
		args[1] = rowCount ; 
		String resultXml = "";
		if (cnt > 0){
			Dmmc.convDmmc(dw,"JZBGZT_DM", "V_DM_XZQH_JZBGZT");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>方法名称：deleteMxb()，删除明细表数据</p>
	 * <p>方法说明：删除明细表数据的方法，本方法将会从表XT_XZQHBGMXB中删除多条记录。</p>
	 * @param mxbxhs
	 *             明细表序号
	 * @since 2009-09-10
	 * @author lijl
	 * @return 无
	 * @exception Exception
	 */
	public void deleteZip(String zipxhs) throws Exception{
		String[] zipxh = zipxhs.split(",");
		UserTransaction ut = new UserTransaction();
		try {
			ut.begin();
			for(int i=0; i<zipxh.length; i++){
				//删除ZIP文件记录				
				D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
				zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
				StringBuffer sql1 = new StringBuffer();
				sql1.append("ZIPXH = '").append(zipxh[i]).append("'");
				zipDw.setFilter(sql1.toString());
				long count1 = zipDw.retrieve();
				if(count1>0){
					String wjlj = StringEx.sNull(zipDw.getItemAny(0, "WJLJ"));
					File file = new File(wjlj);
					if(file.exists()){
						file.delete();
					}
					zipDw.deleteRow(0L);
					zipDw.setTransObject(ut);
					zipDw.update(false);
				}
				
				//删除省级数据
				StringBuffer sqlSb2 = new StringBuffer("delete XZQH_JZBGSJ_TEMP t where ");
				sqlSb2.append(" t.ZIPXH='");
				sqlSb2.append(zipxh[i]);
				sqlSb2.append("'");
				DataWindow deleteDw = DataWindow.dynamicCreate(sqlSb2.toString());
				deleteDw.setTransObject(ut);
				deleteDw.update(false);
				//删除对照表数据
				
				StringBuffer sqlSb3 = new StringBuffer("delete XZQH_JZBGDZB_TEMP t where ");
				sqlSb3.append(" t.ZIPXH='");
				sqlSb3.append(zipxh[i]);
				sqlSb3.append("'");
				DataWindow dzbDw = DataWindow.dynamicCreate(sqlSb3.toString());
				dzbDw.setTransObject(ut);
				dzbDw.update(false);
				
				//删除跨两级数据对照表对照表数据
				
				StringBuffer sqlSb4 = new StringBuffer("delete XZQH_JZBGKLJDZB_TEMP t where ");
				sqlSb4.append(" t.ZIPXH='");
				sqlSb4.append(zipxh[i]);
				sqlSb4.append("'");
				DataWindow kljdzbDw = DataWindow.dynamicCreate(sqlSb4.toString());
				kljdzbDw.setTransObject(ut);
				kljdzbDw.update(false);
				
			}
			ut.commit();
		} catch (Exception e) {
			ut.rollback();
			throw e;
		}
	}
	
	/**
	 * 
	 * <p>
	 * 方法名称：getSqdxx
	 * </p>
	 * <p>
	 * 方法描述：获取申请单信息
	 * </p>
	 * 
	 * @param xzqh_dm
	 *            行政区划代码
	 * @author lijl
	 * @since 2009-7-8
	 */
	public String  isUpload(String xzqh_dm,String fileName) throws Exception{
		String flag = "可以上传";
		DataWindow dw1 = DataWindow.dynamicCreate("select ZIPXH,WJM from XZQH_jZBGZIP where WJM like '"+fileName+"%'");
		dw1.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		if(dw1.retrieve()>0){
			return "上传的文件名已存在，请重新命名！";
		}		
		DataWindow dw = DataWindow.dynamicCreate("select SQDXH,SQDZT_DM from XZQH_BGSQD where SBXZQH_DM='"+xzqh_dm+"'");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long cnt = dw.retrieve();
		if(cnt>0){
			String sqdzt_dm = StringEx.sNull(dw.getItemAny(0L, "SQDZT_DM"));
			if(!sqdzt_dm.equals("50")&&!sqdzt_dm.equals("60")){
				flag = "请先等上次的数据发布后再上传！";
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * <p>
	 * 方法名称：getZipzt
	 * </p>
	 * <p>
	 * 方法描述：获取ZIP文件状态
	 * </p>
	 * 
	 * @param zipxhs
	 *            zip文件序号
	 * @author lijl
	 * @since 2009-11-09
	 */
	public String getZipzt(String zipxhs) throws Exception{
		String[] zipxh = zipxhs.split(",");
		String msg = "可以删除";
		for(int i=0;i<zipxh.length;i++){
			D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
			zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
			StringBuffer sql1 = new StringBuffer();
			sql1.append("ZIPXH = '").append(zipxh[i]).append("'");
			zipDw.setFilter(sql1.toString());
			long count1 = zipDw.retrieve();
			if(count1>0){
				String jzbgzt_dm = StringEx.sNull(zipDw.getItemAny(0, "JZBGZT_DM"));
				String wjm = StringEx.sNull(zipDw.getItemAny(0, "WJM"));
				if(jzbgzt_dm.equals("40")){
					msg = "文件名为“"+wjm+"”的状态为“申请单生成成功”,不能被删除！";
					break;
				}
			}
		}
		return msg;
	}
}
