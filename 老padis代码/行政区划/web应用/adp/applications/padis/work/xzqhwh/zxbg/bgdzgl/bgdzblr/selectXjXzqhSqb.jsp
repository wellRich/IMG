<%@ include file="../../../../../work/public/head.jsp" %>
<%
	String jb_dm = (String)request.getParameter("JB_DM");
	String bglx_dm = (String)request.getParameter("XZQHBGLX_DM");
	
	if(jb_dm==null){
		jb_dm="";
	}
	if(bglx_dm==null){
		bglx_dm="";
	}
%>
<HTML>
<HEAD>
<TITLE>����������ҪǨ�Ƶ��¼���������</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<SCRIPT LANGUAGE="JavaScript" src="../../../../../console/public/js/service.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../../../work/public/grid/js/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../../../work/public/grid/js/grid.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../../../work/public/js/check.js"></SCRIPT>

<!--ҵ��Ҫʹ�õ�JS�ļ�-->
<SCRIPT LANGUAGE="JavaScript" src="./js/xjxzqhGrid.js"></SCRIPT>
</HEAD>
<BODY onkeydown="isEnter(event)"> 
<form name="forForm" action="BgdzblrService.addXzqhbgsqb.do" method="post">
	<input type="hidden" name="JB_DM" value="<%=jb_dm%>"/>
	<input type="hidden" name="XZQHBGLX_DM" value="<%=bglx_dm%>"/>
	<center style="margin-top:8px;">
		<font color="red">���ֹ�¼��Ǩ�ƺ���������</font>	
	</center>
	<fieldset style="margin-top:-10px;"><legend>����������ҪǨ�Ƶ��¼���������</legend>
	<table>
	<tr><td>
	<div id="xjxzqh" style="width:100%;height=360px; overflow: scroll;overflow:auto;">
	</div>
	</td></tr>
	</table>
	</fieldset>
	 <center>
		<button id="btn_add" onclick="addxjxzqhGrid()">ȷ ��</button>	
	</center>
</form>
</BODY>
</HTML>