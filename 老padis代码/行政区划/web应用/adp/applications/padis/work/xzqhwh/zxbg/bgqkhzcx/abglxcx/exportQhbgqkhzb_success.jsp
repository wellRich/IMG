<%@ page contentType="application/vnd.ms-excel;charset=gbk" language="java" %><%@ page import="org.apache.poi.hssf.util.HSSFColor"%><%@ page import="java.io.*"%><%@ page import="com.padis.common.xtservice.ParseExcel"%><%@ page import="java.util.*"%><%@ page import="org.apache.poi.hssf.usermodel.*"%><%@ page import="org.apache.poi.hssf.util.*"%><%

String filename = new String(new String("��������ͷֵ��������������ܱ�").getBytes("GBK"),"ISO8859-1");
response.setContentType( "APPLICATION/OCTET-STREAM" );
response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");

	//�и߶�
	short rowHeight = 330;
		HSSFWorkbook wb = new HSSFWorkbook();// ������HSSFWorkbook����

		HSSFSheet sheet = wb.createSheet("��������ͷֵ��������������ܱ�");// �����µ�sheet����
		sheet.setColumnWidth((short) 0, (short) 5000);
		sheet.setColumnWidth((short) 1, (short) 4000);
		sheet.setColumnWidth((short) 2, (short) 4000);
		sheet.setColumnWidth((short) 3, (short) 4000);
		sheet.setColumnWidth((short) 4, (short) 4000);		
			
		HSSFCellStyle style1 = wb.createCellStyle();// ��������ʽ ���� ��ɫ��ɫ ����Ӵ�	
		style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style1.setAlignment(HSSFCellStyle.ALIGN_LEFT); 
		HSSFFont font1  = wb.createFont();    
		font1.setFontHeightInPoints((short) 11);//�ֺ�
		style1.setFont(font1);
		style1.setWrapText(true);//�ı����������ݶ����Զ�����

		HSSFCellStyle style2 = wb.createCellStyle();// ��������ʽ ����
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font2  = wb.createFont();    
		font2.setFontHeightInPoints((short) 11);//�ֺ�
		style2.setFont(font2);
		style2.setWrapText(true);//�ı����������ݶ����Զ����� 

		HSSFCellStyle style20 = wb.createCellStyle();// ��������ʽ ����
		style20.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style20.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style20.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style20.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style20.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font20  = wb.createFont();    
		font20.setFontHeightInPoints((short) 11);//�ֺ�
		//font20.setItalic(true);//����б��
		style20.setFont(font20);
		style20.setWrapText(true);//�ı����������ݶ����Զ����� 

		//---------------------������------------------
		HSSFCellStyle styTitle = wb.createCellStyle();
		styTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
		HSSFFont fontTitle  = wb.createFont();    
		fontTitle.setFontHeightInPoints((short) 16);//�ֺ�    
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//�Ӵ�    
		styTitle.setFont(fontTitle);
		
		HSSFRow titleRow = sheet.createRow((short)0);
		HSSFCell cellTitle = titleRow.createCell((short)0);
		cellTitle.setCellStyle(styTitle);
		cellTitle.setCellValue("��������ͷֵ��������������ܱ�");
		titleRow.createCell((short)1).setCellStyle(styTitle);
		titleRow.createCell((short)2).setCellStyle(styTitle);
		titleRow.createCell((short)3).setCellStyle(styTitle);
		titleRow.createCell((short)4).setCellStyle(styTitle);
		
		sheet.addMergedRegion(new Region(0,(short)0,0,(short)4)); 
		
		//-----------------------�쵼ǩ��-----------------------------
		HSSFRow row2 = sheet.createRow((short) 1);// ��������	 ,�쵼ǩ�֣�  ��   ��  ��
		row2.setHeight(rowHeight);
		HSSFCell cell20 = row2.createCell((short)0);
		cell20.setCellValue("ʡ�������У�");
		cell20.setCellStyle(style2);
		HSSFCell cell21 = row2.createCell((short)1);
		cell21.setCellValue("�����ϸ��¼��");
		cell21.setCellStyle(style2);
		row2.createCell((short)2).setCellStyle(style2);
		row2.createCell((short)3).setCellStyle(style2);
		row2.createCell((short)4).setCellStyle(style2);

		HSSFRow row3 = sheet.createRow((short) 2);
		row3.setHeight(rowHeight);
		
		row3.createCell((short)0).setCellStyle(style1);
		HSSFCell cell31 = row3.createCell((short)1);
		cell31.setCellValue("����");
		cell31.setCellStyle(style2);
		
		HSSFCell cell32 = row3.createCell((short)2);
		cell32.setCellValue("���");
		cell32.setCellStyle(style2);
		
		HSSFCell cell33 = row3.createCell((short)3);
		cell33.setCellValue("Ǩ��");
		cell33.setCellStyle(style2);
		
		HSSFCell cell34 = row3.createCell((short)4);
		cell34.setCellValue("����");
		cell34.setCellStyle(style2);
		
		//�ϲ���
		sheet.addMergedRegion(new Region(1,(short)0,2,(short)0)); 
		//�ϲ���
		sheet.addMergedRegion(new Region(1,(short)1,1,(short)4));
		

		List mxbList = (List)request.getAttribute("MXBLIST");

		if(mxbList!=null){
			for(int i=0;i<mxbList.size();i++){
				Map listMap = (Map)mxbList.get(i);
				String xzqh_mc = String.valueOf(listMap.get("XZQH_MC"));
				String add = String.valueOf(listMap.get("XINZENG"));		
				String change = String.valueOf(listMap.get("BIANGENG"));
				String merge = String.valueOf(listMap.get("BINGRU"));
				String remove = String.valueOf(listMap.get("QIANYI"));
			
				HSSFRow row = sheet.createRow((short) (i+3));// ��������	
				row.setHeight(rowHeight);
				row.createCell((short) 0);// ������cell
				
				HSSFCell cell0 = row.createCell((short) 0);// ������cell
				cell0.setCellStyle(style1);
				cell0.setCellValue(xzqh_mc);// ����cell���������͵�ֵ	
	
				HSSFCell cell1 = row.createCell((short) 1);// ������cell
				cell1.setCellStyle(style20);
				cell1.setCellValue(Double.parseDouble(add));// ����cell���������͵�ֵ	
					
				HSSFCell cell2 = row.createCell((short) 2);// ������cell
				cell2.setCellStyle(style20);
				cell2.setCellValue(Double.parseDouble(change));// ����cell���������͵�ֵ	
				
				HSSFCell cell3 = row.createCell((short) 3);// ������cell
				cell3.setCellStyle(style20);
				cell3.setCellValue(Double.parseDouble(remove));// ����cell���������͵�ֵ	
				
				HSSFCell cell4 = row.createCell((short) 4);// ������cell
				cell4.setCellStyle(style20);
				cell4.setCellValue(Double.parseDouble(merge));// ����cell���������͵�ֵ

			}
		}

	wb.write(response.getOutputStream());
		
    response.getOutputStream().flush();
    response.getOutputStream().close();
   
 %> 
