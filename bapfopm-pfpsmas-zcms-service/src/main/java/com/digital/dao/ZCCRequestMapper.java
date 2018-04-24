package com.digital.dao;

import com.digital.dao.sqlMapper.ZCCDetailSql;
import com.digital.dao.sqlMapper.ZCCRequestSql;
import com.digital.entity.ZCCRequest;
import com.digital.util.search.BaseMapper;
import com.digital.util.search.QueryFilter;
import com.digital.util.search.QueryReq;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

/**
 * 变更申请单映射
 * @author guoyka
 * @version 2018/3/15
 * @see ZCCRequest
 */
@Mapper
public interface ZCCRequestMapper extends BaseMapper<ZCCRequest>{
    String RESULT_MAP = "com.digital.entity.allOfZCCRequest";

    @SelectProvider(type = ZCCRequestSql.class, method = BaseMapper.GET)
    @ResultMap(RESULT_MAP)
    ZCCRequest get(Object seq);


    /**
     * 通过区划代码查找变更申请单
    * @param zoningCode 区划代码
    * @return 返回申请单列表
    */
    @Select("SELECT SQDXH, SQDMC, SQDZT_DM, SBXZQH_DM, BZ FROM XZQH_BGSQD WHERE SBXZQH_DM = #{levelCode, jdbcType=CHAR} ORDER BY LRSJ ")
    @ResultMap(RESULT_MAP)
    List<ZCCRequest> findAllByLevelCode(@Param(value = "levelCode") String zoningCode);

    /**
     * 通过区划级别代码与状态查找申请单
     * @param levelCode 级别代码
     * @param statuses 若干申请单状态代码
     * @return 申请单实例
     */
    @SelectProvider(type = ZCCRequestSql.class, method = "findByLevelCodeAndStatuses")
    @ResultMap(RESULT_MAP)
    List<ZCCRequest> findByLevelCodeAndStatuses(String levelCode, String ... statuses);

    /**
     * 根据区划代码统计申请单数量
     * @param levelCode 区划代码
     * @return 数量
     */
    @SelectProvider(type = ZCCRequestSql.class, method = "countByLevelCode")
    @ResultType(Integer.class)
    Integer countByLevelCode(String levelCode);


    /**
     * 根据区划代码统计申请单数量
     * @param statuses 若干申请单状态
     * @return 数量
     */
    @SelectProvider(type = ZCCRequestSql.class, method = "countByStatuses")
    @ResultType(Integer.class)
    Integer countByStatuses(String statuses);

    /**
     * 统计指定级别、状态的申请单数量
     * @param levelCode 级别代码
     * @param statuses 若干状态
     * @return 符合条件的申请单数量
     */
    @SelectProvider(type = ZCCRequestSql.class, method = "countByZoningCodeAndStatus")
    @ResultType(Integer.class)
    Integer countByZoningCodeAndStatus(String levelCode, String ... statuses);

    /** 根据区划代码分页查询申请单
     * @param levelCode 区划代码
     * @param offset 起始
     * @param limit 大小
     * @return
     */
    @SelectProvider(type = ZCCRequestSql.class, method = "pageSeekByLevelCode")
    @ResultMap(RESULT_MAP)
    List<ZCCRequest> pageSeekByLevelCode(String levelCode, int offset, int limit);

    /**
     * 分页查询申请单
     * 通过级别代码与状态过滤
     * @param levelCode 区划级别代码
     * @param offset 起始
     * @param pageSize 每页大小
     * @param statuses 若干状态代码
     * @return 区划列表
     */
    @SelectProvider(type = ZCCRequestSql.class, method = "pageSeekByLevelCodeAndStatuses")
    @ResultMap(RESULT_MAP)
    List<ZCCRequest> pageSeekByLevelCodeAndStatuses(String levelCode, int offset, int pageSize, String ... statuses);

    /**
     * 使用上报行政区划代码与状态查找变更申请单
     * @param zoningCode 区划代码
     * @param status 申请单状态
     * @return 申请单列表
     */
    @Select("SELECT SQDXH, SQDMC, SQDZT_DM, SBXZQH_DM, BZ FROM XZQH_BGSQD WHERE SBXZQH_DM = #{levelCode} and SQDZT_DM < #{status}")
    @ResultMap(RESULT_MAP)
    List<ZCCRequest> findAllByZoningCodeAndStatus(@Param(value = "levelCode") String zoningCode, @Param(value = "status") String status);


    /**
     * 通过级别代码、申请单状态过滤查询变更申请单
     * @param levelCode 级别代码
     * @param offset 分页参数
     * @param limit 分页参数
     * @param statuses 若干状态代码
     * @return 申请单列表
     */
    @SelectProvider(type = ZCCRequestSql.class, method = "pageSeekByStatusesAndLevelCode")
    @ResultMap(RESULT_MAP)
    List<ZCCRequest> pageSeekByStatusesAndLevelCode(String levelCode, int offset, int limit, String ... statuses);

    /**
     * 添加变更申请单
     * @param obj map对象或者ZCCRequest对象
     * @return 申请单主键值
     */
    @InsertProvider(type = ZCCRequestSql.class, method = BaseMapper.INSERT)
    @SelectKey(before = false, resultType = Integer.class, keyColumn = "SQDXH", keyProperty = "seq", statement = "SELECT LAST_INSERT_ID()")
    @Options(useGeneratedKeys = true,keyProperty = "seq",keyColumn = "SQDXH")
    int insert(Object obj);

    /**
    * 修改申请单
    * 若传入一个申请单，则会把它所有的属性更新到数据库
     * 传入一个map，只会更新map中的键值对
    * @param zccRequest 申请单对象或者map
     *@return 被修改的数量
    */
    @UpdateProvider(type = ZCCRequestSql.class, method = BaseMapper.UPDATE)
    int update(Object zccRequest);

    /**
     * 删除申请单
     * @param seq 申请单序号
     * @return 被删除的数量
     */
    @DeleteProvider(type = ZCCRequestSql.class, method = BaseMapper.DELETE)
    @Override
    int delete(Object seq);

    @DeleteProvider(type = ZCCRequestSql.class, method = BaseMapper.BATCH_DELETE)
    @Override
    int batchDelete(Collection<?> keys);

    @SelectProvider(type = ZCCRequestSql.class, method = BaseMapper.FIND_BY_IDS)
    @ResultMap(RESULT_MAP)
    @Override
    List<ZCCRequest> findByIds(String ids);

    @SelectProvider(type = ZCCRequestSql.class, method = BaseMapper.FIND_ALL)
    @ResultMap(RESULT_MAP)
    @Override
    List<ZCCRequest> findAll();

    @SelectProvider(type = ZCCRequestSql.class, method = BaseMapper.PAGE_SEEK)
    @Override
    @ResultMap(RESULT_MAP)
    List<ZCCRequest> pageSeek(QueryReq req, int pageNum, int pageSize);

    @SelectProvider(type = ZCCRequestSql.class, method = BaseMapper.SEEK)
    @Override
    @ResultMap(RESULT_MAP)
    List<ZCCRequest> seek(QueryReq req);

    @SelectProvider(type = ZCCRequestSql.class, method = BaseMapper.COUNT_BY)
    @Override
    int countBy(String field, QueryFilter... filters);
}
