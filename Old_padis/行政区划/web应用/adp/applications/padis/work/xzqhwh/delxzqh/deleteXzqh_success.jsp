<%@ include file="../../../work/public/head.jsp" %>
<HTML>
<HEAD>
<TITLE>�����������ά��</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY> 
	  <fieldset style="margin-top:-10px;"><legend>����ɾ��ԭ��</legend>
		<table border="0">
			<tr>
			<td>
			<logic:present name="MESSAGES">
				<display:table name="MESSAGES" id="msgMap" align="center" decorator="demo.DemoWrapper"  class="tab"  requestURI="DelxzqhService.deleteXzqh.do">
				<display:column style="width:12%;"  title="������������" sortable="true" property="XZQHDM"/>
				<display:column style="width:15%;text-align:left;" property="YWBMC" title="ҵ�������" sortable="true"/>	
				<display:column style="width:15%;text-align:left;" property="YWXTDM" title="ҵ��ϵͳ����" sortable="true"/>
				<display:column style="width:15%;text-align:left;"  title="ҵ����ֶ�����" sortable="true" property="YWBXZQHMC"/>
				<display:column style="width:8%;text-align:left;" property="SJL" title="������" sortable="true"/>
				<display:column style="width:35%;text-align:left;" property="BZ" title="��ע" maxlength="50"/>
				</display:table>
			</logic:present>
			<logic:notPresent name="MESSAGES">
				<script language="javascript">
					showMessage("��������ɾ���ɹ���",0);
					parent.location.href="index.jsp";
				</script>
			</logic:notPresent>
			</td>
			</tr>
			</table>
	  </fieldset>
</BODY>
</HTML>