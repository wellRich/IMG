<%@	include	file="../../../../work/public/head.jsp" %>
<html>
	<head>
		<title>变更日志管理</title>
		<script type="text/javascript" src="./js/expxzqh.js"></script>
		<meta http-equiv="Content-Type"	content="text/xml; charset=GBK">
	</head>
	<body>
	<center>
		<font color="red">关于excel格式的区划数据文件请到<a href="ftp://www.cpdrc120.net.cn">ftp://www.cpdrc120.net.cn</a>下载</font>
	</center>
	<FIELDSET>
			<table width="100%" align="center">
				<tr>
					<td align="right" width="14%">导出时间起：</td>
					<td width="36%"><date:input style="width:93%" name="map" property='DCSJQ' valueKey="DCSJQ" title='发表日期' maxlength='14'  calendarDir='/public' />
					</td>
					<td align="right" width="14%">导出时间止：</td>
					<td width="36%"><date:input style="width:93%" name="map" property='DCSJZ' valueKey="DCSJZ" title='发表日期' maxlength='14'  calendarDir='/public' />
					</td>
				</tr>		
			</table>
			<center>
				<button class="btn-big" accesskey="q" onClick="btn_sub_query()">查 询(<u>Q</u>)</button>
				<button class="btn-big" accesskey="r" onClick="btn_sub_reset()">重 置(<u>R</u>)</button>
			</center>
		</FIELDSET>
		<table>
		<tr>
		
		</tr>
		</table>
		<iframe src="QhdmxzService.queryXzqhfb.do?PAGESIZE=20" id="operationArea1" name="operationArea1" width="100%" onload="this.height=operationArea1.document.body.scrollHeight" frameborder="0" ></iframe>
	</body>
</html>

