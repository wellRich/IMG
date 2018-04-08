<%@ page contentType="application/vnd.ms-excel;charset=gbk" language="java" %><%@ page import="org.apache.poi.hssf.util.HSSFColor"%><%@ page import="java.io.*"%><%@ page import="com.padis.common.xtservice.ParseExcel"%><%@ page import="java.util.*"%><%@ page import="org.apache.poi.hssf.usermodel.*"%><%@ page import="org.apache.poi.hssf.util.*"%><%

Map hash = (Map)request.getAttribute("map");
String sqdmc = hash.get("SQDMC").toString();

String filename = new String(sqdmc.getBytes("GBK"),"ISO8859-1");
response.setContentType( "APPLICATION/OCTET-STREAM" );
response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");

	//�и߶�
	short rowHeight = 330;
	HSSFWorkbook wb = new HSSFWorkbook();// ������HSSFWorkbook����

	HSSFSheet sheet = wb.createSheet("������ձ���ϸ��Ϣ");// �����µ�sheet����
	sheet.setColumnWidth((short) 0, (short) 4200);
	sheet.setColumnWidth((short) 1, (short) 4800);
	sheet.setColumnWidth((short) 2, (short) 4800);
	sheet.setColumnWidth((short) 3, (short) 4000);
	sheet.setColumnWidth((short) 4, (short) 4800);
	sheet.setColumnWidth((short) 5, (short) 4800);
	sheet.setColumnWidth((short) 6, (short) 4800);
	
		
	HSSFCellStyle style1 = wb.createCellStyle();// ��������ʽ ���� ��ɫ��ɫ ����Ӵ�	
	style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
	style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
	style1.setAlignment(HSSFCellStyle.ALIGN_LEFT); 
	HSSFFont font1  = wb.createFont();    
	font1.setFontHeightInPoints((short) 11);//�ֺ�    
	//font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  �Ӵ�
	style1.setFont(font1);
	style1.setWrapText(true);//�ı����������ݶ����Զ����� 

	HSSFCellStyle style10 = wb.createCellStyle();// ��������ʽ ���� ��ɫ��ɫ ����Ӵ�	
	style10.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	style10.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	style10.setBorderRight(HSSFCellStyle.BORDER_THIN);
	style10.setBorderTop(HSSFCellStyle.BORDER_THIN);
	style10.setAlignment(HSSFCellStyle.ALIGN_LEFT); 
	HSSFFont font10  = wb.createFont();    
	font10.setFontHeightInPoints((short) 11);//�ֺ�
	font10.setItalic(true);//����б��
	style10.setFont(font10);
	style10.setWrapText(true);//�ı����������ݶ����Զ�����

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
	style20.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	style20.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	style20.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	style20.setBorderRight(HSSFCellStyle.BORDER_THIN);
	style20.setBorderTop(HSSFCellStyle.BORDER_THIN);
	HSSFFont font20  = wb.createFont();    
	font20.setFontHeightInPoints((short) 11);//�ֺ�
	font20.setItalic(true);//����б��
	style20.setFont(font20);
	style20.setWrapText(true);//�ı����������ݶ����Զ����� 
	
	HSSFCellStyle style3 = wb.createCellStyle();// ��������ʽ ����
	style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
	style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
	HSSFFont font3  = wb.createFont();    
	font3.setFontHeightInPoints((short) 11);//�ֺ�  
	style3.setFont(font3);
	style3.setWrapText(true);//�ı����������ݶ����Զ����� 
	

		
	HSSFRow firstRow = sheet.createRow((short) 0);//����

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
	
	HSSFRow titleRow = sheet.createRow((short)1);
	HSSFCell cellTitle = titleRow.createCell((short)1);
	cellTitle.setCellStyle(styTitle);
	cellTitle.setCellValue("�˿ڼ���ϵͳ�������뵥λ�����ϸ");
	titleRow.createCell((short)2).setCellStyle(styTitle);
	titleRow.createCell((short)3).setCellStyle(styTitle);
	titleRow.createCell((short)4).setCellStyle(styTitle);
	titleRow.createCell((short)5).setCellStyle(styTitle);
	titleRow.createCell((short)6).setCellStyle(styTitle);
	sheet.addMergedRegion(new Region(1,(short)1,1,(short)6)); 
	
	//-----------------------�쵼ǩ��-----------------------------
	HSSFRow row2 = sheet.createRow((short) 2);// ��������	 ,�쵼ǩ�֣�  ��   ��  ��
	row2.setHeight(rowHeight);
	HSSFCell cell21 = row2.createCell((short)1);
	cell21.setCellValue("�쵼ǩ�֣�");
	cell21.setCellStyle(style3);
	row2.createCell((short)2).setCellStyle(style1);
	row2.createCell((short)3).setCellStyle(style1);
	sheet.addMergedRegion(new Region(2,(short)2,2,(short)3)); 
	
	HSSFCell cell24 = row2.createCell((short)4);
	cell24.setCellValue("          ��           ��           ��         ");
	cell24.setCellStyle(style2);
	row2.createCell((short)5).setCellStyle(style1);
	row2.createCell((short)6).setCellStyle(style1);
	sheet.addMergedRegion(new Region(2,(short)4,2,(short)6)); 
	
	
	//----------------------------������ձ���ϸ��Ϣ---------------------------------------

	List mxbList = (List)request.getAttribute("MXBLIST");
	String group_xh="";
	int j = 0;
	if(mxbList!=null){
		for(int i=2;j<mxbList.size();){
			Map listMap = (Map)mxbList.get(j);
			String groupxh = String.valueOf(listMap.get("GROUPXH"));
			String groupmc = String.valueOf(listMap.get("GROUPMC"));		
			String yxzqh_dm = String.valueOf(listMap.get("YSXZQH_DM"));
			String yxzqh_mc = String.valueOf(listMap.get("YSXZQH_MC"));
			String bglx_dm = String.valueOf(listMap.get("BGLX_DM"));
			String mbxzqh_dm = String.valueOf(listMap.get("MBXZQH_DM"));
			String mbxzqh_mc = String.valueOf(listMap.get("MBXZQH_MC"));
			String bz = String.valueOf(listMap.get("BZ"));
			if(bz == null || bz.equals("") || bz.equalsIgnoreCase("null"))
			{
				bz="";
			}
		
			if(!group_xh.equals(groupxh))
			{
				i++;
				HSSFRow group = sheet.createRow((short) i);// ��������	
				group.setHeight(rowHeight);
				group.createCell((short)0);
				HSSFCell mingchen = group.createCell((short)1);
				mingchen.setCellValue("����˵��");
				mingchen.setCellStyle(style1);
				HSSFCell groupvalue = group.createCell((short)2);
				groupvalue.setCellValue(groupmc);
				groupvalue.setCellStyle(style10);
				group.createCell((short)3).setCellStyle(style1);
				group.createCell((short)4).setCellStyle(style1);
				group.createCell((short)5).setCellStyle(style1);
				group.createCell((short)6).setCellStyle(style1);
				sheet.addMergedRegion(new Region(i,(short)2,i,(short)6)); 
				
				i++;
				HSSFRow rowTile =  sheet.createRow((short) i);// ��������	
				rowTile.setHeight(rowHeight);
				rowTile.createCell((short) 0);
				
				HSSFCell title1 = rowTile.createCell((short) 1);// ������cell
				title1.setCellStyle(style2);
				title1.setCellValue("ԭ�������뼰����");	
				HSSFCell title2 = rowTile.createCell((short) 2);// ������cell
				title2.setCellStyle(style2);
				sheet.addMergedRegion(new Region(i,(short)1,i,(short)2)); 
	
				HSSFCell title3 = rowTile.createCell((short) 3);// ������cell
				title3.setCellStyle(style2);
				title3.setCellValue("��������");
	
				HSSFCell title4 = rowTile.createCell((short) 4);// ������cell
				title4.setCellStyle(style2);
				title4.setCellValue("���������뼰����");
				HSSFCell title5 = rowTile.createCell((short) 5);// ������cell
				title5.setCellStyle(style2);
				sheet.addMergedRegion(new Region(i,(short)4,i,(short)5)); 
				
				HSSFCell title6 = rowTile.createCell((short) 6);// ������cell
				title6.setCellStyle(style2);
				title6.setCellValue("��ע");
				
				group_xh=groupxh;
				
			}
				i++;
				HSSFRow row = sheet.createRow((short) i);// ��������	
				row.setHeight(rowHeight);
				row.createCell((short) 0);// ������cell
	
				HSSFCell cell1 = row.createCell((short) 1);// ������cell
				cell1.setCellStyle(style20);
				cell1.setCellValue(yxzqh_dm);// ����cell���������͵�ֵ	
					
				HSSFCell cell2 = row.createCell((short) 2);// ������cell
				cell2.setCellStyle(style20);
				cell2.setCellValue(yxzqh_mc);// ����cell���������͵�ֵ	
				
				HSSFCell cell3 = row.createCell((short) 3);// ������cell
				cell3.setCellStyle(style20);
				cell3.setCellValue(bglx_dm);// ����cell���������͵�ֵ	
				
				HSSFCell cell4 = row.createCell((short) 4);// ������cell
				cell4.setCellStyle(style20);
				cell4.setCellValue(mbxzqh_dm);// ����cell���������͵�ֵ	
				
				HSSFCell cell5 = row.createCell((short) 5);// ������cell
				cell5.setCellStyle(style20);
				cell5.setCellValue(mbxzqh_mc);// ����cell���������͵�ֵ	
				
				HSSFCell cell6 = row.createCell((short) 6);// ������cell
				cell6.setCellStyle(style20);
				cell6.setCellValue(bz);// ����cell���������͵�ֵ	
			
			j++;

		}
	}


	wb.write(response.getOutputStream());
		
    response.getOutputStream().flush();
    response.getOutputStream().close();
   
 %> 
