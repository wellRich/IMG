package com.digital.dao.sqlMapper;

import com.digital.entity.ZCCGroup;
import com.digital.util.search.BaseDao;
import com.digital.util.search.QueryFilter;
import com.digital.util.search.QueryReq;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * 变更对照组的sqlProvider
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 2018/3/16
 */
public class ZCCGroupSql implements BaseDao<ZCCGroup> {

    /**
     * 获取变更组的最大编号
     * @param zoningCode 区划代码
     */
    public String getMaxSerialNumber(String zoningCode){
        log.info("getMaxSerialNumber.zoningCode---------> " + zoningCode);
        String sql = new SQL(){{
            String columnName = entitySql.getColumnByField("serialNumber");
            FROM(entitySql.getTableName());
            SELECT("MAX(" + columnName + ")");
            WHERE( columnName + " like '"+ zoningCode + "%'");
        }}.toString();
        log.info("getMaxSerialNumber.sql---------> " + sql);
        return sql;
    }

    public String updateMaxOrderNum(){
        String sql = new SQL(){{
            UPDATE("XZQH_MAX");
            SET(" TABLE_MAX=TABLE_MAX+1");
            WHERE(" TABLE_NAME =#{tableName}");
        }}.toString();
        log.info("updateMaxOrderNum.sql---------> " + sql);
        return sql;
    }

    public String getMaxOrderNum(){
        String sql = new SQL(){{
            FROM("XZQH_MAX");
            SELECT(" TABLE_MAX");
            WHERE(" TABLE_NAME =#{tableName}");
        }}.toString();
        log.info("getMaxOrderNum.sql---------> " + sql);
        return sql;
    }

    /**
     * 查找变更对照组，按编号倒序排列
     * @param ids 以“,”分隔的id
     * @return string
     */
    @Override
    public String findByIds(String ids){
        return entitySql.findByIds(ids) + " ORDER BY BH DESC";
    }

    private static final Logger log = LoggerFactory.getLogger(ZCCGroupSql.class);


    private static EntitySql entitySql = new EntitySql() {
        @Override
        public Class init() {
            return ZCCGroup.class;
        }
    };


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
        System.out.println("come in group");
        return entitySql.countBy(field, filters);
    }
}
