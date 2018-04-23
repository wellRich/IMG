package com.digital.dao.cazc;

/**
 * @author zhangwei
 * @description
 * @date created in 14:47 2018/3/29
 * @throws Exception
 */

import com.digital.entity.cazc.CivilAffairDataUpload;
import com.digital.entity.cazc.CivilAffairZoningCode;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface CivilAffairDataMapper {

    /**
     * 根据文件名，判断文件是否重复，是否唯一
     * @param fileName
     * @return
     */
    @Select("select count(1) from xzqh_mzsjzip where wjm = #{fileName}")
    int checkCivilAffairZipName(String fileName);

    /**
     * @description 上传民政区划，并记录基本信息
     * @method  insertXzqh_mzsjzip
     * @params File
     * @return java.lang.Integer
     * @exception
     */
   // @InsertProvider(type = CivilAffairDataMapperSql.class,method = "insertCivilAffairZip")
    // @Options(useGeneratedKeys = true,keyProperty = "zipXh",keyColumn = "zipXh")
    int insertCivilAffairZip(CivilAffairDataUpload fileInfo) throws  RuntimeException;

    /**
     * @description 上传民政区划，并记录基本信息
     * @method  insertXzqh_mzsjzip
     * @params File
     * @return java.lang.Integer
     * @exception
     */
    @Select("select zipXh,wjm,wjlj,zt_dm,RQ,BZ,LRR_DM,LRSJ,LRJG_DM from xzqh_mzsjzip")
    @Results({
            @Result(property = "zipXh", column = "zipXh", javaType = Integer.class),
            @Result(property = "fileName", column = "WJM", javaType = String.class),
            @Result(property = "filePath", column = "WJLJ", javaType = String.class),
            @Result(property = "status", column = "ZT_DM", javaType = Integer.class),
            @Result(property = "date", column = "RQ", javaType = String.class),
            @Result(property = "comment", column = "BZ", javaType = String.class),
            @Result(property = "enterOneCode", column = "LRR_DM", javaType = String.class),
            @Result(property = "enterTime", column = "LRSJ", javaType = Date.class),
            @Result(property = "organizationCde", column = "LRJG_DM", javaType = String.class)
    })
    List<CivilAffairDataUpload> selectCivilAffairZip();

    /**
     * @description 将民政区划数据插入到数据库中
     * @method  insertCivilAffairDate
     * @params List<CivilAffairZoningCode>
     * @return java.lang.Integer
     * @exception
     */
    //@InsertProvider(type = CivilAffairDataMapperSql.class,method = "insertCivilAffairDate")
    //@Options(useGeneratedKeys = true,keyProperty = "zipXh",keyColumn = "zipXh")
    //@ResultType(value = Integer.class)
    int  insertCivilAffairDate(List<CivilAffairZoningCode> civilAffairZoningCodes);

    /**
     * 修改上传文件状态
     * @param zipXh
     * @param status
     * @return
     */
    @Update("UPDATE xzqh_mzsjzip SET zt_dm = #{status} WHERE zipXh=#{zipXh}")
    int  updateCivilAffairZipStatus(@Param("zipXh") int zipXh,@Param("status") int status);

    /**
     * @description 民政区划数据预览
     * @method  selectCivilAffairZoningCode
     * @params List<CivilAffairZoningCode>
     * @return java.lang.Integer
     * @exception
     */
    @Select("select xzqh_dm,xzqh_mc,sj_xzqh_dm,jcdm  from xzqh_mzsj where sj_xzqh_dm=#{superiorZoningCode} order by xzqh_dm")
    @Results({
            @Result(property = "zoningCode", column = "xzqh_dm", javaType = String.class),
            @Result(property = "zoningName", column = "xzqh_mc", javaType = String.class),
            @Result(property = "assigningCode", column = "jcdm", javaType = String.class),
            @Result(property = "superiorZoningCode", column = "sj_xzqh_dm", javaType = String.class)
    })
    List<CivilAffairZoningCode> selectCivilAffairZoningCode(@Param("superiorZoningCode") String superiorZoningCode);

    /**
     * @description 民政区划数据查询导出
     * @method  selectCivilAffairZoningCode
     * @params List<CivilAffairZoningCode>
     * @return java.lang.Integer
     * @exception
     */
    @Select("select xzqh_dm,xzqh_mc from xzqh_mzsj where sj_xzqh_dm like  concat(#{superiorZoningCode}, '%') order by xzqh_dm")
    @Results({
            @Result(property = "zoningCode", column = "xzqh_dm", javaType = String.class),
            @Result(property = "zoningName", column = "xzqh_mc", javaType = String.class)
    })
    List<CivilAffairZoningCode> downCivilAffairZoningCode(@Param("superiorZoningCode") String superiorZoningCode);

    /**
     * 民政区划数据与行政区划区划数据比较
     * @param id
     * @return
     */
    List<Map<String,Object>> selectCYDate(@Param("id") String id);

    /**
     * 清空民政区划数据
     */
    @Delete("delete from xzqh_mzsj")
    void deleteCivilAffairZoningCode();
}
