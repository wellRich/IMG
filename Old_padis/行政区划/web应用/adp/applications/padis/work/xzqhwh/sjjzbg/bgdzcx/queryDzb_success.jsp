<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>��ѯ�����ϸ</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="./js/bgdzcx.js"></script>
</HEAD>
<BODY>

<form method="POST" id="form1" action= "">
<fieldset style="margin-top:-10px;"><legend>�����ϸ��Ϣ</legend>
	<table border="0">
		<tr> 
			<td>
			<logic:present name="ZIPLIST">
				<display:table name="ZIPLIST" id="rzMap" align="center"  pagesize="10"  decorator="demo.DemoWrapper"  class="tab"  requestURI="BgdzcxService.queryDzb.do">
				<display:column style="width:13%;" property="YSXZQH_DM" title="ԭ��������" sortable="true"/>
				<display:column style="width:16%;text-align:left" property="YSXZQH_MC" title="ԭ��������" sortable="true"/>
				<display:column style="width:8%" property="BGLX_DM" title="��������" sortable="true"/>
				<display:column style="width:13%" property="MBXZQH_DM" title="����������" sortable="true"/>
				<display:column style="width:16%;text-align:left" property="MBXZQH_MC" title="����������" sortable="true"/>
				<display:column style="width:8%;" property="CWSJBZ" title="״̬" sortable="true"/>
				<display:column style="width:15%;text-align:left" property="BZ" title="��ע" maxlength="13"/>
				<display:column style="width:13%;text-align:left" property="CWXX" title="������Ϣ" maxlength="13"/>
				</display:table>
			</logic:present>
			<logic:notPresent name="ZIPLIST">
			<display:table name="ZIPLIST" align="center" id="rzMap" decorator="demo.DemoWrapper" requestURI="BgdzcxService.queryDzb.do"  pagesize="10" class="tab">
				<display:column style="width:15%;" title="ԭ��������"/>
				<display:column style="width:15%;" title="ԭ��������"/>
				<display:column style="width:10%;" title="��������"/>
				<display:column style="width:15%;" title="����������"/>
				<display:column style="width:15%;" title="����������"/>
				<display:column style="width:10%;" title="״̬"/>
				<display:column style="width:22%;" title="��ע"/>
				<display:column style="width:22%;" title="������Ϣ"/>
				</display:table>
			</logic:notPresent>
			</td>
		</tr>
	</table>
	</fieldset>

</form>

</body>
</html>