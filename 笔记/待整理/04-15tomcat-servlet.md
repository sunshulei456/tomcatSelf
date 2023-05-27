# 介绍

## 生命周期

* **Servlet 加载—>实例化—>服务—>销毁**

![img](assets/v2-54a9abdca31d8b40bec9a82e3c30c1f6_720w.webp)

## servlet 间的关系

```
servlet.http.http-servlet  ==继承==》 servlet.GenericServlet  ==实现 ==》 servlet.servlet
```



# http请求

## 区别

```
幂等性（idempotent、idempotence）：幂等是一个数学与计算机学概念，常见于抽象代数中。在编程中一个幂等操作的特点是执行多次或1次，其影响是相同的。

比如：
GET：客户端请求多次或1次，对请求的资源产生的影响是相同；
DELETE：删除多次或1次，其删除的数据范围都是相同的，影响是相同的;
PUT：将A值更新为B值，执行多次其最终结果仍是B值；
```



![image-20230412111312716](assets/image-20230412111312716.png)

## put 和 post

```
简述： 没有强制规范的话，没啥区别
严格：
	PUT时，必须明确知道要操作的对象，如果对象不存在，创建对象；如果对象存在，则全部替换目标对象
	POST创建对象时，之前并不知道要操作的对象，由HTTP服务器为新创建的对象生成一个唯一的URI；使用POST修改已存在的对象时，一般只是修改目标对象的部分内容。
	
一般：
	post 提交表单
	其他：幂等性不一致
```



# == 测试项目构建

## java

```java
public class Demo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        //设置网页响应类型
        response.setContentType("text/html");
        //实现具体操作
        PrintWriter out = response.getWriter();
        out.println("This is a new servlet page");
    }
}
```



## web.xml 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <servlet>
        <servlet-name>Demo</servlet-name>
        <servlet-class>Demo</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>Demo</servlet-name>
        <url-pattern>/Demo</url-pattern>
    </servlet-mapping>
</web-app>
```

# 待整理

```
HttpServlet 是基于Http 协议实现的Servlet 基类,我们在写Servlet 的时候直接继承它就行
 了.SpringMVC 中的DispatchServlet 就是继承了HttpServlet.HttpServlet 重写了service 方法,
 而service 方法首先将ServletRequest 和ServletResponse 转成HttpServletRequest 和
 HttpServletResponse,然后根据Http 不同类型的请求,再路由到不同的处理方法进行处理
 
 
  Web 容器加载Servlet 并将其实例化后，Servlet 生命周期开始，容器运行其init()方法进行Servlet 的初始化；请求到达时调用Servlet 的service 方法，service 方法会调用与请求对应的doGet 或doPost 等方法；
 当服务器关闭会项目被卸载时服务器会将Servlet 实例销毁，此时会调用Servlet 的destroy 方法。
```



```
 `HttpServlet`类中的`doHead()`方法是处理HTTP HEAD请求的方法。HTTP HEAD请求与HTTP GET请求非常相似，但是服务器不会返回任何响应正文，只返回响应头信息。这个方法通常被子类覆盖以提供自定义的响应头信息。

当客户端发送HTTP HEAD请求时，Servlet容器会调用`doHead()`方法。在这个方法中，子类可以设置响应头信息，但是不需要生成响应体。通常，`doHead()`方法只需要设置响应头信息，然后返回。

如果子类没有覆盖`doHead()`方法，则`doHead()`方法的默认实现将调用`doGet()`方法，这意味着服务器将返回与HTTP GET请求相同的响应头信息和响应状态码，但是不会返回响应正文。

总之，`doHead()`方法用于处理HTTP HEAD请求，它允许子类提供自定义的响应头信息。 
```



# == 源码

```java
1.javax.servlet   其中包含定义servlet和servlet容器之间契约的类和接口。

2.javax.servlet.http   其中包含定义HTTP Servlet 和Servlet容器之间的关系。

3.javax.servlet.annotation   其中包含标注servlet，Filter,Listener的标注。它还为被标注元件定义元数据。

4.javax.servlet.descriptor，其中包含提供程序化登录Web应用程序的配置信息的类型。
    
    
     
```

## 类关系

```java
--------------------------------------------------
session_tracking_mode
dispatcher_type 
servlet_container_initializer
servlet_security_element   
    
        
write_listener    
read_listener         
servlet_input_stream
servlet_output_stream    
    
    
//  类关系  =============   


//     
servlet, servlet-config ==> generic_servlet ==> httpServlet
registration ==> servlet_registration   

/*
	[listener,attribute_listener, event,  attribute_event]   
*/ 
servlet-context  
    
