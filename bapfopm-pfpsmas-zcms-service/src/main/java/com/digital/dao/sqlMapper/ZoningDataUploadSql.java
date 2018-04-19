package com.digital.dao.sqlMapper;

import com.alibaba.druid.sql.visitor.functions.If;
import com.digital.entity.province.FocusChangeFileInfo;
import com.digital.util.StringUtil;
import org.apache.ibatis.javassist.runtime.Desc;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Description: TODO 上传文件的sql   方法名 与相应的mapper  方法名相同
 * @Author: zhanghpj
 * @Version 1.0, 17:38 2018/3/2
 * @See
 * @Since
 * @Deprecated
 */
public class ZoningDataUploadSql {

    /**
     * @description 根据文件序号查询zip文件基本信息
     * @method  queryFocusChangeFileInfo
     * @params [fileSquence：文件序号]
     * @return java.lang.String
     * @exception
     */

    public String queryFocusChangeFileInfo(Integer fileSquence){

        return new SQL(){
            {
                SELECT("ZIPXH","XZQH_DM","RQ","WJM","JZBGZT_DM","LRSJ","WJLJ","LRR_DM","LRJG_DM");
                FROM("xzqh_jzbgzip");
                if (fileSquence!=0){
                    WHERE("ZIPXH="+fileSquence);
                }
            }
        }.toString();
    }


    /**
     * @description 根据6位区划代码、日期  查询文件信息
     * @method  queryFocusChangeFileInfo
     * @params [zoningCode：6位区划代码, date:日期]
     * @return java.lang.String
     * @exception
     */
    public String queryFocusChangeFileInfoByCode(String zoningCode,String date){

        return new SQL(){
            {
                SELECT("ZIPXH","XZQH_DM","RQ","WJM","JZBGZT_DM","LRSJ","WJLJ","LRR_DM","LRJG_DM");
                FROM("xzqh_jzbgzip");
                if (!"000000".equals(zoningCode)){
                    WHERE("XZQH_DM="+zoningCode);
                }
                if (!StringUtil.isEmpty(date)){
                    AND();
                    WHERE("RQ="+date);
                }

                ORDER_BY("LRSJ desc");


            }
        }.toString();
    }

    /**
     * @description 根据文件序号动态修改文件信息表中的状态、备注
     * @method  updateFocusChangeInfo
     * @params [fileSquence：文件序号, statusCode：状态吗, note：备注]
     * @return java.lang.String
     * @exception
     */
    public String updateFocusChangeInfo(Integer fileSquence,String statusCode,String note){

        return new SQL(){
            {
                UPDATE("xzqh_jzbgzip");
                if (!StringUtil.isEmpty(statusCode)) {
                    SET("JZBGZT_DM=" + statusCode);
                }
                if (!StringUtil.isEmpty(note)) {
                    SET("BZ= '" + note+"'");
                }
                WHERE("ZIPXH="+fileSquence);
            }
        }.toString();
    }

}
