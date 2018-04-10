<%@ include file="../../../../../work/public/head.jsp" %>
<%
	String flag = (String)map.get("FLAG");
	String sqdxh = (String)map.get("SQDXH");
	String sbxzqh_dm = (String)map.get("SBXZQH_DM");
%>
<HTML >
<HEAD>
<TITLE>查看变更对照表明细</TITLE>
<script type="text/javascript" src="./js/sqdwh.js"></script>
<script type="text/javascript">
	function init()
	{
		
		if('<%=flag%>'=='true')
		{
			var obj = document.getElementById("btn_delete");
			obj.disabled="";
			
		}
		
	}
</script>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY onload="init()">
<form method="POST" name="form2" >
<input type="hidden" name="SQDXH" value="<%=sqdxh%>">
<table border="0">
<tr>
<td>
<logic:present name="ITEMS">
	<display:table name="ITEMS" id="mxbMap" align="center"  pagesize="10"   class="tab"  requestURI="WhbgdzbService.queryMxb.do">
	<display:column style="width:6%;" align="center"  title="选择">
						<input type="checkbox"  id="mxb_check" name="mxb_check"
							class="checkbox" value='<bean:write name="mxbMap" property="GROUPXH"/>' onclick="getSelect(this)"/>
	</display:column>
	<display:column style="width:15%;text-align:left" property="GROUPMC" title="调整说明"/>
	<display:column style="width:15%" property="YSXZQH_DM" title="原区划代码" sortable="true"/>
	<display:column style="width:14%;text-align:center" property="YSXZQH_MC" title="原区划名称" />
	<display:column style="width:8%;text-align:center" title="调整类型" sortable="true" property="BGLX_DM"/>
	<display:column style="width:14%;text-align:center" property="MBXZQH_MC" title="现区划名称"/>
	<display:column style="width:15%" property="MBXZQH_DM" title="现区划代码" sortable="true"/>
	<display:column style="width:13%" property="XGSJ" title="提交时间" dateFormat="yyyy-MM-dd" sortable="true"/>
	</display:table>
</logic:present>
<logic:notPresent name="ITEMS">
<display:table name="ITEMS" align="center" id="mxbMap" requestURI="WhbgdzbService.queryMxb.do"  pagesize="10" class="tab">
<display:column style="width:3%;" align="center"  title="选择">
	</display:column>
		<display:column  title="调整说明" style="width:15%"  />
		<display:column title="原区划代码" style="width:15%"/>
		<display:column title="原区划名称" style="width:14%"/>
		<display:column title="调整类型" style="width:9%"/>
		<display:column title="现区划名称" style="width:14%"/>
		<display:column title="现区划代码" style="width:15%"/>
		<display:column title="提交时间" style="width:15%"/>
	</display:table>
</logic:notPresent>
</td>
</tr>
</table>
<input type="hidden" id="check_value" value=""/> 
 <center>
 	  <button id="btn_delete" disabled="disabled"  accesskey="d"  onclick="deleteMxb('<%=sbxzqh_dm%>')">删除明细(<u>D</u>)</button>
	<button id="btn_refur" accesskey="n"  onclick="javascript:window.location.reload()">刷 新(<u>N</u>)</button>
	</center>
</form>

</body>
</html>