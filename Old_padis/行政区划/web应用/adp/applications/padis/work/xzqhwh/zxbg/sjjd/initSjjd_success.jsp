<%@	include file="../../../../work/public/head.jsp"%>
<%
	int count=0;
%>
<html>
	<HEAD>
		<TITLE>�м��ල</TITLE>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
	<body>
		<fieldset style="margin-top:-10px;"><legend>���ύ������ձ������</legend>
		<table border="0">
		<tr>
		<td>
			<logic:present name="YTJITEMS">
				<display:table name="YTJITEMS" id="sqdMap" align="center"  class="tab"
					 requestURI="SjjdService.initSjjd.do">
					<display:column style="width:10%" title="���"><%=++count%></display:column>
					<display:column style="width:25%" property="XZQH_DM" title="��������" sortable="true"/>
					<display:column style="width:45%;text-align:left" property="XZQH_MC" title="��������" sortable="true"/>
					<display:column style="width:20%;text-align:left" property="SQDZT_DM" title="���ձ�״̬" sortable="true"/>
				</display:table>
			</logic:present>
			<logic:notPresent name="YTJITEMS">
				<display:table name="YTJITEMS" align="center" id="sqdMap"
				 requestURI="SjjdService.initSjjd.do"
					 class="tab">
					 <display:column style="width:10%" title="���"/>
					<display:column style="width:25%" title="��������"/>
					<display:column style="width:45%" title="��������"/>
					<display:column style="width:20%" title="���ձ�״̬"/>
				</display:table>
			</logic:notPresent>
			</td>
		</tr>
		</table>
		</fieldset>
		<fieldset style="margin-top:-15px;"><legend>δ��ɱ�����ձ������(״̬˵������δ��������ʾ��δ����������ձ���δ�ύ����ʾ����¼����ϸ��δ�ύ������ձ�)</legend>
		<iframe src="SjjdService.queryWtj.do" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" frameborder="0" ></iframe>
		</fieldset>
	</body>
</html>

