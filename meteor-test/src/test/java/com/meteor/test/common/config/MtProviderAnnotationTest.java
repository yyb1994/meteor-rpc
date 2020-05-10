package com.meteor.test.common.config;

import com.meteor.common.config.annotation.MtProvider;
import com.meteor.common.core.BaseBean;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ConfigurationBuilder;

import java.io.IOException;
import java.util.Set;

public class MtProviderAnnotationTest {
    //MtProviderAnnotation annotation = new MtProviderAnnotation();

    @Test
    public void getAnnotationClass() throws IOException {
        String packageName = "com.meteor.test";
//        System.out.println(ClasspathHelper.forPackage(packageName));
//        final ClassLoader[] loaders = ClasspathHelper.classLoaders();
//        if (loaders.length == 0) {
//            return;
//        }
//        ClassLoader classLoader = loaders[0];
//        final Enumeration<URL> urls = classLoader.getResources(resourceName(packageName));
//        URL enumeration = urls.nextElement();
//        String filePath = enumeration.getPath();
        //URL url=new URL(resourceName(packageName));
        // 扫包
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(packageName) // 指定路径URL
                //.addUrls(enumeration)
                .addScanners(new SubTypesScanner()) // 添加子类扫描工具
                .addScanners(new FieldAnnotationsScanner()) // 添加 属性注解扫描工具
                .addScanners(new MethodAnnotationsScanner()) // 添加 方法注解扫描工具
                .addScanners(new MethodParameterScanner()) // 添加方法参数扫描工具
                .addScanners(new TypeAnnotationsScanner()) // 添加类注解扫描工具
        );
        {
            // 反射出子类
            Set<Class<? extends BaseBean>> set = reflections.getSubTypesOf(BaseBean.class);
            System.out.println("getSubTypesOf:" + set);

        }
        Set<Class<?>> c = reflections.getTypesAnnotatedWith(MtProvider.class);
        System.out.println("getTypesAnnotatedWith:" + c);
    }

    @Test
    public void testRel() {
        Reflections reflections = new Reflections("org.reflections");
        Set<Class<? extends Scanner>> scannersSet = reflections.getSubTypesOf(Scanner.class);
        System.out.println();
    }

    private static String resourceName(String name) {
        if (name != null) {
            String resourceName = name.replace(".", "/");
            resourceName = resourceName.replace("\\", "/");
            if (resourceName.startsWith("/")) {
                resourceName = resourceName.substring(1);
            }
            return resourceName;
        }
        return null;
    }
}
