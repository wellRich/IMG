<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>查询变更明细</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="./js/bgdzcx.js"></script>
</HEAD>
<BODY>

<form method="POST" id="form1" action= "">
<fieldset style="margin-top:-10px;"><legend>变更明细信息</legend>
	<table border="0">
		<tr> 
			<td>
			<logic:present name="ZIPLIST">
				<display:table name="ZIPLIST" id="rzMap" align="center"  pagesize="10"  decorator="demo.DemoWrapper"  class="tab"  requestURI="BgdzcxService.queryDzb.do">
				<display:column style="width:13%;" property="YSXZQH_DM" title="原区划代码" sortable="true"/>
				<display:column style="width:16%;text-align:left" property="YSXZQH_MC" title="原区划名称" sortable="true"/>
				<display:column style="width:8%" property="BGLX_DM" title="调整类型" sortable="true"/>
				<display:column style="width:13%" property="MBXZQH_DM" title="现区划代码" sortable="true"/>
				<display:column style="width:16%;text-align:left" property="MBXZQH_MC" title="现区划名称" sortable="true"/>
				<display:column style="width:8%;" property="CWSJBZ" title="状态" sortable="true"/>
				<display:column style="width:15%;text-align:left" property="BZ" title="备注" maxlength="13"/>
				<display:column style="width:13%;text-align:left" property="CWXX" title="错误信息" maxlength="13"/>
				</display:table>
			</logic:present>
			<logic:notPresent name="ZIPLIST">
			<display:table name="ZIPLIST" align="center" id="rzMap" decorator="demo.DemoWrapper" requestURI="BgdzcxService.queryDzb.do"  pagesize="10" class="tab">
				<display:column style="width:15%;" title="原区划代码"/>
				<display:column style="width:15%;" title="原区划名称"/>
				<display:column style="width:10%;" title="调整类型"/>
				<display:column style="width:15%;" title="现区划代码"/>
				<display:column style="width:15%;" title="现区划名称"/>
				<display:column style="width:10%;" title="状态"/>
				<display:column style="width:22%;" title="备注"/>
				<display:column style="width:22%;" title="错误信息"/>
				</display:table>
			</logic:notPresent>
			</td>
		</tr>
	</table>
	</fieldset>

</form>

</body>
</html>