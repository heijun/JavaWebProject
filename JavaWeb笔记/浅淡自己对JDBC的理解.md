# 浅淡自己对JDBC的理解



JDK自带了一个`java.sql`包，而这里面定义了大量的接口，不同类型的数据库，都可以通过实现此接口，编写适用于自己数据库的实现类。而不同的数据库厂商实现的这套标准，我们称为`数据库驱动`。

```java

public interface Driver {


    Connection connect(String url, java.util.Properties info)
        throws SQLException;

    boolean acceptsURL(String url) throws SQLException;
  
    DriverPropertyInfo[] getPropertyInfo(String url, java.util.Properties info)
                         throws SQLException;

    int getMajorVersion();
    
    int getMinorVersion();
    
    boolean jdbcCompliant();

    //------------------------- JDBC 4.1 -----------------------------------

   
    public Logger getParentLogger() throws SQLFeatureNotSupportedException;
}
```



1）直接创建 Driver 接口的实现类对象
例如：

```java
Driver driver = new com.mysql.jdbc.Driver();
//因为与 mysql 驱动包中的类发生直接依赖，所以这样可移植性不够好
```


2）DriverManager 类的 registerDriver()

在实际开发中，程序中不直接去访问实现了 Driver 接口的类，而是由驱动程序管理器类(java.sql.DriverManager) 去调用这些 Driver 实现。
DriverManager 类是驱动程序管理器类，负责管理驱动程序。

```java
//DriverManager.registerDriver()方式注册驱动，还是依赖 
DriverManager.registerDriver(new com.mysql.jdbc.Driver());
```

3）Class.forName()或 ClassLoader 对象.loadClass()
因 为 Driver 接 口 的 驱 动 程 序 类 都 包 含 了 静 态 代 码 块 ， 在 这 个 静 态 代 码 块 中 ， 会 调 用 DriverManager.registerDriver() 方法来注册自身的一个实例，所以可以换一种方式来加载驱动。
（即只要想办法让驱动类的这段静态代码块执行即可注册驱动类，而要让这段静态代码块执行，只要让该类被类加载器加载即可）。如下：

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mysql.cj.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Driver extends NonRegisteringDriver implements java.sql.Driver {
    public Driver() throws SQLException {
    }

    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException var1) {
            throw new RuntimeException("Can't register driver!");
        }
    }// 在这个静态代码块中,会调用DriverManager.registerDriver() 方法来注册自身的一个实例；根据类加载机制，当执行 Class.forName(driverClass) 获取其Class对象时， com.mysql.jdbc.Driver 就会被JVM加载，连接，并进行初始化，初始化就会执行静态代码块
}
```

调用 Class 类的静态方法 forName()，向其传递要加载的 JDBC 驱动的类名

```java
class forName("com.mysql.cj.jdbc.Driver");
//通过类加载器加载驱动实现类
try {
         ClassLoader.getSystemClassLoader().loadClass("com.mysql.cj.jdbc.Driver");
     } catch (ClassNotFoundException e) {
         e.printStackTrace();
     }
```

DriverManger将要注册的驱动程序信息封装到了DriverInfo中，然后放进了一个List中。在后边获得连接时会再用到。

```java
private final static CopyOnWriteArrayList<DriverInfo> registeredDrivers = new CopyOnWriteArrayList<>();
```

DriverInfo对象

```java
class DriverInfo {

    final Driver driver;
    DriverAction da;
    DriverInfo(Driver driver, DriverAction action) {
        this.driver = driver;
        da = action;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof DriverInfo)
                && this.driver == ((DriverInfo) other).driver;
    }

    @Override
    public int hashCode() {
        return driver.hashCode();
    }

    @Override
    public String toString() {
        return ("driver[className="  + driver + "]");
    }

    DriverAction action() {
        return da;
    }
}
```



```java
/**
 * Registers the given driver with the {@code DriverManager}.
 * A newly-loaded driver class should call
 * the method {@code registerDriver} to make itself
 * known to the {@code DriverManager}. If the driver is currently
 * registered, no action is taken.
 *
 * @param driver the new JDBC Driver that is to be registered with the
 *               {@code DriverManager}
 * @exception SQLException if a database access error occurs
 * @exception NullPointerException if {@code driver} is null
 */
public static synchronized void registerDriver(java.sql.Driver driver)
    throws SQLException {

    registerDriver(driver, null);
}

/**
 * Registers the given driver with the {@code DriverManager}.
 * A newly-loaded driver class should call
 * the method {@code registerDriver} to make itself
 * known to the {@code DriverManager}. If the driver is currently
 * registered, no action is taken.
 *
 * @param driver the new JDBC Driver that is to be registered with the
 *               {@code DriverManager}
 * @param da     the {@code DriverAction} implementation to be used when
 *               {@code DriverManager#deregisterDriver} is called
 * @exception SQLException if a database access error occurs
 * @exception NullPointerException if {@code driver} is null
 * @since 1.8
 */
