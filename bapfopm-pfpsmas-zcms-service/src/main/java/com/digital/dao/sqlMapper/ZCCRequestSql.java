package com.digital.dao.sqlMapper;

import com.digital.entity.ZCCRequest;
import org.apache.ibatis.jdbc.SQL;

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
     * @param zoingCode 查询条件，区划代码前六位
     * @return
     */
    public String countByZoningCode(String zoingCode){
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT("count(*)");
            WHERE(getColumnByField("ownZoningCode")  + " LIKE '"  + zoingCode + "%'");
        }}.toString();
        log.info("countByZoningCode.sql----------> " + sql);
        return sql;
    }



    public String pageSeekByZoningCode(String levelCode, int offset, int pageSize){
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT(getColumns());
            WHERE(getColumnByField("ownZoningCode")  + " LIKE '"  + levelCode + "%'");
            ORDER_BY(getColumnByField("createDate") + " DESC");
        }}.toString() + " LIMIT " + pageSize + " OFFSET " + offset;
        log.info("pageSeekByZoningCode.sql----------> " + sql);
        return sql;

    }
}
