<%@	include file="../../../../work/public/head.jsp"%>
<%
	String count = String.valueOf(map.get("COUNT"));
%>
<html>
	<HEAD>
		<TITLE>�����ȷ��</TITLE>
		<script type="text/javascript" src="./js/bgsqqr.js"></script>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
	<body>
		<form method="POST" name="form1" action="">
			<logic:present name="SQDLIST">
				<display:table name="SQDLIST" id="sqdMap" align="center"  class="tab"
					 requestURI="BgsqqrService.initSqd.do">
					<display:column title="<input type='checkbox' class='checkbox' id='all_checked' name='all_checked' onclick='selectAll()'/>ȫѡ" style="width:8%" align="center">
						<input type="checkbox" id="sqdxh_check"  name="sqdxh_check" value='<bean:write name="sqdMap" property="SQDXH"/>||<bean:write name="sqdMap" property="SQDZTDM"/>' class="checkbox"/>
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
				 requestURI="BgsqqrService.initSqd.do"
					 class="tab">
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
		 	<button id="btn_yes" accesskey="y"  onclick="bgsqqr('<%=count%>')">ȫʡȷ��(<u>Y</u>)</button>
		 	<button id="btn_refur" accesskey="n"  onclick="javascript:window.location.reload()">ˢ ��(<u>N</u>)</button>
		</center>
		</form>
		<hr></hr>
				<iframe src="BgsqqrService.queryWtj.do?PAGESIZE=10" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" frameborder="0" ></iframe>
	</body>
</html>

