# 课本推荐

```
  1.《深入剖析Tomcat》（作者：陈昊鹏）：这本书是国内外Tomcat源码解析的经典之作，详细介绍了Tomcat的架构、组件、代码实现和性能优化等方面的知识，适合有一定Java基础的读者。

2.《Tomcat源码深度解析》（作者：李刚）：这本书是对Tomcat源码的深入解析，包括Tomcat的架构、Servlet容器、JSP容器、连接器、Session管理和安全机制等方面的内容，适合有一定Java和Web开发经验的读者。

3.《Tomcat技术内幕》（作者：陈昊鹏）：这本书是Tomcat源码解析的进阶之作，详细介绍了Tomcat的启动过程、类加载机制、线程池、内存管理、调试技巧和性能优化等方面的知识，适合有一定Java和Tomcat开发经验的读者。

4.《Tomcat源码分析与实战》（作者：陈昊鹏）：这本书是一本实战性的Tomcat源码解析书籍，通过实际案例来讲解Tomcat的架构设计、性能优化、安全管理和高可用性等方面的知识，适合有一定Java和Web开发经验的读者。

5.《Tomcat源码剖析》（作者：王爽）：这本书是一本全面介绍Tomcat源码的书籍，包括Tomcat的架构设计、Servlet容器、JSP容器、连接器、Session管理和安全机制等方面的内容，适合想要深入了解Tomcat源码的读者。
```



# 目录

```java
// 专门用来存放 Tomcat 服务器的
bin 	可执行程序， 存放 tomcat 的启动、停止等批处理脚本
    	startup/shutdown.bat|.sh   wind/linux 下的启动停止文件
    
conf 	包含Tomcat服务器的配置文件，包括server.xml、web.xml等。
    
lib  	包含Tomcat服务器运行所需要的所有Java类库文件  jar。
    
logs 	包含Tomcat服务器的日志文件，包括访问日志、错误日志等。 
    
    
temp  	包含Tomcat服务器运行时产生的临时文件，如上传的文件等。 
    
webapps 包含所有部署在Tomcat服务器上的Web应用程序。
    
work 	包含Tomcat服务器运行时生成的临时文件，如JSP编译后的.class文件等。
    	是 Tomcat 工作时的目录，
        存放 Tomcat 运行时 jsp 翻译为 Servlet 的源码，
        和 Session 钝化（序列化）的目录。
```

## config

```java

catalina：包含Tomcat服务器的启动和停止脚本文件。// 用于针对每个虚拟机的context 配置
catalina/localhost：包含部署在Tomcat服务器上的Web应用程序的上下文配置文件。
catalina/localhost/examples.xml：包含Tomcat服务器示例Web应用程序的上下文配置文件。
    
server.xml：
    	包含Tomcat服务器的主要配置文件，用于配置服务器端口、连接器、虚拟主机、日志等。    
web.xml：
    	// 通常在自己项目中的 webapp/WEB-INF 中的覆盖
    	包含Tomcat服务器的Web应用程序的配置文件，用于配置Servlet、Filter、Listener等。    
    
    
catalina.policy  // tomcat 安全策略配置
catalina.properties  // tomcat 环境变量
context.xml
logging.properties  // tomcat 日志配置
 
    
    
jaspic-providers.xml
jaspic-providers.xsd

 
tomcat-users.xml
tomcat-users.xsd

  

```

## webapps

```
examples：Tomcat服务器示例Web应用程序的根目录。

ROOT：Tomcat服务器默认的Web应用程序的根目录。

```

## work

```

15. work/Catalina/localhost：包含部署在Tomcat服务器上的Web应用程序的工作目录，用于存储JSP编译后的.class文件等。 
```







* 一些名词

  ```
  cs： 客户端/服务端
  bs:  浏览器/服务器
  静态资源： 所有人看到都是一样的，死数据， 【图片，css】
  动态资源： 后端会根据每个人的不同，给其动态展示数据
  网络通信关键： ip 端口 基础协议
  ```



# 介绍

## 概述

* 一个 Servlet 容器
* Tomcat 8 实现了对 Servlet 3.1 和 JavaServer Page 2.3（JSP）的支持，并提供了作为 Web 服务器的一些特有功能
* Apache软件基金会旗下的 Jakarta 项目，按照 Sun Microsystems 提供的技术规范开发出来





