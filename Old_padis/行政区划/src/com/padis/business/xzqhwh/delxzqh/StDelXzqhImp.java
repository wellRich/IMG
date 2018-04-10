package com.padis.business.xzqhwh.delxzqh;

import java.util.List;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

public class StDelXzqhImp extends DelxzqhImp{
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param xzqhList
	 * @return
	 * @author zhanglw
	 * @throws Exception 
	 * @since 2009-8-4
	 */
	public boolean isAllowDelXzqh(List xzqhList) {
		boolean temp = true;
		try{
			for(int i=0;i<xzqhList.size();i++){
				String xzqhDm = StringEx.sNull(xzqhList.get(i));
				//(1) 查询机构数据
				if(!this.queryRyxx(xzqhDm)){
					temp = false;
				}
				//(2) 查询人员数据
				if(!this.queryJgxx(xzqhDm)){
					temp = false;
				}
				//(3) 查询财务报表数据
				if(!this.queryCwbbsj(xzqhDm)){
					temp = false;
				}
				//(4) 查询服务站个案登记信息数据
				if(!this.queryFwzgadj(xzqhDm)){
					temp = false;
				}
				//(5) 查询服务站报表信息
				if(!this.queryFwzbbxx(xzqhDm)){
					temp = false;
				}
			}
		}catch(Exception e){
			temp = false;
			LogManager.getLogger().log(e);
		}
		return temp;
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param xzqhDm
	 * @return
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-8-4
	 */
	private boolean queryJgxx(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select count(1) COUNTS");
		sqlStr.append(" from RS_JGXX");
		sqlStr.append(" where XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("' and YXBZ = 'Y'");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		dw.retrieve();
		String counts = StringEx.sNull(dw.getItemAny(0L, "COUNTS"));
		if(Integer.parseInt(counts) != 0L){
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("RS_JGXX");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("Z");
			delXzqhBean.setSjl(Integer.parseInt(counts));
			delXzqhBean.setBz("该地区有机构数据"+Integer.parseInt(counts)+"条");
			this.addReturnMessage(delXzqhBean);
			temp = false;
		}
		return temp;
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param xzqhDm
	 * @return
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-8-4
	 */
	private boolean queryRyxx(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select count(1) COUNTS");
		sqlStr.append(" from RS_RYXX R, RS_JGXX J");
		sqlStr.append(" where R.DW_JG_DM = J.JG_DM");
		sqlStr.append(" and J.XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("' and R.YXBZ = 'Y'");
		sqlStr.append(" and J.YXBZ = 'Y'");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		dw.retrieve();
		String counts = StringEx.sNull(dw.getItemAny(0L, "COUNTS"));
		if(Integer.parseInt(counts) != 0L){
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("RS_RYXX,RS_JGXX");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("Z");
			delXzqhBean.setSjl(Integer.parseInt(counts));
			delXzqhBean.setBz("该地区有人员数据"+Integer.parseInt(counts)+"条");
			this.addReturnMessage(delXzqhBean);
			temp = false;
		}
		return temp;
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param xzqhDm
	 * @return
	 * @author zhanglw
	 * @throws Exception 
	 * @since 2009-8-4
	 */
	private boolean queryCwbbsj(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select distinct B.ND");
		sqlStr.append(" from ZJ_BBXX B, RS_JGXX J");
		sqlStr.append(" where B.QX_JGDM = J.JG_DM");
		sqlStr.append(" and J.XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("' and B.YXBZ = 'Y'");
		sqlStr.append(" and J.YXBZ = 'Y'");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rows = dw.retrieve();
		if(rows != 0L){
			XMLDataObject xdo = dw.toXDO();
			StringBuffer bz = new StringBuffer();
			bz.append("该地区有");
			for(int i = 0;i<rows;i++){
				String nd = StringEx.sNull(xdo.getItemAny(i, "ND"));
				bz.append(nd);
				bz.append("年度");
				if(i != rows-1){
					bz.append(",");
				}
			}
			bz.append("的财务统计报表数据");
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("ZJ_BBXX,RS_JGXX");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("Z");
			delXzqhBean.setSjl((int)rows);
			delXzqhBean.setBz(bz.toString());
			this.addReturnMessage(delXzqhBean);
			temp = false;
		}
		return temp;
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param xzqhDm
	 * @return
	 * @throws Exception
	 * @author zhanglw
	 * @since 2009-8-4
	 */
	private boolean queryFwzgadj(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select count(1) COUNTS");
		sqlStr.append(" from FWZ_DJXX");
		sqlStr.append(" where XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("' and YXBZ = 'Y'");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		dw.retrieve();
		String counts = StringEx.sNull(dw.getItemAny(0L, "COUNTS"));
		if(Integer.parseInt(counts) != 0L){
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("FWZ_DJXX");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("Z");
			delXzqhBean.setSjl(Integer.parseInt(counts));
			delXzqhBean.setBz("该地区有服务站个案登记信息"+Integer.parseInt(counts)+"条");
			this.addReturnMessage(delXzqhBean);
			temp = false;
		}
		return temp;
	}
	
	/**
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param xzqhDm
	 * @return
	 * @author zhanglw
	 * @throws Exception 
	 * @since 2009-8-4
	 */
	private boolean queryFwzbbxx(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select J.ND, count(1) COUNTS");
		sqlStr.append(" from FWZ_JBXX J, FWZ_DJXX D");
		sqlStr.append(" where J.FWZ_DM = D.FWZ_DM and D.XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("' group by J.ND");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rows = dw.retrieve();
		if(rows != 0L){
			int count = 0;
			XMLDataObject xdo = dw.toXDO();
			StringBuffer bz = new StringBuffer();
			bz.append("该地区有");
			for(int i = 0;i<rows;i++){
				String nd = StringEx.sNull(xdo.getItemAny(i, "ND"));
				String counts = StringEx.sNull(xdo.getItemAny(i, "COUNTS"));
				bz.append(nd);
				bz.append("年度服务站统计报表");
				bz.append(counts);
				bz.append("张");
				if(i != rows-1){
					bz.append(",");
				}
				count = count + Integer.parseInt(counts);
			}
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("FWZ_JBXX,FWZ_DJXX");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("Z");
			delXzqhBean.setSjl(count);
			delXzqhBean.setBz(bz.toString());
			this.addReturnMessage(delXzqhBean);
			temp = false;
		}
		return temp;
	}
}
