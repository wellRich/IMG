
function btn_sub_query()
{	
	var xzqh_dm = document.getElementById("XZQH_DM").value;
	var rq = document.getElementById("RQ").value;
	window.open("JzbgglService.queryZip.do?XZQH_DM="+xzqh_dm+"&RQ="+rq+"&PAGESIZE=10","operationArea1");
}

function userOperate(bgzl_dm){	
	var zipxh=getSelect();
	if(!checkSelected()){
		showMessage("��ѡ��һ����¼��",0);
		return false;
	}
	location = "JzbgglService.userOperate.do?ZIPXH="+zipxh+"&BGZL_DM="+bgzl_dm;
}

function checkZtdm(jzbgzt_dm){
	var zipxlArray=document.getElementsByName("zipxh_radio");
	for(var i=0;i<zipxlArray.length;i++){
		if(zipxlArray[i].checked==true){
			if(jzbgzt_dm=="10"){			
				//document.getElementById("btn_check").disabled=true;
				document.getElementById("btn_impTemp").disabled=false;
			}
			/*else if(jzbgzt_dm=="20"){
				document.getElementById("btn_impTemp").disabled=true;
				document.getElementById("btn_check").disabled=false;				
			}*/
			else{
				document.getElementById("btn_impTemp").disabled=true;
				//document.getElementById("btn_check").disabled=true;
			}
			break;
		}
	}	
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
