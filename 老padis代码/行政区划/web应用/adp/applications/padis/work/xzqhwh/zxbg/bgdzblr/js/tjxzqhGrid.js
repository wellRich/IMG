/**
*ע���¼�
*/
addLoadListener(initEvents);
var tjxzqhGrid;
var xzqhTree;
var xzqhDm="";
var xzqhMc="";
/**
*��ʼ���¼��б�
*/
function initEvents(){
	newGrid();
	mergerXzqh();
	var jc_dm = parent.opener.document.getElementById("JB_DM").value;
	var initJc_dm = parent.opener.document.getElementById("JC_DM").value;
	if(Number(jc_dm)-Number(initJc_dm)==1){
		document.getElementById("messageID").innerHTML = "<font color=\"red\">�����û�û�п缶����������������Ȩ�ޣ�</font>";
	}else{
		document.getElementById("messageID").innerHTML ="";
	}
}

/**
*�����±��
*@param {String} event �¼�����
*/
function newGrid(event){
	if (typeof event == "undefined"){
		event = window.event;
	}
	/////////////////////////////////////////////////
	//Ŀ¼�ڵ���Ϣ���
	tjxzqhGrid = new PadisGrid("TJXZQH","tjxzqh");
	tjxzqhGrid.showCheckbox=false;

	col=new PadisCol("��������","XZQH_DM");
	col.controlType=2;
	col.width="130px";
	tjxzqhGrid.cols.push(col);
	
	col=new PadisCol("��������","XZQH_MC");
	col.controlType=2;
	col.textAlign="left";
	tjxzqhGrid.cols.push(col);

	//�����ձ��
	tjxzqhGrid.create();
	//ˢ�±��
	tjxzqhGrid.refreshGrid();
	/////////////////////////////////////////////////
}

/**
*���ȷ����ť�¼�
*
*/
function addtjxzqhGrid(){
	var cell = tjxzqhGrid.selectedCell;
	if(cell==null&&xzqhDm==""){
		showMessage("�Բ�����ѡ����������",0);
		return false;
	}
	var mbxzqh_dm = "";
	var mbxzqh_mc = "";
	if(cell!=null){
		var row = cell.parent;
		mbxzqh_dm = row.getCellByColumn("XZQH_DM").value;
		mbxzqh_mc = row.getCellByColumn("XZQH_MC").value;
	}else{
		mbxzqh_dm = xzqhDm;
		mbxzqh_mc = xzqhMc;
	}
	parent.opener.document.getElementById("SJ_XZQH_DM").value = mbxzqh_dm.substring(0,2);
	parent.opener.document.getElementById("DSJ_XZQH_DM").value = mbxzqh_dm.substring(2,4);
	parent.opener.document.getElementById("XJ_XZQH_DM").value = mbxzqh_dm.substring(4,6);
	parent.opener.document.getElementById("XZJ_XZQH_DM").value = mbxzqh_dm.substring(6,9);
	parent.opener.document.getElementById("CJ_XZQH_DM").value = mbxzqh_dm.substring(9,12);
	parent.opener.document.getElementById("Z_XZQH_DM").value = mbxzqh_dm.substring(12,15);
	parent.opener.document.getElementById("MBXZQH_MC").value = mbxzqh_mc;
	parent.opener.document.getElementById("SJ_XZQH_DM").disabled=true;
	parent.opener.document.getElementById("DSJ_XZQH_DM").disabled=true;
	parent.opener.document.getElementById("XJ_XZQH_DM").disabled=true;
	parent.opener.document.getElementById("XZJ_XZQH_DM").disabled=true;
	parent.opener.document.getElementById("CJ_XZQH_DM").disabled=true;
	parent.opener.document.getElementById("Z_XZQH_DM").disabled=true;
	window.close();
}

