package com.digital.dao.sqlMapper;

import com.digital.entity.HistoricalDataInfo;
import com.digital.entity.PreviewDataInfo;
import com.digital.util.search.BaseDao;
import com.digital.util.search.QueryFilter;
import com.digital.util.search.QueryReq;

import java.util.Collection;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 17:15 2018/4/16
 * @See
 * @Since
 * @Deprecated
 */
public class HistoricalDataSql implements BaseDao<HistoricalDataInfo>{


    private static EntitySql entitySql = new EntitySql() {
        @Override
        public Class init() {
            return HistoricalDataInfo.class;
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
    public String pageSeek(QueryReq req, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public String countBy(String field, QueryFilter... filters) {
        return null;
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
