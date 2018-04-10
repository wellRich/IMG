package com.digital.dao.sqlMapper;

import com.digital.util.Common;
import com.digital.entity.PreviewDataInfo;
import org.apache.ibatis.annotations.Update;
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
    public String findFamilyZoning(String levelCode, String columns){
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT(columns);
            WHERE(getColumnByField("levelCode") + " LIKE '" + levelCode.trim() + "%'");
            ORDER_BY(getColumnByField("levelCode") + " DESC");
        }}.toString();
        log.info("findFamilyZoning.sql -------------->" + sql);
        return sql;
    }

    /**
     * 根据区划代码查找同一父级下的同级区划
     * @param zoningCode
     * @return
     */
    public String findBrothersByCode(String zoningCode){
        String superZoningCode = Common.getSuperiorZoningCode(zoningCode);
        String assignCode = Common.getAssigningCode(zoningCode);

        String sql = new SQL(){{
            FROM(getTableName());
            SELECT(getColumns());
            WHERE(getColumnByField("superiorZoningCode") + "=" + superZoningCode );
            AND();
            WHERE(getColumnByField("assigningCode") + "=" + assignCode);
            AND();
            WHERE(getColumnByField("zoningCode") + "!=" + zoningCode);
        }}.toString();
        log.info("findBrothersByCode.sql---------> " + sql);
        return sql;
    }

    /**
     * 查找同一父级区划下的指定名称的区划数据
     * @param zoningCode 区划代码
     * @param zoningName 区划名称
     * @return sql
     */
    public String findBrothersByCodeAndName(String zoningCode, String zoningName){

        //取得上级区划的级别代码
        String superAssignCode = Common.getSuperAssignCode(zoningCode);
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT("COUNT(" + getColumnByField("divisionName") + ")");
            WHERE(getColumnByField("superiorZoningCode") + "='" + superAssignCode + "'");
            AND();
            WHERE(getColumnByField("divisionName") + "='" + zoningName + "'");
        }}.toString();
        log.info("findBrothersByCodeAndName.sql---------> " + sql);
        return sql;
    }

    //初始化时使用
    //获取父系以及下一级区划预览数据
    public String findAllByZoningCode(String zoningCode) {

        int level = Integer.valueOf(Common.getAssigningCode(zoningCode));

        //去0后的区划代码
        String code = Common.removeZeroOfTailing(zoningCode);

        //父系区划代码
        List<String> codes = Common.getPaternalCodes(zoningCode);

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
                WHERE(getColumnByField("zoningCode") + " = '" + zoningCode.trim() + "'");
            }}.toString();
        }

        log.info("findAllByZoningCode.sql-----------> " + sql);
        return sql;
    }





    //获取下一级区划预览数据
    public String findSubordinateZoning(String zoningCode){
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT(getColumns());
            WHERE(getColumnByField("superiorZoningCode") + "=#{zoningCode}");
        }}.toString();
        log.info("findSubordinateZoning.sql--------> " + sql);
        return sql;
    }


    //根据区划代码查找有效的区划预览数据
    public String findValidOneByZoningCode(String zoningCode){
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT(getColumns());
            WHERE("XYBZ = 'Y' AND YXBZ = 'Y' AND XZQH_DM =" + zoningCode);
        }}.toString();
        log.info("findValidOneByZoningCode.sql-------------> " + sql);
        return sql;
    }


    /**
     * 修改全称
     * @param oldFullName 旧的全称
     * @param newFullName 新的全称
     * @param levelCode 级别代码
     * @return sql
     */
    public String updateFullName(String oldFullName, String newFullName, String levelCode){
        String sql = new SQL(){{
            UPDATE(getTableName());
            SET(getColumnByField("divisionFullName") +
                    " = REPLACE(" + getColumnByField("divisionFullName") + ", '" + oldFullName + "', '" + newFullName +"')" );
            WHERE(getColumnByField("levelCode") + " LIKE '" + levelCode + "%'");
        }}.toString();
        log.info("updateFullName.sql---------> " + sql);
        return sql;
    }
}
