<%@ include file="../../../../work/public/head.jsp" %>
<%
	String xzqh_dm = (String)map.get("XZQH_DM");
	if(xzqh_dm==null){
		xzqh_dm ="";
	}
%>
<html>
	<head>
		<title>行政区划省级上传</title>
		<meta http-equiv="Content-Type" content="text/xml; charset=GBK">
		<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/js/check.js"></SCRIPT>
		<script type="text/javascript">
			function btn_sub_save()
			{
				var fileName = document.getElementById("XZQHDZBZIP").value;
				if(fileName!=""){
					var index = fileName.lastIndexOf(".");
					if(index<0){
						showMessage("请选择ZIP压缩文件上传！",0);
						return false;
					}else{
						var hzm = fileName.substring(index+1,fileName.length);
						if(hzm.toUpperCase()!="ZIP"){
							showMessage("请选择ZIP压缩文件上传！",0);
							return false;
						}
						var xzqhDm = document.getElementById("xzqh_dm").value;
						var index1 = fileName.lastIndexOf("\\");
						var wjm="";
						var scsf = "";
						var args = new Array();
						if(index1>0){
							wjm = fileName.substring(index1+1,index);
							scsf = wjm.substring(5,11);
							args = wjm.split("_");
						}else{
							index1 = fileName.lastIndexOf("/");
							if(index1>0){
								wjm = fileName.substring(index1+1,index);
								scsf = wjm.substring(5,11);
								args = wjm.split("_");
							}
						}
						if(args.length!=3){
							showMessage("上传文件名没有用两个下划线分割，请修改文件名！",0);
							return false;
						}else{
							if(args[0].toUpperCase()!="QHDM"){
								showMessage("上传文件名必须以“QHDM_”或“qhdm_”开头，请修改文件名！",0);
								return false;
							}else if(!isNumber1(args[1],6)||args[1].length<6){
								showMessage("上传文件名中间部分必须是六位行政区划代码，请修改文件名！",0);
								return false;
							}else if(!isNumber1(args[2],8)||args[2].length<8){
								showMessage("上传文件名最后必须是年月日组成的八位数字，请修改文件名！",0);
								return false;
							}
						}
						if(scsf!=xzqhDm){
							showMessage("请选择本省数据上传！",0);
							return false;
						}else{
							var paramXml = "<ROOT><map>";
							paramXml = paramXml+"<XZQH_DM>"+xzqhDm+"</XZQH_DM><WJM>"+wjm+"</WJM></map></ROOT>";
							var service = new Service("com.padis.business.xzqhwh.sjjzbg.xzqhsjsc.XzqhsjscService.isUpload");
							var resultXml = service.doService(paramXml); //发送请求，获取返回结果
							var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
							if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
								var msg = doc.selectSingleNode("ROOT/Result/MAP/FLAG").text;
								if(msg!="可以上传"){
									showMessage(msg,0);
									return false;
								}
							}
						}
					}
					document.qForm.action="XzqhsjscService.uploadSjbgsj.do";
					document.qForm.submit();
				}else{
					showMessage("请选择上传文件！",0);
					return false;
				}				
			}
		</script>
	</head>
	<body>
		<form name="qForm" action="/work/xzqhwh/sjjzbg/xzqhsjsc/XzqhsjscService.uploadSjbgsj.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="xzqh_dm" value="<%=xzqh_dm%>"/>
			<input type="hidden" name="subDirKey" value="xzqhwh"/>
			<table cellspacing="0" cellpadding="0" align="center" style="margin-top:12px;width:95%;">
				<tr>
					<td align="right" width="12%" >文件上传&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<input type="file" name="XZQHDZBZIP"  SIZE="30"/>
					</td>
				</tr>
			</table>
			<center>
				<button class="btn-big" accesskey="i" onClick="btn_sub_save()">导入文件(<u>I</u>)</button>
			</center>
			<iframe name="iframeQuery" width="100%" id="iframeQuery" frameborder="0" scrolling="auto" src="XzqhsjscService.queryXzqhjzbgzip.do?PAGESIZE=10" allowTransparency="true" onload="this.height=iframeQuery.document.body.scrollHeight"></iframe>
		</form>			
	</body>
</html>