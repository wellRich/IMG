package com.digital.entity.province;

/**
 * @Description: TODO 集中变更区划数据临时表
 * @Version 1.0, 15:47 2018/2/24
 * @Author: zhanghpj
 * @See
 * @Since
 * @Deprecated
 */

public class ProvinceSectionalData {

    /*
    * 上传文件序号
    * */
    private Integer fileNum;
    /*
    * 区划代码
    * */
    private String zoningCode;
    /*
    * 区划名称
    * */
    private String divisionName;
    /*
    * 导入时间
    * */
    private String date;


    public Integer getFileNum() {
        return fileNum;
    }

    public void setFileNum(Integer fileNum) {
        this.fileNum = fileNum;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
