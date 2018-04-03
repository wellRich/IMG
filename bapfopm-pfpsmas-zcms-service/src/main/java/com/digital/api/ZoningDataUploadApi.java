package com.digital.api;

import com.digital.entity.province.FocusChangeFileInfo;

import java.util.List;

/**
 * @Author: zhanghpj
 * @Version 1.0, 9:48 2018/3/1
 * @See
 * @Since
 * @Deprecated
 */
public interface ZoningDataUploadApi {

    /*
     *存储文件信息
     * */
    int recordFileInfo(FocusChangeFileInfo fileInfo);
    /*
     *判断文件是否有同名文件存在
     * */
    Boolean checkFileExist(String fileName,String zoningCode);
    /*
     *根据文件序号查询zip文件基本信息
     * */
    FocusChangeFileInfo queryFocusChangeFileInfo(Integer fileSquence);
    /*
     * 根据文件状态信息判断文件能否被删除
     * */
    boolean checkFileStatus(Integer fileSquence);
    /*
     * 删除文件信息、以及文件产生的数据
     * */
    boolean deleteFileInfo(Integer fileSquence);
    /*
     * 根据6位区划代码、日期  查询文件信息
     * */
    List<Object> queryFocusChangeFileInfo(String zoningCode, String date);
}
