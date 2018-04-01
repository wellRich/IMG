package com.digital.dao.sqlMapper;

import com.digital.util.Common;
import com.digital.entity.PreviewDataInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 2018/3/21
 */
public class PreviewDataInfoSql extends EntitySql<PreviewDataInfo> {
    @Override
    protected Class<PreviewDataInfo> init() {
        return PreviewDataInfo.class;
    }

    /**
     * 根据级别代码查找自身及子孙区划数据
     * @param levelCode
     * @return String
     */
    public String findChildrenZoning(String levelCode, String columns){
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT(columns);
            WHERE(getColumnByField("levelCode") + " LIKE '" + levelCode.trim() + "%'");
            ORDER_BY();
        }}.toString();
        log.info("findChildrenZoning.sql -------------->" + sql);
        return sql;
    }

    /**
     * 查找同一父级区划下的指定名称的区划数据
     * @param zoningCode 区划代码
     * @param zoningName 区划名称
     * @return sql
     */
    public String findBrothersByCode(String zoningCode, String zoningName){

        //取得上级区划的级别代码
        String superAssignCode = Common.getSuperAssignCode(zoningCode);
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT("COUNT(" + getColumnByField("divisionName") + ")");
            WHERE(getColumnByField("superiorZoningCode") + "='" + superAssignCode + "'");
            AND();
            WHERE(getColumnByField("divisionName") + "='" + zoningName + "'");
        }}.toString();
        log.info("findBrothersByCode.sql---------> " + sql);
        return sql;
    }

    //初始化时使用
    //获取父系以及下一级区划预览数据
    public String findAllByZoningCode(String zoingCode) {

        int level = Integer.valueOf(Common.getAssigningCode(zoingCode));

        //去0后的区划代码
        String code = Common.removeZeroOfTailing(zoingCode);

        //父系区划代码
        List<String> codes = Common.getPaternalCodes(zoingCode);

        log.info("findAllByZoningCode.level----> " + level);

        log.info("findAllByZoningCode.code----> " + code);
        String sql;
        if (level < 6) {
            StringBuilder tempCode = new StringBuilder();
            int size = codes.size();
            for (int i = 0; i < size; i++) {
                tempCode.append("'");
                tempCode.append(codes.get(i));
                tempCode.append("'");
                tempCode.append(i == size - 1 ? "" : ",");
            }
            sql = new SQL() {{
                FROM(getTableName());
                SELECT(getColumns());
                WHERE(getColumnByField("zoningCode") + " LIKE '" + code + "%' AND JCDM = " + (level + 1));
                OR();
                WHERE(getColumnByField("zoningCode") + " IN ( " + tempCode.toString() + ")");
            }}.toString();
        } else {
            sql = new SQL() {{
                FROM(getTableName());
                SELECT(getColumns());
                WHERE(getColumnByField("zoningCode") + " = '" + zoingCode.trim() + "'");
            }}.toString();
        }

        log.info("findAllByZoningCode.sql-----------> " + sql);
        return sql;
    }





    //获取下一级区划预览数据

}
