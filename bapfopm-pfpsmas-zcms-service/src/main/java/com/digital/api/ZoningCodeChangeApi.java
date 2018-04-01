package com.digital.api;

import com.digital.entity.ZCCDetail;
import com.digital.entity.ZCCRequest;

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
     * @param zoningName 登录用户的所属区划代码的区划名称
     * @param pageSize 每页数量
     * @param pageIndex 页码
     * @param totalRecord 数据总数
    * @return dataList实体数据，数据总量totalCount,总页数totalPage,当前页数pageIndex
    */
    Object findZCCReqByZoningLevelCode(String levelCode, String zoningName, Integer pageIndex, Integer pageSize, Integer totalRecord) throws IllegalAccessException;

    /**
    * 根据区划代码查询申请单
    * 不分页
    * @param zoingCode
    * @return
    */
    List<ZCCRequest> findZCCReqByZoningCode(String zoingCode);


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
     * @param zoingCode
     * @return
     */
    String findPreviewByZoingCode(String zoingCode);

    /**
    * 录入区划变更明细对照表数据
    * @param  group  里面是变更对照组的属性键值对（除主键之外的所有属性）
     * @param details 里面是json数组包含若干变更明细数据，具体的结构是ChangeInfo的属性键值对
     * @param  zoingCode 登录用户所属的区划区划代码
    */
    void addDetails(String group, String details, String zoingCode);



    /*
     * 维护变更对照申请
     */


    /**
    * 修改申请单
    * 〈功能详细描述〉
    * @param req 申请单序号
    */
    void updateZCCRequest(Map<String, Object> req);


    /**
     * 获取变更申请单的明细对照数据
     * @param requestSeq 申请单序号
     */
    List<ZCCDetail> getDetails(Integer requestSeq);



    /**
    * 删除明细数据
    * 〈功能详细描述〉
    * @param groupSeq 明细表序号
    */
    void deleteDetails(Integer groupSeq);

    /**
     * 批量删除明细数据
     * 〈功能详细描述〉
     * @param requestSeq 明细表序号
     */

    void deleteByGroupSeqs(Integer requestSeq);
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
    int addZCCGroup(Map<String,Object> groupInfoMap);
}
