* bootstrap.main 
  * 启动类为什么要加锁
  * 为什么要破坏双亲委派责任制？ 是怎么实现的？
  
*  serialVersionUID 

  ```
   serialVersionUID 是 Java 序列化机制中用来控制版本一致性的一个标识符。在序列化和反序列化对象时，如果对象的 serialVersionUID 不一致，就会抛出 InvalidClassException 异常，表示对象版本不一致，无法进行反序列化操作。
  
  因此，为了保证对象的版本一致性，我们可以手动为类添加 serialVersionUID 属性，如果类发生了改变，我们需要手动更新 serialVersionUID 的值，以确保序列化和反序列化操作的正确性。如果不手动添加 serialVersionUID 属性，Java 会自动生成一个 serialVersionUID，但这样会导致在类发生改变时，自动生成的 serialVersionUID 可能会发生变化，导致序列化和反序列化失败。 
  ```

* softcache

  ```
  getObject（） 中的 issue 没有看懂， 2g
  ```

* cache -transaction-cahe 中的代码有问题