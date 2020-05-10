package com.meteor.common.config.annotation.impl;

import com.meteor.common.config.annotation.MtProvider;
import lombok.Data;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.HashSet;
import java.util.Set;

public class MtProviderAnnotation {

    public static void scan(Set<String> scanPackages) {
        Set<Class<?>> set=new HashSet<>();
        scanPackages.forEach(t->{
//            if (StringUtils.isBlank(t)){
//                return;
//            }
            Reflections f = new Reflections(t);
            set.addAll(f.getTypesAnnotatedWith(Data.class));
        });

       // 获取扫描到的标记注解的集合
        for (Class<?> c : set) {
            // 循环获取标记的注解
            MtProvider annotation = c.getAnnotation(MtProvider.class);
            // 打印注解中的内容
            System.out.println(annotation.interfaceName());
        }
    }

    public static void main(String[] args) {
        Set<String> scanPackages = new HashSet<>();
        scanPackages.add("com.meteor.server");
        //MtProviderAnnotation.scan(scanPackages);

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.addUrls(ClasspathHelper.forPackage("com.meteor"));
        builder.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner(),
                new MethodAnnotationsScanner(), new FieldAnnotationsScanner());
        builder.filterInputsBy(new FilterBuilder().includePackage("com.meteor"));

        Reflections reflections = new Reflections(builder);
        System.out.println();
    }
}
