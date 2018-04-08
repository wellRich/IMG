/**
*ע���¼�
*/
addLoadListener(initEvents);
var sjxzqhGrid;
var xzqhTree;
var xzqhDm="";
/**
*��ʼ���¼��б�
*/
function initEvents(){
	newGrid();
	mergerXzqh();
	var jc_dm = parent.opener.document.getElementById("JB_DM").value;
	var initJc_dm = parent.opener.document.getElementById("JC_DM").value;
	if(Number(jc_dm)-Number(initJc_dm)==2){
		document.getElementById("messageID").innerHTML = "<font color=\"red\">�����û�û�п�����Ǩ��������������Ȩ�ޣ�</font>";
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
	sjxzqhGrid = new PadisGrid("SJXZQH","sjxzqh");
	sjxzqhGrid.showCheckbox=false;

	col=new PadisCol("�ϼ���������","XZQH_DM");
	col.controlType=2;
	col.width="130px";
	sjxzqhGrid.cols.push(col);
	
	col=new PadisCol("�ϼ���������","XZQH_MC");
	col.controlType=2;
	col.textAlign="left";
	sjxzqhGrid.cols.push(col);

	//�����ձ��
	sjxzqhGrid.create();
	//ˢ�±��
	sjxzqhGrid.refreshGrid();
	sjxzqhGrid.cellOnClick="clickXzqh();";
	/////////////////////////////////////////////////
}
var first=0;

/**
 * 
 * <p>�������ƣ�clickXzqh</p>
 * <p>�����������������е��¼�</p>
 *
 * @author lijl
 * @since 2009-7-10
 */
function clickXzqh(){
	var flag = document.getElementById("FLAG");
	if(!flag.checked){
		var cell = sjxzqhGrid.selectedCell;
		var row = cell.parent;	
		document.getElementById("SJXZQH_DM").innerText = row.getCellByColumn("XZQH_DM").value;
		if(first==0){
			document.getElementById("QYXZQH_MC").value =parent.opener.document.getElementById("YSXZQH_MC").value;
			first++;
		}
	}
}

/**
 * 
 * <p>�������ƣ�addsjxzqhGrid</p>
 * <p>�������������ȷ����ť�¼�</p>
 *
 * @author lijl
 * @since 2009-7-10
 */
function addsjxzqhGrid(){
	var cell = sjxzqhGrid.selectedCell;
	if(cell==null&&xzqhDm==""){
		showMessage("�Բ�����ѡ���ϼ�������",0);
		return false;
	}
	var jb_dm = document.getElementById("JB_DM").value;
	var qyxzhq_dm  = document.getElementById("QYXZQH_DM").value;
	if(qyxzhq_dm==""){
		showMessage("Ǩ�ƺ���벻��Ϊ�գ�",0);
		return false;
	}else{
		if(jb_dm<=3){
			if(qyxzhq_dm.length!=2){
				showMessage("�ؼ���������������Ϊ��λ��",0);
				return false;
			}else if(qyxzhq_dm=="00"){
				showMessage("�������벻��Ϊ00",0);
				document.getElementById("QYXZQH_DM").value="";
				return false;
			}
		}else if(jb_dm>2){
			if(qyxzhq_dm.length!=3){
				showMessage("�ؼ�������������Ϊ��λ��",0);
				return false;
			}else if(qyxzhq_dm=="000"){
				showMessage("�������벻��Ϊ000",0);
				document.getElementById("QYXZQH_DM").value="";
				return false;
			}
		}
	}
	var sjXzqh_dm = "";
	if(cell!=null){
		var row = cell.parent;
		sjXzqh_dm = row.getCellByColumn("XZQH_DM").value;	
	}else{
		sjXzqh_dm = xzqhDm;
	}
	var xzqh_dm ="";
	if(sjXzqh_dm!=""){
		xzqh_dm = getJbdm(sjXzqh_dm)+qyxzhq_dm;
		var len = 15-xzqh_dm.length;
		if(len>0){
			for(var j=0;j<len;j++){
				xzqh_dm = xzqh_dm+"0";
			}
		}
	}
	var czry_dm = parent.opener.document.getElementById("CZRY_DM").value;
	if(parent.opener.isExistsXzqh(xzqh_dm)!="У��ͨ��"){
		showMessage("Ǩ�ƴ��롰"+xzqh_dm+"���Ѵ��ڣ���������д��",0);
		return false;
	}else if(parent.opener.isExistsYjyXzqh(xzqh_dm)=="true"&&getJbdm(xzqh_dm).length<12&&czry_dm!="00000000000"){
		showMessage("Ǩ�ƺ��������롰"+xzqh_dm+"��������ʹ�ù����ѽ��ã���������д��",0);
		return false;
	}else if(isExistsXzqh(xzqh_dm)){
		showMessage("Ǩ�ƺ��������롰"+xzqh_dm+"���ڱ��α�����ձ����ѱ�ռ�ã���������д��",0);
		return false;
	}
	var mbxzqh_dm = xzqh_dm;
	parent.opener.document.getElementById("SJ_XZQH_DM").value = mbxzqh_dm.substring(0,2);
	parent.opener.document.getElementById("DSJ_XZQH_DM").value = mbxzqh_dm.substring(2,4);
	parent.opener.document.getElementById("XJ_XZQH_DM").value = mbxzqh_dm.substring(4,6);
	parent.opener.document.getElementById("XZJ_XZQH_DM").value = mbxzqh_dm.substring(6,9);
	parent.opener.document.getElementById("CJ_XZQH_DM").value = mbxzqh_dm.substring(9,12);
	parent.opener.document.getElementById("Z_XZQH_DM").value = mbxzqh_dm.substring(12,15);
	parent.opener.document.getElementById("MBXZQH_MC").value = document.getElementById("QYXZQH_MC").value;
	window.close();
}

/**
 * 
 * <p>�������ƣ�mergerXzqh</p>
 * <p>������������ʼ��ҳ�棬�Ѹ�����ѡ�е��������ϼ�������ѯ����</p>
 *
 * @author lijl
 * @since 2009-7-10
 */
function mergerXzqh(){
	var jb_dm = document.getElementById("JB_DM").value;
	var bglx_dm = document.getElementById("XZQHBGLX_DM").value;
	var mbxzqh_dm = parent.opener.document.getElementById("MBXZQH_DM").value;
	var qsj_xzqh_dm = parent.opener.document.getElementById("QSJ_XZQH_DM").value;
	var qdsj_xzqh_dm = parent.opener.document.getElementById("QDSJ_XZQH_DM").value;
	var qxj_xzqh_dm = parent.opener.document.getElementById("QXJ_XZQH_DM").value;
	var qxzj_xzqh_dm = parent.opener.document.getElementById("QXZJ_XZQH_DM").value;
	var qcj_xzqh_dm = parent.opener.document.getElementById("QCJ_XZQH_DM").value;
	var qz_xzqh_dm = parent.opener.document.getElementById("QZ_XZQH_DM").value;
	var ysxzqh_dm = qsj_xzqh_dm+qdsj_xzqh_dm+qxj_xzqh_dm+qxzj_xzqh_dm+qcj_xzqh_dm+qz_xzqh_dm;
	var sjxzqh_dm = getSjxzqhdm(ysxzqh_dm);
	var jcdm = getJcdm(sjxzqh_dm);
	var existRows = parent.opener.xzqhGrid.getArrayData();
	var xml = "";
	if(existRows.length>0){
		for(var j=0;j<existRows.length;j++){
			if(getJcdm(existRows[j][3])==jcdm&&getSjxzqhdm(sjxzqh_dm)==getSjxzqhdm(existRows[j][3])&&existRows[j][6]=="11"){
				xml=xml+"<ITEM><XZQH_DM>"+existRows[j][3]+"</XZQH_DM>";
				xml=xml+"<XZQH_MC>"+existRows[j][4]+"</XZQH_MC></ITEM>";
			}
		}
	}
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<YSXZQH_DM>"+ysxzqh_dm+"</YSXZQH_DM>";
	paramXml = paramXml+"<XZQHBGLX_DM>"+bglx_dm+"</XZQHBGLX_DM></map></ROOT>";
	
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.bgdzblr.BgdzblrService.mergerXzqh");
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
	sjxzqhGrid.setXmlData(strXml,false);
	sjxzqhGrid.refreshGrid();
}

/**
 * 
 * <p>�������ƣ�mergerXzqh</p>
 * <p>������������ʼ��ҳ�棬�Ѹ�����ѡ�е��������ϼ�������ѯ����</p>
 *
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
 * <p>�������ƣ�getJcdm</p>
 * <p>���������������������������ȡ��Ӧ���δ���</p>
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
 * <p>�������ƣ�getJbdm</p>
 * <p>���������������������������ȡ��Ӧ�������</p>
 * @param xzqh_dm ������������
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
 * <p>�������ƣ�isExistsXzqh</p>
 * <p>������������ѯ���������Ƿ���ڸ��������б���</p>
 * @param xzqh_dm ������������
 * @author lijl
 * @since 2009-7-10
 */
function isExistsXzqh(xzqh_dm){
	var flag=false;
	var existRows = new Array();
	existRows = parent.opener.xzqhGrid.getArrayData();
	for(var j=0;j<existRows.length;j++){
		if(existRows[j][3]==xzqh_dm){					
			flag=true;
			break;
		}
	}	
	return flag;
}

/**
 * 
 * <p>�������ƣ�createJkzkTree</p>
 * <p>������������������������</p>
 * @param xzqh_dm ��������
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
	var tree = new WebFXLoadTree("������","BgdzblrService.createSjXzqhTree.do?YSXZQH_DM="+xzqh_dm);
	tree.setShowCheckbox(false);
	tree.setExpanded(true);
	xzqhTree.create("treeContainer",tree);
	
}

/**
 * 
 * <p>�������ƣ�subQuery</p>
 * <p>����������������ڵ��¼�</p>
 * @param xzqh_dm ��������
 * @author lijl
 * @since 2009-7-10
 */
function subQuery(xzqh_dm,disabled){
	if(disabled=="true"){
		return;
	}
	xzqhDm = xzqh_dm;	
	document.getElementById("SJXZQH_DM").innerText = xzqh_dm;
	document.getElementById("QYXZQH_MC").value =parent.opener.document.getElementById("YSXZQH_MC").value;
}

/**
 * 
 * <p>�������ƣ�showTree</p>
 * <p>������������ʾ�缶Ǩ����</p>
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
		if(xzqhTree==null){
			createJkzkTree(ysxzqh_dm);
		}
		document.getElementById("sjxzqh").style.display="none";
		document.getElementById("treeContainer").style.display="";
	}else{
		document.getElementById("sjxzqh").style.display="";
		document.getElementById("treeContainer").style.display="none";
	}
}

/**
 * 
 * <p>�������ƣ�isEnter</p>
 * <p>�������������¼��¼�</p>
 *
 * @author lijl
 * @since 2009-7-10
 */
function isEnter(event){
	var keyCode = event.keyCode;
	var sjxzqhDiv = document.getElementById("sjxzqh");
	var cell=sjxzqhGrid.selectedCell;
	if(keyCode==38){
		if(cell==null){
			cell = sjxzqhGrid.rows[sjxzqhGrid.rows.length-1].getCellByColumn("XZQH_DM");
			sjxzqhGrid.selectedCell = sjxzqhGrid.rows[sjxzqhGrid.rows.length-1].getCellByColumn("XZQH_DM");
			sjxzqhDiv.getElementsByTagName("tr")[sjxzqhGrid.rows.length].className = "tab-on";
			
		}else{
			var index = cell.rowIndex;
			if(index>=1){
				for(var i=1;i<sjxzqhGrid.rows.length+1;i++){
					sjxzqhDiv.getElementsByTagName("tr")[i].className = null;
				}				
				sjxzqhDiv.getElementsByTagName("tr")[index].className = "tab-on";
				sjxzqhGrid.selectedCell = sjxzqhGrid.rows[index-1].getCellByColumn("XZQH_DM");		
			}
		}
		
	}else if(keyCode==40){
		
		if(cell==null){
			cell = sjxzqhGrid.rows[0].getCellByColumn("XZQH_DM");
			sjxzqhGrid.selectedCell = sjxzqhGrid.rows[0].getCellByColumn("XZQH_DM");
			sjxzqhDiv.getElementsByTagName("tr")[1].className = "tab-on";
			
		}else{
			var index = cell.rowIndex;
			if(index<(sjxzqhGrid.rows.length-1)){
				for(var i=1;i<sjxzqhGrid.rows.length+1;i++){
					sjxzqhDiv.getElementsByTagName("tr")[i].className = null;
				}
				
				sjxzqhDiv.getElementsByTagName("tr")[index+2].className = "tab-on";
				sjxzqhGrid.selectedCell = sjxzqhGrid.rows[index+1].getCellByColumn("XZQH_DM");		
			}
		}
	}
	clickXzqh();
}