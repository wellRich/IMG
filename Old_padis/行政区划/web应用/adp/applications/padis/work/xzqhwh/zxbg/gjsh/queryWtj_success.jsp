<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>�������</TITLE>
<script type="text/javascript" src="./js/gjsh.js"></script>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY>
<fieldset style="margin-top:-10px;"><legend>δ�ύ������ձ������</legend>
<logic:present name="WTJSQD">
	<display:table name="WTJSQD" id="mxbMap" align="center"    class="tab"  requestURI="GjshService.queryWtj.do">
	<display:column style="width:20%" property="XZQH_DM" title="��������" sortable="true"/>
	<display:column style="width:60%" property="XZQH_MC" title="��������" />
	</display:table>
</logic:present>
<logic:notPresent name="WTJSQD">
<display:table name="WTJSQD" align="center" id="mxbMap" requestURI="GjshService.queryWtj.do"  class="tab">
		<display:column  title="��������" style="width:20%" />
		<display:column title="��������" style="width:60%"/>
	</display:table>
</logic:notPresent>
<input id="wtj" type="hidden" value='<%=map.get("WTJ")%>'/>
</fieldset>

</body>
</html>