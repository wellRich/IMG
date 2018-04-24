package com.digital.controller.cazc;


import com.digital.api.cazc.CivilAffairDataApi;
import com.digital.controller.BaseController;
import com.digital.entity.cazc.CivilAffairDataUpload;
import com.digital.entity.cazc.CivilAffairZoningCode;
import com.digital.service.cazc.CivilAffairUtil;
import com.digital.util.JSONHelper;
import com.digital.util.RedisUtils;
import com.digital.util.StringUtil;
import com.digital.util.resultData.Constants;
import com.digital.util.resultData.RtnData;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangwei
 * @description 行政区划文件上传控制层
 * @date created in 19:10 2018/3/29
 * @throws Exception
 */
@Controller
@RequestMapping("/civilAffair")
public class CivilAffairDataController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CivilAffairDataController.class);

    @Autowired
   private CivilAffairDataApi civilaffairDataApi;

   @Autowired
   private RedisUtils redisUtils;

    @Value("${spring.http.multipart.location}")
    private String filePath;

    private String pdfPath = "E:/YLQHworkspace/pdf/temp/";

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String upload() {
        return "uploads";
    }


    /**
     * 上传民政区划zip文件
     * zip文件命名规范：MZQHDM_YYYYMMDD.zip
     * @param uploadFile
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/upload/zipFlie", method = RequestMethod.POST)
    @ResponseBody
    public String  uploadCivilAffairZip(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request, HttpServletResponse response){
            Boolean flag = true;
            //校验文件名是否符合规范
            flag = CivilAffairUtil.checkName(uploadFile, filePath);
            if(flag) {
                int count = civilaffairDataApi.checkCivilAffairZipName(uploadFile.getOriginalFilename());
                if(count <1) {
                    CivilAffairDataUpload fileInfo = new CivilAffairDataUpload();
                    fileInfo.setFileName(uploadFile.getOriginalFilename());
                    fileInfo.setFilePath(filePath + uploadFile.getOriginalFilename());
                    fileInfo.setComment("成功");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    fileInfo.setEnterTime(new Date());
                    fileInfo.setStatus(10);
                    //插入数据库
                    int num = civilaffairDataApi.insertCivilAffairZip(fileInfo);
                }else
                    return  new RtnData(Constants.RTN_CODE_ERROR,Constants.RTN_MESSAGE_UPLOAD,"文件已存在，请确认后再上传！").toString();
            }else{
                return  new RtnData(Constants.RTN_CODE_ERROR,Constants.RTN_MESSAGE_UPLOAD,"文件名命名不符合规范，请参考正确格式规范：MZQHDM_YYYYMMDD.zip").toString();
            }
           return new RtnData(Constants.RTN_CODE_SUCCESS,Constants.RTN_MESSAGE_SUCCESS,"文件上传成功!").toString();
    }

    /**
     * 查询民政区划上传zip文件的信息
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/selectCivilAffairZip", method = RequestMethod.POST)
    @ResponseBody
    public String selectCivilAffairZip(@RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,pageSize);
        List<CivilAffairDataUpload>  list= civilaffairDataApi.selectCivilAffairZip();
        PageHelper.count(()->{
            civilaffairDataApi.selectCivilAffairZip();
        });

        if (list.isEmpty()) {
            //没有查到数据
        }
       // String result = new RtnData(String.valueOf(count),list).toString();
        String result  =  JSONHelper.toJSON(list);
        return result;
    }
    /**
     * 将民政区划数据导入数据库中
     * zip文件命名规范：MZQHDM_YYYYMMDD.zip
     * @param //filePath
     * @return
     */
    @RequestMapping(value="/importDate",method = RequestMethod.POST)
    @ResponseBody
    public String insertCivilAffairData(@RequestParam("zipXh") Integer zipXh,@RequestParam("filePath") String filePath){
           //判断是否有值 @RequestParam("filePath") String filePath
       //  String filePath = "E:/YLQHworkspace/upload/temp/mzqhdm_20170403.zip";
       int num =  civilaffairDataApi.updateCivilAffairZipStatus(zipXh,20); //20表示处理中
       if(num <= 0){
           return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR, "状态修改失败！").toString();
       }else
       {
           if (!StringUtil.isEmpty(filePath)) {
               //文件内容校验
               List<CivilAffairZoningCode> civilAffairZoningCodes = CivilAffairUtil.getContent(zipXh, filePath);
               if (civilAffairZoningCodes.size() > 0) {
                   //将list拆分成n份
                   List<List<CivilAffairZoningCode>> lists = CivilAffairUtil.averageAssign(civilAffairZoningCodes, 100);
                   try {
                       //遍历拆分后的list,插入到民政区划数据中
                       int resultNum =  civilaffairDataApi.insertCivilAffairDate(lists,civilAffairZoningCodes.size(),zipXh);
                   }catch (RuntimeException e){
                       logger.error("导入数据失败"+e);
                       civilaffairDataApi.updateCivilAffairZipStatus(zipXh,21); //20表示数据导入失败
                       return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR, "导入数据失败！").toString();
                   }

               } else {
                   return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR, "文件内容不符合规范！").toString();
               }
           } else {
               return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR, "文件不存在或路径不正确！").toString();
           }
           return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, "数据导入成功！").toString();
       }
    }

    /**
     * 民政区划数据预览
     * @param superiorZoningCode
     * @return
     */
    @RequestMapping(value = "/selectCAZ",method = RequestMethod.GET)
    @ResponseBody
    public String selectCivilAffairZoningCode(@RequestParam("superiorZoningCode")String superiorZoningCode){
        //将数据返回结果，进行定制化（zoningCode+行政区划代码）
        List<CivilAffairZoningCode> civilAffairZoningCodes =  CivilAffairUtil.changeName(civilaffairDataApi.selectCivilAffairZoningCode(superiorZoningCode));
        return JSONHelper.toJSON(civilAffairZoningCodes);
    }


    /**
     * 民政区划数据导出
     */
    @RequestMapping(value="/downLoadCAZ",method = RequestMethod.GET)
    @ResponseBody
    public void downCivilAffairZoningCode(@RequestParam("superiorZoningCode")String superiorZoningCode,@RequestParam("type")String type,HttpServletResponse response){
        //@RequestParam("superiorZoningCode")String superiorZoningCode,
        //将民政区划数据查出
         List<CivilAffairZoningCode> civilAffairZoningCodeList = civilaffairDataApi.downCivilAffairZoningCode(superiorZoningCode);
        if("excel".equals(type)) {
            CivilAffairUtil.downExecl(superiorZoningCode, civilAffairZoningCodeList, response);
        }else{
            CivilAffairUtil.downPDf(superiorZoningCode, civilAffairZoningCodeList, response,pdfPath);
        }
    }

    /**
     * /**
     * 民政区划数据与行政区划区划数据比较
     * @param id
     * @return
     */
    @RequestMapping(value="/selectCYDate",method = RequestMethod.GET)
    @ResponseBody
    public String selectCYDate(@RequestParam("id") String id){
        List<Map<String,Object>> list = civilaffairDataApi.selectCYDate(id);
         return JSONHelper.toJSON(list);
    }
}
