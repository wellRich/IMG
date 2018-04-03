package com.digital.entity;

import com.digital.util.JSONHelper;
import com.digital.util.search.Column;
import com.digital.util.search.Table;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.commons.Method;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 行政区划代码变更申请单
 *
 * @author guoyka
 * @version 2018/3/14
 */
@Table(name = "xzqh_bgsqd", primaryKey = "seq")
public class ZCCRequest implements Serializable {

    /*
     * 申请单序号
     * */
    @Column(name = "SQDXH")
    private Integer seq;

    /*
     * 申请单名称
     * */
    @Column(name = "SQDMC")
    private String name;

    /*
     * 申请单状态
     * */
    @Column(name = "SQDZT_DM")
    private String status;

    /*
     * 上报区划的区划代码
     * */
    @Column(name = "SBXZQH_DM")
    private String ownZoningCode;



    /*
     * 录入人代码
     * */
    @Column(name = "LRR_DM")
    private String creatorCode;

    /*
     * 录入时间
     * */
    @Column(name = "LRSJ")
    private String createDate;

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

    /*
     * 审批意见
     * */
    @Column(name = "SPYJ")
    private String approvalOpinion;

    /*
     * 审批人代码
     * */
    @Column(name = "SPR_DM")
    private String verifierCode;

    /*
     * 备注
     * */
    @Column(name = "BZ")
    private String notes;

    @Override
    public String toString() {
        return "“序号：" + seq + ", 说明：" + notes + "”";
    }

    public Map<String, Object> toMap() throws IllegalAccessException{
        Map<String, Object> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field: fields){
            map.put(field.getName(), field.get(this));
        }
        return map;
    }


    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnZoningCode() {
        return ownZoningCode;
    }

    public void setOwnZoningCode(String ownZoningCode) {
        this.ownZoningCode = ownZoningCode;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getApprovalOpinion() {
        return approvalOpinion;
    }

    public void setApprovalOpinion(String approvalOpinion) {
        this.approvalOpinion = approvalOpinion;
    }

    public String getVerifierCode() {
        return verifierCode;
    }

    public void setVerifierCode(String verifierCode) {
        this.verifierCode = verifierCode;
    }
}