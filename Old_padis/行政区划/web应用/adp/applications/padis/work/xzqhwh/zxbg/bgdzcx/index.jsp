<%@ include file="../../../../work/public/head.jsp" %>

<html>
	<head>
		<title>变更对照查询</title>
		<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/js/check.js"></SCRIPT>
		<script type="text/javascript" src="./js/bgdzcx.js"></script>
		<meta http-equiv="Content-Type"	content="text/xml; charset=GBK">
	</head>
	<body>
	<form action="" name ="form1">
	<FIELDSET>
		<table width="100%" align="center">
			<tr>
				<td align="right" width="14%">原区划代码：</td>
				<td width="36%">
				<INPUT style="width:100%" type="text" id="YSXZQH_DM" name="YSXZQH_DM" maxlength="15">
				</td>
				<TD	width="14%" align="right">原区划名称：</TD>
				<TD	width="36%">
					<INPUT style="width:100%" type="text" id="YSXZQH_MC" name="YSXZQH_MC" maxlength="80">
				</td>
			</tr>
			<tr>
				<td align="right" width="14%">现区划代码：</td>
				<td width="36%">
				<INPUT style="width:100%" type="text" id="MBXZQH_DM" name="MBXZQH_DM" maxlength="15">
				</td>
				<TD	width="14%" align="right">现区划名称：</TD>
				<TD	width="36%">
					<INPUT style="width:100%" type="text" id="MBXZQH_MC" name="MBXZQH_MC" maxlength="80">
				</td>
			</tr>
			<tr>
				<td align="right" width="14%">变更时间起：</td>
				<td width="36%"><date:input style="width:93%" name="map" property='BGSJQ' valueKey="BGSJQ" title='发表日期' maxlength='14' readonly="true" calendarDir='/public' />
				</td>
				<td align="right" width="14%">变更时间止：</td>
				<td width="36%"><date:input style="width:93%" name="map" property='BGSJZ' valueKey="BGSJZ" title='发表日期' maxlength='14' readonly="true" calendarDir='/public' />
				</td>
			</tr>
			<tr>
				<TD	width="14%" align="right">调整类型：</TD>
				<TD	width="36%">
					<dict:select table_name="V_DM_XZQH_BGLX" colid="BGLX_DM"  property="BGLX_DM" nullOption="true" nullLabel="=请选择类型=" />
				</td>
			</tr>
		</table>
		<center>
			<button class="btn-big"  accesskey="q" onClick="btn_sub_query()">查 询(<u>Q</u>)</button>
			<button class="btn-big" accesskey="r" onClick="btn_sub_reset()">重 置(<u>R</u>)</button>
		</center>		
	</FIELDSET>
	</form>
	<iframe src="BgdzcxService.queryMxb.do?PAGESIZE=8" id="iframe2" name="iframe2" width="100%" onload="this.height=iframe2.document.body.scrollHeight" frameborder="0" ></iframe>
	
</body>
</html>