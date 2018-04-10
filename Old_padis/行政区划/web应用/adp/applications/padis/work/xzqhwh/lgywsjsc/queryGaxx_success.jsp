<%@ include file="../../../work/public/head.jsp" %>
<% 
	int count = 0;
%>
<HTML>
<HEAD>
<TITLE>查询结果</TITLE>
<SCRIPT LANGUAGE="JavaScript" src="./js/queryGaxx_success.js"></SCRIPT>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY>
<fieldset style="margin-top:-10px;"><legend>流管个案基本信息</legend>
		<table border="0">
			<tr>
			<td>
			<logic:present name="ITEMS"> 
				<display:table name="ITEMS" id="item" align="center"  requestURI="LgywsjscService.queryGaxx.do" pagesize="7" decorator="demo.DemoWrapper" class="tab">	
					<display:column title="选择" style="width:5%;" align="center" >
					<% count++; %>
				<input type="checkbox" id="RKDZDAH"  name="RKDZDAH" value='<bean:write name="item" property="RKDZDAH"/>' 			    	onclick="javascript:selJfdZjffCheckBox()" />
					</display:column>
					<display:column property="XM"  title="姓名"></display:column>
					<display:column property="SFZJHM"  title="公民身份号码"></display:column>
					<display:column property="HJDXXDZ" title="户籍地详细地址"></display:column>
					<display:column property="XJZDXXDZ"  title="现居住地详细地址"></display:column>
				</display:table>
			</logic:present>
			<logic:notPresent name="ITEMS">
					<display:table name="ITEMS" align="center" decorator="demo.DemoWrapper" pagesize="10" class="tab">	
				</display:table>
			</logic:notPresent>
			</td>
			</tr>
			<tr>
				<td valign="bottom">
				<center>
				<%
					if(count!=0){
				%>
				<button onClick="subDel()" accesskey="d" id="btn_delete">删 除(<U>d</U>)</button>		
				<%
						}
				%>
				</center>
				</td>
			</tr>
			</table>
	  </fieldset>
</BODY>
</HTML>