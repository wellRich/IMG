<%@ include file="../../../../work/public/head.jsp" %>
<%
	if(BusinessRetCode!=null){
		if(!BusinessRetCode.equals(new java.lang.Integer(com.padis.common.constants.CommonConstants.RTNMSG_SUCCESS).toString())){
%>
	<script language="javascript">
		showMessage("<%=BusinessMsg%>",2);
		parent.location.href="index.jsp";
	</script>
<%}else{%>
	<script language="javascript">
		showMessage("<%=BusinessMsg%>",0);
		parent.location.href="index.jsp";
	</script>
<%	
}}%>