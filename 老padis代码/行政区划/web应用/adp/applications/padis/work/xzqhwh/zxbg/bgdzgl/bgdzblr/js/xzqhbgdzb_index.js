/**
 * 
 * <p>方法名称：init</p>
 * <p>方法描述：初始化方法</p>
 * @param jc_dm 级次代码
 * @param sqdztdm 申请单状态
 * @param czry_dm
 * @author lijl
 * @since 2009-7-10
 */
function init(jc_dm,sqdztdm,czry_dm){
	showMessage("请确保已成功添加变更对照表，且对照表状态为“未提交”或者“审核不通过”。",0);
	document.getElementById("checkDiv").style.display="";
	/*if(czry_dm!="00000000000"){
		document.getElementById("checkDiv").style.display="none";
	}else{
		document.getElementById("checkDiv").style.display="";
	}
	*/
	if(sqdztdm!="10"&&sqdztdm!="21"){
		if(sqdztdm==""){
			showMessage("系统没有找到对应的变更对照表，请在“建立变更对照表”模块添加一个本级的区划变更对照表！",0);
		}
		document.getElementById("div2").style.display="none";
	}
	if(jc_dm=="0"){
		document.getElementById("XZQHJC").value="XZQH_SHENG";
		document.getElementById("RINGFLAG").disabled=true;
	}
	else if(jc_dm=="1"){
		document.getElementById("XZQH_SHENG").disabled=true;
		document.getElementById("XZQHJC").value="XZQH_SHENG";
		document.getElementById("RINGFLAG").disabled=true;
	}
	else if(jc_dm=="2"){
		document.getElementById("XZQH_SHENG").disabled=true;
		document.getElementById("XZQH_SHI").disabled=true;
		document.getElementById("XZQHJC").value="XZQH_SHI";
		document.getElementById("RINGFLAG").disabled=true;
	}
	else if(jc_dm=="3"){
		document.getElementById("XZQH_SHENG").disabled=true;
		document.getElementById("XZQH_SHI").disabled=true;
		document.getElementById("XZQH_XIAN").disabled=true;
		document.getElementById("XZQHJC").value="XZQH_XIAN";
	}
	else if(jc_dm=="4"){
		document.getElementById("XZQH_SHENG").disabled=true;
		document.getElementById("XZQH_SHI").disabled=true;
		document.getElementById("XZQH_XIAN").disabled=true;
		document.getElementById("XZQH_XIANG").disabled=true;
		document.getElementById("XZQHJC").value="XZQH_XIANG";
	}
	else if(jc_dm=="5"){
		document.getElementById("XZQH_SHENG").disabled=true;
		document.getElementById("XZQH_SHI").disabled=true;
		document.getElementById("XZQH_XIAN").disabled=true;
		document.getElementById("XZQH_XIANG").disabled=true;
		document.getElementById("XZQH_CUN").disabled=true;
		document.getElementById("XZQHJC").value="XZQH_CUN";
	}
	else if(jc_dm=="6"){
		document.getElementById("XZQH_SHENG").disabled=true;
		document.getElementById("XZQH_SHI").disabled=true;
		document.getElementById("XZQH_XIAN").disabled=true;
		document.getElementById("XZQH_XIANG").disabled=true;
		document.getElementById("XZQH_CUN").disabled=true;
		document.getElementById("XZQH_ZU").disabled=true;
		document.getElementById("XZQHJC").value="XZQH_ZU";
	}
}

/**
 * 
 * <p>方法名称：checkJcdm</p>
 * <p>方法描述：将本级区划置为不可用</p>
 * @param bglx_dm 变更类型代码
 * @author lijl
 * @since 2009-7-10
 */
