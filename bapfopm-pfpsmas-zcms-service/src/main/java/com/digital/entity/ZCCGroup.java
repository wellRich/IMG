package com.digital.entity;

import com.digital.util.search.Column;
import com.digital.util.search.Table;

import java.io.Serializable;

/**
 * 行政区划代码变更对照组
 *
 * @author guoyka
 * @version 2018/3/15
 */
@Table(name = "XZQH_BGGROUP", primaryKey = "seq")
public class ZCCGroup implements Serializable{

    public final static String tableName = "XZQH_BGGROUP";
    /*
    * 组序号
    * */
    @Column(name = "GROUPXH")
    private Integer seq;

    /*
     *编号
     **/
    @Column(name = "BH")
    private Long serialNumber;

    /**
     *名称(调整说明)
     */
    @Column(name = "GROUPMC")
    private String name;

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

    /**
     * 行政区划变更申请单序号
     */
    @Column(name = "SQDXH")
    private Integer requestSeq;


    /*
     * 排序号
     */
    @Column(name = "PXH")
    private String orderNum;

    @Override
    public String toString() {
        return name;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getRequestSeq() {
        return requestSeq;
    }

    public void setRequestSeq(Integer requestSeq) {
        this.requestSeq = requestSeq;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
