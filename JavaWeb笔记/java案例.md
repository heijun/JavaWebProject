# java案例

 RedisConfig配置文件

```java
@Configuration
public class RedisConfig {
    @Bean
    public StringRedisTemplate StringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        /**
         * description 开启redis事务（仅支持单机，不支持cluster）
         **/
        template.setEnableTransactionSupport(true);
        return template;
    }

    /**
     * description 配置事务管理器
     **/
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
```



RedisController控制层代码

```java
@RestController
@RequestMapping("redis")
public class RedisController {
    @Autowired
    RedisService2 redisService2;

    /**
     * description hello world！
     * return java.lang.String
     * author 郑晓龙
     * createTime 2019/12/12 16:36
     **/
    @GetMapping("hello")
    public String hello() {
        return "hello,SpringBoot!";
    }

    /**
     * description 不带事务set
     * return java.lang.String
     * author 郑晓龙
     * createTime 2019/12/12 16:36
     **/
    @GetMapping("put")
    public void put(String key, String value) {
        redisService2.put(key, value);
    }

    /**
     * description 带事务set
     * return java.lang.String
     * author 郑晓龙
     * createTime 2019/12/12 16:36
     **/
    @GetMapping("putWithTx")
    public void putWithTx(String key, String value) {
        redisService2.putWithTx(key, value);
    }

    /**
     * description 调用带事务方法不生效的情况
     * return java.lang.String
     * author 郑晓龙
     * createTime 2019/12/12 16:36
     **/
    @GetMapping("invokeWithPutTx")
    public void invokeWithPutTx(String key, String value) {
        redisService2.invokePutWithTx(key, value);
    }

    /**
     * description 调用带事务方法生效的情况
     * return java.lang.String
     * author 郑晓龙
     * createTime 2019/12/12 16:36
     **/
    @GetMapping("invokeWithPutTx2")
    public void invokeWithPutTx2(String key, String value) {
        redisService2.invokePutWithTx2(key, value);
    }
}
```

RedisService2代码

```java
@Service
public class RedisService2 {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisService2 redisService2;
    /**
     * description 不带事务写入
     **/
    public void put(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }
    /**
     * description 带事务写入
     **/
    @Transactional(rollbackFor = Throwable.class)
    public void putWithTx(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }
    /**
     * description 该方法演示内部调用带事务方法 事务不生效的情况
     **/
    public void invokePutWithTx(String key,String value){
        putWithTx(key,value);
    }
    /**
     * description 该方法演示内部调用带事务方法 事务生效的情况
     **/
    public void invokePutWithTx2(String key,String value){
        redisService2.putWithTx(key,value);
    }

}
```





### 类的初始化和对象的实例化

加载：将class文件字节码内容加载到内存中，并将这些静态数据换成方法取得运行时的数据结构，然后生成一个代表这个类的java.lang.Class对象。
链接：将Java类的二进制代码合并到JVM的运行状态之中的过程。
验证：确保加载的类信息符合JVM规范，没有安全方面的问题。
准备：正式为类变量分配内存并设置类变量默认初始值的阶段，这些内存都将在方法区中进行分配。
解析：虚拟机常量池内的符号引用（常量名）替换为直接引用（地址）的过程。
初始化：
执行类构造器<clinit >()方法的过程。类构造器<clinit>()方法是由编译器自动收集类中所有类变量的赋值动作和静态代码块中的语句合并产生的。（**类构造器是构造类信息的，不是构造该类对象的构造器**）。
当初始化一个类的时候，如果发现其父类还没有进行初始化，则需要先触发其父类的初始化。
虚拟机会保证一个类的<clinit >()方法在多线程环境中被正确加锁和同步。

类的静态部分(静态代码块 + 静态域变量)
非静态部分(构造代码块 + 非静态域)
构造器

有且只有四种情况必须立即对类进行初始化：

- 遇到 new、getstatic、putstatic、invokestatic 这四条字节码指令时，如果类还没有进行过初始化，则需要先触发其初始化。生成这四条指令最常见的 Java 代码场景是：使用 new 关键字实例化对象时、读取或设置一个类的静态字段（static）时（被 static 修饰又被 final 修饰的，已在编译期把结果放入常量池的静态字段除外）、以及调用一个类的静态方法时。
- 使用 Java.lang.refect 包的方法对类进行反射调用时，如果类还没有进行过初始化，则需要先触发其初始化。
- 当初始化一个类的时候，如果发现其父类还没有进行初始化，则需要先触发其父类的初始化。
- 当虚拟机启动时，用户需要指定一个要执行的主类，虚拟机会先执行该主类。


**对象的实例化过程分成两部分：类的加载初始化，对象的初始化**

### init 方法

Java 在编译之后会在字节码文件中生成 init 方法，称之为实例构造器，该实例构造器会将语句块，变量初始化，调用父类的构造器等操作收敛到 init 方法中，收敛顺序为：

1. 父类变量初始化
2. 父类语句块
3. 父类构造函数
4. 子类变量初始化
5. 子类语句块
6. 子类构造函数

- 收敛到 init 方法的意思是：将这些操作放入到 init 中去执行。

### clinit 方法

Java 在编译之后会在字节码文件中生成 clinit 方法，称之为类构造器。类构造器同实例构造器一样，也会将静态语句块，静态变量初始化，收敛到 clinit 方法中，收敛顺序为：

1. 父类静态变量初始化
2. 父类静态语句块
3. 子类静态变量初始化
4. 子类静态语句块

- **若父类为接口，则不会调用父类的 clinit 方法。一个类可以没有 clinit 方法。**
- clinit 方法是在类加载过程中执行的，而 init 是在对象实例化执行的，所以 clinit 一定比 init 先执行。**整个顺序就是：**

1. 父类静态变量初始化
2. 父类静态语句块
3. 子类静态变量初始化
4. 子类静态语句块
5. 父类变量初始化
6. 父类语句块
7. 父类构造函数
8. 子类变量初始化
9. 子类语句块
10. 子类构造函数


作者：w4irdo
链接：https://juejin.cn/post/6844903957836333063
来源：稀土掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。





















————————————————
版权声明：本文为CSDN博主「Tyfrank」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/Sun_study/article/details/123143188