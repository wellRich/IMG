package com.digital.dao;

import com.digital.dao.sqlMapper.FormalCodeReleaseSql;
import com.digital.entity.FormalCodeReleaseInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description: TODO 区划发布
 * @Author: zhanghpj
 * @Version 1.0, 18:32 2018/4/23
 * @See
 * @Since
 * @Deprecated
 */
@Mapper
public interface FormalCodeReleaseMapper {

    static final String RESULT_MAP =  "com.digital.entity.allOfFormalCodeReleaseInfo";

    @InsertProvider(type = FormalCodeReleaseSql.class,method = "insert")
    @ResultType(Integer.class)
    int insertCodeRelease(Object formalCodeInfo);

    @SelectProvider(type = FormalCodeReleaseSql.class,method = "selectFormalReleaseByExportNum")
    @ResultMap(RESULT_MAP)
    List<FormalCodeReleaseInfo> selectFormalReleaseByExportNum(String exportDate,String deadline);


}
