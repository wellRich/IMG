<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>�������</TITLE>
<script type="text/javascript" src="./js/gjsh.js"></script>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY>
<form name = "form1" method="post" action="">
<fieldset style="margin-top:-10px;"><legend>���ύ������ձ������</legend>
<logic:present name="YTJSQD">
	<display:table name="YTJSQD" id="xzqhMap" align="center"    class="tab"  requestURI="GjshService.queryYtj.do">
		<bean:define id="xzqh_dm" name="xzqhMap" property="XZQH_DM"/>
		<display:column title="ѡ��" style="width:8%;" align="center" >
			<input type="checkbox" id="xzqh_check"  name="xzqh_check" class="checkbox" value='<bean:write name="xzqhMap" property="XZQH_DM"/>'/></display:column>
		<display:column style="width:20%"  title="��������" sortable="true">
			<a href="javascript:querySqd('<bean:write name="xzqhMap" property="XZQH_DM"/>');">
				<bean:write name="xzqhMap" property="XZQH_DM"/>
			</a>
		</display:column>
		<display:column style="width:72%"  title="��������" >
			<a href="javascript:querySqd('<bean:write name="xzqhMap" property="XZQH_DM"/>');">
				<bean:write name="xzqhMap" property="XZQH_MC"/>(
				<%if(!xzqh_dm.equals("000000000000000")){%>
				<bean:write name="xzqhMap" property="YSB"/>δ�ϱ���<bean:write name="xzqhMap" property="SSB"/>���ϱ���<%}%><bean:write name="xzqhMap" property="FLAG"/>)
			</a>
		</display:column>
	</display:table>
</logic:present>
<logic:notPresent name="YTJSQD">
<display:table name="YTJSQD" align="center" id="xzqhMap" requestURI="GjshService.queryYtj.do"   class="tab">
		<display:column title="ѡ��" style="width:8%" />
		<display:column title="��������" style="width:20%" />
		<display:column title="��������" style="width:72%"/>
	</display:table>
</logic:notPresent>
<br>
<center>
<button  id="btn_gjsh"  class="btn-big" accesskey="c"  onclick="checkComplete()">���ͨ��(<u>C</u>)</button>
<button  id="btn_gjsh"  class="btn-big" accesskey="y"  onclick="gjsh()">ȫ��������(<u>Y</u>)</button>
</center>
</form>
</body>
</html>