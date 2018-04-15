package com.digital.dao.sqlMapper;

import com.digital.entity.ZCCGroup;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author [作者]
 * @version 2018/3/16
 * @see [相关类/方法]
 * @since [产品/模块版本]
 
 */
public class ZCCGroupSql extends EntitySql<ZCCGroup> {
    @Override
    public Class<ZCCGroup> init() {
        return ZCCGroup.class;
    }

    /**
     * 获取变更组的最大编号
     * @param zoningCode 区划代码
     */
    public String getMaxSerialNumber(String zoningCode){
        log.info("getMaxSerialNumber.zoningCode---------> " + zoningCode);
        String sql = new SQL(){{
            String columnName = getColumnByField("serialNumber");
            FROM(getTableName());
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
     * @return
     */
    public String findByIds(String ids){
        return super.findByIds(ids) + " ORDER BY BH DESC";
    }
}
