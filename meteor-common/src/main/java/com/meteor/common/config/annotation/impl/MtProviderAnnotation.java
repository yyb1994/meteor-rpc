package com.meteor.common.config.annotation.impl;

import com.meteor.common.config.annotation.MtProvider;
import com.meteor.common.util.ClasspathPackageScannerUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.checkerframework.checker.units.qual.A;
import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class MtProviderAnnotation {

    public static void scan(String... scanPackages) {
        Set<Class> classSetList = ClasspathPackageScannerUtils.getClassList(scanPackages);
        Set<Class> annotationClassSetList = getClassListByAnnotation(classSetList);
        annotationClassSetList.forEach(t->{
            //获取类实现的接口 Interface
            Class[] interfaces = t.getInterfaces();
            if (interfaces.length==0){
                return;
            }
            Class interClass=interfaces[0];

            for (Class inter : interfaces) {

            }
        });

    }

    public static Set<Class> getClassListByAnnotation(Class<? extends Annotation> clA,Set<Class> classSetList) {
        Set<Class> classSet = new HashSet<>();
        classSetList.forEach(cls -> {
            Annotation[] annotations = cls.getDeclaredAnnotations();
            for (Annotation an : annotations) {
                if (an instanceof clA) {
                    classSet.add(cls);
                }
            }
        });
        return classSet;
    }


}
