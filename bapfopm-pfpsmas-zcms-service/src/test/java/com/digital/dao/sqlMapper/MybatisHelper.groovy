package com.digital.dao.sqlMapper

import org.apache.ibatis.io.Resources
import org.apache.ibatis.jdbc.ScriptRunner
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder

import java.sql.Connection

/**
 * 不启动服务，进行查询测试
 * 〈功能详细描述〉
 * @author guoyka
 * @version 1.0 , 2018/4/17
 */
class MybatisHelper {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            //创建SqlSessionFactory
            Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            println(sqlSessionFactory.properties)
            reader.close();
            //创建数据库
            SqlSession session = null;
            try {
                session = sqlSessionFactory.openSession();
                Connection conn = session.getConnection();
                ScriptRunner runner = new ScriptRunner(conn);
                runner.setLogWriter(null);
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Session
     *
     * @return
     */
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
