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
	 if(!getCount(sqdxh)){
		showMessage("�˱�����ձ����ϸ����̫�࣬������ɾ����ϸ���绰��ϵ��ά��Ա��лл������",0);
		return false;
	 }
	 if(flag)
	 {
	 	 if(sqdzt_dm != "10" && sqdzt_dm!="21" && sqdzt_dm!="")
		 {
		 	showMessage("ֻ��ɾ��������ձ�״̬Ϊδ�ύ�����δͨ���ļ�¼��",0);
		 	return false;
		 }
		if(confirm("ȷ��ɾ���ü�¼����ɾ��������ɾ������������ձ������ɾ����ϸ!"))
		{
			var paramXml = "<ROOT><map>";
			paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
			var service = new Service("com.padis.business.xzqhwh.zxbg.sqdsc.SqdscService.deleteSqd");
			var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
			var code = service.getCode();  //���ؽ����״̬
			var message = service.getMessage();  //���ص���Ϣ
			if( code != 2000 ){
				showBizMsg(code,message);
			}else{
				showMessage("����ִ�гɹ���",0);			
			}
			location.href="index.jsp";
		}
	 }else
	 {
	 	showMessage("��ѡ��һ��������ձ��¼��",0);
		return false;
	 }
}

function getCount(sqdxh){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.sqdsc.SqdscService.getCount");
	var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
	var doc = loadXml(resultXml); //�����׼��MSXML2.DOMDocument����
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var count = doc.selectSingleNode("ROOT/Result/MAP/COUNT").text;
		if(count>30){
			return false;
		}else {
			return true;
		}
	}
}
