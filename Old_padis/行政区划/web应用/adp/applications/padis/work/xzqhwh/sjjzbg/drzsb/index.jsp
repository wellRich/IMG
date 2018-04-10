<%@	include	file="../../../../work/public/head.jsp" %>
<html>
	<head>
		<title>集中变更管理</title>
		<script type="text/javascript" src="./js/drzsb.js"></script>
		<meta http-equiv="Content-Type"	content="text/xml; charset=GBK">
	</head>
	<body>
		<table width="100%" align="center">
			<tr>
				<td align="right" width="12%">6位区划代码：</td>
				<td width="24%">
				<INPUT style="width:100%" type="text" id="XZQH_DM" name="XZQH_DM" maxlength="6">
				</td>
				<td align="right" width="16%">日期（YYYYMMDD）：</td>
				<td width="26%">
				<INPUT style="width:100%" type="text" id="RQ" name="RQ" maxlength="8">
				</td>
				<td width="10%" align="center" >
				<button class="btn-big" accesskey="q" onClick="btn_sub_query()">查 询(<u>Q</u>)</button>
				</td>
			</tr>				
		</table>
		<iframe src="DrzsbService.queryZip.do?PAGESIZE=10" id="operationArea1" name="operationArea1" width="100%" onload="this.height=operationArea1.document.body.scrollHeight" frameborder="0" ></iframe>
	</body>
</html>

