function queryHzbByHzmc(){
	var hzmc = window.document.getElementById("YYHZMC").value;
	window.open("AqhjbcxService.queryMxb.do?XZQH_DM=000000000000000&HZMC="+hzmc,"iframe1");
}
function exportQhbgqkhzb()
{
	var hzmc = window.parent.document.getElementById("YYHZMC").value;
	var xzqh_dm = document.getElementById("XZQHDM").value;
	location="AqhjbcxService.exportQhbgqkhzb.do?value(XZQH_DM)="+xzqh_dm+"&value(HZMC)="+hzmc;
}
function getXjxzqh(xzqh_dm){
	var hzmc = window.parent.document.getElementById("YYHZMC").value;
	location="AqhjbcxService.queryMxb.do?XZQH_DM="+xzqh_dm+"&HZMC="+hzmc;
}
function goBack(){
	var hzmc = window.parent.document.getElementById("YYHZMC").value;
	var xzqh_dm = document.getElementById("XZQHDM").value;
	location="AqhjbcxService.queryMxb.do?XZQH_DM="+xzqh_dm+"&HZMC="+hzmc;
}