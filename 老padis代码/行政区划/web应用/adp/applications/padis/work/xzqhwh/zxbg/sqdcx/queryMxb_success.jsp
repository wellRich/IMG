<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>变更对照表查询</TITLE>
<script type="text/javascript" src="./js/sqdcx.js"></script>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY >
<form method="POST" name="form2" >
<logic:present name="ITEMS">
	<display:table name="ITEMS" id="mxbMap" align="center"  pagesize="10"   class="tab"  requestURI="SqdcxService.queryMxb.do">
	<display:column style="width:10%;text-align:left" property="GROUPMC" title="调整说明"/>
	<display:column style="width:10%" property="YSXZQH_DM" title="原区划代码" sortable="true"/>
	<display:column style="width:10%;text-align:center" property="YSXZQH_MC" title="原区划名称" />
	<display:column style="width:10%;text-align:center" title="调整类型" sortable="true" property="BGLX_DM"/>
	<display:column style="width:10%;text-align:center" property="MBXZQH_MC" title="现区划名称"/>
	<display:column style="width:10%" property="MBXZQH_DM" title="现区划代码" sortable="true"/>
	<display:column style="width:10%" property="CLZT_DM" title="处理状态" />
	<display:column style="width:10%" property="CLJG" title="处理结果" />
	<display:column style="width:10%" property="BZ" title="备注"/>
	<display:column style="width:10%" property="XGSJ" title="提交时间" dateFormat="yyyy-MM-dd" sortable="true"/>
	</display:table>
</logic:present>
<logic:notPresent name="ITEMS">
<display:table name="ITEMS" align="center" id="mxbMap" requestURI="SqdcxService.queryMxb.do"  pagesize="10" class="tab">
		<display:column  title="调整说明" style="width:10%"  />
		<display:column title="原区划代码" style="width:10%"/>
		<display:column title="原区划名称" style="width:10%"/>
		<display:column title="调整类型" style="width:10%"/>
		<display:column title="现区划名称" style="width:10%"/>
		<display:column title="现区划代码" style="width:10%"/>
		<display:column title="处理状态" style="width:10%" />
		<display:column title="处理结果" style="width:10%"  />
		<display:column title="备注" style="width:10%"  />
		<display:column title="提交时间" style="width:10%"/>
	</display:table>
</logic:notPresent>
</form>

</body>
</html>