function checkJcdm(bglx_dm){
	var jc_dm = document.getElementById("JC_DM").value;
	if(bglx_dm=="11"){
		if(jc_dm=="1"){
			document.getElementById("XZQH_SHENG").disabled=false;
		}
		if(jc_dm=="2"){
			document.getElementById("XZQH_SHI").disabled=false;
		}else if(jc_dm=="3"){
			document.getElementById("XZQH_XIAN").disabled=false;
		}else if(jc_dm=="4"){
			document.getElementById("XZQH_XIANG").disabled=false;
		}else if(jc_dm=="5"){
			document.getElementById("XZQH_CUN").disabled=false;
		}else if(jc_dm=="6"){
			document.getElementById("XZQH_ZU").disabled=false;
		}
	}else{
		if(jc_dm=="1"){
			document.getElementById("XZQH_SHENG").disabled=true;
		}
		if(jc_dm=="2"){
			document.getElementById("XZQH_SHI").disabled=true;
		}else if(jc_dm=="3"){
			document.getElementById("XZQH_XIAN").disabled=true;
		}else if(jc_dm=="4"){
			document.getElementById("XZQH_XIANG").disabled=true;
		}else if(jc_dm=="5"){
			document.getElementById("XZQH_CUN").disabled=true;
		}else if(jc_dm=="6"){
			document.getElementById("XZQH_ZU").disabled=true;
		}
	}
}
 /*
  @company digitalchina
  @author lijl
  @param temp 根据temp判断给那个字段赋值
  @param obj 下拉框对象
  @date 2009-07-10
  为联动下拉框中的省级以下区划赋值
*/
function getXjXzqh(obj,temp){
	var fhjd = "";
	var gbjd = obj.id;
	/*户籍地*/
	if(gbjd=="XZQH_SHENG"){
	   fhjd = "XZQH_SHI";
	   document.getElementById("XZQH_XIAN").options.length=0; //清空以前的选项
	   document.getElementById("XZQH_XIANG").options.length=0;
	   document.getElementById("XZQH_CUN").options.length=0;
	   document.getElementById("XZQH_ZU").options.length=0;
	}
	if(gbjd=="XZQH_SHI"){
	   fhjd = "XZQH_XIAN";
	   document.getElementById("XZQH_XIANG").options.length=0;
	   document.getElementById("XZQH_CUN").options.length=0;
	   document.getElementById("XZQH_ZU").options.length=0;
	}
	if(gbjd=="XZQH_XIAN"){
	   fhjd = "XZQH_XIANG";
	   document.getElementById("XZQH_CUN").options.length=0;
	   document.getElementById("XZQH_ZU").options.length=0;
	}
	if(gbjd=="XZQH_XIANG"){
	   fhjd = "XZQH_CUN";
	   document.getElementById("XZQH_ZU").options.length=0;
	}
	if(gbjd=="XZQH_CUN"){
	   fhjd = "XZQH_ZU";
	}
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<SJXZQH>"+obj.value+"</SJXZQH><FHJD>"+fhjd+"</FHJD><DB>V_DM_XZQH_YLSJ</DB></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.common.XzqhService.getXjXzqh");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var code = service.getCode();  //返回结果的状态
	var message = service.getMessage();  //返回的信息
	if( code != 2000 ){
		showBizMsg(code,message);
		return ;
	}else{
		var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
		var shjxzqhDOC = doc.getElementsByTagName(fhjd)(0);
		var shjxzqhXML = shjxzqhDOC.xml;
		ControlService.setOptions(fhjd,shjxzqhXML,'-1');
	}
	
}

 /*
  @company digitalchina
  @author LIJL
  @param xzqhjc
  @param obj 下拉框ID
  @date 2009-06-15
  根据选中的区划不同获取区划名称和代码
*/
function getXzqhMcDm(xzqhjc,jb){
	var obj ;
	
	if(xzqhjc==""){
		obj=document.getElementById(document.getElementById("XZQHJC").value);
	}else{
		obj =document.getElementById(xzqhjc);
	}
	if(jb==""){
		jb=document.getElementById("JB_DM").value;
	}
	
	var xzqh_dm = obj.value;
	var xzqh_mc = "";
	for(i=0;i<obj.length;i++){
	   if(obj[i].selected==true){
		   xzqh_mc = obj[i].innerText;
		   var index = xzqh_mc.lastIndexOf(" ");
		   xzqh_mc = xzqh_mc.substring(0,index);
		   document.getElementById("YSXZQH_MC").value=xzqh_mc;
	   }
	}
	if(xzqhjc!=""){
		document.getElementById("XZQHJC").value=xzqhjc;
	}
	if(jb!=""){
		document.getElementById("JB_DM").value=jb;
	}
	if(document.getElementById("SQDZT_DM").value!="10"&&document.getElementById("SQDZT_DM").value!="21"){
		document.getElementById("div2").style.display="none";
	}else{
		var objSelect = document.getElementById("BGLX_DM");
		var ringFlag = document.getElementById("RINGFLAG");
		if(jb=="0"||jb=="1"){		
			if(objSelect.options.length>2){
				objSelect.options[2] = null; 
				objSelect.options[3] = null; 
				objSelect.options[4] = null;
				objSelect.options[2] = null;
			}else{
				objSelect.options[1].value="11";
				objSelect.options[1].text="新增";
				
			}
			document.getElementById("image1").style.display="none";
			ringFlag.checked=false;
			ringFlag.value="0";
			ringFlag.disabled=true;
		}else{
			ringFlag.disabled=false;
			if(!jsSelectIsExitItem(objSelect,"21")){    
				var varItem1 = new Option("变更", "21");      
				objSelect.options.add(varItem1);
			}
			if(ringFlag.value=="0"){
				if(!jsSelectIsExitItem(objSelect,"31")){
					var varItem2 = new Option("并入", "31");      
					objSelect.options.add(varItem2);
				}
				if(!jsSelectIsExitItem(objSelect,"41")){
					var varItem3 = new Option("迁移", "41");      
					objSelect.options.add(varItem3);
				}
			}
		}
	}
	document.getElementById("YSXZQH_DM").value=xzqh_dm;
	var bglx_dm = document.getElementById("BGLX_DM").value;
	if(bglx_dm!="11"&&bglx_dm!=""){
		document.getElementById("MBXZQH_MC").value=xzqh_mc;
	}else{
		document.getElementById("MBXZQH_MC").value="";
	}
	if(bglx_dm=="31"||bglx_dm=="41"){
		document.getElementById("MBXZQH_MC").disabled=true;
		document.getElementById("MBXZQH_MC").disabled=true;
	}else{
		document.getElementById("MBXZQH_MC").disabled=false;
	}
	if(bglx_dm=="11"||bglx_dm=="21"||bglx_dm=="31"){
		document.getElementById("SJ_XZQH_DM").value=xzqh_dm.substring(0,2);
		document.getElementById("DSJ_XZQH_DM").value=xzqh_dm.substring(2,4);
		document.getElementById("XJ_XZQH_DM").value=xzqh_dm.substring(4,6);
		document.getElementById("XZJ_XZQH_DM").value=xzqh_dm.substring(6,9);
		document.getElementById("CJ_XZQH_DM").value=xzqh_dm.substring(9,12);
		document.getElementById("Z_XZQH_DM").value=xzqh_dm.substring(12,15);
	}
	document.getElementById("QSJ_XZQH_DM").value=xzqh_dm.substring(0,2);
	document.getElementById("QDSJ_XZQH_DM").value=xzqh_dm.substring(2,4);
	document.getElementById("QXJ_XZQH_DM").value=xzqh_dm.substring(4,6);
	document.getElementById("QXZJ_XZQH_DM").value=xzqh_dm.substring(6,9);
	document.getElementById("QCJ_XZQH_DM").value=xzqh_dm.substring(9,12);
	document.getElementById("QZ_XZQH_DM").value=xzqh_dm.substring(12,15);
	
	if(bglx_dm=="11"&&jb=="1"){
		document.getElementById("SJ_XZQH_DM").disabled=true;
		document.getElementById("DSJ_XZQH_DM").disabled=false;
		document.getElementById("XJ_XZQH_DM").disabled=true;
		document.getElementById("XZJ_XZQH_DM").disabled=true;
		document.getElementById("CJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").disabled=true;
		document.getElementById("DSJ_XZQH_DM").value="00";
		document.getElementById("XJ_XZQH_DM").value="00";
		document.getElementById("XZJ_XZQH_DM").value="000";
		document.getElementById("CJ_XZQH_DM").value="000";
		document.getElementById("Z_XZQH_DM").value="000";
	}
	if(bglx_dm=="11"&&jb=="2"){
		document.getElementById("SJ_XZQH_DM").disabled=true;
		document.getElementById("DSJ_XZQH_DM").disabled=true;
		document.getElementById("XJ_XZQH_DM").disabled=false;
		document.getElementById("XZJ_XZQH_DM").disabled=true;
		document.getElementById("CJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").disabled=true;
		document.getElementById("XJ_XZQH_DM").value="00";
		document.getElementById("XZJ_XZQH_DM").value="000";
		document.getElementById("CJ_XZQH_DM").value="000";
		document.getElementById("Z_XZQH_DM").value="000";
	}
	
	if(bglx_dm=="11"&&jb=="3"){
		document.getElementById("SJ_XZQH_DM").disabled=true;
		document.getElementById("XZJ_XZQH_DM").disabled=false;
		document.getElementById("DSJ_XZQH_DM").disabled=true;
		document.getElementById("XJ_XZQH_DM").disabled=true;
		document.getElementById("CJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").disabled=true;
		document.getElementById("XZJ_XZQH_DM").value="000";
		document.getElementById("CJ_XZQH_DM").value="000";
		document.getElementById("Z_XZQH_DM").value="000";
	}

	if(bglx_dm=="11"&&jb=="4"){
		document.getElementById("SJ_XZQH_DM").disabled=true;
		document.getElementById("CJ_XZQH_DM").disabled=false;
		document.getElementById("DSJ_XZQH_DM").disabled=true;
		document.getElementById("XZJ_XZQH_DM").disabled=true;
		document.getElementById("XJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").disabled=true;
		document.getElementById("CJ_XZQH_DM").value="000";
		document.getElementById("Z_XZQH_DM").value="000";
	}
	
	if(bglx_dm=="11"&&jb=="5"){
		document.getElementById("SJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").disabled=false;
		document.getElementById("DSJ_XZQH_DM").disabled=true;
		document.getElementById("XZJ_XZQH_DM").disabled=true;
		document.getElementById("CJ_XZQH_DM").disabled=true;
		document.getElementById("XJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").value="000";
	}
	if(bglx_dm=="11"&&jb=="6"){
		document.getElementById("SJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").disabled=true;
		document.getElementById("DSJ_XZQH_DM").disabled=true;
		document.getElementById("XZJ_XZQH_DM").disabled=true;
		document.getElementById("CJ_XZQH_DM").disabled=true;
		document.getElementById("XJ_XZQH_DM").disabled=true;
	}
	if(bglx_dm=="21"){
		if(document.getElementById("JB_DM").value==document.getElementById("JC_DM").value){
			document.getElementById("SJ_XZQH_DM").disabled=true;
			document.getElementById("DSJ_XZQH_DM").disabled=true;
			document.getElementById("XJ_XZQH_DM").disabled=true;
			document.getElementById("XZJ_XZQH_DM").disabled=true;
			document.getElementById("CJ_XZQH_DM").disabled=true;
			document.getElementById("Z_XZQH_DM").disabled=true;
			document.getElementById("MBXZQH_MC").disabled=true;
		}else{
			if(jb=="1"){
				document.getElementById("SJ_XZQH_DM").disabled=false;
				document.getElementById("DSJ_XZQH_DM").disabled=true;
				document.getElementById("XJ_XZQH_DM").disabled=true;
				document.getElementById("XZJ_XZQH_DM").disabled=true;
				document.getElementById("CJ_XZQH_DM").disabled=true;
				document.getElementById("Z_XZQH_DM").disabled=true;
			}
			if(jb=="2"){
				document.getElementById("SJ_XZQH_DM").disabled=true;
				document.getElementById("XJ_XZQH_DM").disabled=true;
				document.getElementById("DSJ_XZQH_DM").disabled=false;
				document.getElementById("XZJ_XZQH_DM").disabled=true;
				document.getElementById("CJ_XZQH_DM").disabled=true;
				document.getElementById("Z_XZQH_DM").disabled=true;
			}
			
			if(jb=="3"){
				document.getElementById("SJ_XZQH_DM").disabled=true;
				document.getElementById("XZJ_XZQH_DM").disabled=true;
				document.getElementById("DSJ_XZQH_DM").disabled=true;
				document.getElementById("XJ_XZQH_DM").disabled=false;
				document.getElementById("CJ_XZQH_DM").disabled=true;
				document.getElementById("Z_XZQH_DM").disabled=true;
			}

			if(jb=="4"){
				document.getElementById("SJ_XZQH_DM").disabled=true;
				document.getElementById("CJ_XZQH_DM").disabled=true;
				document.getElementById("DSJ_XZQH_DM").disabled=true;
				document.getElementById("XZJ_XZQH_DM").disabled=false;
				document.getElementById("XJ_XZQH_DM").disabled=true;
				document.getElementById("Z_XZQH_DM").disabled=true;
			}
			
			if(jb=="5"){
				document.getElementById("SJ_XZQH_DM").disabled=true;
				document.getElementById("Z_XZQH_DM").disabled=true;
				document.getElementById("DSJ_XZQH_DM").disabled=true;
				document.getElementById("XZJ_XZQH_DM").disabled=true;
				document.getElementById("CJ_XZQH_DM").disabled=false;
				document.getElementById("XJ_XZQH_DM").disabled=true;
			}		
			if(jb=="6"){
				document.getElementById("SJ_XZQH_DM").disabled=true;
				document.getElementById("Z_XZQH_DM").disabled=false;
				document.getElementById("DSJ_XZQH_DM").disabled=true;
				document.getElementById("XZJ_XZQH_DM").disabled=true;
				document.getElementById("CJ_XZQH_DM").disabled=true;
				document.getElementById("XJ_XZQH_DM").disabled=true;
			}
		}
	}
	if(bglx_dm=="41"&&jb=="1"){	
		document.getElementById("SJ_XZQH_DM").value="00";
		document.getElementById("DSJ_XZQH_DM").value="00";
		document.getElementById("XJ_XZQH_DM").value="00";
		document.getElementById("XZJ_XZQH_DM").value="000";
		document.getElementById("CJ_XZQH_DM").value="000";
		document.getElementById("Z_XZQH_DM").value="000";
	}
	if(bglx_dm=="41"&&jb=="2"){
		document.getElementById("SJ_XZQH_DM").value=document.getElementById("QSJ_XZQH_DM").value;
		document.getElementById("DSJ_XZQH_DM").value="00";
		document.getElementById("XJ_XZQH_DM").value="00";
		document.getElementById("XZJ_XZQH_DM").value="000";
		document.getElementById("CJ_XZQH_DM").value="000";
		document.getElementById("Z_XZQH_DM").value="000";
	}
	
	if(bglx_dm=="41"&&jb=="3"){
		document.getElementById("SJ_XZQH_DM").value=document.getElementById("QSJ_XZQH_DM").value;
		document.getElementById("DSJ_XZQH_DM").value=document.getElementById("QDSJ_XZQH_DM").value;
		document.getElementById("XJ_XZQH_DM").value="00";
		document.getElementById("XZJ_XZQH_DM").value="000";
		document.getElementById("CJ_XZQH_DM").value="000";
		document.getElementById("Z_XZQH_DM").value="000";
	}

	if(bglx_dm=="41"&&jb=="4"){
		document.getElementById("SJ_XZQH_DM").value=document.getElementById("QSJ_XZQH_DM").value;
		document.getElementById("DSJ_XZQH_DM").value=document.getElementById("QDSJ_XZQH_DM").value;
		document.getElementById("XJ_XZQH_DM").value=document.getElementById("QXJ_XZQH_DM").value;
		document.getElementById("XZJ_XZQH_DM").value="000";
		document.getElementById("CJ_XZQH_DM").value="000";
		document.getElementById("Z_XZQH_DM").value="000";
	}
	
	if(bglx_dm=="41"&&jb=="5"){
		document.getElementById("SJ_XZQH_DM").value=document.getElementById("QSJ_XZQH_DM").value;
		document.getElementById("DSJ_XZQH_DM").value=document.getElementById("QDSJ_XZQH_DM").value;
		document.getElementById("XJ_XZQH_DM").value=document.getElementById("QXJ_XZQH_DM").value;
		document.getElementById("XZJ_XZQH_DM").value=document.getElementById("QXZJ_XZQH_DM").value;
		document.getElementById("CJ_XZQH_DM").value="000";
		document.getElementById("Z_XZQH_DM").value="000";
	}

	if(bglx_dm=="41"&&jb=="6"){
		document.getElementById("SJ_XZQH_DM").value=document.getElementById("QSJ_XZQH_DM").value;
		document.getElementById("DSJ_XZQH_DM").value=document.getElementById("QDSJ_XZQH_DM").value;
		document.getElementById("XJ_XZQH_DM").value=document.getElementById("QXJ_XZQH_DM").value;
		document.getElementById("XZJ_XZQH_DM").value=document.getElementById("QXZJ_XZQH_DM").value;
		document.getElementById("CJ_XZQH_DM").value=document.getElementById("QCJ_XZQH_DM").value;
		document.getElementById("Z_XZQH_DM").value="000";
	}

	if(bglx_dm=="41"){
		document.getElementById("SJ_XZQH_DM").disabled=true;
		document.getElementById("DSJ_XZQH_DM").disabled=true;
		document.getElementById("XJ_XZQH_DM").disabled=true;
		document.getElementById("XZJ_XZQH_DM").disabled=true;
		document.getElementById("CJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").disabled=true;
		if((jb-document.getElementById("JC_DM").value)>=2) {
			document.getElementById("image1").style.display="";
		}else{
			document.getElementById("image1").style.display="none";
		}
	}

	/*if(bglx_dm=="41"&&jb=="2"){
		document.getElementById("SJ_XZQH_DM").disabled=true;
		document.getElementById("XJ_XZQH_DM").disabled=true;
		document.getElementById("DSJ_XZQH_DM").disabled=false;
		document.getElementById("XZJ_XZQH_DM").disabled=true;
		document.getElementById("CJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").disabled=true;
	}
	
	if(bglx_dm=="41"&&jb=="3"){
		document.getElementById("SJ_XZQH_DM").disabled=true;
		document.getElementById("XZJ_XZQH_DM").disabled=true;
		document.getElementById("DSJ_XZQH_DM").disabled=true;
		document.getElementById("XJ_XZQH_DM").disabled=false;
		document.getElementById("CJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").disabled=true;
	}

	if(bglx_dm=="41"&&jb=="4"){
		document.getElementById("SJ_XZQH_DM").disabled=true;
		document.getElementById("CJ_XZQH_DM").disabled=true;
		document.getElementById("DSJ_XZQH_DM").disabled=true;
		document.getElementById("XZJ_XZQH_DM").disabled=false;
		document.getElementById("XJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").disabled=true;
	}
	
	if(bglx_dm=="41"&&jb=="5"){
		document.getElementById("SJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").disabled=true;
		document.getElementById("DSJ_XZQH_DM").disabled=true;
		document.getElementById("XZJ_XZQH_DM").disabled=true;
		document.getElementById("CJ_XZQH_DM").disabled=false;
		document.getElementById("XJ_XZQH_DM").disabled=true;
	}
	
	if(bglx_dm=="41"&&jb=="6"){
		document.getElementById("SJ_XZQH_DM").disabled=true;
		document.getElementById("Z_XZQH_DM").disabled=false;
		document.getElementById("DSJ_XZQH_DM").disabled=true;
		document.getElementById("XZJ_XZQH_DM").disabled=true;
		document.getElementById("CJ_XZQH_DM").disabled=true;
		document.getElementById("XJ_XZQH_DM").disabled=true;
	}*/
}

