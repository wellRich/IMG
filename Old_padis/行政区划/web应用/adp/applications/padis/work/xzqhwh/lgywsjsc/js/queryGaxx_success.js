/**
 *��ȡ�û��Ѿ�ѡ����Ŀ��ֵ��
 *@param 
 *@return  �Ѿ�ѡ�е��ÿ����Ŀ��ֵ֮����","�ֿ���
*/
function getSelect()
{
 var gwdmArray=document.getElementsByName("RKDZDAH");
 var retGwdm="";
 var count=0;
 
 for(var i=0;i<gwdmArray.length;i++)
 {
 	if(gwdmArray[i].checked==true)
 	{
 		if(count==0)
 		{
 			retGwdm=gwdmArray[i].value;
 			count=1;
 		}
 		else
 		{
 			retGwdm=retGwdm+","+gwdmArray[i].value;
 		}
 	}
 }
 return retGwdm;
}


function subDel()
{	
	var dmArray = getSelect();
	if(dmArray == "")
		{
			showMessage("��ѡ��Ҫɾ���ĸ�����");
			return false;
		}
	if(window.confirm("ȷ��Ҫɾ��ѡ��ĸ�����"))
		{
			var service = new Service("com.padis.business.xzqhwh.lgywsjsc.LgywsjscService.deleteGaxx") ; //���������·��
			var paramXml = "<ROOT><map><RKDZDAH_LIST>"+dmArray+"</RKDZDAH_LIST></map></ROOT>";
			var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
			var code = service.getCode();  //���ؽ����״̬
			var message = service.getMessage();  //���ص���Ϣ
			var doc = loadXml(resultXml); //�����׼��MSXML2.DOMDocument����
			location.reload();
		}
}


