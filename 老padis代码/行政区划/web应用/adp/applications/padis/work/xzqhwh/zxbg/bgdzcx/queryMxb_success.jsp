<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>������ձ��ѯ</TITLE>
<script type="text/javascript" src="./js/sqdcx.js"></script>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY >
<form method="POST" name="form2" >
<table border="0">
<tr>
<td>
<logic:present name="ITEMS">
	<display:table name="ITEMS" id="mxbMap" align="center"  pagesize="10"   class="tab"  requestURI="BgdzcxService.queryMxb.do">
	<display:column style="width:15%" property="YSXZQH_DM" title="ԭ��������" sortable="true"/>
	<display:column style="width:15%;text-align:center" property="YSXZQH_MC" title="ԭ��������" />
	<display:column style="width:10%;text-align:center" title="��������" sortable="true" property="BGLX_DM"/>
	<display:column style="width:15%" property="MBXZQH_DM" title="����������" sortable="true"/>
	<display:column style="width:15%;text-align:center" property="MBXZQH_MC" title="����������"/>	
	<display:column style="width:15%" property="BZ" title="��ע"/>
	<display:column style="width:15%" property="LRSJ" title="���ʱ��" dateFormat="yyyy-MM-dd" sortable="true"/>
	</display:table>
</logic:present>
<logic:notPresent name="ITEMS">
<display:table name="ITEMS" align="center" id="mxbMap" requestURI="BgdzcxService.queryMxb.do"  pagesize="10" class="tab">
		<display:column title="ԭ��������" style="width:15%"/>
		<display:column title="ԭ��������" style="width:15%"/>
		<display:column title="��������" style="width:10%"/>
		<display:column title="����������" style="width:15%"/>
		<display:column title="����������" style="width:15%"/>
		<display:column title="��ע" style="width:15%"/>
		<display:column title="���ʱ��" style="width:15%"/>
	</display:table>
</logic:notPresent>
</td>
</tr>
</table>
</form>

</body>
</html>