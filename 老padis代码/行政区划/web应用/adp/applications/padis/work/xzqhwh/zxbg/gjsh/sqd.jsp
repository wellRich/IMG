<%@ include file="../../../../work/public/head.jsp" %>
<SCRIPT LANGUAGE="JavaScript" src="../../../../console/public/js/tab.js"></SCRIPT>
	<script language="javascript" src="../../../../work/public/js/check.js"></script>
	<script language="javascript" src="../../../../work/public/js/controlService.js"></script>
	<!--帮助信息要使用的JS文件-->
	<script language="javascript" src="../../../../work/public/js/common.js"></script>
<html>
	<head>
		<title>国家审核</title>
		<script type="text/javascript" src="./js/gjsh.js"></script>
		<meta http-equiv="Content-Type"	content="text/xml; charset=GBK">
	</head>
	<body onload="init()">
	<FIELDSET>
			<table width="100%" align="center">
				<tr>
					<td align="right" width="14%">变更对照表名称：</td>
					<td width="36%">
					<INPUT style="width:100%" type="text" id="SQDMC" name="SQDMC" maxlength="100">
					</td>
					<TD	width="14%" align="right">变更对照表状态：</TD>
					<TD	width="36%">
						<select id="SQDZT_DM">
							<option value=""></option>
						</select>
					</td>
				</tr>			
			</table>
			<center>
				<button class="btn-big" accesskey="q" onClick="btn_sub_query('<%=request.getParameter("XZQH_DM")%>')">查 询(<u>Q</u>)</button>
				<button class="btn-big" accesskey="r" onClick="btn_sub_reset()">重 置(<u>R</u>)</button>
			</center>
		</FIELDSET>
		<input type="hidden" name ="xzqh_dm" id="xzqh_dm" value='<%=request.getParameter("XZQH_DM")%>'/>
<iframe src="GjshService.querySqd.do?XZQH_DM=<%=request.getParameter("XZQH_DM")%>" id="iframe_sqd" name="iframe_sqd" width="100%" onload="this.height=iframe_sqd.document.body.scrollHeight" frameborder="0"></iframe>
	</body>
</html>