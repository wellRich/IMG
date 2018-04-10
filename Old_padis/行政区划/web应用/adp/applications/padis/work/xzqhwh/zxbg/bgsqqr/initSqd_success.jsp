<%@	include file="../../../../work/public/head.jsp"%>
<%
	String count = String.valueOf(map.get("COUNT"));
%>
<html>
	<HEAD>
		<TITLE>变更申确认</TITLE>
		<script type="text/javascript" src="./js/bgsqqr.js"></script>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
	<body>
		<form method="POST" name="form1" action="">
			<logic:present name="SQDLIST">
				<display:table name="SQDLIST" id="sqdMap" align="center"  class="tab"
					 requestURI="BgsqqrService.initSqd.do">
					<display:column title="<input type='checkbox' class='checkbox' id='all_checked' name='all_checked' onclick='selectAll()'/>全选" style="width:8%" align="center">
						<input type="checkbox" id="sqdxh_check"  name="sqdxh_check" value='<bean:write name="sqdMap" property="SQDXH"/>||<bean:write name="sqdMap" property="SQDZTDM"/>' class="checkbox"/>
					</display:column>
					<display:column style="width:16%" property="SBXZQH_DM" title="区划代码" />
					<display:column style="width:15%" property="SBXZQH_MC" title="区划名称" />
					<display:column style="width:15%" property="SQDMC" title="变更对照表名称" sortable="true"  />
					<display:column style="width:16%" property="SQDZT_DM" title="变更对照表状态" />
					<display:column style="width:15%" property="BZ" title="备注" />
					<display:column style="width:15%" property="LRSJ" title="录入时间" dateFormat="yyyy-MM-dd" />
				</display:table>
			</logic:present>
			<logic:notPresent name="SQDLIST">
				<display:table name="SQDLIST" align="center" id="sqdMap"
				 requestURI="BgsqqrService.initSqd.do"
					 class="tab">
					<display:column style="width:8%;" property="" title="选择"/>
					<display:column style="width:16%" property="" title="区划代码" />
					<display:column style="width:15%" property="" title="区划名称" />
					<display:column style="width:15%" property="" title="变更对照表名称"/>
					<display:column style="width:16%"  property="" title="变更对照表状态"/>
					<display:column style="width:15%"  property="" title="备注" />
					<display:column style="width:15%"  property="" title="录入时间" dateFormat="yyyy-MM-dd" />
				</display:table>
			</logic:notPresent>
		 <center>
		 	<button id="btn_back" accesskey="b"  onclick="back()">驳 回(<u>B</u>)</button>
		 	<button id="btn_yes" accesskey="y"  onclick="bgsqqr('<%=count%>')">全省确认(<u>Y</u>)</button>
		 	<button id="btn_refur" accesskey="n"  onclick="javascript:window.location.reload()">刷 新(<u>N</u>)</button>
		</center>
		</form>
		<hr></hr>
				<iframe src="BgsqqrService.queryWtj.do?PAGESIZE=10" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" frameborder="0" ></iframe>
	</body>
</html>

