<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>�������ݱȽ�</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="./js/cysjbj.js"></script>
</HEAD>
<BODY>
<form method="POST" id="form1" action= "">
<fieldset style="margin-top:-10px;"><legend>�ϴ��ļ���Ϣ</legend>
			<logic:present name="ZIPLIST">
				<display:table name="ZIPLIST" id="rzMap" align="center"  pagesize="10"  decorator="demo.DemoWrapper"  class="tab"  requestURI="CysjbjService.queryZip.do">
				<display:column title="ѡ��" style="width:5%;" align="center" >
				<input type="radio" id="zipxh_radio"  name="zipxh_radio"  value='<bean:write name="rzMap" property="ZIPXH"/>||<bean:write name="rzMap" property="JZBGZT"/>'/>
				</display:column>
				<display:column style="width:8%" property="XZQH_DM" title="��������" sortable="true"/>
				<display:column style="width:10%" property="RQ" title="����" sortable="true"/>
				<display:column style="width:25%" property="WJM" title="�ļ���"/>
				<display:column style="width:15%" property="JZBGZT_DM" title="״̬" />
				<display:column style="width:19%" property="BZ" title="��ע"/>
				<display:column style="width:18%" property="LRSJ" title="�ϴ�ʱ��" sortable="true"/>	
				</display:table>
			</logic:present>
			<logic:notPresent name="ZIPLIST">
			<display:table name="ZIPLIST" align="center" id="rzMap" decorator="demo.DemoWrapper" requestURI="CysjbjService.queryZip.do"  pagesize="10" class="tab">
				<display:column style="width:5%" title="ѡ��" />
				<display:column style="width:8%" title="��������"/>
				<display:column style="width:10%" title="����"/>
				<display:column style="width:25%" title="�ļ���"/>
				<display:column style="width:15%" title="״̬"/>
				<display:column style="width:19%"  title="��ע"/>
				<display:column style="width:18%" title="�ϴ�ʱ��"/>	
				</display:table>
			</logic:notPresent>
		<input type="hidden" id ="BGXZQH_DM" value=""/>
	<table>
		<tr>
			<td width="50%"></td>
			<td width="10%" align="center"><button class="btn-big" id="btn_query" accesskey="d"  onclick="queryDif()">�������ݱȽ�(<u>D</u>)</button></td>
			<td width="10%" align="right">�������ͣ�</td>
			<td width="20%" align="left">
			<select name="CYLX">
				<option value="1">����һ�����Ʋ�һ��</option>
				<option value="2">��ȫһ��</option>
				<option value="3">ʡ������PADIS������</option>
				<option value="4">ʡ��������PADIS����</option>
			</select>
			</td>
			<td width="10%"></td>
		</tr>
	</table>
	</fieldset>
</form>
</body>
</html>