# 历史

```
java-web-server  + JServ  ==> 3.x servlet2.2 和 jsp1.1
tomcat4.0  ==> servlet4.0 和 jsp1.2
 
```





# 组件

### 内容

* 服务器Server，服务Service，
* 连接器Connector、容器Container
* 连接器Connector和容器Container（核心）



# 常用的web 服务器

```java
|- Web Server
        |- Http Server
            |- Apache
            |- Nginx
            |- ...... 	
        |- Application Server
            |- Servlet Container
            |- CGI Server
            |- ......

// ==================================

webLogic【oracle】
webSphere【IBM】
JBOSS【JBOSS/REDH】
TOMCAT【apach开源】
Jetty【Eclipse】    
    
```



## http 服务器

* HTTP Server本质上也是一种应用程序——它通常运行在服务器之上，绑定服务器的IP地址并监听某一个tcp端口来接收并处理HTTP请求，这样客户端（一般来说是IE, Firefox，Chrome这样的浏览器）就能够通过HTTP协议来获取服务器上的网页（HTML格式）、文档（PDF格式）、音频（MP4格式）、视频（MOV格式）等等资源。

## Application Server 

* 是一个应用执行的服务器。它首先需要支持开发语言的 Runtime（对于 Tomcat 来说，就是 Java），保证应用能够在应用服务器上正常运行。其次，需要支持应用相关的规范，例如类库、安全方面的特性。与HTTP Server相比，Application Server能够动态的生成资源并返回到客户端。
* 当初在Apache Server开发时还未出现Servlet的概念，所以Apache不能内置支持Servlet。实际上，除了Apache，其他许多HTTP Server软件都不能直接支持Servlet。为了支持Servlet，通常要单独开发程序，这种程序一般称为服务器小程序容器（Servlet Container），有时也叫做服务器小程序引擎（Servlet Engine）。
* 它是Web服务器或应用程序服务器的一部分，用于在发送的请求和响应之上提供网络服务，解码基于MIME的请求，格式化基于MIME的响应，它在Servlet的生命周期内包容和管理Servlet，是一个实时运行的外壳程序。运行时由Web服务器软件处理一般请求，并把Servlet调用传递给“容器”来处理。
* 对于 Tomcat 来说，就是需要提供 JSP/Sevlet 运行需要的标准类库、Interface 等。为了方便，应用服务器往往也会集成 HTTP Server 的功能，但是不如专业的 HTTP Server 那么强大，所以Application Server往往是运行在 HTTP Server 的背后，执行应用，将动态的内容转化为静态的内容之后，通过 HTTP Server 分发到客户端。

# tomcat 整体架构

![Tomcat架构及核心原理精讲-Java免费视频课程-博学谷](assets/OIP-C.OyRaIoEMYpdyIQHMHD2WDQHaEA)

## tomcat如何处理一个 http 请求

* 父组件初始化，然后调用子组件

![面试官：tomcat是如何处理http请求的？_田维常的博客-CSDN博客](assets/format,png.png)

![img](assets/v2-964e9c1377091aadb205412e4426b6f8_r.jpg)

![Tomcat外传 - 知乎](assets/v2-0c3308306160ddf4db0b60c66a48cb32_r.jpg)

## 工作流程

```
1. 请求被发送到服务器， 被在监听的 connector 获取，该 Connector 把该请求交给它所在的Service的Engine来处理，并等待Engine的回应；

2. 


Engine获得请求localhost:8080/test/index.jsp，匹配它所有虚拟主机Host；

Engine匹配到名为localhost的Host（即使匹配不到也把请求交给该Host处理，因为该Host被定义为该Engine的默认主机）；

localhost Host获得请求/test/index.jsp，匹配它所拥有的所有Context；

Host匹配到路径为/test的Context（如果匹配不到就把该请求交给路径名为""的Context去处理）；

path="/test"的Context获得请求/index.jsp，在它的mapping table中寻找对应的servlet；

Context匹配到URL PATTERN为*.jsp的servlet，对应于JspServlet类；

构造HttpServletRequest对象和HttpServletResponse对象，作为参数调用JspServlet的doGet或doPost方法；

Context把执行完了之后的HttpServletResponse对象返回给Host；

Host把HttpServletResponse对象返回给Engine；

Engine把HttpServletResponse对象返回给Connector；

Connector把HttpServletResponse对象返回给客户browser；
```



