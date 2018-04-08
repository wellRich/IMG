<%@ include file="../../../../work/public/head.jsp" %>
<html>
	<HEAD>
		<TITLE>变更对照表查询</TITLE>
		<script type="text/javascript" src="./js/sqdcx.js"></script>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
	<body>
		<form method="POST" name="form1" action="">
			<logic:present name="SQDLIST">
				<display:table name="SQDLIST" id="sqdMap" align="center"
					pagesize="8"  class="tab"
					requestURI="SqdcxService.querySqd.do">
					<display:column title="选择" style="width:4%" align="center">
						<input type="radio" id="sqdxh_radio"  name="sqdxh_radio" value='<bean:write name="sqdMap" property="SQDXH"/>||<bean:write name="sqdMap" property="SQDMC"/>' />
					</display:column>
					<display:column style="width:10%" property="SBXZQH_DM" title="区划代码" sortable="true"/>
					<display:column style="width:13%"  property="SBXZQH_MC"title="区划名称" />			
					<display:column style="width:18%" property="SQDMC" title="变更对照表名称"  />
					<display:column style="width:15%" property="SQDZT_DM" title="变更对照表状态" />
					<display:column style="width:14%" property="BZ" title="备注" />
					<display:column style="width:14%" property="SPYJ" title="审批意见" />
					<display:column style="width:12%" property="LRSJ" title="录入时间" dateFormat="yyyy-MM-dd" />
				</display:table>
			</logic:present>
			
			<logic:notPresent name="SQDLIST">
				<display:table name="SQDLIST" align="center" id="sqdMap"
				 requestURI="SqdcxService.querySqd.do"
					pagesize="8" class="tab">
					<display:column style="width:4%" property="" title="选择"/>
					<display:column style="width:13%" property="" title="区划代码" />
					<display:column style="width:13%" property="" title="区划名称" />
					<display:column style="width:16%" property="" title="变更对照表名称"/>
					<display:column style="width:15%"  property="" title="变更对照表状态"/>
					<display:column style="width:12%"  property="" title="备注" />
					<display:column style="width:12%"  property="" title="审批意见" />
					<display:column style="width:15%" property="" title="录入时间" dateFormat="yyyy-MM-dd" />
				</display:table>
			</logic:notPresent>
		 <center>
			 <button id="btn_query" accesskey="m"  onclick="queryMxb()">查看明细(<u>M</u>)</button>
			 <button id="btn_exp" accesskey="e"  onclick="expSqd()">导出打印(<u>E</u>)</button>
		</center>
		</form>
		<iframe src="SqdcxService.queryMxb.do?PAGESIZE=10" id="iframe2" name="iframe2" width="100%" onload="this.height=iframe2.document.body.scrollHeight" frameborder="0" ></iframe>
	</body>
</html>
