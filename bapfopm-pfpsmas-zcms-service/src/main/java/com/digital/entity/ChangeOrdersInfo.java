package com.digital.entity;

/**
 * @Description: TODO  行政区划变更指令表
 * @Version 1.0, 15:55 2018/2/24
 * @Author: zhanghpj
 * @See
 * @Since
 * @Deprecated
 */
public class ChangeOrdersInfo {

    /*
    * 申请表序号
    * */
    private String applicationNum;
    /*
    * 申请表日志序号
    * */
    private String logNum;
    /*
     * 原区划代码
     * */
    private char originalCode;
    /*
     * 原区划名称
     * */
    private String originalName;
    /*
     * 变更类型代码
     * */
    private char typeCode;
    /*
    * 目标区划代码
    * */
    private char targetCode;
    /*
    * 目标区划名称
    * */
    private String targetName;
    /*
    * 日志时间
    * */
    private String date;



    public String getApplicationNum() {
        return applicationNum;
    }

    public void setApplicationNum(String applicationNum) {
        this.applicationNum = applicationNum;
    }

    public String getLogNum() {
        return logNum;
    }

    public void setLogNum(String logNum) {
        this.logNum = logNum;
    }

    public char getOriginalCode() {
        return originalCode;
    }

    public void setOriginalCode(char originalCode) {
        this.originalCode = originalCode;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public char getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(char typeCode) {
        this.typeCode = typeCode;
    }

    public char getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(char targetCode) {
        this.targetCode = targetCode;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
