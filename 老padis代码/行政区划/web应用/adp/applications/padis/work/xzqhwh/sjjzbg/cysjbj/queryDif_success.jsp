<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>�������ݱȽ�</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="./js/cysjbj.js"></script>
</HEAD>
<BODY>

<form method="POST" id="form1" action= "">
<fieldset style="margin-top:-10px;"><legend>�������ݱȽ�</legend>
	<table border="0">
		<tr> 
			<td>
			<logic:present name="ZIPLIST">
				<display:table name="ZIPLIST" id="rzMap" align="center"  pagesize="20"  decorator="demo.DemoWrapper"  class="tab"  requestURI="CysjbjService.queryDif.do">
				<display:column style="width:25%" title="ʡ���ϴ���������" property="XZQHDM" sortable="true"/>
				<display:column style="width:25%" title="ʡ���ϴ���������" property="XZQHMC" sortable="true" />
				<display:column style="width:25%" title="padis�������������"  property="P_XZQHDM" sortable="true"/>
				<display:column style="width:25%" title="padis�������������"  property="P_XZQHMC" sortable="true"/>
				</display:table>
			</logic:present>
			<logic:notPresent name="ZIPLIST">
			<display:table name="ZIPLIST" align="center" id="rzMap" decorator="demo.DemoWrapper" requestURI="CysjbjService.queryDif.do"  pagesize="10" class="tab">
				<display:column style="width:25%" title="ʡ���ϴ���������"/>
				<display:column style="width:25%" title="ʡ���ϴ���������"/>
				<display:column style="width:25%" title="padis�������������"/>
				<display:column style="width:25%" title="padis�������������"/>
				</display:table>
			</logic:notPresent>
			</td>
		</tr>
	</table>
	</fieldset>
</form>

</body>
</html>