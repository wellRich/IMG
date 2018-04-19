package com.digital.dao.sqlMapper;

import com.digital.entity.HistoricalZoningChange;
import com.digital.util.search.BaseDao;
import com.digital.util.search.QueryFilter;
import com.digital.util.search.QueryReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 2018/4/6
 * @see [相关类/方法]
 */
public class HistoricalZoningChangeSql implements BaseDao<HistoricalZoningChange> {

    private static final Logger logger = LoggerFactory.getLogger(HistoricalZoningChangeSql.class);


    private static EntitySql entitySql = new EntitySql() {
        @Override
        public Class init() {
            return HistoricalZoningChange.class;
        }
    };

    @Override
    public String insert(Object o) {
        return entitySql.insert(o);
    }

    @Override
    public String batchDelete(Collection<?> keys) {
        return entitySql.batchDelete(keys);
    }

    @Override
    public String delete(Object o) {
        return entitySql.delete(o);
    }

    @Override
    public String update(Object o) {
        return entitySql.update(o);
    }

    @Override
    public String get(Object o) {
        return entitySql.get(o);
    }

    @Override
    public String seek(QueryReq req) {
        return entitySql.seek(req);
    }

    @Override
    public String findByIds(String ids) {
        return entitySql.findByIds(ids);
    }

    @Override
    public String findAll() {
        return entitySql.findAll();
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
