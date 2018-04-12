package com.digital.entity.province;

/**
 * @Description: TODO 集中变更上传zip包信息表
 * @Version 1.0, 11:31 2018/2/24
 * @Author: zhanghpj
 * @See
 * @Since
 * @Deprecated
 */
public class FocusChangeFileInfo {

   /*
   * 文件序号
   * */
    private Integer fileSquence;
    /*
    * 区划代码
    * */
    private String zoningCode;
    /*
    * 日期(解析文件所得)
    * */
    private String date;
    /*
    *集中变更状态代码
    * */
    private String statusCode;
    /*
    * 备注
    * */
    private String note;
    /*
    *变更指令代码
    * */
    private String instructionCode;
    /*
    * 对应的申请单号
    * */
    private Integer applicationNo;
    /*
    *文件名
    * */
    private String fileName;
    /*
    * 文件路径
    * */
    private String filePath;
    /*
    * 录入人代码
    * */
    private String enterOneCode;
    /*
    * 录入时间
    * */
    private String enterTime;
    /*
    * 录入机构代码
    * */
    private String organizationCode;






    public Integer getFileSquence() {
        return fileSquence;
    }

    public void setFileSquence(Integer fileSquence) {
        this.fileSquence = fileSquence;
    }

    public String getZoningCode() {
        return zoningCode;
    }

    public void setZoningCode(String zoningCode) {
        this.zoningCode = zoningCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInstructionCode() {
        return instructionCode;
    }

    public void setInstructionCode(String instructionCode) {
        this.instructionCode = instructionCode;
    }

    public Integer getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(Integer applicationNo) {
        this.applicationNo = applicationNo;
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

    public String getEnterOneCode() {
        return enterOneCode;
    }

    public void setEnterOneCode(String enterOneCode) {
        this.enterOneCode = enterOneCode;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }


}
