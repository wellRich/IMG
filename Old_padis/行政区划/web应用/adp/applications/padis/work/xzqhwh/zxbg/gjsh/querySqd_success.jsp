<%@ include file="../../../../work/public/head.jsp" %>
<SCRIPT LANGUAGE="JavaScript" src="../../../../console/public/js/tab.js"></SCRIPT>
	<script language="javascript" src="../../../../work/public/js/check.js"></script>
	<script language="javascript" src="../../../../work/public/js/controlService.js"></script>
	<!--������ϢҪʹ�õ�JS�ļ�-->
	<script language="javascript" src="../../../../work/public/js/common.js"></script>
<html>
	<head>
		<title>�������</title>
		<script type="text/javascript" src="./js/gjsh.js"></script>
		<meta http-equiv="Content-Type"	content="text/xml; charset=GBK">
	</head>
	<body >
			<logic:present name="SQDLIST">
				<display:table name="SQDLIST" id="sqdMap" align="center"  class="tab"
					pagesize="8" requestURI="GjshService.querySqd.do">
					<display:column title="<input type='checkbox' class='checkbox' id='all_checked' name='all_checked' onclick='selectAll()'/>ȫѡ" style="width:8%" align="center">
						<input type="checkbox" id="sqdxh_check"  name="sqdxh_check"  value='<bean:write name="sqdMap" property="SQDXH"/>||<bean:write name="sqdMap" property="SQDZTDM"/>' />
					</display:column>
					<display:column style="width:16%" property="SBXZQH_DM" title="��������" />
					<display:column style="width:15%" property="SBXZQH_MC" title="��������" />
					<display:column style="width:15%" property="SQDMC" title="������ձ�����" sortable="true"  />
					<display:column style="width:16%" property="SQDZT_DM" title="������ձ�״̬" />
					<display:column style="width:15%" property="BZ" title="��ע" />
					<display:column style="width:15%" property="LRSJ" title="¼��ʱ��" dateFormat="yyyy-MM-dd" />
				</display:table>
			</logic:present>
			<logic:notPresent name="SQDLIST">
				<display:table name="SQDLIST" align="center" id="sqdMap"
				 requestURI="GjshService.querySqd.do"
					pagesize="8" class="tab">
				<display:column style="width:8%;" property="" title="ѡ��"/>
					<display:column style="width:16%" property="" title="��������" />
					<display:column style="width:15%" property="" title="��������" />
					<display:column style="width:15%" property="" title="������ձ�����"/>
					<display:column style="width:16%"  property="" title="������ձ�״̬"/>
					<display:column style="width:15%"  property="" title="��ע" />
					<display:column style="width:15%"  property="" title="¼��ʱ��" dateFormat="yyyy-MM-dd" />
				</display:table>
			</logic:notPresent>
		 <center>
		 	<button id="btn_back" accesskey="b"  onclick="back()">�� ��(<u>B</u>)</button>
			<button  id="btn_queren"  class="btn-big" accesskey="q"  onclick="bgsqqr()">ȷ ��(<u>Q</u>)</button>
			<button id="btn_refur" accesskey="n"  onclick="javascript:window.location.reload()">ˢ ��(<u>N</u>)</button>
		</center>
	</body>
</html>