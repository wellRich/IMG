<%@ include file="../../../../../work/public/head.jsp" %>
<%	
	List hzmcList = (List)request.getAttribute("HZMCLIST");
	String mrhzmc = "";
%>
<HTML >
<HEAD>
<TITLE>��������ͷֵ��������������ܱ�</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript">
	function queryHzbByHzmc(){
		var hzmc = window.document.getElementById("YYHZMC").value;
		window.open("AqhjbhbglxcxService.queryMxb.do?HZMC="+hzmc,"iframe1");
	}
</script>
</HEAD>
<BODY>
	<form method="POST" name="form2" >
		<center>
			�������ƣ�
			<select name="YYHZMC" style="width:30%">
				<option value="">======��ѡ��======</option>
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
			<button id="btn_query" accesskey="q" onclick="queryHzbByHzmc()">�� ѯ(<u>Q</u>)</button>
		</center>
	</form>
	<iframe src="AqhjbhbglxcxService.queryMxb.do?XZQH_DM=000000000000000&HZMC=<%=mrhzmc%>" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" scrolling="no" frameborder="0"></iframe>
</body>
</html>