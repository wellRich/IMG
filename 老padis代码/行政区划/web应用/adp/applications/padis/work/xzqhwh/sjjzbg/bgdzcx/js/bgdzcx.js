function btn_sub_query()
{	
	var xzqh_dm = document.getElementById("XZQH_DM").value;
	var reg = new RegExp("[0-9][0-9][0-9][0-9][0-9][0-9]");
	
	if(xzqh_dm!=null && xzqh_dm!="" )
	{
		if(reg.test(xzqh_dm))
		{
			var rq = document.getElementById("RQ").value;
			window.open("BgdzcxService.queryZip.do?XZQH_DM="+xzqh_dm+"&RQ="+rq+"&PAGESIZE=10","operationArea1");	
		}
		else
		{
			showMessage("请输入规范的行政区划",0);
		}
	
	}else
	{
		var rq = document.getElementById("RQ").value;
		document.getElementById("operationArea2").src="BgdzcxService.queryDzb.do?PAGESIZE=10";
		window.open("BgdzcxService.queryZip.do?XZQH_DM="+xzqh_dm+"&RQ="+rq+"&PAGESIZE=10","operationArea1");	
	}
}
function queryDzb()
{
	 var zipxhArray=document.getElementsByName("zipxh_radio");
	 var zipxh="";
	 var bgxzqh_dm = document.getElementById("BGXZQH_DM").value;
	 var bglx_dm = document.getElementById("BGLX_DM").value;
	 var cwsjbz = document.getElementById("CWSJBZ").value;
	 for(var i=0;i<zipxhArray.length;i++)
	 {
	 	if(zipxhArray[i].checked == true)
	 	{
			zipxh=zipxhArray[i].value;
	 	}
	 }
	 if(zipxh=="")
	 {
	 	showMessage("请选择一条记录",0);
	 }else
	 {
	 		window.open("BgdzcxService.queryDzb.do?PAGESIZE=10&ZIPXH="+zipxh+"&BGXZQH_DM="+bgxzqh_dm+"&BGLX_DM="+bglx_dm+"&CWSJBZ="+cwsjbz,"operationArea2");	
	 }
}

function queryTempDzb(){	

	var zipxh=getSelect();
	if(checkSelected()!=1){
		showMessage("请选择一条记录！",0);
		return false;
	}
	window.open("BgdzcxService.queryDzb.do?ZIPXH="+zipxh+"&PAGESIZE=10","operationArea2");
}
/*
	*获取选中的电子文档记录的序列号
	*/
	function getSelect()
	{
	 var zipxlArray=document.getElementsByName("zipxh_check");
	 var rtnzipxh="";
	 var cnt=0;
	 for(var i=0;i<zipxlArray.length;i++)
	 {
	 	if(zipxlArray[i].checked==true)
	 	{
	 		if(cnt==0)
	 		{
	 			rtnzipxh=zipxlArray[i].value;
	 			cnt=1;
	 		}
	 		else
	 		{
	 			rtnzipxh=rtnzipxh+","+zipxlArray[i].value;
	 		}
	 	}
	 }
	 return rtnzipxh;
	}
	
	/*
	*检查是否有选中的记录
	*/
	function checkSelected()
	{
	 var zipxlArray=document.getElementsByName("zipxh_check");
	 var cnt=0;
	 for(var i=0;i<zipxlArray.length;i++)
	 {
	 	if(zipxlArray[i].checked==true)
	 	{
	 		cnt++;
	 	}
	 }
	 return cnt;
	}
