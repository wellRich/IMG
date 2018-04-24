package com.digital.service.cazc;


import com.digital.entity.cazc.CivilAffairZoningCode;
import com.digital.util.Common;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author zhangwei
 * @description 文件名校验工具类
 * @date created in 20:11 2018/3/29
 * @throws Exception
 */
public class CivilAffairUtil {

    /**
     * 校验民政区划上传标题是否符合规范
     * zip文件命名规范：MZQHDM_20160119.zip
     * @param upFile
     * @param filePath
     * @return
     */
    public static boolean checkName(MultipartFile upFile,String filePath){
        ZipInputStream zis = null;
        ZipEntry ze = null;
        Boolean flag = true;
        if(upFile.getSize()>0) {
            String fileName = upFile.getOriginalFilename();
            //mzqhdm_
            String startName = fileName.substring(0, fileName.lastIndexOf("_")+1);
            //YYYYMMDD.zip
            String endName = fileName.substring(fileName.lastIndexOf("_")+1);
            Pattern p = Pattern.compile("\\d{8}(.zip|.ZIP)");
            File file = new File(filePath + fileName);
            ZipFile  zipFile = null;
           // System.out.println(startName+"===="+endName);
            if ("mzqhdm_".equals(startName) && p.matcher(endName).matches()) {
                try {
                    //上传文件
                    upFile.transferTo(file);
                    zipFile = new ZipFile(file);
                    //获取zip里面的文件
                    zis  = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
                    while((ze = zis.getNextEntry()) != null){
                        if(!ze.isDirectory()){
                            //判断zip里面的文件名与zip文件名是否一致
                            if(!ze.getName().substring(0,ze.getName().lastIndexOf(".")).equals(fileName.substring(0,fileName.lastIndexOf(".")))){
                                file.delete();
                                flag = false;
                            }
                        }
                    }
                } catch (IOException e) {
                    return flag = false;
                }finally {
                    if(zis != null){
                        try {
                            zis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                flag = false;
            }
        }
        return flag;
    }


    /**
     * 根据传入的条件，将对应的民政区划zip文件中的数据内容，导入到民政区划数据表中
     * @param zipXh
     * @param filePath
     * @return List<CivilAffairZoningCode>
     */
    public static List<CivilAffairZoningCode> getContent(Integer zipXh, String filePath){
        ZipInputStream zis = null;
        BufferedReader br = null;
        ZipEntry ze = null;
        ZipFile zipFile = null;
        List<CivilAffairZoningCode> list = null;
        CivilAffairZoningCode  civilAffairZoningCode = null;
        //long timeMillis = System.currentTimeMillis();
        Date time = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //将文件传入内存中
            zis  = new ZipInputStream(new BufferedInputStream(new FileInputStream(filePath)));
            //得到文件
            zipFile = new ZipFile(filePath);
            list = new ArrayList<CivilAffairZoningCode>();
            //遍历zip中的文件
            while((ze = zis.getNextEntry()) != null){
                if(!ze.isDirectory()){
                   // System.out.println("filename="+ze.getName()+",fileSize="+ze.getSize()+"bytes");
                    br = new BufferedReader(
                            new InputStreamReader(zipFile.getInputStream(ze),"GBK"));
                    String line;
                    int flag = 0;
                    String[] fieldNames = null;
                    while ((line = br.readLine()) != null) {
                        civilAffairZoningCode = new CivilAffairZoningCode();
                        fieldNames = line.split(",");
                        //读取首行数据，同时进行内容校验
                        if(flag != 0) {
                            civilAffairZoningCode.setZipXh(zipXh);
                            civilAffairZoningCode.setZoningCode(fieldNames[0]+"000"); //将民政区划拼接为15位代码
                            civilAffairZoningCode.setZoningName(fieldNames[1]);
                            civilAffairZoningCode.setAssigningCode(Common.getAssigningCode(fieldNames[0]+"000"));
                            civilAffairZoningCode.setSuperiorZoningCode(Common.getSuperiorZoningCode(fieldNames[0]+"000"));
                            civilAffairZoningCode.setEnterTime(sdf.format(time));
                            list.add(civilAffairZoningCode);
                        }else{
                            if (null == fieldNames[0] || !"区划代码".equals(fieldNames[0].trim()) || null == fieldNames[1] || !"区划名称".equals(fieldNames[1].trim())) {
                                break;
                            }
                        }
                        flag++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                 if(zis != null){
                        zis.close();
                 }
                 if(br != null){
                      br.close();
                 }
                if(zipFile != null){
                    zipFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @return List<List<T>>
     */
    public static <T> List<List<T>> averageAssign(List<T> source,int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;  //(先计算出余数)
        int number=source.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 对list中的行政区划名称进行解析拼接
     * @param list
     * @return
     */
    public static List<CivilAffairZoningCode> changeName(List<CivilAffairZoningCode> list){
        for (CivilAffairZoningCode civilAffairZoningCode : list) {
            //拼接名字，zoningName+ 当前行政区划代码
            civilAffairZoningCode.setZoningName(civilAffairZoningCode.getZoningName()+" "+subStrZoningCode(civilAffairZoningCode.getZoningCode(),civilAffairZoningCode.getAssigningCode()));
        }
        return list;
    }

    /**
     * 根据级次代码对行政区划代码进行截取划分
     * @param zongingCode
     * @param jcdm
     * @return
     */
    public static String subStrZoningCode(String zongingCode,String jcdm){
       String result = "";
        if(jcdm .equals( "1")){
             result = zongingCode.substring(0,2);
        }else if(jcdm .equals( "2")){
            result = zongingCode.substring(2,4);
        }else if(jcdm .equals("3")){
            result = zongingCode.substring(4,6);
        }else if(jcdm .equals( "4")){
            result = zongingCode.substring(6,9);
        }else{
            result = zongingCode.substring(9,12);
        }
        return result;
    }

    /**
     * 根据级次代码对行政区划代码进行截取划分
     * @param zongingCode
     * @return
     */
    public static String subStrZoningCode(String zongingCode){
        String result = "";
        if(null != zongingCode && "".equals(zongingCode)) {
            if (zongingCode.substring(0, 2) == "00") {
                result = zongingCode.substring(0, 2);
            } else if (zongingCode.substring(2, 4) == "00") {
                result = zongingCode.substring(0, 4);
            } else if (zongingCode.substring(4, 6) == "00") {
                result = zongingCode.substring(0, 6);
            } else if (zongingCode.substring(6, 9) == "000") {
                result = zongingCode.substring(0, 9);
            } else {
                result = zongingCode.substring(0, 12);
            }
        }
        return result;
    }

    /**
     * 提供下载excel功能
     * @param civilAffairZoningCodeList
     * @param response
     */
    public static void downExecl(String qhdm,List<CivilAffairZoningCode> civilAffairZoningCodeList,HttpServletResponse response){
        //获取XSSFWorkbook（excel文档）
        XSSFWorkbook wb =  createCodeExecle(civilAffairZoningCodeList);
        //输出流
        OutputStream os = null;
        try {
        //设置内容格式
        response.setContentType("application/octet-stream;charset=ISO-8859-1");

        String fileName = java.net.URLEncoder.encode(qhdm+"_mzqhdm", "ISO-8859-1");
        //设置title
        response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xlsx");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");

            os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成excel文件，供下载使用
     * @param civilAffairZoningCodeList
     * @return
     */
    public static XSSFWorkbook createCodeExecle(List<CivilAffairZoningCode> civilAffairZoningCodeList){
        // 第一步，创建一个webbook，对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        Sheet sheet = wb.createSheet("民政区划");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        Row row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style=wb.createCellStyle();//样式对象
        style.setAlignment(HorizontalAlignment.CENTER); // 居中
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(1, 20 * 256);

        //创建第一个单元格，并设置内容
         Cell cell = row.createCell((int) 0);
        cell.setCellValue("行政区划名称");
        cell.setCellStyle(style);
        //创建第二个单元格，并设置内容
         cell = row.createCell((int) 1);
        cell.setCellValue("行政区划代码");
        cell.setCellStyle(style);

        for(int i =0 ;i < civilAffairZoningCodeList.size();i++){
            //循环一次，创建一行
            row = sheet.createRow((int) i + 1);
            //将行政区划代码，插入到第一个单元格中
            row.createCell((short) 0).setCellValue(civilAffairZoningCodeList.get(i).getZoningCode());
            //设置样式
            row.createCell((short) 1).setCellValue(civilAffairZoningCodeList.get(i).getZoningName());
        }
        return wb;
    }

    /**
     * 提供下载pdf功能
     * @param civilAffairZoningCodeList
     * @param response
     */
    public static void downPDf(String qhdm,List<CivilAffairZoningCode> civilAffairZoningCodeList,HttpServletResponse response,String pdfPath){
        //
        File file =  creatCodePDF(civilAffairZoningCodeList,pdfPath);
        //输出流
        OutputStream os = null;
        InputStream in = null;
        try {
            //设置内容格式
            response.setContentType("application/octet-stream;charset=ISO-8859-1");

            String fileName = java.net.URLEncoder.encode(qhdm+"_mzqhdm", "ISO-8859-1");
            //设置title
            response.setHeader("Content-Disposition", "attachment;filename="+fileName+".pdf");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            in = new BufferedInputStream(new FileInputStream(file), 4096);
            os = new BufferedOutputStream(response.getOutputStream());

            byte[] bytes = new byte[4096];
            int i = 0;
            while ((i = in.read(bytes)) > 0) {
                os.write(bytes, 0, i);
            }
            os.flush();
            //垃圾回收
            civilAffairZoningCodeList.clear();
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (os != null) {
                    os.close();
                }
                if(in != null){
                    in.close();
                }
                if(file.exists()){
                    file.delete();
                }
                } catch (IOException e1) {
                }
        }
    }

    /**
     * 生成pdf文件
     * @param
     */
    public static File creatCodePDF(List<CivilAffairZoningCode> civilAffairZoningCodeList,String pdfPath) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);// 实例化文档对象
        File file = new File(pdfPath+"/mzqhdm_tmp.pdf");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, out);// 创建写入器
            document.open();// 打开文档准备写入内容
            // 设置可以在PDF中输入汉字的字体
            BaseFont bfChinese = BaseFont.createFont(
                    "C:\\WINDOWS\\Fonts\\SIMHEI.TTF", BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED);
            Font font = new Font(bfChinese, 16, Font.BOLD);
            //font.setColor(0, 0, 255);字体颜色
            Paragraph paragraph1 = new Paragraph("民政区划代码", font);// 创建段落对象
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            // 创建了一个章节对象，标题为"第7章 IO——输入输出流"
            Chapter chapter1 = new Chapter(paragraph1, 0);
            // 将编号级别设为 0 就不会在页面上显示章节编号
            chapter1.setNumberDepth(0);
            Table table = new Table(2); // 创建表格对象
            // table.setBorderColor(new Color(220, 255, 100)); // 设置表格边框颜色
            // 设置单元格的边距间隔等
            table.setPadding(1);
            //table.setSpacing(1);
            table.setBorderWidth(1);
            com.lowagie.text.Cell cell = null; // 单元格对象
            Font font1 = new Font(bfChinese, 10, Font.BOLD);
            // 添加表头信息
            cell = new com.lowagie.text.Cell(new Paragraph("行政区划代码", font1));
            cell.setHeader(true);
            table.addCell(cell);
            cell = new com.lowagie.text.Cell(new Paragraph("行政区划名称", font1));
            cell.setHeader(true);
            table.addCell(cell);
            table.endHeaders();
            for(int i = 0 ; i< civilAffairZoningCodeList.size();i++){
                table.addCell(new com.lowagie.text.Cell(new Paragraph(civilAffairZoningCodeList.get(i).getZoningCode(), font1)));
                table.addCell(new com.lowagie.text.Cell(new Paragraph(civilAffairZoningCodeList.get(i).getZoningName(), font1)));
            }
            chapter1.add(table);
            // 设置单元格的边距间隔等
            document.add(chapter1);
            // 关闭文档
            document.close();

        } catch (DocumentException e) {
            System.out.println("PDF文件生成失败！" + e);
            e.printStackTrace();
        } catch (IOException ee) {
            System.out.println("PDF文件生成失败！" + ee);
            ee.printStackTrace();
        }finally {
            if (out != null) {
                try {
                    // 关闭输出文件流
                    out.close();
                } catch (IOException e1) {
                }
            }
        }
        return file;
    }

}