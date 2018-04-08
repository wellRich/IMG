<%@	include file="../../../../work/public/head.jsp"%>
<%
	int count=0;
%>
<html>
	<HEAD>
		<TITLE>市级监督</TITLE>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
	<body>
		<fieldset style="margin-top:-10px;"><legend>已提交变更对照表的区划</legend>
		<table border="0">
		<tr>
		<td>
			<logic:present name="YTJITEMS">
				<display:table name="YTJITEMS" id="sqdMap" align="center"  class="tab"
					 requestURI="SjjdService.initSjjd.do">
					<display:column style="width:10%" title="序号"><%=++count%></display:column>
					<display:column style="width:25%" property="XZQH_DM" title="区划代码" sortable="true"/>
					<display:column style="width:45%;text-align:left" property="XZQH_MC" title="区划名称" sortable="true"/>
					<display:column style="width:20%;text-align:left" property="SQDZT_DM" title="对照表状态" sortable="true"/>
				</display:table>
			</logic:present>
			<logic:notPresent name="YTJITEMS">
				<display:table name="YTJITEMS" align="center" id="sqdMap"
				 requestURI="SjjdService.initSjjd.do"
					 class="tab">
					 <display:column style="width:10%" title="序号"/>
					<display:column style="width:25%" title="区划代码"/>
					<display:column style="width:45%" title="区划名称"/>
					<display:column style="width:20%" title="对照表状态"/>
				</display:table>
			</logic:notPresent>
			</td>
		</tr>
		</table>
		</fieldset>
		<fieldset style="margin-top:-15px;"><legend>未完成变更对照表的区划(状态说明：“未建立”表示尚未建立变更对照表；“未提交”表示正在录入明细尚未提交变更对照表)</legend>
		<iframe src="SjjdService.queryWtj.do" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" frameborder="0" ></iframe>
		</fieldset>
	</body>
</html>

