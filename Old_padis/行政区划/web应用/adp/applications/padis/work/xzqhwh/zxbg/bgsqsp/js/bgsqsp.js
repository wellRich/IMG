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
			sqdxh = sqdArray[i].value.split("||")[0];
		}
	 }

	 var iframe = document.getElementById("iframe1");
	iframe.src="BgsqspService.queryMxb.do?SQDXH="+sqdxh;

}

function bgsqsp(str)
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
	var return_value="";
	
	if(str=='N')
	{
		if(confirm("确认所选变更对照表审核不通过？"))
		{
			return_value= showModalDialog("controlWindow.jsp",window,"dialogWidth=450px;dialogHeight=280px;center:yes;");
		}else
		{
			return;
		}
	}else
	{
		if(!confirm("确认所选变更对照表审核通过？"))
		{
			return;
		}
	}
	if(return_value == null)
	{
		return ;
	}
	if(sqdxh != "")
	{
		
		form1.action="BgsqspService.bgsqsp.do?value(FLAG)="+str+"&value(SQDXH)="+sqdxh+"&value(SPYJ)="+return_value;
		form1.submit();
	}

}

function expSqd()
{
	var flag =false;
	var sqdxh ="";
	var count=0;
	var sqdArray=document.getElementsByName("sqdxh_check");	
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			if(count>0){
				sqdxh = sqdxh+",";
			}
			sqdxh = sqdxh+sqdArray[i].value;
			count++;
		}
	 }
	 if(count>0)
	 {
	 		form1.action="BgsqspService.expSqd.do?value(SQDXH)="+sqdxh;
	 		form1.submit();
			
	 }else
	 {
	 	showMessage("请选择一条变更对照表记录！",0);
		return false;
	 }
}



