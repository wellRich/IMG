package com.digital.dao;

import com.digital.dao.sqlMapper.HistoricalDataSql;
import com.digital.entity.FormalTableInfo;
import com.digital.entity.HistoricalDataInfo;
import com.digital.util.search.QueryReq;
import org.apache.ibatis.annotations.*;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 17:14 2018/4/16
 * @See
 * @Since
 * @Deprecated
 */
@Mapper
public interface HistoricalDataMapper {

    /**
     * @description 插入历史数据
     * @method  insertIntoHistorical
     * @params HistoricalDataInfo 历史数据对象
     * @return 操作的条数
     * @exception
     */
    @InsertProvider(type = HistoricalDataSql.class,method = "insert")
    @ResultType(Integer.class)
    int insertIntoHistorical(HistoricalDataInfo historicalDataInfo);

    @SelectProvider(type = HistoricalDataSql.class,method = "seek")
    @ResultType(Integer.class)
    int findMaxVersion(QueryReq queryReq);
}
