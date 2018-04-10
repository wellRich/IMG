var checkboxselect = false;
function selectAll(){	
	var sqdxh_check=document.getElementsByName("sqdxh_check");  	
    if(checkboxselect){		 
         checkboxselect = false;
	}else{					
         checkboxselect = true;
	}
     for(var i=0;i<sqdxh_check.length;i++)   
     {               
         sqdxh_check[i].checked   =   checkboxselect;     
     } 
}

	
function checkSelected()
{
 var wdxlArray=document.getElementsByName("sqdxh_check");
 var cnt=0;
 for(var i=0;i<wdxlArray.length;i++)
 {
	if(wdxlArray[i].checked==true)
	{
		cnt++;
	}
 }
 return cnt;
}

function queryMxb(){
	var sqdxh ="";
	
	if(checkSelected() != 1)
	{
		showMessage("请选择一条变更对照表记录",0);
		return ;
	}
	var sqdArray=document.getElementsByName("sqdxh_check");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			sqdxh = sqdArray[i].value;
		}
	 }

	 var iframe = document.getElementById("iframe1");
	iframe.src="GjssbgService.queryMxb.do?SQDXH="+sqdxh;

}

function gjshtg()
{
	if(checkSelected() < 1)
	{
		showMessage("请选择一条变更对照表记录",0);
		return ;
	}
	var sqdxh="";
	var sqdArray=document.getElementsByName("sqdxh_check");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			sqdxh =sqdxh+ sqdArray[i].value+",";
		}
	 }

	if(!confirm("确认所选变更对照表审核通过？"))
	{
		return;
	}

	if(sqdxh != "")
	{
		
		form1.action="GjssbgService.gjssbg.do?value(SQDXH)="+sqdxh;
		form1.submit();
	}

}





