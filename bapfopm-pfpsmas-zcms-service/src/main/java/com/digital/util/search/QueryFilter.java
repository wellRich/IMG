package com.digital.util.search;

import java.io.Serializable;

/**
 * 查询过滤条件
 * 用于封装sql查询的where子句信息
 * @author guoyka
 * @version 1.0, 2018/4/13
 */
public class QueryFilter implements Serializable {
    public static final String OPR_IS = "=";
    public static final String OPR_IS_NOT = "NOT";
    public static final String OPR_IS_NULL = "IS NULL";
    public static final String OPR_IS_NOT_NULL = "IS NOT NULL";
    public static final String OPR_IN = "IN";
    public static final String OPR_NOT_IN = "NOT IN";
    public static final String OPR_LESS_THAN = "<";
    public static final String OPR_MORE_THAN = ">";
    public static final String OPR_BETWEEN = "BETWEEN";
    public static final String OPR_LIKE = " LIKE ";
    public static final String OPR_MATCH = "MATCH";
    public static final String OPR_CONTAINS = "CONTAINS";

    public static final String LOGIC_AND = "AND";
    public static final String LOGIC_OR = "OR";
    protected String field;
    protected Object value;
    protected String operator;

    public QueryFilter(String field, Object value, String operator) {
        this.field = field;
        this.value = value;
        this.operator = operator;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("field=>").append(this.field);
        sb.append(", value=>").append(this.value);
        sb.append(", operator=>").append(this.operator);
        return sb.toString();
    }

    public QueryFilter(String field, Object value) {
        this(field, value, "is");
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
