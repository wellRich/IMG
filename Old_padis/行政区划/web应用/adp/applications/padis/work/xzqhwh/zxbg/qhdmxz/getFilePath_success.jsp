<%@ include file="../../../../work/public/head.jsp" %>
<%
	String filePathName = (String)map.get("PATH");
	if(filePathName==null){%>
		<script language="javascript">
			showMessage("未找到文件路径！",0);
			history.back();
		</script>
	<%}
	String src = "downloadFile.jsp?filePathName=" + filePathName;
	RequestDispatcher dispatcher=request.getRequestDispatcher(src);  
	dispatcher.forward(request,response);
%>
