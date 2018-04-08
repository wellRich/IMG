<%@ include file="../../../work/public/head.jsp" %>
<%@ page import="com.padis.business.xzqhwh.lgywsjsc.LgywsjscManager"%><%	
	LgywsjscManager mgr = new LgywsjscManager();
	String xzqhdm = (String)map.get("XZQH_DM");
	String dwlsgxStr = (String)map.get("DWLSGX_DM");
	String jc_dm = (String)map.get("JC_DM");
	int dwlsgx=10;
	if(xzqhdm==null){
		xzqhdm="";
	}
	if(dwlsgxStr!=null){
		dwlsgx = Integer.parseInt(dwlsgxStr);
	}
	if(jc_dm==null){
		jc_dm = "1";
	}
%>
<HTML>
<HEAD>
<TITLE>行政区划变更维护</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script language="javascript" src="../../../work/public/js/common.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../../../work/public/js/controlService.js"></SCRIPT>

<!--业务要使用的JS文件-->
<SCRIPT LANGUAGE="JavaScript" src="./js/index.js"></SCRIPT>
</HEAD>
<BODY onload="init(<%=jc_dm %>)"> 
<form name="forForm" action="LgywsjscService.deleteXzqh.do" method="post">
	<fieldset style="margin-top:-10px;"><legend>行政区划变更内容</legend>
	<table>
	<tr>
		<td width="16%"><select id="XZQH_SHENG" size="10" style="width:120px;height:160px;" onclick="getXzqhMcDm(this)" onchange="getXjXzqh(this,'')"><%=mgr.getShengOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="16%"><select id="XZQH_SHI" size="10" style="width:120px;height:160px;" onclick="getXzqhMcDm(this)" onchange="getXjXzqh(this,'')"><%=mgr.getShiOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="XZQH_XIAN" size="10" style="width:120px;height:160px;" onclick="getXzqhMcDm(this)" onchange="getXjXzqh(this,'')"><%=mgr.getXianOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="XZQH_XIANG" size="10" style="width:120px;height:160px;" onclick="getXzqhMcDm(this)" onchange="getXjXzqh(this,'')"><%=mgr.getXiangOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="XZQH_CUN" size="10" style="width:120px;height:160px;" onclick="getXzqhMcDm(this)" onchange="getXjXzqh(this,'')" 
		><%=mgr.getCunOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="XZQH_ZU" size="10" style="width:120px;height:160px;" onclick="getXzqhMcDm(this)" ><%=mgr.getZuOptions(xzqhdm,dwlsgx,null,true)%></select></td>
       </tr>
	  </table>
	  </fieldset>
	  <table>
		<tr>
			<td width="14%" align="right">行政区划名称：</td><td width="36%"><input type="text" name="XZQH_MC" value="" disabled="true"/></td>
			<td width="14%" align="right">行政区划代码：</td><td width="36%"><input type="text" name="XZQH_DM" value="" disabled="true"/></td>
		</tr>
	  </table>
	  <center>
		<button id="query_btn"onclick="subquery()" accesskey="q">查 询(<u>Q</u>)</button>
	  </center>
	   <iframe name="delIframe" width="100%" id="delIframe" frameborder="0" scrolling="auto" src="" allowTransparency="true" height="250px"></iframe>
</form>
</BODY>
</HTML>