<%@ include file="../../../work/public/head.jsp" %>

<HTML >
<HEAD>
<TITLE>�������־</TITLE>
<script type="text/javascript" src="./js/dzbcx.js"></script>
<!--<script type="text/javascript">
	function queryDetailRzxx(sqbxh){
		//var ah = screen.availHeight - 56;
		//var aw = screen.availWidth - 10;
		window.open("../../xzqhwh/bgrzgl/DzbcxService.querySqbxx.do?SQBXH="+sqbxh,"�������־��ϸ��Ϣ","height=450,width=500,left=200,screenX=0,top=200,screenY=0,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes");
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
					<display:column style="width:15%;" title="ԭ������������" sortable="true" property="YSXZQH_DM"/>
					<display:column style="width:20%;text-align:left;" title="ԭ������������" property="YSXZQH_MC" sortable="true"/>
					<display:column style="width:12%;text-align:left;" title="�������" sortable="true" property="BGLX_DM"/>
					<display:column style="width:15%;" title="Ŀ��������������" sortable="true" property="MBXZQH_DM"/>
					<display:column style="width:20%;text-align:left;" title="Ŀ��������������" property="MBXZQH_MC" sortable="true"/>
					<display:column style="width:18%;" property="XGSJ" title="�ύʱ��" sortable="true"/>	
				</display:table>
			</logic:present>
			<logic:notPresent name="RZLIST">
			<display:table name="RZLIST" align="center" id="rzMap" decorator="demo.DemoWrapper" requestURI="DzbcxService.queryMxb.do"  pagesize="10" class="tab">
					<display:column style="width:15%;" title="ԭ������������"/>
					<display:column style="width:20%;text-align:left;" title="ԭ������������"/>
					<display:column style="width:12%;text-align:left;" title="�������"/>
					<display:column style="width:15%;" title="ԭ������������"/>
					<display:column style="width:20%;text-align:left;" title="ԭ������������"/>
					<display:column style="width:18%;" title="�ύʱ��"/>	
					</display:table>
			</logic:notPresent>
			</td>
		</tr>
	</table>
	<!--endprint-->
	<center>
		<button id="btn_query" accesskey="p" class="noprint" onclick="printMxb();">�� ӡ(<u>P</u>)</button>
		
		<button id="btn_refur" accesskey="n"  onclick="javascript:window.location.reload()">ˢ ��(<u>N</u>)</button>
	</center>
</html:form>

</body>
</html>