<%@ include file="../../../work/public/head.jsp" %>

<HTML>
<HEAD>
<TITLE>变更对照管理</TITLE>
</HEAD>
<BODY>
	<logic:present name="DZBLIST">
		<display:table name="DZBLIST" id="dzbMap" align="center"  pagesize="10"  decorator="demo.DemoWrapper"  class="tab"  requestURI="BgdzglService.queryDzb.do">
		<display:column style="width:9%" property="SBXZQH_DM" title="区划代码" sortable="true"/>
		<display:column style="width:10%" property="SQDMC" title="变更对照表名称" />
		<display:column style="width:9%" property="GROUPMC" title="调整说明" />
		<display:column style="width:9%" property="YSXZQH_DM" title="原区划代码" sortable="true"/>
		<display:column style="width:9%" property="YSXZQH_MC" title="原区划名称" />	
		<display:column style="width:9%" property="BGLX_DM" title="调整类型" />	
		<display:column style="width:9%" property="MBXZQH_DM" title="现区划代码" sortable="true"/>	
		<display:column style="width:9%" property="MBXZQH_MC" title="现区划名称" />	
		<display:column style="width:9%" property="CLZT_DM" title="处理状态" />	
		<display:column style="width:9%" property="CLJG" title="处理结果" />	
		<display:column style="width:9%" property="LRSJ" title="提交时间"  dateFormat="yyyy-MM-dd"/>							
		</display:table>
	</logic:present>
	<logic:notPresent name="DZBLIST">
	<display:table name="DZBLIST" align="center" id="dzbMap" decorator="demo.DemoWrapper" requestURI="BgdzglService.queryDzb.do"  pagesize="10" class="tab">
		<display:column style="width:9%"  title="区划代码" sortable="true"/>
		<display:column style="width:10%" title="变更对照表名称" />
		<display:column style="width:9%"  title="调整说明" />
		<display:column style="width:9%"  title="原区划代码" sortable="true"/>
		<display:column style="width:9%"  title="原区划名称" />	
		<display:column style="width:9%"  title="调整类型" />	
		<display:column style="width:9%"  title="现区划代码" sortable="true"/>	
		<display:column style="width:9%"  title="现区划名称" />	
		<display:column style="width:9%"  title="处理状态" />	
		<display:column style="width:9%"  title="处理结果" />	
		<display:column style="width:9%"  title="提交时间"  dateFormat="yyyy-MM-dd"/>	
		</display:table>
	</logic:notPresent>

</body>
</html>