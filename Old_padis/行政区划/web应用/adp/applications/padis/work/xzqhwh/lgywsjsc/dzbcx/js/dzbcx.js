function btn_sub_query()
{	
	var sqbmc = document.getElementById("SQBMC").value;
	var sqblx_dm = document.getElementById("SQBLX_DM").value;
	var ysxzqh_dm = document.getElementById("YSXZQH_DM").value;
	var bgsjq = document.getElementById("BGSJQ").value;
	var bgsjz = document.getElementById("BGSJZ").value;
	var sqbzt_dm = document.getElementById("SQBZT_DM").value;
	if(bgsjq!=""&&bgsjz!=""){
		if(bgsjq>bgsjz){
			showMessage("变更时间起不能大于变更时间止！",0);
			return false;
		}
	}
	window.open("DzbcxService.queryMxb.do?SQBMC="+sqbmc+"&SQBLX_DM="+sqblx_dm+"&YSXZQH_DM="+ysxzqh_dm+"&BGSJQ="+bgsjq+"&BGSJZ="+bgsjz+"&SQBZT_DM="+sqbzt_dm+"&PAGESIZE=10","operationArea1");
}

function btn_sub_reset()
{	
	document.getElementById("SQBMC").value = "";
	document.getElementById("SQBLX_DM").value = "";
	document.getElementById("YSXZQH_DM").value = "";
	document.getElementById("YSXZQH_MC").value = "";
	document.getElementById("BGSJQ").value = "";
	document.getElementById("BGSJZ").value = "";
	document.getElementById("SQBZT_DM").value = "";
}

function printMxb(){
	var sqbmc = parent.document.getElementById("SQBMC").value;
	var sqblx_dm = parent.document.getElementById("SQBLX_DM").value;
	var ysxzqh_dm = parent.document.getElementById("YSXZQH_DM").value;
	var bgsjq = parent.document.getElementById("BGSJQ").value;
	var bgsjz = parent.document.getElementById("BGSJZ").value;
	var sqbzt_dm = parent.document.getElementById("SQBZT_DM").value;
	if(bgsjq!=""&&bgsjz!=""){
		if(bgsjq>bgsjz){
			showMessage("变更时间起不能大于变更时间止！",0);
			return false;
		}
	}
	location.href="DzbcxService.exportExcel.do?SQBMC="+sqbmc+"&SQBLX_DM="+sqblx_dm+"&YSXZQH_DM="+ysxzqh_dm+"&BGSJQ="+bgsjq+"&BGSJZ="+bgsjz+"&SQBZT_DM="+sqbzt_dm;  

}

/*
	*获取选中的电子文档记录的序列号
	*/
	function getSelect()
	{
	 var wdxlArray=document.getElementsByName("sqbxh_check");
	 var rtnwdxl="";
	 var cnt=0;
	 for(var i=0;i<wdxlArray.length;i++)
	 {
	 	if(wdxlArray[i].checked==true)
	 	{
	 		if(cnt==0)
	 		{
	 			rtnwdxl=wdxlArray[i].value;
	 			cnt=1;
	 		}
	 		else
	 		{
	 			rtnwdxl=rtnwdxl+","+wdxlArray[i].value;
	 		}
	 	}
	 }
	 return rtnwdxl;
	}
	
	/*
	*检查是否有选中的记录
	*/
	function checkSelected()
	{
	 var wdxlArray=document.getElementsByName("sqbxh_check");
	 var cnt=0;
	 for(var i=0;i<wdxlArray.length;i++)
	 {
	 	if(wdxlArray[i].checked==true)
	 	{
	 		cnt++;
	 	}
	 }
	 return cnt;
	}
