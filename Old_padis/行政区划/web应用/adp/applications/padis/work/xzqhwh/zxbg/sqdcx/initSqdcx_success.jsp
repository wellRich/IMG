<%@ include file="../../../../work/public/head.jsp" %>

<html>
	<head>
		<title>������ձ��ѯ</title>
		<script type="text/javascript" src="./js/sqdcx.js"></script>
		<meta http-equiv="Content-Type"	content="text/xml; charset=GBK">
	</head>
	<body>
	<FIELDSET>
	<form action="" name ="form1">
	<input type="hidden" id ="BGXZQH_DM" value=""/>
			<table width="100%" align="center">
				<tr>
					<td align="right" width="14%">������ձ����ƣ�</td>
					<td width="36%">
					<INPUT style="width:100%" type="text" id="SQDMC" name="SQDMC" maxlength="100">
					</td>
					<TD	width="14%" align="right">������ձ�״̬��</TD>
					<TD	width="36%">
						<dict:select table_name="V_DM_XZQH_SQDZT" colid="SQDZT_DM"  property="SQDZT_DM" nullOption="true" nullLabel="========��ѡ������========"/>
					</td>
				</tr>
				<tr>
					<TD	width="14%" align="right">����˵����</TD>
					<TD	width="36%">
						<INPUT style="width:100%" type="text" id="TZSM" name="TZSM" maxlength="100">
					</td>
					<!--<td align="right" width="14%">�ϱ�������</td>
					<td width="36%">
					<INPUT style="width:92%" type="text" id="BGXZQH_MC" name="BGXZQH_MC" disabled="true" maxlength="80">
					<img src="../../../../public/images/ex.gif" width="16" onclick = "javascript:getOptionTree('../../../../common/commonframe/commonframe.do?value(XT_TYKJ_KJMC)=XZQH&value(CODE)=<%=(String)map.get("JB_DM")%>&value(INCLUDE)=Y&inputKey=BGXZQH_DM&inputName=BGXZQH_MC&value(CONDITION)=JCDM IN (%271%27,%272%27,%273%27)')"/>
					</td>-->
				</tr>
				<tr>
					<td align="right" width="14%">����ʱ����</td>
					<td width="36%"><date:input style="width:93%" name="map" property='BGSJQ' valueKey="BGSJQ" title='��������' maxlength='14' readonly="true" calendarDir='/public' />
					</td>
					<td align="right" width="14%">����ʱ��ֹ��</td>
					<td width="36%"><date:input style="width:93%" name="map" property='BGSJZ' valueKey="BGSJZ" title='��������' maxlength='14' readonly="true" calendarDir='/public' />
					</td>
				</tr>
			</table>
			<center>
				<button class="btn-big"  accesskey="q" onClick="btn_sub_query()">�� ѯ(<u>Q</u>)</button>
				<button class="btn-big" accesskey="r" onClick="btn_sub_reset()">�� ��(<u>R</u>)</button>
			</center>
</form>			
		</FIELDSET>
		
		<iframe src="SqdcxService.querySqd.do?PAGESIZE=8" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" frameborder="0"></iframe>
		
	</body>
</html>