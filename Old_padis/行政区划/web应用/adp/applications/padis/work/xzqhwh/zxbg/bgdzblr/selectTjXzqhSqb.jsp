<%@ include file="../../../../work/public/head.jsp" %>
<HTML>
<HEAD>
<TITLE>同级区划代码表</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="../../../../work/public/xloadtree/js/xtree2.js"></script>
<script type="text/javascript" src="../../../../work/public/xloadtree/js/xloadtree2.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../../../../console/public/js/service.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/grid/js/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/grid/js/grid.js"></SCRIPT>
<script language="javascript" src="../../../../work/public/droptree/js/dropTree.js"></script>
<script language="javascript" src="../../../../work/public/droptree/js/common.js"></script>
<LINK rel="stylesheet" href="../../../../work/public/droptree/css/style.css" type="text/css">

<!--业务要使用的JS文件-->
<SCRIPT LANGUAGE="JavaScript" src="./js/tjxzqhGrid.js"></SCRIPT>
<script language="javascript">
	webFXTreeConfig.rootIcon='../../../public/xloadtree/images/foldericon.png',
	webFXTreeConfig.openRootIcon='../../../public/xloadtree/images/openfoldericon.png',
	webFXTreeConfig.folderIcon='../../../public/xloadtree/images/foldericon.png',
	webFXTreeConfig.openFolderIcon='../../../public/xloadtree/images/openfoldericon.png',
	webFXTreeConfig.fileIcon='../../../public/xloadtree/images/file.png',
	webFXTreeConfig.iIcon='../../../public/xloadtree/images/I.png',
	webFXTreeConfig.lIcon='../../../public/xloadtree/images/L.png',
	webFXTreeConfig.lMinusIcon='../../../public/xloadtree/images/Lminus.png',
	webFXTreeConfig.lPlusIcon='../../../public/xloadtree/images/Lplus.png',
	webFXTreeConfig.tIcon='../../../public/xloadtree/images/T.png',
	webFXTreeConfig.tMinusIcon='../../../public/xloadtree/images/Tminus.png',
	webFXTreeConfig.tPlusIcon='../../../public/xloadtree/images/Tplus.png',
	webFXTreeConfig.blankIcon='../../../public/xloadtree/images/blank.png';
	webFXTreeConfig.loadingIcon ='../../../public/xloadtree/images/loading.gif';
	webFXTreeConfig.defaultText="Tree Item";
	webFXTreeConfig.defaultAction=null;
	webFXTreeConfig.defaultBehavior="classic";
	webFXTreeConfig.usePersistence=true;
	//add by wangweip on 2007-11-29 当复选框disabled的时候,字体的颜色
	webFXTreeConfig.disabledFontColor='#ABABAB';


var PadisDropTreeConfig = {
	IMAGE_MOUSE_DOWN: "../../../public/droptree/images/select/select_btn_down.gif",
	IMAGE_MOUSE_UP:"../../../public/droptree/images/select/select_btn.gif",
	IMAGE_MOUSE_OVER:"../../../public/droptree/images/select/select_btn_on.gif",
	IMAGE_MOUSE_OUT:"../../../public/droptree/images/select/select_btn.gif",
	TEXTBOX_WIDTH:200,
	DROPTREE_WIDTH:200,
	DROPTREE_HEIGHT:250
};
    if("<%=BusinessMsg%>".length>11){
		showMessage("<%=BusinessMsg%>",0);
    }
</script>
</HEAD>
<BODY onkeydown="isEnter(event)"> 
<form name="forForm" action="BgdzblrService.addXzqhbgsqb.do" method="post">
	<fieldset style="margin-top:-10px;"><legend>同级区划代码表</legend>
	<table>
	<tr><td><input type="checkbox" name="FLAG" value="0" class="checkbox" onclick="showTree()"/>&nbsp;&nbsp;&nbsp;&nbsp;跨级并入</td><td id="messageID"></td></tr>
	<tr><td colspan="2">
	<div id="tjxzqh" style="width:100%;height=370px; overflow: scroll;overflow:auto;">
	</div>
	<div id="treeContainer" style="width:100%;display:none" />
	</td></tr>
	</table>
	</fieldset>
	 <center style="position:absolute;top:425px;left:175px">
		<button id="btn_add" onclick="addtjxzqhGrid()">确 定</button>	
	</center>
</form>
</BODY>
</HTML>