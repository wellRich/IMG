function initPage(){
	window.open("promptMessage.html","��ʾ��Ϣ","height=170px,width=450px,top=250,left=400,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=no,resizable=no");
}

function addSqd(mc,count)
{
	if(count!="0"){
		showMessage("������ͬһ��ʱ���������������ձ�",0);
		return ;
	}
	var return_value= showModalDialog("controlWindow.jsp?control=add&mc="+mc,window,"dialogWidth=450px;dialogHeight=280px;center:yes;");
	if(return_value == null)
	{
		return ;
	}
	 if(return_value !=null && return_value!=""){
	 	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.jlbgdzb.JlbgdzbService.querySqdzt");
		var resultXml = service.doService(); //�������󣬻�ȡ���ؽ��
		var doc = loadXml(resultXml);
		if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
			var counts = doc.selectSingleNode("ROOT/Result/MAP/COUNT").text;
			if(Number(counts)>0){
				showMessage("������ͬһ��ʱ���������������ձ�",0);
				location.href="index.jsp";
				return ;
			}
		}
		var sqdmc = return_value.substring(0,return_value.indexOf("&"));
		var bz=return_value.substring(return_value.indexOf("&")+1);
		var paramXml = "<ROOT><map>";
		paramXml = paramXml+"<SQDMC>"+sqdmc+"</SQDMC><BZ>"+bz+"</BZ></map></ROOT>";
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.jlbgdzb.JlbgdzbService.addSqd");
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
	
}

function checkScsj(sqdxh){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.jlbgdzb.JlbgdzbService.checkScsj");
	var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
	var doc = loadXml(resultXml); //�����׼��MSXML2.DOMDocument����
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var flag = doc.selectSingleNode("ROOT/Result/MAP/FLAG").text;
		return flag;
	}
}

