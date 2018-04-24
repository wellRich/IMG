package com.digital.controller;

import com.digital.api.ImportFormalTableApi;
import com.digital.api.ImportTemporaryApi;
import com.digital.dao.PreviewDataInfoMapper;
import com.digital.entity.PreviewDataInfo;
import com.digital.util.resultData.Constants;
import com.digital.util.resultData.RtnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 10:48 2018/4/10
 * @See
 * @Since
 * @Deprecated
 */
@Controller
@RequestMapping("formal")
public class FormalTableController extends BaseController {

    @Autowired
    private ImportFormalTableApi importFormalTableApi;


    /**
     * @description 导入到正式表
     * @method  importformalTable
     * @params
     * @return
     * @exception
     */
    @RequestMapping(value = "/import/formalTable",method = RequestMethod.GET)
    @ResponseBody
    public String importformalTable(){
        try{
            importFormalTableApi.importformalTable();
            return new RtnData().toString();
        }catch (RuntimeException e){
            return new RtnData(Constants.RTN_CODE_ERROR,Constants.RTN_MESSAGE_ERROR,e.getMessage()).toString();
        }
    }




}
