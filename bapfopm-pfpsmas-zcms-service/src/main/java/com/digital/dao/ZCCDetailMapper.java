package com.digital.dao;

import com.digital.dao.sqlMapper.ZCCDetailSql;
import com.digital.entity.ZCCDetail;
import com.digital.entity.ZCCGroup;
import com.digital.util.search.QueryFilter;
import com.digital.util.search.QueryReq;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author [作者]
 * @version 2018/3/19
 * @see [相关类/方法]
 * @since [产品/模块版本]
 
 */
@Mapper
public interface ZCCDetailMapper {
    static final String RESULT_MAP =  "com.digital.entity.allOfZCCDetail";
    @SelectProvider(type = ZCCDetailSql.class, method = "get")
    @Results(id = "get", value = {
            @Result(property = "seq", column = "MXBXH", javaType = Integer.class),
            @Result(property = "groupSeq", column = "GROUPXH", javaType = Integer.class),
            @Result(property = "originalZoningCode", column = "YSXZQH_DM", javaType = String.class),
            @Result(property = "originalZoningName", column = "YSXZQH_MC", javaType = String.class),
            @Result(property = "changeType", column = "BGLX_DM", javaType = String.class),
            @Result(property = "currentZoningCode", column = "MBXZQH_DM", javaType = String.class),
            @Result(property = "status", column = "CLZT_DM", javaType = String.class),
            @Result(property = "result", column = "CLJG", javaType = String.class),
            @Result(property = "orderNum", column = "PXH", javaType = String.class),
            @Result(property = "currentZoningName", column = "MBXZQH_MC", javaType = String.class),
            @Result(property = "notes", column = "BZ", javaType = String.class),
            @Result(property = "ringFlag", column = "RINGFLAG", javaType = Integer.class)
    })
    ZCCDetail get(Object obj);

    //删除一条明细
    @DeleteProvider(type = ZCCDetailSql.class, method = "delete")
    int delete(Object object);


    //删除指定变更组下的变更明细
    @Delete("DELETE FROM XZQH_BGMXB WHERE GROUPXH =#{groupSeq}")
    int deleteByGroupSeq(@Param("groupSeq") Object groupSeq);

    //删除若干变更组下的变更明细
    @DeleteProvider(type = ZCCDetailSql.class, method = "deleteByGroupSeqs")
    int deleteByGroupSeqs(List<Integer> seqList);

    @UpdateProvider(type = ZCCDetailSql.class, method = "update")
    int update(Object detail);

    //插入明细，并设置对象的主键属性
    @InsertProvider(type = ZCCDetailSql.class, method = "insert")
    @SelectKey(before = false, resultType = Integer.class, keyColumn = "MXBXH", keyProperty = "seq", statement = "SELECT LAST_INSERT_ID()")
    @Options(useGeneratedKeys = true)
    Integer insert(Object detail);

    @SelectProvider(type = ZCCDetailSql.class, method = "findAll")
    @ResultMap(value = "get")
    List<ZCCDetail> findAll();

    @Select("SELECT MXBXH, PXH, GROUPXH, BGLX_DM, BZ, YSXZQH_DM, YSXZQH_MC, YSXZQH_DM, MBXZQH_DM, MBXZQH_MC, CLJG, CLZT_DM, RINGFLAG " +
            "FROM XZQH_BGMXB " +
            "WHERE GROUPXH = #{groupSeq} ORDER BY PXH DESC")
    @ResultMap(value = "get")
    List<ZCCDetail> findByGroupSeq(@Param(value = "groupSeq") Object groupSeq);

    /**
     * 查找若干组下的变更对照明细
     * @param seqStr 若干变更组序号，以“,”分隔
     * @return 变更明细列表
     */
    @SelectProvider(type = ZCCDetailSql.class, method = "pageSeekByGroups")
    @ResultMap(value = "get")
    List<ZCCDetail> pageSeekByGroups(String seqStr, int offset, int limit);


    /**
     * 统计若干组下的变更对照明细数量
     * @param seqStr 若干变更组序号，以“,”分隔
     * @return 变更明细列表
     */
    @SelectProvider(type = ZCCDetailSql.class, method = "countByGroups")
    int countByGroups(String seqStr);

    @SelectProvider(type = ZCCDetailSql.class, method = "countBy")
    int countBy(Collection<QueryFilter> filters);


    @SelectProvider(type = ZCCDetailSql.class, method = "seek")
    @ResultMap(RESULT_MAP)
    List<ZCCDetail> seek(QueryReq req);
}