<%@ include file="../../../../work/public/head.jsp" %>

<HTML >
<HEAD>
<TITLE>�������ȷ��</TITLE>
<script type="text/javascript" src="./js/bgsqqr.js"></script>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY>
<fieldset style="margin-top:-10px;"><legend>δ�ύ������ձ������</legend>
<table border="0">
<tr>
<td>
<logic:present name="XJSQD">
	<display:table name="XJSQD" id="mxbMap" align="center"  pagesize="10"   class="tab"  requestURI="BgsqqrService.queryWtj.do">
	<display:column style="width:20%" property="XZQH_DM" title="��������" sortable="true"/>
	<display:column style="width:80%" property="XZQH_QC" title="��������" />
	</display:table>
</logic:present>
<logic:notPresent name="XJSQD">
<display:table name="XJSQD" align="center" id="mxbMap" requestURI="BgsqqrService.queryWtj.do"  pagesize="10" class="tab">
		<display:column  title="��������" style="width:20%" />
		<display:column title="��������" style="width:80%"/>
	</display:table>
</logic:notPresent>
</td>
</tr>
</table>
</fieldset>
<input id="wtj" type="hidden" value='<%=map.get("WTJ")%>'/>

</body>
</html>