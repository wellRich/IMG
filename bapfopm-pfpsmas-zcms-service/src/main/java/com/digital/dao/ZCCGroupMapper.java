package com.digital.dao;

import com.digital.dao.sqlMapper.ZCCGroupSql;
import com.digital.util.search.BaseMapper;
import org.apache.ibatis.annotations.*;
import com.digital.entity.ZCCGroup;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

/**
 * 区划变更对照组映射
 * @author guoyka
 * @version 2018/3/16
 * @see ZCCGroup
 */
@Mapper
public interface ZCCGroupMapper extends BaseMapper<ZCCGroup>{
    String RESULT_MAP = "com.digital.entity.allOfZCCGroup";
    @DeleteProvider(type = ZCCGroupSql.class, method = BaseMapper.DELETE)
    int delete(Object object);

    @UpdateProvider(type = ZCCGroupSql.class, method = BaseMapper.UPDATE)
    int update(ZCCGroup group);

    @InsertProvider(type = ZCCGroupSql.class, method = BaseMapper.INSERT)
    @SelectKey(before = false, resultType = Integer.class, keyProperty = "seq", statementType= StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID()")
    @Options(useGeneratedKeys = true)
    int insert(Object group);

    //根据申请单查找变更对照组
    @Select("SELECT * FROM XZQH_BGGROUP WHERE SQDXH=#{requestSeq} ORDER BY BH, PXH")
    @ResultMap(RESULT_MAP)
    List<ZCCGroup> findByRequestSeq(@Param("requestSeq") Integer requestSeq);

    @SelectProvider(type = ZCCGroupSql.class, method = BaseMapper.GET)
    @ResultMap(RESULT_MAP)
    ZCCGroup get(Object key);

    /**
     * 通过若干id查找对照组
     * @param ids 若干对照组的序号（id），以“,”分割
     * @return 对照组列表
     */
    @SelectProvider(type = ZCCGroupSql.class, method = BaseMapper.FIND_BY_IDS)
    @ResultMap(RESULT_MAP)
    List<ZCCGroup> findByIds(String ids);

    /**
     *  根据区划代码查找该区划相关的变更组中最大的编号
     * @param zoningCode 区划代码
     * @return
     */
    @SelectProvider(type = ZCCGroupSql.class, method = "getMaxSerialNumber")
    @ResultType(value = Long.class)
    Long getMaxSerialNumber(String zoningCode);


    /**
     * 获取最大的排序号
     * @param tableName 表名
     * @return 最大值
     */
    @ResultType(value = Long.class)
    @SelectProvider(type = ZCCGroupSql.class, method = "getMaxOrderNum")
    Long getMaxOrderNum(@Param(value = "tableName") String tableName);

    /**
     * 更新最大值
     * @param tableName 表名
     * @return 被修改的数据条数
     */
    @UpdateProvider(type = ZCCGroupSql.class, method = "updateMaxOrderNum")
    int updateMaxOrderNum(@Param(value = "tableName") String tableName);

    /**
     * 初始化最大值
     * @param params
     * @return
     */
    @Update("INSERT INTO XZQH_MAX(TABLE_NAME, TABLE_NOTE, TABLE_MAX) VALUES(#{tableName}, #{tableNote}, #{tableMax})")
    int insertMaxOrderNum(Map<String, Object> params);


}
