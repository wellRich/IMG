<%@ include file="../../../../work/public/head.jsp" %>
<%
	String xzqh_dm = (String)map.get("XZQH_DM");
	if(xzqh_dm==null){
		xzqh_dm ="";
	}
%>
<html>
	<head>
		<title>��������ʡ���ϴ�</title>
		<meta http-equiv="Content-Type" content="text/xml; charset=GBK">
		<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/js/check.js"></SCRIPT>
		<script type="text/javascript">
			function btn_sub_save()
			{
				var fileName = document.getElementById("XZQHDZBZIP").value;
				if(fileName!=""){
					var index = fileName.lastIndexOf(".");
					if(index<0){
						showMessage("��ѡ��ZIPѹ���ļ��ϴ���",0);
						return false;
					}else{
						var hzm = fileName.substring(index+1,fileName.length);
						if(hzm.toUpperCase()!="ZIP"){
							showMessage("��ѡ��ZIPѹ���ļ��ϴ���",0);
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
							showMessage("�ϴ��ļ���û���������»��߷ָ���޸��ļ�����",0);
							return false;
						}else{
							if(args[0].toUpperCase()!="QHDM"){
								showMessage("�ϴ��ļ��������ԡ�QHDM_����qhdm_����ͷ�����޸��ļ�����",0);
								return false;
							}else if(!isNumber1(args[1],6)||args[1].length<6){
								showMessage("�ϴ��ļ����м䲿�ֱ�������λ�����������룬���޸��ļ�����",0);
								return false;
							}else if(!isNumber1(args[2],8)||args[2].length<8){
								showMessage("�ϴ��ļ�������������������ɵİ�λ���֣����޸��ļ�����",0);
								return false;
							}
						}
						if(scsf!=xzqhDm){
							showMessage("��ѡ��ʡ�����ϴ���",0);
							return false;
						}else{
							var paramXml = "<ROOT><map>";
							paramXml = paramXml+"<XZQH_DM>"+xzqhDm+"</XZQH_DM><WJM>"+wjm+"</WJM></map></ROOT>";
							var service = new Service("com.padis.business.xzqhwh.sjjzbg.xzqhsjsc.XzqhsjscService.isUpload");
							var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
							var doc = loadXml(resultXml); //�����׼��MSXML2.DOMDocument����
							if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
								var msg = doc.selectSingleNode("ROOT/Result/MAP/FLAG").text;
								if(msg!="�����ϴ�"){
									showMessage(msg,0);
									return false;
								}
							}
						}
					}
					document.qForm.action="XzqhsjscService.uploadSjbgsj.do";
					document.qForm.submit();
				}else{
					showMessage("��ѡ���ϴ��ļ���",0);
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
					<td align="right" width="12%" >�ļ��ϴ�&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<input type="file" name="XZQHDZBZIP"  SIZE="30"/>
					</td>
				</tr>
			</table>
			<center>
				<button class="btn-big" accesskey="i" onClick="btn_sub_save()">�����ļ�(<u>I</u>)</button>
			</center>
			<iframe name="iframeQuery" width="100%" id="iframeQuery" frameborder="0" scrolling="auto" src="XzqhsjscService.queryXzqhjzbgzip.do?PAGESIZE=10" allowTransparency="true" onload="this.height=iframeQuery.document.body.scrollHeight"></iframe>
		</form>			
	</body>
</html>