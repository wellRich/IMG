/**
 *获取用户已经选择条目的值。
 *@param 
 *@return  已经选中的项，每个条目的值之间用","分开。
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
			showMessage("请选择要删除的个案！");
			return false;
		}
	if(window.confirm("确定要删除选择的个案吗？"))
		{
			var service = new Service("com.padis.business.xzqhwh.lgywsjsc.LgywsjscService.deleteGaxx") ; //发送请求的路径
			var paramXml = "<ROOT><map><RKDZDAH_LIST>"+dmArray+"</RKDZDAH_LIST></map></ROOT>";
			var resultXml = service.doService(paramXml); //发送请求，获取返回结果
			var code = service.getCode();  //返回结果的状态
			var message = service.getMessage();  //返回的信息
			var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
			location.reload();
		}
}


