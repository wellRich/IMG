<%@ include file="../../../../work/public/head.jsp" %>
<%
	String szxzqh_dm = (String)map.get("SZXZQH_DM");
	if(szxzqh_dm==null){
		szxzqh_dm="";
	}
%>
<HTML >
<HEAD>
<TITLE>申请表日志</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="./js/jzbggl.js"></script>
</HEAD>
<BODY>
	<table border="0">
		<tr>
			<td>
			<logic:present name="ZIPLIST">
				<display:table name="ZIPLIST" id="rzMap" align="center"  pagesize="10"  decorator="demo.DemoWrapper"  class="tab"  requestURI="JzbgglService.queryZip.do">
				<bean:define id="zipxh" name="rzMap" property="ZIPXH"/>
				<bean:define id="jzbgzt_dm" name="rzMap" property="JZBGZTDM"/>
				<display:column title="选择" style="width:6%;" align="center" >
				<input type="radio" id="zipxh_radio"  name="zipxh_radio" class="radio" value='<%=zipxh%>' onclick="checkZtdm('<%=jzbgzt_dm%>')"/></display:column>
				<display:column style="width:14%;" property="XZQH_DM" title="区划代码" sortable="true"/>
				<display:column style="width:14%;" property="RQ" title="日期" sortable="true"/>
				<display:column style="width:30%;text-align:left;" property="WJM" title="文件名" sortable="true"/>
				<display:column style="width:18%;text-align:left;" property="JZBGZT_DM" title="状态" sortable="true"/>
				<display:column style="width:18%;" property="LRSJ" title="上传时间" sortable="true"/>	
				</display:table>
			</logic:present>
			<logic:notPresent name="ZIPLIST">
			<display:table name="ZIPLIST" align="center" id="rzMap" decorator="demo.DemoWrapper" requestURI="JzbgglService.queryZip.do"  pagesize="10" class="tab">
				<display:column style="width:6%;" title="选择" />
				<display:column style="width:14%;" title="区划代码"/>
				<display:column style="width:14%;" title="日期"/>
				<display:column style="width:30%;" title="文件名"/>
				<display:column style="width:18%;" title="状态"/>
				<display:column style="width:18%;" title="上传时间"/>	
				</display:table>
			</logic:notPresent>
			</td>
		</tr>
	</table>
	<center>
		<button id="btn_impTemp" name="btn_impTemp"  accesskey="e"  onclick="userOperate('1');" class="btn-big">导入临时表(<u>E</u>)</button>
		<!--<button id="btn_check" name="btn_check" accesskey="c"  onclick="userOperate('2');" class="btn-big">逻辑校验(<u>C</u>)</button>-->
	</center>
</body>
</html>