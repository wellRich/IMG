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
				<display:column title="ѡ��" style="width:6%;" align="center" >
				<input type="radio" id="zipxh_radio"  name="zipxh_radio" class="radio" value='<%=zipxh%>' onclick="checkZtdm('<%=jzbgzt_dm%>')"/></display:column>
				<display:column style="width:14%;" property="XZQH_DM" title="��������" sortable="true"/>
				<display:column style="width:14%;" property="RQ" title="����" sortable="true"/>
				<display:column style="width:30%;text-align:left;" property="WJM" title="�ļ���" sortable="true"/>
				<display:column style="width:18%;text-align:left;" property="JZBGZT_DM" title="״̬" sortable="true"/>
				<display:column style="width:18%;" property="LRSJ" title="�ϴ�ʱ��" sortable="true"/>	
				</display:table>
			</logic:present>
			<logic:notPresent name="ZIPLIST">
			<display:table name="ZIPLIST" align="center" id="rzMap" decorator="demo.DemoWrapper" requestURI="JzbgglService.queryZip.do"  pagesize="10" class="tab">
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
		<button id="btn_impTemp" name="btn_impTemp"  accesskey="e"  onclick="userOperate('1');" class="btn-big">������ʱ��(<u>E</u>)</button>
		<!--<button id="btn_check" name="btn_check" accesskey="c"  onclick="userOperate('2');" class="btn-big">�߼�У��(<u>C</u>)</button>-->
	</center>
</body>
</html>