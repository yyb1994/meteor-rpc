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
package com.meteor.common.config.annotation;


import com.meteor.common.util.Assert;
import org.reflections.util.ClasspathHelper;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * 注解工具类
 *
 * @author SuperMu
 * @time 2020-05-27
 */
public class AnnotationBeanUtils {

    /**
     * 获取接口类
     * @param interfaceClass class
     * @return 接口类class
     */
    public static Class<?> findServiceInterfaceClass(Class<?> interfaceClass) {
        Assert.notNull(interfaceClass,
                "interfaceClass can not null!");

        //ClassLoader classLoader = interfaceClass != null ? interfaceClass.getClassLoader() : ClasspathHelper.contextClassLoader();
        Class<?>[] allInterfaces = ClassUtils.getAllInterfacesForClass(interfaceClass);

        if (allInterfaces.length> 0) {
            interfaceClass = allInterfaces[0];
        }

        Assert.notNull(interfaceClass,
                "@Service interfaceClass() or interfaceName() or interface class must be present!");

        Assert.isTrue(interfaceClass.isInterface(),
                "The annotated type must be an interface!");

        return interfaceClass;
    }


}
