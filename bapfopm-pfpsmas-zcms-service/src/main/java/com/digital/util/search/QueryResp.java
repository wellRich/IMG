package com.digital.util.search;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 2018/3/21
 */
public final class QueryResp<T> {

    /*
    数据总数
     */
    private int totalRecord;

    /*
    每页大小
     */
    private int pageSize;

    /*
    当前页码
     */
    private int pageIndex;

    /*
    总页数
     */
    private int totalPage;

    /*
    实体数据
     */
    private List<T> dataList;


    public Map<String, Object> toMap() throws IllegalAccessException{
        Map<String, Object> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field: fields){
            map.put(field.getName(), field.get(this));
        }
        return map;
    }

    public QueryResp() {
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRecord() {
        return this.totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageCount() {
        return this.pageSize == 0 ? 0 : (this.totalRecord + this.pageSize - 1) / this.pageSize;
    }
}
