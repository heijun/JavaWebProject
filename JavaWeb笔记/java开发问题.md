# java开发问题

问题：启动应用程序后，我收到 ClassNotFoundException: javax.servlet.http.HttpFilter

```java
@WebFilter("/to-do/*")
public class AuthenticationFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (AuthenticationService.isAuthenticated(req)){
            super.doFilter(req,res,chain);
        }else{
            res.sendRedirect("/");
        }
    }
}
```



```java
java.lang.NoClassDefFoundError: javax/servlet/http/HttpFilter
    at java.lang.ClassLoader.defineClass1(Native Method)
    at java.lang.ClassLoader.defineClass(ClassLoader.java:763)
[...]
Caused by: java.lang.ClassNotFoundException: javax.servlet.http.HttpFilter
    at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1344)
    at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1172)
    ... 59 more

```

您至少需要使用 Tomcat 版本**9** ， HttpFilter是在Tomacat8.0实现Servlet 3.1*时在*Servlet 4.0中引入的

另一种方法是在版本**8**中添加依赖项`javaee-api`

```xml
<dependency>
    <groupId>javax</groupId>
    <artifactId>javaee-api</artifactId>
    <version>8.0</version>
</dependency>
```

原文链接；https://stackoverflow.com/questions/57346981/how-to-fix-classnotfoundexception-javax-servlet-http-httpfilter







```java
BeanCreationException: Error creating bean with name 'userController': Injection of resource dependencies failed，

BeanNotOfRequiredTypeException: Bean named 'redisTemplate' is expected to be of type 'org.springframework.data.redis.core.StringRedisTemplate' but was actually of type 'org.springframework.data.redis.core.RedisTemplate'


翻译如下:

BeanCreationException:没有找到对应的bean，注入依赖资源项失败

BeanNotOfRequiredTypeException：名为“redisTemplate”的Bean应为“org.springframework.data.redis.core.StringRedisTemplate”类型，但实际为“org.springframework.data.redis.core.redisTemplate”类型。这个就很奇怪，我并没有用到redisTemplate的依赖，也不可能注入出错


两个注解都可以完成依赖注入功能。
1.@Autowired：
  @Autowired ：默认是以byType按类型自动注入。
  @Autowired + @Qualifier("名称")：将按照名称自动注入
  
2.@Resource：
  @Resource() 如果没有指定name属性，当注解写在字段上时，默认取字段名进行按照名称注入，
  如果注解写在setter方法上默认取属性名进行注入。 
  当找不到与名称匹配的bean时才按照类型进行装配。但是需要注意的是，如果name属性一旦指定，就只会按照名称进行装配。
  @Resource(name=" ")  将按照名称自动注入

————————————————
版权声明：本文为CSDN博主「庭前云落」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/remsqks/article/details/108586063
```



```java
@Service
public class RedisService  {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public void testSessionCallback(){
        StringRedisTemplate  stringRedisTemplate = this.getRedisTemplate();

        testSessionCallback( stringRedisTemplate);
    }

    private StringRedisTemplate getRedisTemplate() {
        return  stringRedisTemplate;
    }


    public void testSessionCallback(StringRedisTemplate  stringRedisTemplate) {
        stringRedisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
               /* redisOperations.opsForValue().set("myKey", value);*/
                String myValue = String.valueOf(redisOperations.opsForValue().get("b"));
                System.out.println(myValue);
                return myValue;
            }
        });
    }

}
```

```java
127.0.0.1:6379> set b 3
OK
127.0.0.1:6379>
```



```java
2022-08-15 13:05:35.325  INFO 15864 --- [           main] c.test4.SpringBootTest4ApplicationTests  : Started SpringBootTest4ApplicationTests in 4.405 seconds (JVM running for 5.987)
3
```



Redis通过`multi` `exec` `discard` 提供对事务的支持，这些操作在 RedisTemplate上可用。但是，RedisTemplate不能保证使用相同的连接运行事务中的所有操作。

