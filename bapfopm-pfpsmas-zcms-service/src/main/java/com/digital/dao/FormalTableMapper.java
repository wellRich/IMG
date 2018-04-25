package com.digital.dao;

import com.digital.dao.sqlMapper.FormalTableSql;
import com.digital.entity.FormalTableInfo;
import com.digital.util.Common;
import com.digital.util.search.BaseMapper;
import com.digital.util.search.QueryReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 9:34 2018/4/10
 * @See
 * @Since
 * @Deprecated
 */
@Mapper
public interface FormalTableMapper extends BaseMapper<FormalTableInfo>{
    static final String RESULT_MAP =  "com.digital.entity.allOfFormalTableInfo";
    //插入正式表
    @InsertProvider(type = FormalTableSql.class,method = BaseMapper.INSERT)
    @ResultType(Integer.class)
    int insertFormatTableData(FormalTableInfo formalTableInfo);

    //查询key
    @SelectProvider(type = FormalTableSql.class,method = "findZoningKey")
    @ResultType(String.class)
    String findZoningKey(String zoningCode);


    /**
     * 获取指定区划的子级区划
     * @param zoningCode 区划代码
     * @return 所有的子级区划
     */
    @SelectProvider(type = FormalTableSql.class, method = "findSubordinateZoning")
    @ResultMap(RESULT_MAP)
    List<FormalTableInfo> findSubordinateZoning(String zoningCode);


    @Update("update dm_xzqh set XYBZ='N',YXBZ='N' where XZQH_DM = ")
    int updateFormalZoningStatus(String zoningCode);

    @SelectProvider(type = FormalTableSql.class, method = BaseMapper.SEEK)
    @ResultMap(RESULT_MAP)
    List<FormalTableInfo> seek(QueryReq req);

    @SelectProvider(type = FormalTableSql.class, method = BaseMapper.FIND_ALL)
    @ResultMap(RESULT_MAP)
    List<FormalTableInfo> findAll();

    @Select("select substr(s.XZQH_DM,1,6) FROM dm_xzqh_ylsj s WHERE s.JCDM='1'")
    @ResultType(String.class)
    List<String> queryProvincialCode();


    //删除所有数据
    @Delete("delete from dm_xzqh")
    @ResultType(Integer.class)
    int delete();


}
