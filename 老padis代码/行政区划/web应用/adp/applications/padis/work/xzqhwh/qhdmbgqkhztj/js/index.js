function btn_sub_query()
{	
	var hzmc = document.getElementById("HZMC").value;
	var hzsjq = document.getElementById("HZSJQ").value;
	var hzsjz = document.getElementById("HZSJZ").value;
	var checkObj = document.getElementsByName("HZTJ");
	var hztj="";
	var count=0;
	for(var i=0;i<checkObj.length;i++){
		if(checkObj[i].checked){
			hztj = checkObj[i].value;
			count++;
		}
	}
	if(count==2){
		hztj = "3";
	}
	if(hzmc==""){
		showMessage("���λ������Ʋ���Ϊ��,����д��",0);
		return false;
	}
	if(hzsjq==""&&hzsjz==""){
		showMessage("����ʱ�䲻��Ϊ�գ�",0);
		return false;
	}
	if(hzsjq!=""&&hzsjz!=""){
		if(hzsjq>hzsjz){
			showMessage("����ʱ�����ܴ��ڻ���ʱ��ֹ��",0);
			return false;
		}
	}
	if(hztj==""){
		showMessage("��ѡ�����������",0);
		return false;
	}
	location="QhdmbgqkhztjService.sumQhbgqk.do?HZSJQ="+hzsjq+"&HZSJZ="+hzsjz+"&HZTJ="+hztj+"&HZMC="+hzmc;
}

function btn_sub_reset()
{	
	document.getElementById("HZSJQ").value = "";
	document.getElementById("HZSJZ").value = "";
	document.getElementById("HZMC").value = "";
	document.getElementById("YYHZMC").value = "";
	var checkObj = document.getElementsByName("HZTJ");
	for(var i=0;i<checkObj.length;i++){
		if(checkObj[i].checked){
			checkObj[i].checked=false;
		}
	}
}

function btn_sub_delete()
{	
	var yyhzmc = document.getElementById("YYHZMC").value;
	if(yyhzmc==""){
		showMessage("��ѡ��Ҫɾ���Ļ������ƣ�",0);
		return false;
	}else{
		if(confirm("�˴ν���ɾ�������л�������Ϊ��"+yyhzmc+"�������ݣ���ȷ��Ҫɾ���˻���������")){
			var paramXml = "<ROOT><map>";
			paramXml = paramXml+"<HZMC>"+yyhzmc+"</HZMC></map></ROOT>";
			var service = new Service("com.padis.business.xzqhwh.qhdmbgqkhztj.QhdmbgqkhztjService.deleteQhbgqk");
			var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
			var code = service.getCode();  //���ؽ����״̬
			var message = service.getMessage();  //���ص���Ϣ
			if( code != 2000 ){
				showBizMsg(code,message);
			}else{
				showMessage("ɾ���������ݳɹ���",0);
				location="index.jsp";
			}
		}
	}
}