/**
 * 
 * <p>方法名称：addGrid</p>
 * <p>方法描述：增加清单</p>
 *
 * @author lijl
 * @since 2009-7-10
 */
function addGrid(){
	var czry_dm = document.getElementById("CZRY_DM").value;
	var bgq_xzqh_mc = document.getElementById("YSXZQH_MC").value;
	var jb_dm = document.getElementById("JB_DM").value;
	var bgh_xzqh_mc = document.getElementById("MBXZQH_MC").value;
	var sj_xzqh_dm = document.getElementById("SJ_XZQH_DM").value;
	var dsj_xzqh_dm = document.getElementById("DSJ_XZQH_DM").value;
	var xj_xzqh_dm = document.getElementById("XJ_XZQH_DM").value;
	var xzj_xzqh_dm = document.getElementById("XZJ_XZQH_DM").value;
	var cj_xzqh_dm = document.getElementById("CJ_XZQH_DM").value;
	var z_xzqh_dm = document.getElementById("Z_XZQH_DM").value;
	var qsj_xzqh_dm = document.getElementById("QSJ_XZQH_DM").value;
	var qdsj_xzqh_dm = document.getElementById("QDSJ_XZQH_DM").value;
	var qxj_xzqh_dm = document.getElementById("QXJ_XZQH_DM").value;
	var qxzj_xzqh_dm = document.getElementById("QXZJ_XZQH_DM").value;
	var qcj_xzqh_dm = document.getElementById("QCJ_XZQH_DM").value;
	var qz_xzqh_dm = document.getElementById("QZ_XZQH_DM").value;
	var bz = document.getElementById("BZ").value;
	var bglx_dm = document.getElementById("BGLX_DM").value;
	var bgh_xzqh_dm  = sj_xzqh_dm+dsj_xzqh_dm+xj_xzqh_dm+xzj_xzqh_dm+cj_xzqh_dm+z_xzqh_dm;
	var bgq_xzqh_dm  = qsj_xzqh_dm+qdsj_xzqh_dm+qxj_xzqh_dm+qxzj_xzqh_dm+qcj_xzqh_dm+qz_xzqh_dm;
	
	if(bglx_dm==""){
		showMessage("调整类型不能为空！",0);
		return false;
	}
	
	if(!checkZxqh()){
		return;
	}
	if(bgq_xzqh_dm==""){
		showMessage("原区划代码不能为空！",0);
		return false;
	}
	if(bgq_xzqh_mc==""){
		showMessage("原区划名称不能为空！",0);
		return false;
	}
	if(bgh_xzqh_dm==""){
		showMessage("现区划代码不能为空！",0);
		return false;
	}
	if(bgh_xzqh_mc==""){
		showMessage("现区划名称不能为空！",0);
		return false;
	}else{
		if(bgh_xzqh_mc.indexOf("<")>-1||bgh_xzqh_mc.indexOf(">")>-1||bgh_xzqh_mc.indexOf("&")>-1){
			showMessage("现区划名称含有特殊字符，请重新命名！",0);
			return false;
		}
	}
	if(bglx_dm=="31"){
		if(bgq_xzqh_dm==bgh_xzqh_dm){
			showMessage("请选择要并入的区划！",0);
			return false;
		}
		var count = hasChildXzqh(bgq_xzqh_dm);
		if(count!="0"){
			var existRows = new Array();
			var newRows=0;
			var addCount=0;
			existRows = xzqhGrid.getArrayData();
			for(var j=0;j<existRows.length;j++){
				var sj_dm = getSjxzqhdm(existRows[j][0]);
				if(sj_dm==bgq_xzqh_dm&&existRows[j][6]=="41"){
					newRows++;
				}
				if(getSjxzqhdm(existRows[j][3])==bgq_xzqh_dm&&existRows[j][6]=="11"){
					addCount++;
				}
			}
			if(newRows!=(Number(count)+Number(addCount))){
				window.open("selectXjXzqhSqb.jsp?XZQHBGLX_DM=31&JB_DM="+jb_dm,"并入后的区划代码表","height=450px,width=600px,top=100,left=250,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=no,resizable=no");
				return;
			}
		}

		if(isExistsXzqh(bgh_xzqh_dm)=="校验通过"){
			var flag=false;
			var existRows = new Array();	
			existRows = xzqhGrid.getArrayData();
			for(var j=0;j<existRows.length;j++){
				if(existRows[j][3]==bgh_xzqh_dm&&existRows[j][6]=="11"){					
					flag=true;
					break;
				}		
			}
			if(!flag){
				showMessage("现区划代码不存在，请重新填写！",0);
				return false;
			}
		}
	}else{
		if(bglx_dm=="11"){
			if(isExistsXzqh(bgh_xzqh_dm)!="校验通过"){
				showMessage("现区划代码已存在，请重新填写！",0);
				return false;
			}else if(isExistsYjyXzqh(bgh_xzqh_dm)=="true"&&getJbdm(bgh_xzqh_dm).length<12&&czry_dm!="00000000000"){
				showMessage("现区划代码曾经被使用过，已禁用，请重新填写！",0);
				return false;
			}else{
				var flag=false;
				var existRows = new Array();	
				existRows = xzqhGrid.getArrayData();
				for(var j=0;j<existRows.length;j++){
					if(existRows[j][3]==bgh_xzqh_dm){					
						flag=true;
						break;
					}		
				}		
				if(flag){
					showMessage("现区划代码已存在，请重新填写！",0);
					return false;
				}
			}
		}else if(bglx_dm=="21"){
			var ringFlag = document.getElementById("RINGFLAG");

			if(bgq_xzqh_dm==bgh_xzqh_dm&&bgq_xzqh_mc==bgh_xzqh_mc){
				showMessage("原区划代码名称与现区划代码名称完全一样，没有变化！",0);
				return false;
			}
			var len = getJbdm(bgq_xzqh_dm).length-getJbdm(bgh_xzqh_dm).length;
			if(len!=0){
				showMessage("不能输入"+len+"个0！",0);
				return false;
			}
			var existRows = new Array();	
			existRows = xzqhGrid.getArrayData();
			if(ringFlag.checked){
				ringFlag.value="1";
			}else{
				if(isExistsXzqh(bgh_xzqh_dm)!="校验通过"){
					if(bgq_xzqh_mc==bgh_xzqh_mc){
						showMessage("现区划代码已存在，请重新填写！",0);
						return false;
					}
				}else if(isExistsYjyXzqh(bgh_xzqh_dm)=="true"&&getJbdm(bgh_xzqh_dm).length<12&&czry_dm!="00000000000"){
					showMessage("现区划代码曾经被使用过，已禁用，请重新填写！",0);
					return false;
				}else{
					for(var j=0;j<existRows.length;j++){
						if(existRows[j][3]==bgh_xzqh_dm){					
							showMessage("现区划代码在本次变更对照表中已被占用，请重新填写！",0);
							return false;
						}		
					}
				}
			}
		}else if(bglx_dm=="41"){
			if(isExistsXzqh(bgh_xzqh_dm)!="校验通过"){
				showMessage("现区划代码已存在，请重新填写！",0);
				return false;
			}
		}
	}
	if(bz.indexOf("<")>-1||bz.indexOf(">")>-1||bz.indexOf("&")>-1){
		showMessage("备注含有特殊字符，请重新命名！",0);
		return false;
	}
	if(checkBmgz()){
		return false;
	}
	var xzqh_dm = bgq_xzqh_dm;
	if(bglx_dm=="11"){
		xzqh_dm = bgh_xzqh_dm;
	}
	if(!isExistsSjxzqh(xzqh_dm)){
		return ;
	}
	
	var xzqhs = new Array();
	var xzqh = new Array();
	pxh = pxh+1;
	if(bglx_dm=="11"){
		xzqh[0]="";
		xzqh[1]="";
	}else{
		xzqh[0]=bgq_xzqh_dm;
		xzqh[1]=bgq_xzqh_mc;
	}
	if(bglx_dm=="11"){
		xzqh[2]="新增";
	}else if(bglx_dm=="21"){
		xzqh[2]="变更";
	}else if(bglx_dm=="31"){
		xzqh[2]="并入";
	}else if(bglx_dm=="41"){
		xzqh[2]="迁移";
	}
	xzqh[3]=bgh_xzqh_dm;
	xzqh[4]=bgh_xzqh_mc;
	xzqh[5]=bz;
	xzqh[6]=bglx_dm;
	xzqhs[0]=xzqh;
	fillGrid(xzqhs);
}

