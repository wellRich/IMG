package com.digital.entity;

import com.digital.util.search.Column;
import com.digital.util.search.Table;

import java.io.Serializable;

/**
 * 变更历史数据
 * 记录区划变更明细对照历史数据，以备查找变更过程
 * @author guoyka
 * @version 2018/4/5
 */
@Table(name = "XZQH_BGMXLSJL", primaryKey = "seq")
public class HistoricalZoningChange implements Serializable{

    //明细表序号
    @Column(name="MXBXH")
    private Integer seq;

    //变更对照组序号
    @Column(name = "GROUPXH")
    private Integer groupSeq;

    //原区划代码
    @Column(name = "YSXZQH_DM")
    private String originalZoningCode;

    //原区划名称
    @Column(name = "YSXZQH_MC")
    private String originalZoningName;

    //目标行政区划代码
    @Column(name = "MBXZQH_DM")
    private String targetZoningCode;

    //目标区划名称
    @Column(name = "MBXZQH_MC")
    private String targetZoningName;

    //变更类型
    @Column(name = "BGLX_DM")
    private String changeType;

    //录入人代码
    @Column(name = "LRR_DM")
    private String creatorCode;

    //录入时间
    @Column(name = "LRSJ")
    private String createDate;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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

    public String getCreatorCode() {
        return creatorCode;
    }

    public void setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
