function queryXzqhByDm(){
	var xzqh_dm =document.XzqhDmForm.XZQH_DM.value;
	if(xzqh_dm==""){
		showMessage("区划代码不能为空，请填写！",0);
		return false;
	}else {
		if(!isNumber(xzqh_dm)){
			showMessage("区划代码不能含有非数字字符！",0);
			return false;
		}
		if(xzqh_dm.length<6){
			showMessage("最少填写六位区划代码，请补充！",0);
			return false;
		}
	}
	document.XzqhDmForm.submit();
}

function queryXzqhByMc(obj){
	var xzqh_mc =document.XzqhMcForm.XZQH_MC.value;
	var jcdm =document.XzqhMcForm.JCDM.value;
	if(xzqh_mc==""){
		showMessage("区划名称不能为空，请填写！",0);
		return false;
	}
	
	document.XzqhMcForm.submit();
}