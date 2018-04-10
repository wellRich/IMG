<%@ include file="../../../../work/public/head.jsp" %>
<html>
	<head>
		<title>国家审核</title>
		<script type="text/javascript" src="./js/gjsh.js"></script>
		<meta http-equiv="Content-Type"	content="text/xml; charset=GBK">
	</head>
	<body>
		<iframe src="GjshService.queryYtj.do" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" scrolling="no" frameborder="0"></iframe>
		<iframe src="GjshService.queryWtj.do" id="iframe2" name="iframe2" width="100%" onload="this.height=iframe2.document.body.scrollHeight" scrolling="no" frameborder="0" ></iframe>
	</body>
</html>