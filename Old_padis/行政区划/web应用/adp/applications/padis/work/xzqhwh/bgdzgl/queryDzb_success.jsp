<%@ include file="../../../work/public/head.jsp" %>

<HTML>
<HEAD>
<TITLE>������չ���</TITLE>
</HEAD>
<BODY>
	<logic:present name="DZBLIST">
		<display:table name="DZBLIST" id="dzbMap" align="center"  pagesize="10"  decorator="demo.DemoWrapper"  class="tab"  requestURI="BgdzglService.queryDzb.do">
		<display:column style="width:9%" property="SBXZQH_DM" title="��������" sortable="true"/>
		<display:column style="width:10%" property="SQDMC" title="������ձ�����" />
		<display:column style="width:9%" property="GROUPMC" title="����˵��" />
		<display:column style="width:9%" property="YSXZQH_DM" title="ԭ��������" sortable="true"/>
		<display:column style="width:9%" property="YSXZQH_MC" title="ԭ��������" />	
		<display:column style="width:9%" property="BGLX_DM" title="��������" />	
		<display:column style="width:9%" property="MBXZQH_DM" title="����������" sortable="true"/>	
		<display:column style="width:9%" property="MBXZQH_MC" title="����������" />	
		<display:column style="width:9%" property="CLZT_DM" title="����״̬" />	
		<display:column style="width:9%" property="CLJG" title="������" />	
		<display:column style="width:9%" property="LRSJ" title="�ύʱ��"  dateFormat="yyyy-MM-dd"/>							
		</display:table>
	</logic:present>
	<logic:notPresent name="DZBLIST">
	<display:table name="DZBLIST" align="center" id="dzbMap" decorator="demo.DemoWrapper" requestURI="BgdzglService.queryDzb.do"  pagesize="10" class="tab">
		<display:column style="width:9%"  title="��������" sortable="true"/>
		<display:column style="width:10%" title="������ձ�����" />
		<display:column style="width:9%"  title="����˵��" />
		<display:column style="width:9%"  title="ԭ��������" sortable="true"/>
		<display:column style="width:9%"  title="ԭ��������" />	
		<display:column style="width:9%"  title="��������" />	
		<display:column style="width:9%"  title="����������" sortable="true"/>	
		<display:column style="width:9%"  title="����������" />	
		<display:column style="width:9%"  title="����״̬" />	
		<display:column style="width:9%"  title="������" />	
		<display:column style="width:9%"  title="�ύʱ��"  dateFormat="yyyy-MM-dd"/>	
		</display:table>
	</logic:notPresent>

</body>
</html>