## 连接器架构 == coyote

# 源码构成

```
javax		
	javax的实现
catalina    
	Tomcat 中的一个核心组件，它是一个 Servlet 容器，用于处理 Web 应用程序的 HTTP 请求和响应。它负责管理 Web 应用程序的生命周期，包括加载、初始化、运行和卸载。Catalina 还负责处理与 Web 容器相关的一些任务，如会话管理、安全性、JNDI、JMX 等。在 Tomcat 中，Catalina 与 Coyote（一个 HTTP 连接器）一起工作，以提供完整的 Web 服务器功能。 
```

## org.apach

```
catalina
coyote
el
jasper
juli
naming
tomcat
```



### catalina

```java
1. connector：该目录包含了 Tomcat 的连接器组件，主要用于处理 HTTP 请求和响应，包括 Coyote HTTP/1.1 Connector、AJP Connector 等。
    
2. core：该目录包含了 Tomcat 的核心组件，主要包括 Catalina、ClassLoader、Container、Context、Lifecycle、Loader、Manager 等。
    
3. deploy: 英  /dɪˈplɔɪ/ 部署
    文件夹是 Tomcat 的部署目录，用于存放 Web 应用程序的部署描述文件和部署相关的配置文件。在该目录中，每个部署的 Web 应用程序都有一个对应的目录，其中包含了该应用程序的部署描述文件 web.xml 和一些其他的配置文件。

filters : 
	 Tomcat 中的过滤器组件，用于处理 Web 应用程序的 HTTP 请求和响应。过滤器是一种能够拦截和处理 HTTP 请求和响应的组件，它可以在请求到达 Servlet 之前对请求进行预处理，也可以在响应返回客户端之前对响应进行后处理。过滤器主要用于实现一些通用的功能，如日志记录、字符编码转换、安全性、压缩等。
         
    
ha：该目录包含了 Tomcat 的高可用性组件，主要用于实现 Tomcat 集群，包括 Cluster、Membership、Replication 等。
```

* 待整理

```java




3. 

4. jasper：该目录包含了 Tomcat 的 JSP 引擎 Jasper，用于将 JSP 文件编译成 Servlet。

5. juli：该目录包含了 Tomcat 的日志组件 Juli，用于记录 Tomcat 的日志信息。

6. realm：该目录包含了 Tomcat 的安全组件 Realm，用于实现用户认证和授权。

7. session：该目录包含了 Tomcat 的会话管理组件，用于管理 Web 应用程序的会话信息。

8. startup：该目录包含了 Tomcat 的启动组件，用于启动 Tomcat 服务器。

9. util：该目录包含了 Tomcat 的工具类和实用程序，用于提供一些通用的功能，如日期处理、加密解密、字符串处理等
```





### filters

```java
 
	constants

	local_strings.properties
	local_strings_de.properties
	local_strings_es.properties
	local_strings_fr.properties
	local_strings_ja.properties
	local_strings_ko.properties
	local_strings_ru.properties
	local_strings_zh__c_n.properties
	// =================================
	
     filter_base        
	add_default_charset_filter    // 用于实现字符编码转换。
	set_character_encoding_filter
        
	cors_filter     // 用于实现跨域资源共享。
	csrf_prevention_filter
	csrf_prevention_filter_base
	rest_csrf_prevention_filter
        
	
        
	failed_request_filter
        
	http_header_security_filter
	request_dumper_filter
	request_filter
	expires_filter   // 用于设置 HTTP 响应头中的 Expires 属性，控制浏览器缓存。 
        
	remote_addr_filter
	remote_c_i_d_r_filter
	remote_host_filter
	remote_ip_filter   // 用于获取客户端的真实 IP 地址
        
	session_initializer_filter

	webdav_fix_filter


 

2. Compression Filter：用于实现 HTTP 响应内容的压缩。 
5. Security Filter：用于实现 Web 应用程序的安全性控制。
 
7. SSL Filter：用于实现 SSL 安全连接
```



## 阅读

* 启动

  ```
  直接运行 org/apache/catalina/startup/Bootstrap.java 这个类中的 main 方法i
  ```


## 框架

```
catalina.startup
```





## 



# ==