<%@ include file="../../../../../work/public/head.jsp" %>
<%	
	String xzqhDm = "";
	String sjxzqhDm = "";
	if(map!=null){
		xzqhDm = (String)map.get("XZQHDM");
		sjxzqhDm = (String)map.get("SJXZQH_DM");
	}
%>
<HTML >
<HEAD>
<TITLE>按变更类型分的区划变更情况汇总表</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="js/index.js"></script>
</HEAD>
<BODY>
<form method="POST" name="form2" >
<table border="0">
<input type="hidden" name="XZQHDM" value="<%=xzqhDm%>"/>
<input type="hidden" name="SJXZQH_DM" value="<%=sjxzqhDm%>"/>
<tr>
<td>
<logic:present name="ITEMS">
	<display:table name="ITEMS" id="mxbMap" align="center" class="tab"  requestURI="AbglxcxService.queryMxb.do" style="width:97%;">
	<bean:define id="xzqh_dm" name="mxbMap" property="XZQH_DM"/>
	<bean:define id="xzqh_mc" name="mxbMap" property="XZQH_MC"/>
	<bean:define id="jc_dm" name="mxbMap" property="JC_DM"/>
	<display:column style="width:25%;text-align:left" title="省（区、市）" sortable="true">
		<%if(!jc_dm.equals("3")){%>
			<a href="JavaScript:getXjxzqh('<%=xzqh_dm%>');"><%=xzqh_mc%></a>
		<%}else{%>
			<%=xzqh_mc%>
		<%}%>
	</display:column>
	<display:column style="width:20%;text-align:right" property="XINZENG" title="新增" sortable="true"/>
	<display:column style="width:20%;text-align:right" property="BIANGENG"title="变更" sortable="true"/>
	<display:column style="width:20%;text-align:right" property="QIANYI" title="迁移" sortable="true"/>
	<display:column style="width:20%;text-align:right" property="BINGRU" title="并入" sortable="true"/>	
	</display:table>
</logic:present>
<logic:notPresent name="ITEMS">
<display:table name="ITEMS" align="center" id="mxbMap" requestURI="AbglxcxService.queryMxb.do" class="tab" style="width:97%;margin-top:15px">
		<display:column title="省（区、市）" style="width:20%"/>
		<display:column title="新增" style="width:20%"/>
		<display:column title="变更" style="width:20%"/>
		<display:column title="迁移" style="width:20%"/>
		<display:column title="并入" style="width:20%"/>
	</display:table>
</logic:notPresent>
</td>
</tr>
</table>
<center>
	<button id="btn_exp" accesskey="b"  onclick="goBack()">返 回(<u>B</u>)</button>
	<button id="btn_exp" accesskey="e"  onclick="exportQhbgqkhzb()">导出打印(<u>E</u>)</button>
</center>
</form>

</body>
</html>