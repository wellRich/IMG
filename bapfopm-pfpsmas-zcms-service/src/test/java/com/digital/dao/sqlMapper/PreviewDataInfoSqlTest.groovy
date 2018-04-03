package com.digital.dao.sqlMapper

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author guoyka
 * @version 2018/4/2
 * @see [ 相关类/方法]
 * @since [产品/模块版本]
 * @deprecated
 */
class PreviewDataInfoSqlTest extends GroovyTestCase {
    void testFindBrothersByCode() {
        new PreviewDataInfoSql().findBrothersByCode("370102000000000")
    }
}