/**
 *增加多行Grid表格.
 *@param  无
 *@return 无
*/
function fillGrid(xzqhs){
	
	var bglx_dm = document.getElementById("BGLX_DM").value;
	var existRows = new Array();	
	existRows = xzqhGrid.getArrayData();
	if(xzqhs.length>0){
		for(var i=0;i<xzqhs.length;i++){
			var newRows=0;
			for(var j=0;j<existRows.length;j++){
				if(existRows[j][0]!=""&&xzqhs[i][0]!=""){
					if(existRows[j][0]!=xzqhs[i][0]){
						newRows++;
					}else{
						showMessage("原区划代码“"+existRows[j][0]+"”已在第"+(j+1)+"行被改变，不能重复操作！",0);
						pxh--;
						break;
					}
				}else{
					newRows++;
				}
			}
			if(newRows==existRows.length){
				xzqhGrid.addRow(xzqhs[i]);
			}			
		}
		for(var i=0;i<xzqhGrid.rows.length;i++)
		{
			xzqhGrid.rows[i].setChecked(true);
		}
	}
}

/**
 *查看上级区划是否存在
 *@param  无
 *@return 无
*/
function isExistsSjxzqh(xzqh_dm){
	var flag=true;
	var xzqhjb_dm = getJbdm(xzqh_dm);
	var existRows = new Array();	
	existRows = xzqhGrid.getArrayData();
	if(existRows.length>0){
		for(var i=0;i<existRows.length;i++){
			if(existRows[i][0]!=""){
				var xzqhdm = getJbdm(existRows[i][0]);
				if(xzqhjb_dm.indexOf(xzqhdm)>-1&&xzqhjb_dm!=xzqhdm){
					showMessage("区划代码“"+xzqh_dm+"”上级区划代码在第"+(i+1)+"行已被变更，不能再对下级区划进行操作！",0);
					flag = false;
					break;
				}
			}
		}
	}
	return flag;
}

