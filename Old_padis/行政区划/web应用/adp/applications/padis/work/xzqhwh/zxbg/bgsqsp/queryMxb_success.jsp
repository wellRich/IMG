<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>������ձ�����</TITLE>
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
		<display:column style="width:15%;text-align:left" property="GROUPMC" title="����˵��"/>
	<display:column style="width:15%" property="YSXZQH_DM" title="ԭ��������" sortable="true"/>
	<display:column style="width:14%;text-align:center" property="YSXZQH_MC" title="ԭ��������"/>
	<display:column style="width:9%;text-align:center" title="��������" property="BGLX_DM"/>
	<display:column style="width:14%;text-align:center" property="MBXZQH_MC" title="����������"/>
	<display:column style="width:15%" property="MBXZQH_DM" title="����������" sortable="true"/>
	<display:column style="width:15%" property="XGSJ" title="�ύʱ��" dateFormat="yyyy-MM-dd" sortable="true"/>
	</display:table>
</logic:present>
<logic:notPresent name="ITEMS">
<display:table name="ITEMS" align="center" id="mxbMap" requestURI="BgsqspService.queryMxb.do"  class="tab">
		<display:column  title="����˵��" style="width:15%"  />
		<display:column title="ԭ��������" style="width:15%"/>
		<display:column title="ԭ��������" style="width:14%"/>
		<display:column title="��������" style="width:9%"/>
		<display:column title="����������" style="width:14%"/>
		<display:column title="����������" style="width:15%"/>
		<display:column title="�ύʱ��" style="width:15%"/>
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