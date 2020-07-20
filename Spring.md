## Spring

---

> ### 概述
>
> 一站式框架——web层，服务层，持久层
> Web层： SpringMVC
> Service层： Bean管理、声明式事务
> DAO层： ORM模块、JDBC模块

### 一、Spring——IOC

> IOC：inversion of control，控制反转。作用是将对象的创建权反转给（交给）Spring，使用时只需调用即可。主要用作容器管理。

* **IOC核心思想**

  对接口和实现类之间解耦合。通过工厂包装接口的实现类。在工厂类中读取配置文件，并利用反射来获取到实现类的方法。这样一来，需要调用不同的实现类时，不需要修改源代码，只需修改配置文件即可。

<img src="https://s2.ax1x.com/2020/02/28/3DQwKf.png" alt="3DQwKf.png" style="zoom:90%;" />

* **工程搭建**
  
  * 创建web工程，maven工程创建时直接选择webapp模板进行创建
  * pom.xml添加spring framework的依赖
  * 在main下新建resource文件夹，并在project structure中的module中设为resource root
  * 在resource文件夹下创建applicationcontext.xml文件，为spring框架的配置文件，添加bean
* 编写测试方法
  
    ```java
    //xml中bean标签的配置
  <bean id="UserService" class="com.spring_test.UserDatabaseImpl"></bean>
  
    //这里UserService为接口，UserServiceImpl和UserDatabaseImpl是该接口的两个实现类
    public class SpringTest1 {
        /*传统方式*/
        @Test
        public void springTest1(){
            UserServiceImpl userService = new UserServiceImpl();
            userService.save();
            UserDatabaseImpl userDatabase = new UserDatabaseImpl();
            userDatabase.save();
      }
  
        /*Spring的IOC方式*/
        @Test
        public void springTest2(){
            //创建Spring的工厂
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserServiceImp userService = (UserServiceImp)applicationContext.getBean("UserService");//里面写bean的id
            userService.save();
        }
    }
  ```
  
*  **IOC和DI**

> IOC：控制反转；将类的创建权反转给Spring进行管理
> DI：Dependency injection，依赖注入。前提必须有IOC的环境，Spring管理这个类的时候将类的依赖的属性注入（设置）进来。

* **Spring的工厂类**

  老版本的工厂类BeanFactory：调用getBean的时候，才会生成类的实例。

  新版本的工程类ApplicationContext：加载配置文件的时候，就会将Spring管理的类都实例化

  <img src="https://s2.ax1x.com/2020/03/02/3RbDyR.png" alt="3RbDyR.png" style="zoom:90%;" />

* Spring的xml文件的配置

  > 在maven中添加了spring相关的依赖后，IDEA的创建xml子列表中会有spring config选项

  

  * bean标签的配置：

    * id，class已经提到

    * init-method：初始化方法，在Bean实例创建时调用

      destory-method：销毁方法，在Bean被销毁的时候执行的方法（Bean是单例创建，工厂关闭）

    * scope：bean的作用范围的配置
  
      ​	取值：singleton(默认模式)，单例模式，只允许创建一个对象

      ​			   prototype，多例模式，允许创建多个对象
  
  * bean属性的注入（DI）（***方式四最简便，最推荐***）
  
    * 方式一，以Set方式注入(在类中声明属性的set方法)
  
      ```xml
      <bean id="nameDao" class="com.spring_test.NameDaoImpl">
        <!--value只能设置普通数据类型-->
          <property name="name" value="zhangfei" />
    </bean>
      ```
  
    * 方式二，以构造函数方式注入(在类中声明有参构造函数)
  
      ```xml
    <bean id="userService" class="com.spring_test.UserServiceImpl">
      	<constructor-arg name="name" value="guanyu"/>
    </bean>
      ```
  
      * **注意**，上面的两种方法中的`value`都只能给普通数据类型赋值，若属性类型为对象，则用`ref`
  
      ```xml
      <bean id="nameDao2" class="com.spring_test.NameDao2">
        <!--若属性的类型为对象，那么不能用value，而是用ref-->
          <property name="nameDaoImpl" ref="nameDao"/>
    </bean>
      ```

      > 这边的`name`指类中声明的变量名，`ref`中的内容，是另一个bean的id。

      
  
    * 方式三：p名称空间注入
  
      在beans标签内添加第二行内容
    
    ```xml
      <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:p="http://www.springframework.org/schema/p"
    ```
  
      接下来可在bean标签内直接通过`p:name='guanyu'`或`p:nameDaoImpl-ref=nameDao`来赋值，如：
  
      ```xml
  <bean id="nameDao2" class="com.spring_test.NameDao2" p:nameDaoImpl-ref="nameDao"></bean>
      ```
  
    * ***方式四***：SpEl（Spring Expression Language），直接在value内用`#{}`来表示任何类型的属性，*可以是对象类型，可以是一个表达式，甚至可以是别的对象的属性或方法*。
    
      ```xml
      <bean id="nameDao2" class="com.spring_test.NameDao2" p:nameDaoImpl-ref="nameDao">
        <!--这里对象类型也不需要再使用ref-->
          <property name="nameDaoImpl" value="#{nameDao}"/>
</bean>
      ```
  
    * 集合类型的属性注入（要用到property下一级的标签）
  
      * 数组，List，Set，类型的注入
    
        ```xml
        <!-- 注入list集合 -->
        <property name="list">
            <list>
                <value>张三</value>
              	<value>李四</value>
            </list>
    </property>
        ```
  
      * map类型的注入
    
        ```xml
        <property name="map">
            <map>
                <entry key="aaa" value="111"/>
                <entry key="bbb" value="222"/>
            </map>
        </property>
        ```
  

