package com.padis.business.xzqhwh.zxbg.qhztcx;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p>
 * Description: 操作日志服务类（CzrzcxService）的manager,提供CzrzcxService底层处理的方法
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @since 2008-01-12
 * @author 李靖亮
 * @version 1.0
 * @修改人：张金成
 * @修改时间：2007-02-25
 * @修改内容：删除文档的方法有问题，当附件为空时，程序报错
 */

public class QhztcxManager {

	/**
	 * <p>方法名称：queryXjXzqh()，查询下级行政区划列表</p>
	 * <p>方法说明：查询下级行政区划列表的方法，本方法将会往DM_XZQH_YLSJ表中检索出多条记录。</p>
	 * @since 2009-11-13
	 * @author LIJL
	 * String 行政区划列表
	 * @throws Exception
	 */
	public String queryXjXzqh(XMLDataObject xmldo ,long[] args) throws Exception {
		String xzqh_dm = StringEx.sNull(xmldo.getItemValue("XZQH_DM"));
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //每页的数据条数
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //页的索引
		pageSize = pageSize.equals("") ? "20" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("select XZQH_DM,XZQH_MC,decode(YXBZ,'Y','有效','N','禁用') YXBZ from DM_XZQH_YLSJ where SJ_XZQH_DM ='");
		queryBuffer.append(xzqh_dm);
		queryBuffer.append("' and instr(XZQH_DM,'L')<1 order by XZQH_DM asc");
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
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
}
