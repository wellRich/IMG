<%@ include file="../../../../work/public/head.jsp" %>
<HTML>
<HEAD>
<TITLE>区划基本信息</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY> 
<logic:present name="XZQHLIST">
	<display:table name="XZQHLIST" id="xzqhMap" align="center"  class="tab"
		pagesize="10" requestURI="QhdmtjcxService.queryXzqhxx.do">
		<display:column style="width:15%" property="XZQH_DM" title="区划代码" sortable="true"/>
		<display:column style="width:15%;text-align:left" property="XZQH_MC" title="区划名称" sortable="true"/>
		<display:column style="width:32%;text-align:left" property="XZQH_QC" title="区划全称" sortable="true"/>
		<display:column style="width:8%" property="JCMC" title="级次" sortable="true"/>
		<display:column style="width:15%;text-align:left" property="SJ_XZQH_MC" title="上级区划名称" sortable="true"/>
		<display:column style="width:15%" property="SJ_XZQH_DM" title="上级区划代码" sortable="true"/>
	</display:table>
	</logic:present>
	<logic:notPresent name="XZQHLIST">
		<display:table name="XZQHLIST" align="center" id="xzqhMap"
		 requestURI="QhdmtjcxService.queryXzqhxx.do"
			pagesize="10" class="tab">
			<display:column style="width:15%" title="区划代码"/>
			<display:column style="width:20%" title="区划名称"/>
			<display:column style="width:25%" title="区划全称"/>
			<display:column style="width:8%" title="级次"/>
			<display:column style="width:15%" title="上级区划名称"/>
			<display:column style="width:15%" title="上级区划代码"/>
		</display:table>
	</logic:notPresent>
</BODY>
</HTML>