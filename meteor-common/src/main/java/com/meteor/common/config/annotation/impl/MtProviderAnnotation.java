package com.meteor.common.config.annotation.impl;

import com.meteor.common.config.annotation.MtProvider;
import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ConfigurationBuilder;

public class MtProviderAnnotation {

    public static void scan(String... scanPackages) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(scanPackages) // 指定路径URL
                .addScanners(new SubTypesScanner()) // 添加子类扫描工具
                .addScanners(new FieldAnnotationsScanner()) // 添加 属性注解扫描工具
                .addScanners(new MethodAnnotationsScanner()) // 添加 方法注解扫描工具
                .addScanners(new MethodParameterScanner()) // 添加方法参数扫描工具
                .addScanners(new TypeAnnotationsScanner()) // 添加类注解扫描工具
        );

        // 获取扫描到的标记注解的集合
        for (Class<?> c : reflections.getTypesAnnotatedWith(MtProvider.class)) {
            // 循环获取标记的注解
            MtProvider annotation = c.getAnnotation(MtProvider.class);

            // 打印注解中的内容
            System.out.println(annotation.interfaceName());
            System.out.println(c.getSimpleName());
        }
    }


}
