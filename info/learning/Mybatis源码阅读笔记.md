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
    public final class MappedStatement {

    	private String resource; //来源，一般为文件名或者是注解的类名
	    private Configuration configuration; //Mybatis的全局唯一Configuration
	    private String id; //标志符，可以用于缓存
	    private Integer fetchSize; //每次需要查询的行数（可选）
	    private Integer timeout;//超时时间
	    private StatementType statementType;//语句类型，决定最后将使用Statement, PreparedStatement还是CallableStatement进行查询
	    private ResultSetType resultSetType;//结果集的读取类型，与java.sql.ResultSet中的类型对应。
	    private SqlSource sqlSource;//Mybatis中的sqlSource，保存了初次解析的结果
	    private Cache cache;//缓存空间
	    private ParameterMap parameterMap;//保存了方法参数与sql语句中的参数对应关系
	    private List<ResultMap> resultMaps;//可选，定义结果集与Java类型中的字段映射关系
	    private boolean flushCacheRequired;//是否立即写入
	    private boolean useCache;//是否使用缓存
	    private boolean resultOrdered;//可选，默认为false
	    private SqlCommandType sqlCommandType;//Sql执行类型（增、删、改、查）
	    private KeyGenerator keyGenerator;//可选，键生成器
	    private String[] keyProperties;//可选，作为键的属性
	    private String[] keyColumns;//可选，键的列
	    private boolean hasNestedResultMaps;//是否有嵌套的映射关系
	    private String databaseId;//数据库的id
	    private Log statementLog;//logger
	    private LanguageDriver lang;//解析器
	    private String[] resultSets;//可选，数据集的名称
    }

# Executor 执行器

#  缓存

## 一级缓存，默认开启的

> 一级缓存作用域是SqlSession范围内的，在一个SqlSession生命周期内，同样的查询只会执行一次查询，       
> session提交、回滚，都会清空Cache

    public abstract class BaseExecutor implements Executor {
      private static final Log log = LogFactory.getLog(BaseExecutor.class);

      protected Transaction transaction;
      protected Executor wrapper;

      protected ConcurrentLinkedQueue<DeferredLoad> deferredLoads;
      
      //SqlSession范围的缓存
      protected PerpetualCache localCache;
      
      //SqlSession范围的参数的缓存
      protected PerpetualCache localOutputParameterCache;
      protected Configuration configuration;

     ......
    }


## 二级缓存，可配置是否开启

> **Mapper范围：**二级缓存对象是来自MappedStatement实例的缓存Cache，这个缓存是在注册Mapper接口类时创建的，以mapper的命名空间作为id，所以说缓存是mapper范围内的;  
> **序列化：** 二级缓存需要查询结果映射的pojo对象实现java.io.Serializable接口实现序列化和反序列化操作  
> **局限性：**如果跨Mapper的话，会有脏读，多表操作更不能使用，不建议使用二级缓存


# 插件
使用jdk代理包装SqlSession的四大对象（Executor、StatementHandler、ParameterHandler、ResultHandler），在它们执行指定（在Plugin实现类上使用@Intercepts注解指定）方法时，会被拦截，我们可以在此时修改原有的运行逻辑