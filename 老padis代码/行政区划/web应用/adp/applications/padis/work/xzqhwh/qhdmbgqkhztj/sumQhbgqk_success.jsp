<%@ include file="../../../work/public/head.jsp" %>
<%
	String hzsjq = (String)map.get("HZSJQ");
	String hzsjz = (String)map.get("HZSJZ");
	String hzmc = (String)map.get("HZMC");
	if(hzsjq==null){
		hzsjq = "";
	}
	if(hzsjz==null){
		hzsjz = "";
	}
	if(hzmc==null){
		hzmc = "";
	}
	if(BusinessRetCode!=null){
		if(!BusinessRetCode.equals(new java.lang.Integer(com.padis.common.constants.CommonConstants.RTNMSG_SUCCESS).toString())){
%>
	<script language="javascript">
		showMessage("<%=BusinessMsg%>",2);
		history.back();
	</script>
<%}else{%>
	<script language="javascript">
		showMessage("<%=BusinessMsg%>",0);
		location="QhdmbgqkhztjService.initHztj.do?HZSJQ=<%=hzsjq%>&HZSJZ=<%=hzsjz%>&HZMC=<%=hzmc%>";
	</script>
<%	
}}%>