function btn_sub_query()
{	
	var sqdmc = document.getElementById("SQDMC").value;
	var sqdzt_dm = document.getElementById("SQDZT_DM").value;
	var bgxzqh_dm = document.getElementById("BGXZQH_DM").value;
	var tzsm = document.getElementById("TZSM").value;
	var bgsjq = document.getElementById("BGSJQ").value;
	var bgsjz = document.getElementById("BGSJZ").value;
	if(bgsjq!=""&&bgsjz!=""){
		if(bgsjq>bgsjz){
			showMessage("变更时间起不能大于变更时间止！",0);
			return false;
		}
	}
	var iframe1 = document.getElementById("iframe1");
	iframe1.src="SqdcxService.querySqd.do?PAGESIZE=8&SQDMC="+sqdmc+"&SQDZT_DM="+sqdzt_dm+"&BGXZQH_DM="+bgxzqh_dm+"&TZSM="+tzsm+"&BGSJQ="+bgsjq+"&BGSJZ="+bgsjz;
}

function btn_sub_reset()
{	
	document.getElementById("SQDMC").value="";
	document.getElementById("SQDZT_DM").value="";
	document.getElementById("BGXZQH_DM").value="";
	document.getElementById("BGXZQH_MC").value="";
	document.getElementById("TZSM").value="";
	document.getElementById("BGSJQ").value="";
	document.getElementById("BGSJZ").value="";
}
function queryMxb()
{
	var sqdxh ="";
	var flag =false;
	var sqdArray=document.getElementsByName("sqdxh_radio");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
		}
	 }
	 if(flag)
	 {
	 	document.getElementById("iframe2").src="SqdcxService.queryMxb.do?PAGESIZE=10&SQDXH="+sqdxh;
	 }else
	 {
	 	showMessage("请选择一条变更对照表记录！",0);
		return false;
	 }
}


function expSqd()
{
	var flag =false;
	var sqdxh ="";
	var sqdmc="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
			sqdmc = sqdArray[i].value.split("||")[1];
		}
	 }
	 if(flag)
	 {
	 		form1.action="SqdcxService.expSqd.do?value(SQDXH)="+sqdxh+"&value(SQDMC)="+sqdmc;
	 		form1.submit();
			
	 }else
	 {
	 	showMessage("请选择一条变更对照表记录！",0);
		return false;
	 }
}