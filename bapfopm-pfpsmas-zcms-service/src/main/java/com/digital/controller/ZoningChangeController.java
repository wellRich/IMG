package com.digital.controller;

import com.digital.dao.PreviewDataInfoMapper;
import com.digital.entity.PreviewDataInfo;
import com.digital.entity.ZCCRequest;
import com.digital.service.ZoningCodeChangeApiImpl;
import com.digital.util.Common;
import com.digital.util.JSONHelper;
import com.digital.util.resultData.Constants;
import com.digital.util.resultData.RtnData;
import com.digital.util.search.QueryResp;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 区划变更管理
 * 在线上报区划变更
 * @author guoyka
 * @version 2018/3/28
 */
@Controller
@RequestMapping(value = "/zoningChange")
public class ZoningChangeController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ZoningChangeController.class);



    @Autowired
    ZoningCodeChangeApiImpl zoningCodeChangeApi;

    @Autowired
    PreviewDataInfoMapper previewDataInfoMapper;

    //初始化变更申请单建立页面
    @RequestMapping(value = "/buildRequest", method = RequestMethod.GET)
    @ResponseBody
    public Object buildRequest(){
       try {
           //传入登录人的区划代码
           String zoningCode = "370102000000000";

           //级别代码
           String levelCode = Common.getLevelCode(zoningCode);

           //级次代码
           String assigningCode = Common.getAssigningCode(zoningCode);

           //单位隶属关系
           String unitRelationShip = "20";
           Map<String, Object> model = new HashMap<>();
           PreviewDataInfo previewDataInfo = previewDataInfoMapper.findOneByZoningCode(zoningCode);
           model.put("zoningCode", zoningCode);
           model.put("levelCode", levelCode);
           if(previewDataInfo == null){
               throw new RuntimeException("未找到登录用户所属的区划数据！");
           }else {
               model.put("zoningName", previewDataInfo.getDivisionName());
           }
           log.info("buildRequest--------> " + previewDataInfo);
           return JSONHelper.toJSON(new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, model));
       }catch (Exception ex){
           log.error("buildRequest.error---> " + ex.getMessage(), ex);
           return JSONHelper.toJSON(new  RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR));
       }

    }

    //查询出登录用户的历史申请单，分页
    @RequestMapping(value = "/ZoningChangeRequestList", method = RequestMethod.GET)
    @ResponseBody
    public Object ZoningChangeRequestList(@RequestParam(value = "zoningName")String zoningName, @RequestParam(value = "levelCode", required = true)String levelCode, @RequestParam(value = "pageIndex", defaultValue = "1")Integer pageIndex, @RequestParam(value = "total", defaultValue = "0")Integer total){
        log.info("ZoningChangeRequestList.levelCode----> " + levelCode);
        try{
            return JSONHelper.toJSON(new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, zoningCodeChangeApi.findZCCReqByZoningLevelCode(levelCode, zoningName, pageIndex, 5, total) ), RtnData.class);
        }catch (Exception e) {
          log.error("ZoningChangeRequestList---> " + e.getMessage());
           return JSONHelper.toJSON(new  RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR));
        }
    }


    @RequestMapping(value = "/addZoningChangeRequest", method = RequestMethod.POST)
    public String addZoningChangeRequest(ZCCRequest obj){
        log.info("addZoningChangeRequest");
        try {
            zoningCodeChangeApi.addZCCRequest(obj);
            return "redirect:/zoningChange/ZoningChangeRequestList?zoningCode=" + obj.getOwnZoningCode();
        }catch (Exception ex){
            log.error(ex.getMessage());
            return JSONHelper.toJSON(new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR));
        }
    }

}