/**
 *获取区划名称
 *@param  无
 *@return 字符串
*/
function getXzqhMc(){
	var bglx_dm = document.getElementById("BGLX_DM").value;
	var sj_xzqh_dm = document.getElementById("SJ_XZQH_DM").value;
	var dsj_xzqh_dm = document.getElementById("DSJ_XZQH_DM").value;
	var xj_xzqh_dm = document.getElementById("XJ_XZQH_DM").value;
	var xzj_xzqh_dm = document.getElementById("XZJ_XZQH_DM").value;
	var cj_xzqh_dm = document.getElementById("CJ_XZQH_DM").value;
	var z_xzqh_dm = document.getElementById("Z_XZQH_DM").value;
	var bgh_xzqh_dm  = sj_xzqh_dm+dsj_xzqh_dm+xj_xzqh_dm+xzj_xzqh_dm+cj_xzqh_dm+z_xzqh_dm;
	var xzqh_mc = isExistsXzqh(bgh_xzqh_dm);
	if(bglx_dm=="31"){
		if(xzqh_mc!="校验通过"){
			document.getElementById("MBXZQH_MC").value = xzqh_mc;
		}
	}
}
/**
 *查看区划是否存在
 *@param  无
 *@return 字符串
*/
function isExistsXzqh(xzqh_dm){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<XZQH_DM>"+xzqh_dm+"</XZQH_DM></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.bgdzblr.BgdzblrService.isExistsXzqh");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var flag = doc.selectSingleNode("ROOT/Result/MAP/FLAG").text;
		return flag;
	}
}

