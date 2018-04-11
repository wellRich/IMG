package com.digital.dao.sqlMapper;

import com.digital.entity.ZCCRequest;
import org.apache.ibatis.jdbc.SQL;
import org.apache.tools.ant.util.StringUtils;

import java.util.Map;

/**
 * @Description: TODO 变更申请单的sql   方法名 与相应的mapper中方法名相同
 * @author guoyka
 * @version 2018/3/16
 */
public class ZCCRequestSql extends EntitySql<ZCCRequest> {

    @Override
    protected Class<ZCCRequest> init() {
        return ZCCRequest.class;
    }

    /**
     * 统计查询
     * @param levelCode 查询条件，区划代码前六位
     * @return
     */
    public String countByZoningCode(String levelCode){
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT("count(*)");
            WHERE(getColumnByField("levelCode")  + " LIKE '"  + levelCode + "'");
        }}.toString();
        log.info("countByZoningCode.sql----------> " + sql);
        return sql;
    }



    public String pageSeekByZoningCode(String levelCode, int offset, int pageSize){
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT(getColumns());
            WHERE(getColumnByField("levelCode")  + " LIKE '"  + levelCode + "%'");
            ORDER_BY(getColumnByField("createDate") + " DESC");
        }}.toString() + " LIMIT " + pageSize + " OFFSET " + offset;
        log.info("pageSeekByZoningCode.sql----------> " + sql);
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
            FROM(getTableName());
            SELECT("count(*)");
            WHERE(getColumnByField("status") + " IN (" + org.apache.commons.lang.StringUtils.join(statuses, ",") + ")");
            AND();
            WHERE(getColumnByField("levelCode") + " = '" + levelCode + "'");
        }}.toString();
        log.info("countByZoningCode.sql----------> " + sql);
        return sql;
    }

    public String findByLevelCodeAndStatuses(String levelCode, String ... statuses){
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT(getColumns());
            WHERE(getColumnByField("status") + " IN (" + org.apache.commons.lang.StringUtils.join(statuses, ",") + ")");
            AND();
            WHERE(getColumnByField("levelCode") + " = '" + levelCode + "'");
        }}.toString();
        log.info("findOneByLevelCodeAndStatuses.sql----------> " + sql);
        return sql;
    }

    public String pageSeekByLevelCodeAndStatuses(String levelCode, int offset, int pageSize, String ... statuses){
        String sql = findByLevelCodeAndStatuses(levelCode, statuses) + " LIMIT " + pageSize + " OFFSET " + offset;
        log.info("findOneByLevelCodeAndStatuses.sql----------> " + sql);
        return sql;
    }
}
