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

function bgsqqr(count)
{
	var wtj = window.frames["iframe1"].document.getElementById("wtj").value;
	if(wtj !="0")
	{
		showMessage("����δ�ύ������ձ������",0);
		return ;
	}
	if(count == "0")
	{
		if(confirm("ȷ���������ͨ���ı�����ձ�"))
		{
			form1.action="BgsqqrService.bgsqqr.do";
			form1.submit();		
		}

	}else
	{
		showMessage("����δ���ͨ���ļ�¼",0);
	}

}

function back()
{
	var sqdxh="";
	var sqdztdm="";
	var sqdxhArray=document.getElementsByName("sqdxh_check");
	var flag = false;
	var isPass = true;
	var counts=0;
	for(var i=0;i<sqdxhArray.length;i++)
	{
		if(sqdxhArray[i].checked==true)
		{
			flag = true;
			if(counts>0){
				sqdxh = sqdxh+",";
			}
			sqdxh =sqdxh+sqdxhArray[i].value.split("||")[0];
			sqdztdm = sqdxhArray[i].value.split("||")[1];
			if(sqdztdm!="20"){
				isPass = false;
			}
			counts++;
		}
	}
	if(flag)
	{
		if(!isPass)
		{
			showMessage("ֻ�ܲ������ͨ���ļ�¼!",0);
			return ;
		}
		if(confirm("ȷ�ϲ���ѡ�еı�����ձ�"))
		{
			var return_value="";
			return_value= showModalDialog("controlWindow.jsp",window,"dialogWidth=450px;dialogHeight=280px;center:yes;");
	
			if(return_value == null)
			{
				return ;
			}
			if(sqdxh != null && sqdxh != "")
			{
				form1.action="BgsqqrService.back.do?value(SQDXH)="+sqdxh+"&value(SPYJ)="+return_value;
				form1.submit();
			}		
		}

	}else
	{
		showMessage("��ѡ��һ����¼!",0);
	}
	
}