/**
 *是否存在已禁用的区划代码
 *@param  无
 *@return 字符串
*/
function isExistsYjyXzqh(xzqh_dm){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<XZQH_DM>"+xzqh_dm+"</XZQH_DM></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.bgdzblr.BgdzblrService.isExistsYjyXzqh");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var flag = doc.selectSingleNode("ROOT/Result/MAP/FLAG").text;
		return flag;
	}
}

/**
 *是否显示并入或迁移的小图标
 *@param  无
 *@return 字符串
*/
function xstp(){
	var xjc_dm = document.getElementById("JB_DM").value;
	var init_jc_dm = document.getElementById("JC_DM").value;
	var bglx_dm = document.getElementById("BGLX_DM").value;
	
	if(bglx_dm=="41"||bglx_dm=="31"){
		if(bglx_dm=="31"){
			document.getElementById("SJ_XZQH_DM").disabled=true;
			document.getElementById("DSJ_XZQH_DM").disabled=true;
			document.getElementById("XJ_XZQH_DM").disabled=true;
			document.getElementById("XZJ_XZQH_DM").disabled=true;
			document.getElementById("CJ_XZQH_DM").disabled=true;
			document.getElementById("Z_XZQH_DM").disabled=true;
			if(xjc_dm==init_jc_dm){
				document.getElementById("image1").style.display="none";
			}else{
				document.getElementById("image1").style.display="";
			}
		}
		if(bglx_dm=="41"){
			if((xjc_dm-init_jc_dm)>=2) {
				document.getElementById("image1").style.display="";
			}
		}
	}else{
		document.getElementById("image1").style.display="none";
	}
	checkJcdm(bglx_dm);
	
}