/**
*��ʼ��ҳ�淽�����ϲ�ͬ����������������ͬ������������ѯ����
*
*/
function mergerXzqh(){
	var jcdm = parent.opener.document.getElementById("JB_DM").value;
	var mbxzqh_dm = parent.opener.document.getElementById("MBXZQH_DM").value;
	var mbxzqh_dm = parent.opener.document.getElementById("MBXZQH_DM").value;
	var qsj_xzqh_dm = parent.opener.document.getElementById("QSJ_XZQH_DM").value;
	var qdsj_xzqh_dm = parent.opener.document.getElementById("QDSJ_XZQH_DM").value;
	var qxj_xzqh_dm = parent.opener.document.getElementById("QXJ_XZQH_DM").value;
	var qxzj_xzqh_dm = parent.opener.document.getElementById("QXZJ_XZQH_DM").value;
	var qcj_xzqh_dm = parent.opener.document.getElementById("QCJ_XZQH_DM").value;
	var qz_xzqh_dm = parent.opener.document.getElementById("QZ_XZQH_DM").value;
	var ysxzqh_dm = qsj_xzqh_dm+qdsj_xzqh_dm+qxj_xzqh_dm+qxzj_xzqh_dm+qcj_xzqh_dm+qz_xzqh_dm;
	var existRows = parent.opener.xzqhGrid.getArrayData();
	var xml = "";
	if(existRows.length>0){
		for(var j=0;j<existRows.length;j++){
			if(getJcdm(existRows[j][3])==jcdm&&existRows[j][6]=="11"&&getSjxzqhdm(ysxzqh_dm)==getSjxzqhdm(existRows[j][3])){
				xml=xml+"<ITEM><XZQH_DM>"+existRows[j][3]+"</XZQH_DM>";
				xml=xml+"<XZQH_MC>"+existRows[j][4]+"</XZQH_MC></ITEM>";
			}
		}
	}
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<YSXZQH_DM>"+ysxzqh_dm+"</YSXZQH_DM></map></ROOT>";
	
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzblr.BgdzblrService.getTjXzqh");
	var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
	var doc = loadXml(resultXml); //�����׼��MSXML2.DOMDocument����
	var xzqhDOC = doc.getElementsByTagName("ITEMS")(0);
 	var xzqhXML = xzqhDOC.xml;
	var strXml ="";
	if( xzqhDOC.hasChildNodes ){ //�ж��Ƿ����ӱ�ǩ
		if(xml!=""){
			var index = xzqhXML.indexOf("</ITEMS>");
			strXml=xzqhXML.substring(0,index)+xml+"</ITEMS>";
		}else{
			strXml = xzqhXML;
		}
	}else{
		strXml = "<ITEMS>"+xml+"</ITEMS>";
	}
	tjxzqhGrid.setXmlData(strXml,false);
	tjxzqhGrid.refreshGrid();
}


/**
*��ȡ�ϼ����������ķ���
*@param xzqh_dm ������������
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
 * <p>�������ƣ�getJbdm</p>
 * <p>���������������������������ȡ��Ӧ�������</p>
 * @param xzqh_dm ������������
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
 * <p>�������ƣ�createJkzkTree</p>
 * <p>������������������������</p>
 * @param xzqh_dm ������������
 * @author lijl
 * @since 2009-7-10
 */
function createJkzkTree(xzqh_dm)
{
	//�����ı���Ŀ��		
	PadisDropTreeConfig.TEXTBOX_WIDTH="100%";
	//�����������Ŀ��,������������<�ı�����,�����������=�ı�����
	PadisDropTreeConfig.DROPTREE_WIDTH=150;
	//�����������ĸ߶�
	PadisDropTreeConfig.DROPTREE_HEIGHT=350;
	xzqhTree=new PadisDropTree();
	var tree = new WebFXLoadTree("������","BgdzblrService.createTjXzqhTree.do?YSXZQH_DM="+xzqh_dm);
	tree.setShowCheckbox(false);
	tree.setExpanded(true);
	xzqhTree.create("treeContainer",tree);
}

/**
 * 
 * <p>�������ƣ�subQuery</p>
 * <p>��������������ڵ㷽��</p>
 * @param xzqh_dm ������������
 * @author lijl
 * @since 2009-7-10
 */
