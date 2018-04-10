<%@ page contentType="application/vnd.ms-excel;charset=gbk" language="java" %><%@ page import="org.apache.poi.hssf.util.HSSFColor"%><%@ page import="java.io.*"%><%@ page import="com.padis.common.xtservice.ParseExcel"%><%@ page import="java.util.*"%><%@ page import="org.apache.poi.hssf.usermodel.*"%><%@ page import="org.apache.poi.hssf.util.*"%><%

String filename = new String(("�������������ϸ").getBytes("GBK"),"ISO8859-1");
response.setContentType( "APPLICATION/OCTET-STREAM" );
response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");

	HSSFWorkbook wb = new HSSFWorkbook();// ������HSSFWorkbook����

	HSSFSheet sheet = wb.createSheet("�������������ϸ");// �����µ�sheet����
	sheet.setColumnWidth((short) 0, (short) 4200);
	sheet.setColumnWidth((short) 1, (short) 4000);
	sheet.setColumnWidth((short) 2, (short) 4000);
	sheet.setColumnWidth((short) 3, (short) 4200);
	sheet.setColumnWidth((short) 4, (short) 4000);
	sheet.setColumnWidth((short) 5, (short) 4000);
	
	HSSFRow firstRow = sheet.createRow((short) 0);// ��������		 
	
	HSSFCellStyle style1 = wb.createCellStyle();// ��������ʽ ���� ��ɫ��ɫ ����Ӵ�	
	style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
	style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
	style1.setBottomBorderColor(HSSFColor.BLACK.index); 	
	style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
	HSSFFont font1  = wb.createFont();    
	font1.setFontHeightInPoints((short) 10);//�ֺ�    
	font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//�Ӵ�    
	style1.setFont(font1);

	HSSFCellStyle style2 = wb.createCellStyle();// ��������ʽ ����
	style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
	style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
	style2.setBottomBorderColor(HSSFColor.BLACK.index);
	HSSFFont font2  = wb.createFont();    
	font2.setFontHeightInPoints((short) 9);//�ֺ�  
	style2.setFont(font2);
	style2.setWrapText(true);//�ı����������ݶ����Զ����� 
	
	HSSFCellStyle style3 = wb.createCellStyle();// ��������ʽ ����
	style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
	style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
	style3.setBottomBorderColor(HSSFColor.BLACK.index);
	HSSFFont font3  = wb.createFont();    
	font3.setFontHeightInPoints((short) 9);//�ֺ�  
	style3.setFont(font3);
	style3.setWrapText(true);//�ı����������ݶ����Զ����� 


	HSSFCell cell00 = firstRow.createCell((short) 0);// ������cell
	cell00.setCellStyle(style1);
	cell00.setCellValue("ԭ������������");

	HSSFCell cell01 = firstRow.createCell((short) 1);// ������cell
	cell01.setCellValue("ԭ������������");
	cell01.setCellStyle(style1);

	HSSFCell cell02 = firstRow.createCell((short) 2);// ������cell
	cell02.setCellValue("�������");
	cell02.setCellStyle(style1);
	
	HSSFCell cell03 = firstRow.createCell((short) 3);// ������cell
	cell03.setCellValue("Ŀ��������������");
	cell03.setCellStyle(style1);

	HSSFCell cell04 = firstRow.createCell((short) 4);// ������cell
	cell04.setCellStyle(style1);
	cell04.setCellValue("Ŀ��������������");
	
	HSSFCell cell05 = firstRow.createCell((short) 5);// ������cell
	cell05.setCellStyle(style1);
	cell05.setCellValue("�������");
	List mxbList = (List)request.getAttribute("RZLIST");
	if(mxbList!=null){
		for(int i=1;i<mxbList.size();i++){
			Map listMap = (Map)mxbList.get(i);
			String yxzqh_dm = String.valueOf(listMap.get("YSXZQH_DM"));
			String yxzqh_mc = String.valueOf(listMap.get("YSXZQH_MC"));
			String bglx_dm = String.valueOf(listMap.get("BGLX_DM"));
			String mbxzqh_dm = String.valueOf(listMap.get("MBXZQH_DM"));
			String mbxzqh_mc = String.valueOf(listMap.get("MBXZQH_MC"));
			String xgsj = String.valueOf(listMap.get("XGSJ"));
			

			HSSFRow row = sheet.createRow((short) i);// ��������

			HSSFCell cell0 = row.createCell((short) 0);// ������cell
			cell0.setCellStyle(style2);
			cell0.setCellValue(yxzqh_dm);// ����cell���������͵�ֵ		

			HSSFCell cell1 = row.createCell((short) 1);// ������cell
			cell1.setCellStyle(style3);
			cell1.setCellValue(yxzqh_mc);

			HSSFCell cell2 = row.createCell((short) 2);// ������cell
			cell2.setCellStyle(style3);
			cell2.setCellValue(bglx_dm);

			HSSFCell cell3 = row.createCell((short) 3);// ������cell
			cell3.setCellStyle(style2);
			cell3.setCellValue(mbxzqh_dm);
			
			HSSFCell cell4 = row.createCell((short) 4);// ������cell
			cell4.setCellStyle(style3);
			cell4.setCellValue(mbxzqh_mc);
			
			HSSFCell cell5 = row.createCell((short) 5);// ������cell
			cell5.setCellStyle(style2);
			cell5.setCellValue(xgsj);
		}
	}

	wb.write(response.getOutputStream());
		
    response.getOutputStream().flush();
    response.getOutputStream().close();
 %> 
