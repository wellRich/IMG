package com.digital.util.search;

import com.github.pagehelper.PageInfo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 用于封装分页查询
 *
 * @author guoyka
 * @version 2018/3/21
 */
public final class QueryResp<T>{

    /*
    数据总数
     */
    private int totalRecord;

    /*
    每页大小
     */
    private int pageSize;


    private int offset;


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

    public QueryResp(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.offset = (pageIndex - 1) * pageSize;
    }

    public void query(Supplier<List<T>> supplier){
        setDataList(supplier.get());
    }

    public void count(Supplier<Integer> count){
        System.out.println("QueryResp.count -----> " + count.get());
        setTotalRecord(count.get());
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
        setTotalPage(getPageCount());
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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    protected int getPageCount() {
        return getPageSize() == 0 ? 0 : (getTotalRecord() + getPageSize() - 1) / getPageSize();
    }
}
