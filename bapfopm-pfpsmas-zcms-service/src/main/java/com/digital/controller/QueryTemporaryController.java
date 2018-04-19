package com.digital.controller;

import com.digital.api.ImportTemporaryApi;
import com.digital.api.ZoningDataUploadApi;
import com.digital.entity.province.ContrastTemporary;
import com.digital.entity.province.FocusChangeFileInfo;
import com.digital.util.FileUtil;
import com.digital.util.resultData.Constants;
import com.digital.util.resultData.RtnData;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 11:03 2018/4/3
 * @See
 * @Since
 * @Deprecated
 */
@Controller
@RequestMapping("/queryTemp")
public class QueryTemporaryController {

    private static final Logger logger = LoggerFactory.getLogger(QueryTemporaryController.class);
    @Autowired
    private ImportTemporaryApi importTemporaryApi;

    @Autowired
    private ZoningDataUploadApi zoningDataUploadApi;


    @Value("${spring.http.multipart.location}")
    private String filePath;

    /*
    * 查询变更信息
    * */
    @RequestMapping(value = "/query/checkedData" ,method = RequestMethod.GET)
    @ResponseBody
    public String queryTemporaryData(@RequestParam("fileSquence") Integer fileSquence,String zoningCode,String errorStatus,String typeCode,Integer pageNum,Integer pageSize){
        List<ContrastTemporary> temporaryList = new ArrayList<>();
        PageHelper.startPage(pageNum,pageSize);
        temporaryList = importTemporaryApi.queryChangeData(fileSquence,zoningCode,errorStatus,typeCode);
        long count = PageHelper.count(()->{
            importTemporaryApi.queryChangeData(fileSquence,zoningCode,errorStatus,typeCode);
        });
        if (!temporaryList.isEmpty()){
            return new RtnData(String.valueOf(count),temporaryList).toString();
        }

        return new RtnData(Constants.RTN_CODE_ERROR,Constants.RTN_QUERY_ISEMPTY).toString();
    }

    /*
    * 下载变更数据
    * */
    @RequestMapping(value = "/query/downloadData",method = RequestMethod.GET)
    @ResponseBody
    public void downloadChangeData(@RequestParam("fileSquence") Integer fileSquence, String errorStatus, HttpServletResponse response){
        List<ContrastTemporary> temporaryList = new ArrayList<>();
        temporaryList = importTemporaryApi.queryTemporary(fileSquence,errorStatus);
        FocusChangeFileInfo fileInfo = zoningDataUploadApi.queryFocusChangeFileInfo(fileSquence);
        if (temporaryList.isEmpty()){
            //没有数据
          }
        String fileName = fileInfo.getFileName();
        String txtName = fileInfo.getFilePath()+File.separator+fileName.substring(0,fileName.indexOf("."))+"_Message.txt";
        //将数据写入到txt中
        FileUtil.writerTxt(temporaryList,txtName);

        response.setCharacterEncoding("GBK");
        response.setHeader("content-type", "text/plain");
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=" + txtName.substring(txtName.lastIndexOf(File.separator)));
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(txtName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件下载出现异常！");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("关闭流 出现异常！");
                }
            }
        }

    }


}
