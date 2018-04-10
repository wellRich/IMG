<%@ include file="../../../../work/public/head.jsp" %>
<%@ page import="com.padis.business.xzqhwh.common.XzqhManager"%><%	
	XzqhManager bghMgr = new XzqhManager();
	bghMgr.setDb("V_DM_XZQH_YLSJ");
	XzqhManager bgqMgr = new XzqhManager();
	bgqMgr.setDb("V_DM_XZQH");
	String xzqhdm = (String)map.get("XZQH_DM");
	String dwlsgxStr = (String)map.get("DWLSGX_DM");
	int dwlsgx=10;
	if(xzqhdm==null){
		xzqhdm="";
	}
	if(dwlsgxStr!=null){
		dwlsgx = Integer.parseInt(dwlsgxStr);
	}
%>
<HTML>
<HEAD>
<TITLE>区划变更维护</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script language="javascript" src="../../../../work/public/js/common.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/js/controlService.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../../console/public/js/service.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/grid/js/grid.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/js/check.js"></SCRIPT>

<!--业务要使用的JS文件-->
<SCRIPT LANGUAGE="JavaScript" src="./js/xzqhbgdzb_index.js"></SCRIPT>
</HEAD>
<BODY> 
<form name="forForm" action="QhdmylService.addXzqhbgsqb.do" method="post">
	<fieldset style="margin-top:-10px;"><legend>变更后区划内容</legend>
	<table>
	<tr>
		<td width="16%"><select id="HXZQH_SHENG" size="10" style="width:120px;height:160px;" onchange="getBghXjXzqh(this,'','1')"><%=bghMgr.getShengOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="16%"><select id="HXZQH_SHI" size="10" style="width:120px;height:160px;" onchange="getBghXjXzqh(this,'','1')"><%=bghMgr.getShiOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="HXZQH_XIAN" size="10" style="width:120px;height:160px;" onchange="getBghXjXzqh(this,'','1')"><%=bghMgr.getXianOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="HXZQH_XIANG" size="10" style="width:120px;height:160px;" onchange="getBghXjXzqh(this,'','1')"><%=bghMgr.getXiangOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="HXZQH_CUN" size="10" style="width:120px;height:160px;" onchange="getBghXjXzqh(this,'','1')" 
		><%=bghMgr.getCunOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="HXZQH_ZU" size="10" style="width:120px;height:160px;"><%=bghMgr.getZuOptions(xzqhdm,dwlsgx,null,true)%></select></td>
       </tr>
	  </table>
	  </fieldset>
	  <fieldset style="margin-top:-18px;"><legend>变更前区划内容</legend>
		<table>
		<tr>
			<td width="16%"><select id="QXZQH_SHENG" size="10" style="width:120px;height:160px;" onchange="getBgqXjXzqh(this,'','1')"><%=bgqMgr.getShengOptions(xzqhdm,dwlsgx,null,true)%></select></td>
			<td width="16%"><select id="QXZQH_SHI" size="10" style="width:120px;height:160px;" onchange="getBgqXjXzqh(this,'','1')"><%=bgqMgr.getShiOptions(xzqhdm,dwlsgx,null,true)%></select></td>
			<td width="17%"><select id="QXZQH_XIAN" size="10" style="width:120px;height:160px;" onchange="getBgqXjXzqh(this,'','1')"><%=bgqMgr.getXianOptions(xzqhdm,dwlsgx,null,true)%></select></td>
			<td width="17%"><select id="QXZQH_XIANG" size="10" style="width:120px;height:160px;" onchange="getBgqXjXzqh(this,'','1')"><%=bgqMgr.getXiangOptions(xzqhdm,dwlsgx,null,true)%></select></td>
			<td width="17%"><select id="QXZQH_CUN" size="10" style="width:120px;height:160px;" onchange="getBgqXjXzqh(this,'','1')" 
			><%=bgqMgr.getCunOptions(xzqhdm,dwlsgx,null,true)%></select></td>
			<td width="17%"><select id="QXZQH_ZU" size="10" style="width:120px;height:160px;" ><%=bgqMgr.getZuOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		   </tr>
		  </table>
	  </fieldset>
	  <table>
		<tr>
			<td width="20%" align="right">变更前代码：</td><td width="30%" id="qxzqh_dm" align="left"></td>
			<td width="20%" align="right">变更前名称：</td><td width="30%" id="qxzqh_mc" align="left"></td>			
		</tr>
		<tr>
			<td width="20%" align="right">变更后代码：</td><td width="30%" id="hxzqh_dm" align="left"></td>
			<td width="20%" align="right">变更后名称：</td><td width="30%" id="hxzqh_mc" align="left"></td>			
		</tr>
	  </table>
</form>
</BODY>
</HTML>