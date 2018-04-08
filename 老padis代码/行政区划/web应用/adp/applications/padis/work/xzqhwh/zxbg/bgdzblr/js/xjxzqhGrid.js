/**
*注册事件
*/
addLoadListener(initEvents);
var xjxzqhGrid;
var isClick=true;
/**
*初始化事件列表
*/
function initEvents(){
	newGrid();
	mergerXzqh();
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
	xjxzqhGrid = new PadisGrid("SJXZQH","xjxzqh");

	col=new PadisCol("并入区划代码","MBXZQH_DM");
	col.controlType=2;
	col.width="80px";
	xjxzqhGrid.cols.push(col);

	
	col=new PadisCol("原区划代码","YSXZQH_DM");
	col.controlType=2;
	xjxzqhGrid.cols.push(col);
	
	col=new PadisCol("原区划名称","YSXZQH_MC");
	col.controlType=2;
	col.textAlign="left";
	xjxzqhGrid.cols.push(col);
	
	col=new PadisCol("迁移后区划名称","MBXZQH_MC");
	col.controlType=0;
	col.textAlign="left";
	xjxzqhGrid.cols.push(col);

	col=new PadisCol("迁移后区划代码","BJQH_DM");
	col.controlType=0;
	col.maxLength="3";
	col.width="110px";
	col.errMsg="迁移后区划代码不能为空!";
	xjxzqhGrid.cols.push(col);

	//创建空表格
	xjxzqhGrid.create();
	//刷新表格
	xjxzqhGrid.refreshGrid();
	xjxzqhGrid.cellOnChange="changeXzqh();";
}

/**
 *输入框改变事件，查看行政区划是否存在
 *@param  无
 *@return 字符串
*/
function changeXzqh(){
	
	var cell=xjxzqhGrid.selectedCell;
	if(!isClick){
		if(cell.rowIndex>0){
			cell = xjxzqhGrid.rows[cell.rowIndex-1].getCellByColumn("BJQH_DM");
		}else{
			cell = xjxzqhGrid.rows[cell.rowIndex].getCellByColumn("BJQH_DM");
		}
		isClick = true;
	}
	var row = cell.parent;
	var bjqh_dm = row.getCellByColumn("BJQH_DM").value;
	if(!checkBjxzqh(row,bjqh_dm,(cell.rowIndex+1))){
		return;
	}
	var xzqh_jb_dm = row.getCellByColumn("MBXZQH_DM").value+bjqh_dm;
	var len = 15-xzqh_jb_dm.length;
	if(len>0){
		for(var i=0;i<len;i++){
			xzqh_jb_dm = xzqh_jb_dm+"0";
		}
	}
	var rowsArr = xjxzqhGrid.rows;
	for(var i=0;i<rowsArr.length;i++){
		var bjqhDm = rowsArr[i].getCellByColumn("BJQH_DM").value;
		if(bjqhDm==bjqh_dm&&cell.rowIndex!==i){
			showMessage("第"+(i+1)+"行和第"+(cell.rowIndex+1)+"行区划代码重复，请重新填写！",0);
			row.getCellByColumn("BJQH_DM").value="";
			return false;
		}
	}
	var czry_dm = parent.opener.document.getElementById("CZRY_DM").value;
	if(parent.opener.isExistsXzqh(xzqh_jb_dm)!="校验通过"){
		showMessage("区划代码“"+xzqh_jb_dm+"”已存在，请重新编写！",0);
			row.getCellByColumn("BJQH_DM").value="";
			return false;
	}else if(parent.opener.isExistsYjyXzqh(xzqh_jb_dm)=="true"&&getJbdm(xzqh_jb_dm).length<12&&czry_dm!="00000000000"){
		showMessage("迁移后区划代码“"+xzqh_jb_dm+"”曾经被使用过，已禁用，请重新填写！",0);
		return false;
	}else if(isExistsXzqh(xzqh_jb_dm,"M")){
		showMessage("迁移后区划代码“"+xzqh_jb_dm+"”在本次变更对照表中已被占用，请重新填写！",0);
		return false;
	}
}

