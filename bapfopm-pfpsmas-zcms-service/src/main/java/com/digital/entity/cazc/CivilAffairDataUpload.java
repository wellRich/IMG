package com.digital.entity.cazc;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangwei
 * @Description  民政区划代码上传zip文件
 * @date created in 18:54 2018/3/28
 * @throws Exception
 */
public class CivilAffairDataUpload implements Serializable {

    /*
    文件序号
     */
    private Integer zipXh;
    /*
     *文件名
     */
    private String fileName;

    /*
     文件路径
     */
    private String filePath;
    /*
    状态
     */
    private String status;
    /*
    日期
     */
    private String date;

    /*
    备注
     */
    private String comment;

    /*
    录入人代码
     */
    private String enterOneCode;

    /*
    录入时间
     */
    private Date enterTime;

    /*
    录入机构代码
     */
    private String organizationCde;

    public Integer getZipXh() {
        return zipXh;
    }

    public void setZipXh(Integer zipXh) {
        this.zipXh = zipXh;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public String getEnterOneCode() {
        return enterOneCode;
    }

    public void setEnterOneCode(String enterOneCode) {
        this.enterOneCode = enterOneCode;
    }


    public String getOrganizationCde() {
        return organizationCde;
    }

    public void setOrganizationCde(String organizationCde) {
        this.organizationCde = organizationCde;
    }

    @Override
    public String toString() {
        return "CivilAffairDataUpload{" +
                "zipXh=" + zipXh +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", comment='" + comment + '\'' +
                ", enterOneCode='" + enterOneCode + '\'' +
                ", enterTime=" + enterTime +
                ", organizationCde='" + organizationCde + '\'' +
                '}';
    }
}
