package com.digital.entity;

import com.digital.util.search.Column;
import com.digital.util.search.Table;

import java.io.Serializable;

/**
 * @Description: TODO 历史数据表
 * @Author: zhanghpj
 * @Version 1.0, 16:23 2018/4/16
 * @See
 * @Since
 * @Deprecated
 */
@Table(name = "dm_xzqh_old",primaryKey = "index")
public class HistoricalDataInfo implements Serializable {

    /*
     *序列号
     * */
    @Column(name = "ID")
    private Integer index;
    /*
     * 区划对应的唯一主键
     * */
    @Column(name = "UNIQUE_KEY")
    public String uniqueKey;
    /*
     * 行政区划代码
     * */
    @Column(name = "XZQH_DM")
    public String zoningCode;

    /*
     * 行政区划名称
     * */
    @Column(name = "XZQH_MC")
    public String divisionName;

    /*
     * 行政区划简称
     * */
    @Column(name = "XZQH_JC")
    public String divisionAbbreviation;

    /*
     * 行政区划全称
     * */
    @Column(name = "XZQH_QC")
    public String divisionFullName;

    /*
     * 级次代码
     * */
    @Column(name = "JCDM")
    public String  assigningCode;

    /*
     * 级别代码
     * */
    @Column(name = "JBDM")
    public String levelCode;

    /*
     * 上级行政区划代码
     * */
    @Column(name = "SJ_XZQH_DM")
    public String superiorZoningCode;

    /*
     * 单位隶属关系
     * */
    @Column(name = "DWLSGX_DM")
    public String subordinateRelations;

    /*
     *虚拟节点标志
     * */
    @Column(name = "XNJD_BZ")
    public String virtualNode;

    /*
     * 旧行政区划代码
     * */
    @Column(name = "OLD_XZQH_DM")
    public String oldZoningCode;

    /*
     * 权限机构代码
     * */
    @Column(name = "QX_JGDM")
    public String accessCode;

    /*
     * 录入人代码
     * */
    @Column(name = "LRR_DM")
    public String enterOneCode;

    /*
     * 录入时间
     * */
    @Column(name = "LRSJ")
    private String createDate;



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
     * 行政区划类型代码
     * */
    @Column(name = "XZQHLX_DM")
    private String type = "11";

    /*
    * 版本号
    * */
    @Column(name = "VERSION")
    private Integer version;

    public HistoricalDataInfo() {
    }

    public void PreviewDataToHistoricalDataInfo(PreviewDataInfo previewDataInfo) {

        this.uniqueKey = previewDataInfo.getUniqueKey();
        this.zoningCode = previewDataInfo.getZoningCode();
        this.divisionName = previewDataInfo.getDivisionName();
        this.divisionAbbreviation = previewDataInfo.getDivisionAbbreviation();
        this.divisionFullName = previewDataInfo.getDivisionFullName();
        this.assigningCode = previewDataInfo.getAssigningCode();
        this.levelCode = previewDataInfo.getLevelCode();
        this.superiorZoningCode = previewDataInfo.getSuperiorZoningCode();
        this.subordinateRelations = previewDataInfo.getSubordinateRelations();
        this.virtualNode = previewDataInfo.getVirtualNode();
        this.oldZoningCode = previewDataInfo.getOldZoningCode();
        this.accessCode = previewDataInfo.getAccessCode();
        this.enterOneCode = previewDataInfo.getEnterOneCode();
        this.createDate = previewDataInfo.getCreateDate();
        this.updaterCode = previewDataInfo.getUpdaterCode();
        this.lastUpdate = previewDataInfo.getLastUpdate();
        this.type = previewDataInfo.getType();

    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getZoningCode() {
        return zoningCode;
    }

    public void setZoningCode(String zoningCode) {
        this.zoningCode = zoningCode;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getDivisionAbbreviation() {
        return divisionAbbreviation;
    }

    public void setDivisionAbbreviation(String divisionAbbreviation) {
        this.divisionAbbreviation = divisionAbbreviation;
    }

    public String getDivisionFullName() {
        return divisionFullName;
    }

    public void setDivisionFullName(String divisionFullName) {
        this.divisionFullName = divisionFullName;
    }

    public String getAssigningCode() {
        return assigningCode;
    }

    public void setAssigningCode(String assigningCode) {
        this.assigningCode = assigningCode;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getSuperiorZoningCode() {
        return superiorZoningCode;
    }

    public void setSuperiorZoningCode(String superiorZoningCode) {
        this.superiorZoningCode = superiorZoningCode;
    }



    public String getSubordinateRelations() {
        return subordinateRelations;
    }

    public void setSubordinateRelations(String subordinateRelations) {
        this.subordinateRelations = subordinateRelations;
    }



    public String getVirtualNode() {
        return virtualNode;
    }

    public void setVirtualNode(String virtualNode) {
        this.virtualNode = virtualNode;
    }

    public String getOldZoningCode() {
        return oldZoningCode;
    }

    public void setOldZoningCode(String oldZoningCode) {
        this.oldZoningCode = oldZoningCode;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getEnterOneCode() {
        return enterOneCode;
    }

    public void setEnterOneCode(String enterOneCode) {
        this.enterOneCode = enterOneCode;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
