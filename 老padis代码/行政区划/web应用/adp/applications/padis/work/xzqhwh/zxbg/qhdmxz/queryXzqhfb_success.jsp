<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>�������־</TITLE>
<script type="text/javascript" src="./js/expxzqh.js"></script>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY>
<html:form method="POST" styleId="form1" action= "work/xzqhwh/zxbg/qhdmxz/QhdmxzService.queryXzqhfb.do">
<table border="0">
<tr>
<td>
<logic:present name="RZLIST">
	<display:table name="RZLIST" id="rzMap" align="center"  pagesize="20"  decorator="demo.DemoWrapper"  class="tab"  requestURI="QhdmxzService.queryXzqhfb.do">
	  <bean:define id="dcxh" name="rzMap" property="DCXH"/>
	  <bean:define id="wjlj" name="rzMap" property="WJLJ"/> 
	  <bean:define id="wjm" name="rzMap" property="WJM"/>
	<display:column style="width:50%;text-align:left;"  title="�ļ�����" sortable="true" maxlength="50"><a href="QhdmxzService.getFilePath.do?DCXH=<%=dcxh%>"><%=wjm%></a></display:column>
	<display:column style="width:25%;text-align:right;" property="WJDX" title="�ļ���С(KB)" sortable="true"/>	
	<display:column style="width:25%;" property="DCSJ" title="����ʱ��" sortable="true"/>	
	</display:table>
</logic:present>
<logic:notPresent name="RZLIST">
<display:table name="RZLIST" align="center" id="rzMap" decorator="demo.DemoWrapper" requestURI="QhdmxzService.queryXzqhfb.do"  pagesize="20" class="tab">
		<display:column title="�ļ�����" style="width:18%"/>
		<display:column title="�ļ���С(KB)" style="width:16%"/>
		<display:column title="����ʱ��" style="width:17%"/>
	</display:table>
</logic:notPresent>
</td>
</tr>
</table>
 <center>
	 <button id="btn_refur" accesskey="n"  onclick="javascript:window.location.reload()">ˢ ��(<u>N</u>)</button>
	</center>
</html:form>

</body>
</html>