// [chain, config, registration]        
filter     
        
    
// 	[listener, wrapper, event, attribute_listener, attribute_event]    
servlet_request ==> http_servlet_request
servlet_response ==> http_servlet_response
Request_dispatcher          
// [i]=============
// [session_cookie_config， listener(activation,binding, id, x), context, event, bindding-event]
cookie
http_session  
```



## base

* base

  ```java
  // [i]=============
  	servlet          servlet 的基础接口，一个servlet需要实现该接口
  	servlet_config   sevlet 的配置， 比如解析的 web 中的参数
  
  	servlet_registration        
  	servlet_container_initializer
          
  	servlet_context  servlet 的上下文， 全局
  	servlet_context_listener        
  	servlet_context_attribute_listener
          
  	registration  
  // [c]=============        
  
  	servlet_context_event
  	servlet_context_attribute_event
  	servlet_security_element    
  // [ab]=============
  	servlet_input_stream
  	servlet_output_stream
      generic_servlet  // servlet 的通用实现  
  //  =============   
       servlet_exception
          
  
           
  ```

  

* request

  ```java
  // [i]=============
  
  	servlet_request
  	servlet_request_listener        
  	servlet_request_attribute_listener   
  	servlet_request_attribute_event 
  	request_dispatcher        
  // [c]=============        
  	servlet_request_event
  	servlet_request_wrapper  
          
  	dispatcher_type
          
          
  
  ```

  

* response

  ```java
  // [i]=============
  	servlet_response
          
  // [c]=============        
  	servlet_response_wrapper
  // [ab]=============
   
          
  //  =============    
  
  	
  ```

  

* session cookie

  ```
  	session_cookie_config
  	session_tracking_mode
  	
  ```

* 过滤器 + 监听器

  ```java
  // [i]=============
  	filter
      filter_chain
      filter_config
      filter_registration
  
      write_listener    
      read_listener        
  // [c]=============
  
  
  	
  
  ```

* 一些配置

  ```
  	local_strings.properties
  	local_strings_de.properties
  	local_strings_es.properties
  	local_strings_fr.properties
  	local_strings_ja.properties
  	local_strings_ko.properties
  	local_strings_zh__c_n.properties
  ```

  

* ds

```java
	async_context
	async_event
	async_listener
 
	http_constraint_element
	http_method_constraint_element
	multipart_config_element
 
	single_thread_model
	unavailable_exception

```

 

## annotation
```java
	handles_types
	http_constraint
	http_method_constraint
	multipart_config
	servlet_security
        
  	web_init_param
	web_filter
	web_listener
	web_servlet
```
## http

* servlet

  ```java
  	
  // [i] ==================
  	http_servlet_request
     	http_servlet_response
  // [c] ==================
     [ab]http_servlet   抽象类，至于处理什么样的请求，自己设计servlet实现
     http_servlet_request_wrapper
     http_servlet_response_wrapper
  	
  	http_upgrade_handler
  // [Del] ==================        
  	http_utils
  ```

  

* cookie session

  ```java
  	cookie
  	http_session        
  // [i] ==================
  	http_session
  	http_session_activation_listener 
  	http_session_binding_listener
  	http_session_id_listener
  	http_session_listener
          
  	[del]http_session_context
  // [c] ==================
  	cookie
      http_session_binding_event
      http_session_event
          
   
  ```

  

* 其他

```java
	part
	web_connection

     // ----------------------
	local_strings.properties
	local_strings_de.properties
	local_strings_es.properties
	local_strings_fr.properties
	local_strings_ja.properties
	local_strings_ko.properties
	local_strings_zh__c_n.properties
 
	
```



## jsp

```java
	error_data
	http_jsp_page
	jsp_application_context
	jsp_context
	jsp_engine_info
	jsp_exception
	jsp_factory
	jsp_page
	jsp_tag_exception
	jsp_writer
	package.html
	page_context
	skip_page_exception
```
### jsp.el
```java
	e_l_exception
	e_l_parse_exception
	expression
	expression_evaluator
	function_mapper
	implicit_object_e_l_resolver
	package.html
	scoped_attribute_e_l_resolver
	variable_resolver
```
### jsp.resources

```java
	jspxml.dtd
	jspxml.xsd
```

### jsp.tagext.doc-files

```java
	body_tag_protocol.gif
	iteration_tag_protocol.gif
	tag_protocol.gif
	variable_info-1.gif
```

### jsp.tagext

```java
	body_content
	body_tag
	body_tag_support
	dynamic_attributes
	function_info
	iteration_tag
	jsp_fragment
	jsp_id_consumer
	jsp_tag
	package.html
	page_data
	simple_tag
	simple_tag_support
	tag
	tag_adapter
	tag_attribute_info
	tag_data
	tag_extra_info
	tag_file_info
	tag_info
	tag_library_info
	tag_library_validator
	tag_support
	tag_variable_info
	try_catch_finally
	validation_message
	variable_info
```

## resources
```java
	datatypes.dtd
	j2ee_1_4.xsd
	j2ee_web_services_1_1.xsd
	j2ee_web_services_client_1_1.xsd
	javaee_5.xsd
	javaee_6.xsd
	javaee_7.xsd
	javaee_web_services_1_2.xsd
	javaee_web_services_1_3.xsd
	javaee_web_services_1_4.xsd
	javaee_web_services_client_1_2.xsd
	javaee_web_services_client_1_3.xsd
	javaee_web_services_client_1_4.xsd
	jsp_2_0.xsd
	jsp_2_1.xsd
	jsp_2_2.xsd
	jsp_2_3.xsd
	web-app_2_2.dtd
	web-app_2_3.dtd
	web-app_2_4.xsd
	web-app_2_5.xsd
	web-app_3_0.xsd
	web-app_3_1.xsd
	web-common_3_0.xsd
	web-common_3_1.xsd
	web-fragment_3_0.xsd
	web-fragment_3_1.xsd
	web-jsptaglibrary_1_1.dtd
	web-jsptaglibrary_1_2.dtd
	web-jsptaglibrary_2_0.xsd
	web-jsptaglibrary_2_1.xsd
	xml.xsd
	x_m_l_schema.dtd
```
## descriptor
```java
	jsp_config_descriptor
	jsp_property_group_descriptor
	taglib_descriptor
```
