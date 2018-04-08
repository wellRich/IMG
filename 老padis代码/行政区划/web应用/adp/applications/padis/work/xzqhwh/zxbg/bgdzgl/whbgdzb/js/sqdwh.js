function queryMxb(){
	var flag =false;
	var sqdxh ="";
	var sqdzt_dm="";
	var sbxzqh_dm="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
			sqdzt_dm =sqdArray[i].value.split("||")[1];
			sbxzqh_dm =sqdArray[i].value.split("||")[2];
		}
	 }
	 if(flag)
	 {
	 		var iframe = document.getElementById("iframe1");
			iframe.src="WhbgdzbService.queryMxb.do?PAGESIZE=10&SQDXH="+sqdxh+"&SQDZT_DM="+sqdzt_dm+"&SBXZQH_DM="+sbxzqh_dm;
	 }else
	 {
	 	showMessage("��ѡ��һ��������ձ��¼��",0);
		return false;
	 }

}

function getSelect(obj)
{
	var groupxh = obj.value;
	var str = document.getElementById("check_value").value;
	if(obj.checked == true)
	{
		if(str!=groupxh)
		{
			str = groupxh;
		}
	}else
	{
		str ="";	
	}
	var wdxlArray=document.getElementsByName("mxb_check");
	for(var i=0;i<wdxlArray.length;i++)
 	{
		if(wdxlArray[i].value==str)
		{
			wdxlArray[i].checked =true;
		}else
		{
			wdxlArray[i].checked =false;
		}
	 }
	 document.getElementById("check_value").value=str;
	
}

/*
*����Ƿ���ѡ�еļ�¼
*/
function checkSelected()
{
 var wdxlArray=document.getElementsByName("mxb_check");
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

function deleteMxb(sbxzqh_dm){
	
	if(checkSelected()<1){
		showMessage("��ѡ��Ҫɾ���ļ�¼��",0);
		return false;
	}
	
	var groupxh=document.getElementById("check_value").value;
	var sqdxh=document.getElementById("SQDXH").value;
	var flag = checkScsj(sqdxh);
	if(flag=="true"){
		showMessage("�����ϱ����ݲ���ɾ����ϸ��ֻ��ɾ������������ձ�",0);
		return false;
	}
	if(confirm("����������Ϊ"+groupxh+"�ı�����ձ�ɾ����"))
	{
		var paramXml = "<ROOT><map>";
		paramXml = paramXml+"<GROUPXH>"+groupxh+"</GROUPXH><SQDXH>"+sqdxh+"</SQDXH><SBXZQH_DM>"+sbxzqh_dm+"</SBXZQH_DM></map></ROOT>";
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb.WhbgdzbService.deleteMxb");
		var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
		var code = service.getCode();  //���ؽ����״̬
		var message = service.getMessage();  //���ص���Ϣ
		if( code != 2000 ){
			showBizMsg(code,message);
		}else{
			showMessage("����ִ�гɹ���",0);		
		}
		parent.location.href="index.jsp";
	}
}

function updateSqd()
{
	var flag =false;
	var sqdxh ="";
	var sqdmc="";
	var bz = "";
	var sqdzt_dm ="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
			sqdzt_dm =sqdArray[i].value.split("||")[1];
			sqdmc =  sqdArray[i].value.split("||")[3];
			bz =  sqdArray[i].value.split("||")[4];
		}
	 }
	 
	 if(flag)
	 {
	 	if(sqdzt_dm != "10" && sqdzt_dm!="21" && sqdzt_dm!="")
		 {
		 	showMessage("ֻ���޸ı�����ձ�״̬Ϊδ�ύ�����δͨ���ļ�¼��",0);
		 	return false;
		 }
		var return_value= showModalDialog("controlWindow.jsp?control=update&sqdmc="+sqdmc+"&bz="+bz,window,"dialogWidth=450px;dialogHeight=280px;center:yes;");
		if(return_value == null)
		{
		 	return ;
		}
		sqdmc = return_value.substring(0,return_value.indexOf("&"));
		bz=return_value.substring(return_value.indexOf("&")+1);
	 	var paramXml = "<ROOT><map>";
		paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH><SQDMC>"+sqdmc+"</SQDMC><BZ>"+bz+"</BZ></map></ROOT>";
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb.WhbgdzbService.updateSqd");
		var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
		var code = service.getCode();  //���ؽ����״̬
		var message = service.getMessage();  //���ص���Ϣ
		if( code != 2000 ){
			showBizMsg(code,message);
			return ;
		}else{
			location.href="index.jsp";
		}
	 }else
	 {
	 	showMessage("��ѡ��һ��������ձ��¼��",0);
		return false;
	 }
	
}

function deleteSqd()
{
	var flag =false;
	var sqdxh ="";
	var sqdzt_dm="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
			sqdzt_dm =sqdArray[i].value.split("||")[1];
		}
	 }
	 if(flag)
	 {
	 	 if(sqdzt_dm != "10" && sqdzt_dm!="21" && sqdzt_dm!="")
		 {
		 	showMessage("ֻ��ɾ��������ձ�״̬Ϊδ�ύ�����δͨ���ļ�¼��",0);
		 	return false;
		 }
		if(confirm("ȷ��ɾ���ü�¼��"))
		{
			var paramXml = "<ROOT><map>";
			paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
			var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb.WhbgdzbService.deleteSqd");
			var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
			var code = service.getCode();  //���ؽ����״̬
			var message = service.getMessage();  //���ص���Ϣ
			if( code != 2000 ){
				showBizMsg(code,message);
				return ;
			}else{
				showMessage("����ִ�гɹ���",0);
				location.href="index.jsp";
			}
		
		}
	 }else
	 {
	 	showMessage("��ѡ��һ��������ձ��¼��",0);
		return false;
	 }
}





function expSqd()
{
	var flag =false;
	var sqdxh ="";
	var sqdmc="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
			sqdmc = sqdArray[i].value.split("||")[2];
		}
	 }
	 if(flag)
	 {
	 		form1.action="WhbgdzbService.expSqd.do?value(SQDXH)="+sqdxh+"&value(SQDMC)="+sqdmc;
	 		form1.submit();
			
	 }else
	 {
	 	showMessage("��ѡ��һ��������ձ��¼��",0);
		return false;
	 }
}

function checkScsj(sqdxh){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb.WhbgdzbService.checkScsj");
	var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
	var doc = loadXml(resultXml); //�����׼��MSXML2.DOMDocument����
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var flag = doc.selectSingleNode("ROOT/Result/MAP/FLAG").text;
		return flag;
	}
}

