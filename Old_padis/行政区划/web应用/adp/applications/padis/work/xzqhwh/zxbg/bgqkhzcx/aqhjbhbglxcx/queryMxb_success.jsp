<%@ include file="../../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>按区划级别和变更类型查询区划变更情况</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript">
	function exportQhbgqkhzb()
	{
		var hzmc = window.parent.document.getElementById("YYHZMC").value;
		location="AqhjbhbglxcxService.exportQhbgqkhzb.do?HZMC="+hzmc;
	}
</script>
</HEAD>
<BODY >
<form method="POST" name="form2" >
<table border="0">
<tr>
<td>
<logic:present name="ITEMS">
	<display:table name="ITEMS" id="mxbMap" align="center" class="tab"  requestURI="AqhjbhbglxcxService.queryMxb.do" style="width:97%;margin-top:15px">
	<display:column style="width:20%" property="BGLX_MC" title="变更类型" sortable="true"/>
	<display:column style="width:13%;text-align:right" property="BGS_SHENG" title="省级" sortable="true"/>
	<display:column style="width:13%;text-align:right" property="BGS_SHI" title="地级" sortable="true"/>
	<display:column style="width:13%;text-align:right" property="BGS_XIAN" title="县级" sortable="true"/>
	<display:column style="width:13%;text-align:right" property="BGS_XIANG" title="乡级" sortable="true"/>	
	<display:column style="width:13%;text-align:right" property="BGS_CUN" title="村级" sortable="true"/>
	<display:column style="width:15%;text-align:right" property="BGS_XIAOJI" title="小计" sortable="true"/>
	</display:table>
</logic:present>
<logic:notPresent name="ITEMS">
<display:table name="ITEMS" align="center" id="mxbMap" requestURI="AqhjbhbglxcxService.queryMxb.do"  pagesize="10" class="tab" style="width:97%;margin-top:15px">
		<display:column title="变更类型" style="width:20%"/>
		<display:column title="地级" style="width:13%"/>
		<display:column title="市级" style="width:13%"/>
		<display:column title="县级" style="width:13%"/>
		<display:column title="乡级" style="width:13%"/>
		<display:column title="村级" style="width:13%"/>
		<display:column title="小计" style="width:15%"/>
	</display:table>
</logic:notPresent>
</td>
</tr>
</table>
	<center>
		<button id="btn_exp" accesskey="e"  onclick="exportQhbgqkhzb()">导出打印(<u>E</u>)</button>
	</center>
</form>

</body>
</html>