function btn_sub_query()
{	
	var xzqh_dm = document.getElementById("XZQH_DM").value;
	var rq = document.getElementById("RQ").value;
	window.open("DrzsbService.queryZip.do?XZQH_DM="+xzqh_dm+"&RQ="+rq+"&PAGESIZE=10","operationArea1");
}

function userOperate(bgzl_dm){	
	var zipxh=getSelect();
	if(!checkSelected()){
		showMessage("��ѡ��һ����¼��",0);
		return false;
	}
	if(zipxh!=""){
		var arr = new Array();
		arr = zipxh.split("||");
		zipxh = arr[0];
		if(arr[1]!="30"){
			showMessage("ֻ�ܵ����߼�У��ɹ������ݣ�",0);
			return false;
		}
	}
	location = "DrzsbService.userOperate.do?ZIPXH="+zipxh+"&BGZL_DM="+bgzl_dm;
}
/*
*��ȡѡ�еĵ����ĵ���¼�����к�
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
*����Ƿ���ѡ�еļ�¼
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