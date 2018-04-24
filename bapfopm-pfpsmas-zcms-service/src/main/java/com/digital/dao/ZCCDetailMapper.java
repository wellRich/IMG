package com.digital.dao;

import com.digital.dao.sqlMapper.ZCCDetailSql;
import com.digital.entity.ZCCDetail;
import com.digital.entity.ZCCGroup;
import com.digital.util.search.*;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
public interface ZCCDetailMapper extends BaseMapper<ZCCDetail>{
    String RESULT_MAP =  "com.digital.entity.allOfZCCDetail";

    @SelectProvider(type = ZCCDetailSql.class, method = BaseMapper.GET)
    @ResultMap(RESULT_MAP)
    ZCCDetail get(Object obj);

    //删除一条明细
    @DeleteProvider(type = ZCCDetailSql.class, method = BaseMapper.DELETE)
    int delete(Object object);


    //删除指定变更组下的变更明细
    @Delete("DELETE FROM XZQH_BGMXB WHERE GROUPXH =#{groupSeq}")
    int deleteByGroupSeq(@Param("groupSeq") Object groupSeq);

    //删除若干变更组下的变更明细
    @DeleteProvider(type = ZCCDetailSql.class, method = "deleteByGroupSeqs")
    int deleteByGroupSeqs(List<Integer> seqList);


    //插入明细，并设置对象的主键属性
    @InsertProvider(type = ZCCDetailSql.class, method = BaseMapper.INSERT)
    @SelectKey(before = false, resultType = Integer.class, keyColumn = "MXBXH", keyProperty = "seq", statement = "SELECT LAST_INSERT_ID()")
    @Options(useGeneratedKeys = true)
    int insert(Object detail);

    @SelectProvider(type = ZCCDetailSql.class, method = BaseMapper.FIND_ALL)
    @ResultMap(RESULT_MAP)
    List<ZCCDetail> findAll();

    @Select("SELECT MXBXH, PXH, GROUPXH, BGLX_DM, BZ, YSXZQH_DM, YSXZQH_MC, YSXZQH_DM, MBXZQH_DM, MBXZQH_MC, CLJG, CLZT_DM, RINGFLAG " +
            "FROM XZQH_BGMXB " +
            "WHERE GROUPXH = #{groupSeq} ORDER BY PXH DESC")
    @ResultMap(RESULT_MAP)
    List<ZCCDetail> findByGroupSeq(@Param(value = "groupSeq") Object groupSeq);

    /**
     * 查找若干组下的变更对照明细
     * @param seqStr 若干变更组序号，以“,”分隔
     * @return 变更明细列表
     */
    @SelectProvider(type = ZCCDetailSql.class, method = "pageSeekByGroups")
    @ResultMap(RESULT_MAP)
    List<ZCCDetail> pageSeekByGroups(String seqStr, int offset, int limit);

    /**
     * 统计若干组下的变更对照明细数量
     * @param seqStr 若干变更组序号，以“,”分隔
     * @return 变更明细列表
     */
    @SelectProvider(type = ZCCDetailSql.class, method = "countByGroups")
    int countByGroups(String seqStr);

    @SelectProvider(type = ZCCDetailSql.class, method = BaseMapper.COUNT_BY)
    int countBy(String field, QueryFilter ... filters);

    @Override
    @SelectProvider(type = ZCCDetailSql.class, method = BaseMapper.PAGE_SEEK)
    @ResultMap(RESULT_MAP)
    List<ZCCDetail> pageSeek(QueryReq req, int pageNum, int pageSize);

    @SelectProvider(type = ZCCDetailSql.class, method = BaseMapper.SEEK)
    @ResultMap(RESULT_MAP)
    @Override
    List<ZCCDetail> seek(QueryReq req);
}
