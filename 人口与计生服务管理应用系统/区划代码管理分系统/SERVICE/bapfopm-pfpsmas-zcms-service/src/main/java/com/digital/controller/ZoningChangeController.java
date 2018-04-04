package com.digital.controller;

import com.digital.api.ZoningCodeChangeApi;
import com.digital.dao.PreviewDataInfoMapper;
import com.digital.entity.PreviewDataInfo;
import com.digital.entity.ZCCRequest;
import com.digital.service.ZoningCodeChangeApiImpl;
import com.digital.util.Common;
import com.digital.util.JSONHelper;
import com.digital.util.resultData.Constants;
import com.digital.util.resultData.RtnData;
import com.digital.util.search.QueryResp;
import org.apache.ibatis.annotations.Param;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区划变更管理
 * 在线上报区划变更
 * @author guoyka
 * @version 2018/3/28
 */
@Controller
@RequestMapping(value = "/zoningChangeManager")
public class ZoningChangeController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ZoningChangeController.class);



    @Autowired
    private ZoningCodeChangeApi zoningCodeChangeApi;


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
            PreviewDataInfo previewDataInfo = zoningCodeChangeApi.findOneByZoningCode(zoningCode);
            model.put("zoningCode", zoningCode);
            model.put("levelCode", levelCode);
            if(previewDataInfo == null){
                throw new RuntimeException("未找到登录用户所属的区划数据！");
            }else {
                model.put("zoningName", previewDataInfo.getDivisionName());
            }
            log.info("buildRequest--------> " + previewDataInfo);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, model).toString();
        }catch (Exception ex){
            log.error("buildRequest.error---> " + ex.getMessage(), ex);
            return new  RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }

    }


    /**
     * 查询出登录用户的申请单，分页
     * @param zoningName 区划名称
     * @param levelCode 级别代码
     * @param pageIndex 页码
     * @param total 总数
     * @return QueryResp 查询结果
     */
    @RequestMapping(value = "/ZoningChangeRequestList", method = RequestMethod.GET)
    @ResponseBody
    public Object ZoningChangeRequestList(@RequestParam(value = "zoningName")String zoningName, @RequestParam(value = "levelCode", required = true)String levelCode, @RequestParam(value = "pageIndex", defaultValue = "1")Integer pageIndex, @RequestParam(value = "total", defaultValue = "0")Integer total){
        log.info("ZoningChangeRequestList.levelCode----> " + levelCode);
        try{
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, zoningCodeChangeApi.findZCCReqByZoningLevelCode(levelCode, zoningName, pageIndex, 5, total) ).toString();
        }catch (Exception e) {
            log.error("ZoningChangeRequestList---> " + e.getMessage());
            return new  RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }


    /**
     * 添加申请单
     * @param obj 申请单表单对象
     * @return String
     */
    @RequestMapping(value = "/addZoningChangeRequest", method = RequestMethod.POST)
    @ResponseBody
    public Object addZoningChangeRequest(ZCCRequest obj){
        log.info("addZoningChangeRequest");
        try {
            int key = zoningCodeChangeApi.addZCCRequest(obj);
            log.info("addZoningChangeRequest.key-----> " + key);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS).toString();
        }catch (Exception ex){
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }



    /**
     * 初始化明细录入页面
     * 按区划级次分成六个列表
     * 只有登录用户所在区划的下级区划是可以点击的，点击加载被点击的区划的下级区划
     */
    @RequestMapping(value = "/initAddDetails", method = RequestMethod.GET)
    @ResponseBody
    public Object intAddDetails(){
        try {
            String zoningCode = "370102000000000";

            //级次代码
            String assigningCode = Common.getAssigningCode(zoningCode);

            //查找申请单，如果没有找到，则返回信息，请先建立申请单
            log.info("zoningCode---------> " + zoningCode);
            Map<String, Object> result = new HashMap<>();
            result.put("assigningCode", assigningCode);
            result.put("zoningCode", zoningCode);
            result.put("previewData", zoningCodeChangeApi.findPreviewByZoningCode(zoningCode));
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, result).toString();
        }catch (Exception ex){
            log.error(ex.getMessage());
            ex.printStackTrace();
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }

    /**
     * 获取子级区划
     * @param zoningCode 区划代码
     * @return map 下级区划
     */
    @RequestMapping(value = "/getSubordinateZoning", method = RequestMethod.GET)
    @ResponseBody
    public Object getSubordinateZoning(@Param(value = "zoningCode")String zoningCode){
        try {
            String assigningCode = Common.getAssigningCode(zoningCode);
            Map<String, Object> result = new HashMap<>();
            List<PreviewDataInfo> previewDataInfos = zoningCodeChangeApi.findSubordinateZoning(zoningCode);
            result.put(assigningCode, previewDataInfos);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, result).toString();
        }catch (Exception ex){
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }

    /**
     * 获取同一父级下的同级
     * @param zoningCode 当前操作的区划的区划代码
     * @return
     */
    @RequestMapping(value = "/getBrothersZoning", method = RequestMethod.GET)
    @ResponseBody
    public Object getBrotherZoning(@Param(value = "zoningCode")String zoningCode){
        try {
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, zoningCodeChangeApi.findBrothersByCode(zoningCode)).toString();
        }catch (Exception ex){
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }


    /**
     * 添加变更对照明细
     * @param group json变更组信息
     * @param details json变更对照明细
     * @param zoningCode 区划代码
     * @return RtnData 处理结果
     */
    @RequestMapping(value = "/saveDetails", method = RequestMethod.POST)
    @ResponseBody
    public Object saveDetails(@Param(value = "group")String group, @Param(value = "details")String details, @Param(value = "zoningCode")String zoningCode){
        try {
            log.info("saveDetails.zoningCode---------> " + zoningCode);
            zoningCodeChangeApi.addDetails(group, details, zoningCode);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS).toString();
        }catch (Exception ex){
            log.error(ex.getMessage());
            ex.printStackTrace();
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }



    /**
     * 查看变更申请单下的变更明细对照数据
     * @param seq 申请单序号
     * @return RtnData
     */
    @RequestMapping(value = "/checkDetails", method = RequestMethod.GET)
    @ResponseBody
    public Object checkDetails(@Param(value = "seq")Integer seq, @RequestParam(value = "pageIndex", defaultValue = "1")int pageIndex, @RequestParam(value = "pageSize", defaultValue = "10")int pageSize){
        try {
            //zoningCodeChangeApi.getDetails(seq);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, zoningCodeChangeApi.pageSeekByGroups(seq, pageIndex, pageSize)).toString() ;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }

    /**
     * 以excel形式导出指定申请单下的变更对照明细数据
     *
     */
    public Object exportDetailsOfReq(){
        return null;
    }


    /**
     * 更新申请单
     * @return RtnData
     */
    public Object updateReq(){
        return null;
    }

    /**
     * 删除指定组的明细数据
     * @return
     */
    public Object deleteDetailsOfGroup(){
        return null;
    }


    /**
     * 省级审核
     */

    public Object provincialCheck(){
        return null;
    }

    /**
     * 、省级确认
     */


    /**
     * 国家审核
     */
    public Object nationalCheck(){
        return null;
    }

}
