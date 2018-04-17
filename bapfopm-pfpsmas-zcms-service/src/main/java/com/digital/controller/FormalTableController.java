package com.digital.controller;

import com.digital.api.ImportFormalTableApi;
import com.digital.api.ImportTemporaryApi;
import com.digital.dao.PreviewDataInfoMapper;
import com.digital.entity.PreviewDataInfo;
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


    @RequestMapping(value = "/import/formalTable",method = RequestMethod.GET)
    @ResponseBody
    public void test(@RequestParam("zoningCode")String zoningCode){

    }




}