/**
 *确定按钮事件
 *@param  无
 *@return 字符串
*/
function addxjxzqhGrid(){
	var jb_dm = document.getElementById("JB_DM").value;
	var checkedRows = xjxzqhGrid.getCheckedRows();
	if(checkedRows.length<1){
		showMessage("对不起，至少选择一行！",0);
		return false;
	}
	var xzqhs = new Array();
	for(var i=(checkedRows.length-1);i>=0;i--){
		var ysxzqh_dm = checkedRows[i].getCellByColumn("YSXZQH_DM").value;
		var ysxzqh_mc = checkedRows[i].getCellByColumn("YSXZQH_MC").value;
		var mbxzqh_dm = checkedRows[i].getCellByColumn("MBXZQH_DM").value;
		var mbxzqh_mc = checkedRows[i].getCellByColumn("MBXZQH_MC").value;
		var bjqh_dm = checkedRows[i].getCellByColumn("BJQH_DM").value;
		if(!checkBjxzqh(checkedRows[i],bjqh_dm,(checkedRows.length-i))){
			return;
		}
		for(var m=(i-1);m>=0;m--){
			var bjqhdm = checkedRows[m].getCellByColumn("BJQH_DM").value;
			if(bjqhdm==bjqh_dm){
				showMessage("第"+(checkedRows.length-i)+"行和第"+(checkedRows.length-m)+"行区划代码重复，请重新填写！",0);
				return false;
			}
		}
		var xzqh_jb_dm = mbxzqh_dm+bjqh_dm;
		var len = 15-xzqh_jb_dm.length;
		if(len>0){
			for(var j=0;j<len;j++){
				xzqh_jb_dm = xzqh_jb_dm+"0";
			}
		}
		var czry_dm = parent.opener.document.getElementById("CZRY_DM").value;
		if(isExistsXzqh(ysxzqh_dm,"Y")){
			showMessage("原区划代码“"+ysxzqh_dm+"”已存在本次变更对照表中，不能对同一个区划重复操作！",0);
			return false;
		}else if(isExistsXzqh(xzqh_jb_dm,"M")){
			showMessage("迁移后区划代码“"+xzqh_jb_dm+"”在本次变更对照表中已被占用，请重新填写！",0);
			return false;
		}else if(parent.opener.isExistsXzqh(xzqh_jb_dm)!="校验通过"){
			showMessage("迁移后区划代码“"+xzqh_jb_dm+"”已存在，请重新填写！",0);
			return false;
		}else if(parent.opener.isExistsYjyXzqh(xzqh_jb_dm)=="true"&&getJbdm(xzqh_jb_dm).length<12&&czry_dm!="00000000000"){
			showMessage("迁移后区划代码“"+xzqh_jb_dm+"”曾经被使用过，已禁用，请重新填写！",0);
			return false;
		}
		var xzqh = new Array();
		xzqh[0]=ysxzqh_dm;
		xzqh[1]=ysxzqh_mc;
		xzqh[2]="迁移";
		xzqh[3]=xzqh_jb_dm;
		xzqh[4]=mbxzqh_mc;
		xzqh[5]="";
		xzqh[6]="41";
		xzqhs[i]=xzqh;
	}
	fillGrid(xzqhs);
	var qsj_xzqh_dm = parent.opener.document.getElementById("QSJ_XZQH_DM").value;
	var qdsj_xzqh_dm = parent.opener.document.getElementById("QDSJ_XZQH_DM").value;
	var qxj_xzqh_dm = parent.opener.document.getElementById("QXJ_XZQH_DM").value;
	var qxzj_xzqh_dm = parent.opener.document.getElementById("QXZJ_XZQH_DM").value;
	var qcj_xzqh_dm = parent.opener.document.getElementById("QCJ_XZQH_DM").value;
	var qz_xzqh_dm = parent.opener.document.getElementById("QZ_XZQH_DM").value;
	var ysxzqh_dm = qsj_xzqh_dm+qdsj_xzqh_dm+qxj_xzqh_dm+qxzj_xzqh_dm+qcj_xzqh_dm+qz_xzqh_dm;
	var count = hasChildXzqh(ysxzqh_dm);
	var addCount = 0;
	if(count!="0"){
		var existRows = new Array();
		var newRows=0;
		existRows = parent.opener.xzqhGrid.getArrayData();
		for(var j=0;j<existRows.length;j++){
			var sj_dm = getSjxzqhdm(existRows[j][0]);
			if(sj_dm==ysxzqh_dm&&existRows[j][6]=="41"){
				newRows++;
			}
			if(getSjxzqhdm(existRows[j][3])==ysxzqh_dm&&existRows[j][6]=="11"){
				addCount++;
			}
		}
		if(newRows==(Number(count)+Number(addCount))){
			addSjXzqh();
		}
	}
	window.close();
}

