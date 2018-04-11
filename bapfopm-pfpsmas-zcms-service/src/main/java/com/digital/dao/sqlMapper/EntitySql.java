package com.digital.dao.sqlMapper;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

import com.digital.util.search.BaseEntity;
import com.digital.util.search.Column;
import com.digital.util.search.Table;



/**
 * CRUD的默认实现SqlProvider
 * 〈功能详细描述〉
 *
 * @author [作者]
 * @version 2018/3/17
 * @see [相关类/方法]
 * @since [产品/模块版本]
 
 */
public abstract class EntitySql<T extends Serializable> implements BaseEntity<T> {
    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(ZCCGroupSql.class);

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表格主键对应的实体属性名称
     */
    private String primaryField;



    /**
     * 众列名称，以逗号分割的字符串
     */
    private String columns;

    /**
     * 不包括主键的众列名称，同样是以逗号分割的字符串
     */
    private String columnsExcPrimary;

    /**
     * 结构[属性：字段]
     */
    private Map<String, String> fieldsAndCols = new HashMap<>();

    /**
     * [属性：字段]，不包括主键
     */
    private Map<String, String> fiesAndColsExcPrimary = new HashMap<>();


    private Class<T> clazz;

    protected EntitySql() {
        clazz = init();
        Table table = clazz.getAnnotation(Table.class);
        this.tableName = table.name();
        this.primaryField = table.primaryKey();
        Field[] fields = clazz.getDeclaredFields();
        int size = fields.length;
        Field field;
        String colName;
        String fieldName;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            field = fields[i];
            fieldName = field.getName();
            if(fields[i].isAnnotationPresent(Column.class)){
                colName = field.getAnnotation(Column.class).name();
                if(!this.primaryField.equals(field.getName())){//过滤有问题
                    this.fiesAndColsExcPrimary.put(fieldName, colName);
                }
                this.fieldsAndCols.put(fieldName, colName);
                sb.append(colName).append(i < size - 1 ? "," : "");
            }
        }
        this.columns = sb.toString();
        //fieldsAndCols.forEach((k, v) -> System.out.println("fieldsAndCols===== k--------> " + k + ", v ----> " + v));
        //this.columnsExcPrimary = this.columns.replace(getColumnByField(getPrimaryField()) + ",", "");
        this.columnsExcPrimary = StringUtils.join(fiesAndColsExcPrimary.keySet(), ",");
    }

    protected abstract Class<T> init();

    //需要修改
    public String insert(final Object entity){
        String sql = new SQL(){{
            INSERT_INTO(tableName);
           // getFiesAndColsExcPrimary().forEach((k, v) -> VALUES(v, "#{" + k + "}"));
            getFieldsAndCols().forEach((k, v) -> VALUES(v, "#{" + k + "}"));
        }}.toString();
        log.info("insert.sql----> " + sql);
        return sql;
    }

    public String findAll(){
        return new SQL(){{
            SELECT(columns);
            FROM(tableName);
        }}.toString();
    }

    public String update(final Object group){
        String sql;
        if(group == null){
            throw  new RuntimeException("传入对象不存在！");
        }else {
            if(isEntity(group)){
                sql = new SQL(){{
                    UPDATE(tableName);
                    getFiesAndColsExcPrimary().forEach((k, v) -> SET(v + "=#{" + k + "}"));
                    WHERE(getColumnByField(primaryField) + "=#{" + primaryField + "}");
                }}.toString();
            }else if(group instanceof Map){
                Map temp = (Map)group;
                if(temp.containsKey(primaryField)){
                    sql = new SQL(){{
                        UPDATE(tableName);
                        temp.forEach((k, v) ->{
                            SET(getColumnByField(k.toString()) + "=#{" + k + "}");
                        });
                        WHERE(getColumnByField(primaryField) + "=#{" + primaryField + "}");
                    }}.toString();
                }else {
                    throw new RuntimeException("未传入主键值！");
                }
            }else {
                throw new IllegalArgumentException("只接收实体对象或者[属性-属性值]结构的map对象");
            }
            log.info("update.sql-------> " + sql);
            return sql;
        }
    }

    //通过主键获取对象
    public String get(Object primaryKey) {
        String sql = new SQL() {{
            FROM(tableName);
            SELECT(columns);
            WHERE(getColumnByField(getPrimaryField()) + "=" + primaryKey);
        }}.toString();
        log.info("getByPrimaryKey.sql------------> " + sql);
        return sql;
    }


    /**
     * 通过若干主键获取对象集合
     * @param ids 以“,”分隔的id
     * @return sql
     */
    public String findByIds(String ids){
        String sql = new SQL(){{
            FROM(tableName);
            SELECT("*");
            WHERE(getColumnByField(getPrimaryField()) + "  IN (" + ids + ")");
        }}.toString();
        log.info("findByIds.sql ---> " + sql);
        return sql;
    }

    //通过字段名获取数据列名
    protected String getColumnByField(String fieldName){
        return fieldsAndCols.get(fieldName);
    }

    /**
     * 通过若干属性查询
      * @param queryReq 查询请求
     */
    public String seek(Object queryReq){
        String sql = new SQL(){{

        }}.toString();
        log.info("seek.sql--------> " + sql);
        return sql;
    }

    public String delete(Object primaryKey){
        String sql;
        if(primaryKey == null){
            log.error("操作的对象为空！");
            throw new RuntimeException("对象为空！");
        }else {
            if(isEntity(primaryKey)){
                sql =  new SQL(){{
                    DELETE_FROM(getTableName());
                    WHERE(getColumnByField(getPrimaryField()) + "=#{" + getPrimaryField() + "}");
                }}.toString();
            }else {
                sql = new SQL(){{
                    DELETE_FROM(getTableName());
                    WHERE(getColumnByField(getPrimaryField()) + "=" + primaryKey);
                }}.toString();
            }
            log.info("deleteRequest.sql--------------> " + sql);
            return sql;
        }
    }

    /**
     *
     * @return sql
     */
    public String batchDelete(Collection<?> keys){
        String sql = new SQL(){{
            DELETE_FROM(getTableName());
            WHERE(getColumnByField("seq") + " IN (#{" + StringUtils.join(keys, ",")  + "})");
        }}.toString();
        log.info("batchDelete.sql----------> " + sql);
        return sql;
    }

    private boolean isEntity(Object obj){
        return obj.getClass().equals(clazz);
    }

    public String getTableName() {
        return tableName;
    }


    public Map<String, String> getFieldsAndCols() {
        return fieldsAndCols;
    }

    public String getColumns() {
        return columns;
    }

    public String getColumnsExcPrimary() {
        return columnsExcPrimary;
    }

    public Map<String, String> getFiesAndColsExcPrimary() {
        return fiesAndColsExcPrimary;
    }

    public String getPrimaryField() {
        return primaryField;
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
