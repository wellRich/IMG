<%@ include file="../../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>����������ͱ�����Ͳ�ѯ����������</TITLE>
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
	<display:column style="width:20%" property="BGLX_MC" title="�������" sortable="true"/>
	<display:column style="width:13%;text-align:right" property="BGS_SHENG" title="ʡ��" sortable="true"/>
	<display:column style="width:13%;text-align:right" property="BGS_SHI" title="�ؼ�" sortable="true"/>
	<display:column style="width:13%;text-align:right" property="BGS_XIAN" title="�ؼ�" sortable="true"/>
	<display:column style="width:13%;text-align:right" property="BGS_XIANG" title="�缶" sortable="true"/>	
	<display:column style="width:13%;text-align:right" property="BGS_CUN" title="�弶" sortable="true"/>
	<display:column style="width:15%;text-align:right" property="BGS_XIAOJI" title="С��" sortable="true"/>
	</display:table>
</logic:present>
<logic:notPresent name="ITEMS">
<display:table name="ITEMS" align="center" id="mxbMap" requestURI="AqhjbhbglxcxService.queryMxb.do"  pagesize="10" class="tab" style="width:97%;margin-top:15px">
		<display:column title="�������" style="width:20%"/>
		<display:column title="�ؼ�" style="width:13%"/>
		<display:column title="�м�" style="width:13%"/>
		<display:column title="�ؼ�" style="width:13%"/>
		<display:column title="�缶" style="width:13%"/>
		<display:column title="�弶" style="width:13%"/>
		<display:column title="С��" style="width:15%"/>
	</display:table>
</logic:notPresent>
</td>
</tr>
</table>
	<center>
		<button id="btn_exp" accesskey="e"  onclick="exportQhbgqkhzb()">������ӡ(<u>E</u>)</button>
	</center>
</form>

</body>
</html>