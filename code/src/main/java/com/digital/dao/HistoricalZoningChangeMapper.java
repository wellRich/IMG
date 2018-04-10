package com.digital.dao;

import com.digital.dao.sqlMapper.HistoricalZoningChangeSql;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 2018/4/6
 * @see [相关类/方法]
 */
@Mapper
public interface HistoricalZoningChangeMapper {

    //插入历史数据
    @InsertProvider(type = HistoricalZoningChangeSql.class, method = "insert")
    @ResultType(Integer.class)
    Integer insert(Object info);


    //删除历史数据
    @DeleteProvider(type = HistoricalZoningChangeSql.class, method = "delete")
    Integer delete(Object key);


    @DeleteProvider(type = HistoricalZoningChangeSql.class, method = "batchDelete")
    Integer batchDelete(@Param("keys")Collection<?> keys);
}
