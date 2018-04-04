package com.digital.api;

import com.digital.entity.PreviewDataInfo;
import com.digital.entity.ZCCDetail;
import com.digital.entity.ZCCGroup;
import com.digital.entity.ZCCRequest;
import com.digital.util.search.QueryResp;

import java.util.List;
import java.util.Map;

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
     * @param zoningCode 区划代码
     * @param pageSize 每页数量
     * @param pageIndex 页码
     * @param totalRecord 数据总数
     * @return dataList实体数据，数据总量totalCount,总页数totalPage,当前页数pageIndex
     */
    Object findZCCReqByZoningLevelCode(String levelCode, String zoningCode, Integer pageIndex, Integer pageSize, Integer totalRecord) throws IllegalAccessException;

    /**
     * 根据区划代码查询申请单
     * 不分页
     * @param zoningCode
     * @return
     */
    List<ZCCRequest> findZCCReqByZoningCode(String zoningCode);


    /**
     * 根据区划代码与状态查询申请单
     * @param zoingCode
     * @param status
     * @return
     */
    List<ZCCRequest> findZCCReqByZCAndStatus(String zoingCode, String status);

    /*
     *
     * 录入变更明细
     * */

    /**
     * 根据区划代码查询区划预览数据
     * @param zoningCode
     * @return
     */
    Map findPreviewByZoningCode(String zoningCode);


    /**
     *
     * @param zoningCode
     * @return
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
     * @param  group  里面是变更对照组的属性键值对（除主键之外的所有属性）
     * @param details 里面是json数组包含若干变更明细数据，具体的结构是ChangeInfo的属性键值对
     * @param  zoingCode 登录用户所属的区划区划代码
     */
    void addDetails(String group, String details, String zoingCode);


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
     * 更新申请单
     * 省级审核、省级确认、国家审核与申请单维护，都通过这个接口实现
     * @param seq 申请单序号
     * @param name 申请单名称
     * @param notes 申请单说明
     */
    void updateZCCRequest(Integer seq, String name, String notes);


    /**
     * 获取变更申请单的明细对照数据,分页
     * @param requestSeq 申请单序号
     * @param pageIndex 页码
     * @param pageSize 每页显示数量
     */
    QueryResp<ZCCDetail> pageSeekByGroups(Integer requestSeq, int pageIndex, int pageSize);



    /**
     * 删除明细数据
     * 〈功能详细描述〉
     * @param groupSeq 明细表序号
     */
    void deleteDetails(Integer groupSeq);

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
     * @param seq 申请单序号
     */
    void provincialCheck(Integer seq);


    /**
     * 省级确认
     * @param seq 申请单序号
     */
    void provincialConfirm(Integer seq);


    /**
     * 国家审核
     * @param seq 申请单序号
     */
    void nationalCheck(Integer seq);


}
