package com.digital.entity.province;

import java.math.BigInteger;

/**
 * @Description: TODO  集中变更对照数据临时表
 * @Version 1.0, 15:16 2018/2/24
 * @Author: zhanghpj
 * @See
 * @Since
 * @Deprecated
 */
public class ContrastTemporary {

    /*
    * 对照表序号
    * */
    private Integer tableNum;
    /*
    * 上传文件序号
    * */
    private Integer fileNum;
    /*
    * 原区划代码
    * */
    private String originalCode;
    /*
    * 原区划名称
    * */
    private String originalName;
    /*
    * 变更类型代码
    * */
    private String typeCode;
    /*
    * 现区划代码
    * */
    private String nowCode;
    /*
    * 现区划名称
    * */
    private String nowName;
    /*
    * 错误数据标识
    * */
    private String errorIdentification;
    /*
    * 备注
    * */
    private String note;

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
    /*
    * 错误信息
    * */
    private String errorMessage;
    /*
    * 组编号
    * */
    private BigInteger groupNum;
    /*
    * 组名称
    * */
    private String groupName;
    /*
    * 排序号
    * */
    private Integer orderNum;



    public Integer getTableNum() {
        return tableNum;
    }

    public void setTableNum(Integer tableNum) {
        this.tableNum = tableNum;
    }

    public Integer getFileNum() {
        return fileNum;
    }

    public void setFileNum(Integer fileNum) {
        this.fileNum = fileNum;
    }

    public String getOriginalCode() {
        return originalCode;
    }

    public void setOriginalCode(String originalCode) {
        this.originalCode = originalCode;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getNowCode() {
        return nowCode;
    }

    public void setNowCode(String nowCode) {
        this.nowCode = nowCode;
    }

    public String getNowName() {
        return nowName;
    }

    public void setNowName(String nowName) {
        this.nowName = nowName;
    }

    public String getErrorIdentification() {
        return errorIdentification;
    }

    public void setErrorIdentification(String errorIdentification) {
        this.errorIdentification = errorIdentification;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BigInteger getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(BigInteger groupNum) {
        this.groupNum = groupNum;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
