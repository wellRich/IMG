package com.digital.dao.sqlMapper

import com.digital.entity.ZCCRequest
import com.digital.util.search.QueryFilter
import com.digital.util.search.QueryReq

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author guoyka
 * @version 1.0 , 2018/4/13
 */
class EntitySqlTest extends GroovyTestCase {
    void testSeek() {
        QueryReq req = new QueryReq("seq", "seq, name");
        EntitySql<ZCCRequest> entitySql = new ZCCRequestSql();
        entitySql.seek(req)
    }

    @org.junit.Test
    void testFilter(){
        QueryFilter filter = new QueryFilter("seq", "1000", QueryFilter.OPR_IS)
        println(filter)
    }
}
