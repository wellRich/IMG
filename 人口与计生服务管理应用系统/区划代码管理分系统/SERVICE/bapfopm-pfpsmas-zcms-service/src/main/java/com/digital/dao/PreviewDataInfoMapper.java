package com.digital.dao;

import com.digital.dao.sqlMapper.PreviewDataInfoSql;
import com.digital.entity.PreviewDataInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 2018/3/21
 */
@Mapper
public interface PreviewDataInfoMapper {

    @ResultType(Integer.class)
    @UpdateProvider(type = PreviewDataInfoSql.class, method = "update")
    Integer save(Object previewDataInfo);


    @ResultType(Integer.class)
    @InsertProvider(type = PreviewDataInfoSql.class, method = "insert")
    Integer insert(Object previewDataInfo);

    @SelectProvider(type = PreviewDataInfoSql.class, method = "get")
    @ResultMap(value = "findAll")
    PreviewDataInfo get(Object key);


    @SelectProvider(type = PreviewDataInfoSql.class, method = "findAllByZoningCode")
    @Results(id = "findAll", value = {
            @Result(column = "XZQH_DM", property = "zoningCode", javaType = String.class),
            @Result(column = "XZQH_MC", property = "divisionName", javaType = String.class),
            @Result(column = "XZQH_JC", property = "divisionAbbreviation", javaType = String.class),
            @Result(column = "XZQH_QC", property = "divisionFullName", javaType = String.class),
            @Result(column = "JCDM", property = "assigningCode", javaType = String.class),
            @Result(column = "JBDM", property = "levelCode", javaType = String.class),
            @Result(column = "SJ_XZQH_DM", property = "superiorZoningCode", javaType = String.class),
            @Result(column = "XYBZ", property = "chooseSign", javaType = String.class),
            @Result(column = "YXBZ", property = "usefulSign", javaType = String.class),
            @Result(column = "DWLSGX_DM", property = "subordinateRelations", javaType = String.class),
            @Result(column = "YXQ_Q", property = "validityStart", javaType = String.class),
            @Result(column = "YXQ_Z", property = "validityStup", javaType = String.class),
            @Result(column = "XNJD_BZ", property = "virtualNode", javaType = String.class),
            @Result(column = "OLD_XZQH_DM", property = "oldZoningCode", javaType = String.class),
            @Result(column = "QX_JGDM", property = "accessCode", javaType = String.class),
            @Result(column = "LRSJ", property = "createDate", javaType = String.class),
            @Result(column = "XGR_DM", property = "updaterCode", javaType = String.class),
            @Result(column = "XGSJ", property = "lastUpdate", javaType = String.class),
            @Result(column = "XZQHLX_DM", property = "type", javaType = String.class),
            @Result(column = "LRR_DM", property = "enterOneCode", javaType = String.class)
    })
    List<PreviewDataInfo> findAllByZoningCode(String zoningCode);

    @SelectProvider(type = PreviewDataInfoSql.class, method = "findFamilyZoning")
    @Results({
            @Result(column = "XZQH_DM", property = "zoningCode", javaType = String.class),
            @Result(column = "XZQH_JC", property = "divisionAbbreviation", javaType = String.class),
            @Result(column = "XZQH_MC", property = "divisionName", javaType = String.class)
    })
    List<PreviewDataInfo> findFamilyZoning(String levelCode, String columns);

    /**
     * 查找同一父级区划下的，指定名称的区划数量
     *
     * @param zoningCode 区划代码
     * @param zoningName 区划名称
     * @return Integer 数量
     */
    @SelectProvider(type = PreviewDataInfoSql.class, method = "findBrothersByCodeAndName")
    int findBrothersByCodeAndName(String zoningCode, String zoningName);

    @SelectProvider(type = PreviewDataInfoSql.class, method = "findBrothersByCode")
    @ResultMap(value = "findAll")
    List<PreviewDataInfo> findBrothersByCode(String zoningCode);


    /**
     * 通过区划代码查找区划预览数据
     *
     * @param zoningCode 区划代码
     * @return PreviewDataInfo 单条区划预览数据
     */
    @Select("SELECT * FROM DM_XZQH_YLSJ WHERE XZQH_DM =#{zoningCode}")
   /* @Results({
            @Result(column = "XZQH_DM", property = "zoningCode", javaType = String.class),
            @Result(column = "XZQH_MC", property = "divisionName", javaType = String.class),
            @Result(column = "XZQH_JC", property = "divisionAbbreviation", javaType = String.class),
            @Result(column = "JCDM", property = "assigningCode", javaType = String.class),
            @Result(column = "DWLSGX_DM", property = "subordinateRelations", javaType = String.class)
    })*/
    @ResultMap(value = "findAll")
    PreviewDataInfo findOneByZoningCode(@Param(value = "zoningCode") String zoningCode);


    /**
     * 通过区划代码查找
     * 有效标志为Y、选用标志为Y的区划预览数据
     *
     * @param zoningCode 区划代码
     * @return PreviewDataInfo 单条区划预览数据
     */
    //@Select("SELECT XZQH_MC,XZQH_QC,JCDM,DWLSGX_DM FROM DM_XZQH_YLSJ WHERE XYBZ = 'Y' AND YXBZ = 'Y' AND XZQH_DM =#{zoningCode, jdbcType=VARCHAR}")
    /*@Results({
            @Result(column = "XZQH_DM", property = "zoningCode", javaType = String.class),
            @Result(column = "XZQH_MC", property = "divisionName", javaType = String.class),
            @Result(column = "XZQH_JC", property = "divisionAbbreviation", javaType = String.class),
            @Result(column = "JCDM", property = "assigningCode", javaType = String.class),
            @Result(column = "DWLSGX_DM", property = "subordinateRelations", javaType = String.class)
    })
    PreviewDataInfo findValidOneByZoningCode(@Param(value = "zoningCode") String zoningCode);
    */
    @SelectProvider(type = PreviewDataInfoSql.class, method = "findValidOneByZoningCode")
    @ResultMap(value = "findAll")
    PreviewDataInfo findValidOneByZoningCode(String zoningCode);


    @SelectProvider(type = PreviewDataInfoSql.class, method = "findSubordinateZoning")
    @ResultMap(value = "findAll")
    List<PreviewDataInfo> findSubordinateZoning(String zoningCode);


    //更新数据至预览数据表
    @Update("UPDATE DM_XZQH_YLSJ Y SET Y.XYBZ='N', Y.YXBZ='N', Y.YXQ_Z =#{createDate}, Y.XGSJ =#{createDate} WHERE Y.XYBZ = 'Y' AND Y.YXBZ = 'Y' AND Y.XZQH_DM=#{zoningCode}")
    @ResultType(Integer.class)
    Integer saveMergeData(@Param(value = "zoningCode") String zoningCode, @Param(value = "createDate") Date createDate);


    //插入历史数据
    @Insert("INSERT INTO XZQH_BGMXLSJL (MXBXH,GROUPXH,YSXZQH_DM,YSXZQH_MC,BGLX_DM,MBXZQH_DM,MBXZQH_MC,LRR_DM,LRSJ)" +
            " VALUES(#{seq, jdbcType = INTEGER}," +
            " #{groupSeq, jdbcType = INTEGER}, #{originCode, jdbcType = CHAR}, #{originName, jdbcType = VARCHAR}," +
            " #{changeType, jdbcType = CHAR}, #{currentCode, jdbcType = CHAR}, #{currentName, jdbcType = CHAR}, #{creatorCode, jdbcType = CHAR}, #{createDate, jdbcType = VARCHAR})")
    Integer insertHistoryData(Map info);
}
