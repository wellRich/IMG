package com.digital.api;

import com.digital.entity.PreviewDataInfo;
import com.digital.entity.ZCCDetail;
import com.digital.entity.ZCCGroup;
import com.digital.entity.ZCCRequest;
import com.digital.util.search.QueryResp;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author [作者]
 * @version 2018/3/15
 * @see [相关类/方法]
 * @since [产品/模块版本]

 */
public interface ZoningCodeChangeApi {

    /*
     * 建立变更对照表
     */

    /**
     *  添加行政区划变更申请单
     * @param obj 申请单对象
     * @return
     */
    int addZCCRequest(ZCCRequest obj);

    /**
     * 根据区划代码查询申请单
     *  做分页
     * @param levelCode 登录用户的所属区划代码的级别代码
     * @param zoningName 区划名称
     * @param pageSize 每页数量
     * @param pageIndex 页码
     * @param totalRecord 数据总数
     * @return dataList实体数据，数据总量totalCount,总页数totalPage,当前页数pageIndex
     */
    Object findZCCReqByZoningLevelCode(String levelCode, String zoningName, Integer pageIndex, Integer pageSize, Integer totalRecord) throws IllegalAccessException;

    /**
     * 根据区划代码查询申请单
     * 不分页
     * @param zoningCode
     * @return
     */
    List<ZCCRequest> findZCCReqByZoningCode(String zoningCode);


    /**
     * 查找未被国家审核的申请单
     * @param levelCode
     * @return List
     */
    List<ZCCRequest> findNotPassNationalCheckReq(String levelCode);

    /*
     *
     * 录入变更明细
     * */

    /**
     * 查找允许录入明细的申请单，状态为未提交或者是审核不通过
     * @param levelCode 区划级别代码
     * @return 申请单列表
     */
    List<ZCCRequest> findWritableZCCRequests(String levelCode);

    /**
     * 根据区划代码查询区划预览数据
     * @param zoningCode
     * @return
     */
    Map findPreviewByZoningCode(String zoningCode) throws IllegalAccessException;


    /**
     * 获取下一级区划预览数据
     * @param zoningCode 代码
     * @return 子级区划代码
     */
    List<PreviewDataInfo> findSubordinateZoning(String zoningCode);

    /**
     * 通过区划代码查找区划预览数据
     * @param zoningCode 区划代码
     * @return PreviewDataInfo
     */
    PreviewDataInfo findOneByZoningCode(String zoningCode);

    /**
     * 录入区划变更明细对照表数据
     * @param creatorCode 创建人代码
     * @param creatorDeptCode 创建人所在机构代码
     * @param  group  里面是变更对照组的属性键值对（除主键之外的所有属性）
     * @param details 里面是json数组包含若干变更明细数据，具体的结构是ChangeInfo的属性键值对
     * @param  zoningCode 登录用户所属的区划区划代码
     */
    void addDetails(String group, String details, String zoningCode, String creatorCode, String creatorDeptCode);


    /**
     * 根据区划代码查找同一父级下的同级区划
     * @param zoningCode 区划代码
     * @return List
     */
    List<PreviewDataInfo> findBrothersByCode(String zoningCode);


    ///////////////////////////////////////////////////////////////////
    /*
     * 维护变更对照申请
     */

    /**
     * 初始化区划变更申请单维护界面
     * @param levelCode 区划级别代码
     * @param zoningName 区划名称
     * @param pageIndex 当前页码
     * @param pageSize 每页总数
     * @param total 查询总数
     * @return 分页查询对象
     */
    Object initMaintainZCCReq(String levelCode, String zoningName, Integer pageIndex, Integer pageSize, Integer total) throws IllegalAccessException;

    /**
     * 更新申请单
     * 省级审核、省级确认、国家审核与申请单维护，都通过这个接口实现
     * @param param 更新信息
     */
    void updateZCCRequest(Map<String, Object> param);


    /**
     * 获取变更申请单的明细对照数据,分页
     * @param requestSeq 申请单序号
     * @param pageIndex 页码
     * @param pageSize 每页显示数量
     */
    QueryResp<ZCCDetail> pageSeekByGroups(Integer requestSeq, int pageIndex, int pageSize);


    /**
     * 导出指定申请单下的区划变更明细对照表
     * @param seq 申请单序号
     * @param response  响应
     * @return 表格
     */
    void exportDetailsOfReq(Integer seq, HttpServletResponse response);

    /**
     * 删除明细数据
     * 〈功能详细描述〉
     * @param groupSeqs 若干明细表序号
     */
    void deleteDetails(String groupSeqs);

   ////////////////////////////////////////////////////////////
    /**
     * 提交变更申请单
     */

    /**
     * 提交变更申请单
     * 〈功能详细描述〉
     * @param seq 申请单序号
     */
    void submitZCCRequest(Integer seq);


    /**
     * 撤销变更申请单
     * 〈功能详细描述〉
     * @param seq 申请单序号
     */
    void revokeZCCRequest(Integer seq);

    /**
     * 添加变更对照组
     〈功能详细描述〉
     * @param groupInfoMap 变更组信息
     */
    int addZCCGroup(ZCCGroup groupInfoMap);

    /**
     * 添加变更对照组
     〈功能详细描述〉
     * @param group 变更组信息
     * @param levelCode 区划级别代码
     */
    int addZCCGroup(String levelCode, ZCCGroup group);


    /**
     * 省级审核
     * @param seqStr 以逗号分隔的若干申请单序号
     * @param isPassed 是否通过审核
     * @param msg 审核意见
     */
    void provincialCheck(String seqStr, boolean isPassed, String msg);


    /**
     * 省级确认
     * @param isPassed 是否审批通过
     * @param msg 审批意见
     * @param seqStr 以逗号分隔的若干申请单序号
     */
    void provincialConfirm(String seqStr, boolean isPassed, String msg);


    /**
     * 国家审核
     * @param seqStr 以逗号分隔的若干申请单序号
     * @param isPassed 是否通过审核
     * @param msg 审批意见
     */
    void nationalCheck(String seqStr, boolean isPassed, String msg);


}
