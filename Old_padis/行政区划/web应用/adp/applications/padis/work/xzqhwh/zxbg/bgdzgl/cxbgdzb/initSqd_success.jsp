<%@	include file="../../../../../work/public/head.jsp"%>
<html>
	<HEAD>
		<TITLE>����������ձ�</TITLE>
		<script type="text/javascript" src="./js/sqdwh.js"></script>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
	<body>
		<form method="POST" name="form1" action="">
		
			<logic:present name="SQDLIST">
				<display:table name="SQDLIST" id="sqdMap" align="center"
					pagesize="8"  class="tab"
					requestURI="CxbgdzbService.initSqd.do">
					<display:column title="ѡ��" style="width:4%" align="center">
						<input type="radio" id="sqdxh_radio"  name="sqdxh_radio" value='<bean:write name="sqdMap" property="SQDXH"/>||<bean:write name="sqdMap" property="SQDZTDM"/>||<bean:write name="sqdMap" property="SBXZQH_DM"/>||<bean:write name="sqdMap" property="SQDMC"/>||<bean:write name="sqdMap" property="BZ"/>' />
					</display:column>
					<display:column style="width:13%" property="SBXZQH_DM" title="��������" />
					<display:column style="width:13%"  property="SBXZQH_MC"title="��������" />			
					<display:column style="width:16%" property="SQDMC" title="������ձ�����"  />
					<display:column style="width:15%" property="SQDZT_DM" title="������ձ�״̬" />
					<display:column style="width:12%" property="BZ" title="��ע" />
					<display:column style="width:12%" property="SPYJ" title="�������" />
					<display:column style="width:15%" property="LRSJ" title="¼��ʱ��" dateFormat="yyyy-MM-dd" />
				</display:table>
			</logic:present>
			
			<logic:notPresent name="SQDLIST">
				<display:table name="SQDLIST" align="center" id="sqdMap"
				 requestURI="CxbgdzbService.initSqd.do"
					pagesize="8" class="tab">
					<display:column style="width:4%" property="" title="ѡ��"/>
					<display:column style="width:13%" property="" title="��������" />
					<display:column style="width:13%" property="" title="��������" />
					<display:column style="width:16%" property="" title="������ձ�����"/>
					<display:column style="width:15%"  property="" title="������ձ�״̬"/>
					<display:column style="width:12%"  property="" title="��ע" />
					<display:column style="width:12%"  property="" title="�������" />
					<display:column style="width:15%" property="" title="¼��ʱ��" dateFormat="yyyy-MM-dd" />
				</display:table>
			</logic:notPresent>
		 <center>
			 <button id="btn_delete" accesskey="c"  onclick="revokeSqd()">�� ��(<u>C</u>)</button>
			 <button id="btn_reload" accesskey="r"  onclick="JavaScript:window.location.reload();">ˢ ��(<u>R</u>)</button>
		</center>
		</form>
	</body>
</html>

