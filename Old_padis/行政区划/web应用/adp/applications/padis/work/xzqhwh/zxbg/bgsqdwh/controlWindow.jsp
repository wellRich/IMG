<%@	include file="../../../../work/public/head.jsp"%>
<%@page import="ctais.util.StringEx" %>
<html>
	<HEAD>
		<TITLE>������ձ�ά��</TITLE>
		<script type="text/javascript" src="./js/sqdwh.js"></script>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
<script language="javascript">
function get_show()
{
	var sqdmc = document.getElementById("sqdmc").value;
	var strpattern = /^[^*.&%$\\#@\/]+$/;
	if(sqdmc==null||sqdmc==""){
		showMessage("����д������ձ�����",0);
		return false;
	}
	if(!strpattern.test(sqdmc))
	{
		showMessage("���������ձ������в��ܰ��������ַ�",0);
		return false;
	}
	var bz = document.getElementById("bz").value;
	if(bz.length >1000)
	{
		showMessage("���ݳ�������",0);
		document.getElementById("bz").value = bz.substring(0,1000);
		return false;
	}
	if(bz!=null && bz!=""  && !strpattern.test(bz))
	{
		showMessage("��ע�в��ܰ��������ַ�",0);
		return false;
	}
	window.returnValue = sqdmc+"&"+document.getElementById("bz").value;
	window.close();
}
function init()
{
	var control='<%=request.getParameter("control")%>';
	if(control=="add")
	{
		var myDate = new Date();
		document.getElementById("sqdmc").value='<%=request.getParameter("mc")%>'+"���������������ձ�";
	}else if(control =="update")
	{
		document.getElementById("sqdmc").value ='<%=request.getParameter("sqdmc")%>';
		document.getElementById("bz").value ='<%=StringEx.sNull(request.getParameter("bz"))%>';
	}else
	{
		showMessage("��ʼ������",0);
		window.close();
	}
}
</script>

<body style="border:0px; padding:0px; margin:0px" onload="init()">
<table>
<tr height="10%">
	<td colspan="2" ></td>
</tr>
<tr height="30%">
	<td width="5%" align="right">������ձ����ƣ�</td><td width="8%"><input type="text" maxlength="40" name="sqdmc" id="sqdmc"/></td>
</tr>
<tr height="30%">
	<td width="5%" align="right">��ע��</td><td width="8%"><textarea name="bz" id="bz" rows="4"></textarea></td>
</tr>
<tr height="30%">
	<td colspan="2" align="center"><button accesskey="S" id="btn_sl" onClick="get_show()">ȷ��(<u>S</u>)</button></td>
</tr>
</table>

</body>