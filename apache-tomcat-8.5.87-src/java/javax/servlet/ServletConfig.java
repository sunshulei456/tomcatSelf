/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.servlet;

import java.util.Enumeration;

/**
 * <p>
 *     简单理解就是从 web.xml 解析出来的配置信息都会封装到该类中
 * </p>
 * A servlet configuration object used by a servlet container to pass
 * information to a servlet during initialization.
 */
public interface ServletConfig {

    /**
     * <p>
     *     返回Servlet的名字，即web.xml文件中相应<servlet>元素的<servlet-name>子元素的值。
     *     如果没有为Servlet配置<serlvet-name>子元素，则返回Servlet类的名字。
     * </p>
     * Returns the name of this servlet instance. The name may be provided via
     * server administration, assigned in the web application deployment
     * descriptor, or for an unregistered (and thus unnamed) servlet instance it
     * will be the servlet's class name.
     *
     * @return the name of the servlet instance
     */
    String getServletName();

    /**
     * <p>
     *     取Servlet 上下文对象(非常重要)
     * </p>
     * Returns a reference to the {@link ServletContext} in which the caller is
     * executing.
     *
     * @return a {@link ServletContext} object, used by the caller to interact
     *         with its servlet container
     * @see ServletContext
     */
    ServletContext getServletContext();

    /**
     * <p>
     *     据给定的初始化参数名，返回 init-param 中的配置参数
     *     <br/>
     *     就是 web.xml 中 <init-param> 标签 中的
     * </p>
     *
     * Returns a <code>String</code> containing the value of the named
     * initialization parameter, or <code>null</code> if the parameter does not
     * exist.
     *
     * @param name
     *            a <code>String</code> specifying the name of the
     *            initialization parameter
     * @return a <code>String</code> containing the value of the initialization
     *         parameter
     */
    String getInitParameter(String name);

    /**
     * <p>
     *     返回一个Enumeration对象，获取配置的所有init-param 名字集合
     *     <br/>
     *     就是 web.xml 中 <init-param> 标签 中
     * </p>
     * Returns the names of the servlet's initialization parameters as an
     * <code>Enumeration</code> of <code>String</code> objects, or an empty
     * <code>Enumeration</code> if the servlet has no initialization parameters.
     *
     * @return an <code>Enumeration</code> of <code>String</code> objects
     *         containing the names of the servlet's initialization parameters
     */
    Enumeration<String> getInitParameterNames();
}
