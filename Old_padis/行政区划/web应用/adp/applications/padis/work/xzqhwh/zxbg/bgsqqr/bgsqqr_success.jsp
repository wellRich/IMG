<%@ include file="../../../../work/public/head.jsp" %>
<%
	if(BusinessRetCode!=null){
		if(!BusinessRetCode.equals(new java.lang.Integer(com.padis.common.constants.CommonConstants.RTNMSG_SUCCESS).toString())){
%>
	<script language="javascript">
		showMessage("<%=BusinessMsg%>",2);
		history.back();
	</script>
<%}else{%>
	<script language="javascript">
		showMessage("操作执行成功！",0);	
		location.href="index.jsp";
	</script>
<%	
}}%>