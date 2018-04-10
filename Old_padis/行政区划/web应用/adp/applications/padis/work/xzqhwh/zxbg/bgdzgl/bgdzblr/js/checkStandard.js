 /*
  @company digitalchina
  @author LIJL
  @date 2009-06-15
  校验编码规则
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
			if(keyWord!="市"){
				if(confirm("01－20，51－70表示省直辖市,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(shi_dm)<=50&&Number(shi_dm)>=21){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			var keyWord1 = xzhq_mc.substring(xzhq_mc.length-3,xzhq_mc.length);
			if(keyWord!="市"&&keyWord!="盟"&&keyWord1!="自治州"){
				if(confirm("21-50表示地区（自治州、盟）,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(shi_dm)<=80&&Number(shi_dm)>=71){
			if(confirm("71－80 表示人口和计划生育系统专用代码,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
				return false;
			}else{
				return true;
			}
		}
	}else if(jc_dm=="3"){
		if(Number(xian_dm)<=18&&Number(xian_dm)>=1){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			if(keyWord!="区"&&keyWord!="市"){
				if(confirm("01－18表示市辖区或地区（组织者、盟）辖县级市,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(xian_dm)<=70&&Number(xian_dm)>=21){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			if(keyWord!="县"&&keyWord!="旗"){
				if(confirm("21－70 表示县（旗）,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(xian_dm)<=80&&Number(xian_dm)>=71){
			if(confirm("71－80表示人口和计划生育系统专用代码,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
				return false;
			}else{
				return true;
			}
		}else if(Number(xian_dm)<=99&&Number(xian_dm)>=81){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			if(keyWord!="市"){
				if(confirm("81－99表示省直辖县级市,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
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
			if(xzhq_mc.indexOf("街道")<0&&xzhq_mc.indexOf("办事处")<0){
				if(confirm("001－099表示街道（地区）的代码,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(xiang_dm)<=199&&Number(xiang_dm)>=100){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			if(keyWord!="镇"){
				if(confirm("100－199表示镇的代码,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(xiang_dm)<=399&&Number(xiang_dm)>=200){
			keyWord = xzhq_mc.substring(xzhq_mc.length-1,xzhq_mc.length);
			if(keyWord!="乡"){
				if(confirm("200－399表示乡的代码,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(xiang_dm)<=599&&Number(xiang_dm)>=400){
			keyWord = xzhq_mc.substring(xzhq_mc.length-2,xzhq_mc.length);
			if(keyWord!="单位"&&xzhq_mc.indexOf("公司")<0&&xzhq_mc.indexOf("集团")<0){
				if(confirm("400－599表示政企合一单位的代码,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(xiang_dm)<=899&&Number(xiang_dm)>=700){
			if(confirm("700-899表示人口和计划生育系统专用代码,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
				return false;
			}else{
				return true;
			}
		}
	}else if(jc_dm=="5"){
		if(Number(cun_dm)<=199&&Number(cun_dm)>=1){
			keyWord = xzhq_mc.substring(xzhq_mc.length-5,xzhq_mc.length);
			if(keyWord!="居民委员会"&&xzhq_mc.indexOf("社区")<0){
				if(confirm("001－199表示居民委员会代码（含社区）,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(cun_dm)<=399&&Number(cun_dm)>=200){
			keyWord = xzhq_mc.substring(xzhq_mc.length-5,xzhq_mc.length);
			if(keyWord!="村民委员会"&&keyWord.indexOf("村")<0&&keyWord.indexOf("村委会")<0){
				if(confirm("200－399表示村民委员会代码,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
					return false;
				}else{
					return true;
				}
			}
		}else if(Number(cun_dm)<=899&&Number(cun_dm)>=800){
			if(confirm("800—899表示人口和计划生育系统专用代码,请检查您输入的区划名称是否符合国家区划编码规则，确定使用此名称吗？",0)==true){
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
 * <p>方法名称：getJcdm</p>
 * <p>方法描述：根据行政区划代码获取相应级次代码</p>
 * @param xzqh_dm 行政区划代码
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
