<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>������Ϣ</TITLE>
<script type="text/javascript" src="js/queryWjyWd_success.js"></script>
<script type="text/javascript">
</script>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY SCROLL="YES">
<html:form method="POST" styleId="qForm" action= "work/xzqhwh/zxbg/qhztcx/QhztcxService.queryXjXzqh.do?pagesize=20">
<table border="0">
<tr>
<td>
<logic:present name="WDLIST">
	<display:table name="WDLIST" id="wdMap" align="center"  pagesize="19" decorator="demo.DemoWrapper"  class="tab"  requestURI="QhztcxService.queryXjXzqh.do">
		<display:column style="width:25%" title="��������" property="XZQH_DM" sortable="true"/>
		<display:column style="width:13%;text-align:left;" title="��������" property="XZQH_MC" sortable="true"/>
		<display:column style="width:16%" title="��Ч��־" property="YXBZ" sortable="true"/>
	</display:table>
</logic:present>
<logic:notPresent name="WDLIST">
<display:table name="WDLIST" align="center" id="myMap" decorator="demo.DemoWrapper" requestURI="QhztcxService.queryXjXzqh.do"  pagesize="10" class="tab">	
		<display:column style="width:25%" title="��������"/>
		<display:column style="width:13%" title="��������"/>
		<display:column style="width:16%" title="��Ч��־"/>	
	</display:table>
</logic:notPresent>
</td>
</tr>
</table>
</html:form>

</body>
</html>
