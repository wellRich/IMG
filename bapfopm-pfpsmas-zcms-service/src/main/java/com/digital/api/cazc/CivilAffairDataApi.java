package com.digital.api.cazc;


import com.digital.entity.cazc.CivilAffairDataUpload;
import com.digital.entity.cazc.CivilAffairZoningCode;

import java.util.List;
import java.util.Map;

/**
 * @author zhangwei
 * @description 民政区划文件上传模块
 * @date created in 19:01 2018/3/29
 * @throws Exception
 */
public interface CivilAffairDataApi {

    /**
     *校验文件名是否重复
     * @param fileName
     * @return int
     */
    int checkCivilAffairZipName(String fileName);

    /**
     * 修改文件状态
     * @param zipXh
     * @param status
     * @return
     */
    int updateCivilAffairZipStatus(int zipXh,int status);


    /**
     * @description 上传民政区划，并记录基本信息
     * @method  insertXzqh_mzsjzip
     * @params CivilAffairDataUpload
     * @return java.lang.Integer
     * @exception
     */
    int insertCivilAffairZip(CivilAffairDataUpload fileInfo);

    /**
     * @description 上传民政区划，并记录基本信息
     * @method  selectCivilAffairZip
     * @return  List<CivilAffairDataUpload>
     * @exception
     */
    List<CivilAffairDataUpload> selectCivilAffairZip();

    /**
     * @description 将民政区划数据插入到数据库中
     * @method  insertCivilAffairData
     * @return  java.Lang.Integer
     * @exception
     */
    int insertCivilAffairDate(List<List<CivilAffairZoningCode>> lists,int civilAffairZoningCodeSize,int zipXh) throws  RuntimeException;

    /**
     * @description 民政区划查询预览
     * @method  selectCivilAffairZoningCode
     * @return  List<CivilAffairZoningCode>
     * @exception
     */
    List<CivilAffairZoningCode> selectCivilAffairZoningCode(String superiorZoningCode);

    /**
     * @description  导出区划查询预览
     * @method  selectCivilAffairZoningCode
     * @exception
     */
    List<CivilAffairZoningCode> downCivilAffairZoningCode(String superiorZoningCode);


    /**
     *
     * 民政区划数据与行政区划区划数据比较
     * @param id
     * @return
     */
    List<Map<String,Object>> selectCYDate(String id);

    /**
     * 清空民政区划数据
     */
    void deleteCivilAffairZoningCode();
}
