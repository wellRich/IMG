<%@ include file="../../../../work/public/head.jsp" %>
<HTML >
<HEAD>
<TITLE>差异数据比较</TITLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="./js/cysjbj.js"></script>
</HEAD>
<BODY>
<form method="POST" id="form1" action= "">
<fieldset style="margin-top:-10px;"><legend>上传文件信息</legend>
			<logic:present name="ZIPLIST">
				<display:table name="ZIPLIST" id="rzMap" align="center"  pagesize="10"  decorator="demo.DemoWrapper"  class="tab"  requestURI="CysjbjService.queryZip.do">
				<display:column title="选择" style="width:5%;" align="center" >
				<input type="radio" id="zipxh_radio"  name="zipxh_radio"  value='<bean:write name="rzMap" property="ZIPXH"/>||<bean:write name="rzMap" property="JZBGZT"/>'/>
				</display:column>
				<display:column style="width:8%" property="XZQH_DM" title="区划代码" sortable="true"/>
				<display:column style="width:10%" property="RQ" title="日期" sortable="true"/>
				<display:column style="width:25%" property="WJM" title="文件名"/>
				<display:column style="width:15%" property="JZBGZT_DM" title="状态" />
				<display:column style="width:19%" property="BZ" title="备注"/>
				<display:column style="width:18%" property="LRSJ" title="上传时间" sortable="true"/>	
				</display:table>
			</logic:present>
			<logic:notPresent name="ZIPLIST">
			<display:table name="ZIPLIST" align="center" id="rzMap" decorator="demo.DemoWrapper" requestURI="CysjbjService.queryZip.do"  pagesize="10" class="tab">
				<display:column style="width:5%" title="选择" />
				<display:column style="width:8%" title="区划代码"/>
				<display:column style="width:10%" title="日期"/>
				<display:column style="width:25%" title="文件名"/>
				<display:column style="width:15%" title="状态"/>
				<display:column style="width:19%"  title="备注"/>
				<display:column style="width:18%" title="上传时间"/>	
				</display:table>
			</logic:notPresent>
		<input type="hidden" id ="BGXZQH_DM" value=""/>
	<table>
		<tr>
			<td width="50%"></td>
			<td width="10%" align="center"><button class="btn-big" id="btn_query" accesskey="d"  onclick="queryDif()">差异数据比较(<u>D</u>)</button></td>
			<td width="10%" align="right">差异类型：</td>
			<td width="20%" align="left">
			<select name="CYLX">
				<option value="1">代码一致名称不一致</option>
				<option value="2">完全一样</option>
				<option value="3">省级存在PADIS不存在</option>
				<option value="4">省级不存在PADIS存在</option>
			</select>
			</td>
			<td width="10%"></td>
		</tr>
	</table>
	</fieldset>
</form>
</body>
</html>