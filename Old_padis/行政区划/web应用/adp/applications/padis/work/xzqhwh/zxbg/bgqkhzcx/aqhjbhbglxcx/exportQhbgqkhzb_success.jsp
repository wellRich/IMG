<%@ page contentType="application/vnd.ms-excel;charset=gbk" language="java" %><%@ page import="org.apache.poi.hssf.util.HSSFColor"%><%@ page import="java.io.*"%><%@ page import="com.padis.common.xtservice.ParseExcel"%><%@ page import="java.util.*"%><%@ page import="org.apache.poi.hssf.usermodel.*"%><%@ page import="org.apache.poi.hssf.util.*"%><%

String filename = new String(new String("全国区划代码变更情况一览表").getBytes("GBK"),"ISO8859-1");
response.setContentType( "APPLICATION/OCTET-STREAM" );
response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");

	//行高度
	short rowHeight = 330;
		HSSFWorkbook wb = new HSSFWorkbook();// 建立新HSSFWorkbook对象

		HSSFSheet sheet = wb.createSheet("全国区划代码变更情况一览表");// 建立新的sheet对象
		sheet.setColumnWidth((short) 0, (short) 4200);
		sheet.setColumnWidth((short) 1, (short) 4000);
		sheet.setColumnWidth((short) 2, (short) 4000);
		sheet.setColumnWidth((short) 3, (short) 4000);
		sheet.setColumnWidth((short) 4, (short) 4000);
		sheet.setColumnWidth((short) 5, (short) 4000);
		sheet.setColumnWidth((short) 6, (short) 4000);
			
		HSSFCellStyle style1 = wb.createCellStyle();// 定义表格样式 居中 灰色底色 字体加粗	
		style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style1.setAlignment(HSSFCellStyle.ALIGN_LEFT); 
		HSSFFont font1  = wb.createFont();    
		font1.setFontHeightInPoints((short) 11);//字号
		style1.setFont(font1);
		style1.setWrapText(true);//文本区域随内容多少自动调整

		HSSFCellStyle style2 = wb.createCellStyle();// 定义表格样式 居中
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font2  = wb.createFont();    
		font2.setFontHeightInPoints((short) 11);//字号
		style2.setFont(font2);
		style2.setWrapText(true);//文本区域随内容多少自动调整 

		HSSFCellStyle style20 = wb.createCellStyle();// 定义表格样式 居中
		style20.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style20.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style20.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style20.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style20.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font20  = wb.createFont();    
		font20.setFontHeightInPoints((short) 11);//字号
		//font20.setItalic(true);//设置斜体
		style20.setFont(font20);
		style20.setWrapText(true);//文本区域随内容多少自动调整 

		//---------------------标题行------------------
		HSSFCellStyle styTitle = wb.createCellStyle();
		styTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
		HSSFFont fontTitle  = wb.createFont();    
		fontTitle.setFontHeightInPoints((short) 16);//字号    
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗    
		styTitle.setFont(fontTitle);
		
		HSSFRow titleRow = sheet.createRow((short)0);
		HSSFCell cellTitle = titleRow.createCell((short)0);
		cellTitle.setCellStyle(styTitle);
		cellTitle.setCellValue("全国区划代码变更情况一览表");
		titleRow.createCell((short)1).setCellStyle(styTitle);
		titleRow.createCell((short)2).setCellStyle(styTitle);
		titleRow.createCell((short)3).setCellStyle(styTitle);
		titleRow.createCell((short)4).setCellStyle(styTitle);
		titleRow.createCell((short)5).setCellStyle(styTitle);
		titleRow.createCell((short)6).setCellStyle(styTitle);
		
		sheet.addMergedRegion(new Region(0,(short)0,0,(short)6)); 
		
		//-----------------------领导签字-----------------------------
		HSSFRow row2 = sheet.createRow((short) 1);// 建立新行	 ,领导签字：  年   月  日
		row2.setHeight(rowHeight);
		HSSFCell cell20 = row2.createCell((short)0);
		cell20.setCellValue("变更类型");
		cell20.setCellStyle(style2);
		HSSFCell cell21 = row2.createCell((short)1);
		cell21.setCellValue("变更明细记录数");
		cell21.setCellStyle(style2);
		row2.createCell((short)2).setCellStyle(style2);
		row2.createCell((short)3).setCellStyle(style2);
		row2.createCell((short)4).setCellStyle(style2);
		row2.createCell((short)5).setCellStyle(style2);
		HSSFCell cell26 = row2.createCell((short)6);
		cell26.setCellValue("小计");
		cell26.setCellStyle(style2);

		HSSFRow row3 = sheet.createRow((short) 2);
		row3.setHeight(rowHeight);
		
		row3.createCell((short)0).setCellStyle(style1);
		HSSFCell cell31 = row3.createCell((short)1);
		cell31.setCellValue("省级");
		cell31.setCellStyle(style2);
		
		HSSFCell cell32 = row3.createCell((short)2);
		cell32.setCellValue("地级");
		cell32.setCellStyle(style2);
		
		HSSFCell cell33 = row3.createCell((short)3);
		cell33.setCellValue("县级");
		cell33.setCellStyle(style2);
		
		HSSFCell cell34 = row3.createCell((short)4);
		cell34.setCellValue("乡级");
		cell34.setCellStyle(style2);
		
		HSSFCell cell35 = row3.createCell((short)5);
		cell35.setCellValue("村级");
		cell35.setCellStyle(style2);
		
		row3.createCell((short)6).setCellStyle(style2);
		//合并行
		sheet.addMergedRegion(new Region(1,(short)0,2,(short)0));
		sheet.addMergedRegion(new Region(1,(short)6,2,(short)6));
		//合并列
		sheet.addMergedRegion(new Region(1,(short)1,1,(short)5));
		

		List mxbList = (List)request.getAttribute("MXBLIST");

		if(mxbList!=null){
			for(int i=0;i<mxbList.size();i++){
				Map listMap = (Map)mxbList.get(i);
				String bglx_mc = String.valueOf(listMap.get("BGLX_MC"));
				String shengji = String.valueOf(listMap.get("BGS_SHENG"));		
				String shiji = String.valueOf(listMap.get("BGS_SHI"));
				String xianji = String.valueOf(listMap.get("BGS_XIAN"));
				String xiangji = String.valueOf(listMap.get("BGS_XIANG"));
				String cunji = String.valueOf(listMap.get("BGS_CUN"));
				String xiaoji = String.valueOf(listMap.get("BGS_XIAOJI"));
			
				HSSFRow row = sheet.createRow((short) (i+3));// 建立新行	
				row.setHeight(rowHeight);
				row.createCell((short) 0);// 建立新cell
				
				HSSFCell cell0 = row.createCell((short) 0);// 建立新cell
				cell0.setCellStyle(style1);
				cell0.setCellValue(bglx_mc);// 设置cell的整数类型的值	
	
				HSSFCell cell1 = row.createCell((short) 1);// 建立新cell
				cell1.setCellStyle(style20);
				cell1.setCellValue(Double.parseDouble(shengji));// 设置cell的整数类型的值	
					
				HSSFCell cell2 = row.createCell((short) 2);// 建立新cell
				cell2.setCellStyle(style20);
				cell2.setCellValue(Double.parseDouble(shiji));// 设置cell的整数类型的值	
				
				HSSFCell cell3 = row.createCell((short) 3);// 建立新cell
				cell3.setCellStyle(style20);
				cell3.setCellValue(Double.parseDouble(xianji));// 设置cell的整数类型的值	
				
				HSSFCell cell4 = row.createCell((short) 4);// 建立新cell
				cell4.setCellStyle(style20);
				cell4.setCellValue(Double.parseDouble(xiangji));// 设置cell的整数类型的值	
				
				HSSFCell cell5 = row.createCell((short) 5);// 建立新cell
				cell5.setCellStyle(style20);
				cell5.setCellValue(Double.parseDouble(cunji));// 设置cell的整数类型的值

				HSSFCell cell6 = row.createCell((short) 6);// 建立新cell
				cell6.setCellStyle(style20);
				cell6.setCellValue(Double.parseDouble(xiaoji));// 设置cell的整数类型的值

			}
		}

	wb.write(response.getOutputStream());
		
    response.getOutputStream().flush();
    response.getOutputStream().close();
   
 %> 
