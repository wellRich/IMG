package com.digital

import com.digital.dao.ZCCRequestMapper
import com.digital.dao.sqlMapper.EntitySql
import com.digital.entity.ZCCDetail
import com.digital.entity.ZCCGroup
import com.digital.entity.ZCCRequest
import com.digital.util.JSONHelper
import com.digital.util.search.QueryResp
import com.github.pagehelper.PageHelper
import org.junit.Test;
/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author guoyka
 * @version 2018/4/11
 */
class Test {

    @org.junit.Test
    void buildMapperXml(){
        XmlParser parser = new XmlParser()

        parser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)

        parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        Node node = parser.parse("src/main/resources/mybatis/mapper/entityMapper.xml");

        println("node-------> " + node)

    }

    ZCCRequestMapper zccRequestMapper;

    @org.junit.Test
    void toJS(){


        PageHelper.startPage(null).doCount()
    }
}
