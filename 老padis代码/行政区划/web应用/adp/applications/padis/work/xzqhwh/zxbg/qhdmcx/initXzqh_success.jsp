<%@ include file="../../../../work/public/head.jsp" %>
<%@ page import="com.padis.business.xzqhwh.common.XzqhManager"%><%
	XzqhManager mgr = new XzqhManager();
	String xzqhdm = (String)map.get("XZQH_DM");
	String dwlsgxStr = (String)map.get("DWLSGX_DM");
	String flag = (String)map.get("FLAG");
	if(flag.equals("0")){
		mgr.setDb("V_DM_XZQH_YLSJ");
	}
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
<TITLE>�������ά��</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script language="javascript" src="../../../../work/public/js/common.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/js/controlService.js"></SCRIPT>

<!--ҵ��Ҫʹ�õ�JS�ļ�-->
<SCRIPT LANGUAGE="JavaScript" src="./js/qhdmcx_index.js"></SCRIPT>
</HEAD>
<BODY> 
<form name="forForm" action="" method="post" target="queryIframe">
	<fieldset style="margin-top:-10px;"><legend>���������ѯ</legend>
	<table>
	<tr>
		<td width="16%"><select id="XZQH_SHENG" size="10" style="width:120px;height:160px;" onchange="getXjXzqh(this,'<%=mgr.getDb()%>');queryXzqh(this,'<%=mgr.getDb()%>')"><%=mgr.getShengOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="16%"><select id="XZQH_SHI" size="10" style="width:120px;height:160px;"  onchange="getXjXzqh(this,'<%=mgr.getDb()%>');queryXzqh(this,'<%=mgr.getDb()%>')"><%=mgr.getShiOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="XZQH_XIAN" size="10" style="width:120px;height:160px;"  onchange="getXjXzqh(this,'<%=mgr.getDb()%>');queryXzqh(this,'<%=mgr.getDb()%>')"><%=mgr.getXianOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="XZQH_XIANG" size="10" style="width:120px;height:160px;"  onchange="getXjXzqh(this,'<%=mgr.getDb()%>');queryXzqh(this,'<%=mgr.getDb()%>')"><%=mgr.getXiangOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="XZQH_CUN" size="10" style="width:120px;height:160px;"  onchange="getXjXzqh(this,'<%=mgr.getDb()%>');queryXzqh(this,'<%=mgr.getDb()%>')"><%=mgr.getCunOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="XZQH_ZU" size="10" style="width:120px;height:160px;" onchange="queryXzqh(this,'<%=mgr.getDb()%>')" ><%=mgr.getZuOptions(xzqhdm,dwlsgx,null,true)%></select></td>
       </tr>
	  </table>
	  </fieldset>
	  	<fieldset style="margin-top:-10px;"><legend>������ϸ��Ϣ</legend>
	  <table>
	  	<tr height="20px">
		<td colspan="2">����ȫ�ƣ�<label id="XZQH_QC"></label></td>
		</tr>
		<tr height="20px">
		<td width="50%">�������룺<label id="XZQH_DM"></label></td>
		<td width="50%">�������ƣ�<label id="XZQH_MC"></label></td>
		</tr>
		<tr height="20px">
		<td width="50%">��λ������ϵ��<label id="DWLSGX"></label></td>
		<td width="50%">����ڵ��־��<label id="XNJD_BZ"></label></td>
		<tr height="20px">
		<td width="50%">�ϼ��������ƣ�<label id="SJ_XZQH_MC"></label></td>
		<td width="50%">�����������ƣ�<label id="XZQHLX"></label></td>
		</tr>
	  </table>
</fieldset>
</form>
</BODY>
</HTML>