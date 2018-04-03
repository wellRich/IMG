package com.digital.entity;

import com.digital.util.search.Column;
import com.digital.util.search.Table;

import java.io.Serializable;

/**
 * @Description: TODO 区划数据预览数据
 * @Author: zhanghpj
 * @Version 1.0, 14:32 2018/3/12
 * @See
 * @Since
 * @Deprecated
 */
@Table(name = "DM_XZQH_YLSJ", primaryKey = "XZQH_DM")
public class PreviewDataInfo implements Serializable {

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
     * 选用标志
     * */
    @Column(name = "XYBZ")
    public String chooseSign;

    /*
     * 有效标志
     * */
    @Column(name = "YXBZ")
    public String usefulSign;

    /*
     * 单位隶属关系
     * */
    @Column(name = "DWLSGX_DM")
    public String subordinateRelations;

    /*
     *有效期起
     * */
    @Column(name = "YXQ_Q")
    public String validityStart;

    /*
     * 有效期止
     * */
    @Column(name = "YXQ_Z")
    public String validityStup;

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
    private String creatorDate;



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

    public String getChooseSign() {
        return chooseSign;
    }

    public void setChooseSign(String chooseSign) {
        this.chooseSign = chooseSign;
    }

    public String getUsefulSign() {
        return usefulSign;
    }

    public void setUsefulSign(String usefulSign) {
        this.usefulSign = usefulSign;
    }

    public String getSubordinateRelations() {
        return subordinateRelations;
    }

    public void setSubordinateRelations(String subordinateRelations) {
        this.subordinateRelations = subordinateRelations;
    }

    public String getValidityStart() {
        return validityStart;
    }

    public void setValidityStart(String validityStart) {
        this.validityStart = validityStart;
    }

    public String getValidityStup() {
        return validityStup;
    }

    public void setValidityStup(String validityStup) {
        this.validityStup = validityStup;
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

    public String getCreatorDate() {
        return creatorDate;
    }

    public void setCreatorDate(String creatorDate) {
        this.creatorDate = creatorDate;
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
}
