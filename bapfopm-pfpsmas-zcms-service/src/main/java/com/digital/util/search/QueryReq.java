package com.digital.util.search;

import com.digital.dao.sqlMapper.EntitySql;

import java.io.Serializable;
import java.util.*;

/**
 * 查询封装
 * @author guoyka
 * @version 1.0, 2018/4/13
 */
public class QueryReq implements Serializable{
    //public static final int DEFAULT_PAGE_SIZE = 10;

    private EntitySql entitySql;

    //需要查询的属性
    //考虑使用表达式{},把属性框起来，解析也许更快
    public String selectFields;

   /* public Integer limit;
    public Integer offset = 0;
    public Integer total;*/

   //排序
    public String sort;


    //查询条件
    public List<QueryFilter> search = new ArrayList();
    //public String searchLogic = "AND";

    //默认查询条件，未用上
    public List<QueryFilter> restrictions = new ArrayList();
    //public Map extraData = new HashMap();

    public QueryReq() {
    }

    public QueryReq(String sort) {
        this.sort = sort;
    }
/*
    public QueryReq(String sort, Integer limit) {
        this.sort = sort;
        this.limit = limit;
    }*/

    public QueryReq(String sort, String selectFields) {
        this.sort = sort;
        this.selectFields = selectFields;
    }


  /*  public QueryReq(Integer limit, String selectFields) {
        this.limit = limit;
        this.selectFields = selectFields;
    }*/

   /* public QueryReq(String sort, Integer limit, String selectFields) {
        this.sort = sort;
        //this.limit = limit;
        this.selectFields = selectFields;
    }*/

  /*  public QueryReq(String sort, Integer limit, Integer offset, Integer total, String selectFields) {
        this.sort = sort;
      *//*  this.limit = limit;
        this.offset = offset;
        this.total = total;*//*
        this.selectFields = selectFields;
    }*/

    public String getSelectFields() {
        return this.selectFields;
    }

    public QueryReq setSelectFields(String selectFields) {
        this.selectFields = selectFields;
        return this;
    }

    /* public Integer getLimit() {
        return this.limit;
    }

    public QueryReq setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }*/

   /* public Integer getOffset() {
        return this.offset;
    }

    public QueryReq setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public Integer getTotal() {
        return this.total;
    }

    public QueryReq setTotal(Integer total) {
        this.total = total;
        return this;
    }*/

    public String getSort() {
        return this.sort;
    }

    public QueryReq setSort(String sort) {
        this.sort = sort;
        return this;
    }

    /*public String getSearchLogic() {
        return this.searchLogic;
    }

    public QueryReq setSearchLogic(String searchLogic) {
        this.searchLogic = searchLogic;
        return this;
    }*/

    /*public Map getExtraData() {
        return this.extraData;
    }

    public QueryReq setExtraData(Map extraData) {
        this.extraData = extraData;
        return this;
    }
*/
    public QueryReq addFilter(String field, Object value, String operator) {
        return this.addFilter(new QueryFilter(field, value, operator));
    }

    public QueryReq addFilter(String field, Object value) {
        return this.addFilter(field, value, "is");
    }

    public QueryReq addFilter(QueryFilter filter) {
        if (!this.exists(this.search, filter.getField())) {
            this.search.add(filter);
        }
        return this;
    }

    public QueryReq addRestriction(String field, Object value, String operator) {
        return this.addRestriction(new QueryFilter(field, value, operator));
    }

    public QueryReq addRestriction(String field, Object value) {
        return this.addRestriction(field, value, "is");
    }

    public QueryReq addRestriction(QueryFilter filter) {
        if (!this.exists(this.restrictions, filter.getField())) {
            this.restrictions.add(filter);
        }

        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("selectFields=>").append(this.selectFields);
       /* sb.append(", limit=>").append(this.limit);
        sb.append(", offset=>").append(this.offset);
        sb.append(", total=>").append(this.total);
        sb.append(", searchLogic=>").append(this.searchLogic);*/
        sb.append(", search=>").append(this.search);
        sb.append(", restrictions=>").append(this.restrictions);
        //sb.append(", extraData=>").append(this.extraData);
        return sb.toString();
    }

    private boolean exists(List<QueryFilter> bucket, String field) {
        Iterator queryFilterIterator = bucket.iterator();

        QueryFilter queryFilter;
        do {
            if (!queryFilterIterator.hasNext()) {
                return false;
            }

            queryFilter = (QueryFilter)queryFilterIterator.next();
        } while(!queryFilter.getField().equals(field));

        return true;
    }
}
