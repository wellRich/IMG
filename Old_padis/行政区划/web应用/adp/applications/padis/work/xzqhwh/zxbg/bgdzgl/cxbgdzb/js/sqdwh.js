function revokeSqd(){
	var flag =false;
	var sqdxh ="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
		}
	 }
	 if(flag)
	 {	var sqdzt_dm = querySqdzt(sqdxh);
		if(sqdzt_dm != "11")
		{
		 	showMessage("ֻ�ܳ���������ձ�״̬Ϊ���ύ�ļ�¼��",0);
		 	location.href="index.jsp";
			return;
		}
		var paramXml = "<ROOT><map>";
		paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH><SQDZT_DM>"+sqdzt_dm+"</SQDZT_DM></map></ROOT>";
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.cxbgdzb.CxbgdzbService.revokeSqd");
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
	 }else
	 {
	 	showMessage("��ѡ��һ��������ձ��¼��",0);
		return false;
	 }
}

function querySqdzt(sqdxh){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.cxbgdzb.CxbgdzbService.querySqdzt");
	var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
	var doc = loadXml(resultXml); //�����׼��MSXML2.DOMDocument����
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var flag = doc.selectSingleNode("ROOT/Result/MAP/SQDZT_DM").text;
		return flag;
	}
}