/**
 *增加多行Grid表格.
 *@param  无
 *@return 无
*/
function fillGrid(xzqhs){
	var bglx_dm = parent.opener.document.getElementById("BGLX_DM").value;
	var existRows = new Array();	
	existRows = parent.opener.xzqhGrid.getArrayData();
	if(xzqhs.length>0){
		for(var i=(xzqhs.length-1);i>=0;i--){
			var newRows=0;
			for(var j=0;j<existRows.length;j++){
				if(existRows[j][0]!=xzqhs[i][0]){
					newRows++;
				}else{
					showMessage("原区划代码“"+existRows[j][0]+"”已存在，请重新填写！",0);
					return;
				}				
			}
			if(newRows==existRows.length){
				parent.opener.xzqhGrid.addRow(xzqhs[i]);
			}			
		}
		for(var i=0;i<parent.opener.xzqhGrid.rows.length;i++)
		{
			parent.opener.xzqhGrid.rows[i].setChecked(true);
		}
	}
	
}

/**
 * 
 * <p>方法名称：mergerXzqh</p>
 * <p>方法描述：初始化页面，将父窗口选中的区划代码的下级区划查询出来</p>
 *
 * @author lijl
 * @since 2009-7-10
 */
function mergerXzqh(){
	var jb_dm = document.getElementById("JB_DM").value;
	var bglx_dm = document.getElementById("XZQHBGLX_DM").value;
	
	var qsj_xzqh_dm = parent.opener.document.getElementById("QSJ_XZQH_DM").value;
	var qdsj_xzqh_dm = parent.opener.document.getElementById("QDSJ_XZQH_DM").value;
	var qxj_xzqh_dm = parent.opener.document.getElementById("QXJ_XZQH_DM").value;
	var qxzj_xzqh_dm = parent.opener.document.getElementById("QXZJ_XZQH_DM").value;
	var qcj_xzqh_dm = parent.opener.document.getElementById("QCJ_XZQH_DM").value;
	var qz_xzqh_dm = parent.opener.document.getElementById("QZ_XZQH_DM").value;
	var ysxzqh_dm = qsj_xzqh_dm+qdsj_xzqh_dm+qxj_xzqh_dm+qxzj_xzqh_dm+qcj_xzqh_dm+qz_xzqh_dm;

	var hsj_xzqh_dm = parent.opener.document.getElementById("SJ_XZQH_DM").value;
	var hdsj_xzqh_dm = parent.opener.document.getElementById("DSJ_XZQH_DM").value;
	var hxj_xzqh_dm = parent.opener.document.getElementById("XJ_XZQH_DM").value;
	var hxzj_xzqh_dm = parent.opener.document.getElementById("XZJ_XZQH_DM").value;
	var hcj_xzqh_dm = parent.opener.document.getElementById("CJ_XZQH_DM").value;
	var hz_xzqh_dm = parent.opener.document.getElementById("Z_XZQH_DM").value;

	var mbxzqh_dm = hsj_xzqh_dm+hdsj_xzqh_dm+hxj_xzqh_dm+hxzj_xzqh_dm+hcj_xzqh_dm+hz_xzqh_dm;
	var existRows = parent.opener.xzqhGrid.getArrayData();
	var xml = "";
	if(existRows.length>0){
		for(var j=0;j<existRows.length;j++){
			if(getSjxzqhdm(existRows[j][3])==ysxzqh_dm&&existRows[j][6]=="11"){
				var flag=true;
				for(var i=(j+1);i<existRows.length;i++){
					if(existRows[i][0]==existRows[j][3]&&existRows[i][6]=="41"){
						flag=false;
						break;
					}
				}
				if(flag){
					xml=xml+"<ITEM><YSXZQH_DM>"+existRows[j][3]+"</YSXZQH_DM><YSXZQH_MC>"+existRows[j][4]+"</YSXZQH_MC><MBXZQH_DM>"+getJbdm(mbxzqh_dm)+"</MBXZQH_DM>";
					xml=xml+"<MBXZQH_MC>"+existRows[j][4]+"</MBXZQH_MC><BJQH_DM></BJQH_DM></ITEM>";
				}
			}
		}
	}
	
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<YSXZQH_DM>"+ysxzqh_dm+"</YSXZQH_DM><MBXZQH_DM>"+mbxzqh_dm+"</MBXZQH_DM>";
	paramXml = paramXml+"<XZQHBGLX_DM>"+bglx_dm+"</XZQHBGLX_DM>"+parent.opener.xzqhGrid.getXmlData()+"</map></ROOT>";	
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzblr.BgdzblrService.mergerXzqh");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
	var xzqhDOC = doc.getElementsByTagName("ITEMS")(0);
 	var xzqhXML = xzqhDOC.xml;
	var strXml ="";
	if( xzqhDOC.hasChildNodes ){ //判断是否有子标签
		if(xml!=""){
			var index = xzqhXML.indexOf("</ITEMS>");
			strXml=xzqhXML.substring(0,index)+xml+"</ITEMS>";
		}else{
			strXml = xzqhXML;
		}
	}else{
		strXml = "<ITEMS>"+xml+"</ITEMS>";
	}
	
	xjxzqhGrid.setXmlData(strXml,false);
	xjxzqhGrid.refreshGrid();
	
}