public static synchronized void registerDriver(java.sql.Driver driver,
        DriverAction da)
    throws SQLException {

    /* Register the driver if it has not already been added to our list */
    if(driver != null) {
        registeredDrivers.addIfAbsent(new DriverInfo(driver, da));
    } else {
        // This is for compatibility with the original DriverManager
        throw new NullPointerException();
    }

    println("registerDriver: " + driver);

}
```

在Main方法中我们一般用下面的方法获取连接对象：

```java
@CallerSensitive
public static Connection getConnection(String url,
    String user, String password) throws SQLException {
    
java.util.Properties info = new java.util.Properties();
    
if (user != null) {
    info.put("user", user);
}
if (password != null) {
    info.put("password", password);
}

return (getConnection(url, info, Reflection.getCallerClass()));   //内部有实现
}
```

方法返回的是下面的getConnection静态方法， 实际是在这个方法中返回一个 Connection con连接实例：

```java
private static Connection getConnection(
        String url, java.util.Properties info, Class<?> caller) throws SQLException {
        /*
         * When callerCl is null, we should check the application's
         * (which is invoking this class indirectly)
         * classloader, so that the JDBC driver class outside rt.jar
         * can be loaded from here.
         */
        ClassLoader callerCL = caller != null ? caller.getClassLoader() : null;
        synchronized(DriverManager.class) {
            // synchronize loading of the correct classloader.
            if (callerCL == null) {
                callerCL = Thread.currentThread().getContextClassLoader();
            }
        }

        if(url == null) {
            throw new SQLException("The url cannot be null", "08001");
        }

        println("DriverManager.getConnection(\"" + url + "\")");

        // Walk through the loaded registeredDrivers attempting to make a connection.
        // Remember the first exception that gets raised so we can reraise it.
        SQLException reason = null;

        for(DriverInfo aDriver : registeredDrivers) {
            // If the caller does not have permission to load the driver then
            // skip it.
            if(isDriverAllowed(aDriver.driver, callerCL)) {
                try {
                    println("    trying " + aDriver.driver.getClass().getName());
                    Connection con = aDriver.driver.connect(url, info);
                    if (con != null) {
                        // Success!
                        println("getConnection returning " + aDriver.driver.getClass().getName());
                        return (con);
                    }
                } catch (SQLException ex) {
                    if (reason == null) {
                        reason = ex;
                    }
                }

            } else {
                println("    skipping: " + aDriver.getClass().getName());
            }

        }

        // if we got here nobody could connect.
        if (reason != null)    {
            println("getConnection failed: " + reason);
            throw reason;
        }

        println("getConnection: no suitable driver found for "+ url);
        throw new SQLException("No suitable driver found for "+ url, "08001");
    }


}
```

获取了连接实例后就可以创建一个用于执行SQL的statement对象.并进行后续操作：

```java
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        //1. 通过DriverManager来获得数据库连接
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmanager","root","123456");
             //2. 创建一个用于执行SQL的Statement对象
             Statement statement = connection.createStatement()){   //注意前两步都放在try()中，因为在最后需要释放资源！
            //3. 执行SQL语句，并得到结果集
            ResultSet set = statement.executeQuery("select * from bookmanager.book");
            //4. 查看结果
            while (set.next()){
        System.out.println(set.getString(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
//5. 释放资源，try-with-resource语法会自动帮助我们close


    }
}
```







```java
public boolean equals(Object o) {
    if (o == this) {
        return true;
    } else if (!(o instanceof Student)) {
        return false;
    } else {
        Student other = (Student)o;
        if (!other.canEqual(this)) {
            return false;
        } else {
            label59: {
                Object this$sid = this.getSid();
                Object other$sid = other.getSid();
                if (this$sid == null) {
                    if (other$sid == null) {
                        break label59;
                    }
                } else if (this$sid.equals(other$sid)) {
                    break label59;
                }

                return false;
            }

            Object this$grade = this.getGrade();
            Object other$grade = other.getGrade();
            if (this$grade == null) {
                if (other$grade != null) {
                    return false;
                }
            } else if (!this$grade.equals(other$grade)) {
                return false;
            }

            Object this$name = this.getName();
            Object other$name = other.getName();
            if (this$name == null) {
                if (other$name != null) {
                    return false;
                }
            } else if (!this$name.equals(other$name)) {
                return false;
            }

            Object this$sex = this.getSex();
            Object other$sex = other.getSex();
            if (this$sex == null) {
                if (other$sex != null) {
                    return false;
                }
            } else if (!this$sex.equals(other$sex)) {
                return false;
            }

            return true;
        }
    }
}

protected boolean canEqual(Object other) {
     return other instanceof Student;
}
```



```java
public int hashCode() {
    int PRIME = true;
    int result = 1;
    Object $sid = this.getSid();
    int result = result * 59 + ($sid == null ? 43 : $sid.hashCode());
    Object $grade = this.getGrade();
    result = result * 59 + ($grade == null ? 43 : $grade.hashCode());
    Object $name = this.getName();
    result = result * 59 + ($name == null ? 43 : $name.hashCode());
    Object $sex = this.getSex();
    result = result * 59 + ($sex == null ? 43 : $sex.hashCode());
    return result;
}
```



版权声明：本文为CSDN博主「你走吧起风了__」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/m0_46405589/article/details/107914361