<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>查询集中变更数据包</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="./js/bgdzcx.js"></script>
</HEAD>
<BODY>
<form action="" id="form1" method="POST">
<fieldset style="margin-top:-10px;"><legend>上传文件信息</legend>
			<logic:present name="ZIPLIST">
				<display:table name="ZIPLIST" id="rzMap" align="center"  pagesize="10"  decorator="demo.DemoWrapper"  class="tab"  requestURI="BgdzcxService.queryZip.do">
				<display:column title="选择" style="width:5%" align="center" >
				<input type="radio" id="zipxh_radio"  name="zipxh_radio"  value='<bean:write name="rzMap" property="ZIPXH"/>'/>
				</display:column>
				<display:column style="width:8%" property="XZQH_DM" title="区划代码" sortable="true"/>
				<display:column style="width:10%" property="RQ" title="日期" sortable="true"/>
				<display:column style="width:25%" property="WJM" title="文件名" />
				<display:column style="width:15%" property="JZBGZT_DM" title="状态" />
				<display:column style="width:19%" property="BZ" title="备注"/>
				<display:column style="width:18%" property="LRSJ" title="上传时间" sortable="true"/>	
				</display:table>
			</logic:present>
			<logic:notPresent name="ZIPLIST">
			<display:table name="ZIPLIST" align="center" id="rzMap" decorator="demo.DemoWrapper" requestURI="BgdzcxService.queryZip.do"  pagesize="10" class="tab">
				<display:column style="width:5%" title="选择" />
				<display:column style="width:8%" title="区划代码"/>
				<display:column style="width:10%" title="日期"/>
				<display:column style="width:25%" title="文件名"/>
				<display:column style="width:15%" title="状态"/>
				<display:column style="width:19%" title="备注"/>				
				<display:column style="width:18%" title="上传时间"/>	
				</display:table>
			</logic:notPresent>
		<!--<input type="hidden" id ="BGXZQH_DM" value=""/>-->
	<table>
		<tr>
			<td width="12%" align="right">变更区划代码：</td>
			<td width="12%" align="left" >
			<input  type="text" id="BGXZQH_DM" name="BGXZQH_DM" value="" maxlength="15"/>
			</td>
			<!--<td width="4%" align="left" >
			<img src="../../../../public/images/ex.gif"  onclick = "javascript:getOptionTree('../../../../common/commonframe/commonframe.do?value(XT_TYKJ_KJMC)=XZQH&value(CODE)=<%=(String)map.get("JB_DM")%>&value(INCLUDE)=Y&inputKey=BGXZQH_DM&inputName=BGXZQH_MC&value(CONDITION)=JCDM IN (%271%27,%272%27,%273%27)')"/>
			</td>-->
			<td width="8%" align="right">错误标志：</td>
			<td width="12%" align="left">
			<select name="CWSJBZ">
				<option value="" selected>===请选择===</option>
				<option value="Y">错误数据</option>
				<option value="N">正确数据</option>
			</select>
			</td>
			<td width="10%" align="right">调整类型：</td>
			<td width="12%" align="left">
			<dict:select table_name="V_DM_XZQH_BGLX" colid="BGLX_DM"  property="BGLX_DM" nullOption="true" nullLabel="===请选择==="/>
			</td>
			<td width="10%" align="center"><button id="btn_query" class="btn-big" accesskey="d"  onclick="queryDzb()">查询明细(<u>D</u>)</button></td>
		</tr>
	</table>
	</fieldset>
</form>
</body>
</html>