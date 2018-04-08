function btn_sub_query()
{	
	var xzqh_dm = document.getElementById("XZQH_DM").value;
	var rq = document.getElementById("RQ").value;
	window.open("DrzsbService.queryZip.do?XZQH_DM="+xzqh_dm+"&RQ="+rq+"&PAGESIZE=10","operationArea1");
}

function userOperate(bgzl_dm){	
	var zipxh=getSelect();
	if(!checkSelected()){
		showMessage("请选择一条记录！",0);
		return false;
	}
	if(zipxh!=""){
		var arr = new Array();
		arr = zipxh.split("||");
		zipxh = arr[0];
		if(arr[1]!="30"){
			showMessage("只能导入逻辑校验成功的数据！",0);
			return false;
		}
	}
	location = "DrzsbService.userOperate.do?ZIPXH="+zipxh+"&BGZL_DM="+bgzl_dm;
}
/*
*获取选中的电子文档记录的序列号
*/
function getSelect()
{
 var zipxlArray=document.getElementsByName("zipxh_radio");
 var rtnzipxh="";
 for(var i=0;i<zipxlArray.length;i++)
 {
	if(zipxlArray[i].checked==true)
	{
		rtnzipxh=zipxlArray[i].value;
		break;
	}
 }
 return rtnzipxh;
}

/*
*检查是否有选中的记录
*/
function checkSelected()
{
 var zipxlArray=document.getElementsByName("zipxh_radio");
 var flag=false;
 for(var i=0;i<zipxlArray.length;i++)
 {
	if(zipxlArray[i].checked==true)
	{
		flag=true;
		break;
	}
 }
 return flag;
}