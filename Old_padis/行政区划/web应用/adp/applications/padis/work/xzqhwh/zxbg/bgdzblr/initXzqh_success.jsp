<%@ include file="../../../../work/public/head.jsp" %>
<%@ page import="com.padis.business.xzqhwh.common.XzqhManager"%><%	
	XzqhManager mgr = new XzqhManager();
	mgr.setDb("V_DM_XZQH_YLSJ");
	String xzqhdm = (String)map.get("XZQH_DM");
	String dwlsgxStr = (String)map.get("DWLSGX_DM");
	String xzqhjb_dm = (String)map.get("XZQHJB_DM");
	String jc_dm = (String)map.get("JC_DM");
	String czry_dm = (String)map.get("CZRY_DM");
	String sqdxh = (String)map.get("SQDXH");
	String sqdztmc = (String)map.get("SQDZT_DM");
	String sqdztdm = (String)map.get("SQDZTDM");
	int dwlsgx=10;
	if(xzqhdm==null){
		xzqhdm="";
	}
	if(dwlsgxStr!=null){
		dwlsgx = Integer.parseInt(dwlsgxStr);
	}
	if(xzqhjb_dm==null){
		xzqhjb_dm = "";
	}
	if(jc_dm==null){
		jc_dm = "1";
	}
	if(czry_dm==null){
		czry_dm = "";
	}
	if(sqdxh==null){
		sqdxh = "";
	}
	if(sqdztmc==null){
		sqdztmc = "";
	}
	if(sqdztdm==null){
		sqdztdm = "";
	}
%>
<HTML>
<HEAD>
<TITLE>区划变更维护</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script language="javascript" src="../../../../work/public/js/common.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/js/controlService.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../../console/public/js/service.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/grid/js/grid.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../../work/public/js/check.js"></SCRIPT>

<!--业务要使用的JS文件-->
<SCRIPT LANGUAGE="JavaScript" src="./js/xzqhbgdzb_index.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="./js/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="./js/checkStandard.js"></SCRIPT>
</HEAD>
<BODY onload="init('<%=jc_dm%>','<%=sqdztdm%>','<%=czry_dm%>')"> 
<form name="forForm" action="BgdzblrService.addXzqhbgsqb.do" method="post">
	<input type="hidden" name="CZRY_DM" value="<%=czry_dm%>"/>
	<input type="hidden" name="JB_DM" value="<%=jc_dm%>"/>
	<input type="hidden" name="SQDZT_DM" value="<%=sqdztdm%>"/>
	<input type="hidden" name="JC_DM" value="<%=jc_dm%>"/>
	<input type="hidden" name="XZQHJB_DM" value="<%=xzqhjb_dm%>"/>
	<input type="hidden" name="SQDXH" value="<%=sqdxh%>"/>
	<input type="hidden" name="XZQHJC" value=""/>
	<table>
		<tr align="center">
			<td width="100%">变更对照表状态：<%=sqdztmc%>
			&nbsp;&nbsp;<font color="red">变更对照表状态为“未提交”和“审批不通过”下，才能够录入变更明细！</font>
			</td>
		</tr>
	</table>
	<fieldset style="margin-top:-10px;"><legend>区划变更内容</legend>
	<table>
	<tr>
		<td width="16%"><select id="XZQH_SHENG" size="10" style="width:120px;height:160px;" onclick="getXzqhMcDm('XZQH_SHENG','1')" onchange="getXjXzqh(this,'')"><%=mgr.getShengOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="16%"><select id="XZQH_SHI" size="10" style="width:120px;height:160px;" onclick="getXzqhMcDm('XZQH_SHI','2')" onchange="getXjXzqh(this,'')"><%=mgr.getShiOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="XZQH_XIAN" size="10" style="width:120px;height:160px;" onclick="getXzqhMcDm('XZQH_XIAN','3')" onchange="getXjXzqh(this,'')"><%=mgr.getXianOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="XZQH_XIANG" size="10" style="width:120px;height:160px;" onclick="getXzqhMcDm('XZQH_XIANG','4')" onchange="getXjXzqh(this,'')"><%=mgr.getXiangOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="XZQH_CUN" size="10" style="width:120px;height:160px;" onclick="getXzqhMcDm('XZQH_CUN','5')" onchange="getXjXzqh(this,'')" 
		><%=mgr.getCunOptions(xzqhdm,dwlsgx,null,true)%></select></td>
		<td width="17%"><select id="XZQH_ZU" size="10" style="width:120px;height:160px;" onclick="getXzqhMcDm('XZQH_ZU','6')" ><%=mgr.getZuOptions(xzqhdm,dwlsgx,null,true)%></select></td>
       </tr>
	  </table>
	  </fieldset>
