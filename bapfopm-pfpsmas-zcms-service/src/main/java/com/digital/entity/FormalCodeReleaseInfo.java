package com.digital.entity;

import com.digital.util.search.Column;
import com.digital.util.search.Table;

import java.io.Serializable;

/**
 * @Description: TODO 行政区划发布
 * @Author: zhanghpj
 * @Version 1.0, 18:22 2018/4/23
 * @See
 * @Since
 * @Deprecated
 */
@Table(name = "xt_xzqhfb",primaryKey = "exportNum")
public class FormalCodeReleaseInfo implements Serializable{

    /*
    * 导出序号
    * */
    @Column(name = "DCXH")
    private Integer exportNum;
    /*
    * 文件名
    * */
    @Column(name = "WJM")
    private String fileName;
    /*
    * 文件路径
    * */
    @Column(name = "WJLJ")
    private String filePath;

    /*
    * 文件大小
    * */
    @Column(name = "WJDX")
    private String fileSize;
    /*
    * 导出时间
    * */
    @Column(name = "DCSJ")
    private String exportDate;


    public Integer getExportNum() {
        return exportNum;
    }

    public void setExportNum(Integer exportNum) {
        this.exportNum = exportNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getExportDate() {
        return exportDate;
    }

    public void setExportDate(String exportDate) {
        this.exportDate = exportDate;
    }

}
