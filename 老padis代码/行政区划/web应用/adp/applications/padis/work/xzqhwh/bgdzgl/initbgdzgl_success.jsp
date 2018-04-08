<%@ include file="../../../work/public/head.jsp" %>
<HTML>
<HEAD>
<TITLE>变更对照管理</TITLE>
<script type="text/javascript" src="./js/bgdzgl.js"></script>
</HEAD>
<BODY>
	<FIELDSET>
	
	<form action="" name ="form1">
		<input type="hidden" id ="BGXZQH_DM" value=""/>
			<table width="100%" align="center">
				<tr>
					<TD	width="14%" align="right">调整类型：</TD>
					<TD	width="36%">
						<dict:select table_name="V_DM_XZQH_BGLX" colid="BGLX_DM"  property="BGLX_DM" nullOption="true" nullLabel="====请选择===="/>
					</td>
					<TD	width="14%" align="right">处理状态：</TD>
					<TD	width="36%">
						<!--  <dict:select table_name="V_DM_XZQH_CLZT"  colid="CLZT_DM"  property="CLZT_DM" nullOption="true" nullLabel="====请选择===="/>-->
						<select id='CLZT_DM'>
							<option value=''>====请选择====</option>
							<option value='10'>待处理</option>
							<option value='20'>处理中</option>
							<option value='30'>处理成功</option>
							<option value='31'>处理失败</option>
						</select>
						
					</td>
				</tr>
				<tr>
					<td align="right" width="14%">变更区划：</td>
					<td width="36%">
					<INPUT style="width:92%" type="text" id="BGXZQH_MC" name="BGXZQH_MC" disabled="true" maxlength="80">
					<img src="../../../public/images/ex.gif" width="16" onclick = "javascript:getOptionTree('../../../common/commonframe/commonframe.do?value(XT_TYKJ_KJMC)=XZQH&value(CODE)=<%=(String)map.get("JB_DM")%>&value(INCLUDE)=Y&inputKey=BGXZQH_DM&inputName=BGXZQH_MC&value(CONDITION)=JCDM IN (%271%27,%272%27,%273%27)')"/>
					</td>
					<TD	width="14%" align="right"></TD>
					<TD	width="36%">
					</td>
				</tr>
				<tr>
					<td align="right" width="14%">提交时间起：</td>
					<td width="36%"><date:input style="width:93%" name="map" property='TJSJQ' valueKey="TJSJQ" title='发表日期' maxlength='14' readonly="true" calendarDir='/public' />
					</td>
					<td align="right" width="14%">提交时间止：</td>
					<td width="36%"><date:input style="width:93%" name="map" property='TJSJZ' valueKey="TJSJZ" title='发表日期' maxlength='14' readonly="true" calendarDir='/public' />
					</td>
				</tr>					
			</table>
			<center>
				<button class="btn-big"  accesskey="q" onClick="btn_sub_query()">查 询(<u>Q</u>)</button>
				<button class="btn-big" accesskey="r" onClick="btn_sub_reset()">重 置(<u>R</u>)</button>
			</center>
</form>			
		</FIELDSET>
		<iframe src="BgdzglService.queryDzb.do?PAGESIZE=10" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" scrolling="no" frameborder="0"></iframe>
</BODY>
</NTML>