Spring Data Redis 提供了`SessionCallback`接口，供需要一个执行多个操作`connection`时使用，例如使用 Redis 事务时。以下示例使用该`multi`方法：

```java
//execute a transaction
List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
  public List<Object> execute(RedisOperations operations) throws DataAccessException {
    operations.multi();
    operations.opsForSet().add("key", "value1");

    // This will contain the results of all operations in the transaction
    return operations.exec();
  }
});
System.out.println("Number of items added to set: " + txResults.get(0));
```

`RedisTemplate`使用它的值、哈希键和哈希值序列化器在`exec`返回之前反序列化所有结果。还有一种额外的`exec`方法可以让您为事务结果传递自定义序列化程序。

从 1.1 版本开始，对 RedisConnection 和 RedisTemplate 的 exec 方法进行了重要更改。以前，这些方法直接从连接器返回事务结果。这意味着数据类型通常与 RedisConnection 方法返回的数据类型不同。例如，zAdd 返回一个布尔值，指示元素是否已添加到排序集中。大多数连接器将此值作为 long 返回，Spring Data Redis 执行转换。另一个常见的区别是大多数连接器为诸如设置之类的操作返回一个状态回复（通常是字符串，OK）。这些回复通常被 Spring Data Redis 丢弃。在 1.1 之前，这些转换不会对 exec 的结果执行。此外，结果在 RedisTemplate 中没有反序列化，因此它们通常包含原始字节数组。如果此更改破坏了您的应用程序，请在您的 RedisConnectionFactory 上将 convertPipelineAndTxResults 设置为 false 以禁用此行为。





```java
Error creating bean with name 'tubunComponent' defined in com.test5.config.TwConfig: Unsatisfied dependency expressed through method 'tubunComponent' parameter 0; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'denpaComponent': Unsatisfied dependency expressed through field 'tolenComponent'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'tolenComponent': Injection of resource dependencies failed; nested exception is org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'tubunComponent': Requested bean is currently in creation: Is there an unresolvable circular reference?
```





循环依赖问题

```java
@Service
public class A {
    public A(B b) {
    }
}
@Service
public class B {
    public B(A a) {
    }
}
```



```java
Caused by: org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'a': Requested bean is currently in creation: Is there an unresolvable circular reference?
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.beforeSingletonCreation(DefaultSingletonBeanRegistry.java:339)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:215)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:318)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:199)
```

构造器注入构成的循环依赖，此种循环依赖方式**是无法解决的**，只能抛出`BeanCurrentlyInCreationException`异常表示循环依赖。这也是构造器注入的最大劣势



##### field属性注入（setter方法注入）循环依赖

这种方式是我们**最最最最**为常用的依赖注入方式（所以猜都能猜到它肯定不会有问题啦）：

```java
@Service
public class A {
    @Autowired
    private B b;
}

@Service
public class B {
    @Autowired
    private A a;
}
```

`根本原因`：Spring解决循环依赖依靠的是Bean的“中间态”这个概念，而这个中间态指的是已经实例化，但还没初始化的状态。而**构造器是完成实例化的东东**，所以构造器的循环依赖无法解决



##### prototype  field属性注入循环依赖

`prototype`在平时使用情况较少，但是也并不是不会使用到，因此此种方式也需要引起重视。

```java
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class A {
    @Autowired
    private B b;
}

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class B {
    @Autowired
    private A a;
}
```

结果：**需要注意的是**本例中**启动时是不会报错的**（**因为非单例Bean默认不会初始化，而是使用时才会初始化**），所以很简单咱们只需要手动`getBean()`或者在一个单例Bean内`@Autowired`一下它即可

```java
// 在单例Bean内注入
    @Autowired
    private A a;
```



这样子启动就报错：

