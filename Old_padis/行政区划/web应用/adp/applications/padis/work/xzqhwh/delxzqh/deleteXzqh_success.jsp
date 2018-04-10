<%@ include file="../../../work/public/head.jsp" %>
<HTML>
<HEAD>
<TITLE>行政区划变更维护</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY> 
	  <fieldset style="margin-top:-10px;"><legend>不能删除原因</legend>
		<table border="0">
			<tr>
			<td>
			<logic:present name="MESSAGES">
				<display:table name="MESSAGES" id="msgMap" align="center" decorator="demo.DemoWrapper"  class="tab"  requestURI="DelxzqhService.deleteXzqh.do">
				<display:column style="width:12%;"  title="行政区划代码" sortable="true" property="XZQHDM"/>
				<display:column style="width:15%;text-align:left;" property="YWBMC" title="业务表名称" sortable="true"/>	
				<display:column style="width:15%;text-align:left;" property="YWXTDM" title="业务系统名称" sortable="true"/>
				<display:column style="width:15%;text-align:left;"  title="业务表字段名称" sortable="true" property="YWBXZQHMC"/>
				<display:column style="width:8%;text-align:left;" property="SJL" title="数据量" sortable="true"/>
				<display:column style="width:35%;text-align:left;" property="BZ" title="备注" maxlength="50"/>
				</display:table>
			</logic:present>
			<logic:notPresent name="MESSAGES">
				<script language="javascript">
					showMessage("行政区划删除成功！",0);
					parent.location.href="index.jsp";
				</script>
			</logic:notPresent>
			</td>
			</tr>
			</table>
	  </fieldset>
</BODY>
</HTML>