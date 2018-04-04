package com.digital.entity;

import com.digital.util.Common;
import com.digital.util.StringUtil;
import com.google.gson.internal.LinkedTreeMap;

import java.util.Date;
import java.util.Map;

/**
 * 区划变更业务对象
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 2018/3/23
 */
public class ChangeInfo implements Cloneable{

    private Integer requestSeq;//申请表序号（申请表主键）
    private Integer groupSeq;//变更组序号（变更表主键）
    private String originalZoningCode;//原区划代码
    private String originalZoningName;//原区划名称
    private String targetZoningCode;//目标区划代码
    private String targetZoningName;//目标区划名称
    private String changeType;//变更类型代码
    private String assigningCode;//变更区划的级次代码（原和目标都是同级）
    private String levelCode;//级别代码
    private String organizationCode;//权限机构代码
    private String creatorCode;//录入人代码
    private Date creatorDate;//创建时间
    private String notes;//备注
    private Integer ringFlag;//是否为环状数据Code

    public ChangeInfo() {
    }

    public ChangeInfo(Map<String, String> param) {
        this.changeType = param.get("changeType");
        this.creatorCode = param.get("creatorCode");
        this.groupSeq = Integer.valueOf(param.get("groupSeq"));
        this.organizationCode = param.get("organizationCode");
        this.originalZoningCode = param.get("originalZoningCode");
        this.originalZoningName = param.get("originalZoningName");
        this.targetZoningCode = param.get("targetZoningCode");
        this.targetZoningName = param.get("targetZoningName");
        this.assigningCode = param.get("assigningCode");
        this.requestSeq = Integer.valueOf(param.get("requestSeq"));
        this.notes = param.get("notes");
        this.ringFlag = Integer.valueOf(param.get("ringFlag"));
    }

    public ChangeInfo(LinkedTreeMap<String, String> param) {
        this.changeType = param.get("changeType");
        this.creatorCode = param.get("creatorCode");
        this.groupSeq = Integer.valueOf(param.get("groupSeq"));
        this.organizationCode = param.get("organizationCode");
        this.originalZoningCode = param.get("originalZoningCode");
        this.originalZoningName = param.get("originalZoningName");
        this.targetZoningCode = param.get("targetZoningCode");
        this.targetZoningName = param.get("targetZoningName");
        this.assigningCode = param.get("assigningCode");
        this.requestSeq = Integer.valueOf(param.get("requestSeq"));
        this.notes = param.get("notes");
        this.ringFlag = Integer.valueOf(param.get("ringFlag"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangeInfo that = (ChangeInfo) o;

        //变更类型为“新增”的情况，原区划代码与原区划名称为空，所以要分开处理
        if(changeType.equals(Common.ADD)){
            return targetZoningCode.equals(that.targetZoningCode);
        }else {
            return originalZoningCode.equals(that.originalZoningCode) && targetZoningCode.equals(that.targetZoningCode);
        }
    }

    public ZCCDetail toDetail(){
        ZCCDetail detail = new ZCCDetail();
        detail.setChangeType(this.changeType);
        detail.setOriginZoningCode(this.originalZoningCode);
        detail.setOriginZoningName(this.getOriginalZoningName());
        detail.setCurrentZoningCode(this.targetZoningCode);
        detail.setCurrentZoningName(this.targetZoningName);
        detail.setGroupSeq(this.groupSeq);
        detail.setCreatorCode(this.creatorCode);
        detail.setCreatorDeptCode(this.organizationCode);
        detail.setNotes(this.notes);
        detail.setCreatorDate(StringUtil.getTime());
        detail.setStatus(Common.XZQH_CLZT_DCL);
        detail.setRingFlag(this.ringFlag);
        return detail;
    }

    @Override
    public int hashCode() {
        int result = targetZoningCode.hashCode();
        result = 31 * result + targetZoningName.hashCode();
        return result;
    }

    @Override
    public ChangeInfo clone() throws CloneNotSupportedException {
        return (ChangeInfo) super.clone();
    }
/*
    @Override
    public String toString() {
        return " originalZoningCode is " + originalZoningCode
                + ", targetZoningCode is " + targetZoningCode + ", hashCode = " + this.hashCode();
    }*/



    public Date getCreatorDate() {
        return creatorDate;
    }

    public void setCreatorDate(Date creatorDate) {
        this.creatorDate = creatorDate;
    }

    public Integer getRequestSeq() {
        return requestSeq;
    }

    public void setRequestSeq(Integer requestSeq) {
        this.requestSeq = requestSeq;
    }

    public Integer getGroupSeq() {
        return groupSeq;
    }

    public void setGroupSeq(Integer groupSeq) {
        this.groupSeq = groupSeq;
    }

    public String getOriginalZoningCode() {
        return originalZoningCode;
    }

    public void setOriginalZoningCode(String originalZoningCode) {
        this.originalZoningCode = originalZoningCode;
    }

    public String getOriginalZoningName() {
        return originalZoningName;
    }

    public void setOriginalZoningName(String originalZoningName) {
        this.originalZoningName = originalZoningName;
    }

    public String getTargetZoningCode() {
        return targetZoningCode;
    }

    public void setTargetZoningCode(String targetZoningCode) {
        this.targetZoningCode = targetZoningCode;
    }

    public String getTargetZoningName() {
        return targetZoningName;
    }

    public void setTargetZoningName(String targetZoningName) {
        this.targetZoningName = targetZoningName;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    //获取级次代码
    public String getAssigningCode() {
        if(this.assigningCode != null){
            return this.assigningCode;
        }else {
            setAssigningCode( Common.getAssigningCode(this.targetZoningCode));
            return this.assigningCode;
        }
    }

    public void setAssigningCode(String assigningCode) {
        this.assigningCode = assigningCode;
    }

    //获取级别代码
    public String getLevelCode() {
        if(this.levelCode != null){
            return this.levelCode;
        }else {
            setLevelCode(Common.getLevelCode(this.targetZoningCode));
            return this.levelCode;
        }
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getCreatorCode() {
        return creatorCode;
    }

    public void setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getRingFlag() {
        return ringFlag;
    }

    public void setRingFlag(Integer ringFlag) {
        this.ringFlag = ringFlag;
    }


}
