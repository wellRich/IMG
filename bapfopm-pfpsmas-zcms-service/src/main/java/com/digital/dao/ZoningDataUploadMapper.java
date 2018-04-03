package com.digital.dao;

import com.digital.dao.sqlMapper.ZoningDataUploadSql;
import com.digital.entity.province.FocusChangeFileInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: zhanghpj
 * @Version 1.0, 10:39 2018/3/1
 * @See
 * @Since
 * @Deprecated
 */

@Mapper
public interface ZoningDataUploadMapper {



    /*
     * 添加集中上报zip文件信息
     * */
    @Insert("insert into xzqh_jzbgzip(XZQH_DM,RQ,JZBGZT_DM,BZ,BGZL_DM,WJM,WJLJ,LRR_DM,LRSJ,LRJG_DM) values(" +
            "#{zoningCode},#{date}," +
            "#{statusCode},#{note},#{instructionCode}," +
            "#{fileName},#{filePath}," +
            "#{enterOneCode},#{enterTime},#{organizationCode})")
    @Options(useGeneratedKeys = true,keyProperty = "fileSquence",keyColumn = "ZIPXH")
    int insertFileInfo(FocusChangeFileInfo fileInfo);


    /*
     * 检查ZipFile是否已经存在
     * */
    @Select("select ZIPXH,WJM from xzqh_jzbgzip where WJM like #{fileName}%")
    @Results({
            @Result(column = "ZIPXH",property = "fileSquence",javaType =Integer.class ),
            @Result(column = "WJM",property = "fileName",javaType = String.class),

    })
    List<FocusChangeFileInfo> checkFileExist(String fileName);



    /*
     * 根据文件序号查询zip文件基本信息
     * */
    @SelectProvider(type = ZoningDataUploadSql.class,method = "queryFocusChangeFileInfo")
    @Results(id = "fileInfo",value = {
            @Result(column = "ZIPXH",property = "fileSquence",javaType =Integer.class),
            @Result(column = "XZQH_DM",property = "zoningCode",javaType = String.class),
            @Result(column = "RQ",property = "date",javaType = String.class),
            @Result(column = "JZBGZT_DM",property = "statusCode",javaType = String.class),
            @Result(column = "WJM",property = "fileName",javaType =String.class ),
            @Result(column = "LRSJ",property = "enterTime",javaType =String.class ),
            @Result(column = "WJLJ",property = "filePath",javaType = String.class),
            @Result(column = "LRR_DM",property = "enterOneCode",javaType = String.class),
            @Result(column = "LRJG_DM",property = "organizationCode",javaType = String.class)
    })
    FocusChangeFileInfo queryFocusChangeFileInfo(Integer fileSquence);

    /*
     * 根据6位区划代码、日期  查询文件信息
     * */
    @SelectProvider(type = ZoningDataUploadSql.class,method = "queryFocusChangeFileInfoByCode")
    @ResultMap("fileInfo")
    List<Object> queryFocusChangeFileInfoByCode( String zoningCode, String date);



    /*
     * 根据文件序号动态修改文件信息表中的状态、备注
     * */
    @UpdateProvider(type = ZoningDataUploadSql.class,method = "updateFocusChangeInfo")
    int updateFocusChangeInfo(Integer fileSquence, String statusCode, String note);

    /*
    * 添加申请单序号
    * */
    @Update("update xzqh_jzbgzip set SQDXH=#{applicationNum},JZBGZT_DM=#{instructionCode} where ZIPXH=#{fileSquence}")
    int updateFocusApplicationNum(@Param("fileSquence") Integer fileSquence,@Param("applicationNum") Integer applicationNum,@Param("instructionCode")String instructionCode);





}