```java
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'mytest.TestSpringBean': Unsatisfied dependency expressed through field 'a'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'a': Unsatisfied dependency expressed through field 'b'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'b': Unsatisfied dependency expressed through field 'a'; nested exception is org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'a': Requested bean is currently in creation: Is there an unresolvable circular reference?

	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:596)
	at org.springframework.beans.factory.annotation.InjectionMetadata.inject(InjectionMetadata.java:90)
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessProperties(AutowiredAnnotationBeanPostProcessor.java:374)
```



如何解决？？？ 可能有的小伙伴看到网上有说使用`@Lazy`注解解决：

```java
    @Lazy
    @Autowired
    private A a;
```



此处负责任的告诉你这样是解决不了问题的(**可能会掩盖问题**)，`@Lazy`只是延迟初始化而已，当你真正使用到它（初始化）的时候，依旧会报如上异常。

对于Spring循环依赖的情况总结如下：

1. 不能解决的情况： 1. 构造器注入循环依赖 2. `prototype` field属性注入循环依赖
2. 能解决的情况： 1. field属性注入（setter方法注入）循环依赖



### Bean加载机制

#### getBean 方法

`getBean(String name)` 方法，根据名称获取 Bean，当然还有许多重载方法，如下：

```java
@Override
public Object getBean(String name) throws BeansException {
    return doGetBean(name, null, null, false);
}

@Override
public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
    return doGetBean(name, requiredType, null, false);
}

@Override
public Object getBean(String name, Object... args) throws BeansException {
    return doGetBean(name, null, args, false);
}

public <T> T getBean(String name, @Nullable Class<T> requiredType, @Nullable Object... args)
        throws BeansException {
    return doGetBean(name, requiredType, args, false);
}
```

最终都会调用 `doGetBean(...)` 这个方法

- 可以看到这个方法加载 Bean 的过程中，会先从缓存中获取**单例模式**的 Bean；
- 不管是从缓存中获取的还是新创建的，都会进行处理，如果是 FactoryBean 类型则调用其 getObject() 获取目标对象；
- BeanFactory 可能有父容器，如果当前容器找不到 BeanDefinition 则会尝试让父容器创建；
- 创建 Bean 的任务交由 AbstractAutowireCapableBeanFactory 去完成；
- 如果获取到的 Bean 不是我们想要类型，会通过类型转换机制转换成目标类型

