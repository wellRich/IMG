package com.digital.dao;

import com.digital.dao.sqlMapper.FormalTableSql;
import com.digital.entity.FormalTableInfo;
import com.digital.util.Common;
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
public interface FormalTableMapper {
    static final String RESULT_MAP =  "com.digital.entity.allOfFormalTableInfo";
    //插入正式表
    @InsertProvider(type = FormalTableSql.class,method = "insert")
//    @Insert("insert into dm_xzqh values(#{uniqueKey},#{zoningCode},#{divisionName},#{divisionAbbreviation}," +
//            "#{divisionFullName},#{assigningCode},#{levelCode},#{superiorZoningCode},#{chooseSign}," +
//            "#{usefulSign},#{subordinateRelations},#{validityStart},#{validityStup},#{virtualNode}," +
//            "#{oldZoningCode},#{accessCode},#{enterOneCode},#{createDate},#{updaterCode},#{lastUpdate}," +
//            "#{type},#{version})")
    @ResultType(Integer.class)
    int insetFormatTableData(FormalTableInfo formalTableInfo);

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

    @SelectProvider(type = FormalTableSql.class, method = "seek")
    @ResultMap("com.digital.entity.allOfFormalTableInfo")
    List<FormalTableInfo> seek(QueryReq req);

    @SelectProvider(type = FormalTableSql.class, method = "findAll")
    @ResultMap("com.digital.entity.allOfFormalTableInfo")
    List<FormalTableInfo> findAll();
}
