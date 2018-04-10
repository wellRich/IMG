/**
*注册事件
*/
addLoadListener(initEvents);
var xzqhGrid;
var pxh=0;
/**
*初始化事件列表
*/
function initEvents(){
	newGrid();
}

/**
*创建新表格
*@param {String} event 事件参数
*/
function newGrid(event){
	if (typeof event == "undefined"){
		event = window.event;
	}
	/////////////////////////////////////////////////
	//目录节点信息表格
	xzqhGrid = new PadisGrid("XZQH","xzqh");
	//xzqhGrid.showCheckbox=false;//不显示复选框
	/*var col=new PadisCol("序号","PXH");
	col.controlType=2;
	col.width="30px";
	xzqhGrid.cols.push(col);*/

	var col=new PadisCol("原区划代码","YSXZQH_DM");
	col.controlType=2;
	col.width="110px";
	xzqhGrid.cols.push(col);
	
	col=new PadisCol("原区划名称","YSXZQH_MC");
	col.controlType=2;
	col.textAlign="left";
	xzqhGrid.cols.push(col);

	col=new PadisCol("调整类型","BGLX_MC");
	col.controlType=2;
	col.width="60px";
	xzqhGrid.cols.push(col);
	
	col=new PadisCol("现区划代码","MBXZQH_DM");
	col.controlType=2;
	col.width="110px";
	xzqhGrid.cols.push(col);

	col=new PadisCol("现区划名称","MBXZQH_MC");
	col.controlType=2;
	col.textAlign="left";
	xzqhGrid.cols.push(col);

	col=new PadisCol("备注","BZ");
	col.controlType=2;
	col.textAlign="left";
	xzqhGrid.cols.push(col);

	col=new PadisCol("调整类型代码","BGLX_DM");
	col.controlType=3;
	col.width="30px";
	xzqhGrid.cols.push(col);

	//创建空表格
	xzqhGrid.create();
	//刷新表格
	xzqhGrid.refreshGrid();
	/////////////////////////////////////////////////
}
/**
 *响应“增加申请清单”按钮.
 *@param  无
 *@return 无
*/
function addXzqhsqb(){
	addGrid();
}

/**
 *响应“删除清单”按钮.
 *@param  无
 *@return 无
*/
function removeRow()
{
	if(typeof event=="undefined")
	{
		event=window.event;
	}
	xzqhGrid.removeRow();
	/*for(var i=0;i<xzqhGrid.rows.length;i++)
	{
		xzqhGrid.rows[i].getCellByColumn("PXH").setValue(i+1);
		xzqhGrid.rows[i].setChecked(true);
	}
	pxh =xzqhGrid.rows.length;
	*/
}

/**
 * 
 * <p>方法名称：checkZxqh</p>
 * <p>方法描述：检查输入框是否符合要求</p>
 *
 * @author lijl
 * @since 2009-7-10
 */
function checkZxqh(){
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
	
	var bgh_xzqh_dm  = sj_xzqh_dm+dsj_xzqh_dm+xj_xzqh_dm+xzj_xzqh_dm+cj_xzqh_dm+z_xzqh_dm;
	var bgq_xzqh_dm  = qsj_xzqh_dm+qdsj_xzqh_dm+qxj_xzqh_dm+qxzj_xzqh_dm+qcj_xzqh_dm+qz_xzqh_dm;
	

	if(sj_xzqh_dm.length!=2||qsj_xzqh_dm.length!=2){
		showMessage("省级区划代码长度为两位，请修改！",0);
		return false;
	}
	else if(dsj_xzqh_dm.length!=2||qdsj_xzqh_dm.length!=2){
		showMessage("市级区划代码长度为两位，请修改！",0);
		return false;
	}
	else if(xj_xzqh_dm.length!=2||qxj_xzqh_dm.length!=2){
		showMessage("县级区划代码长度为两位，请修改！",0);
		return false;
	}
	else if(xzj_xzqh_dm.length!=3||qxzj_xzqh_dm.length!=3){
		showMessage("乡镇级区划代码长度为三位，请修改！",0);
		return false;
	}
	else if(cj_xzqh_dm.length!=3||qcj_xzqh_dm.length!=3){
		showMessage("村级区划代码长度为三位，请修改！",0);
		return false;
	}
	else if(z_xzqh_dm.length!=3||qz_xzqh_dm.length!=3){
		showMessage("组区划代码长度为三位，请修改！",0);
		return false;
	}
	else{
		if(!isNumber(bgh_xzqh_dm)||!isNumber(bgq_xzqh_dm)){
			showMessage("只能输入数字！",0);
			return false;
		}else{
			return true;
		}
	}
}

