function btn_sub_query()
{	
	var dcsjq = document.getElementById("DCSJQ").value;
	var dcsjz = document.getElementById("DCSJZ").value;
	if(dcsjq==""&&dcsjz=="")
	{
		showMessage("查询时间不能为空！",0);
		return false;
	}
	if(dcsjq!=""&&dcsjz!=""){
		if(dcsjq>dcsjz){
			showMessage("变更时间起不能大于变更时间止！",0);
			return false;
		}
	}
	window.open("QhdmxzService.queryXzqhfb.do?DCSJQ="+dcsjq+"&DCSJZ="+dcsjz+"&PAGESIZE=20","operationArea1");
}

function btn_sub_reset()
{	
	document.getElementById("DCSJQ").value = "";
	document.getElementById("DCSJZ").value = "";
}


function exportFile(){
	var service = new Service("com.padis.business.xzqhwh.zxbg.qhdmxz.QhdmxzService.exportFile");
	var resultXml = service.doService();
	//var doc = loadXml(resultXml);
	var code = service.getCode();  //返回结果的状态
	var message = service.getMessage();  //返回的信息
	if( code != 2000 ){
		showBizMsg(code,message);
		return ;
	}else{
		showBizMsg(code,message);
	}
}