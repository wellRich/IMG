<%@	include file="../../../../work/public/head.jsp"%>
<html>
	<HEAD>
		<TITLE>����ʵʱ���</TITLE>
		<script type="text/javascript" src="./js/gjssbg.js"></script>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
	<body>
		<form method="POST" name="form1" action="">
		
			<logic:present name="SQDLIST">
				<display:table name="SQDLIST" id="sqdMap" align="center"  class="tab"
					pagesize="8" requestURI="GjssbgService.initSqd.do">
					<display:column title="<input type='checkbox' class='checkbox' id='all_checked' name='all_checked' onclick='selectAll()'/>ȫѡ" style="width:8%;" align="center">
						<input type="checkbox" id="sqdxh_check"  name="sqdxh_check" value='<bean:write name="sqdMap" property="SQDXH"/>' />
					</display:column>
					<display:column style="width:15%"  title="����" >
						<bean:write name="sqdMap" property="SBXZQH_DM"/>(<bean:write name="sqdMap" property="SBXZQH"/>)
					</display:column>
					<display:column style="width:28%" property="SQDMC" title="������ձ�����" sortable="true"  />
					<display:column style="width:15%" property="SQDZT_DM" title="������ձ�״̬" />
					<display:column style="width:34%" property="BZ" title="��ע" />
				</display:table>
			</logic:present>
			
			<logic:notPresent name="SQDLIST">
				<display:table name="SQDLIST" align="center" id="sqdMap"
				 requestURI="GjssbgService.initSqd.do"
					pagesize="8" class="tab">
					<display:column style="width:6%" property="" title="ѡ��"/>
					<display:column style="width:15%" property="" title="����" />
					<display:column style="width:28%" property="" title="������ձ�����"/>
					<display:column style="width:15%"  property="" title="������ձ�״̬"/>
					<display:column style="width:36%"  property="" title="��ע" />
				</display:table>
			</logic:notPresent>
		 <center>
		 	<button class="btn-big" id="btn_query" accesskey="q"  onclick="queryMxb()">�鿴��ϸ(<u>Q</u>)</button>
		 	<button class="btn-big" id="btn_yes" accesskey="y"  onclick="gjshtg()">�������ͨ��(<u>Y</u>)</button>
		</center>
		</form>
		<hr></hr>
		<iframe src="GjssbgService.queryMxb.do" id="iframe1" name="iframe1" width="100%" onload="this.height=iframe1.document.body.scrollHeight" frameborder="0" ></iframe>
	</body>
</html>

