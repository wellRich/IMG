package com.padis.business.xzqhwh.delxzqh;

import java.util.ArrayList;
import java.util.List;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;

public class LgDelXzqhImp extends DelxzqhImp{

	public boolean isAllowDelXzqh(List xzqhlist) {
		List messageList = getReturnMessage();
		List beanList = init();		
		boolean flag = true;
		DataWindow dw = null;
		try {
			
			for(int i=0 ; i<xzqhlist.size();i++)
			{
				String xzqhdm =xzqhlist.get(i).toString();
				for(int m=0 ;m<beanList.size();m++)
				{
					DelxzqhBean xzqhBean = (DelxzqhBean)beanList.get(m);
					StringBuffer sqlStr = new StringBuffer();
					sqlStr.append("select count(*) con from ");
					sqlStr.append(xzqhBean.getYwbmc());
					sqlStr.append(" where ");
					sqlStr.append(xzqhBean.getYwbxzqhmc());
					sqlStr.append("='");
					sqlStr.append(xzqhdm);
					sqlStr.append("'");
					dw = DataWindow.dynamicCreate(sqlStr.toString());
					dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
					dw.retrieve();
					int sum = Integer.parseInt(dw.getItemAny(0, "con").toString());
					if(sum >0)
					{
						flag=false;
						DelxzqhBean bean = new DelxzqhBean();
						bean.setYwbmc(xzqhBean.getYwbmc());
						bean.setYwbxzqhmc(xzqhBean.getYwbxzqhmc());
						bean.setXzqhdm(xzqhdm);
						bean.setYwxtdm("L");
						bean.setSjl(sum);
						bean.setBz("存在与此行政区划代码相关的业务数据"+sum+"条");
						messageList.add(bean);
					}
					
				}
			}
			beanList=null;
			return flag;


		} catch (Exception e) {
			LogManager.getLogger().log(e);
		}
		return false;
	}
	
	/**
	 * 
	 * <p>方法名称：init</p>
	 * <p>方法描述：初始化需要查询的记录</p>
	 * @return ArrayList
	 * @author pengld
	 * @since 2009-8-3
	 */
	private ArrayList init()
	{
		ArrayList beanList = new ArrayList();

		// LG_JBXX :HJDXZQH_DM
		DelxzqhBean xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_JBXX");
		xzqhBean.setYwbxzqhmc("HJDXZQH_DM");
		beanList.add(xzqhBean);

		// LG_JBXX :ZFXJDXZQH_DM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_JBXX");
		xzqhBean.setYwbxzqhmc("ZFXJDXZQH_DM");
		beanList.add(xzqhBean);

		// LG_JBXX :ZFHJDXZQH_DM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_JBXX");
		xzqhBean.setYwbxzqhmc("ZFHJDXZQH_DM");
		beanList.add(xzqhBean);

		// LG_JBXX :XJZDXZQH_DM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_JBXX");
		xzqhBean.setYwbxzqhmc("XJZDXZQH_DM");
		beanList.add(xzqhBean);
		
		// LG_LDDJ :HJDXZQH_DM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_LDDJ");
		xzqhBean.setYwbxzqhmc("HJDXZQH_DM");
		beanList.add(xzqhBean);
		
		// LG_LDDJ :XJZDXZQH_DM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_LDDJ");
		xzqhBean.setYwbxzqhmc("XJZDXZQH_DM");
		beanList.add(xzqhBean);
		
		// LG_LDDJ :ZFXJDXZQH_DM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_LDDJ");
		xzqhBean.setYwbxzqhmc("ZFXJDXZQH_DM");
		beanList.add(xzqhBean);
		
		// LG_LDDJ :ZFHJDXZQH_DM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_LDDJ");
		xzqhBean.setYwbxzqhmc("ZFHJDXZQH_DM");
		beanList.add(xzqhBean);

		// LG_TBFK :JSD_XZQHDM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_TBFK");
		xzqhBean.setYwbxzqhmc("JSD_XZQHDM");
		beanList.add(xzqhBean);
		
		// LG_TBFK :FSD_XZQHDM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_TBFK");
		xzqhBean.setYwbxzqhmc("FSD_XZQHDM");
		beanList.add(xzqhBean);
		
		// LG_XCFK :FSD_XZQHDM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_XCFK");
		xzqhBean.setYwbxzqhmc("FSD_XZQHDM");
		beanList.add(xzqhBean);
		
		// LG_XCFK :JSD_XZQHDM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_XCFK");
		xzqhBean.setYwbxzqhmc("JSD_XZQHDM");
		beanList.add(xzqhBean);
		
		// LG_HJXX :XZQH_DM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_HJXX");
		xzqhBean.setYwbxzqhmc("XZQH_DM");
		beanList.add(xzqhBean);
		
		// LG_HJXX :QRDXZQH_DM
		xzqhBean = new DelxzqhBean();
		xzqhBean.setYwbmc("LG_HJXX");
		xzqhBean.setYwbxzqhmc("QRDXZQH_DM");
		beanList.add(xzqhBean);
		
		return beanList;
		
	}
	
}
