## 概述

```
catalina.policy
catalina.properties

context.xml
server.xml
web.xml

jaspic-providers.xml
jaspic-providers.xsd

tomcat-users.xml
tomcat-users.xsd


looging.propperties



```





## 内容

```java
server.xml：Tomcat的主配置文件，包含Service, Connector, Engine, Realm, Valve, Hosts主组件的相关配置信息；
    
    
web.xml：
    遵循Servlet规范标准的配置文件，用于配置servlet，
    并为所有的Web应用程序提供包括MIME映射等默认配置信息；
    
context.xml：
    所有host的默认配置信息；
    用于定义所有 web 应用均需加载的 context 配置， 如果web应用制定了自己的 context.xml ，该文件将被覆盖
    
logging.properties：日志相关配置；
    
tomcat-users.xml：
    tomcat 的操作权限控制映射，默认的用户和角色映射配置信息， 
    Realm认证时用到的相关角色、用户和密码等信息；
    Tomcat自带的manager默认情况下会用到此文件；
    在Tomcat中添加/删除用户，为用户指定角色等将通过编辑此文件实现；
    
catalina.policy：
    tomcat 运行的安全策略配置
    Java相关的安全策略配置文件，在系统资源级别上提供访问控制的能力，以安全模式启动Tomcat会使用这个配置
    
catalina
    用于存储针对每个额虚拟机的 context 配置
catalina.properties：
    tomcat 的环境变量
    Tomcat内部package的定义及访问相关的控制，也包括对通过类装载器装载的内容的控制；
    Tomcat在启动时会事先读取此文件的相关设置；
    
    
jaspic-providers.xml：用户认证配置文件
```