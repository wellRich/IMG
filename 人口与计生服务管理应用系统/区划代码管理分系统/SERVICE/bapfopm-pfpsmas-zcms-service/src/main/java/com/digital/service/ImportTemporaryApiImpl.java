package com.digital.service;

import com.digital.api.ImportTemporaryApi;
import com.digital.dao.TemporaryDataMapper;
import com.digital.dao.ZoningDataUploadMapper;
import com.digital.entity.province.ContrastTemporary;
import com.digital.entity.province.FocusChangeFileInfo;
import com.digital.entity.province.ProvinceSectionalData;
import com.digital.util.Common;
import com.digital.util.FileUtil;
import com.digital.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO  导入临时表模块
 * @Author: zhanghpj
 * @Version 1.0, 16:15 2018/3/6
 * @See
 * @Since
 * @Deprecated
 */
@Service
@Transactional
public class ImportTemporaryApiImpl implements ImportTemporaryApi{

    private static final Logger logger = LoggerFactory.getLogger(ImportTemporaryApiImpl.class);

    @Autowired
    private TemporaryDataMapper temporaryDataMapper;

    @Autowired
    private ZoningDataUploadMapper zoningDataUploadMapper;




    /*
     * 导入临时表
     * */
    @Override
    public boolean importTemporary(FocusChangeFileInfo fileInfo){
        boolean checkStatus = true;
        List<String> zoningCodeList = new ArrayList<>();
        List<String> changeCodeList = new ArrayList<>();
        Map checkMap = new HashMap();


        String fileName = fileInfo.getFileName();
        String filePath = fileInfo.getFilePath();
        String zipFile = filePath + File.separator+fileName;
        checkMap = FileUtil.readZip(zipFile);
        if ((boolean)checkMap.get("check")){
            changeCodeList = (List<String>) checkMap.get("BGDZB");
            if (changeCodeList.isEmpty()){
                logger.debug("变更没有解析到数据！！！");
            }
            for (int i = 0; i < changeCodeList.size(); i++) {
                int result = this.importContrast(changeCodeList.get(i),fileInfo,i+1);
                if (result!=1){
                    checkStatus =false;
                    logger.debug("含有插入失败的数据!该数据的序号为："+i+1);
                }
            }
            zoningCodeList = (List<String>) checkMap.get("XZQH");
            if (zoningCodeList.isEmpty()){
                logger.debug("区划代码没有解析到数据！！！");
            }
            for (int i =0;i<zoningCodeList.size();i++){
                int result = this.importZoningCode(zoningCodeList.get(i),fileInfo);
                if (result!=1){
                    logger.debug("含有插入失败的数据!该数据的序号为："+i+1);
                    checkStatus =false;
                }
            }
            if (checkStatus){
                zoningDataUploadMapper.updateFocusChangeInfo(fileInfo.getFileSquence(), Common.XZQH_JZBGZT_DRLSBCG,(String) checkMap.get("message"));
            }else {
                zoningDataUploadMapper.updateFocusChangeInfo(fileInfo.getFileSquence(), Common.XZQH_JZBGZT_DRLSBSB,(String) checkMap.get("message"));
            }

        }else{
            //记录错误信息
            zoningDataUploadMapper.updateFocusChangeInfo(fileInfo.getFileSquence(), Common.XZQH_JZBGZT_DRLSBSB,(String) checkMap.get("message"));
            checkStatus= false;
        }
        return checkStatus;
    }

    /*
    * 提供下载查询
    * */
    @Override
    public List<ContrastTemporary> queryTemporary(Integer fileSquence, String errorIdentification) {
        List<ContrastTemporary> temporaryList = new ArrayList<>();
        temporaryList = temporaryDataMapper.queryTemporary(fileSquence,errorIdentification);
        return temporaryList;
    }


    /*
     * 导入到变更临时表
     * */
    public int importContrast(String temporaryData,FocusChangeFileInfo fileInfo,Integer orderNum){
        ContrastTemporary contrastTemporary = new ContrastTemporary();
        String[] datas = temporaryData.split(",");
        contrastTemporary.setFileNum(fileInfo.getFileSquence());
        contrastTemporary.setOriginalName(datas[2]);
        contrastTemporary.setOriginalCode(datas[3]);
        contrastTemporary.setTypeCode(datas[4]);
        contrastTemporary.setNowName(datas[5]);
        contrastTemporary.setNowCode(datas[6]);
        contrastTemporary.setEnterOneCode(fileInfo.getEnterOneCode());
        contrastTemporary.setEnterTime(StringUtil.getTime());
        contrastTemporary.setOrganizationCode(fileInfo.getOrganizationCode());
        contrastTemporary.setErrorIdentification("N");
        contrastTemporary.setGroupNum(Long.valueOf(StringUtil.sNull(datas[0].trim())));
        contrastTemporary.setGroupName(datas[1]);
        contrastTemporary.setOrderNum(orderNum);
        return temporaryDataMapper.insertChangeData(contrastTemporary);
    }


    /*
     * 导入到区划代码临时表中
     * */
    public int importZoningCode(String zoningCode,FocusChangeFileInfo fileInfo){
        ProvinceSectionalData provinceSectionalData = new ProvinceSectionalData();
        String[] datas = zoningCode.split(",");
        provinceSectionalData.setFileNum(fileInfo.getFileSquence());
        provinceSectionalData.setDivisionName(datas[0]);
        provinceSectionalData.setZoningCode(datas[1]);
        provinceSectionalData.setDate(StringUtil.getTime());
        return temporaryDataMapper.insertZoningData(provinceSectionalData);
    }


}
