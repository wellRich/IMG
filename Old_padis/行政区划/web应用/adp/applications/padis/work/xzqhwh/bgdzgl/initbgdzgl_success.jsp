<%@ include file="../../../work/public/head.jsp" %>
<HTML>
<HEAD>
<TITLE>������չ���</TITLE>
<script type="text/javascript" src="./js/bgdzgl.js"></script>
</HEAD>
<BODY>
	<FIELDSET>
	
	<form action="" name ="form1">
		<input type="hidden" id ="BGXZQH_DM" value=""/>
			<table width="100%" align="center">
				<tr>
					<TD	width="14%" align="right">�������ͣ�</TD>
					<TD	width="36%">
						<dict:select table_name="V_DM_XZQH_BGLX" colid="BGLX_DM"  property="BGLX_DM" nullOption="true" nullLabel="====��ѡ��===="/>
					</td>
					<TD	width="14%" align="right">����״̬��</TD>
					<TD	width="36%">
						<!--  <dict:select table_name="V_DM_XZQH_CLZT"  colid="CLZT_DM"  property="CLZT_DM" nullOption="true" nullLabel="====��ѡ��===="/>-->
						<select id='CLZT_DM'>
							<option value=''>====��ѡ��====</option>
							<option value='10'>������</option>
							<option value='20'>������</option>
							<option value='30'>����ɹ�</option>
							<option value='31'>����ʧ��</option>
						</select>
						
					</td>
				</tr>
				<tr>
					<td align="right" width="14%">���������</td>
					<td width="36%">
					<INPUT style="width:92%" type="text" id="BGXZQH_MC" name="BGXZQH_MC" disabled="true" maxlength="80">
					<img src="../../../public/images/ex.gif" width="16" onclick = "javascript:getOptionTree('../../../common/commonframe/commonframe.do?value(XT_TYKJ_KJMC)=XZQH&value(CODE)=<%=(String)map.get("JB_DM")%>&value(INCLUDE)=Y&inputKey=BGXZQH_DM&inputName=BGXZQH_MC&value(CONDITION)=JCDM IN (%271%27,%272%27,%273%27)')"/>
					</td>
					<TD	width="14%" align="right"></TD>
					<TD	width="36%">
					</td>
				</tr>
				<tr>
					<td align="right" width="14%">�ύʱ����</td>
					<td width="36%"><date:input style="width:93%" name="map" property='TJSJQ' valueKey="TJSJQ" title='��������' maxlength='14' readonly="true" calendarDir='/public' />
					</td>
					<td align="right" width="14%">�ύʱ��ֹ��</td>
					<td width="36%"><date:input style="width:93%" name="map" property='TJSJZ' valueKey="TJSJZ" title='��������' maxlength='14' readonly="true" calendarDir='/public' />
					</td>
				</tr>					
			</table>
			<center>
				<button class="btn-big"  accesskey="q" onClick="btn_sub_query()">�� ѯ(<u>Q</u>)</button>
				<button class="btn-big" accesskey="r" onClick="btn_sub_reset()">�� ��(<u>R</u>)</button>
			</center>
</form>			
		</FIELDSET>
		<iframe src="BgdzglService.queryDzb.do?PAGESIZE=10" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" scrolling="no" frameborder="0"></iframe>
</BODY>
</NTML>