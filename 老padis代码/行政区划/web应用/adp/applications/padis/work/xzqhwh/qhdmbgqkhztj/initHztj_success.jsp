<%@	include	file="../../../work/public/head.jsp" %>
<%
	List hzmcList = (List)request.getAttribute("HZMCLIST");
	String hzsjq ="";
	String hzsjz = "";
	String bchzmc = "";
	if(map!=null){
		hzsjq = (String)map.get("HZSJQ");
		hzsjz = (String)map.get("HZSJZ");
		bchzmc = (String)map.get("HZMC");
	}
	if(hzsjq==null){
		hzsjq = "";
	}
	if(hzsjz==null){
		hzsjz = "";
	}
	if(bchzmc==null){
		bchzmc = "";
	}
%>
<html>
	<head>
		<title>区划变更情况后台统计汇总</title>
		<script type="text/javascript" src="./js/index.js"></script>
		<meta http-equiv="Content-Type"	content="text/xml; charset=GBK">
	</head>
	<body>
	<FIELDSET>
	<center>
		本次汇总名称：<font color="red">*</font><input type="text" style="width:30%" name="HZMC" maxlength="25"  value="<%=bchzmc%>"/>
	<center>
		<table width="100%" align="center">
			<tr>
				<td align="right" width="14%">汇总时间起：</td>
				<td width="36%"><date:input style="width:93%" name="map" property='HZSJQ' valueKey="HZSJQ" title='发表日期' maxlength='14'  calendarDir='/public'/>
				</td>
				<td align="right" width="14%">汇总时间止：</td>
				<td width="36%"><date:input style="width:93%" name="map" property='HZSJZ' valueKey="HZSJZ" title='发表日期' maxlength='14'  calendarDir='/public'/>
				</td>
			</tr>
			<tr>
				<td align="right" width="14%">汇总条件：</td>
				<td width="36%">
					<input type="checkbox" name="HZTJ" class="checkbox" value="1"/>区划级别
					<input type="checkbox" name="HZTJ" class="checkbox" value="2"/>变更类型
				</td>
				<td align="right" width="14%">已有汇总名称：</td>
				<td width="36%">
					<select name="YYHZMC">
						<option value="">=====请选择数据=====</option>
						<%if(hzmcList!=null){
							for(int i=0; i<hzmcList.size();i++){
								Map hzmcMap = (Map)hzmcList.get(i);
								String hzmc = (String)hzmcMap.get("HZMC");
								%>
								
								<option value="<%=hzmc%>"><%=hzmc%></option>
							<%}%>
						<%}%>
					</select>
				</td>
			</tr>
		</table>
		<center>
			<button class="btn-big" accesskey="q" onClick="btn_sub_query()">汇 总(<u>Q</u>)</button>
			<button class="btn-big" accesskey="w" onClick="btn_sub_delete()">删 除(<u>W</u>)</button>
			<button class="btn-big" accesskey="r" onClick="btn_sub_reset()">重 置(<u>R</u>)</button>
		</center>
	</FIELDSET>
	</body>
</html>