```java
protected <T> T doGetBean(final String name, @Nullable final Class<T> requiredType,
        @Nullable final Object[] args, boolean typeCheckOnly) throws BeansException {

    // <1> 获取 `beanName`
    // 因为入参 `name` 可能是别名，也可能是 FactoryBean 类型 Bean 的名称（`&` 开头，需要去除）
    // 所以需要获取真实的 beanName
    final String beanName = transformedBeanName(name);
    Object bean;

    // <2> 先从缓存（仅缓存单例 Bean ）中获取 Bean 对象，这里缓存指的是 `3` 个 Map
    // 缓存中也可能是正在初始化的 Bean，可以避免**循环依赖注入**引起的问题
    // Eagerly check singleton cache for manually registered singletons.
    Object sharedInstance = getSingleton(beanName);
    // <3> 若从缓存中获取到对应的 Bean，且 `args` 参数为空
    if (sharedInstance != null && args == null) {
        if (logger.isTraceEnabled()) {
            if (isSingletonCurrentlyInCreation(beanName)) {
                logger.trace("Returning eagerly cached instance of singleton bean '" + beanName +
                        "' that is not fully initialized yet - a consequence of a circular reference");
            }
            else {
                logger.trace("Returning cached instance of singleton bean '" + beanName + "'");
            }
        }
        // <3.1> 获取 Bean 的目标对象，`scopedInstance` 非 FactoryBean 类型直接返回
        // 否则，调用 FactoryBean#getObject() 获取目标对象
        bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
    }
    // 缓存中没有对应的 Bean，则开启 Bean 的加载
    else {
        // Fail if we're already creating this bean instance:
        // We're assumably within a circular reference.
        // <4> 如果**非单例模式**下的 Bean 正在创建，这里又开始创建，表明存在循环依赖，则直接抛出异常
        if (isPrototypeCurrentlyInCreation(beanName)) {
            throw new BeanCurrentlyInCreationException(beanName);
        }

        // Check if bean definition exists in this factory.
        BeanFactory parentBeanFactory = getParentBeanFactory();
        // <5> 如果从当前容器中没有找到对应的 BeanDefinition，则从父容器中加载（如果存在父容器）
        if (parentBeanFactory != null && !containsBeanDefinition(beanName)) {
            // Not found -> check parent.
            // <5.1> 获取 `beanName`，因为可能是别名，则进行处理
            // 和第 `1` 步不同，不需要对 `&` 进行处理，因为进入父容器重新依赖查找
            String nameToLookup = originalBeanName(name);
            // <5.2> 若为 AbstractBeanFactory 类型，委托父容器的 doGetBean 方法进行处理
            // 否则，就是非 Spring IoC 容器，根据参数调用相应的 `getBean(...)`方法
            if (parentBeanFactory instanceof AbstractBeanFactory) {
                return ((AbstractBeanFactory) parentBeanFactory).doGetBean(nameToLookup, requiredType, args, typeCheckOnly);
            }
            else if (args != null) {
                return (T) parentBeanFactory.getBean(nameToLookup, args);
            }
            else if (requiredType != null) {
                return parentBeanFactory.getBean(nameToLookup, requiredType);
            }
            else {
                return (T) parentBeanFactory.getBean(nameToLookup);
            }
        }

        // <6> 如果不是仅仅做类型检查，则表示需要创建 Bean，将 `beanName` 标记为已创建过
        if (!typeCheckOnly) {
            markBeanAsCreated(beanName);
        }

        try {
            // <7> 从容器中获取 `beanName` 对应的的 RootBeanDefinition（合并后）
            final RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
            // 检查是否为抽象类
            checkMergedBeanDefinition(mbd, beanName, args);

            // Guarantee initialization of beans that the current bean depends on.
            // <8> 获取当前正在创建的 Bean 所依赖对象集合（`depends-on` 配置的依赖）
            String[] dependsOn = mbd.getDependsOn();
            if (dependsOn != null) {
                for (String dep : dependsOn) {
                    // <8.1> 检测是否存在循环依赖，存在则抛出异常
                    if (isDependent(beanName, dep)) {
                        throw new BeanCreationException(mbd.getResourceDescription(), beanName,
                                "Circular depends-on relationship between '" + beanName + "' and '" + dep + "'");
                    }
                    // <8.2> 将 `beanName` 与 `dep` 之间依赖的关系进行缓存
                    registerDependentBean(dep, beanName);
                    try {
                        // <8.3> 先创建好依赖的 Bean（重新调用 `getBean(...)` 方法）
                        getBean(dep);
                    }
                    catch (NoSuchBeanDefinitionException ex) {
                        throw new BeanCreationException(mbd.getResourceDescription(), beanName,
                                "'" + beanName + "' depends on missing bean '" + dep + "'", ex);
                    }
                }
            }

            // Create bean instance.
            // <9> 开始创建 Bean，不同模式创建方式不同
            if (mbd.isSingleton()) { // <9.1> 单例模式
                /*
                 * <9.1.1> 创建 Bean，成功创建则进行缓存，并移除缓存的早期对象
                 * 创建过程实际调用的下面这个 `createBean(...)` 方法
                 */
                sharedInstance = getSingleton(beanName,
                        // ObjectFactory 实现类
                        () -> {
                            try {
                                // **【核心】** 创建 Bean
                                return createBean(beanName, mbd, args);
                            } catch (BeansException ex) {
                                // Explicitly remove instance from singleton cache: It might have been put there
                                // eagerly by the creation process, to allow for circular reference resolution.
                                // Also remove any beans that received a temporary reference to the bean.
                                // 如果创建过程出现异常，则显式地从缓存中删除当前 Bean 相关信息
                                // 在单例模式下为了解决循环依赖，创建过程会缓存早期对象，这里需要进行删除
                                destroySingleton(beanName);
                                throw ex;
                            }
                });
                // <9.1.2> 获取 Bean 的目标对象，`scopedInstance` 非 FactoryBean 类型直接返回
                // 否则，调用 FactoryBean#getObject() 获取目标对象
                bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
            }
            // <9.2> 原型模式
            else if (mbd.isPrototype()) {
                // It's a prototype -> create a new instance.
                Object prototypeInstance = null;
                try {
                    // <9.2.1> 将 `beanName` 标记为原型模式正在创建
                    beforePrototypeCreation(beanName);
                    // <9.2.2> **【核心】** 创建 Bean
                    prototypeInstance = createBean(beanName, mbd, args);
                }
                finally {
                    // <9.2.3> 将 `beanName` 标记为不在创建中，照应第 `9.2.1` 步
                    afterPrototypeCreation(beanName);
                }
                // <9.2.4> 获取 Bean 的目标对象，`scopedInstance` 非 FactoryBean 类型直接返回
                // 否则，调用 FactoryBean#getObject() 获取目标对象
                bean = getObjectForBeanInstance(prototypeInstance, name, beanName, mbd);
            }
            // <9.3> 其他模式
            else {
                // <9.3.1> 获取该模式的 Scope 对象 `scope`，不存在则抛出异常
                String scopeName = mbd.getScope();
                final Scope scope = this.scopes.get(scopeName);
                if (scope == null) {
                    throw new IllegalStateException("No Scope registered for scope name '" + scopeName + "'");
                }
                try {
                    // <9.3.1> 从 `scope` 中获取 `beanName` 对应的对象（看你的具体实现），不存在则执行**原型模式**的四个步骤进行创建
                    Object scopedInstance = scope.get(beanName, () -> {
                        // 将 `beanName` 标记为原型模式正在创建
                        beforePrototypeCreation(beanName);
                        try {
                            // **【核心】** 创建 Bean
                            return createBean(beanName, mbd, args);
                        }
                        finally {
                            // 将 `beanName` 标记为不在创建中，照应上一步
                            afterPrototypeCreation(beanName);
                        }
                    });
                    // 获取 Bean 的目标对象，`scopedInstance` 非 FactoryBean 类型直接返回
                    // 否则，调用 FactoryBean#getObject() 获取目标对象
                    bean = getObjectForBeanInstance(scopedInstance, name, beanName, mbd);
                }
                catch (IllegalStateException ex) {
                    throw new BeanCreationException(beanName,
                            "Scope '" + scopeName + "' is not active for the current thread; consider " +
                            "defining a scoped proxy for this bean if you intend to refer to it from a singleton",
                            ex);
                }
            }
        }
        catch (BeansException ex) {
            cleanupAfterBeanCreationFailure(beanName);
            throw ex;
        }
    }

    // Check if required type matches the type of the actual bean instance.
    // <10> 如果入参 `requiredType` 不为空，并且 Bean 不是该类型，则需要进行类型转换
    if (requiredType != null && !requiredType.isInstance(bean)) {
        try {
            // <10.1> 通过类型转换机制，将 Bean 转换成 `requiredType` 类型
            T convertedBean = getTypeConverter().convertIfNecessary(bean, requiredType);
            // <10.2> 转换后的 Bean 为空则抛出异常
            if (convertedBean == null) {
                // 转换失败，抛出 BeanNotOfRequiredTypeException 异常
                throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
            }
            // <10.3> 返回类型转换后的 Bean 对象
            return convertedBean;
        }
        catch (TypeMismatchException ex) {
            if (logger.isTraceEnabled()) {
                logger.trace("Failed to convert bean '" + name + "' to required type '" +
                        ClassUtils.getQualifiedName(requiredType) + "'", ex);
            }
            throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
        }
    }
    // <11> 返回获取到的 Bean
    return (T) bean;
}
```



