
function commitSqd(){
	var flag =false;
	var sqdxh ="";
	var sqdzt_dm="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	if(confirm("��ȷ���ڡ�¼������ϸ��ģ��������ɱ�������������������ϸ¼�빤������ֽ���ļ��ѱ���ظ���ͬ־�󶨡��Ƿ������",0)==true){
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
		 	showMessage("ֻ���ύ������ձ�״̬Ϊδ�ύ�����δͨ���ļ�¼��",0);
		 	return false;
		 }
		var paramXml = "<ROOT><map>";
		paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH><SQDZT_DM>"+sqdzt_dm+"</SQDZT_DM></map></ROOT>";
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.tjbgdzb.TjbgdzbService.queryMxb");
		var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
		var doc = loadXml(resultXml);
		var msg = "ȷ���ύ�ü�¼��";
		if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
			var flag = doc.selectSingleNode("ROOT/Result/MAP/SFLTJ").text;
			if(flag=="true"){
				msg = "�Ƿ�Ҫ���ϱ���";
			}
		}
		if(confirm(msg))
		{
			var paramXml = "<ROOT><map>";
			paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH><SQDZT_DM>"+sqdzt_dm+"</SQDZT_DM></map></ROOT>";
			var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.tjbgdzb.TjbgdzbService.commitSqd");
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
}


