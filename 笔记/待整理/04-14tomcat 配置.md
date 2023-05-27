# 服务器配置 **server.xml**

* **server.xml****的每一个元素都对应了Tomcat****中的一个组件**；通过对xml文件中元素的配置，可以实现对Tomcat中各个组件的控制

## 整体结构

```xml
<Server>
    <Service>
        <Connector />
        <Connector />
        <Engine>
            <Host>
                <Context /><!-- 现在常常使用自动部署，不推荐配置Context元素，Context小节有详细说明 -->
            </Host>
        </Engine>
    </Service>
</Server>
```

## Server

* 整个配置文件的根元素

* 代表整个Tomcat容器，因此它必须是server.xml中唯一一个最外层的元素。**一个****Server****元素中可以有一个或多个Service****元素。

* 提供一个接口让客户端能够访问到这个Service集合，同时维护它所包含的所有的Service的声明周期，包括如何初始化、如何结束服务、如何找到客户端要访问的Service。

* 属性

  ```xml
  port="8005"    Server接收shutdown指令的端口号，设为-1可以禁掉该端口
  shutdown="SHUTDOWN" 示关闭Server的指令
  ```

  

## service

* 在Connector和Engine外面包了一层，把它们组装在一起，对外提供服务【门面模式？？？】

* **一个****Service****可以包含多个Connector****，但是只能包含一个 Engine, Connector的作用是从客户端接收请求，Engine的作用是处理接收进来的请求。

* 属性

  ```xml
  name="Catalina" 
  ```

  

## Connector

* 代表了外部客户端发送请求到特定Service的接口；同时也是外部客户端从特定Service接收响应的接口。

* 是接收连接请求，创建Request和Response对象用于和请求端交换数据；然后分配线程让Engine来处理这个请求，并把产生的Request和Response对象传给Engine。

* 属性

  ```
  port="8080"    客户端可以通过8080端口号使用http协议访问Tomcat。
  protocol="HTTP/1.1"   规定了请求的协议
  connectionTimeout="20000"   连接的超时时间
  redirectPort="8443"  强制要求https而请求是http时，重定向至端口号为8443的Connector
  ```

### connectionTimeout

```
	目前大多数HTTP请求使用的是长连接（HTTP/1.1默认keep-alive为true），而长连接意味着，一个TCP的socket在当前请求结束后，如果没有新的请求到来，socket不会立马释放，而是等timeout后再释放。

	如果使用BIO，“读取socket并交给Worker中的线程”这个过程是阻塞的，也就意味着在socket等待下一个请求或等待释放的过程中，处理这个socket的工作线程会一直被占用，无法释放；因此Tomcat可以同时处理的socket数目不能超过最大线程数，性能受到了极大限制。
	而使用NIO，“读取socket并交给Worker中的线程”这个过程是非阻塞的，当socket在等待下一个请求或等待释放时，并不会占用工作线程，因此Tomcat可以同时处理的socket数目远大于最大线程数，并发性能大大提高。
```



### protocol

* Connector在处理HTTP请求时，会使用不同的protocol。不同的Tomcat版本支持的protocol不同

* 默认值

  ```
  如果没有指定protocol，则使用默认值HTTP/1.1
  含义
  	在Tomcat7中，自动选取使用BIO或APR（如果找到APR需要的本地库，则使用APR，否则使用BIO）；
  	在Tomcat8中，自动选取使用NIO或APR（如果找到APR需要的本地库，则使用APR，否则使用NIO）。
  ```

  

| 类型 |                                                              |                       |
| ---- | ------------------------------------------------------------ | --------------------- |
| BIO  | Blocking IO，顾名思义是阻塞的IO                              | Tomcat7，8.5和9去掉了 |
| NIO  | Non-blocking IO，则是非阻塞的IO                              | Tomcat7               |
| APR  | Apache Portable Runtime，是Apache可移植运行库，利用本地库可以实现高可扩展性、高性能<br/>在Tomcat上运行高并发应用的首选模式，但是需要安装apr、apr-utils、tomcat-native等包。 | Tomcat7               |
| NIO2 |                                                              | Tomcat8               |

### acceptCount

accept队列的长度；当accept队列中连接的个数达到acceptCount时，队列满，进来的请求一律被拒绝。默认值是100。