```java
 protected Object createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) throws BeanCreationException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Creating instance of bean '" + beanName + "'");
        }

        RootBeanDefinition mbdToUse = mbd;
        Class<?> resolvedClass = this.resolveBeanClass(mbd, beanName, new Class[0]);
        if (resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null) {
            mbdToUse = new RootBeanDefinition(mbd);
            mbdToUse.setBeanClass(resolvedClass);
        }

        try {
            mbdToUse.prepareMethodOverrides();
        } catch (BeanDefinitionValidationException var9) {
            throw new BeanDefinitionStoreException(mbdToUse.getResourceDescription(), beanName, "Validation of method overrides failed", var9);
        }

        Object beanInstance;
        try {
            beanInstance = this.resolveBeforeInstantiation(beanName, mbdToUse);
            if (beanInstance != null) {
                return beanInstance;
            }
        } catch (Throwable var10) {
            throw new BeanCreationException(mbdToUse.getResourceDescription(), beanName, "BeanPostProcessor before instantiation of bean failed", var10);
        }

        try {
            beanInstance = this.doCreateBean(beanName, mbdToUse, args);
            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Finished creating instance of bean '" + beanName + "'");
            }

            return beanInstance;
        } catch (ImplicitlyAppearedSingletonException | BeanCreationException var7) {
            throw var7;
        } catch (Throwable var8) {
            throw new BeanCreationException(mbdToUse.getResourceDescription(), beanName, "Unexpected exception during bean creation", var8);
        }
    }
```



