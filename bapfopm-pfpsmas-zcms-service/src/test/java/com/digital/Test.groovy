package com.digital

import com.digital.dao.FormalTableMapper
import com.digital.dao.ZCCRequestMapper
import com.digital.dao.sqlMapper.EntitySql
import com.digital.dao.sqlMapper.FormalTableSql
import com.digital.dao.sqlMapper.MybatisHelper
import com.digital.dao.sqlMapper.PreviewDataInfoSql
import com.digital.dao.sqlMapper.ZCCRequestSql
import com.digital.entity.FormalTableInfo
import com.digital.entity.PreviewDataInfo
import com.digital.entity.ZCCDetail
import com.digital.entity.ZCCGroup
import com.digital.entity.ZCCRequest
import com.digital.util.Common
import com.digital.util.EntityHelper
import com.digital.util.JSONHelper
import com.digital.util.StringUtil
import com.digital.util.search.Column
import com.digital.util.search.QueryFilter
import com.digital.util.search.QueryReq
import com.digital.util.search.QueryResp
import com.digital.util.search.Table
import com.github.pagehelper.PageHelper
import groovy.sql.Sql
import groovy.xml.StreamingMarkupBuilder
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.session.SqlSession
import org.aspectj.weaver.loadtime.ClassPreProcessorAgentAdapter
import org.codehaus.groovy.runtime.IOGroovyMethods
import org.junit.Test

import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author guoyka
 * @version 2018/4/11
 */
class Test {

    static pr(def obj) {
        println obj
    }

    @org.junit.Test
    void testMap(){
        ZCCRequest  request = new ZCCRequest()
        Integer[] a = [100, 200];
        QueryFilter filter = new QueryFilter("id", a, QueryFilter.OPR_BETWEEN)
        pr "---------> " + filter.toSqlPart()
    }

