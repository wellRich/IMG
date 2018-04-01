package com.digital.controller;

import com.digital.api.ZoningDataUploadApi;
import com.digital.entity.province.FocusChangeFileInfo;
import com.digital.util.Common;
import com.digital.util.FileUtil;
import com.digital.util.StringUtil;
import com.digital.util.resultData.Constants;
import com.digital.util.resultData.RtnData;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import org.bouncycastle.cert.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description TODO:区划代码上传
 *
 * @Author: zhanghpj
 * @Version 1.0, 18:12 2018/2/27
 * @See
 * @Since
 * @Deprecated
 */
@Controller
@RequestMapping("/zoning")
public class ZoningDataUploadController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ZoningDataUploadController.class);

    @Autowired
    private ZoningDataUploadApi zoningDataUploadApi;

    @Value("${spring.http.multipart.location}")
    private String filePath;


    /*
     * 测试异常处理
     * */
    @RequestMapping(value = "/exception",method = RequestMethod.GET)
    public void exceptionTest(){
        throw new RuntimeException("我是异常");
    }
    /*
     * 测试上传的页面
     * */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String upload() {
        return "uploads";
    }

    /**
     * @return java.lang.String
     * @throws
     * @description 文件上传
     * @method codeUpload
     * @params [file 上传的文件, request, response]
     */
    @RequestMapping(value = "/upload/zipFile", method = RequestMethod.POST)
    @ResponseBody
    public String codeUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean checked = false;
        //校验文件名称是否正确
        checked = FileUtil.checkFileName(file,filePath);
        if (!checked){
            return new RtnData(Constants.RTN_CODE_ERROR,Constants.RTN_MESSAGE_UPLOAD,"文件名出错！").toString();
        }
        String fileName = file.getOriginalFilename();
        String zipName = fileName.substring(0, fileName.lastIndexOf("."));
        //存储文件名
        String[] arg = new String[4];
        arg = zipName.split("_");
        //存储zip文件信息
        FocusChangeFileInfo fileInfo = new FocusChangeFileInfo();
        fileInfo.setZoningCode(arg[1]);
        fileInfo.setDate(arg[2]);
        fileInfo.setStatusCode("10");
        fileInfo.setInstructionCode("0");
        fileInfo.setNote("我什么都不知道");
        fileInfo.setFileName(fileName);
        fileInfo.setFilePath(filePath + zipName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fileInfo.setEnterTime(dateFormat.format(System.currentTimeMillis()));
        //录入人代码
        fileInfo.setEnterOneCode("000");
        //录入机构代码
        fileInfo.setOrganizationCode("000");


        int fileNum = zoningDataUploadApi.recordFileInfo(fileInfo);
        if (fileNum > 0) {
            return new RtnData().toString();
        }
        return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
    }

    /**
     * @description  查询文件信息；国家查全部、省级查自己
     * @method  queryFocusChangeFileInfo
     * @params [request, response]
     * @return java.lang.Object
     * @exception
     */
    @RequestMapping(value = "/query/fileInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object queryFocusChangeFileInfo(@RequestParam("zoningCode")String zoningCode,
                                           @RequestParam("date")String date,
                                           @RequestParam("pageNum")Integer pageNum,
                                           @RequestParam("pageSize")Integer pageSize,
                                           HttpServletRequest request, HttpServletResponse response) {

        /*获取 页数和 页码*/
        PageHelper.startPage(pageNum, pageSize);
        List<FocusChangeFileInfo> fileInfoList = new ArrayList<>();
        fileInfoList = zoningDataUploadApi.queryFocusChangeFileInfo(zoningCode,date);
        PageHelper.count(()->{
            zoningDataUploadApi.queryFocusChangeFileInfo(zoningCode,date);
        });
        System.out.println(fileInfoList.get(0).getFileSquence());
        for (FocusChangeFileInfo fileInfo : fileInfoList) {
            logger.info(StringUtil.objToJson(fileInfo));
        }
        if (fileInfoList.isEmpty()) {
            //没有查到数据
        }
        return new RtnData(StringUtil.objToJson(fileInfoList)).toString();
    }

    /**
     * @description 判断同名文件是否存在
     * @method  checkFileExist
     * @params [fileName：文件名, zoningCode：区划代码]
     * @return java.lang.String
     * @exception
     */
    @RequestMapping(value = "/check/zipFile",method = RequestMethod.GET)
    @ResponseBody
    public String checkFileExist(@RequestParam("fileName")String fileName,@RequestParam("zoningCode")String zoningCode){

        if (StringUtil.isEmpty(fileName) || StringUtil.isEmpty(zoningCode)){
            return new RtnData(Constants.RTN_CODE_ERROR,Constants.RTN_MESSAGE_ERROR).toString();
        }
        boolean checked = zoningDataUploadApi.checkFileExist(fileName,zoningCode);
        return new RtnData(checked).toString();
    }


    /**
     * @description 删除所选中的文件信息、以及文件数据
     * @method  deleteFile
     * @params [fileSquence：文件序号]
     * @return java.lang.String
     * @exception
     */
    @RequestMapping(value = "delete/zipFile",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteFileInfo(@RequestParam("fileSquence")Integer fileSquence){
        zoningDataUploadApi.deleteFileInfo(fileSquence);
        return new RtnData().toString();
    }


    /**
     * @description 根据文件状态 判断能否被删除
     * @method  checkFileStatus
     * @params [fileSquence：文件序号]
     * @return java.lang.String
     * @exception
     */
    @RequestMapping(value = "/query/fileStatusCode",method = RequestMethod.GET)
    @ResponseBody
    public String checkFileStatus(@RequestParam("fileSquence") Integer fileSquence){

        boolean ckecked = zoningDataUploadApi.checkFileStatus(fileSquence);
        return new RtnData(ckecked).toString();
    }


}
