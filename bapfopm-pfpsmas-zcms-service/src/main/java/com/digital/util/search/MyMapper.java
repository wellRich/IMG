package com.digital.util.search;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 2018/4/10
 */
public interface MyMapper<T> {

    @Results(id = "test", value = {
            @Result(property = "seq", column = "SQDXH", javaType = Integer.class),
            @Result(property = "name", column = "SQDMC", javaType = String.class),
            @Result(property = "status", column = "SQDZT_DM", javaType = String.class),
            @Result(property = "levelCode", column = "SBXZQH_DM", javaType = String.class),
            @Result(property = "creatorCode", column = "LRR_DM", javaType = String.class),
            @Result(property = "createDate", column = "LRSJ", javaType = String.class),
            @Result(property = "creatorDeptCode", column = "LRJG_DM", javaType = String.class),
            @Result(property = "updaterCode", column = "XGR_DM", javaType = String.class),
            @Result(property = "approvalOpinion", column = "SPYJ", javaType = String.class),
            @Result(property = "verifierCode", column = "SPR_DM", javaType = String.class),
            @Result(property = "notes", column = "BZ", javaType = String.class)
    })
    String test();
}
