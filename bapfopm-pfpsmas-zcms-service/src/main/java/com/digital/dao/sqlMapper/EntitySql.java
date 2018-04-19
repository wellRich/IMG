package com.digital.dao.sqlMapper;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import com.digital.util.search.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;


/**
 * CRUD的默认实现SqlProvider
 *
 * @author guoyka
 * @version 2018/3/17
 */
public abstract class EntitySql<T extends Serializable> implements BaseDao<T> {
    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(EntitySql.class);

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


    public abstract Class<T> init();

    protected  EntitySql(Class<T> clazz){
        log.info("entitySql有参构造器构造器被调用.clazz------------》 " + clazz);
        this.clazz = clazz;
        Table table = clazz.getAnnotation(Table.class);
        this.tableName = table.name();
        this.primaryField = table.primaryKey();
        Field[] fields = clazz.getDeclaredFields();
        int size = fields.length;
        Field field;
        String colName;
        String fieldName;
        StringBuilder sb = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            field = fields[i];
            fieldName = field.getName();
            if(fields[i].isAnnotationPresent(Column.class)){
                colName = field.getAnnotation(Column.class).name();
                if(!this.primaryField.equals(field.getName())){//过滤有问题
                    this.fiesAndColsExcPrimary.put(fieldName, colName);
                    stringBuilder.append(colName).append(i < size - 1 ? "," : "");
                }
                this.fieldsAndCols.put(fieldName, colName);
                sb.append(colName).append(i < size - 1 ? "," : "");
            }
        }
        this.columns = sb.toString();

        this.columnsExcPrimary = stringBuilder.toString();
        log.info("去除主键之后的列名:------------>:"+columnsExcPrimary);
    }

    protected  EntitySql() {
        log.info("entitySql无参构造器被调用------------> " + this);
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
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            field = fields[i];
            fieldName = field.getName();
            if(fields[i].isAnnotationPresent(Column.class)){
                colName = field.getAnnotation(Column.class).name();
                if(!this.primaryField.equals(field.getName())){//过滤有问题
                    this.fiesAndColsExcPrimary.put(fieldName, colName);
                    stringBuilder.append(colName).append(i < size - 1 ? "," : "");
                }
                this.fieldsAndCols.put(fieldName, colName);
                sb.append(colName).append(i < size - 1 ? "," : "");
            }
        }
        this.columns = sb.toString();
        this.columnsExcPrimary = stringBuilder.toString();
    }


    //需要修改
    public String insert(final Object entity){
        String sql = new SQL(){{
            INSERT_INTO(tableName);
           // getFiesAndColsExcPrimary().forEach((k, v) -> VALUES(v, "#{" + k + "}"));
            getFieldsAndCols().forEach((k, v) -> VALUES(v, "#{" + k + "}"));
        }}.toString();
        log.info("entitySql.insert.sql----> " + sql);
        return sql;
    }

    /**
     * 删除实例对象
     * @param obj 可以是主键、实例对象
     * @return
     */
    public String delete(Object obj){
        String sql;
        if(obj == null){
            log.error("操作的对象为空！");
            throw new RuntimeException("对象为空！");
        }else {
            if(isEntity(obj)){
                sql =  new SQL(){{
                    DELETE_FROM(getTableName());
                    WHERE(getColumnByField(getPrimaryField()) + "=#{" + getPrimaryField() + "}");
                }}.toString();
            }else {
                sql = new SQL(){{
                    DELETE_FROM(getTableName());
                    WHERE(getColumnByField(getPrimaryField()) + "=" + obj);
                }}.toString();
            }
            log.info("entitySql.delete.sql--------------> " + sql);
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
        log.info("entitySql.batchDelete.sql----------> " + sql);
        return sql;
    }

    /**
     * 更新实例对象
     * 如果传入map，则据键值对修改对象，
     * 如果传入是一个已在的实例，则会将此实例的所有属性更新至数据库，
     * 建议使用map作为参数
     * @param group 包含主键的map：[keyName: value] 或者 实例对象
     * @return sql
     */
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
            log.info("entitySql.update.sql-------> " + sql);
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
        log.info("entitySql.get.sql------------> " + sql);
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
            SELECT(getColumns());
            WHERE(getColumnByField(getPrimaryField()) + "  IN (" + ids + ")");
        }}.toString();
        log.info("entitySql.findByIds.sql ---> " + sql);
        return sql;
    }

    /**
     * 统计查询
     * @param field 统计的字段，默认是主键
     * @param filters 过滤条件
     * @return sql
     */
    public String countBy(String field, QueryFilter ... filters){
        String sql = new SQL(){{
            FROM(tableName);
            if(field != null && "".equals(field)){
                if(getColumnByField(field) == null){
                    SELECT("count(" + primaryField + ")");
                }else {
                    SELECT("count(" + field + ")");
                }
            }else {
                SELECT("count(" + primaryField + ")");
            }
            parseFilters(this, filters);
        }}.toString();
        log.info("entitySql.countBy.sql------> " + sql);
        return rename(sql, getFieldsAndCols());
    }

    /**
     * 分页查询
     * @param req 查询封装对象
     * @param pageIndex 当前在页码
     * @param pageSize 每页大小
     * @return sql
     */
    public String pageSeek(QueryReq req, int pageIndex, int pageSize){
        List<QueryFilter> filters = req.search;
        int offset = (pageIndex - 1) * pageSize;
        String sql = new SQL() {{
            FROM(getTableName());
            if (req.selectFields == null || req.selectFields.equals("")) {
                SELECT(getColumns());
            } else {
                SELECT(req.selectFields);
            }
            parseFilters(this, filters.toArray(new QueryFilter[]{}));
            if (req.sort != null) {
                ORDER_BY(req.sort);
            }
        }}.toString() +  " LIMIT " + pageSize + " OFFSET " + offset;
        log.info("entitySql.pageSeek.sql----------> " + sql);
        return rename(sql, getFieldsAndCols());
    }

    /**
     * 根据指定的若干条件查询若干字段查询
     * @param req 查询封装对象
     * @return sql
     */
    public String seek(QueryReq req) {
        List<QueryFilter> filters = req.search;
        String sql = new SQL() {{
            FROM(getTableName());
            if (req.selectFields == null || req.selectFields.equals("")) {
                SELECT(getColumns());
            } else {
                SELECT(req.selectFields);
            }
            parseFilters(this, filters.toArray(new QueryFilter[]{}));
            if (req.sort != null) {
                ORDER_BY(req.sort);
            }
        }}.toString();
        sql = rename(sql, fieldsAndCols);
        log.info("entitySql.seek.sql-----> " + sql);
        return sql;
    }

    private void parseFilters(SQL sql, QueryFilter ... queryFilters){
        sql.WHERE("1 = 1");
        for(QueryFilter filter: queryFilters){
            if(filter.getLogic().equals(QueryFilter.LOGIC_AND)){
                sql.AND();
                sql.WHERE(filter.toSqlPart());
            }else {
                sql.OR();
                sql.WHERE(filter.toSqlPart());
            }
        }
    }

    public String findAll(){
        String sql =  new SQL(){{
            SELECT(columns);
            FROM(tableName);
        }}.toString();
        log.info("entitySql.findAll.sql----------> " + sql);
        return sql;
    }


    //通过字段名获取数据列名
    protected String getColumnByField(String fieldName){
        return fieldsAndCols.get(fieldName);
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
