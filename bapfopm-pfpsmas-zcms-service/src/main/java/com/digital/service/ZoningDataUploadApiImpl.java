package com.digital.service;

import com.digital.api.ZoningDataUploadApi;
import com.digital.dao.TemporaryDataMapper;
import com.digital.dao.ZoningDataUploadMapper;
import com.digital.entity.province.FocusChangeFileInfo;
import com.digital.util.Common;
import com.digital.util.FileUtil;
import com.digital.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 文件上传模块
 * @Author: zhanghpj
 * @Version 1.0, 10:38 2018/3/1
 * @See
 * @Since
 * @Deprecated
 */
@Service
@Transactional
public class ZoningDataUploadApiImpl implements ZoningDataUploadApi {


    private static final Logger logger = LoggerFactory.getLogger(ZoningDataUploadApiImpl.class);

    @Autowired
    private ZoningDataUploadMapper zoningDataUploadMapper;

    @Autowired
    private TemporaryDataMapper importTemporaryMapper;





    /**
     * @description 保存zip文件的基本信息
     * @method  recordFileInfo
     * @params [fileInfo zip文件信息]
     * @return int
     * @exception
     */
    @Override
    public int recordFileInfo(FocusChangeFileInfo fileInfo) {

        return   zoningDataUploadMapper.insertFileInfo(fileInfo);
    }

    /**
     * @description 查询zip文件
     * @method  queryFocusChangeFileInfo
     * @params [fileSquence：文件序号]
     * @return List<FocusChangeFileInfo>
     * @exception
     */
    @Override
    public FocusChangeFileInfo queryFocusChangeFileInfo(Integer fileSquence) {

        return zoningDataUploadMapper.queryFocusChangeFileInfo(fileSquence);
    }





    /**
     * @description 判断zip文件是否存在
     * @method  checkFileExists
     * @params [fileName：文件名, zoningCode：区划代码]
     * @return java.lang.Boolean
     * @exception
     */
    @Override
    public Boolean checkFileExist(String fileName, String zoningCode) {

        List<FocusChangeFileInfo> list = new ArrayList<>();
        Boolean check = true;
        list = zoningDataUploadMapper.checkFileExist(fileName);
        if (!list.isEmpty()){
            return false;
        }
        /*查看否存在该省的申请单   存在表示上次区划没有发布*/
        return check;
    }

    /**
     * @description 根据文件状态判断文件能否被删除
     * @method  checkFileStatus
     * @params [fileSquence：文件序号]
     * @return boolean 可以删除 true； 不可删除 false
     * @exception
     */
    @Override
    public boolean checkFileStatus(Integer fileSquence) {

        FocusChangeFileInfo fileInfo = new FocusChangeFileInfo();
        fileInfo = zoningDataUploadMapper.queryFocusChangeFileInfo(fileSquence);
        String status = fileInfo.getStatusCode();
        if (!StringUtil.isEmpty(status)){
            /*判断变更状态、如果是40表示 已经申请单生成成功；不可被删除*/
            if (Common.XZQH_JZBGZT_SQDSQCG.equals(status)||
                    Common.XZQH_JZBGZT_DRLSBCLZ.equals(status)||
                    Common.XZQH_JZBGZT_LJJYCLZ.equals(status)||
                    Common.XZQH_JZBGZT_SQDSQCLZ.equals(status)){
                return false;
            }
        }
        return true;
    }

    /**
     * @description 删除文件、以及文件所产生的数据
     * @method  deleteFileInfo
     * @params [fileSquence：文件序号]
     * @return boolean
     * @exception
     */
    @Override
    public boolean deleteFileInfo(Integer fileSquence) {

        FocusChangeFileInfo fileInfo = new FocusChangeFileInfo();
        fileInfo = zoningDataUploadMapper.queryFocusChangeFileInfo(fileSquence);
        String filePath = fileInfo.getFilePath();
        File fileDir = new File(filePath);
        //删除文件
        if (fileDir.exists()){
            boolean checked = FileUtil.deleteDir(fileDir);
            if (!checked){
                logger.info("删除文件失败");
            }
        }
        //删除省级数据
        importTemporaryMapper.deleteZoningDatas(fileSquence);
        //删除对照表数据
        importTemporaryMapper.deleteChangeDatas(fileSquence);
        //删除文件信息
        zoningDataUploadMapper.deleteFocusChangeInfo(fileSquence);
        return true;
    }

    @Override
    public List<Object> queryFocusChangeFileInfo(String zoningCode, String date) {
        return zoningDataUploadMapper.queryFocusChangeFileInfoByCode(zoningCode,date);
    }

    /*
     * 修改文件变更状态
     * */
    @Override
    public int updateTypeCode(Integer fileSquence, String typeCode) {
        return zoningDataUploadMapper.updateFocusChangeInfo(fileSquence,typeCode,null);
    }


}
