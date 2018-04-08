<%@ include file="../../../../../work/public/head.jsp" %>
<%	
	List hzmcList = (List)request.getAttribute("HZMCLIST");
	String mrhzmc = "";
%>
<HTML >
<HEAD>
<TITLE>按变更类型分的区划变更情况汇总表</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="js/index.js"></script>
</HEAD>
<BODY>
	<form method="POST" name="form2" >
		<center>
			汇总名称：
			<select name="YYHZMC" style="width:30%">
				<option value="">======请选择======</option>
				<%if(hzmcList!=null){
					for(int i=0; i<hzmcList.size();i++){
						Map hzmcMap = (Map)hzmcList.get(i);
						String hzmc = (String)hzmcMap.get("HZMC");
						if(i==0){
							mrhzmc = hzmc;
							%>				
							<option value="<%=hzmc%>" selected="true"><%=hzmc%></option>
						<%}else{%>
							<option value="<%=hzmc%>"><%=hzmc%></option>
					<%}}%>
				<%}%>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="btn_query" accesskey="q" onclick="queryHzbByHzmc()">查 询(<u>Q</u>)</button>
		</center>
	</form>
	<iframe src="AbglxcxService.queryMxb.do?XZQH_DM=000000000000000&HZMC=<%=mrhzmc%>" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" scrolling="no" frameborder="0"></iframe>
</body>
</html>