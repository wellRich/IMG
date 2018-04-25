package com.digital.entity;

import java.io.Serializable;
import com.digital.util.Common;
import com.digital.util.JSONHelper;
import com.digital.util.search.Column;
import com.digital.util.search.Table;

/**
 * 行政区划变更对照表明细
 *
 * @author guoyka
 * @version 2018/3/15
 */
@Table(name = "XZQH_BGMXB", primaryKey = "seq")
public class ZCCDetail implements Serializable {
    /**
     * 明细表序号
     */
    @Column(name = "MXBXH")
    private Integer seq;

    /**
     * 所属变更组的序号
     */
    @Column(name = "GROUPXH")
    private Integer groupSeq;

    /*
    * 所属变更组名称
    * */
    private String groupName;


    /**
     * 原区划代码
     */
    @Column(name = "YSXZQH_DM")
    private String originalZoningCode;

    /**
     * 原区划名称
     */
    @Column(name = "YSXZQH_MC")
    private String originalZoningName;

    /**
     * 变更类型
     */
    @Column(name = "BGLX_DM")
    private String changeType;

    /**
     * 现区划代码
     */
    @Column(name = "MBXZQH_DM")
    private String currentZoningCode;

    /**
     * 现区划名称
     */
    @Column(name = "MBXZQH_MC")
    private String currentZoningName;

    /**
     * 处理状态
     */
    @Column(name = "CLZT_DM")
    private String status;

    /**
     * 处理结果
     */
    @Column(name = "CLJG")
    private String result;

    /**
     * 备注
     */
    @Column(name = "BZ")
    private String notes;

    /*
   * 录入人代码
   * */
    @Column(name = "LRR_DM")
    private String creatorCode;

    /*
    * 录入时间
    * */
    @Column(name = "LRSJ")
    private String creatorDate;

    /*
    * 录入机构代码
    * */
    @Column(name = "LRJG_DM")
    private String creatorDeptCode;

    /*
    * 修改人代码
    * */
    @Column(name = "XGR_DM")
    private String updaterCode;


    /*
    * 修改时间
    * */
    @Column(name = "XGSJ")
    private String lastUpdate;


    /*
    * 修改机构代码
    * */
    @Column(name = "XGJG_DM")
    private String updaterDeptCode;

    /**
     * 排序号
     */
    @Column(name = "PXH")
    private Integer orderNum;

    /**
     * 环状数据标志
     */
    @Column(name = "RINGFLAG")
    private Integer ringFlag;

    public String translateChangeType(String changeType){
        if(changeType.equals(Common.ADD)){
            return "新增";
        }else if(changeType.equals(Common.CHANGE)){
            return "变更";
        }else if(changeType.equals(Common.MERGE)){
            return "并入";
        }else if(changeType.equals(Common.MOVE)){
            return "迁移";
        }else {
            return "N/A";
        }
    }

    @Override
    public String toString() {
        return JSONHelper.toJSON(this);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String displayChangeType(){
        return translateChangeType(changeType);
    }

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

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getCurrentZoningCode() {
        return currentZoningCode;
    }

    public void setCurrentZoningCode(String currentZoningCode) {
        this.currentZoningCode = currentZoningCode;
    }

    public String getCurrentZoningName() {
        return currentZoningName;
    }

    public void setCurrentZoningName(String currentZoningName) {
        this.currentZoningName = currentZoningName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatorCode() {
        return creatorCode;
    }

    public void setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
    }

    public String getCreatorDate() {
        return creatorDate;
    }

    public void setCreatorDate(String creatorDate) {
        this.creatorDate = creatorDate;
    }

    public String getCreatorDeptCode() {
        return creatorDeptCode;
    }

    public void setCreatorDeptCode(String creatorDeptCode) {
        this.creatorDeptCode = creatorDeptCode;
    }

    public String getUpdaterCode() {
        return updaterCode;
    }

    public void setUpdaterCode(String updaterCode) {
        this.updaterCode = updaterCode;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUpdaterDeptCode() {
        return updaterDeptCode;
    }

    public void setUpdaterDeptCode(String updaterDeptCode) {
        this.updaterDeptCode = updaterDeptCode;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getRingFlag() {
        return ringFlag;
    }

    public void setRingFlag(Integer ringFlag) {
        this.ringFlag = ringFlag;
    }

}
