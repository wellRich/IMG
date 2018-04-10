<%@ include file="../../../../work/public/head.jsp" %>

<HTML >
<HEAD>
<TITLE>变更申请确认</TITLE>
<script type="text/javascript" src="./js/bgsqqr.js"></script>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY>
<fieldset style="margin-top:-10px;"><legend>未提交变更对照表的区划</legend>
<table border="0">
<tr>
<td>
<logic:present name="XJSQD">
	<display:table name="XJSQD" id="mxbMap" align="center"  pagesize="10"   class="tab"  requestURI="BgsqqrService.queryWtj.do">
	<display:column style="width:20%" property="XZQH_DM" title="区划代码" sortable="true"/>
	<display:column style="width:80%" property="XZQH_QC" title="区划名称" />
	</display:table>
</logic:present>
<logic:notPresent name="XJSQD">
<display:table name="XJSQD" align="center" id="mxbMap" requestURI="BgsqqrService.queryWtj.do"  pagesize="10" class="tab">
		<display:column  title="区划代码" style="width:20%" />
		<display:column title="区划名称" style="width:80%"/>
	</display:table>
</logic:notPresent>
</td>
</tr>
</table>
</fieldset>
<input id="wtj" type="hidden" value='<%=map.get("WTJ")%>'/>

</body>
</html>