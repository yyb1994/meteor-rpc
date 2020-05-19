package com.meteor.test.common.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.meteor.common.config.annotation.MtProvider;
import com.meteor.common.core.BaseBean;
import com.meteor.common.util.ClasspathPackageScannerUtils;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ConfigurationBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class MtProviderAnnotationTest {
    private static final Log log = LogFactory.get(ClasspathPackageScannerUtils.class);
    //MtProviderAnnotation annotation = new MtProviderAnnotation();

    @Test
    public void getAnnotationClass() throws IOException {
        String packageName = "com.meteor.test";
        URL url = ClasspathPackageScannerUtils.getAbsPathUrl(packageName);
        if (url == null) {
            return;
        }
        String filePath = url.getPath();
        //URL url = new URL(resourceName(packageName));
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

    /**
     * https://blog.csdn.net/weixin_30765319/article/details/97853558
     * https://www.cnblogs.com/juncaoit/p/7591778.html
     * https://blog.csdn.net/weixin_30765319/article/details/97853558
     * https://blog.csdn.net/wuminghua59920/article/details/84717103
     *
     * @throws IOException
     */
    @Test
    public void testFileScanner() throws IOException {
        Set<Class> res = new HashSet<>();
        String packageName = "com.meteor.service";
        URL url = ClasspathPackageScannerUtils.getAbsPathUrl(packageName);
        if (url == null) {
            return;
        }
        String filePath = url.getPath();
        if (filePath == null) return;
        res = ClasspathPackageScannerUtils.getFileClasses(url, packageName);
//        File dir = new File(filePath);
//        String[] list = dir.list();
//        if (list == null) return;
//        for (String classPath : list) {
//            if (classPath.endsWith(".class")) {
//                classPath = classPath.replace(".class", "");
//                try {
//                    Class<?> aClass = Class.forName(packageName + "." + classPath);
//                    res.add(aClass);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                res.addAll(getClasses(packageName + "." + classPath));
//            }
//        }
        System.out.println(res.toString());
        for (Class cls:res){
            Annotation[] annotations = cls.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                log.info("通过class.getDeclaredAnnotations()获取全部的注解：" + annotation);
            }
        }
    }


}