function subQuery(xzqh_dm,disabled){
	if(disabled=="true"){
		return;
	}
	xzqhDm = xzqh_dm;
	xzqhMc = xzqhTree.getSelected().text;
}

/**
 * 
 * <p>�������ƣ�showTree</p>
 * <p>������������ʾ�缶Ǩ������������</p>
 * 
 * @author lijl
 * @since 2009-7-10
 */
function showTree(){
	var qsj_xzqh_dm = parent.opener.document.getElementById("QSJ_XZQH_DM").value;
	var qdsj_xzqh_dm = parent.opener.document.getElementById("QDSJ_XZQH_DM").value;
	var qxj_xzqh_dm = parent.opener.document.getElementById("QXJ_XZQH_DM").value;
	var qxzj_xzqh_dm = parent.opener.document.getElementById("QXZJ_XZQH_DM").value;
	var qcj_xzqh_dm = parent.opener.document.getElementById("QCJ_XZQH_DM").value;
	var qz_xzqh_dm = parent.opener.document.getElementById("QZ_XZQH_DM").value;
	var ysxzqh_dm = qsj_xzqh_dm+qdsj_xzqh_dm+qxj_xzqh_dm+qxzj_xzqh_dm+qcj_xzqh_dm+qz_xzqh_dm;
	var flag = document.getElementById("FLAG");
	if(flag.checked){
		createJkzkTree(ysxzqh_dm);
		document.getElementById("tjxzqh").style.display="none";
		document.getElementById("treeContainer").style.display="";
	}else{
		document.getElementById("tjxzqh").style.display="";
		document.getElementById("treeContainer").style.display="none";
	}
}

/**
 * 
 * <p>�������ƣ�isEnter</p>
 * <p>������������ʾ�缶Ǩ������������</p>
 * @param event �����¼������¼��¼�
 * @author lijl
 * @since 2009-7-10
 */
function isEnter(event){
	var keyCode = event.keyCode;
	var tjxzqhDiv = document.getElementById("tjxzqh");
	var cell=tjxzqhGrid.selectedCell;
	if(keyCode==38){
		if(cell==null){
			cell = tjxzqhGrid.rows[tjxzqhGrid.rows.length-1].getCellByColumn("XZQH_DM");
			tjxzqhGrid.selectedCell = tjxzqhGrid.rows[tjxzqhGrid.rows.length-1].getCellByColumn("XZQH_DM");
			tjxzqhDiv.getElementsByTagName("tr")[tjxzqhGrid.rows.length].className = "tab-on";
			
		}else{
			var index = cell.rowIndex;
			if(index>=1){
				for(var i=1;i<tjxzqhGrid.rows.length+1;i++){
					tjxzqhDiv.getElementsByTagName("tr")[i].className = null;
				}				
				tjxzqhDiv.getElementsByTagName("tr")[index].className = "tab-on";
				tjxzqhGrid.selectedCell = tjxzqhGrid.rows[index-1].getCellByColumn("XZQH_DM");		
			}
		}
		
	}else if(keyCode==40){
		
		if(cell==null){
			cell = tjxzqhGrid.rows[0].getCellByColumn("XZQH_DM");
			tjxzqhGrid.selectedCell = tjxzqhGrid.rows[0].getCellByColumn("XZQH_DM");
			tjxzqhDiv.getElementsByTagName("tr")[1].className = "tab-on";
			
		}else{
			var index = cell.rowIndex;
			if(index<(tjxzqhGrid.rows.length-1)){
				for(var i=1;i<tjxzqhGrid.rows.length+1;i++){
					tjxzqhDiv.getElementsByTagName("tr")[i].className = null;
				}
				
				tjxzqhDiv.getElementsByTagName("tr")[index+2].className = "tab-on";
				tjxzqhGrid.selectedCell = tjxzqhGrid.rows[index+1].getCellByColumn("XZQH_DM");		
			}
		}
	}
}