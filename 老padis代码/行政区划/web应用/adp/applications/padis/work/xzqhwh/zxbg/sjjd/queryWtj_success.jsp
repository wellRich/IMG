<%@ include file="../../../../work/public/head.jsp" %>
<%
	int count=0;
%>
<HTML >
<HEAD>
<TITLE>未完成变更对照表的区划</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY>

<table border="0">
<tr>
<td>
<logic:present name="WTJITEMS">
	<display:table name="WTJITEMS" id="mxbMap" align="center" class="tab" requestURI="SjjdService.queryWtj.do">
		<display:column style="width:10%" title="序号"><%=++count%></display:column>
		<display:column style="width:25%" property="XZQH_DM" title="区划代码" sortable="true"/>
		<display:column style="width:45%;text-align:left" property="XZQH_MC" title="区划名称" sortable="true"/>
		<display:column style="width:20%;text-align:left" property="SQDZT_DM" title="对照表状态" sortable="true"/>
	</display:table>
</logic:present>
<logic:notPresent name="WTJITEMS">
	<display:table name="WTJITEMS" align="center" id="mxbMap" requestURI="SjjdService.queryWtj.do" class="tab">
		<display:column style="width:10%" title="序号"/>
		<display:column  title="区划代码" style="width:25%"/>
		<display:column title="区划名称" style="width:45%"/>
		<display:column style="width:20%" title="对照表状态"/>
	</display:table>
</logic:notPresent>
</td>
</tr>
</table>
</body>
</html>