<%@ include file="../../../work/public/head.jsp" %>

<HTML >
<HEAD>
<TITLE>申请表日志</TITLE>
<script type="text/javascript" src="./js/dzbcx.js"></script>
<!--<script type="text/javascript">
	function queryDetailRzxx(sqbxh){
		//var ah = screen.availHeight - 56;
		//var aw = screen.availWidth - 10;
		window.open("../../xzqhwh/bgrzgl/DzbcxService.querySqbxx.do?SQBXH="+sqbxh,"申请表日志详细信息","height=450,width=500,left=200,screenX=0,top=200,screenY=0,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes");
	}
</script>-->
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</HEAD>
<BODY>
<html:form method="POST" styleId="form1" action= "work/xzqhwh/bgrzgl/BgrzglService.querySqbrz.do">
<!--startprint-->
	<table border="0">
		<tr>
			<td>
			<logic:present name="RZLIST">
				<display:table name="RZLIST" id="rzMap" align="center"  pagesize="10"  decorator="demo.DemoWrapper"  class="tab"  requestURI="DzbcxService.queryMxb.do">
					<display:column style="width:15%;" title="原行政区划代码" sortable="true" property="YSXZQH_DM"/>
					<display:column style="width:20%;text-align:left;" title="原行政区划名称" property="YSXZQH_MC" sortable="true"/>
					<display:column style="width:12%;text-align:left;" title="变更类型" sortable="true" property="BGLX_DM"/>
					<display:column style="width:15%;" title="目标行政区划代码" sortable="true" property="MBXZQH_DM"/>
					<display:column style="width:20%;text-align:left;" title="目标行政区划名称" property="MBXZQH_MC" sortable="true"/>
					<display:column style="width:18%;" property="XGSJ" title="提交时间" sortable="true"/>	
				</display:table>
			</logic:present>
			<logic:notPresent name="RZLIST">
			<display:table name="RZLIST" align="center" id="rzMap" decorator="demo.DemoWrapper" requestURI="DzbcxService.queryMxb.do"  pagesize="10" class="tab">
					<display:column style="width:15%;" title="原行政区划代码"/>
					<display:column style="width:20%;text-align:left;" title="原行政区划名称"/>
					<display:column style="width:12%;text-align:left;" title="变更类型"/>
					<display:column style="width:15%;" title="原行政区划代码"/>
					<display:column style="width:20%;text-align:left;" title="原行政区划名称"/>
					<display:column style="width:18%;" title="提交时间"/>	
					</display:table>
			</logic:notPresent>
			</td>
		</tr>
	</table>
	<!--endprint-->
	<center>
		<button id="btn_query" accesskey="p" class="noprint" onclick="printMxb();">打 印(<u>P</u>)</button>
		
		<button id="btn_refur" accesskey="n"  onclick="javascript:window.location.reload()">刷 新(<u>N</u>)</button>
	</center>
</html:form>

</body>
</html>