/**
 * 
 * <p>方法名称：getSjxzqh</p>
 * <p>方法描述：获取上级区划</p>
 *
 * @author lijl
 * @since 2009-7-10
 */
function getSjxzqh(flag){
	var bglx_dm = document.getElementById("BGLX_DM").value;
	if(bglx_dm!="11"&&bglx_dm!="41"){
		if(flag=="1"){
			document.getElementById("SJ_XZQH_DM").value=document.getElementById("QSJ_XZQH_DM").value;
		}
		if(flag=="2"){
			document.getElementById("DSJ_XZQH_DM").value=document.getElementById("QDSJ_XZQH_DM").value;
		}
		if(flag=="3"){
			document.getElementById("XJ_XZQH_DM").value=document.getElementById("QXJ_XZQH_DM").value;
		}
		if(flag=="4"){
			document.getElementById("XZJ_XZQH_DM").value=document.getElementById("QXZJ_XZQH_DM").value;
		}
		if(flag=="5"){
			document.getElementById("CJ_XZQH_DM").value=document.getElementById("QCJ_XZQH_DM").value;
		}
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
 *保存数据
 *@param  无
 *@return 无
*/
function saveXzqh(){
	var ringFlag = document.getElementById("RINGFLAG").value;
	var czry_dm = document.getElementById("CZRY_DM").value;
	var sqbmc = document.getElementById("SQBMC").value;
	var sqdxh = document.getElementById("SQDXH").value;
	if(sqbmc.indexOf("<")>-1||sqbmc.indexOf(">")>-1||sqbmc.indexOf("&")>-1){
		showMessage("备注含有特殊字符，请重新命名！",0);
		return false;
	}
	//首先验证表单
	var existRows = xzqhGrid.getArrayData();
	if(existRows.length<=0){
		showMessage("请先添加数据！",0);
		return false;
	}
	for(var j=0;j<existRows.length;j++){
		if(existRows[j][6]!="21"&&ringFlag=="1"){
			showMessage("环状数据不能有除变更以外的类型！",0);
			return false;
		}else{
			if(czry_dm!="00000000000"){
				if(getJbdm(existRows[j][0]).length<12&&ringFlag=="1"){
					showMessage("只能录入村级环状数据！",0);
					return false;
				}
			}
		}
	}

	var otherXml = "<SQBMC>"+sqbmc+"</SQBMC><SQDXH>"+sqdxh+"</SQDXH><RINGFLAG>"+ringFlag+"</RINGFLAG>";
	var paramXml = xzqhGrid.getXmlData()+""+otherXml;	// 获取区划内容
	if(ringFlag=="1"){
		var service0 = new Service("com.padis.business.xzqhwh.zxbg.bgdzblr.BgdzblrService.checkSqd");
		var resultXml0 = service0.doService(paramXml);
		var code0 = service0.getCode();  //返回结果的状态
		var message0 = service0.getMessage();  //返回的信息
		if(code0==2000){
			var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzblr.BgdzblrService.addXzqhbgsqb");
			var resultXml = service.doService(paramXml);
			var code = service.getCode();  //返回结果的状态
			var message = service.getMessage();  //返回的信息
			if(code!= 2000){
				showBizMsg(code,message);
				return ;
			}else{
				showMessage("保存成功！",0);
				pxh=0;
				xzqhGrid.clearGrid();
				location.reload();
			}
		}else{
			showMessage(message0,0);
		}
	}else{
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzblr.BgdzblrService.addXzqhbgsqb");
		var resultXml = service.doService(paramXml);
		var code = service.getCode();  //返回结果的状态
		var message = service.getMessage();  //返回的信息
		if(code!= 2000){
			showBizMsg(code,message);
			return ;
		}else{
			showMessage("保存成功！",0);
			pxh=0;
			xzqhGrid.clearGrid();
			location.reload();
		}
	}
}
