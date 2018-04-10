package com.digital.dao.sqlMapper;

import com.digital.entity.ZCCDetail;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author [作者]
 * @version 2018/3/19
 * @see [相关类/方法]
 * @since [产品/模块版本]
 
 */
public class ZCCDetailSql extends EntitySql<ZCCDetail>  {
    @Override
    protected Class<ZCCDetail> init() {
        return ZCCDetail.class;
    }


    public String deleteByGroupSeqs(List<Integer> seqList){
        String sql = new SQL(){{
            DELETE_FROM(getTableName());
            WHERE("GROUPXH in (" + StringUtils.join(seqList, ",")+ ")" );
        }}.toString();
        log.info("deleteByGroupSeqs.sql--------> " + sql);
        return sql;
    }

    //查找若干对照组下的变更对照明细
    public String pageSeekByGroups(String seqStr, int offset, int limit){
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT(getColumns());
            WHERE("GROUPXH in (" + seqStr + ")" );
        }}.toString() + " LIMIT " + limit + " OFFSET " + offset;;
        log.info("pageSeekByGroups.sql--------> " + sql);
        return sql;
    }

    //统计若干对照组下的变更对照明细数量
    public String countByGroups(String seqStr){
        String sql = new SQL(){{
            FROM(getTableName());
            SELECT("COUNT(*)");
            WHERE("GROUPXH in (" + seqStr + ")" );
        }}.toString();
        log.info("countByGroups.sql----------> " + sql);
        return sql;
    }

   /* public String findByApplicationNum(Integer applicationNum){
        String sql = new SQL(){
            {
              SELECT("M.MXBXH","M.GROUPXH","M.YSXZQH_DM","M.YSXZQH_MC","M.BGLX_DM","M.MBXZQH_DM","M.MBXZQH_MC","M.BZ","M.LRR_DM","M.LRJG_DM");
              FROM("XZQH_BGMXB M","XZQH_BGGROUP G");
              WHERE("M.GROUPXH=G.GROUPXH");
              AND();
              WHERE("G.SQDXH="+applicationNum);
            }
        }.toString();
        return sql;
    }*/


}
