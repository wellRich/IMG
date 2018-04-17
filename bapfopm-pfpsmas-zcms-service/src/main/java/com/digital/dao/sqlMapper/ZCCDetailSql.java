package com.digital.dao.sqlMapper;

import com.digital.entity.ZCCDetail;
import com.digital.util.search.BaseDao;
import com.digital.util.search.QueryFilter;
import com.digital.util.search.QueryReq;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author [作者]
 * @version 2018/3/19
 * @see [相关类/方法]
 * @since [产品/模块版本]
 
 */
public class ZCCDetailSql implements BaseDao<ZCCDetail> {
    
    public String deleteByGroupSeqs(List<Integer> seqList){
        String sql = new SQL(){{
            DELETE_FROM(entitySql.getTableName());
            WHERE("GROUPXH in (" + StringUtils.join(seqList, ",")+ ")" );
        }}.toString();
        log.info("deleteByGroupSeqs.sql--------> " + sql);
        return sql;
    }

    //查找若干对照组下的变更对照明细
    public String pageSeekByGroups(String seqStr, int offset, int limit){
        String sql = new SQL(){{
            FROM(entitySql.getTableName());
            SELECT(entitySql.getColumns());
            WHERE("GROUPXH in (" + seqStr + ")" );
        }}.toString() + " LIMIT " + limit + " OFFSET " + offset;;
        log.info("pageSeekByGroups.sql--------> " + sql);
        return sql;
    }

    //统计若干对照组下的变更对照明细数量
    public String countByGroups(String seqStr){
        String sql = new SQL(){{
            FROM(entitySql.getTableName());
            SELECT("COUNT(" + entitySql.getColumnByField(entitySql.getPrimaryField()) + ")");
            WHERE("GROUPXH in (" + seqStr + ")" );
        }}.toString();
        log.info("countByGroups.sql----------> " + sql);
        return sql;
    }
    
         

    private static final Logger log = LoggerFactory.getLogger(ZCCDetailSql.class);


    private static EntitySql entitySql = new EntitySql() {
        @Override
        public Class init() {
            return ZCCDetail.class;
        }
    };


    public String countBy(Collection<QueryFilter> filters){
        return entitySql.countBy(null, filters);
    }

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

}
