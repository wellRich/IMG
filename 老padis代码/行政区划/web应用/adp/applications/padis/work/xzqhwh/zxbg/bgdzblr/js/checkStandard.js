 /*
  @company digitalchina
  @author LIJL
  @date 2009-06-15
  У��������
*/
function checkBmgz(){
	//var flag=true;
	var sheng_dm = document.getElementById("SJ_XZQH_DM").value;
	var shi_dm = document.getElementById("DSJ_XZQH_DM").value;
	var xian_dm = document.getElementById("XJ_XZQH_DM").value;
	var xiang_dm = document.getElementById("XZJ_XZQH_DM").value;
	var cun_dm = document.getElementById("CJ_XZQH_DM").value;
	var zu_dm = document.getElementById("Z_XZQH_DM").value;
	var xzhq_mc = document.getElementById("MBXZQH_MC").value;
	var xzqh_dm = sheng_dm+shi_dm+xian_dm+xiang_dm+cun_dm+zu_dm;
	var jc_dm = getJcdm(xzqh_dm);
	var keyWord="";
	if(jc_dm=="2"){
		if((Number(shi_dm)<=20&&Number(shi_dm)>=1)||(Number(shi_dm)<=70&&Number(shi_dm)>=51)){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			if(keyWord!="��"){
				if(confirm("01��20��51��70��ʾʡֱϽ��,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(shi_dm)<=50&&Number(shi_dm)>=21){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			var keyWord1 = xzhq_mc.substring(xzhq_mc.length-3,xzhq_mc.length);
			if(keyWord!="��"&&keyWord!="��"&&keyWord1!="������"){
				if(confirm("21-50��ʾ�����������ݡ��ˣ�,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(shi_dm)<=80&&Number(shi_dm)>=71){
			if(confirm("71��80 ��ʾ�˿ںͼƻ�����ϵͳר�ô���,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
				return false;
			}else{
				return true;
			}
		}
	}else if(jc_dm=="3"){
		if(Number(xian_dm)<=18&&Number(xian_dm)>=1){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			if(keyWord!="��"&&keyWord!="��"){
				if(confirm("01��18��ʾ��Ͻ�����������֯�ߡ��ˣ�Ͻ�ؼ���,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(xian_dm)<=70&&Number(xian_dm)>=21){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			if(keyWord!="��"&&keyWord!="��"){
				if(confirm("21��70 ��ʾ�أ��죩,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(xian_dm)<=80&&Number(xian_dm)>=71){
			if(confirm("71��80��ʾ�˿ںͼƻ�����ϵͳר�ô���,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
				return false;
			}else{
				return true;
			}
		}else if(Number(xian_dm)<=99&&Number(xian_dm)>=81){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			if(keyWord!="��"){
				if(confirm("81��99��ʾʡֱϽ�ؼ���,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}
	}else if(jc_dm=="4"){
		if(Number(xiang_dm)<=99&&Number(xiang_dm)>=1){
			keyWord = xzhq_mc.substring(xzhq_mc.length-3,xzhq_mc.length);
			var keyWord1 = xzhq_mc.substring(xzhq_mc.length-3,xzhq_mc.length);
			if(xzhq_mc.indexOf("�ֵ�")<0&&xzhq_mc.indexOf("���´�")<0){
				if(confirm("001��099��ʾ�ֵ����������Ĵ���,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(xiang_dm)<=199&&Number(xiang_dm)>=100){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			if(keyWord!="��"){
				if(confirm("100��199��ʾ��Ĵ���,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(xiang_dm)<=399&&Number(xiang_dm)>=200){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			if(keyWord!="��"){
				if(confirm("200��399��ʾ��Ĵ���,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(xiang_dm)<=599&&Number(xiang_dm)>=400){
			keyWord = xzhq_mc.substring(xzhq_mc.length-2,xzhq_mc.length);
			if(keyWord!="��λ"&&xzhq_mc.indexOf("��˾")<0&&xzhq_mc.indexOf("����")<0){
				if(confirm("400��599��ʾ�����һ��λ�Ĵ���,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(xiang_dm)<=899&&Number(xiang_dm)>=700){
			if(confirm("700-899��ʾ�˿ںͼƻ�����ϵͳר�ô���,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
				return false;
			}else{
				return true;
			}
		}
	}else if(jc_dm=="5"){
		if(Number(cun_dm)<=199&&Number(cun_dm)>=1){
			keyWord = xzhq_mc.substring(xzhq_mc.length-5,xzhq_mc.length);
			if(keyWord!="����ίԱ��"&&xzhq_mc.indexOf("����")<0){
				if(confirm("001��199��ʾ����ίԱ����루��������,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(cun_dm)<=399&&Number(cun_dm)>=200){
			keyWord = xzhq_mc.substring(xzhq_mc.length-5,xzhq_mc.length);
			if(keyWord!="����ίԱ��"&&keyWord.indexOf("��")<0&&keyWord.indexOf("��ί��")<0){
				if(confirm("200��399��ʾ����ίԱ�����,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(cun_dm)<=899&&Number(cun_dm)>=800){
			if(confirm("800��899��ʾ�˿ںͼƻ�����ϵͳר�ô���,��������������������Ƿ���Ϲ��������������ȷ��ʹ�ô�������",0)==true){
				return false;
			}else{
				return true;
			}
		}
	}

	//return flag;
}

/**
 * 
 * <p>�������ƣ�getJcdm</p>
 * <p>���������������������������ȡ��Ӧ���δ���</p>
 * @param xzqh_dm ������������
 * @author lijl
 * @since 2009-7-10
 */
function getJcdm(xzqh_dm){
	if(xzqh_dm==null||xzqh_dm==""||xzqh_dm.length!=15){
		return "";
	}
	var jcdm="";
	if(xzqh_dm.substring(0, 2)=="00"){
		return "0";
	}else if(xzqh_dm.substring(2, 4)=="00"){
		jcdm = "1";
	}else if(xzqh_dm.substring(4, 6)=="00"){
		jcdm = "2";
	}else if(xzqh_dm.substring(6, 9)=="000"){
		jcdm = "3";
	}else if(xzqh_dm.substring(9, 12)=="000"){
		jcdm = "4";
	}else if(xzqh_dm.substring(12, 15)=="000"){
		jcdm = "5";
	}else{
		jcdm = "6";
	}
	return jcdm;
}