/**
 *弹出并入或者迁移的区划列表
 *@param  无
 *@return 字符串
*/
function selectSjxzqh(){
	var bglx_dm = document.getElementById("BGLX_DM").value;
	var qsj_xzqh_dm = document.getElementById("QSJ_XZQH_DM").value;
	var qdsj_xzqh_dm = document.getElementById("QDSJ_XZQH_DM").value;
	var qxj_xzqh_dm = document.getElementById("QXJ_XZQH_DM").value;
	var qxzj_xzqh_dm = document.getElementById("QXZJ_XZQH_DM").value;
	var qcj_xzqh_dm = document.getElementById("QCJ_XZQH_DM").value;
	var qz_xzqh_dm = document.getElementById("QZ_XZQH_DM").value;
	var jb_dm = document.getElementById("JB_DM").value;
	var bgq_xzqh_dm  = qsj_xzqh_dm+qdsj_xzqh_dm+qxj_xzqh_dm+qxzj_xzqh_dm+qcj_xzqh_dm+qz_xzqh_dm;
	if(bgq_xzqh_dm.length==0){
		showMessage("请选择目标区划！",0);
		return false;
	}
	if(bglx_dm=="31"){
		window.open("selectTjXzqhSqb.jsp?XZQHBGLX_DM=31&JB_DM="+jb_dm,"同级区划代码表","height=450px,width=450px,top=100,left=250,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=no,resizable=no");
	} else if(bglx_dm=="41"){
		window.open("selectSjXzqhSqb.jsp?XZQHBGLX_DM=41&JB_DM="+jb_dm,"上级区划代码表","height=500,width=500px,top=100,left=250,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=no,resizable=no");
	}
}

