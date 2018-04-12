package com.digital.controller;

import com.digital.api.ZoningCodeChangeApi;
import com.digital.dao.PreviewDataInfoMapper;
import com.digital.entity.PreviewDataInfo;
import com.digital.entity.ZCCRequest;
import com.digital.service.ZoningCodeChangeApiImpl;
import com.digital.util.Common;
import com.digital.util.JSONHelper;
import com.digital.util.StringUtil;
import com.digital.util.resultData.Constants;
import com.digital.util.resultData.RtnData;
import com.digital.util.search.QueryResp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.tools.ant.taskdefs.EchoXML;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区划变更管理
 * 在线上报区划变更
 *
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
    public Object buildRequest() {
        try {
            //传入登录人的区划代码
            String zoningCode = "370102000000000";
            Map<String, Object> model = new HashMap<>();
            PreviewDataInfo previewDataInfo = zoningCodeChangeApi.findOneByZoningCode(zoningCode);
            model.put("zoningCode", zoningCode);
            if (previewDataInfo == null) {
                throw new RuntimeException("未找到登录用户所属的区划数据！");
            } else {
                model.put("zoningName", previewDataInfo.getDivisionName());
                model.put("levelCode", previewDataInfo.getLevelCode());
            }
            log.info("buildRequest--------> " + previewDataInfo);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, model).toString();
        } catch (Exception ex) {
            log.error("buildRequest.error---> " + ex.getMessage(), ex);
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }


    /**
     * 查询出登录用户的申请单，分页
     * 区划代码、登录人信息将来是从session中取得的
     * 暂时做为参数传入，并设置了默认值——山东历下区的区划代码，
     * 如果是省级用户，可以传入370000000000000
     * 是否需要增加创建人代码作为过滤条件 ======>
     *
     * @param pageIndex 页码
     * @param pageSize  每页总数
     * @param total     总数
     * @return QueryResp 查询结果
     */
    @RequestMapping(value = "/ZoningChangeRequestList", method = RequestMethod.GET)
    @ResponseBody
    public Object ZoningChangeRequestList(@RequestParam(value = "zoningCode", required = false, defaultValue = "370102000000000") String zoningCode
            , @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex
            , @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
            , @RequestParam(value = "total", defaultValue = "0") Integer total) {
        try {
            log.info("ZoningChangeRequestList.zoningCode----> " + zoningCode);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, zoningCodeChangeApi.findZCCReqByZoningCode(zoningCode, pageIndex, pageSize, total)).toString();
        } catch (Exception e) {
            log.error("ZoningChangeRequestList---> " + e.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }


    /**
     * 添加申请单
     *
     * @param obj 申请单表单对象
     * @return String
     */
    @RequestMapping(value = "/addZoningChangeRequest", method = RequestMethod.POST)
    @ResponseBody
    public Object addZoningChangeRequest(ZCCRequest obj) {
        log.info("addZoningChangeRequest");
        try {
            String creatorCode = "9527";
            String deptCode = "10000";
            obj.setCreatorCode(creatorCode);
            obj.setCreatorDeptCode(deptCode);
            obj.setCreateDate(StringUtil.getTime());
            zoningCodeChangeApi.addZCCRequest(obj);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS).toString();
        } catch (Exception ex) {
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
    public Object intAddDetails(@RequestParam(value = "zoningCode", required = false, defaultValue = "370102000000000") String zoningCode) {
        try {

            //级次代码
            String assigningCode = Common.getAssigningCode(zoningCode);

            //级别代码
            String levelCode = Common.getLevelCode(zoningCode);
            log.info("initAddDetails.levelCOde--------> " + levelCode);

            //查找申请单，如果没有找到，则返回信息，请先建立申请单
            List<ZCCRequest> zccRequests = zoningCodeChangeApi.findWritableZCCRequests(levelCode);
            int size = zccRequests.size();
            if (size == 0) {
                return new RtnData(Constants.RTN_CODE_ERROR, "系统中不存在可供录入变更明细的区划变更申请单，请先创建变更申请单！").toString();
            } else if (size == 1) {
                Map<String, Object> result = new HashMap<>();
                result.put("assigningCode", assigningCode);
                result.put("seq", zccRequests.get(0).getSeq());
                result.put("zoningCode", zoningCode);
                result.put("previewData", zoningCodeChangeApi.findPreviewByZoningCode(zoningCode));
                return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, result).toString();
            } else {
                return new RtnData(Constants.RTN_CODE_ERROR, "系统中存在" + size + "个未经过国家审核的区划变更申请单，请联系管理员！").toString();
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }

    /**
     * 获取子级区划
     *
     * @param zoningCode 区划代码
     * @return map 下级区划
     */
    @RequestMapping(value = "/getSubordinateZoning", method = RequestMethod.GET)
    @ResponseBody
    public Object getSubordinateZoning(@Param(value = "zoningCode") String zoningCode) {
        try {
            String assigningCode = Common.getAssigningCode(zoningCode);
            log.info("asss-----> " + assigningCode);
            Map<Object, Object> result = new HashMap<>();
            result.put(Integer.valueOf(assigningCode) + 1, zoningCodeChangeApi.findSubordinateZoning(zoningCode));
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, result).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }

    /**
     * 获取同一父级下的同级区划，称同胞兄弟区划
     *
     * @param zoningCode 当前操作的区划的区划代码
     * @return
     */
    @RequestMapping(value = "/getBrothersZoning", method = RequestMethod.GET)
    @ResponseBody
    public Object getBrotherZoning(@Param(value = "zoningCode") String zoningCode) {
        try {
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, zoningCodeChangeApi.findBrothersByCode(zoningCode)).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }


    /**
     * 添加变更对照明细
     *
     * @param group      json变更组信息
     * @param details    json变更对照明细
     * @param zoningCode 区划代码
     * @return RtnData 处理结果
     */
    @RequestMapping(value = "/saveDetails", method = RequestMethod.POST)
    @ResponseBody
    public Object saveDetails(@Param(value = "group") String group, @Param(value = "details") String details, @Param(value = "zoningCode") String zoningCode) {
        try {
            log.info("saveDetails.zoningCode---------> " + zoningCode);
            String creatorCode = "9527";
            String creatorDeptCode = "10000";
            zoningCodeChangeApi.addDetails(group, details, zoningCode, creatorCode, creatorDeptCode);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, ex.getMessage()).toString();
        }
    }


    /**
     * 查看变更申请单下的变更明细对照数据
     *
     * @param seq 申请单序号
     * @return RtnData
     */
    @RequestMapping(value = "/checkDetails", method = RequestMethod.GET)
    @ResponseBody
    public Object checkDetails(@Param(value = "seq") Integer seq
            , @RequestParam(value = "total", defaultValue = "0") int total
            , @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex
            , @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, zoningCodeChangeApi.pageSeekByGroups(seq, pageIndex, pageSize, total)).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }

    /**
     * 以excel形式导出指定申请单下的变更对照明细数据
     */
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    @ResponseBody
    public void exportDetailsOfReq(@Param("seq") Integer seq, HttpServletResponse response) {
        try {
            zoningCodeChangeApi.exportDetailsOfReq(seq, response);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }


    /**
     * 更新申请单
     *
     * @return RtnData
     */
    @RequestMapping(value = "/updateZCCRequest", method = RequestMethod.GET)
    @ResponseBody
    public Object updateZCCReq(@Param("seq") Integer seq, @Param("name") String name, @Param("notes") String notes) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("lastUpdate", StringUtil.getTime());
            param.put("creatorCode", "");
            param.put("updaterDeptCode", "8789");
            param.put("notes", notes);
            param.put("name", name);
            param.put("seq", seq);
            zoningCodeChangeApi.updateZCCRequest(param);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS).toString();
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }

    /**
     * 删除指定组的明细数据
     *
     * @return RtnData
     */
    @RequestMapping(value = "/deleteDetails", method = RequestMethod.GET)
    @ResponseBody
    public Object deleteDetailsOfGroup(@RequestParam(value = "groupSeqs") String groupSeqs) {
        try {
            zoningCodeChangeApi.deleteDetails(groupSeqs);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }


    /**
     * 提交省级审核
     */
    @RequestMapping(value = "/submitToCheck", method = RequestMethod.GET)
    @ResponseBody
    public Object submitToCheck(@Param("seq") Integer seq) {
        try {
            zoningCodeChangeApi.submitZCCRequest(seq);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }

    /**
     * 撤销申请单
     */
    @RequestMapping(value = "/revokeToCheck", method = RequestMethod.GET)
    @ResponseBody
    public Object revokeZCCRequest(@Param("seq") Integer seq) {
        try {
            zoningCodeChangeApi.revokeZCCRequest(seq);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }

    /**
     * 省级审核
     * 是否还有一个审批意见，需要更新到申请单上
     *
     * @param seqStr   若干申请单序号，以逗号分隔
     * @param isPassed 是否通过
     * @param msg      审核意见
     */
    @RequestMapping(value = "/provincialCheck", method = RequestMethod.GET)
    @ResponseBody
    public Object provincialCheck(@RequestParam(value = "seqStr") String seqStr, @RequestParam(value = "isPassed") boolean isPassed, @RequestParam(value = "msg", defaultValue = "审核通过！") String msg) {
        try {
            zoningCodeChangeApi.provincialCheck(seqStr, isPassed, msg);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }

    /**
     * 省级确认
     */
    @RequestMapping(value = "/provincialConfirm", method = RequestMethod.GET)
    @ResponseBody
    public Object provincialConfirm(@RequestParam(value = "seqStr") String seqStr, @RequestParam(value = "isPassed") boolean isPassed, @RequestParam(value = "msg", defaultValue = "审核通过！") String msg) {
        try {
            zoningCodeChangeApi.provincialConfirm(seqStr, isPassed, msg);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }

    /**
     * 国家审核
     */
    @RequestMapping(value = "/nationalCheck", method = RequestMethod.GET)
    @ResponseBody
    public Object nationalCheck(@RequestParam(value = "seqStr") String seqStr, @RequestParam(value = "isPassed") boolean isPassed, @RequestParam(value = "msg", defaultValue = "审核通过！") String msg) {
        try {
            zoningCodeChangeApi.nationalCheck(seqStr, isPassed, msg);
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }


    /**
     * 国家查看未提交的省级申请单
     */
    @RequestMapping(value = "/listOfNotSubmitReq", method = RequestMethod.GET)
    @ResponseBody
    public Object listOfNotSubmitReq(@RequestParam(value = "total", defaultValue = "0") int total
            , @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex
            , @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, zoningCodeChangeApi.findNotSubmitReq(pageIndex, pageSize, total)).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
        }
    }

}
