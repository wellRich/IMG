package com.digital.api;

import com.digital.entity.PreviewDataInfo;
import com.digital.entity.ZCCDetail;
import com.digital.entity.ZCCGroup;
import com.digital.entity.ZCCRequest;
import com.digital.util.search.QueryReq;
import com.digital.util.search.QueryResp;
import com.sun.javafx.collections.MappingChange;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 行政区划在线变更管理
 *
 * @author guoyka
 * @version 1.0, 2018/3/15
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
     * 通过区划代码查找申请单
     * 数据用来初始化申请单创建页面
     * @param zoningCode 区划代码
     * @param pageSize 每页数据量
     * @param pageIndex 请求的页码
     * @param totalRecord 数据总量，首次查询为空，随后的查询，就从前台传来
     * @return 申请单列表，分页
     */
    Object findZCCReqByZoningCode(String zoningCode, Integer pageIndex, Integer pageSize, Integer totalRecord) throws IllegalAccessException;




    /**
     * 查找指定区划的未被国家审核的申请单
     * @param levelCode 区划级别代码
     * @return List
     */
    List<ZCCRequest> findNotPassNationalCheckReq(String levelCode);


    /**
     *  国家用户或者省级用户查看未提交的申请单
     * @param levelCode 级别代码
     * @param pageIndex 页码
     * @param pageSize 每页大小
     * @param total 总数
     * @return list
     */
    Object checkNotSubmitReq(String levelCode, Integer pageIndex, Integer pageSize, Integer total);


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
     *  查询若干级次的区划预览数据，限定在指定根区划的子孙区划
     * @param ownZoning 登录用户所属区划
     * @param assigningCodes  若干目标级次
     * @return
     */
    List<PreviewDataInfo> findByAssigningCodesAndRootZoning(String ownZoning, String superiorZoningCode, String assigningCodes);

        /**
         * 将指定区划预览数据的上级与上级拼成两级的树形结构，
         * 数据范围限定在登录用户所属的区划的子孙区划中
         * @param ownZoningCode 登录用户所属区划代码
         * @param originalZoningCode 操作区划的原区划代码
         * @return 层次结构
         */
    List<Map> getTowLevelTree(String ownZoningCode, String originalZoningCode);


    /**
     * 根据区划代码查询区划预览数据
     * @param zoningCode 区划代码
     * @return [级次代码: 相应的若干区划数据]
     * @throws IllegalAccessException
     */
    Map findPreviewByZoningCode(String zoningCode) throws IllegalAccessException;



    /**
     * 获取下一级区划预览数据
     * @param zoningCode 代码
     * @return map，子级区划代码的键值对，加上父级的级别代码键值对[superiorLevelCode: '']
     * @throws IllegalAccessException
     */
    List<?> findSubordinateZoning(String zoningCode) throws IllegalAccessException;

    /**
     * 通过区划代码查找区划预览数据
     * @param zoningCode 区划代码
     * @return 区划预览数据实体
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


    /**
     * 获取上级区划
     * 登录用户区划的子孙区划中级次高一级的区划，排除原父级区划
     * @param  zoningCode 区划代码
     * @param  ownZoningCode 登录人所属的区划代码
     * @return      list
     */
    List<PreviewDataInfo> findZoningesOfUncle(String zoningCode, String ownZoningCode);


    ///////////////////////////////////////////////////////////////////
    /*
     * 维护变更对照申请
     */


    /**
     * 更新申请单
     * 省级审核、省级确认、国家审核与申请单维护，都通过这个接口实现
     * @param param 更新信息
     */
    void updateZCCRequest(Map<String, Object> param);


    /**
     * 获取变更申请单的明细对照数据,分页
     * @param total 数据总数
     * @param requestSeq 申请单序号
     * @param pageIndex 页码
     * @param pageSize 每页显示数量
     * @throws IllegalAccessException
     * @return 分页封装对象
     */
    QueryResp<?> pageSeekByGroups(Integer requestSeq, int pageIndex, int pageSize, int total) throws IllegalAccessException;


    /**
     * 导出指定申请单下的区划变更明细对照表
     * @param seq 申请单序号
     * @param response  响应
     * @return excel表格下载
     */
    void exportDetailsOfReq(Integer seq, HttpServletResponse response);

    /**
     * 删除明细数据
     * 对于申请单状态是10（未提交省级审核）与 21（审核不通过）的申请单，
     * 可以删除申请单下的区划变更对照明细
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
     * 分页查询申请单
     * @param req 查询封装对象
     * @param pageIndex 当前页
     * @param pageSize 每页大小
     * @param total 数据总量
     * @return 分页封装对象
     */
    QueryResp<ZCCRequest> pageSeek(QueryReq req, int pageIndex, int pageSize, int total);

    /**
     * 撤销变更申请单
     * 〈功能详细描述〉
     * @param seq 申请单序号
     */
    void revokeZCCRequest(Integer seq);

    /**
     * 添加变更对照组
     * @param groupInfoMap 变更组信息
     */
    int addZCCGroup(ZCCGroup groupInfoMap);

    /**
     * 添加变更对照组
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