### maxConnections

Tomcat在任意时刻接收和处理的最大连接数。当Tomcat接收的连接数达到maxConnections时，Acceptor线程不会读取accept队列中的连接；这时accept队列中的线程会一直阻塞着，直到Tomcat接收的连接数小于maxConnections。如果设置为-1，则连接数不受限制。

默认值与连接器使用的协议有关：NIO的默认值是10000，APR/native的默认值是8192，而BIO的默认值为maxThreads（如果配置了Executor，则默认值是Executor的maxThreads）。

在windows下，APR/native的maxConnections值会自动调整为设置值以下最大的1024的整数倍；如设置为2000，则最大值实际是1024。

### maxThreads

请求处理线程的最大数量。默认值是200（Tomcat7和8都是的）。如果该Connector绑定了Executor，这个值会被忽略，因为该Connector将使用绑定的Executor，而不是内置的线程池来执行任务。

maxThreads规定的是最大的线程数目，并不是实际running的CPU数量；实际上，maxThreads的大小比CPU核心数量要大得多。这是因为，处理请求的线程真正用于计算的时间可能很少，大多数时间可能在阻塞，如等待数据库返回数据、等待硬盘读写数据等。因此，在某一时刻，只有少数的线程真正的在使用物理CPU，大多数线程都在等待；因此线程数远大于物理核心数才是合理的。

换句话说，Tomcat通过使用比CPU核心数量多得多的线程数，可以使CPU忙碌起来，大大提高CPU的利用率。

### 设置

```
（1）maxThreads的设置既与应用的特点有关，也与服务器的CPU核心数量有关。通过前面介绍可以知道，maxThreads数量应该远大于CPU核心数量；而且CPU核心数越大，maxThreads应该越大；应用中CPU越不密集（IO越密集），maxThreads应该越大，以便能够充分利用CPU。当然，maxThreads的值并不是越大越好，如果maxThreads过大，那么CPU会花费大量的时间用于线程的切换，整体效率会降低。

（2）maxConnections的设置与Tomcat的运行模式有关。如果tomcat使用的是BIO，那么maxConnections的值应该与maxThreads一致；如果tomcat使用的是NIO，maxConnections值应该远大于maxThreads。

（3）通过前面的介绍可以知道，虽然tomcat同时可以处理的连接数目是maxConnections，但服务器中可以同时接收的连接数为maxConnections+acceptCount 。acceptCount的设置，与应用在连接过高情况下希望做出什么反应有关系。如果设置过大，后面进入的请求等待时间会很长；如果设置过小，后面进入的请求立马返回connection refused。
```



## Engine、Host、Context

## Executor

* 代表Tomcat中的线程池，可以由其他组件共享使用

* Executor是Service元素的内嵌元素。一般来说，使用线程池的是Connector组件；为了使Connector能使用线程池，Executor元素应该放在Connector前面

* 属性

  ```
  <Executor name="tomcatThreadPool" namePrefix ="catalina-exec-" maxThreads="150" minSpareThreads="4" />
  <Connector executor="tomcatThreadPool" port="8080" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" acceptCount="1000" />
  
  
  name：该线程池的标记
  maxThreads：线程池中最大活跃线程数，默认值200（Tomcat7和8都是）
  minSpareThreads：线程池中保持的最小线程数，最小值是25
  maxIdleTime：线程空闲的最大时间，当空闲超过该值时关闭线程（除非线程数小于minSpareThreads），单位是ms，默认值60000（1分钟）
  daemon：是否后台线程，默认值true
  threadPriority：线程优先级，默认值5
  namePrefix：线程名字的前缀，线程池中线程名字为：namePrefix+线程编号
  
  ```



# 状态查看

## jconsole

## 命令

```
查看进程状态 
ps –e | grep java #  27989 ...
结果
27989？ 00 java
	只打印了一个进程的信息；27989是进程id，java是指执行的java命令。这是因为启动一个tomcat，内部所有的工作都在这一个进程里完成，包括主线程、垃圾回收线程、Acceptor线程、请求处理线程等等。


该进程内有多少个线程
ps –o nlwp 27989
结果
	没有排除处于idle状态的线程
	
获得真正在running的线程数量
	ps -eLo pid ,stat | grep 27989 | grep running | wc -l
```