### 二、SpringIOC的注解方式

* 创建工程，并创建`applicationContext.xml`配置文件

  * 与配置xml不同的方式，不在xml的beans标签下配置bean标签，而是用注解的方式

    * 首先在beans下开启Spring的组件扫描

    ```xml
    <context:component-scan base-package="com.spring_ioc_annotation"/>
    ```

  * 在类中添加注解

    其中Component的值即为bean中的id

    属性上的Value即为属性注入**这里注意的是如果是这种方式，set方法不是必须的，若有set则注解放在set方法上**

* IOC注解详解

  * `@Component`：组件

    * 修饰一个类，将这个类交给Spring管理
    * 三个衍生注解，**实际使用时根据需要选择这三个注解**
      * `@Contoroler`： web层
      * `@Service`： service层
      * `@Repository`：dao层

  * 属性注入的注解

    * 普通属性：`@Value`： 设置普通属性的值
    * 对象类型的属性：
      * `@Autowired` + `@Qualifierd`
      * `@Resource`： **这个注解不属于Spring框架，Maven要添加依赖javax.annotation**

  * Bean作用域与生命周期

    ```xml
    <bean id="nameDao" class="com.spring_test.NameDaoImpl" init-method="setup" destroy-method="destroy" scope="prototype" >
    ```

    * `@Scope`： bean的作用域，默认为singleton，修饰在类上面。（对应xml中为`scope`）
    * `@PostConstruct`： 修饰在初始化方法上。（对应xml中为`init-method`）
    * `@Predestroy`： 修饰在销毁方法上。（对应xml中为`destroy-method`）

* 综合例子如下

  ```java
  @Repository(value = "userRepository")
  @Scope(value = "singleton")
  public class UserRepositoryImpl implements UserRepository{
  
      @Autowired
      @Qualifier("userDao")
      //@Resource(name = "userDao")
      private UserDao userDao;
  
      @PostConstruct
      public void init(){
          System.out.println("userRepository被初始化了...");
      }
  
      @Override
      public void save() {
          System.out.println("UserRepository的save()方法被调用了...");
          userDao.save();
      }
  
      @PreDestroy
      public void destroy(){
          System.out.println("userRepository被销毁了...");
      }
  }
  ```

* XML和注解的比较
  * XML：适用任何场景，结构清晰，维护方便。
  * 注解：开发方便，但是若一个类不是自己提供的，无法对其进行注解修改，就无法使用。
* XML和注解整合开发
  
  * XML管理Bean，注解完成属性注入。



### 三、Spring——AOP

> **AOP概念**:  aspect oriented programming。面向切面编程，通过运行期间进行动态代理实现程序功能统一维护，能够对业务逻辑的各个部分进行隔离。
> **适用场景：**权限校验、日志记录、性能监控、事务管理。

