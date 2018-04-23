package com.digital.service.cazc;


import com.digital.api.cazc.CivilAffairDataApi;
import com.digital.dao.cazc.CivilAffairDataMapper;
import com.digital.entity.cazc.CivilAffairDataUpload;
import com.digital.entity.cazc.CivilAffairZoningCode;
import org.apache.ibatis.annotations.Insert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
     * 修改zip状态
     * @param zipXh
     * @param status
     * @return
     */
    @Override
    public int updateCivilAffairZipStatus(int zipXh, int status) {
        return civilAffairDataMapper.updateCivilAffairZipStatus(zipXh, status);
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
     * @param lists
     * @return int
     */
    @Override
    @Transactional
    public int insertCivilAffairDate(List<List<CivilAffairZoningCode>> lists,int civilAffairZoningCodeSize,int zipXh) throws  RuntimeException{
        //清空民政区划数据
        deleteCivilAffairZoningCode();
       int resultSum = 0;
        for (int i = 0; i < lists.size(); i++) {
          int  num = civilAffairDataMapper.insertCivilAffairDate(lists.get(i));
            resultSum = resultSum + num;
        }
        updateCivilAffairZipStatus(zipXh,22); //22表示数据导入成功
        return resultSum;
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

    /**
     * 民政区划数据与行政区划区划数据比较
     * @param id
     * @return
     */
    @Override
    public List<Map<String,Object>> selectCYDate(String id) {
        return  civilAffairDataMapper.selectCYDate(id);
    }

    /**
     * 清空民政区划数据
     */
   public void deleteCivilAffairZoningCode(){
       civilAffairDataMapper.deleteCivilAffairZoningCode();
    }

}
