package com.meteor.common.config.annotation.impl;

import com.meteor.common.config.annotation.MtProvider;
import com.meteor.common.util.ClasspathScannerUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class MtProviderAnnotation {

    public static void scan(String... scanPackages) {
        Set<Class> classSetList = ClasspathScannerUtils.getClassList(scanPackages);
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

    public static Set<Class> getClassListByAnnotation(Set<Class> classSetList) {
        Set<Class> classSet = new HashSet<>();
        classSetList.forEach(cls -> {
            Annotation[] annotations = cls.getDeclaredAnnotations();
            for (Annotation an : annotations) {
                if (an instanceof MtProvider) {
                    classSet.add(cls);
                }
            }
        });
        return classSet;
    }


}