![3qkwnO.png](https://s2.ax1x.com/2020/03/06/3qkwnO.png)

* **底层实现**：动态代理（方式会根据判断自动切换）（即通过一个代理类，能够对不同的类进行代理）
  
  * JDK动态代理：只能对实现了接口的类产生代理
  * Cglib动态代理：对没有实现接口的类产生代理
  
* **Spring中AspectJ的XML方式de AOP开发**

  * AOP的相关术语 

  ![3q5QfI.png](https://s2.ax1x.com/2020/03/06/3q5QfI.png)

  

  * **入门AOP流程**

    * 创建maven工程，添加spring依赖，创建接口和实现类

    * 配置applicationContext.xml，添加实现类的bean

    * 创建Aspect类，创建Advice方法，并在xml中添加Aspect类的bean

      * 配置AOP(其中pointcut的expression表达式如何写后面会提到)  

      ```xml
      <!--配置AOP-->
      <aop:config proxy-target-class="true">
          <!--配置切入点，哪些类的哪些方法需要被增强-->
          <aop:pointcut id="pointcut1_save" expression="execution(* com.spring_aop_xml.ProductDaoImpl.save(..))"/>
          <!--配置切面类来实现对上面的pointcut的增强(advice)-->
          <aop:aspect ref="myAspectXml"> <!--ref为切面类的id-->
              <!--在save方法调用前后分别进行校验和日志记录-->
              <aop:before method="checkPri" pointcut-ref="pointcut1_save"/>
              <aop:after method="logDocumented" pointcut-ref="pointcut1_save"/>
          </aop:aspect>
      </aop:config>
      
      ```

      > 注意`proxy-target-class="true"`，若没有加，在使用`@Resource注解时`会产生报错。
    
      
    
  * Spring结合junit编写测试类的简便写法（**仅通过注解来实现配置文件读取与getBean的步骤**）

      ```java
      @RunWith(SpringJUnit4ClassRunner.class)//固定写法
      @ContextConfiguration("classpath:applicationContext3.xml")//解析配置文件创建工厂
      public class SpringAopTest1 {
          //配置文件解析后，直接调用所需的bean对象
          @Resource(name = "productDao")
          private ProductDaoImpl productDao;

          @Test
          public void springAopTest1(){
              productDao.save();
              productDao.delete();
          }
      }
      ```

  * **Spring的通知类型**

    * 前置通知`<aop:bfore/>`：在目标执行前进行操作，能够**获取切入点信息**。

        ```java
        public void checkPri(JoinPoint joinPoint){
        System.out.println("校验成功..." + joinPoint);
            }
        /*  那么接下来会在控制台打出
            校验成功...execution(void com.spring_aop_xml.ProductDaoImpl.save())
            商品被保存*/
        ```

    * 后置通知`<aop:after-returning/>`：在目标方法执行后的操作，**能获得目标方法的返回值**。

        ```java
        public void logDocumented(Object result){//且result的内容为目标方法的返回值
            System.out.println("日志记录成功...");
        }
        //那么xml中配置时，后面要加上returning，值必须为advice中的输入参数名称result
        ```

        ```xml
        <!--配置时，后面要加上returning，值必须为advice中的输入参数名称result-->
        <aop:after-returning method="searchSuccess" pointcut-ref="pointcut2_search" returning="result"/>
        ```

    

    * 环绕通知`<aop:around/>`:在目标方法执行前和执行后都进行操作

        ```java
        public Object aroundMethod(ProceedingJoinPoint joinPoint){
            Systom.out.print("环绕前增强...");
            Object obj = joinPoint.proceed();//执行目标方法
            Systom.out.print("环绕后增强");
            return obj;
        }
        ```

        

    * 异常抛出通知`<aop:afterthrowing/>`：在目标方法内出现异常时进行操作,**可以打印异常信息**

        ```java
        public void exceptionThrow(Throwable ex){
            System.out.println("异常抛出了..." + ex.getMessage());
        }
        ```

        ```xml
        <!--配置时，要在后面加上throwing参数，值为advice中的输入变量的名称ex-->
        <aop:after-throwing method="exceptionThrow" pointcut-ref="pointcut3_update" throwing="ex"/>
        ```

  * 最终通知`<aop:after/>`，无论目标方法代码是否有异常，都会执行。(相当于try catch中的finnally代码块)

* **AOP切入点表达式的写法**

  * **基于execution的函数**完成的

  * 语法:[访问修饰符(可省略)] 方法返回值 包名.类名.方法名(参数)

    ​		(public) void com.spring_aop_xml.ProductDaoImpl.save(..)


* **SpringAOP的注解开发**
  
  * 创建工程，创建目标类，创建切面类并编写advice
  
  * 创建配置文件，添加目标类和切面类的beans
  
  * 在配置文件中开启注解的AOP开发
  
    ```xml
    <aop:aspectj-autoproxy  proxy-target-class="true"/>
    ```
  
    > 同xml类型的配置，不加`proxy-target-class="true"`的话，在使用`@Resource`注解时会报错
  
  * 在切面类上使用注解
  
  * 注解详解
  
    * `@Aspect`：写在切面类的上方，声明用来配置AOP
    * 写在切面类的方法的上方，声明advice类型
      * `@Before`：前置通知
      * `@AfterReturning`：后置通知
      * `@Around`：环绕通知
      * `@After`：最终通知

### 四、Spring的事务管理

> **事务**：逻辑上的一组操作，组成这个操作的各个单元，要么全部成功，要么全部失败
>
> **事物的特性**：
>
> * 原子性：事务不可分割
> * 一致性：事务执行前后数据完整性保持一致
> * 隔离性：一个事务的执行不应该收到其他事务的干扰
> * 持久性：一旦事务结束，数据就持久化到数据库

* **Spring事务管理的API**（主要包括三个类）

  * `PlatformTransactionManager`：平台事务管理器，接口，是Spring用来管理事务的真正对象
    * `DataSourceTransactionManager`：底层用JDBC管理事务
    * `HibernateTransactionManager`：底层用Hibernate管理事务
  * `TransactionDefinition`：事务定义信息，用于定义事务的相关信息，如隔离级别，超时信息，传播行为，是否只读
  * `TransactionStatus`：事务的状态，用来记录事务管理过程中是无状态的对象
  * 关系：***平台事务管理器***根据事务***定义信息***进行事务的管理，在管理过程中会产生各种状态，将这种状态的信息记录到***事务状态的对象***中

* **Spring事务的传播行为**(共3个类别):用来解决业务层复杂的方法相互调用的问题

  <img src="https://s1.ax1x.com/2020/04/21/J8MPZF.png" alt="J8MPZF.png" style="zoom: 50%;" />

  * 保证多个操作在同一个事务中
    * `PROPAGATION_REQUIRED`: 默认值，如果x中有事务，使用x中的事务，如果x没有，y里创建一个新的事务，将操作(cd)包含进来
  * 保证多个操作不在同一个事务中
    * `PROPAGATION_REQUIRES_NEW`:如果x中有事务，将x的事务挂起，创建新事务，只包含自身操作(cd)，如果x中没有事务，创建一个新事务，包含自身操作(cd)
  * 嵌套事务
    * `PROPAGATION_NESTED`:嵌套事务，如果x中有事务，按照x的事务执行，执行完成后，设置一个保存点，执行y中的操作，如果没有异常，执行通过，如果有异常，可以选择回滚到最初始位置，也可以回滚到保存点。
  
* **Spring事务管理**

  * **编程式管理**

    * 在spring配置文件中配置平台事务管理器

      ```xml
          <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
              <property name="dataSource" ref="dataSource"/>
          </bean>
      ```

      

    * Spring提供了事务管理的模板，接下来配置事务管理的模板类

    * 注入事务管理的模板，将事务执行的代码放到模板

  * **声明式事务管理：XML方式**

    > 声明式事务管理用的是AOP实现

    * 配置平台事务管理器

    * 配置事务的增强（这里切面类不用自己编写，Spring有事务的模板）

      <img src="https://s1.ax1x.com/2020/04/21/J82UXt.png" alt="J82UXt.png" style="zoom: 67%;" />

    * AOP配置，将增强应用到目标类

  * **声明式事务管理：注解方式**

    * 配置平台事务管理器

    * 开启注解事务

      ```xml
      <tx:annotation-driven transaction-manager="transactionManager"
      ```

    * 在业务层上添加注解`@Transactional`






* 见到的问题，

  * aop配置中没有加`proxy-target-class="true"`，报错

    ```
    Injection of resource dependencies failed; nested exception is org.springframework.beans.factory.BeanNotOfRequiredTypeException: Bean named 'productDao' must be of type [com.spring_aop_xml.ProductDaoImpl], but was actually of type [com.sun.proxy.$Proxy13]
    ```

    

  * init-method方法在工厂生成时就会被调用
  
  
  
  