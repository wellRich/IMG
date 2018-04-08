<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>变更对照表审批</TITLE>
<script type="text/javascript" src="./js/bgsqsp.js"></script>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY>
<form method="POST" name="form2" >
<table border="0">
<tr>
<td>
<logic:present name="ITEMS">
	<display:table name="ITEMS" id="mxbMap" align="center"    class="tab"  requestURI="BgsqspService.queryMxb.do">
		<display:column style="width:15%;text-align:left" property="GROUPMC" title="调整说明"/>
	<display:column style="width:15%" property="YSXZQH_DM" title="原区划代码" sortable="true"/>
	<display:column style="width:14%;text-align:center" property="YSXZQH_MC" title="原区划名称"/>
	<display:column style="width:9%;text-align:center" title="调整类型" property="BGLX_DM"/>
	<display:column style="width:14%;text-align:center" property="MBXZQH_MC" title="现区划名称"/>
	<display:column style="width:15%" property="MBXZQH_DM" title="现区划代码" sortable="true"/>
	<display:column style="width:15%" property="XGSJ" title="提交时间" dateFormat="yyyy-MM-dd" sortable="true"/>
	</display:table>
</logic:present>
<logic:notPresent name="ITEMS">
<display:table name="ITEMS" align="center" id="mxbMap" requestURI="BgsqspService.queryMxb.do"  class="tab">
		<display:column  title="调整说明" style="width:15%"  />
		<display:column title="原区划代码" style="width:15%"/>
		<display:column title="原区划名称" style="width:14%"/>
		<display:column title="调整类型" style="width:9%"/>
		<display:column title="现区划名称" style="width:14%"/>
		<display:column title="现区划代码" style="width:15%"/>
		<display:column title="提交时间" style="width:15%"/>
	</display:table>
</logic:notPresent>
</td>
</tr>
</table>
 <center>
	</center>
</form>

</body>
</html>