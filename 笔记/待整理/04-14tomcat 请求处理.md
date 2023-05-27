# tomcat 的请求流程

## 概述

* tomcat 是通过 mapper 数组来确定每个请求应该有哪个 wrapper 容器中的 servlet 处理的

* mapper 数组工作原理

  ```
  	mapper 组件中保存了 web 应用的配置信息，即 容器组件和访问路径的映射关系， 当一个请求到来的时候， mapper 组件通过解析请求 url 中的域名和路径，再到自己的 map 中去查询， 就能定位到一个 servlet.
  	一个请求 url 最后只会定位到一个 wrapper 容器，就是一个 servlet
  	
  ```

* 过程

  ```
  endpoint 监听端点，接收请求
  processor 处理
  coyota_adapter 
  	路径映射 mapper
  	获取第一个 value 执行↓
  =================================[协议解析]	
  enging 匹配获取 host
  host  匹配获取 context
  contxt 匹配 wrapper
  wrapper 构造 filter_chain
  filter_chain 执行各个 filter, 执行 servlet
  servlet
  ```

![image-20230414074701090](assets/image-20230414074701090.png)







































