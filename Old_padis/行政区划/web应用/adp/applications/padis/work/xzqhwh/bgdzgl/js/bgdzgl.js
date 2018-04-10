function btn_sub_query()
{	
	var clzt_dm = document.getElementById("CLZT_DM").value;
	var bgxzqh_dm = document.getElementById("BGXZQH_DM").value;
	var bglx_dm = document.getElementById("BGLX_DM").value;
	var tjsjq = document.getElementById("TJSJQ").value;
	var tjsjz = document.getElementById("TJSJZ").value;
	if(tjsjq!=""&&tjsjz!=""){
		if(tjsjq>tjsjz){
			showMessage("提交时间起不能大于提交时间止！",0);
			return false;
		}
	}
	var iframe1 = document.getElementById("iframe1");
	iframe1.src="BgdzglService.queryDzb.do?PAGESIZE=10&CLZT_DM="+clzt_dm+"&BGXZQH_DM="+bgxzqh_dm+"&BGLX_DM="+bglx_dm+"&TJSJQ="+tjsjq+"&TJSJZ="+tjsjz;
}

function btn_sub_reset()
{	
	document.getElementById("BGLX_DM").value="";
	document.getElementById("BGXZQH_DM").value="";
	document.getElementById("BGXZQH_MC").value="";
	document.getElementById("CLZT_DM").value="";
	document.getElementById("TJSJQ").value="";
	document.getElementById("TJSJZ").value="";
}