### FactoryBean 的处理

获取 Bean 的目标对象

#### getObjectForBeanInstance 方法

```java
// AbstractBeanFactory.java
protected Object getObjectForBeanInstance( Object beanInstance, String name, String beanName, 
                                          @Nullable RootBeanDefinition mbd) {
    // Don't let calling code try to dereference the factory if the bean isn't a factory.
    // <1> 若 `name` 以 `&` 开头，说明想要获取 FactoryBean，则校验其**正确性**
    if (BeanFactoryUtils.isFactoryDereference(name)) {
        // <1.1> 如果是 NullBean 空对象，则直接返回
        if (beanInstance instanceof NullBean) {
            return beanInstance;
        }
        // <1.2> 如果不是 FactoryBean 类型，则抛出异常
        if (!(beanInstance instanceof FactoryBean)) {
            throw new BeanIsNotAFactoryException(beanName, beanInstance.getClass());
        }
    }

    // Now we have the bean instance, which may be a normal bean or a FactoryBean.
    // If it's a FactoryBean, we use it to create a bean instance, unless the caller actually wants a reference to the factory.
    // 到这里我们就有了一个 Bean，可能是一个正常的 Bean，也可能是一个 FactoryBean
    // 如果是 FactoryBean，则需要通过其 getObject() 方法获取目标对象

    // <2> 如果 `beanInstance` 不是 FactoryBean 类型，不需要再处理则直接返回
    // 或者（表示是 FactoryBean 类型） `name` 以 `&` 开头，表示你想要获取实际 FactoryBean 对象，则直接返回
    // 还不符合条件的话，表示是 FactoryBean，需要获取 getObject() 返回目标对象
    if (!(beanInstance instanceof FactoryBean) || BeanFactoryUtils.isFactoryDereference(name)) {
        return beanInstance;
    }

    Object object = null;
    // <3> 如果入参没有传 BeanDefinition，则从 `factoryBeanObjectCache` 缓存中获取对应的 Bean 对象
	// 入参传了 BeanDefinition 表示这个 Bean 是刚创建的，不走缓存，需要调用其 getObject() 方法获取目标对象
	// `factoryBeanObjectCache`：FactoryBean#getObject() 调用一次后返回的目标对象缓存在这里
    if (mbd == null) {
        object = getCachedObjectForFactoryBean(beanName);
    }
    // <4> 若第 `3` 步获取的对象为空，则需要调用 FactoryBean#getObject() 获得对象
    if (object == null) {
        // Return bean instance from factory.
        // <4.1> 将 `beanInstance` 转换成 FactoryBean 类型
        FactoryBean<?> factory = (FactoryBean<?>) beanInstance;
        // Caches object obtained from FactoryBean if it is a singleton.
        // <4.2> 如果入参没有传 BeanDefinition 并且当前容器存在对应的 BeanDefinition
        if (mbd == null && containsBeanDefinition(beanName)) {
            // 获取对应的 RootBeanDefinition（合并后）
            mbd = getMergedLocalBeanDefinition(beanName);
        }
        // 是否是用户定义的（不是 Spring 创建解析出来的）
        boolean synthetic = (mbd != null && mbd.isSynthetic());
        // <4.3> **【核心】**通过 FactoryBean 获得目标对象，单例模式会缓存在 `factoryBeanObjectCache` 中
        object = getObjectFromFactoryBean(factory, beanName, !synthetic);
    }
    return object;
}
```



