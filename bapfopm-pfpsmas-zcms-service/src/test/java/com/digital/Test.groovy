package com.digital

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
}
