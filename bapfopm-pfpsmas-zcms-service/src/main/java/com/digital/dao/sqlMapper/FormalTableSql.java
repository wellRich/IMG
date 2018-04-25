package com.digital.dao.sqlMapper;

import java.util.Collection;
import java.util.Map;

import com.digital.util.search.QueryFilter;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.digital.entity.FormalTableInfo;
import com.digital.util.search.BaseDao;
import com.digital.util.search.QueryReq;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 9:35 2018/4/10
 * @See
 * @Since
 * @Deprecated
 */

public class FormalTableSql implements BaseDao<FormalTableInfo> {

    private static final Logger logger = LoggerFactory.getLogger(FormalTableSql.class);


    private static EntitySql entitySql = new EntitySql() {
        @Override
        public Class init() {
            return FormalTableInfo.class;
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

    /*
    * 根据行政区划代码查询key
    * */
    public String findZoningKey(String zoningCode){
        String sql = new SQL(){
            {
                SELECT("UNIQUE_KEY");
                FROM("dm_xzqh");
                WHERE("XZQH_DM="+zoningCode);
            }
        }.toString();
        logger.info("findZoningKey>>:"+sql);
        return sql;
    }



    //获取下一级区划预览数据
    public String findSubordinateZoning(String zoningCode){
        String sql = new SQL(){{
            FROM(entitySql.getTableName());
            SELECT(entitySql.getColumns());
            WHERE(entitySql.getColumnByField("superiorZoningCode") + "=#{zoningCode}");
            AND();
            WHERE(entitySql.getColumnByField("chooseSign") + "='Y' AND "
                    + entitySql.getColumnByField("usefulSign") + "= 'Y'");
        }}.toString();
        entitySql.log.info("findSubordinateZoning.sql--------> " + sql);
        return sql;
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