#### getObjectFromFactoryBean 方法

`getObjectFromFactoryBean(FactoryBean<?> factory, String beanName, boolean shouldPostProcess)` 方法，获取 FactoryBean 的目标对象，方法如下：

```java
// FactoryBeanRegistrySupport.java
protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName, boolean shouldPostProcess) {
    // <1> `factory` 为单例模式，且单例 Bean 缓存中存在 `beanName` 对应的 FactoryBean 对象
    if (factory.isSingleton() && containsSingleton(beanName)) {
        synchronized (getSingletonMutex()) { // <1.1> 获取单例锁，保证安全
            // <1.2> 从 `factoryBeanObjectCache` 缓存中获取 FactoryBean#getObject() 创建的目标对象
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                // <1.3> 则根据 `factory` 获取目标对象，调用 FactoryBean#getObject() 方法
                object = doGetObjectFromFactoryBean(factory, beanName);
                // Only post-process and store if not put there already during getObject() call above
                // (e.g. because of circular reference processing triggered by custom getBean calls)
                // <1.4> 这里再进行一次校验，看是否在缓存中存在 FactoryBean 创建的目标对象，如果有则优先从缓存中获取
                // 保证 FactoryBean#getObject() 只能被调用一次
                // 没有的话，则对刚获取到的目标对象进行接下来的处理
                Object alreadyThere = this.factoryBeanObjectCache.get(beanName);
                if (alreadyThere != null) {
                    object = alreadyThere;
                } else {
                    // <1.5> 是否需要后续处理，这个 FactoryBean 的前身 BeanDefinition 是否由 Spring 解析出来的，通常情况下都是
                    if (shouldPostProcess) {
                        // <1.5.1> 若该 FactoryBean 处于创建中，则直接返回这个目标对象，不进行接下来的处理过程
                        if (isSingletonCurrentlyInCreation(beanName)) {
                            // Temporarily return non-post-processed object, not storing it yet..
                            return object;
                        }
                        // <1.5.2> 前置处理，将 `beanName` 标志为正在创建
                        beforeSingletonCreation(beanName);
                        try {
                            // <1.5.3> 对通过 FactoryBean 获取的目标对象进行后置处理
                            // 遍历所有的 BeanPostProcessor 的 postProcessAfterInitialization 方法（初始化的处理）
                            object = postProcessObjectFromFactoryBean(object, beanName);
                        }
                        catch (Throwable ex) {
                            throw new BeanCreationException(beanName,
                                    "Post-processing of FactoryBean's singleton object failed", ex);
                        }
                        finally {
                            // <1.5.4> 后置处理，将 `beanName` 标志为不在创建中
                            afterSingletonCreation(beanName);
                        }
                    }
                    // <1.6> 如果缓存中存在 `beanName` 对应的 FactoryBean 对象
                    // 上面不是判断了吗？也可能在上面的处理过程会有所变化，所以这里在做一层判断
                    // 目的：缓存 FactoryBean 创建的目标对象，则需要保证 FactoryBean 本身这个对象存在缓存中
                    if (containsSingleton(beanName)) {
                        // <1.6.1> 将这个 FactoryBean 创建的目标对象保存至 `factoryBeanObjectCache`
                        this.factoryBeanObjectCache.put(beanName, object);
                    }
                }
            }
            // <1.7> 返回 FactoryBean 创建的目标对象
            return object;
        }
    }
    // <2> `factory` 非单例模式，或单例 Bean 缓存中不存在 `beanName` 对应的 FactoryBean 对象
    else {
        // <2.1> 则根据 `factory` 获取目标对象，调用 FactoryBean#getObject() 方法
        Object object = doGetObjectFromFactoryBean(factory, beanName);
        // <2.2> 是否需要后续处理，这个 FactoryBean 的前身 BeanDefinition 是否由 Spring 解析出来的，通常情况下都是
        if (shouldPostProcess) {
            try {
                // <2.2.1> 对通过 FactoryBean 获取的目标对象进行后置处理
                // 遍历所有的 BeanPostProcessor 的 postProcessAfterInitialization 方法（初始化的处理）
                object = postProcessObjectFromFactoryBean(object, beanName);
            }
            catch (Throwable ex) {
                throw new BeanCreationException(beanName, "Post-processing of FactoryBean's object failed", ex);
            }
        }
        // <2.3> 返回 FactoryBean 创建的目标对象，非单例模式不会进行缓存
        return object;
    }
}
```



