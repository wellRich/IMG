package com.digital.controller;

import com.digital.api.FormalCodeReleaseApi;
import com.digital.entity.FormalCodeReleaseInfo;
import com.digital.util.resultData.Constants;
import com.digital.util.resultData.RtnData;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 18:15 2018/4/24
 * @See
 * @Since
 * @Deprecated
 */
@Controller
@RequestMapping("release")
public class FormalCodeReleaseController {

    @Autowired
    private FormalCodeReleaseApi formalCodeReleaseApi;
    /**
     * @description 查看区划发布信息 || 按照时间查询
     * @method  selectFormalReleaseByExportNum
     * @params exportDate：导出时间；deadline ：截至时间
     * @return
     * @exception
     */
    @RequestMapping(value = "/query/formalCode",method = RequestMethod.GET)
    @ResponseBody
    public String selectFormalReleaseByExportNum(String exportDate, String deadline, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum){

        PageHelper.startPage(pageNum,pageSize);
        List<FormalCodeReleaseInfo> formalCodeReleaseInfos = formalCodeReleaseApi.selectFormalReleaseByExportNum(exportDate, deadline);
        long count = PageHelper.count(()->{
            formalCodeReleaseApi.selectFormalReleaseByExportNum(exportDate, deadline);
        });
        if (formalCodeReleaseInfos!=null)
            return new RtnData(String.valueOf(count),formalCodeReleaseInfos).toString();
        return new RtnData(Constants.RTN_CODE_ERROR,Constants.RTN_QUERY_ISEMPTY).toString();
    }

    /**
     * @description 下载全国区划代码zip
     * @method downloadFormalRelease
     * @params response 下载；zipPath： 下载路径
     * @return
     * @exception
     */
    @RequestMapping(value = "/download/formalCodeZip",method = RequestMethod.GET)
    @ResponseBody
    public String downloadFormalRelease(HttpServletResponse response,@RequestParam("zipPath") String zipPath){
        if (zipPath!=null){
            zipPath = zipPath.replaceAll("\\\\","/");
        }else{
            return new RtnData(Constants.RTN_CODE_ERROR,Constants.RTN_PATH_ISMPTY).toString();
        }
        File file = new File(zipPath);
        if (!file.exists()){
            return new RtnData(Constants.RTN_CODE_ERROR,Constants.RTN_FILE_EXIST).toString();
        }
        try{
            formalCodeReleaseApi.downloadFormalRelease(response,zipPath);
        }catch (RuntimeException e){
            return new RtnData(Constants.RTN_CODE_ERROR,e.getMessage()).toString();
        }
        return new RtnData().toString();
    }

}