/**
 *得到上级区划代码
 *@param  xzqh_dm 区划代码
 *@return 字符串
*/
function getSjxzqhdm(xzqh_dm){
	if(xzqh_dm==null||xzqh_dm==""||xzqh_dm.length!=15){
		return "";
	}
	var sjxzqhdm="";
	if(xzqh_dm.substring(0, 2)=="00"){
		return "";
	}else if(xzqh_dm.substring(2, 4)=="00"){
		sjxzqhdm = "000000000000000";
	}else if(xzqh_dm.substring(4, 6)=="00"){
		sjxzqhdm = xzqh_dm.substring(0, 2)+"0000000000000";
	}else if(xzqh_dm.substring(6, 9)=="000"){
		sjxzqhdm = xzqh_dm.substring(0, 4)+"00000000000";
	}else if(xzqh_dm.substring(9, 12)=="000"){
		sjxzqhdm = xzqh_dm.substring(0, 6)+"000000000";
	}else if(xzqh_dm.substring(12, 15)=="000"){
		sjxzqhdm = xzqh_dm.substring(0, 9)+"000000";
	}else{
		sjxzqhdm = xzqh_dm.substring(0, 12)+"000";
	}
	return sjxzqhdm;
}

/**
 *得到下级区划代码
 *@param  xzqh_dm 区划代码
 *@return 字符串
*/
function hasChildXzqh(xzqh_dm){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<XZQH_DM>"+xzqh_dm+"</XZQH_DM></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.bgdzblr.BgdzblrService.hasChildXzqh");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var count = doc.selectSingleNode("ROOT/Result/MAP/FLAG").text;
		return count;
	}
}


/**
 * 
 * <p>方法名称：getJbdm</p>
 * <p>方法描述：根据区划代码获取相应级别代码</p>
 * @param xzqh_dm 区划代码
 * @author lijl
 * @since 2009-7-10
 */
function getJbdm(xzqh_dm){
	if(xzqh_dm==null||xzqh_dm==""||xzqh_dm.length!=15){
		return "";
	}
	var jbdm="";
	if(xzqh_dm.substring(0, 2)=="00"){
		return "";
	}else if(xzqh_dm.substring(2, 4)=="00"){
		jbdm = xzqh_dm.substring(0, 2);
	}else if(xzqh_dm.substring(4, 6)=="00"){
		jbdm = xzqh_dm.substring(0, 4);
	}else if(xzqh_dm.substring(6, 9)=="000"){
		jbdm = xzqh_dm.substring(0, 6);
	}else if(xzqh_dm.substring(9, 12)=="000"){
		jbdm = xzqh_dm.substring(0, 9);
	}else if(xzqh_dm.substring(12, 15)=="000"){
		jbdm = xzqh_dm.substring(0, 12);
	}else{
		jbdm = xzqh_dm;
	}
	return jbdm;
}

/**
 * 
 * <p>方法名称：changeSelect</p>
 * <p>方法描述：控制省市两级的操作权限，只能新增</p>
 *
 * @author lijl
 * @since 2009-7-10
 */
function changeSelect(){
	var ringFlag = document.getElementById("RINGFLAG");
	var html = "";
	if(ringFlag.checked){
		ringFlag.value="1";
		html = html+"<select name=\"BGLX_DM\" id=\"BGLX_DM\" style=\"width:100%\"  onchange=\"getXzqhMcDm('','')\">";
		html = html+"<option value=\"\">=请选择类型=</option><option value=\"21\">变更</option></select>";
		document.getElementById("msgTd").innerHTML = "<font color=\"red\">只能录入一个环状或者链状数据</font>";
	}else{
		ringFlag.value="0";
		html = html+"调整类型<font color=\"red\">*</font>";
		html = html+"<select name=\"BGLX_DM\" id=\"BGLX_DM\" onclick=\"xstp()\" style=\"width:78%\"";
		html = html+" onchange=\"getXzqhMcDm('','')\"><option value=\"\">=请选择类型=</option>";
		html = html+"<option value=\"11\">新增</option><option value=\"21\">变更</option>";
		html = html+"<option value=\"31\">并入</option><option value=\"41\">迁移</option>";
		html = html+"</select>";
		document.getElementById("msgTd").innerHTML ="";
	}
	document.getElementById("selectTd").innerHTML=html;
}

/**
 * 
 * <p>方法名称：jsSelectIsExitItem</p>
 * <p>方法描述：是否已存在下拉框中</p>
 * @param xzqh_dm 区划代码
 * @author lijl
 * @since 2009-7-10
 */
function jsSelectIsExitItem(objSelect, objItemValue) {
 var isExit = false;
 for (var i = 0; i < objSelect.options.length; i++) {
	 if (objSelect.options[i].value == objItemValue) {
		 isExit = true;
		 break;
	 }
 }
 return isExit;
}