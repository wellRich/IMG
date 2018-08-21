[https://www.cnblogs.com/luoxn28/p/6417892.html]("一篇写得不错的帖子")

 
# 整体架构
![](https://i.imgur.com/qN9DL1h.png)
****

## MyBatis的主要成员

* Configuration        MyBatis所有的配置信息都保存在Configuration对象之中，配置文件中的大部分配置都会存储到该类中
* SqlSession            作为MyBatis工作的主要顶层API，表示和数据库交互时的会话，完成必要数据库增删改查功能
* Executor               MyBatis执行器，是MyBatis 调度的核心，负责SQL语句的生成和查询缓存的维护
* StatementHandler 封装了JDBC Statement操作，负责对JDBC statement 的操作，如设置参数等
* ParameterHandler  负责对用户传递的参数转换成JDBC Statement 所对应的数据类型
* ResultSetHandler   负责将JDBC返回的ResultSet结果集对象转换成List类型的集合
* TypeHandler          负责java数据类型和jdbc数据类型(也可以说是数据表列类型)之间的映射和转换
* MappedStatement  MappedStatement维护一条<select|update|delete|insert>节点的封装
* SqlSource              负责根据用户传递的parameterObject，动态地生成SQL语句，将信息封装到BoundSql对象中，并返回
* BoundSql              表示动态生成的SQL语句以及相应的参数信息

> 以上主要成员在一次数据库操作中基本都会涉及，在SQL操作中重点需要关注的是SQL参数什么时候被设置和结果集怎么转换为JavaBean对象的，这两个过程正好对应StatementHandler和ResultSetHandler类中的处理逻辑。



#  SqlSource
##  定义： 
> Represents the content of a mapped statement read from an XML file or an annotation.
It creates the SQL that will be passed to the database out of the input parameter received from the user.  
> 映射语句，通过XML配置或者java注解配置
> 接收用户的输入创建SQL
	
    public interface SqlSource {

      BoundSql getBoundSql(Object parameterObject);

    }

##  实现
### StaticSqlSource 
> 基本实现，是其它SqlSource的基础实现

### DynamicSqlSource 
> 著名的动态sql，基于XML配置，具有参数


### RawSqlSource 
> 占位符sql，基于XML配置，比动态sql快


### ProviderSqlSource 
> 可以，不使用xml配置，由注解完成配置，使用一个java类的若干返回值为string的方法提供sql
    


### VelocitySqlSource 
> 不必关注，实验性质的


# SqlSession

# MapperStatemet

# Executor 执行器

#  缓存

## 一级缓存



## 二级缓存

