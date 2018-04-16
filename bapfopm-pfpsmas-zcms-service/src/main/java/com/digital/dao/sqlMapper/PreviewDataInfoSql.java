package com.digital.dao.sqlMapper;

import com.digital.util.Common;
import com.digital.entity.PreviewDataInfo;
import com.digital.util.search.BaseDao;
import com.digital.util.search.QueryReq;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 2018/3/21
 */
public class PreviewDataInfoSql implements BaseDao<PreviewDataInfo> {

    /**
     * 根据级别代码查找自身及子孙区划数据
     * @param levelCode 区划级别代码
     * @return String
     */
    public String findFamilyZoning(String levelCode, String columns){
        String sql = new SQL(){{
            FROM(entitySql.getTableName());
            SELECT(columns);
            WHERE(entitySql.getColumnByField("levelCode") + " LIKE '" + levelCode.trim() + "%'");
            ORDER_BY(entitySql.getColumnByField("levelCode") + " DESC");
        }}.toString();
        log.info("findFamilyZoning.sql -------------->" + sql);
        return sql;
    }

    /**
     * 根据区划代码查找同一父级下的同级区划
     * @param zoningCode 区划代码
     * @return string
     */
    public String findBrothersByCode(String zoningCode){
        String superZoningCode = Common.getSuperiorZoningCode(zoningCode);
        String assignCode = Common.getAssigningCode(zoningCode);

        String sql = new SQL(){{
            FROM(entitySql.getTableName());
            SELECT(entitySql.getColumns());
            WHERE(entitySql.getColumnByField("superiorZoningCode") + "=" + superZoningCode );
            AND();
            WHERE(entitySql.getColumnByField("assigningCode") + "=" + assignCode);
            AND();
            WHERE(entitySql.getColumnByField("zoningCode") + "!=" + zoningCode);
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
            FROM(entitySql.getTableName());
            SELECT("COUNT(" + entitySql.getColumnByField("divisionName") + ")");
            WHERE(entitySql.getColumnByField("superiorZoningCode") + "='" + superAssignCode + "'");
            AND();
            WHERE(entitySql.getColumnByField("divisionName") + "='" + zoningName + "'");
        }}.toString();
        log.info("findBrothersByCodeAndName.sql---------> " + sql);
        return sql;
    }

    //初始化时使用
    //获取父系以及下一级区划预览数据
    public String findAncestorsAndSubsByZoningCode(String zoningCode) {
        int assigningCode = Integer.valueOf(Common.getAssigningCode(zoningCode));

        //去0后的区划代码
        String code = Common.removeZeroOfTailing(zoningCode);

        //祖系区划代码
        List<String> ancestralCodes = Common.getPaternalCodes(zoningCode);
        String sql;

        //组以上级别的的区划
        StringBuilder tempCode = new StringBuilder();
        int size = ancestralCodes.size();
        for (int i = 0; i < size; i++) {
            tempCode.append("'");
            tempCode.append(ancestralCodes.get(i));
            tempCode.append("'");
            tempCode.append(i == size - 1 ? "" : ",");
        }
        log.info("findAncestorsAndSubsByZoningCode.tempCode----> " + tempCode);
        sql = new SQL() {{
            FROM(entitySql.getTableName());
            SELECT(entitySql.getColumns());
            if(assigningCode < Common.GROUP_LEVEL){
                WHERE(entitySql.getColumnByField("zoningCode") + " LIKE '" + code + "%' AND JCDM = " + (assigningCode + 1));
                OR();
                WHERE(entitySql.getColumnByField("zoningCode") + " IN ( " + tempCode.toString() + ")");
            }else {
                WHERE(entitySql.getColumnByField("zoningCode") + " IN ( " + tempCode.toString() + ")");
            }
        }}.toString();
        log.info("findAncestorsAndSubsByZoningCode.sql-----------> " + sql);
        return sql;
    }



    //获取下一级区划预览数据
    public String findSubordinateZoning(String zoningCode){
        String sql = new SQL(){{
            FROM(entitySql.getTableName());
            SELECT(entitySql.getColumns());
            WHERE(entitySql.getColumnByField("superiorZoningCode") + "=#{zoningCode}");
        }}.toString();
        log.info("findSubordinateZoning.sql--------> " + sql);
        return sql;
    }


    //根据区划代码查找有效的区划预览数据
    public String findValidOneByZoningCode(String zoningCode){
        String sql = new SQL(){{
            FROM(entitySql.getTableName());
            SELECT(entitySql.getColumns());
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
            UPDATE(entitySql.getTableName());
            SET(entitySql.getColumnByField("divisionFullName") +
                    " = REPLACE(" + entitySql.getColumnByField("divisionFullName") + ", '" + oldFullName + "', '" + newFullName +"')" );
            WHERE(entitySql.getColumnByField("levelCode") + " LIKE '" + levelCode + "%'");
        }}.toString();
        log.info("updateFullName.sql---------> " + sql);
        return sql;
    }


    private static final Logger log = LoggerFactory.getLogger(PreviewDataInfoSql.class);


    private static EntitySql entitySql = new EntitySql() {
        @Override
        public Class init() {
            return PreviewDataInfo.class;
        }
    };



    @Override
    public String findByIds(String ids) {
        return entitySql.findByIds(ids);
    }

    @Override
    public String insert(Object o) {
        return entitySql.insert(o);
    }

    @Override
    public String update(Object o) {
        return entitySql.update(o);
    }

    @Override
    public String delete(Object o) {
        return entitySql.delete(o);
    }

    @Override
    public String findAll() {
        return entitySql.findAll();
    }

    @Override
    public String seek(QueryReq req) {
        return entitySql.seek(req);
    }

    @Override
    public String get(Object o) {
        return entitySql.get(o);
    }

    @Override
    public String batchDelete(Collection<?> keys) {
        return entitySql.batchDelete(keys);
    }





    /**
     * @description 用zoningCode查询预览数据 TODO 最后需要重构这个方法
     * @method  findPreviewDataInfoByZoningCode
     * @params String zoningCode
     * @return String sql
     * @exception
     */
    public String findPreviewDataInfoByZoningCode(String zoningCode){
        String sql = new SQL(){
            {
                SELECT("group_concat(UNIQUE_KEY)",
                        "XZQH_DM","XZQH_MC","XZQH_JC","XZQH_QC",
                        "JCDM","JBDM","XYBZ","YXBZ",
                        "DWLSGX_DM","SJ_XZQH_DM","YXQ_Q","YXQ_Z",
                        "XNJD_BZ","OLD_XZQH_DM","QX_JGDM","LRR_DM","LRSJ","XGR_DM","XGSJ","XZQHLX_DM");
                FROM("dm_xzqh_ylsj");
                WHERE("XZQH_DM ="+zoningCode);
                AND();
                WHERE("XYBZ ='Y' and YXBZ = 'Y'");
            }
        }.toString();
        log.info("findPreviewDataInfoByZoningCode.sql---------> " + sql);
        return sql;
    }
}