    @org.junit.Test
    void buildMapperXml() {
        StreamingMarkupBuilder builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = "UTF-8"
        String packName = "com.digital.entity"
        File file = new File("E:\\workspace\\Server\\人口与计生服务管理应用系统\\区划代码管理分系统\\SERVICE\\bapfopm-pfpsmas-zcms-service\\src\\main\\java\\com\\digital\\entity")
        List<String> entityNames = []
        peekFile(file, entityNames)
        def d = ('<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">');
        def mapper = {
            mkp.xmlDeclaration()
            mkp.yieldUnescaped(d)
            mapper(namespace: packName) {
                for (String className : entityNames) {
                    try {
                        if (className != null || className != "") {
                            Class clazz = Class.forName(className)
                            if (clazz.isAnnotationPresent(Table.class)) {
                                Field[] fields = clazz.getDeclaredFields()
                                String primaryKey = clazz.getAnnotation(Table.class).primaryKey()
                                String type = (clazz.getPackage().name + "." + clazz.simpleName)

                                resultMap(id: "allOf" + className.substring(className.lastIndexOf(".") + 1), type: type) {
                                    for (int i = 0; i < fields.length; i++) {
                                        if (Modifier.isStatic(fields[i].getModifiers())) {
                                            pr(fields[i].getName() + "是静态属性，过滤过滤.")
                                        } else {
                                            if (fields[i].getName() == primaryKey) {
                                                id(property: (fields[i].name), javaType: fields[i].getType().simpleName, column: getCol(fields[i]))
                                            } else {
                                                result(property: (fields[i].name), javaType: fields[i].getType().simpleName, column: getCol(fields[i]))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        pr("${className} ---> " + ex.getMessage())
                    }
                }

            }
        }
        def writer = new FileWriter("E:\\workspace\\Server\\人口与计生服务管理应用系统\\区划代码管理分系统\\SERVICE\\bapfopm-pfpsmas-zcms-service\\src\\main\\resources\\mybatis\\mapper\\Mapper.xml")
        writer << builder.bind(mapper)
    }

    /**
     * 获取目录下的java类名及完整包名，如 com.digital.entity.ZCCRequest
     * @param file
     * @param fileNames
     * @return
     */
    def peekFile(File file, List<String> fileNames) {
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File e : file.listFiles()) {
                    if (e.isDirectory()) {
                        peekFile(e, fileNames)
                    } else {
                        if (e.getName().endsWith(".java")) {
                            fileNames.add(e.getPath()
                                    .replace("E:\\workspace\\Server\\人口与计生服务管理应用系统\\区划代码管理分系统\\SERVICE\\bapfopm-pfpsmas-zcms-service\\src\\main\\java\\", "")
                                    .replace("\\", ".").replace(".java", ""))
                        }
                    }
                }
                return fileNames
            } else {
                if (file.getName().endsWith(".java")) {
                    fileNames.add(file.getPath()
                            .replace("E:\\workspace\\Server\\人口与计生服务管理应用系统\\区划代码管理分系统\\SERVICE\\bapfopm-pfpsmas-zcms-service\\src\\main\\java\\", "")
                            .replace("\\", ".").replace(".java", ""))
                }
                return fileNames
            }
        } else {
            return new RuntimeException("目录或者文件不存在！");
        }
    }

    /**
     * 获取字段上的Column注解
     * @param field
     * @return
     */
    def getCol(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return field.getAnnotation(Column.class).name()
        } else {
            throw new RuntimeException(field.name + "未有添加注解com.digital.util.search.Column，请添加！")
        }
    }

    ZCCRequestMapper zccRequestMapper;

    @org.junit.Test
    void toJS() {
        EntitySql sql = new FormalTableSql<FormalTableInfo>()
        QueryReq req = new QueryReq( "uniqueKey", "uniqueKey,zoningCode,divisionName")
        req.addFilter(new QueryFilter("zoningCode", "370102%", QueryFilter.OPR_LIKE))
        sql.seek(req)
        //println sql.seek(req)
    }

    @org.junit.Test
    void testFindAllByZoningCode(){
//        String str = "12345";
//        println("----> " + str.substring(0, 3))
//        new PreviewDataInfoSql().findAncestorsAndSubsByZoningCode('371481001400401')
//
//
//        new PreviewDataInfoSql().findAncestorsAndSubsByZoningCode('370102000000000')
//
//
//        new PreviewDataInfoSql().findAncestorsAndSubsByZoningCode(Common.NATION_ZONING_CODE)

        println StringUtil.insertSingleQuotationMarks([1,23,4])
    }


    @org.junit.Test
    void testSeek() {
        Sql db = Sql.newInstance(
                url: "jdbc:mysql://10.1.2.42:3306/xzqh?useUnicode=true&characterEncoding=UTF-8&useSSL=false",
                user: "root",
                password: "root", driverClassName: "com.mysql.jdbc.Driver"
        )
        try {
            def code = '370102000000000'
            def assigningCode = 3
            def processedCodes = "'370102000000000', '370100000000000'"
            def req = new QueryReq()
            req.addFilter("zoningCode", code, QueryFilter.OPR_LIKE);
            req.addFilter("assigningCode", assigningCode + 1);
            println(req.addFilter("zoningCode", processedCodes, QueryFilter.OPR_IN, QueryFilter.LOGIC_OR))
            req.search.each { pr it }
            String str = new FormalTableSql().seek(req)
            ResultSet resultSet = null;

            db.query(str, {
                while (it.next()) {
                    pr it.getString("XZQH_MC");
                }
            })

            /*try {
                Runtime runtime = Runtime.getRuntime();
                String cmd = 'F:\\github_work\\从E盘的zcms项目目录复制代码文件到github目录.sh'
                Process ps = runtime.exec(cmd);
                ps.waitFor();

                BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                String result = sb.toString();
                System.out.println(result);
            }   catch (IOException e) {
                e.printStackTrace();
            }*/

        } finally {
            db.close()
        }
    }

    public static <T> void out(T... args) {
        for (T t : args) {
            System.out.println(t);
        }
    }

     public static  <T> List<T> find(String sql, T clazz){
         Sql db = Sql.newInstance(
                 url: "jdbc:mysql://10.1.2.42:3306/xzqh?useUnicode=true&characterEncoding=UTF-8&useSSL=false",
                 user: "root",
                 password: "root", driverClassName: "com.mysql.jdbc.Driver"
         )
         try {


             db.query(sql, {

             })


             }finally {
             db.close()
         }
    }

    @org.junit.Test
    void testAss(){
        def code = '370000000000000'
        println("l---------》 " + code.length())

        pr("re----> " + Common.removeZeroOfTailing(code))
    }
}
