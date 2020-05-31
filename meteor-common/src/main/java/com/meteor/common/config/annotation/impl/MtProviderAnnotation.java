package com.meteor.common.config.annotation.impl;

import com.meteor.common.config.annotation.AnnotationBeanUtils;
import com.meteor.common.config.annotation.MtProvider;
import com.meteor.common.config.annotation.ServiceBeanNameBuilder;
import com.meteor.common.core.CommonConstants;
import com.meteor.common.util.ClasspathScannerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes;

@Slf4j
public class MtProviderAnnotation {

    private final static List<Class<? extends Annotation>> SERVICE_ANNOTATION_LIST = asList(
            MtProvider.class
    );

    private final static Class<? extends Annotation> SERVICE_ANNOTATION = MtProvider.class;


    public static void registerBean(String... scanPackages) {
        Set<Class> classSetList = ClasspathScannerUtils.getClassList(scanPackages);
        Set<Class> annotationClassSetList = getClassListByAnnotation(classSetList);

        annotationClassSetList.forEach(t -> {
            registerBean(t);
        });

    }

    private static void registerBean(Class beanClass) {
        //获取实现类的接口类
        Class interClass = AnnotationBeanUtils.findServiceInterfaceClass(beanClass);
        Annotation annotation = AnnotationUtils.getAnnotation(beanClass, SERVICE_ANNOTATION);
        AnnotationAttributes serviceAnnotationAttributes = getAnnotationAttributes(annotation, false, false);
        String beanName = ServiceBeanNameBuilder.generateServiceBeanName(serviceAnnotationAttributes, interClass);
        //注入到Map中
        CommonConstants.Server.REGISTER_SERVICE_MAP.put(beanName, interClass);
        if (log.isInfoEnabled()) {
            log.info("The Bean[" + beanClass.getName() +
                    "] of ServiceBean has been registered with name : " + beanName);
        }

    }


    /**
     * 筛选指定注解的class
     * org.apache.dubbo.common.utils.ServiceAnnotationResolver#getServiceAnnotation(java.lang.Class<?>)
     * bean注册
     * org.apache.dubbo.config.spring.beans.factory.annotation.ServiceClassPostProcessor#registerServiceBeans
     * org.apache.dubbo.config.spring.beans.factory.annotation.ServiceClassPostProcessor#registerServiceBean
     *
     * @param classSetList
     * @return
     */
    public static Set<Class> getClassListByAnnotation(Set<Class> classSetList) {
        Set<Class> classSet = new HashSet<>();
        classSetList.forEach(cls -> {
            if (getAnnotation(cls)) {
                classSet.add(cls);
            }
        });
        return classSet;
    }


    private static Boolean getAnnotation(Class cls) {
        return cls.isAnnotationPresent(SERVICE_ANNOTATION);
        //  return serviceAnnotationTypes.stream().anyMatch(cls::isAnnotationPresent);
    }

}
