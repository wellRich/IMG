<%@ include file="../../../../work/public/head.jsp" %>
<HTML>
<HEAD>
<TITLE>����������Ϣ</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY> 
<logic:present name="XZQHLIST">
	<display:table name="XZQHLIST" id="xzqhMap" align="center"  class="tab"
		pagesize="10" requestURI="QhdmtjcxService.queryXzqhxx.do">
		<display:column style="width:15%" property="XZQH_DM" title="��������" sortable="true"/>
		<display:column style="width:15%;text-align:left" property="XZQH_MC" title="��������" sortable="true"/>
		<display:column style="width:32%;text-align:left" property="XZQH_QC" title="����ȫ��" sortable="true"/>
		<display:column style="width:8%" property="JCMC" title="����" sortable="true"/>
		<display:column style="width:15%;text-align:left" property="SJ_XZQH_MC" title="�ϼ���������" sortable="true"/>
		<display:column style="width:15%" property="SJ_XZQH_DM" title="�ϼ���������" sortable="true"/>
	</display:table>
	</logic:present>
	<logic:notPresent name="XZQHLIST">
		<display:table name="XZQHLIST" align="center" id="xzqhMap"
		 requestURI="QhdmtjcxService.queryXzqhxx.do"
			pagesize="10" class="tab">
			<display:column style="width:15%" title="��������"/>
			<display:column style="width:20%" title="��������"/>
			<display:column style="width:25%" title="����ȫ��"/>
			<display:column style="width:8%" title="����"/>
			<display:column style="width:15%" title="�ϼ���������"/>
			<display:column style="width:15%" title="�ϼ���������"/>
		</display:table>
	</logic:notPresent>
</BODY>
</HTML>