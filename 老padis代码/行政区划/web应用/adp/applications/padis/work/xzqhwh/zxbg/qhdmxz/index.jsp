<%@	include	file="../../../../work/public/head.jsp" %>
<html>
	<head>
		<title>�����־����</title>
		<script type="text/javascript" src="./js/expxzqh.js"></script>
		<meta http-equiv="Content-Type"	content="text/xml; charset=GBK">
	</head>
	<body>
	<center>
		<font color="red">����excel��ʽ�����������ļ��뵽<a href="ftp://www.cpdrc120.net.cn">ftp://www.cpdrc120.net.cn</a>����</font>
	</center>
	<FIELDSET>
			<table width="100%" align="center">
				<tr>
					<td align="right" width="14%">����ʱ����</td>
					<td width="36%"><date:input style="width:93%" name="map" property='DCSJQ' valueKey="DCSJQ" title='��������' maxlength='14'  calendarDir='/public' />
					</td>
					<td align="right" width="14%">����ʱ��ֹ��</td>
					<td width="36%"><date:input style="width:93%" name="map" property='DCSJZ' valueKey="DCSJZ" title='��������' maxlength='14'  calendarDir='/public' />
					</td>
				</tr>		
			</table>
			<center>
				<button class="btn-big" accesskey="q" onClick="btn_sub_query()">�� ѯ(<u>Q</u>)</button>
				<button class="btn-big" accesskey="r" onClick="btn_sub_reset()">�� ��(<u>R</u>)</button>
			</center>
		</FIELDSET>
		<table>
		<tr>
		
		</tr>
		</table>
		<iframe src="QhdmxzService.queryXzqhfb.do?PAGESIZE=20" id="operationArea1" name="operationArea1" width="100%" onload="this.height=operationArea1.document.body.scrollHeight" frameborder="0" ></iframe>
	</body>
</html>

