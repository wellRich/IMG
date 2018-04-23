package com.digital.dao.sqlMapper;

import com.digital.entity.ZCCRequest;
import com.digital.util.search.BaseDao;
import com.digital.util.search.QueryFilter;
import com.digital.util.search.QueryReq;
import org.apache.ibatis.jdbc.SQL;
import org.apache.tools.ant.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * 变更申请单的sqlProvider
 * @author guoyka
 * @version 2018/3/16
 */
public class ZCCRequestSql implements BaseDao<ZCCRequest> {

    /**
     * 统计查询
     * @param levelCode 查询条件，区划代码前六位
     * @return  sql
     */
    public String countByLevelCode(String levelCode){
        String sql = new SQL(){{
            FROM(entitySql.getTableName());
            SELECT("COUNT(" + entitySql.getColumnByField(entitySql.getPrimaryField()) + ")");
            WHERE(entitySql.getColumnByField("levelCode")  + " LIKE '"  + levelCode + "%'");
        }}.toString();
        log.info("countByZoningCode.sql----------> " + sql);
        return sql;
    }

    /**
     * 统计查询
     * @param statuses 若干申请单状态
     * @return  sql
     */
    public String countByStatuses(String  statuses){
        String sql = new SQL(){{
            FROM(entitySql.getTableName());
            SELECT("COUNT(" + entitySql.getColumnByField(entitySql.getPrimaryField()) + ")");
            WHERE(entitySql.getColumnByField("status") + " IN (" + statuses + ")");
        }}.toString();
        log.info("countByZoningCode.sql----------> " + sql);
        return sql;
    }


    /**
     * 统计指定级别、状态的申请单数量
     * @param levelCode 级别代码
     * @param statuses 若干状态
     * @return sql
     */
    public String countByZoningCodeAndStatus(String levelCode, String ... statuses){
        String sql = new SQL(){{
            FROM(entitySql.getTableName());
            SELECT("COUNT(" + entitySql.getColumnByField(entitySql.getPrimaryField()) + ")");
            if(statuses.length > 0){
                WHERE(entitySql.getColumnByField("status") + " IN (" + org.apache.commons.lang.StringUtils.join(statuses, ",") + ")");
            }else {
                WHERE(entitySql.getColumnByField("status") + " IN (" + org.apache.commons.lang.StringUtils.join(statuses, ",") + ")");
                AND();
                WHERE(entitySql.getColumnByField("levelCode") + " = '" + levelCode + "'");
            }
        }}.toString();
        log.info("countByZoningCode.sql----------> " + sql);
        return sql;
    }


    public String pageSeekByLevelCode(String levelCode, int offset, int pageSize){
        String sql = new SQL(){{
            FROM(entitySql.getTableName());
            SELECT(entitySql.getColumns());
            WHERE(entitySql.getColumnByField("levelCode")  + " LIKE '"  + levelCode + "%'");
            ORDER_BY(entitySql.getColumnByField("createDate") + " DESC");
        }}.toString() + " LIMIT " + pageSize + " OFFSET " + offset;
        log.info("pageSeekByLevelCode.sql----------> " + sql);
        return sql;

    }


    /**
     * 根据状态查找变更申请单
     * @param statuses 若干状态
     * @return sql
     */
    public String pageSeekByStatusesAndLevelCode(String  levelCode, int offset, int limit, String ... statuses){
        String sql = new SQL(){{
            FROM(entitySql.getTableName());

            SELECT(entitySql.getColumns());

            WHERE(" 1 = 1");

            if(levelCode != null){
                AND();
                WHERE(entitySql.getColumnByField("levelCode") + " LIKE '" + levelCode + "%'");
            }

            if(statuses.length > 0){
                AND();
                WHERE(entitySql.getColumnByField("status") + " IN (" + org.apache.commons.lang.StringUtils.join(statuses, ",") + ")");
            }
        }}.toString() +  " LIMIT " + limit + " OFFSET " + offset;
        log.info("pageSeekByStatusesAndLevelCode.sql--------------> " + sql);
        return sql;
    }

    /**
     * 根据级别代码与状态查询申请单
     * @param levelCode 级别代码
     * @param statuses 若干状态
     * @return sql
     */
    public String findByLevelCodeAndStatuses(String levelCode, String ... statuses){
        String sql = new SQL(){{
            FROM(entitySql.getTableName());
            SELECT(entitySql.getColumns());
            if(statuses.length > 0){
                WHERE(entitySql.getColumnByField("status") + " IN (" + org.apache.commons.lang.StringUtils.join(statuses, ",") + ")");
                AND();
                WHERE(entitySql.getColumnByField("levelCode") + " = '" + levelCode + "'");
            }else {
                WHERE(entitySql.getColumnByField("levelCode") + " = '" + levelCode + "'");
            }
        }}.toString();
        log.info("findOneByLevelCodeAndStatuses.sql----------> " + sql);
        return sql;
    }

    /**
     * 根据级别代码与状态分页查询申请单
     * @param levelCode 级别代码
     * @param offset 偏移
     * @param pageSize 每页大小
     * @param statuses 若干状态
     * @return sql
     */
    public String pageSeekByLevelCodeAndStatuses(String levelCode, int offset, int pageSize, String ... statuses){
        String sql = findByLevelCodeAndStatuses(levelCode, statuses) + " LIMIT " + pageSize + " OFFSET " + offset;
        log.info("findOneByLevelCodeAndStatuses.sql----------> " + sql);
        return sql;
    }

    private static final Logger log = LoggerFactory.getLogger(ZCCRequestSql.class);


    private static EntitySql entitySql = new EntitySql() {
        @Override
        public Class init() {
            return ZCCRequest.class;
        }
    };


    @Override
    public String findByIds(String ids) {
        return entitySql.findByIds(ids);
    }

    @Override
    public String insert(Object o) {
        return entitySql.insert(o);
    }

    @Override
    public String update(Object o) {
        return entitySql.update(o);
    }

    @Override
    public String delete(Object o) {
        return entitySql.delete(o);
    }

    @Override
    public String findAll() {
        return entitySql.findAll();
    }

    @Override
    public String seek(QueryReq req) {
        return entitySql.seek(req);
    }

    @Override
    public String get(Object o) {
        return entitySql.get(o);
    }

    @Override
    public String batchDelete(Collection<?> keys) {
        return entitySql.batchDelete(keys);
    }

    @Override
    public String pageSeek(QueryReq req, int pageIndex, int pageSize) {
        return entitySql.pageSeek(req, pageIndex, pageSize);
    }

    @Override
    public String countBy(String field, QueryFilter... filters) {
        return entitySql.countBy(field, filters);
    }
}
