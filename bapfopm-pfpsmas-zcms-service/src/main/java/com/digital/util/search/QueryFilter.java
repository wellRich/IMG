package com.digital.util.search;

import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 查询过滤条件
 * 用于封装sql查询的where子句信息
 * @author guoyka
 * @version 1.0, 2018/4/13
 */
public class QueryFilter implements Serializable {
    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(QueryFilter.class);

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
    public static final String OPR_NOT_CONTAINS = "NOT CONTAINS";

    public static final String LOGIC_AND = "AND";
    public static final String LOGIC_OR = "OR";

    protected String field;
    protected Object value;
    protected String operator = OPR_IS;
    protected String logic = LOGIC_AND;

    public QueryFilter(String field, Object value, String operator, String logic) {
        this.field = field;
        this.value = value;
        this.operator = operator;
        this.logic = logic;
    }


    public QueryFilter(String field, Object value, String operator) {
        this.field = field;
        this.value = value;
        this.operator = operator;
    }


    public QueryFilter(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("field=>").append(this.field);
        sb.append(", value=>").append(this.value);
        sb.append(", operator=>").append(this.operator);
        return sb.toString();
    }

    /**
     * 转化成sql子句
     * @return
     */
    public String toSql(){
        StringBuilder sql = new StringBuilder();
        switch (this.operator){
            case OPR_IS:
                sql.append(" ").append(field).append(" ").append(operator).append(" '").append(value).append("'");
                break;
            case OPR_IS_NOT:
                sql.append(" ").append(field).append(" ").append(operator).append(" '").append(value).append("'");
                break;
            case OPR_LESS_THAN:
                sql.append(" ").append(field).append(" ").append(operator).append(" '").append(value).append("'");
                break;
            case OPR_MORE_THAN:
                sql.append(" ").append(field).append(" ").append(operator).append(" '").append(value).append("'");
                break;
            case OPR_IN:
                sql.append(" ").append(field).append(" ").append(operator).append(" (").append(value).append(")");
                break;
            case OPR_NOT_IN:
                sql.append(" ").append(field).append(" ").append(operator).append(" (").append(value).append(")");
                break;
            case OPR_IS_NULL:
                sql.append(" ").append(field).append(" ").append(operator);
                break;
            case OPR_IS_NOT_NULL:
                sql.append(" ").append(field).append(" ").append(operator);
                break;
            case OPR_LIKE:
                sql.append(" ").append(field).append(" ").append(operator).append(" '").append(value).append("'");
                break;
            case OPR_BETWEEN:
                try {
                    Object[] objects = (Object[])value;
                    sql.append(" ").append(field).append(" ").append(operator).append(" '").append(objects[0]).append(" AND ").append(objects);
                }catch (Exception ex){
                    log.error("QueryFilter.toSql---> " + ex.getMessage());
                }

                break;
            case OPR_CONTAINS:
                sql.append(" ").append(operator).append("(").append(field).append(", ").append("('").append(value).append("')");
                break;

            case OPR_NOT_CONTAINS:
                sql.append(" ").append(operator).append("(").append(field).append(", ").append("('").append(value).append("')");
                break;

            case OPR_MATCH:
                sql.append(" ").append(operator).append("('").append(field).append("')").append("AGAINST('").append(value).append("')");
                break;
                default:
                    log.warn("未定义的操作符[" + operator + "]");
                    return value.toString();

        }
        return sql.toString();
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

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }
}
