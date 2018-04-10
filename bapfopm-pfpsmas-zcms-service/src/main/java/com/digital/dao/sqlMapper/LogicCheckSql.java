package com.digital.dao.sqlMapper;

import com.digital.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Description: TODO 导入临时表的sql   方法名 与相应的mapper中方法名相同
 * @Author: zhanghpj
 * @Version 1.0, 10:31 2018/3/13
 * @See
 * @Since
 * @Deprecated
 */
public class LogicCheckSql {

    /**
     * @description 逻辑校验
     * @method  isSuperiorExist
     * @params [zoningCode 区划代码, divisionName 区划名称, superiorZoningCode 上级代码]
     * @return java.lang.String sql
     * @exception
     */
    public String logicCheck(String zoningCode, String divisionName, String superiorZoningCode) {
        return new SQL() {
            {
                SELECT("count(*)");
                FROM("dm_xzqh_ylsj");
                if (!StringUtil.isEmpty(zoningCode))
                WHERE("XZQH_DM=" + zoningCode);
                if (!StringUtil.isEmpty(divisionName)) {
                    AND();
                    WHERE("XZQH_MC=" + divisionName);
                }
                if (!StringUtil.isEmpty(superiorZoningCode)){
                    AND();
                    WHERE("SJ_XZQH_DM="+superiorZoningCode);
                }
            }
        }.toString();
    }

    /**
     * @description 查询 zipxh 文件中 xzqh_dm 表示的区划 下级的个数
     * @method  queryLevelExist
     * @params [fileNum：文件序号, zoningCode：区划代码]
     * @return java.lang.String 个数
     * @exception
     */
    public String queryLevelExist(Integer fileNum, String zoningCode){

        return new SQL(){
            {
                SELECT("count(*)");
                FROM("xzqh_jzbgsj_temp");
                WHERE("ZIPXH="+fileNum,"XZQH_DM'"+zoningCode+"%'");
            }
        }.toString();
    }



}
