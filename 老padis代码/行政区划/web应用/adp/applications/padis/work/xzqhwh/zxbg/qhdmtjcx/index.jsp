<%@ include file="../../../../work/public/head.jsp" %>
<html>
<head>
<title>������Ϣ��ѯ</title>
<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/js/check.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="./js/index.js"></SCRIPT>
</head>
<body>
	<form name="XzqhDmForm" id="XzqhDmForm" method="post" action="/padis/work/xzqhwh/zxbg/qhdmtjcx/QhdmtjcxService.queryXzqhxx.do" target="targetbody">
		<FIELDSET style="margin-top:-10px;"><LEGEND class="legend_Noimg">����������<img src="../../../../work/lg/public/img/Angle_up.gif" id="sfzImg"></LEGEND>
		<table class="list" style="width:99%">
			<tr>
				<td width="14%" class="list_right">��������<span class="star">*</span></td>
				<td colspan=2 width="25%"><input type="text" name="XZQH_DM" maxlength="15" size="20" value="" id="XZQH_DM" style="width:90%" title="��������"></td>
				<td width="14%"></td>
				<td colspan=2 width="25%"></td>
				<td colspan=3 width="22%" align="right">
				<button  accesskey="q" onclick="queryXzqhByDm()" >�� ѯ(<u>Q</u>)</button></td>
			</tr>
		</table>
	 </FIELDSET>
	</form>
<form name="XzqhMcForm" id="XzqhMcForm" method="post" action="/padis/work/xzqhwh/zxbg/qhdmtjcx/QhdmtjcxService.queryXzqhxx.do" target="targetbody">
	 <FIELDSET style="margin-top:-10px;"><LEGEND class="legend_Noimg">����������<img src="../../../../work/lg/public/img/Angle_up.gif" id="xmsrImg"></LEGEND>
	    <table class="list" style="width:99%">
			<tr>
				<td width="14%" class="list_right">��������<span class="star">*</span></td>
				<td colspan=2 width="25%"><input type="text" name="XZQH_MC" maxlength="30" size="20" value="" id="XZQH_MC" style="width:90%" title="��������"></td>
				<td width="14%" class="list_right">��������<span class="star">*</span></td>
				<td width="25%" colspan=2>
					<select name="JCDM"  style="width:90%">
						<option value="">===��ѡ��===</option>
						<option value="1">ʡ��</option>
						<option value="2">�м�</option>
						<option value="3">�ؼ�</option>
						<option value="4" selected>�缶</option>
						<option value="5">�弶</option>
					</select>
				</td>
				<td colspan=3 width="22%" align="right">
				<button accesskey="w" onclick="queryXzqhByMc()" >�� ѯ(<u>W</u>)</button></td>
			</tr>
		</table>
    </FIELDSET>
 	</form>
	<iframe name='targetbody' id="iframe2" src="queryXzqhxx_success.jsp" frameborder=0 width="99%" scrolling="no" onload="this.height=iframe2.document.body.scrollHeight"></iframe>
</body>
</html>