package com.digital.dao;

import com.digital.dao.sqlMapper.LogicCheckSql;
import com.digital.entity.PreviewDataInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description: TODO 逻辑校验
 * @Author: zhanghpj
 * @Version 1.0, 9:46 2018/3/13
 * @See
 * @Since
 * @Deprecated
 */
@Mapper
public interface LogicCheckMapper {

    /*
     * 判断代码是否存在
     * */
    @SelectProvider(type = LogicCheckSql.class, method = "logicCheck")
    int logicCheck( String zoningCode,  String divisionName, String superiorZoningCode);


    /*
     * 查询区划代码表中的是否有该代码的下级
     * */
    @SelectProvider(type = LogicCheckSql.class,method = "queryLevelExist")
    int queryLevelExist(Integer fileNum, String zoningCode);




}
