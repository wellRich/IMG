function btn_sub_query()
{
	var ysxzqh_dm = document.getElementById("YSXZQH_DM").value;
	var ysxzqh_mc = document.getElementById("YSXZQH_MC").value;
	var mbxzqh_dm = document.getElementById("MBXZQH_DM").value;
	var mbxzqh_mc = document.getElementById("MBXZQH_MC").value;
	var bglx_dm = document.getElementById("BGLX_DM").value;
	var bgsjq = document.getElementById("BGSJQ").value;
	var bgsjz = document.getElementById("BGSJZ").value;
	if(ysxzqh_dm!=""){
		if(!isNumber(ysxzqh_dm)){
			showMessage("只能输入数字！",0);
			return false;
		}
	}
	if(mbxzqh_dm!=""){
		if(!isNumber(mbxzqh_dm)){
			showMessage("只能输入数字！",0);
			return false;
		}
	}
	if(bgsjq!=""&&bgsjz!=""){
		if(bgsjq>bgsjz){
			showMessage("变更时间起不能大于变更时间止！",0);
			return false;
		}
	}
	
	window.open("BgdzcxService.queryMxb.do?PAGESIZE=10&YSXZQH_DM="+ysxzqh_dm+"&YSXZQH_MC="+ysxzqh_mc+"&MBXZQH_DM="+mbxzqh_dm+"&MBXZQH_MC="+mbxzqh_mc+"&BGLX_DM="+bglx_dm+"&BGSJQ="+bgsjq+"&BGSJZ="+bgsjz,"iframe2");
}

function btn_sub_reset()
{	
	document.getElementById("YSXZQH_DM").value="";
	document.getElementById("YSXZQH_MC").value="";
	document.getElementById("MBXZQH_DM").value="";
	document.getElementById("MBXZQH_MC").value="";
	document.getElementById("BGLX_DM").value="";
	document.getElementById("BGSJQ").value="";
	document.getElementById("BGSJZ").value="";
}
