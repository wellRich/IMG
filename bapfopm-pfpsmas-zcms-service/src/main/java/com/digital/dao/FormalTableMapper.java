package com.digital.dao;

import com.digital.dao.sqlMapper.FormalTableSql;
import com.digital.entity.FormalTableInfo;
import org.apache.ibatis.annotations.*;

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




    @Update("update dm_xzqh set XYBZ='N',YXBZ='N' where XZQH_DM = ")
    int updateFormalZoningStatus(String zoningCode);
}
