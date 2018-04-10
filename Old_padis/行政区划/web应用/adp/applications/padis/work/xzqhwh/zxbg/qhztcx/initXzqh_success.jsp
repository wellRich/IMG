<%@ include file="../../../../work/public/head.jsp" %>
<%
	Map xzqhMap = (Map)request.getAttribute("map");
	String xzqh_dm = (String)xzqhMap.get("XZQH_DM");
	String xzqh_mc = (String)xzqhMap.get("XZQH_MC");
	if(xzqh_dm==null){
		xzqh_dm="";
	}
	if(xzqh_mc==null){
		xzqh_mc="";
	}
%>
<html xmlns:ctais>
<head>
<title>Çø»®×´Ì¬²éÑ¯</title>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="../../../../work/public/xloadtree/js/xtree2.js"></script>
<script type="text/javascript" src="../../../../work/public/xloadtree/js/xloadtree2.js"></script>
<script type="text/javascript" src="../../../../work/public/xloadtree/js/xmlextras.js"></script>

<link type="text/css" rel="stylesheet" href="../../../../../public/css/tree.css"/>

<script type="text/javascript">
	webFXTreeConfig.rootIcon='../../../../work/public/xloadtree/images/foldericon.png',
	webFXTreeConfig.openRootIcon='../../../../work/public/xloadtree/images/openfoldericon.png',
	webFXTreeConfig.folderIcon='../../../../work/public/xloadtree/images/foldericon.png',
	webFXTreeConfig.openFolderIcon='../../../../work/public/xloadtree/images/openfoldericon.png',
	webFXTreeConfig.fileIcon='../../../../work/public/xloadtree/images/file.png',
	webFXTreeConfig.iIcon='../../../../work/public/xloadtree/images/I.png',
	webFXTreeConfig.lIcon='../../../../work/public/xloadtree/images/L.png',
	webFXTreeConfig.lMinusIcon='../../../../work/public/xloadtree/images/Lminus.png',
	webFXTreeConfig.lPlusIcon='../../../../work/public/xloadtree/images/Lplus.png',
	webFXTreeConfig.tIcon='../../../../work/public/xloadtree/images/T.png',
	webFXTreeConfig.tMinusIcon='../../../../work/public/xloadtree/images/Tminus.png',
	webFXTreeConfig.tPlusIcon='../../../../work/public/xloadtree/images/Tplus.png',
	webFXTreeConfig.blankIcon='../../../../work/public/xloadtree/images/blank.png';
	webFXTreeConfig.loadingIcon ='../../../../work/public/xloadtree/images/loading.gif';
</script>

</head>
<body bgcolor="#FFFFFF">
<table style="width:100%;height:100%" border="0" cellspacing="0" cellpadding="0" >
 <tr> 
    <td width="25%" height="100%" valign="top"> 
    	<div id="treeContainer" style="width:100%;height:100%;overflow:auto;">    	
			<script type="text/javascript">
				var str= "QhztcxService.initTree.do?XZQH_DM=<%=xzqh_dm%>";
				tree = new WebFXLoadTree('<%=xzqh_mc%>', str);
				tree.createTree("treeContainer");
				tree.setKey("<%=xzqh_dm%>");
				tree.setAction("javascript:subQuery('<%=xzqh_dm%>')");
				tree.setExpanded(true);
			</script>
			</div>			
    </td>
    <td rowspan=2 width="100%" valign="top">
    	<iframe name="infoarea1" src="QhztcxService.queryXjXzqh.do?value(PAGESIZE)=20" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
	</td>
  </tr>
</table>
</body>
</html>

<script language="javascript">
 	var mljdxl = null ;
	function subQuery(xl){
		mljdxl = xl ;
		window.open("QhztcxService.queryXjXzqh.do?value(XZQH_DM)="+xl+"&value(PAGESIZE)=20","infoarea1") ;
	}		
</script>