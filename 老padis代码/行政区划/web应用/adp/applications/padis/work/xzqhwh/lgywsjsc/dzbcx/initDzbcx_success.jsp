<%@	include	file="../../../work/public/head.jsp" %>
<%
	String jb_dm = (String)map.get("JB_DM");
	if(jb_dm==null){
		jb_dm="";
	}
%>
<html>
	<head>
		<title>�����־����</title>
		<script type="text/javascript" src="./js/dzbcx.js"></script>
		<meta http-equiv="Content-Type"	content="text/xml; charset=GBK">
	</head>
	<body>
	<FIELDSET>
			<input type="hidden" name="YSXZQH_DM" value=""/>
			<table width="100%" align="center">
				<tr>
					<td align="right" width="14%">������ձ����ƣ�</td>
					<td width="36%">
					<INPUT style="width:100%" type="text" id="SQBMC" name="SQBMC" maxlength="100">
					</td>
					<TD	width="14%" align="right">������ͣ�</TD>
					<TD	width="36%">
						<dict:select table_name="V_DM_XZQH_SQBLX" colid="SQBLX_DM"  property="SQBLX_DM" nullOption="true" nullLabel="========��ѡ������========"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="14%">�������������</td>
					<td width="36%">
					<INPUT style="width:92%" type="text" id="YSXZQH_MC" name="YSXZQH_MC" disabled="true" maxlength="80">
					<img src="../../../public/images/ex.gif" width="16" onclick = "javascript:getOptionTree('../../../common/commonframe/commonframe.do?value(XT_TYKJ_KJMC)=XZQH&value(CODE)=<%=jb_dm%>&value(INCLUDE)=Y&inputKey=YSXZQH_DM&inputName=YSXZQH_MC')"/>
					</td>
					<TD	width="14%" align="right">����״̬��</TD>
					<TD	width="36%">
						<dict:select table_name="V_DM_XZQH_SQBZT" colid="SQBZT_DM"  property="SQBZT_DM" nullOption="true" nullLabel="========��ѡ������========"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="14%">���ʱ����</td>
					<td width="36%"><date:input style="width:93%" name="map" property='BGSJQ' valueKey="BGSJQ" title='��������' maxlength='14'  calendarDir='/public' />
					</td>
					<td align="right" width="14%">���ʱ��ֹ��</td>
					<td width="36%"><date:input style="width:93%" name="map" property='BGSJZ' valueKey="BGSJZ" title='��������' maxlength='14'  calendarDir='/public' />
					</td>
				</tr>				
			</table>
			<center>
				<button class="btn-big" accesskey="q" onClick="btn_sub_query()">�� ѯ(<u>Q</u>)</button>
				<button class="btn-big" accesskey="r" onClick="btn_sub_reset()">�� ��(<u>R</u>)</button>
			</center>
		</FIELDSET>
		<iframe src="DzbcxService.queryMxb.do?PAGESIZE=10" id="operationArea1" name="operationArea1" width="100%" onload="this.height=operationArea1.document.body.scrollHeight" frameborder="0" ></iframe>
	</body>
</html>