<div id="div2">
	  <table>
		<tr>
		<td align="right" width="14%">调整说明&nbsp;&nbsp;</td>
		<td width="36%">
		<INPUT style="width:100%" type="text" id="SQBMC" name="SQBMC" maxlength="30">
		</td>
		<!--<td align="right" width="14%" id="addTd">
		<div style="display:none;" id="checkDiv"><INPUT type="checkbox" class="checkbox" id="RINGFLAG" name="RINGFLAG" value="0" onclick="changeSelect()">&nbsp;&nbsp;环状变更&nbsp;&nbsp;</div>调整类型<font color="red">*</font></td>
		<td width="36%" id="selectTd">		
		<dict:select table_name="V_DM_XZQH_BGLX" colid="BGLX_DM"  property="BGLX_DM" nullOption="true" nullLabel="=请选择类型=" onclick="xstp()" onchange="getXzqhMcDm('','')"/>
		</td>-->
		<td align="right" width="14%" id="addTd" >
		<div style="display:none;" id="checkDiv"><INPUT type="checkbox" class="checkbox" id="RINGFLAG" name="RINGFLAG" value="0" onclick="changeSelect()">&nbsp;&nbsp;重用变更&nbsp;&nbsp;</div></td>
		<td width="36%" id="selectTd">
		调整类型<font color="red">*</font>
		<dict:select table_name="V_DM_XZQH_BGLX" colid="BGLX_DM"  property="BGLX_DM" nullOption="true" nullLabel="=请选择类型=" onclick="xstp()" onchange="getXzqhMcDm('','')" style="width:78%"/>
		</td>
		</tr>
		<tr>
			<td align="right" width="14%">原区划代码<font color="red">*</font></td>
			<td width="36%" id="XZQHDM">
			<input type="hidden" name="YSXZQH_DM" value=""/>
			<INPUT style="width:15%" type="text" id="QSJ_XZQH_DM" name="QSJ_XZQH_DM" disabled="true" maxlength="2">
			<INPUT style="width:15%" type="text" id="QDSJ_XZQH_DM" name="QDSJ_XZQH_DM" disabled="true" maxlength="2">
			<INPUT style="width:15%" type="text" id="QXJ_XZQH_DM" name="QXJ_XZQH_DM" disabled="true" maxlength="2">
			<INPUT style="width:15%" type="text" id="QXZJ_XZQH_DM" name="QXZJ_XZQH_DM" disabled="true" maxlength="3">
			<INPUT style="width:16%" type="text" id="QCJ_XZQH_DM" name="QCJ_XZQH_DM" disabled="true" maxlength="3">
			<INPUT style="width:15%" type="text" id="QZ_XZQH_DM" name="QZ_XZQH_DM" disabled="true" maxlength="3">
			</td>
			<td align="right" width="14%">现区划代码<font color="red">*</font></td>
			<input type="hidden" name="MBXZQH_DM" value=""/>		
			<td width="36%">
			<INPUT style="width:14%" type="text" id="SJ_XZQH_DM" name="SJ_XZQH_DM" disabled="true" maxlength="2">
			<INPUT style="width:14%" type="text" id="DSJ_XZQH_DM" name="DSJ_XZQH_DM" disabled="true" maxlength="2" onclick="getSjxzqh('1')" onchange="getXzqhMc()">
			<INPUT style="width:14%" type="text" id="XJ_XZQH_DM" name="XJ_XZQH_DM" disabled="true" maxlength="2" onclick="getSjxzqh('2')" onchange="getXzqhMc()">
			<INPUT style="width:14%" type="text" id="XZJ_XZQH_DM" name="XZJ_XZQH_DM" disabled="true" maxlength="3" onclick="getSjxzqh('3')" onchange="getXzqhMc()">
			<INPUT style="width:14%" type="text" id="CJ_XZQH_DM" name="CJ_XZQH_DM" disabled="true" maxlength="3" onclick="getSjxzqh('4')" onchange="getXzqhMc()">
			<INPUT style="width:14%" type="text" id="Z_XZQH_DM" name="Z_XZQH_DM" disabled="true" maxlength="3" onclick="getSjxzqh('5')" onchange="getXzqhMc()">
			<img id="image1" src="../../../../public/images/ex.gif" width="16px" onclick = "selectSjxzqh();" style="display:none"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="14%">原区划名称<font color="red">*</font></td>
			<td width="36%">
			<INPUT style="width:100%" type="text" id="YSXZQH_MC" name="YSXZQH_MC" disabled="true" maxlength="80"></td>
			<td align="right" width="14%">现区划名称<font color="red">*</font></td>
			<td width="36%">
			<INPUT style="width:100%" type="text" id="MBXZQH_MC" name="MBXZQH_MC" maxlength="80">
			</td>
		</tr>
		<tr>
			<td align="right" width="14%">备注--------&nbsp;&nbsp;</td>
			<td width="36%">
			<INPUT style="width:100%" type="text" id="BZ" name="BZ" maxlength="500"></td>
			<td colspan="2" align="center" id="msgTd"></td>
		</tr>
	</table>
	<center>
		<button onClick="addXzqhsqb();" accesskey="a">添加明细(<U>A</U>)</button>
		<button accesskey="w" id="btn_delete" onclick="removeRow();">删 除(<U>W</U>)</button>
	</center>
	<div id="div1">
		<div id="xzqh" height="100%" width="100%">
		</div>				
		 <center>
			<button id="btn_save" accesskey="s" onclick="saveXzqh()">保 存(<u>S</u>)</button>
		</center>
	</div>
</div>
</form>
</BODY>
</HTML>