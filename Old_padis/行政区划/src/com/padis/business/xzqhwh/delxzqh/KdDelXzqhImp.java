package com.padis.business.xzqhwh.delxzqh;

import java.util.List;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

public class KdDelXzqhImp extends DelxzqhImp{
	
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
				//查询快速调查常规监测报表信息
				if(!this.queryRkjhsytj(xzqhDm)){
					temp = false;
				}
				if(!this.queryNdjyqk(xzqhDm)){
					temp = false;
				}
				if(!this.queryCgjcJgxx(xzqhDm)){
					temp = false;
				}
				//查询快速调查监测点信息
				if(!this.queryKdjcdxx(xzqhDm)){
					temp = false;
				}
				//查询快速调查行政区划分组信息
				if(!this.queryKdfzxx(xzqhDm)){
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
	 * @author zhanglw
	 * @throws Exception 
	 * @since 2009-8-4
	 */
	private boolean queryRkjhsytj(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select ND, count(1) COUNTS");
		sqlStr.append(" from KD_CGJC_RKJHSYTJ");
		sqlStr.append(" where XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("' group by ND");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rows = dw.retrieve();
		if(rows != 0L){
			int count = 0;
			XMLDataObject xdo = dw.toXDO();
			StringBuffer bz = new StringBuffer();
			bz.append("常规监测-人口与出生情况统计表中有");
			for(int i = 0;i<rows;i++){
				String nd = StringEx.sNull(xdo.getItemAny(i, "ND"));
				String counts = StringEx.sNull(xdo.getItemAny(i, "COUNTS"));
				bz.append(nd);
				bz.append("年度的数据");
				bz.append(counts);
				bz.append("条");
				if(i != rows-1){
					bz.append(",");
				}
				count = count + Integer.parseInt(counts);
			}
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("KD_CGJC_RKJHSYTJ");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("K");
			delXzqhBean.setSjl(count);
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
	private boolean queryNdjyqk(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select ND, count(1) COUNTS");
		sqlStr.append(" from KD_CGJC_NDJYQK");
		sqlStr.append(" where XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("' group by ND");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rows = dw.retrieve();
		if(rows != 0L){
			int count = 0;
			XMLDataObject xdo = dw.toXDO();
			StringBuffer bz = new StringBuffer();
			bz.append("常规监测-节育情况统计表中有");
			for(int i = 0;i<rows;i++){
				String nd = StringEx.sNull(xdo.getItemAny(i, "ND"));
				String counts = StringEx.sNull(xdo.getItemAny(i, "COUNTS"));
				bz.append(nd);
				bz.append("年度的数据");
				bz.append(counts);
				bz.append("条");
				if(i != rows-1){
					bz.append(",");
				}
				count = count + Integer.parseInt(counts);
			}
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("KD_CGJC_NDJYQK");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("K");
			delXzqhBean.setSjl(count);
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
	private boolean queryCgjcJgxx(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select T.ND, count(1) COUNTS");
		sqlStr.append(" from KD_CGJC_TBXX T, RS_JGXX J");
		sqlStr.append(" where T.QX_JGDM = J.JG_DM");
		sqlStr.append(" and J.XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("' and J.YXBZ = 'Y'");
		sqlStr.append(" group by T.ND");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rows = dw.retrieve();
		if(rows != 0L){
			int count = 0;
			XMLDataObject xdo = dw.toXDO();
			StringBuffer bz = new StringBuffer();
			bz.append("常规监测-该地区有");
			for(int i = 0;i<rows;i++){
				String nd = StringEx.sNull(xdo.getItemAny(i, "ND"));
				String counts = StringEx.sNull(xdo.getItemAny(i, "COUNTS"));
				bz.append(nd);
				bz.append("年度填报数据");
				bz.append(counts);
				bz.append("条");
				if(i != rows-1){
					bz.append(",");
				}
				count = count + Integer.parseInt(counts);
			}
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("KD_CGJC_TBXX,RS_JGXX");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("K");
			delXzqhBean.setSjl(count);
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
	private boolean queryKdjcdxx(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select count(1) COUNTS");
		sqlStr.append(" from KD_JCDXX");
		sqlStr.append(" where XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("'");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		dw.retrieve();
		String counts = StringEx.sNull(dw.getItemAny(0L, "COUNTS"));
		if(Integer.parseInt(counts) != 0L){
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("KD_JCDXX");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("K");
			delXzqhBean.setSjl(Integer.parseInt(counts));
			delXzqhBean.setBz("该行政区划在监测点-监测县表中存在数据"+Integer.parseInt(counts)+"条");
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
	private boolean queryKdfzxx(String xzqhDm) throws Exception{
		boolean temp = true;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select count(1) COUNTS");
		sqlStr.append(" from KD_XZQHFZ_FZCY");
		sqlStr.append(" where XZQH_DM = '");
		sqlStr.append(xzqhDm);
		sqlStr.append("'");
		DataWindow dw = DataWindow.dynamicCreate(sqlStr.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		dw.retrieve();
		String counts = StringEx.sNull(dw.getItemAny(0L, "COUNTS"));
		if(Integer.parseInt(counts) != 0L){
			DelxzqhBean delXzqhBean = new DelxzqhBean();
			delXzqhBean.setXzqhdm(xzqhDm);
			delXzqhBean.setYwbmc("KD_XZQHFZ_FZCY");
			delXzqhBean.setYwbxzqhmc("XZQH_DM");
			delXzqhBean.setYwxtdm("K");
			delXzqhBean.setSjl(Integer.parseInt(counts));
			delXzqhBean.setBz("该行政区划在行政区划分组成员信息表中存在"+Integer.parseInt(counts)+"条数据");
			this.addReturnMessage(delXzqhBean);
			temp = false;
		}
		return temp;
	}
}
