<%@ include file="../../../../work/public/head.jsp" %>
<%
	String szxzqh_dm = (String)map.get("SZXZQH_DM");
	if(szxzqh_dm==null){
		szxzqh_dm="";
	}
%>
<HTML >
<HEAD>
<TITLE>�������־</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="./js/deletezip.js"></script>
</HEAD>
<BODY>
<html:form method="POST" styleId="form1" action= "work/xzqhwh/sjjzbg/xzqhsjsc/XzqhsjscService.queryXzqhjzbgzip.do">
	<table border="0">
		<tr>
			<td>
			<logic:present name="ZIPLIST">
				<display:table name="ZIPLIST" id="rzMap" align="center"  pagesize="10"  decorator="demo.DemoWrapper"  class="tab"  requestURI="XzqhsjscService.queryXzqhjzbgzip.do">
				<bean:define id="zipxh" name="rzMap" property="ZIPXH"/>
				<bean:define id="sjxzqh_dm" name="rzMap" property="SJXZQH_DM"/>
				<display:column title="ѡ��" style="width:6%;" align="center" >
				<input type="checkbox" id="zipxh_check"  name="zipxh_check" class="checkbox" value='<%=zipxh%>,<%=sjxzqh_dm%>'/></display:column>
				<display:column style="width:14%;" property="XZQH_DM" title="��������" sortable="true"/>
				<display:column style="width:14%;" property="RQ" title="����" sortable="true"/>
				<display:column style="width:30%;text-align:left;" property="WJM" title="�ļ���" sortable="true"/>
				<display:column style="width:18%;text-align:left;" property="JZBGZT_DM" title="״̬" sortable="true"/>
				<display:column style="width:18%;" property="LRSJ" title="�ϴ�ʱ��" sortable="true"/>	
				</display:table>
			</logic:present>
			<logic:notPresent name="ZIPLIST">
			<display:table name="ZIPLIST" align="center" id="rzMap" decorator="demo.DemoWrapper" requestURI="XzqhsjscService.queryXzqhjzbgzip.do"  pagesize="10" class="tab">
				<display:column style="width:6%;" title="ѡ��" />
				<display:column style="width:14%;" title="��������"/>
				<display:column style="width:14%;" title="����"/>
				<display:column style="width:30%;" title="�ļ���"/>
				<display:column style="width:18%;" title="״̬"/>
				<display:column style="width:18%;" title="�ϴ�ʱ��"/>	
				</display:table>
			</logic:notPresent>
			</td>
		</tr>
	</table>
	<center>
	 <button id="btn_query" accesskey="w"  onclick="deleteZip('<%=szxzqh_dm%>');">ɾ ��(<u>W</u>)</button>
	</center>
</html:form>
</body>
</html>