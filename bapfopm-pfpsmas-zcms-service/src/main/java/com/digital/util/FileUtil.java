package com.digital.util;

import com.digital.entity.province.ContrastTemporary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.FileUtils;

/**
 * @Author: zhanghpj
 * @Version 1.0, 17:35 2018/3/1
 * @See
 * @Since
 * @Deprecated
 */
//@Component
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


    private static final String QHDM_TITLE = "区划名称,区划代码";

    private static final String QHDM_BGDZB_TITLE = "调整编号,调整说明,原区划名称,原区划代码,调整类型,现区划名称,现区划代码,错误信息";

    private static final String sign = ",";

    //区划代码变更对照文件的文件名开头
    private static final String QHDM_BGDZB = "QHDM_BGDZB_";

//    @Autowired
//    public static RedisTemplate redisTemplate;

    /**
     * @description 读取zip文件中的内容 并对其中的内容进行简要的校验
     * @method  readZip
     * @params [filePath:文件绝对路径]
     * @return List<String> 内容集合
     * @exception
     */
    public static Map<String,Object> readZip(String filePath) {

        Map<String,Object> resultMap = new HashMap<>();
        ZipFile zip = null;
        FileInputStream inputStream = null;
        BufferedInputStream bis =null;
        ZipInputStream zis = null;
        try {
            zip = new ZipFile(filePath);

            inputStream = new FileInputStream(filePath);
            bis = new BufferedInputStream(inputStream);
            zis = new ZipInputStream(bis);
            java.util.zip.ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    logger.info("压缩包中有文件夹！！！！");
                } else {
                    System.out.println("file:" + entry.getName());
                    String txtName = entry.getName();

                    logger.info("文件头内容：" + txtName.substring(txtName.lastIndexOf("/") + 1, txtName.indexOf("_")));
//                   if ("XZQH".equals(txtName.substring(txtName.lastIndexOf("/") + 1, txtName.indexOf("_")))) {
                    int index = 1;
                    String count = "0";
                    List<String> zoningList = new ArrayList<>();
                    List<String> changeList = new ArrayList<>();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zip.getInputStream(entry), "GBK"));
                    String line = null;
                    //读取文件内容并对文件内容进行大致校验
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().length() <= 0)
                            continue;
                        String[] str = line.split(",");
                        if (index == 1){
                            count = line.trim();
                        }else if (index == 2){
                            if (txtName.substring(txtName.lastIndexOf("/")+1).contains("_BGDZB_")) {
                                if (!StringUtil.sNull(str[0].trim()).equals("调整编号")
                                        || !StringUtil.sNull(str[1].trim()).equals("调整说明")
                                        || !StringUtil.sNull(str[2].trim()).equals("原区划名称")
                                        || !StringUtil.sNull(str[3].trim()).equals("原区划代码")
                                        || !StringUtil.sNull(str[4].trim()).equals("调整类型")
                                        || !StringUtil.sNull(str[5].trim()).equals("现区划名称")
                                        || !StringUtil.sNull(str[6].trim()).equals("现区划代码")) {
                                    //返回状态信息
                                    resultMap.put("check", false);
                                    resultMap.put("message", txtName.substring(txtName.lastIndexOf("/") + 1) + "标题行出错，与规范不符，请对照规范修改！！");
                                    return resultMap;
                                }
                            }else{
                                if (!StringUtil.sNull(str[0].trim()).equals("区划名称")
                                        ||!StringUtil.sNull(str[1].trim()).equals("区划代码")){
                                    resultMap.put("check",false);
                                    resultMap.put("message",txtName.substring(txtName.lastIndexOf("/")+1)+"标题行出错，与规范不符，请对照规范修改！！");
                                    return resultMap;
                                }
                            }


                        }else{
                            if (txtName.substring(txtName.lastIndexOf("/")+1).contains("_BGDZB_")) {
                                //严格的“，”分割7个字段
                                if (str.length!=7){
                                    resultMap.put("check",false);
                                    resultMap.put("message",txtName.substring(txtName.lastIndexOf("/")+1)+"第"+index+"行出现逗号分隔错误，请检查！");
                                    return resultMap;
                                }
                                changeList.add(line);
                            }else {
                                if (str.length!=2){
                                    resultMap.put("check",false);
                                    resultMap.put("message",txtName.substring(txtName.lastIndexOf("/")+1)+"第"+index+"行出现逗号分隔错误，请检查！");
                                    return resultMap;
                                }
                                zoningList.add(line);
                            }
                        }

                        index++;
                    }
                    if (index-3 != Integer.parseInt(count)){
                        resultMap.put("check",false);
                        resultMap.put("message",txtName.substring(txtName.lastIndexOf("/")+1)+"给出的数据量和实际条数不符合，请检查！");
                        return resultMap;
                    }
                    resultMap.put("check",true);
                    if (txtName.substring(txtName.lastIndexOf("/")+1).contains("_BGDZB_")){
                        resultMap.put("BGDZB",changeList);
                    }else {
                        resultMap.put("XZQH",zoningList);
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException();
        }finally {
            try {
                assert inputStream != null;
                inputStream.close();
                assert bis != null;
                bis.close();
                assert zis != null;
                zis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return resultMap;
    }

    public static void writerTxt(List<ContrastTemporary> temporaryList,String filePath)  {
        //创建文件

        if (fileExists(filePath)) {
            File file = new File(filePath);
            deleteDir(file);
        }
        try {
            File file =  buildFile(filePath,false);
            //"调整编号,调整说明,原区划名称,原区划代码,调整类型,现区划名称,现区划代码,错误信息"
            FileUtils.writeStringToFile(file,QHDM_BGDZB_TITLE+"\r\n","UTF-8");
            for (ContrastTemporary temporary : temporaryList) {
                FileUtils.writeStringToFile(file,temporary.getGroupNum()+sign+temporary.getGroupName()+sign+
                        temporary.getOriginalName()+sign+temporary.getOriginalCode()+sign+
                        temporary.getTypeCode()+sign+
                        temporary.getNowName()+sign+temporary.getNowCode()+sign+
                        (StringUtil.isEmpty(temporary.getErrorMessage())?"无":temporary.getErrorMessage())+"\r\n","UTF-8",true);
            }
        } catch (IOException e) {
            logger.info("文件创建失败");
            e.printStackTrace();
        }


    }

    /*
    * 读取txt文件中的内容
    * */
    public static List<String> readTxt(String file)  {

        FileInputStream inputStream = null;
        String dataTotal = null;
        Integer count = 1;
        String zoningName ,zoningCode =null;
        List<String> list = new ArrayList<>();
        try {
            inputStream = new FileInputStream(file);
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream,"GBK"));
            String data = null;
            data = bufReader.readLine();
            //存放数据


            while (data!=null){

                list.add(data);

                data = bufReader.readLine();
            }
            inputStream.close();
            bufReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return list;
    }

    /*
    * 判断文件是否存在
    * */
    public static boolean fileExists(String fn) {
        File f = new File(fn);
        return f.exists();
    }

    /**
     * @description 递归删除目录及目录下的文件
     * @method  deleteDir
     * @params [dir：文件]
     * @return boolean
     * @exception
     */
    public static boolean deleteDir(File dir){

        if (dir.isDirectory()){
            String[] childen = dir.list();
            //递归删除目录中的子目录下
            for (int i =0; i < childen.length; i++){
                boolean success = deleteDir(new File(dir,childen[i]));
                if (!success){
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /*
    * 创建文件
    * */
    public static File buildFile(String fileName, boolean isDirectory) {
        File target = new File(fileName);
        if (isDirectory) {
            target.mkdirs();
        } else {
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
                target = new File(target.getAbsolutePath());
            }
        }
        return target;
    }



    /**
     * 省级接口上传文件校验——压缩文件中的文本文件名的检验
     * txt文件命名规范：
     * 文件名形如：QHDM_XXXXXX_yyyymmdd.txt 或者 QHDM_BGDZB_XXXXXX_yyyymmdd.txt
     * zip中的文件必须是以 .txt 为扩展名
     * 文件以 QHDM_BGDZB_ 或者 QHDM_ 开头
     * 6位区划代码中与zip文件的区划代码一致
     * 以 _yyyymmdd 结束
     * @param uploadFile 上传的zip文件
     * @param filePath 文件路径
     * @return boolean
     * @exception IOException
     */
    public static boolean checkFileName(MultipartFile uploadFile, String filePath) {
        String fileName = uploadFile.getOriginalFilename();
        logger.info("是否有一个点："+fileName.contains("."));
        String zipName = fileName.substring(0, fileName.indexOf("."));
        logger.info("是否有一个点："+fileName.contains("."));

        File file1 = new File(filePath + zipName);
        if (!file1.exists()) {
            file1.mkdirs();
        }

        File file = new File(file1.getAbsolutePath() + File.separator+ fileName);
        boolean result = true;
        try {
            uploadFile.transferTo(file);
            if (file.exists()) {
                logger.info("开始校验压缩文件" + file.getName() + "中的文本文件！");
                String zipFileName = file.getName();
                String textFilePath = zipFileName.substring(0, zipFileName.lastIndexOf(".")) + "/";
                ZipFile zipFile = new ZipFile(file);

                // 1 取得文件名，以文件名的长度排序
                String[] textFileNames = zipFile.stream().filter(e-> !e.isDirectory()).map(e-> e.getName()).sorted(Comparator.comparingInt(value -> value.length())).toArray(String[]::new);

                // 2 文件数量校验，必须是2个
                if(textFileNames.length == 2){
                    // 3 文件名校验
                    int index = zipFileName.indexOf("_");
                    String middle = zipFileName.substring(index + 1, zipFileName.lastIndexOf("."));
                    LinkedList<Pattern> patterns = new LinkedList<>();
                    patterns.add(Pattern.compile("^QHDM_" + middle + "\\.(txt|TXT)?"));
                    patterns.add(Pattern.compile("^QHDM_BGDZB_" + middle + "\\.(txt|TXT)?"));
                    boolean qhbg = patterns.get(0).matcher(textFileNames[0].replace(textFilePath, "")).matches();
                    boolean qhbg_duizou = patterns.get(1).matcher(textFileNames[1].replace(textFilePath, "")).matches();
                    if(qhbg && qhbg_duizou){
                        logger.info(zipFileName + "中变更对照表文件与区划数据文件的文件名符合规范!");
                    }else if (qhbg){
                        logger.warn(zipFileName + "中变更对照表文件的文件名不规范！");
                    }else if(qhbg_duizou){
                        logger.warn(zipFileName + "中区划数据文件的文件名不规范！");
                    }else{
                        logger.warn(zipFileName + "中变更对照表文件与区划数据文件的文件名都不符合规范!");
                    }
                    result = qhbg && qhbg_duizou;
                }else {
                    throw new RuntimeException("压缩文件中文本数量要求是2个，此压缩文件中的文本数量是" + textFileNames.length + "个！");
                }
                logger.info("校验的结果是-----> " + result);
            } else {
                String msg = file.getName() + " 文件不存在";
                logger.error(msg);
                throw new RuntimeException(msg);
            }
            if(!result){
                if(file.exists()){
                    file.delete();
                }
            }
            return result;
        } catch (RuntimeException | IOException ex) {
            logger.error(ex.getMessage(), ex);
            if(file.exists()){
                file.delete();
            }
            return false;
        }
    }



    /**
    * 上传文件的内容校验
    * @param  lines 文本文件内容
    * @param  fileName 文本文件名
    * @return
    * @exception/throws
    * @see
    */
    public static List<String[]> checkContent(List<String> lines, String fileName) {
        Pattern numPattern = Pattern.compile("[1-9]\\d*");
        String num = lines.get(0).trim();
        int size = lines.size();
        List<String[]> data = new ArrayList<>(size);
        logger.info(fileName + "记录的数据总量--------> " + num);
        boolean isNum =  numPattern.matcher(num).matches();//Pattern.matches("^\\d+$", num);
        // 数据行数校验
        if (isNum) {
            int count = Integer.valueOf(num);
            if (count != size - 2) {
                throw new RuntimeException(fileName + "给出的数据量和实际条数不符合，请检查！");
            } else {
                //标题校验
                String title = lines.get(1).trim();
                if(fileName.indexOf(QHDM_BGDZB) > -1){
                    if (title.equals(QHDM_BGDZB_TITLE)) {
                        int index = fileName.lastIndexOf("_");

                        //省级区划代码
                        String provinceCode = fileName.substring(index - 6, index);

                        //错误信息
                        StringBuffer errorMsg = new StringBuffer();

                        //调整序号
                        String changeNum;

                        //调整说明
                        String changeNotes;

                        //原区划名称
                        String originalName;

                        //原区划代码
                        String originalCode;

                        //变更类型
                        String changeType;

                        //现区划名称
                        String currentName;

                        //现区划代码
                        String currentCode;

                        String line = "";
                        for(int i = 2; i < size; i ++){
                            line = lines.get(i);
                            String[] elements = line.split(",");
                            changeNum = elements[0].trim();
                            changeNotes = elements[1].trim();
                            originalName = elements[2].trim();
                            originalCode = elements[3].trim();
                            changeType = elements[4].trim();
                            currentName = elements[5].trim();
                            currentCode = elements[6].trim();
                            String[] result = new String[8];
                            if(elements.length != 7){
                                throw new RuntimeException("“" + line + "”逗号分隔错误");
                            }else {
                                result[1] = changeNotes;
                                if(changeNum.equals("")){
                                    errorMsg.append(" 第" + i + "行出错：调整编号为空，");
                                }else {
                                    result[0] = changeNum;
                                }

                                if(changeType.equals("")){
                                    errorMsg.append("调整类型为空，");
                                }else {
                                    result[4] = changeType;
                                    if (changeType.equals("11")) {
                                        if (!originalCode.equals("")) {
                                            errorMsg.append("原区划代码为空，");
                                        }else {
                                            if(originalCode.length() != 12 && originalCode.length() != 15){
                                                errorMsg.append("原区划代码长度不是12与15，");
                                            }else {
                                                if(!originalCode.startsWith(provinceCode)){
                                                    errorMsg.append("原区划代码中的省级代码与zip文件不一致，");
                                                }else {
                                                    result[3] = originalCode;
                                                }
                                            }
                                        }

                                        if (!originalName.equals("")) {
                                            errorMsg.append("原区划名称为空，");
                                        }else {
                                            result[2] = originalName;
                                        }
                                    }else {
                                        result[3] = originalCode;
                                        result[2] = originalName;
                                    }
                                }

                                if(!currentName.equals("")){
                                    errorMsg.append("现区划名称为空，");
                                }else {
                                    result[5] = currentName;
                                }

                                if(!currentCode.equals("")){
                                    errorMsg.append("现区划代码为空，");
                                }else {
                                    if(originalCode.length() != 12 && originalCode.length() != 15){
                                        errorMsg.append("现区划代码长度不是12与15，");
                                    }else {
                                        if(!originalCode.startsWith(provinceCode)){
                                            errorMsg.append("现区划代码中的省级代码与zip文件不一致，");
                                        }else {
                                            result[6] = originalCode;
                                        }
                                    }
                                }
                            }
                            result[7] = errorMsg.toString();

                            //插入临时表,由zhanghp提供
                            data.add(result);
                        }
                        return data;
                    } else {
                        throw new RuntimeException(fileName + "文件标题错误，上传文件标题是[" + title + "],规范中要求标题是：[调整编号,调整说明,原区划名称,原区划代码,调整类型,现区划名称,现区划代码]");
                    }
                }else{
                    if (title.equals(QHDM_TITLE)) {
                        for(String line: lines){
                            String[] elements = line.split(",");

                            //插入临时表,由zhanghp提供
                            data.add(elements);
                        }
                        return data;
                    } else {
                        throw new RuntimeException(fileName + "文件标题错误，上传文件标题是[" + title + "],规范中要求标题是：[区划名称,区划代码]");
                    }
                }
            }
        } else {
            throw new RuntimeException("文本首行未给出数据行数，请检查！");
        }
    }
}
