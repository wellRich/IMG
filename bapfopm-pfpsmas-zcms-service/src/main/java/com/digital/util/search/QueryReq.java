package com.digital.util.search;

import java.util.*;

/**
 * 查询封装
 * @author guoyka
 * @version 1.0, 2018/4/13
 */
public class QueryReq {
    public static final int DEFAULT_PAGE_SIZE = 10;
    public String select;
    public Integer limit;
    public Integer offset = 0;
    public Integer total;
    public String sort;
    public List<QueryFilter> search = new ArrayList();
    public String searchLogic = "AND";
    public List<QueryFilter> restrictions = new ArrayList();
    public Map extraData = new HashMap();

    public QueryReq() {
    }

    public QueryReq(String sort) {
        this.sort = sort;
    }

    public QueryReq(String sort, Integer limit) {
        this.sort = sort;
        this.limit = limit;
    }

    public QueryReq(String sort, String select) {
        this.sort = sort;
        this.select = select;
    }

    public QueryReq(Integer limit, String select) {
        this.limit = limit;
        this.select = select;
    }

    public QueryReq(String sort, Integer limit, String select) {
        this.sort = sort;
        this.limit = limit;
        this.select = select;
    }

    public QueryReq(String sort, Integer limit, Integer offset, Integer total, String select) {
        this.sort = sort;
        this.limit = limit;
        this.offset = offset;
        this.total = total;
        this.select = select;
    }

    public String getSelect() {
        return this.select;
    }

    public QueryReq setSelect(String select) {
        this.select = select;
        return this;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public QueryReq setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
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
    }

    public String getSort() {
        return this.sort;
    }

    public QueryReq setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public String getSearchLogic() {
        return this.searchLogic;
    }

    public QueryReq setSearchLogic(String searchLogic) {
        this.searchLogic = searchLogic;
        return this;
    }

    public Map getExtraData() {
        return this.extraData;
    }

    public QueryReq setExtraData(Map extraData) {
        this.extraData = extraData;
        return this;
    }

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
        sb.append("select=>").append(this.select);
        sb.append(", limit=>").append(this.limit);
        sb.append(", offset=>").append(this.offset);
        sb.append(", total=>").append(this.total);
        sb.append(", searchLogic=>").append(this.searchLogic);
        sb.append(", search=>").append(this.search);
        sb.append(", restrictions=>").append(this.restrictions);
        sb.append(", extraData=>").append(this.extraData);
        return sb.toString();
    }

    private boolean exists(List<QueryFilter> bucket, String field) {
        Iterator var3 = bucket.iterator();

        QueryFilter f;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            f = (QueryFilter)var3.next();
        } while(!f.getField().equals(field));

        return true;
    }
}
