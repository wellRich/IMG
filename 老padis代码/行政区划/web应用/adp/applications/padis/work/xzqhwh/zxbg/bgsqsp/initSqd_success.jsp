<%@	include file="../../../../work/public/head.jsp"%>
<html>
	<HEAD>
		<TITLE>������ձ�����</TITLE>
		<script type="text/javascript" src="./js/bgsqsp.js"></script>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
	<body>
		<form method="POST" name="form1" action="">
		
			<logic:present name="SQDLIST">
				<display:table name="SQDLIST" id="sqdMap" align="center"  class="tab"
					pagesize="8" requestURI="BgsqspService.initSqd.do">
					<display:column title="<input type='checkbox' class='checkbox' id='all_checked' name='all_checked' onclick='selectAll()'/>ȫѡ" style="width:8%" align="center">
						<input type="checkbox" id="sqdxh_check"  name="sqdxh_check" value='<bean:write name="sqdMap" property="SQDXH"/>' />
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
				 requestURI="BgsqspService.initSqd.do"
					pagesize="8" class="tab">
					<display:column style="width:8%" property="" title="ѡ��"/>
					<display:column style="width:16%" property="" title="��������" />
					<display:column style="width:15%" property="" title="��������" />
					<display:column style="width:15%" property="" title="������ձ�����"/>
					<display:column style="width:16%"  property="" title="������ձ�״̬"/>
					<display:column style="width:15%"  property="" title="��ע" />
					<display:column style="width:15%"  property="" title="¼��ʱ��" dateFormat="yyyy-MM-dd" />
				</display:table>
			</logic:notPresent>
		 <center>
		 	<button  class="btn-big" id="btn_query" accesskey="q"  onclick="queryMxb()">�鿴��ϸ(<u>Q</u>)</button>
		 	<button  class="btn-big" id="btn_yes" accesskey="y"  onclick="bgsqsp('Y')">���ͨ��(<u>Y</u>)</button>
		 	<button  class="btn-big" id="btn_no" accesskey="n"  onclick="bgsqsp('N')">��˲�ͨ��(<u>N</u>)</button>
			<button id="btn_delete" accesskey="e"  onclick="expSqd()">������ӡ(<u>E</u>)</button>
		</center>
		</form>
		<hr></hr>
		<iframe src="BgsqspService.queryMxb.do" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" frameborder="0" ></iframe>
	</body>
</html>

