<%@	include file="../../../../work/public/head.jsp"%>
<html>
	<HEAD>
		<TITLE>�����������</TITLE>
		<script type="text/javascript" src="./js/bgsqqr.js"></script>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
<script language="javascript">
function get_show()
{
	var spyj= document.getElementById("spyj").value;
	if(spyj.length <=0)
	{
		showMessage("����������Ϊ�գ�����д��",0);
		return;
	}
	
	if(spyj.length >800)
	{
		showMessage("���ݳ�������",0);
		document.getElementById("spyj").value = spyj.substring(0,800);
		return;
	}
	var strpattern = /^[^*.&%$\\#@\/]+$/;
	if(spyj!=null && spyj!=""  && !strpattern.test(spyj))
	{
		showMessage("���������ܰ��������ַ�",0);
		return;
	}
	window.returnValue = spyj;
	window.close();
}

</script>

<body style="border:0px; padding:0px; margin:0px" >
<table>
<tr height="10%">
	<td colspan="2"></td>
</tr>
<tr>
	<td width="5%"></td><td align="left">������<font color="red">*</font></td>
</tr>
<tr>
	<td width="5%"></td><td align="left" ><textarea  id="spyj" name="spyj" rows="5" ></textarea></td>
</tr>
<tr height="30%">
	<td align="center" colspan="2" ><button accesskey="s" id="btn_sl" onClick="get_show()">ȷ��(<u>S</u>)</button></td>
</tr>
</table>

</body>