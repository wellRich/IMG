/**
*ע���¼�
*/
addLoadListener(initEvents);
var xzqhGrid;
var pxh=0;
/**
*��ʼ���¼��б�
*/
function initEvents(){
	newGrid();
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
	xzqhGrid = new PadisGrid("XZQH","xzqh");
	//xzqhGrid.showCheckbox=false;//����ʾ��ѡ��
	/*var col=new PadisCol("���","PXH");
	col.controlType=2;
	col.width="30px";
	xzqhGrid.cols.push(col);*/

	var col=new PadisCol("ԭ��������","YSXZQH_DM");
	col.controlType=2;
	col.width="110px";
	xzqhGrid.cols.push(col);
	
	col=new PadisCol("ԭ��������","YSXZQH_MC");
	col.controlType=2;
	col.textAlign="left";
	xzqhGrid.cols.push(col);

	col=new PadisCol("��������","BGLX_MC");
	col.controlType=2;
	col.width="60px";
	xzqhGrid.cols.push(col);
	
	col=new PadisCol("����������","MBXZQH_DM");
	col.controlType=2;
	col.width="110px";
	xzqhGrid.cols.push(col);

	col=new PadisCol("����������","MBXZQH_MC");
	col.controlType=2;
	col.textAlign="left";
	xzqhGrid.cols.push(col);

	col=new PadisCol("��ע","BZ");
	col.controlType=2;
	col.textAlign="left";
	xzqhGrid.cols.push(col);

	col=new PadisCol("�������ʹ���","BGLX_DM");
	col.controlType=3;
	col.width="30px";
	xzqhGrid.cols.push(col);

	//�����ձ��
	xzqhGrid.create();
	//ˢ�±��
	xzqhGrid.refreshGrid();
	/////////////////////////////////////////////////
}
/**
 *��Ӧ�����������嵥����ť.
 *@param  ��
 *@return ��
*/
function addXzqhsqb(){
	addGrid();
}

/**
 *��Ӧ��ɾ���嵥����ť.
 *@param  ��
 *@return ��
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
 * <p>�������ƣ�checkZxqh</p>
 * <p>�������������������Ƿ����Ҫ��</p>
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
		showMessage("ʡ���������볤��Ϊ��λ�����޸ģ�",0);
		return false;
	}
	else if(dsj_xzqh_dm.length!=2||qdsj_xzqh_dm.length!=2){
		showMessage("�м��������볤��Ϊ��λ�����޸ģ�",0);
		return false;
	}
	else if(xj_xzqh_dm.length!=2||qxj_xzqh_dm.length!=2){
		showMessage("�ؼ��������볤��Ϊ��λ�����޸ģ�",0);
		return false;
	}
	else if(xzj_xzqh_dm.length!=3||qxzj_xzqh_dm.length!=3){
		showMessage("�����������볤��Ϊ��λ�����޸ģ�",0);
		return false;
	}
	else if(cj_xzqh_dm.length!=3||qcj_xzqh_dm.length!=3){
		showMessage("�弶�������볤��Ϊ��λ�����޸ģ�",0);
		return false;
	}
	else if(z_xzqh_dm.length!=3||qz_xzqh_dm.length!=3){
		showMessage("���������볤��Ϊ��λ�����޸ģ�",0);
		return false;
	}
	else{
		if(!isNumber(bgh_xzqh_dm)||!isNumber(bgq_xzqh_dm)){
			showMessage("ֻ���������֣�",0);
			return false;
		}else{
			return true;
		}
	}
}

/**
 * 
 * <p>�������ƣ�getSjxzqh</p>
 * <p>������������ȡ�ϼ�����</p>
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
 * <p>�������ƣ�getJbdm</p>
 * <p>�����������������������ȡ��Ӧ�������</p>
 * @param xzqh_dm ��������
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
 *��������
 *@param  ��
 *@return ��
*/
function saveXzqh(){
	var ringFlag = document.getElementById("RINGFLAG").value;
	var czry_dm = document.getElementById("CZRY_DM").value;
	var sqbmc = document.getElementById("SQBMC").value;
	var sqdxh = document.getElementById("SQDXH").value;
	if(sqbmc.indexOf("<")>-1||sqbmc.indexOf(">")>-1||sqbmc.indexOf("&")>-1){
		showMessage("��ע���������ַ���������������",0);
		return false;
	}
	//������֤��
	var existRows = xzqhGrid.getArrayData();
	if(existRows.length<=0){
		showMessage("����������ݣ�",0);
		return false;
	}
	for(var j=0;j<existRows.length;j++){
		if(existRows[j][6]!="21"&&ringFlag=="1"){
			showMessage("��״���ݲ����г������������ͣ�",0);
			return false;
		}else{
			if(czry_dm!="00000000000"){
				if(getJbdm(existRows[j][0]).length<12&&ringFlag=="1"){
					showMessage("ֻ��¼��弶��״���ݣ�",0);
					return false;
				}
			}
		}
	}

	var otherXml = "<SQBMC>"+sqbmc+"</SQBMC><SQDXH>"+sqdxh+"</SQDXH><RINGFLAG>"+ringFlag+"</RINGFLAG>";
	var paramXml = xzqhGrid.getXmlData()+""+otherXml;	// ��ȡ��������
	if(ringFlag=="1"){
		var service0 = new Service("com.padis.business.xzqhwh.zxbg.bgdzblr.BgdzblrService.checkSqd");
		var resultXml0 = service0.doService(paramXml);
		var code0 = service0.getCode();  //���ؽ����״̬
		var message0 = service0.getMessage();  //���ص���Ϣ
		if(code0==2000){
			var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzblr.BgdzblrService.addXzqhbgsqb");
			var resultXml = service.doService(paramXml);
			var code = service.getCode();  //���ؽ����״̬
			var message = service.getMessage();  //���ص���Ϣ
			if(code!= 2000){
				showBizMsg(code,message);
				return ;
			}else{
				showMessage("����ɹ���",0);
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
		var code = service.getCode();  //���ؽ����״̬
		var message = service.getMessage();  //���ص���Ϣ
		if(code!= 2000){
			showBizMsg(code,message);
			return ;
		}else{
			showMessage("����ɹ���",0);
			pxh=0;
			xzqhGrid.clearGrid();
			location.reload();
		}
	}
}