/**
 * 
 * <p>方法名称：getSjxzqhdm</p>
 * <p>方法描述：得到上级行政区划代码</p>
 * @param xzqh_dm 行政区划代码
 * @author lijl
 * @since 2009-7-10
 */
function getSjxzqhdm(xzqh_dm){
	var sjxzqhdm="";
	if(xzqh_dm==null||xzqh_dm==""||xzqh_dm.length!=15){
		return sjxzqhdm;
	}
	if(xzqh_dm.substring(0, 2)=="00"){
		return sjxzqhdm;
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
 * 
 * <p>方法名称：getJcdm</p>
 * <p>方法描述：根据行政区划代码获取相应级次代码</p>
 * @param xzqh_dm 行政区划代码
 * @author lijl
 * @since 2009-7-10
 */
function getJcdm(xzqh_dm){
	var jcdm="";
	if(xzqh_dm==null||xzqh_dm==""||xzqh_dm.length!=15){
		return jcdm;
	}	
	if(xzqh_dm.substring(0, 2)=="00"){
		jcdm = "0";
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

/**
 * 
 * <p>方法名称：isExistsXzqh</p>
 * <p>方法描述：是否被变更过</p>
 * @param xzqh_dm 行政区划代码
 * @author lijl
 * @since 2009-7-10
 */
function isExistsXzqh(xzqh_dm,biaozhi){
	var flag=false;
	var existRows = new Array();
	existRows = parent.opener.xzqhGrid.getArrayData();
	for(var j=0;j<existRows.length;j++){
		if(biaozhi=="M"){
			if(existRows[j][3]==xzqh_dm){					
				flag=true;
				break;
			}
		}else if(biaozhi=="Y"){
			if(existRows[j][0]==xzqh_dm&&existRows[j][0]!=""){					
				flag=true;
				break;
			}
		}
	}	
	return flag;
}

/**
 * 
 * <p>方法名称：addSjXzqh</p>
 * <p>方法描述：确定按钮事件</p>
 * @param xzqh_dm 行政区划代码
 * @author lijl
 * @since 2009-7-10
 */
function addSjXzqh(){
	var bgq_xzqh_mc = parent.opener.document.getElementById("YSXZQH_MC").value;
	var jb_dm = parent.opener.document.getElementById("JB_DM").value;
	var bgh_xzqh_mc = parent.opener.document.getElementById("MBXZQH_MC").value;
	var sj_xzqh_dm = parent.opener.document.getElementById("SJ_XZQH_DM").value;
	var dsj_xzqh_dm = parent.opener.document.getElementById("DSJ_XZQH_DM").value;
	var xj_xzqh_dm = parent.opener.document.getElementById("XJ_XZQH_DM").value;
	var xzj_xzqh_dm = parent.opener.document.getElementById("XZJ_XZQH_DM").value;
	var cj_xzqh_dm = parent.opener.document.getElementById("CJ_XZQH_DM").value;
	var z_xzqh_dm = parent.opener.document.getElementById("Z_XZQH_DM").value;
	var qsj_xzqh_dm = parent.opener.document.getElementById("QSJ_XZQH_DM").value;
	var qdsj_xzqh_dm = parent.opener.document.getElementById("QDSJ_XZQH_DM").value;
	var qxj_xzqh_dm = parent.opener.document.getElementById("QXJ_XZQH_DM").value;
	var qxzj_xzqh_dm = parent.opener.document.getElementById("QXZJ_XZQH_DM").value;
	var qcj_xzqh_dm = parent.opener.document.getElementById("QCJ_XZQH_DM").value;
	var qz_xzqh_dm = parent.opener.document.getElementById("QZ_XZQH_DM").value;
	var bz = parent.opener.document.getElementById("BZ").value;
	var bglx_dm = parent.opener.document.getElementById("BGLX_DM").value;
	var bgh_xzqh_dm  = sj_xzqh_dm+dsj_xzqh_dm+xj_xzqh_dm+xzj_xzqh_dm+cj_xzqh_dm+z_xzqh_dm;
	var bgq_xzqh_dm  = qsj_xzqh_dm+qdsj_xzqh_dm+qxj_xzqh_dm+qxzj_xzqh_dm+qcj_xzqh_dm+qz_xzqh_dm;

	var xzqhs = new Array();
	var xzqh = new Array();
	xzqh[0]=bgq_xzqh_dm;
	xzqh[1]=bgq_xzqh_mc;
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
	parent.opener.fillGrid(xzqhs);
}

/**
 * 
 * <p>方法名称：checkBjxzqh</p>
 * <p>方法描述：校验本行区划代码是否符合要求</p>
 * @param row 行对象
 * @param bjqh_dm 本级区划代码
 * @param index 行位置
 * @author lijl
 * @since 2009-7-10
 */
function checkBjxzqh(row,bjqh_dm,index){
	var jb_dm = parent.opener.document.getElementById("JB_DM").value;
	var flag = true;
	if(bjqh_dm.length==0){
		showMessage("第"+index+"行迁移后区划代码不能为空",0);
		return false;
	}else{
		if(!isNumber(bjqh_dm)){
			showMessage("只能输入数字！",0);
			row.getCellByColumn("BJQH_DM").value="";
			flag = false;
		}
		if(jb_dm<=2){
			if(bjqh_dm.length!=2){
				showMessage("县级及以上区划代码为两位！",0);
				flag = false;
			}else if(bjqh_dm=="00"){
				showMessage("区划代码不能为00",0);
				row.getCellByColumn("BJQH_DM").value="";
				flag = false;
			}
		}else if(jb_dm>2){
			if(bjqh_dm.length!=3){
				showMessage("县级以下区划代码为三位！",0);
				flag = false;
			}else if(bjqh_dm=="000"){
				showMessage("区划代码不能为000",0);
				row.getCellByColumn("BJQH_DM").value="";
				flag = false;
			}
		}
	}
	return flag;
}

/**
 * 
 * <p>方法名称：hasChildXzqh</p>
 * <p>方法描述：是否有下级节点</p>
 * @param xzqh_dm 行政区划代码
 * @author lijl
 * @since 2009-7-10
 */
function hasChildXzqh(xzqh_dm){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<XZQH_DM>"+xzqh_dm+"</XZQH_DM></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzblr.BgdzblrService.hasChildXzqh");
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
 * <p>方法描述：根据行政区划代码获取相应级别代码</p>
 * @param xzqh_dm 行政区划代码
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
 * <p>方法名称：isEnter</p>
 * <p>方法描述：上下左右事件</p>
 * @param event 键盘事件
 * @author lijl
 * @since 2009-7-10
 */
function isEnter(event){
	var keyCode = event.keyCode;
	var xjxzqhDiv = document.getElementById("xjxzqh");
	var cell=xjxzqhGrid.selectedCell;
	if(keyCode==37){
		if(cell==null){
			cell = xjxzqhGrid.rows[0].getCellByColumn("MBXZQH_MC");
			xjxzqhGrid.selectedCell = xjxzqhGrid.rows[0].getCellByColumn("MBXZQH_MC");
			xjxzqhDiv.getElementsByTagName("tr")[1].className = "tab-on";
			
		}else{
			var index = cell.rowIndex;
			xjxzqhGrid.selectedCell = xjxzqhGrid.rows[index].getCellByColumn("MBXZQH_MC");	
		}
		isClick=false;
	}else if(keyCode==38){
		
		if(cell==null){
			cell = xjxzqhGrid.rows[xjxzqhGrid.rows.length-1].getCellByColumn("BJQH_DM");
			xjxzqhGrid.selectedCell = xjxzqhGrid.rows[xjxzqhGrid.rows.length-1].getCellByColumn("BJQH_DM");
			xjxzqhDiv.getElementsByTagName("tr")[xjxzqhGrid.rows.length].className = "tab-on";
			
		}else{
			var index = cell.rowIndex;
			if(index>=1){
				for(var i=1;i<xjxzqhGrid.rows.length+1;i++){
					xjxzqhDiv.getElementsByTagName("tr")[i].className = null;
				}				
				xjxzqhDiv.getElementsByTagName("tr")[index].className = "tab-on";
				xjxzqhGrid.selectedCell = xjxzqhGrid.rows[index-1].getCellByColumn("BJQH_DM");		
			}
		}
		isClick=false;
	}else if(keyCode==39){
		if(cell==null){
			cell = xjxzqhGrid.rows[0].getCellByColumn("BJQH_DM");
			xjxzqhGrid.selectedCell = xjxzqhGrid.rows[0].getCellByColumn("BJQH_DM");
			xjxzqhDiv.getElementsByTagName("tr")[1].className = "tab-on";
			
		}else{
			var index = cell.rowIndex;
			xjxzqhGrid.selectedCell = xjxzqhGrid.rows[index].getCellByColumn("BJQH_DM");	
		}
		isClick=false;
	}else if(keyCode==40){
		if(cell==null){
			cell = xjxzqhGrid.rows[0].getCellByColumn("BJQH_DM");
			xjxzqhGrid.selectedCell = xjxzqhGrid.rows[0].getCellByColumn("BJQH_DM");
			xjxzqhDiv.getElementsByTagName("tr")[1].className = "tab-on";
			
		}else{
			var index = cell.rowIndex;
			if(index<(xjxzqhGrid.rows.length-1)){
				for(var i=1;i<xjxzqhGrid.rows.length+1;i++){
					xjxzqhDiv.getElementsByTagName("tr")[i].className = null;
				}
				
				xjxzqhDiv.getElementsByTagName("tr")[index+2].className = "tab-on";
				xjxzqhGrid.selectedCell = xjxzqhGrid.rows[index+1].getCellByColumn("BJQH_DM");		
			}
		}
		isClick=false;
	}
	xjxzqhGrid.selectedCell.changeToControl();
	
}