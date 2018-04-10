
			
function btn_sub_query()
{	
	var xzqh_dm = document.getElementById("XZQH_DM").value;
	var reg = new RegExp("[0-9][0-9][0-9][0-9][0-9][0-9]");
	
	if(xzqh_dm!=null && xzqh_dm!="" )
	{
		if(reg.test(xzqh_dm))
		{
			var rq = document.getElementById("RQ").value;
			window.open("CysjbjService.queryZip.do?XZQH_DM="+xzqh_dm+"&RQ="+rq+"&PAGESIZE=10","operationArea1");	
		}
		else
		{
			showMessage("请输入规范的行政区划",0);
		}
	
	}else
	{
		var rq = document.getElementById("RQ").value;
		window.open("CysjbjService.queryZip.do?XZQH_DM="+xzqh_dm+"&RQ="+rq+"&PAGESIZE=10","operationArea1");	
	}
}
function queryDif()
{
	 var zipxhArray=document.getElementsByName("zipxh_radio");
	 var zipxh="";
	 var jzbgzt="";
	 var cylx = document.getElementById("CYLX").value;
	 
	 for(var i=0;i<zipxhArray.length;i++)
	 {
	 	if(zipxhArray[i].checked == true)
	 	{
			zipxh=zipxhArray[i].value.split("||")[0];
			jzbgzt=zipxhArray[i].value.split("||")[1];
	 	}
	 }
	 if(zipxh=="")
	 {
	 	showMessage("请选择一条记录",0);
	 }else
	 {
	 	if(jzbgzt=="40")
	 	{
	 		window.open("CysjbjService.queryDif.do?PAGESIZE=20&ZIPXH="+zipxh+"&CYLX="+cylx,"operationArea2");	
	 	}else
	 	{
		 	 showMessage("只能对变更对照表生成成功的的数据进行差异比较",0);
	 	}
	 }
}
