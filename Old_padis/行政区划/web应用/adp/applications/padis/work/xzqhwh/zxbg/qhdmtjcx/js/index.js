function queryXzqhByDm(){
	var xzqh_dm =document.XzqhDmForm.XZQH_DM.value;
	if(xzqh_dm==""){
		showMessage("�������벻��Ϊ�գ�����д��",0);
		return false;
	}else {
		if(!isNumber(xzqh_dm)){
			showMessage("�������벻�ܺ��з������ַ���",0);
			return false;
		}
		if(xzqh_dm.length<6){
			showMessage("������д��λ�������룬�벹�䣡",0);
			return false;
		}
	}
	document.XzqhDmForm.submit();
}

function queryXzqhByMc(obj){
	var xzqh_mc =document.XzqhMcForm.XZQH_MC.value;
	var jcdm =document.XzqhMcForm.JCDM.value;
	if(xzqh_mc==""){
		showMessage("�������Ʋ���Ϊ�գ�����д��",0);
		return false;
	}
	
	document.XzqhMcForm.submit();
}