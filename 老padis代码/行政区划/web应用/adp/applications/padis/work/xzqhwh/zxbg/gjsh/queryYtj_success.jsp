<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>国家审核</TITLE>
<script type="text/javascript" src="./js/gjsh.js"></script>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY>
<form name = "form1" method="post" action="">
<fieldset style="margin-top:-10px;"><legend>已提交变更对照表的区划</legend>
<logic:present name="YTJSQD">
	<display:table name="YTJSQD" id="xzqhMap" align="center"    class="tab"  requestURI="GjshService.queryYtj.do">
		<bean:define id="xzqh_dm" name="xzqhMap" property="XZQH_DM"/>
		<display:column title="选择" style="width:8%;" align="center" >
			<input type="checkbox" id="xzqh_check"  name="xzqh_check" class="checkbox" value='<bean:write name="xzqhMap" property="XZQH_DM"/>'/></display:column>
		<display:column style="width:20%"  title="区划代码" sortable="true">
			<a href="javascript:querySqd('<bean:write name="xzqhMap" property="XZQH_DM"/>');">
				<bean:write name="xzqhMap" property="XZQH_DM"/>
			</a>
		</display:column>
		<display:column style="width:72%"  title="区划名称" >
			<a href="javascript:querySqd('<bean:write name="xzqhMap" property="XZQH_DM"/>');">
				<bean:write name="xzqhMap" property="XZQH_MC"/>(
				<%if(!xzqh_dm.equals("000000000000000")){%>
				<bean:write name="xzqhMap" property="YSB"/>未上报，<bean:write name="xzqhMap" property="SSB"/>已上报，<%}%><bean:write name="xzqhMap" property="FLAG"/>)
			</a>
		</display:column>
	</display:table>
</logic:present>
<logic:notPresent name="YTJSQD">
<display:table name="YTJSQD" align="center" id="xzqhMap" requestURI="GjshService.queryYtj.do"   class="tab">
		<display:column title="选择" style="width:8%" />
		<display:column title="区划代码" style="width:20%" />
		<display:column title="区划名称" style="width:72%"/>
	</display:table>
</logic:notPresent>
<br>
<center>
<button  id="btn_gjsh"  class="btn-big" accesskey="c"  onclick="checkComplete()">审核通过(<u>C</u>)</button>
<button  id="btn_gjsh"  class="btn-big" accesskey="y"  onclick="gjsh()">全部审核完成(<u>Y</u>)</button>
</center>
</form>
</body>
</html>