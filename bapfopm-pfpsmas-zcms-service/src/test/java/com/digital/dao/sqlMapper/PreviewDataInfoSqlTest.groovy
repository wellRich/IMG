package com.digital.dao.sqlMapper

import com.digital.entity.ZCCGroup
import com.digital.util.Common
import com.digital.util.JSONHelper
import org.junit.Test

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author guoyka
 * @version 2018/4/2
 */
class PreviewDataInfoSqlTest extends GroovyTestCase {
    void testFindBrothersByCode() {
        new PreviewDataInfoSql().findBrothersByCode("370102000000000")
    }

    @Test
    void testGroupJson(){
        ZCCGroup group = new ZCCGroup();
        group.creatorDeptCode = '1000'
        group.creatorCode = '9527'
        group.name = '天竺'
        group.requestSeq = '1'
        println JSONHelper.toJSON(group)

        println Common.getSuperiorZoningCode("370102006490000");
        println Common.getSuperiorZoningCode("370102006702000");
    }
}
