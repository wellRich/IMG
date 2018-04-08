package com.digital.dao;

import com.digital.dao.sqlMapper.TemporaryDataSql;
import com.digital.entity.province.ContrastTemporary;
import com.digital.entity.province.ProvinceSectionalData;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 16:11 2018/3/6
 * @See
 * @Since
 * @Deprecated
 */
@Mapper
public interface TemporaryDataMapper {

    /*
     * 插入区划数据到临时表
     * */
    @Insert("insert into xzqh_jzbgsj_temp(ZIPXH,XZQH_DM,XZQH_MC,DRSJ) values(" +
            "#{fileNum},#{zoningCode},#{divisionName},#{date})")
    int insertZoningData(ProvinceSectionalData provinceSectionalData);

    /*
     * 插入变更对照数据到临时表
     * */
    @Insert("insert into xzqh_jzbgdzb_temp(DZBXH,ZIPXH,YSXZQH_DM,YSXZQH_MC,BGLX_DM,MBXZQH_DM,MBXZQH_MC,CWSJBZ,LRR_DM,LRSJ,LRJG_DM,BH,GROUPMC,PXH) values(" +
            "0,#{fileNum}," +
            "#{originalCode},#{originalName}," +
            "#{typeCode},#{nowCode},#{nowName},#{errorIdentification}," +
            "#{enterOneCode},#{enterTime},#{organizationCode},#{groupNum}," +
            "#{groupName},#{orderNum})")
    int insertChangeData(ContrastTemporary contrastTemporary);

    /*
     * 查询（zipxh）省内所有的变更对照数据
     * */
    @Results(id = "ChangeData",value = {
            @Result(column = "DZBXH",property = "tableNum",javaType =Integer.class ),
            @Result(column = "YSXZQH_DM",property = "originalCode",javaType =String.class ),
            @Result(column = "YSXZQH_MC",property = "originalName",javaType =String.class ),
            @Result(column = "BGLX_DM",property = "typeCode",javaType =String.class ),
            @Result(column = "MBXZQH_DM",property = "nowCode",javaType =String.class ),
            @Result(column = "MBXZQH_MC",property = "nowName",javaType =String.class ),
            @Result(column = "CWXX",property = "errorMessage",javaType =String.class ),
            @Result(column = "LRR_DM",property = "enterOneCode",javaType =String.class ),
            @Result(column = "BZ",property = "note",javaType =String.class ),
            @Result(column = "BH",property = "groupNum",javaType = Long.class),
            @Result(column = "GROUPMC",property = "groupName",javaType = String.class),
            @Result(column = "LRJG_DM",property = "organizationCode",javaType =String.class ),
            @Result(column = "PXH",property = "orderNum",javaType =Integer.class )
    })
    @SelectProvider(type = TemporaryDataSql.class,method = "queryChangeData")
    List<ContrastTemporary> queryChangeData( Integer fileNum,String zoningCode,String errorIdentification,String changeType);


    /*
    * 提供下载的查询
    * */
    @SelectProvider(type=TemporaryDataSql.class,method = "queryTemporary")
    @Results(id = "queryTemporary",value = {
            @Result(column = "DZBXH",property = "tableNum",javaType =Integer.class ),
            @Result(column = "YSXZQH_DM",property = "originalCode",javaType =String.class ),
            @Result(column = "YSXZQH_MC",property = "originalName",javaType =String.class ),
            @Result(column = "BGLX_DM",property = "typeCode",javaType =String.class ),
            @Result(column = "MBXZQH_DM",property = "nowCode",javaType =String.class ),
            @Result(column = "MBXZQH_MC",property = "nowName",javaType =String.class ),
            @Result(column = "CWXX",property = "errorMessage",javaType =String.class ),
            @Result(column = "BH",property = "groupNum",javaType = Long.class),
            @Result(column = "GROUPMC",property = "groupName",javaType = String.class),
            @Result(column = "LRSJ",property = "enterTime",javaType = String.class),
            @Result(column = "PXH",property = "orderNum",javaType =Integer.class )
    })
    @Options(useGeneratedKeys = true)
    List<ContrastTemporary> queryTemporary(Integer fileSquence,String errorIdentification);

    /*
     * 删除临时表中的区划数据
     * */
    @Delete("delete from xzqh_jzbgsj_temp where ZIPXH=#{fileSquence}")
    int deleteZoningDatas(Integer fileSquence);

    /*
     * 删除变更临时表中的变更区划数据
     * */
    @Delete("delete from xzqh_jzbgdzb_temp where ZIPXH=#{fileSquence}")
    int deleteChangeDatas(Integer fileSquence);


    //@UpdateProvider(type = TemporaryDataSql.class,method = "updateChangeData")
    @Update("update xzqh_jzbgdzb_temp set CWSJBZ=#{errStatus},CWXX=#{errMsg} where DZBXH=#{tableNum}")
    int updateChangeData(@Param("tableNum") Integer tableNum,@Param("errStatus") String errStatus,@Param("errMsg") String errMsg);



}
