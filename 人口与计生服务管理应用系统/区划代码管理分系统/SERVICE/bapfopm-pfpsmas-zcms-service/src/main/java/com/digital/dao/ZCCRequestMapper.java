package com.digital.dao;

import com.digital.dao.sqlMapper.ZCCDetailSql;
import com.digital.dao.sqlMapper.ZCCGroupSql;
import com.digital.dao.sqlMapper.ZCCRequestSql;
import com.digital.entity.ZCCRequest;
import com.digital.util.search.QueryResp;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;

/**
 * 变更申请单映射
 * @author guoyka
 * @version 2018/3/15
 * @see ZCCRequest
 */
@Mapper
public interface ZCCRequestMapper {

    @SelectProvider(type = ZCCRequestSql.class, method = "get")
    @Results({
            @Result(property = "seq", column = "SQDXH", javaType = Integer.class),
            @Result(property = "name", column = "SQDMC", javaType = String.class),
            @Result(property = "status", column = "SQDZT_DM", javaType = String.class),
            @Result(property = "ownZoningCode", column = "SBXZQH_DM", javaType = String.class),
            @Result(property = "notes", column = "BZ", javaType = String.class)
    })
    ZCCRequest get(Integer seq);


    /**
     * 通过区划代码查找变更申请单
    * @param zoingCode 区划代码
    * @return 返回申请单列表
    */
    @Select("SELECT SQDXH, SQDMC, SQDZT_DM, SBXZQH_DM, BZ FROM XZQH_BGSQD WHERE SBXZQH_DM = #{zoingCode, jdbcType=CHAR} ORDER BY LRSJ ")
    @Results(id = "findAll", value = {
            @Result(property = "seq", column = "SQDXH", javaType = Integer.class),
            @Result(property = "name", column = "SQDMC", javaType = String.class),
            @Result(property = "status", column = "SQDZT_DM", javaType = String.class),
            @Result(property = "ownZoningCode", column = "SBXZQH_DM", javaType = String.class),
            @Result(property = "notes", column = "BZ", javaType = String.class)
    })
    List<ZCCRequest> findAllByZoningCode(@Param(value = "zoingCode") String zoingCode);

    /**
     * 根据区划代码统计申请单数量
     * @param zoningCode 区划代码
     * @return
     */
    @SelectProvider(type = ZCCRequestSql.class, method = "countByZoningCode")
    int countByZoningCode(String zoningCode);

    /** 根据区划代码分页查询申请单
     * @param zoningCode 区划代码
     * @param offset 起始
     * @param limit 大小
     * @return
     */
    @SelectProvider(type = ZCCRequestSql.class, method = "pageSeekByZoningCode")
    @ResultMap(value = "findAll")
    List<ZCCRequest> pageSeekByZoningCode(String zoningCode, int offset, int limit);


    Page<ZCCRequest> pageSeek(String zoningCode);
    /**
     * 使用上报行政区划代码与状态查找变更申请单
     * @param zoingCode 区划代码
     * @param status 申请单状态
     * @return 申请单列表
     */
    @Select("SELECT SQDXH, SQDMC, SQDZT_DM, SBXZQH_DM, BZ FROM XZQH_BGSQD WHERE SBXZQH_DM = #{ownZoningCode} and SQDZT_DM < #{status}")
    @ResultMap(value = "findAll")
    List<ZCCRequest> findAllByZoningCodeAndStatus(@Param(value = "ownZoningCode") String zoingCode, @Param(value = "status") String status);


    /**
     * 添加变更申请单
     * @param obj map对象或者ZCCRequest对象
     * @return 申请单主键值
     */
    @InsertProvider(type = ZCCRequestSql.class, method = "insert")
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
    @UpdateProvider(type = ZCCDetailSql.class, method = "update")
    int update(Object zccRequest);

    /**
     * 删除申请单
     * @param seq 申请单序号
     * @return 被删除的数量
     */
    @DeleteProvider(type = ZCCRequestSql.class, method = "delete")
    int delete(Integer seq);
}
