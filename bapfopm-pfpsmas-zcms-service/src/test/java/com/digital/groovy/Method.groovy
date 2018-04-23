package com.digital.groovy

import com.digital.dao.sqlMapper.ZCCDetailSql
import com.digital.util.search.Column
import com.digital.util.search.Table
import groovy.xml.StreamingMarkupBuilder
import org.junit.Test

import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * 一些脚本 代码
 * 〈功能详细描述〉
 */
class Method {
    String path = 'E:\\workspace\\root\\bapfopm-pfpsmas-zcms-service\\src\\main\\java\\com\\digital\\entity'

    String str = 'E:\\workspace\\root\\bapfopm-pfpsmas-zcms-service\\src\\main\\java\\'

    String xmlPath = 'E:\\workspace\\root\\bapfopm-pfpsmas-zcms-service\\src\\main\\resources\\mybatis\\mapper'

    @Test
    void buildMapperXml(){
        String packName = 'com.digital.entity'



        def entityNames = peekEntity(new File(path), [])
        entityNames.each {e -> println(e)}

        def comment="<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n" +
                "        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">"
        StreamingMarkupBuilder builder=new groovy.xml.StreamingMarkupBuilder()
        builder.encoding='utf-8'
        def doc = {
            mkp.xmlDeclaration()
            mkp.yieldUnescaped(comment)
            mapper(namespace: packName){
                for(String className: entityNames){
                    Class clazz = Class.forName(className)
                    if(clazz.isAnnotationPresent(Table.class)){
                        String primaryKey = clazz.getAnnotation(Table.class).primaryKey()
                        Field[] fields = clazz.getDeclaredFields()
                        resultMap(type: className, id: "allOf" + className.substring(className.lastIndexOf(".")+1)){
                            for (Field field: fields){
                                if(Modifier.isStatic(field.getModifiers())){
                                    println "${field.getName()}是静态属性，忽略忽略！"
                                }else {
                                    if(field.isAnnotationPresent(Column.class)){
                                        Column column = field.getAnnotation(Column.class)
                                        String colName = column.name()
                                        String fieldName = field.getName()
                                        if(fieldName == primaryKey){
                                            id(property: fieldName, column: colName, javaType:field.getType().getSimpleName())
                                        }else {
                                            result(property: fieldName, column: colName, javaType:field.getType().getSimpleName())
                                        }
                                    }else {
                                        println "${field.getName()}未添加注解com.digital.util.search.Column，请补充完整"
                                    }
                                }
                            }
                        }
                    }
                }

            }



        }

        Writer writer = new FileWriter(xmlPath + "\\Mapper.xml")
        writer << builder.bind(doc)

    }



    def peekEntity(File file, List<String> entityNames){
        if(file.isDirectory()){
            for(File e: file.listFiles()){
                if(e.isDirectory()){
                    peekEntity(e, entityNames)
                }else {
                    if(e.getName().endsWith(".java")){
                        entityNames.add(e.getPath().replace(str, "").replace("\\", ".").replace(".java", ""))
                    }
                }
            }
            return entityNames
        }else {
            if(file.getName().endsWith(".java")){
                entityNames.add(file.getPath().replace(str, "").replace("\\", "").replace(".java", ""))
                return entityNames
            }
        }
    }

    @Test
    void testRename(){
        println new ZCCDetailSql().rename("kk121kk121212kk", [kk: '$$'])
    }
}
