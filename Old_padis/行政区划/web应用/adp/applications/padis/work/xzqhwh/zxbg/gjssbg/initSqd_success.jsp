<%@	include file="../../../../work/public/head.jsp"%>
<html>
	<HEAD>
		<TITLE>国家实时变更</TITLE>
		<script type="text/javascript" src="./js/gjssbg.js"></script>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
	<body>
		<form method="POST" name="form1" action="">
		
			<logic:present name="SQDLIST">
				<display:table name="SQDLIST" id="sqdMap" align="center"  class="tab"
					pagesize="8" requestURI="GjssbgService.initSqd.do">
					<display:column title="<input type='checkbox' class='checkbox' id='all_checked' name='all_checked' onclick='selectAll()'/>全选" style="width:8%;" align="center">
						<input type="checkbox" id="sqdxh_check"  name="sqdxh_check" value='<bean:write name="sqdMap" property="SQDXH"/>' />
					</display:column>
					<display:column style="width:15%"  title="区划" >
						<bean:write name="sqdMap" property="SBXZQH_DM"/>(<bean:write name="sqdMap" property="SBXZQH"/>)
					</display:column>
					<display:column style="width:28%" property="SQDMC" title="变更对照表名称" sortable="true"  />
					<display:column style="width:15%" property="SQDZT_DM" title="变更对照表状态" />
					<display:column style="width:34%" property="BZ" title="备注" />
				</display:table>
			</logic:present>
			
			<logic:notPresent name="SQDLIST">
				<display:table name="SQDLIST" align="center" id="sqdMap"
				 requestURI="GjssbgService.initSqd.do"
					pagesize="8" class="tab">
					<display:column style="width:6%" property="" title="选择"/>
					<display:column style="width:15%" property="" title="区划" />
					<display:column style="width:28%" property="" title="变更对照表名称"/>
					<display:column style="width:15%"  property="" title="变更对照表状态"/>
					<display:column style="width:36%"  property="" title="备注" />
				</display:table>
			</logic:notPresent>
		 <center>
		 	<button class="btn-big" id="btn_query" accesskey="q"  onclick="queryMxb()">查看明细(<u>Q</u>)</button>
		 	<button class="btn-big" id="btn_yes" accesskey="y"  onclick="gjshtg()">国家审核通过(<u>Y</u>)</button>
		</center>
		</form>
		<hr></hr>
		<iframe src="GjssbgService.queryMxb.do" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" frameborder="0" ></iframe>
	</body>
</html>

