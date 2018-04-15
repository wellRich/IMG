package com.digital.dao.sqlMapper;

import com.digital.entity.FormalTableInfo;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 9:35 2018/4/10
 * @See
 * @Since
 * @Deprecated
 */
public class FormalTableSql extends EntitySql<FormalTableInfo>{

    private static final Logger logger = LoggerFactory.getLogger(FormalTableSql.class);

    @Override
    public Class<FormalTableInfo> init() {
        return FormalTableInfo.class;
    }


    /*
    * 根据行政区划代码查询key
    * */
    public String findZoningKey(String zoningCode){
        String sql = new SQL(){
            {
                SELECT("UNIQUE_KEY");
                FROM("dm_xzqh");
                WHERE("XZQH_DM="+zoningCode);
            }
        }.toString();
        logger.info("findZoningKey>>:"+sql);
        return sql;
    }




    //获取下一级区划预览数据
    public String findSubordinateZoning(String zoningCode){
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT(getColumns());
            WHERE(getColumnByField("superiorZoningCode") + "=#{zoningCode}");
            AND();
            WHERE(getColumnByField("chooseSign") + "='Y' AND "
                    + getColumnByField("usefulSign") + "= 'Y'");
        }}.toString();
        log.info("findSubordinateZoning.sql--------> " + sql);
        return sql;
    }





    /*public String insetFormatTableData(FormalTableInfo tableInfo){
        String sql = new SQL(){
            {
                INSERT_INTO("dm_xzqh");
                VALUES("UNIQUE_KEY",tableInfo.getUniqueKey());
                VALUES("XZQH_DM",tableInfo.getZoningCode());
                VALUES("XZQH_MC",tableInfo.getDivisionName());
                VALUES("XZQH_JC",tableInfo.getDivisionAbbreviation());
                VALUES("XZQH_QC",tableInfo.getDivisionFullName());
                VALUES("JCDM",tableInfo.getAssigningCode());
                VALUES("JBDM",tableInfo.getLevelCode());
                VALUES("SJ_XZQH_DM",tableInfo.getSuperiorZoningCode());
                VALUES("XYBZ",tableInfo.getChooseSign());
                VALUES("YXBZ",tableInfo.getUsefulSign());
                VALUES("DWLSGX_DM",tableInfo.getSubordinateRelations());
                VALUES("YXQ_Q",tableInfo.getValidityStart());
                VALUES("YXQ_Z",tableInfo.getValidityStup());
                VALUES("XNJD_BZ",tableInfo.getVirtualNode());
                VALUES("OLD_XZQH_DM",tableInfo.getOldZoningCode());
                VALUES("QX_JGDM",tableInfo.getAccessCode());
                VALUES("LRR_DM",tableInfo.getEnterOneCode());
                VALUES("LRSJ",tableInfo.getCreateDate());
                VALUES("XGR_DM",tableInfo.getUpdaterCode());
                VALUES("XGSJ",tableInfo.getLastUpdate());
                VALUES("XZQHLX_DM",tableInfo.getType());
                VALUES("VERSION","'"+tableInfo.getVersion()+"'");
            }
        }.toString();

        logger.info("insetFormatTableData>>:"+sql);
        return sql;
    }*/



}
