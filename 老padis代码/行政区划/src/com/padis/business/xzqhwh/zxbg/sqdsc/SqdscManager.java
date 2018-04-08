package com.padis.business.xzqhwh.zxbg.sqdsc;

import java.util.ArrayList;
import java.util.List;

import com.padis.business.common.data.dm.D_dm_xzqh_ylsj;
import com.padis.business.common.data.xzqh.D_xzqh_bggroup;
import com.padis.business.common.data.xzqh.D_xzqh_bgmxb;
import com.padis.business.common.data.xzqh.D_xzqh_bgsqd;
import com.padis.business.common.data.xzqh.D_xzqh_jzbgzip;
import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.XzqhbgBean;
import com.padis.business.xzqhwh.common.XzqhbgCommon;
import com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb.AddCheckXzqhImp;
import com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb.ChangeCheckXzqhImp;
import com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb.MergeCheckXzqhImp;
import com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb.MoveCheckXzqhImp;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;


/**
 * <p>Title: SqdwhManager.java </p>
 * <p>Description:<类的功能描述> </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @date 2009-9-22
 * @author pengld
 * @version 1.0
 * 修改历史： 
 * 修改人
 * 修改时间(yyyy/mm/dd)
 * 修改内容
 * 版本号
 */

public class SqdscManager {

	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param qxjg_dm
	 * @param xmldo
	 * @param args
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-22
	 */
	public String querySqb(String xzqh_dm, XMLDataObject xmldo ,long[] args) throws Exception {	
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "8" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("SELECT SQDXH,SQDMC,SQDZT_DM AS SQDZTDM,SQDZT_DM,BZ,SPYJ,SBXZQH_DM||'000000000' AS SBXZQH_MC,SBXZQH_DM,LRSJ FROM XZQH_BGSQD WHERE SBXZQH_DM = '");
		queryBuffer.append(xzqh_dm.substring(0,6));
		queryBuffer.append("' ORDER BY LRSJ DESC");
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
			Dmmc.convDmmc(dw,"SQDZT_DM", "V_DM_XZQH_SQDZT");
			Dmmc.convDmmc(dw, "SBXZQH_MC","V_DM_XZQH");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param xdo
	 * @throws Exception
	 * @author lijl
	 * @since 2009-9-22
	 */
	public void deleteSqd(String sqdxh) throws Exception {
		
		D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
		d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		d_xzqh_bgsqd.setFilter("SQDXH ='"+sqdxh+"'");
		long rows = d_xzqh_bgsqd.retrieve();
		UserTransaction ut = new UserTransaction();
		if(rows>0L)
		{
			try{
				ut.begin();
				String sbxzqh_dm = StringEx.sNull(d_xzqh_bgsqd.getItemAny(0L, "SBXZQH_DM"));	
				String sqdzt_dm = StringEx.sNull(d_xzqh_bgsqd.getItemAny(0L, "SQDZT_DM"));
				if(!sqdzt_dm.equals(Common.XZQH_SQDZT_WTJ)&&!sqdzt_dm.equals(Common.XZQH_SQDZT_SHBTG)){
					throw new Exception("只能删除状态为“未提交”或者“审核不通过”的申请单！");
				}
				if(!sbxzqh_dm.equals("")&&sbxzqh_dm.length()>2){
					sbxzqh_dm = sbxzqh_dm.substring(0, 2);
				}
				D_xzqh_bggroup d_xzqh_bggroup = new D_xzqh_bggroup();
				d_xzqh_bggroup.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
				//按照时间顺序倒序删除，但是考虑到省级上报文件的时间都一样，LRSJ DESC 无法正常显示，所以改成主键方式，这里存在问题就是主键迁移的时候
				d_xzqh_bggroup.setFilter("SQDXH ='"+sqdxh+"' ORDER BY GROUPXH DESC");
				long rows1 = d_xzqh_bggroup.retrieve();
				if(rows1>0){
					for(int i=0; i<rows1; i++){
						String groupxh = StringEx.sNull(d_xzqh_bggroup.getItemAny(0, "GROUPXH"));
						D_xzqh_bgmxb d_xzqh_bgmxb = new D_xzqh_bgmxb();
						d_xzqh_bgmxb.setConnectionName(ConnConfig.getConnectionName(this.getClass()));					
						d_xzqh_bgmxb.setFilter("GROUPXH ='"+groupxh+"'");
						d_xzqh_bgmxb.setOrderby("PXH DESC");
						long rows2 = d_xzqh_bgmxb.retrieve();					
						if(rows2>0){
							for(int j=0; j<rows2; j++){
								String mbxzqh_dm = StringEx.sNull(d_xzqh_bgmxb.getItemAny(0, "MBXZQH_DM"));
								String ysxzqh_dm = StringEx.sNull(d_xzqh_bgmxb.getItemAny(0, "YSXZQH_DM"));
								String mbxzqh_mc = StringEx.sNull(d_xzqh_bgmxb.getItemAny(0, "MBXZQH_MC"));
								String ysxzqh_mc = StringEx.sNull(d_xzqh_bgmxb.getItemAny(0, "YSXZQH_MC"));
								String bglx_dm = StringEx.sNull(d_xzqh_bgmxb.getItemAny(0, "BGLX_DM"));
								String lrsj = StringEx.sNull(d_xzqh_bgmxb.getItemAny(0, "LRSJ"));
								if(!lrsj.equals("")&&lrsj.length()>=19){
									lrsj = lrsj.substring(0, 19);
								}
								boolean flag=true;
								if(bglx_dm.equals(Common.ADD)){
									AddCheckXzqhImp addCheck= new AddCheckXzqhImp();
									flag = addCheck.checkXzqh("",mbxzqh_dm,groupxh,lrsj,sbxzqh_dm);
								}else if(bglx_dm.equals(Common.CHANGE)){
									ChangeCheckXzqhImp changeCheck= new ChangeCheckXzqhImp();
									flag = changeCheck.checkXzqh(ysxzqh_dm,mbxzqh_dm,groupxh,lrsj,sbxzqh_dm);							
								}else if(bglx_dm.equals(Common.MERGE)){
									MergeCheckXzqhImp mergeCheck= new MergeCheckXzqhImp();
									flag = mergeCheck.checkXzqh(ysxzqh_dm,"",groupxh,lrsj,sbxzqh_dm);
								}else if(bglx_dm.equals(Common.MOVE)){					
									MoveCheckXzqhImp moveCheck= new MoveCheckXzqhImp();
									flag = moveCheck.checkXzqh(ysxzqh_dm,mbxzqh_dm,groupxh,lrsj,sbxzqh_dm);
								}
								if(flag){
									this.restoreXzqh(ysxzqh_dm,ysxzqh_mc,bglx_dm,mbxzqh_dm,mbxzqh_mc,lrsj,ut);
									d_xzqh_bgmxb.deleteRow(0L);
									d_xzqh_bgmxb.setTransObject(ut);
									d_xzqh_bgmxb.update(false);
								}
								
							}
						}
						d_xzqh_bggroup.deleteRow(0L);
						d_xzqh_bggroup.setTransObject(ut);
						d_xzqh_bggroup.update(false);
					}
				}
				
				//
				D_xzqh_jzbgzip zip = new D_xzqh_jzbgzip();
				zip.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
				zip.setFilter("SQDXH ='"+sqdxh+"'");
				long row = zip.retrieve();
				if(row>0){
					zip.setItemString(0, "SQDXH", "");
					zip.setItemString(0, "JZBGZT_DM", "41");
					zip.setItemString(0, "BZ", "国家审核不通过："+StringEx.sNull(d_xzqh_bgsqd.getItemAny(0L, "SPYJ")));
					zip.setTransObject(ut);
					zip.update(false);			
				}
				
				d_xzqh_bgsqd.deleteRow(0L);
				d_xzqh_bgsqd.setTransObject(ut);
				d_xzqh_bgsqd.update(false);
				
				
				ut.commit();
			}catch(Exception e){
				ut.rollback();
				throw e;
			}
		}

	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param xdo
	 * @param czry_dm
	 * @throws Exception
	 * @author lijl
	 * @since 2009-9-22
	 */
	public void restoreXzqh(String ysxzqh_dm,String ysxzqh_mc, String bglx_dm, String mbxzqh_dm,String mbxzqh_mc, String lrsj, UserTransaction ut) throws Exception 
	{	
		if(bglx_dm.equals(Common.MERGE)){			
			D_dm_xzqh_ylsj dw1 = new D_dm_xzqh_ylsj();
			dw1.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
			StringBuffer sql1 = new StringBuffer(
					"XYBZ = 'N' AND YXBZ = 'N' AND to_char(YXQ_Z,'YYYY-MM-DD hh24:mi:ss')='").append(lrsj).append("' and XZQH_DM='");
			sql1.append(ysxzqh_dm);
			sql1.append("'");
			dw1.setFilter(sql1.toString());
			long count1 = dw1.retrieve();
			if(count1>0){
				dw1.setItemString(0L, "YXQ_Z", "");
				dw1.setItemString(0L, "XYBZ", "Y");
				dw1.setItemString(0L, "YXBZ", "Y");
				dw1.setItemString(0L, "XGSJ", XtDate.getDBTime());
				dw1.setTransObject(ut);
				dw1.update(false);
			}
		}else{
			XzqhbgCommon bgCommon= new XzqhbgCommon();
			String sjXzqhmc = bgCommon.getSjxzqhMc("dm_xzqh_ylsj",Common.getSjxzqhdm(ysxzqh_dm));
			if(Common.checkMcbg(ysxzqh_dm, mbxzqh_dm)){
				StringBuffer sqlSb1 = new StringBuffer("update DM_XZQH_YLSJ y set y.XZQH_MC = '");
				sqlSb1.append(ysxzqh_mc);
				sqlSb1.append("', y.XZQH_JC='");
				sqlSb1.append(ysxzqh_mc);
				sqlSb1.append("'");
				sqlSb1.append("where y.xzqh_dm='").append(ysxzqh_dm).append("'");
				DataWindow updateDw1 = DataWindow.dynamicCreate(sqlSb1.toString());
				updateDw1.setTransObject(ut);
				updateDw1.update(false);
				
				StringBuffer sqlSb2 = new StringBuffer("update DM_XZQH_YLSJ y set y.XZQH_QC=replace(y.XZQH_QC,'");
				sqlSb2.append(sjXzqhmc+mbxzqh_mc);
				sqlSb2.append("','").append(sjXzqhmc+ysxzqh_mc).append("')");
				sqlSb2.append("where y.xzqh_dm like'").append(Common.getJbdm(ysxzqh_dm)).append("%'");
				DataWindow updateDw2 = DataWindow.dynamicCreate(sqlSb2.toString());
				updateDw2.setTransObject(ut);
				updateDw2.update(false);
			}else{
				List list = this.getXjXzqh(ysxzqh_dm,mbxzqh_dm,bglx_dm);
				if(list==null||list.size()<=0){
					return;
				}
				for(int i=0;i<list.size();i++){
					
					XzqhbgBean bean = (XzqhbgBean)list.get(i);
					String srcXzqh_dm = bean.getSrcXzqhdm();
					String destXzqh_dm = bean.getDestXzqhdm();
					String bglxdm = bean.getBglxdm();
					D_dm_xzqh_ylsj dw = new D_dm_xzqh_ylsj();
					dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
					StringBuffer sql = new StringBuffer(
							"XYBZ = 'Y' AND YXBZ = 'Y' and XZQH_DM='");
					sql.append(destXzqh_dm);
					sql.append("'");
					dw.setFilter(sql.toString());
					long count = dw.retrieve();
					if(count>0){
						if(bglxdm.equals(Common.ADD)){
							dw.deleteRow(0L);
							dw.setTransObject(ut);
							dw.update(false);
						}else if(bglxdm.equals(Common.CHANGE)||bglxdm.equals(Common.MOVE)){				
							if(Common.checkMcbg(ysxzqh_dm, mbxzqh_dm)){
								continue;
							}
							dw.deleteRow(0L);
							dw.setTransObject(ut);
							dw.update(false);
							
							D_dm_xzqh_ylsj dw1 = new D_dm_xzqh_ylsj();
							dw1.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
							StringBuffer sql1 = new StringBuffer(
									"XYBZ = 'N' AND YXBZ = 'N' AND to_char(YXQ_Z,'YYYY-MM-DD hh24:mi:ss')='").append(lrsj).append("' and XZQH_DM='");
							sql1.append(srcXzqh_dm);
							sql1.append("'");
							dw1.setFilter(sql1.toString());
							long count1 = dw1.retrieve();
							if(count1>0){
								dw1.setItemString(0L, "YXQ_Z", "");
								dw1.setItemString(0L, "XYBZ", "Y");
								dw1.setItemString(0L, "YXBZ", "Y");
								dw1.setItemString(0L, "XGSJ", XtDate.getDBTime());
								dw1.setTransObject(ut);
								dw1.update(false);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * <p>方法名称：getXjXzqh</p>
	 * <p>方法描述：</p>
	 * @param ysxzqh_dm 原始区划代码
	 * @param mbxzqh_dm 目标区划代码
	 * @param bglx_dm 变更类型代码
	 * @throws Exception
	 * @author pengld
	 * @since 2009-9-22
	 */
	public List getXjXzqh(String ysxzqh_dm,String mbxzqh_dm,String bglx_dm) throws Exception{
		String ysjbdm = Common.getJbdm(ysxzqh_dm);
		String mbjbdm = Common.getJbdm(mbxzqh_dm);
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT X.XZQH_DM,X.XZQH_MC,X.JCDM FROM V_DM_XZQH_YLSJ X WHERE X.JBDM LIKE '");
		sql.append(mbjbdm);
		sql.append("%'");
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		if (xzqhDw == null) {
			return null;
		} else {
			for (long j = 0; j<count; j++) {		
				String xzqhdm = StringEx.sNull(xzqhDw.getItemAny(j, "XZQH_DM"));
				XzqhbgBean bean = new XzqhbgBean();
				bean.setBglxdm(bglx_dm);
				bean.setDestXzqhdm(xzqhdm);
				bean.setSrcXzqhdm(xzqhdm.replaceAll(mbjbdm,ysjbdm));
				list.add(bean);
			}
		}
		return list;
	}
	
	/**
	 * <p>方法名称：getCount</p>
	 * <p>方法描述：获取申请单下所有的明细数据总数</p>
	 * @param sqdxh 申请单序号
	 * @throws Exception
	 * @author lijl
	 * @since 2010-01-28
	 */
	public String getCount(String sqdxh) throws Exception{
		StringBuffer sql = new StringBuffer("select count(*) ZS from xzqh_bgmxb where groupxh in(select groupxh from xzqh_bggroup where sqdxh='");
		sql.append(sqdxh);
		sql.append("')");
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		String rows = "0";
		if(count>0){
			rows = String.valueOf(xzqhDw.getItemAny(0, "ZS"));
		}
		return rows;
	}
}