* BeanFactory是个Factory，也就是 IOC 容器或对象工厂，所有的 Bean 都是由 BeanFactory( 也就是 IOC 容器 ) 来进行管理。
* FactoryBean是一个能生产或者修饰生成对象的工厂Bean(本质上也是一个Bean)，可以在BeanFactory（IOC容器）中被管理，所以它并不是一个简单的Bean。当使用容器中factory bean的时候，该容器不会返回factory bean本身，而是返回其生成的对象。要想获取FactoryBean的实现类本身，得在getBean(String BeanName)中的BeanName之前加上&,写成getBean(String &BeanName)。

实际上我们的Mapper最终就以FactoryBean的形式，被注册到容器中进行加载

对 BeanFactory 接口的体系结构进行了分析，得知 **DefaultListableBeanFactory** 是 BeanFactory 的最底层实现，也就是 Spring 的底层 IoC 容器。接着分析了 `AbstractBeanFactory` 的 `getBean(...)` 方法，当我们显示或者隐式地调用这个方法时，会触发 Bean 的加载。上面所有小节对 Bean 的加载过程进行了分析，我已经有序地在每个小节面前添加了序号，这些序号对应着加载过程中的顺序。

不同作用域的 Bean 的创建，底层都会调用 `AbstractAutowireCapableBeanFactory` 的 `createBean(...)`方法进行创建，创建 Bean 的过程涉及到 Bean 生命周期的大部分阶段，例如实例化阶段、属性赋值阶段、Aware 接口回调阶段、初始化阶段都是在这个方法中完成的



Bean定义]首先扫描Bean，加载Bean定义 -> **[Bean定义]Bean定义和Bean工厂后置处理** -> [依赖注入]根据Bean定义通过反射创建Bean实例 -> [依赖注入]进行依赖注入（顺便解决循环依赖问题）-> [初始化Bean]BeanPostProcessor的初始化之前方法 -> [初始化Bean]Bean**初始化方法** -> [初始化Bean]BeanPostProcessor的初始化之前后方法 -> [完成]最终得到的Bean加载完成的实例
