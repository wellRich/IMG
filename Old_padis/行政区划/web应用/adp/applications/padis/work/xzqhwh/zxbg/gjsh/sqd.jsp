<%@ include file="../../../../work/public/head.jsp" %>
<SCRIPT LANGUAGE="JavaScript" src="../../../../console/public/js/tab.js"></SCRIPT>
	<script language="javascript" src="../../../../work/public/js/check.js"></script>
	<script language="javascript" src="../../../../work/public/js/controlService.js"></script>
	<!--������ϢҪʹ�õ�JS�ļ�-->
	<script language="javascript" src="../../../../work/public/js/common.js"></script>
<html>
	<head>
		<title>�������</title>
		<script type="text/javascript" src="./js/gjsh.js"></script>
		<meta http-equiv="Content-Type"	content="text/xml; charset=GBK">
	</head>
	<body onload="init()">
	<FIELDSET>
			<table width="100%" align="center">
				<tr>
					<td align="right" width="14%">������ձ����ƣ�</td>
					<td width="36%">
					<INPUT style="width:100%" type="text" id="SQDMC" name="SQDMC" maxlength="100">
					</td>
					<TD	width="14%" align="right">������ձ�״̬��</TD>
					<TD	width="36%">
						<select id="SQDZT_DM">
							<option value=""></option>
						</select>
					</td>
				</tr>			
			</table>
			<center>
				<button class="btn-big" accesskey="q" onClick="btn_sub_query('<%=request.getParameter("XZQH_DM")%>')">�� ѯ(<u>Q</u>)</button>
				<button class="btn-big" accesskey="r" onClick="btn_sub_reset()">�� ��(<u>R</u>)</button>
			</center>
		</FIELDSET>
		<input type="hidden" name ="xzqh_dm" id="xzqh_dm" value='<%=request.getParameter("XZQH_DM")%>'/>
<iframe src="GjshService.querySqd.do?XZQH_DM=<%=request.getParameter("XZQH_DM")%>" id="iframe_sqd" name="iframe_sqd" width="100%" onload="this.height=iframe_sqd.document.body.scrollHeight" frameborder="0"></iframe>
	</body>
</html>