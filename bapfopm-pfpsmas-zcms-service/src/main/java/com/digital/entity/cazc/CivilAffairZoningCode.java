package com.digital.entity.cazc;

import java.io.Serializable;

/**
 * @author zhangwei
 * @description   民政区划代码实体类
 * @date created in 11:29 2018/4/2
 * @throws Exception
 */
public class CivilAffairZoningCode implements Serializable {

    /*
       文件序号
     */
    private Integer zipXh;
    /*
       行政区代码
     */
    private String zoningCode;
    /*
    行政区划名称
     */
    private String zoningName;
    /*
    上级行政区划代码
     */
    private String superiorZoningCode;
    /*
       级次代码
     */
    private String assigningCode;
    /*
    录入时间
     */
    private String enterTime;
    /*
      录入机构代码
     */
    private String enterOneCode;

    public Integer getZipXh() {
        return zipXh;
    }

    public void setZipXh(Integer zipXh) {
        this.zipXh = zipXh;
    }

    public String getZoningCode() {
        return zoningCode;
    }

    public void setZoningCode(String zoningCode) {
        this.zoningCode = zoningCode;
    }

    public String getZoningName() {
        return zoningName;
    }

    public void setZoningName(String zoningName) {
        this.zoningName = zoningName;
    }

    public String getSuperiorZoningCode() {
        return superiorZoningCode;
    }

    public void setSuperiorZoningCode(String superiorZoningCode) {
        this.superiorZoningCode = superiorZoningCode;
    }

    public String getAssigningCode() {
        return assigningCode;
    }

    public void setAssigningCode(String assigningCode) {
        this.assigningCode = assigningCode;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getEnterOneCode() {
        return enterOneCode;
    }

    public void setEnterOneCode(String enterOneCode) {
        this.enterOneCode = enterOneCode;
    }

    @Override
    public String toString() {
        return "CivilAffairZoneCode{" +
                "zipXh=" + zipXh +
                ", zoningCode='" + zoningCode + '\'' +
                ", zoningName='" + zoningName + '\'' +
                ", upperCode='" + superiorZoningCode + '\'' +
                ", levelCode='" + assigningCode + '\'' +
                ", enterTime=" + enterTime +
                ", enterOneCode='" + enterOneCode + '\'' +
                '}';
    }
}
