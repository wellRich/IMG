package com.digital.dao.sqlMapper;

import com.digital.entity.FormalCodeReleaseInfo;
import com.digital.entity.FormalTableInfo;
import com.digital.util.StringUtil;
import com.digital.util.search.BaseDao;
import com.digital.util.search.QueryFilter;
import com.digital.util.search.QueryReq;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 18:33 2018/4/23
 * @See
 * @Since
 * @Deprecated
 */
public class FormalCodeReleaseSql implements BaseDao<FormalCodeReleaseInfo> {
    private static final Logger logger = LoggerFactory.getLogger(FormalCodeReleaseSql.class);


    public String selectFormalReleaseByExportNum(String exportDate,String deadline){
        String sql = new SQL(){
            {
                SELECT("WJM","WJLJ","WJDX","DCSJ");
                FROM("xt_xzqhfb");
                if (!StringUtil.isEmpty(exportDate)){
                    WHERE("DCSJ>='"+exportDate+"'");
                }
                if (!StringUtil.isEmpty(exportDate)&&!StringUtil.isEmpty(deadline)){
                    AND();
                }
                if (!StringUtil.isEmpty(deadline)){
                    WHERE("DCSJ <='"+deadline+"'");
                }
                ORDER_BY("DCSJ desc");

            }
        }.toString();
        logger.info("selectFormalReleaseByExportNum------>"+sql);
        return sql;
    }

    private static EntitySql entitySql = new EntitySql() {
        @Override
        public Class init() {
            return FormalCodeReleaseInfo.class;
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
