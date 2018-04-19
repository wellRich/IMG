package com.digital.service.cazc;


import com.digital.api.cazc.CivilAffairDataApi;
import com.digital.dao.cazc.CivilAffairDataMapper;
import com.digital.entity.cazc.CivilAffairDataUpload;
import com.digital.entity.cazc.CivilAffairZoningCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangwei
 * @description
 * @date created in 19:07 2018/3/29
 * @throws Exception
 */
@Service
public class CivilAffairDataApiImpl implements CivilAffairDataApi {

    private static final Logger logger = LoggerFactory.getLogger(CivilAffairDataApiImpl.class);

    @Autowired
    private CivilAffairDataMapper civilAffairDataMapper;

    /**
     * 校验上传zip文件名是否重复
     * @param fileName
     * @return int
     */
    @Override
    public int checkCivilAffairZipName(String fileName) {
        return civilAffairDataMapper.checkCivilAffairZipName(fileName);
    }

    /**
     * @description 上传民政区划，并记录基本信息
     * @method  insertXzqh_mzsjzip
     * @params CivilAffairDataUpload
     * @return java.lang.Integer
     * @exception
     */
    @Override
    public int insertCivilAffairZip(CivilAffairDataUpload fileInfo) {
        return civilAffairDataMapper.insertCivilAffairZip(fileInfo);
    }

    /**
     * @description 查询民政区划上传zip文件信息
     * @method  selectCivilAffairZip
     * @return List<CivilAffairDataUpload>
     * @exception
     */
    @Override
    public List<CivilAffairDataUpload> selectCivilAffairZip() {
        return civilAffairDataMapper.selectCivilAffairZip();
    }

    /**
     * @description 将民政区划的数据插入到数据库中
     * @param civilAffairZoningCodes
     * @return int
     */
    @Override
    public int insertCivilAffairDate(List<CivilAffairZoningCode> civilAffairZoningCodes) {
        return civilAffairDataMapper.insertCivilAffairDate(civilAffairZoningCodes);
    }

    /**
     * 民政区划数据预览
     * @param superiorZoningCode
     * @param superiorZoningCode
     * @return
     */
    @Override
    public List<CivilAffairZoningCode> selectCivilAffairZoningCode(String superiorZoningCode) {
        return civilAffairDataMapper.selectCivilAffairZoningCode(superiorZoningCode);
    }

    /**
     * 导出民政区划代码数据
     */
    @Override
    public List<CivilAffairZoningCode> downCivilAffairZoningCode(String superiorZoningCode) {
        return  civilAffairDataMapper.downCivilAffairZoningCode(superiorZoningCode);
    }


}
