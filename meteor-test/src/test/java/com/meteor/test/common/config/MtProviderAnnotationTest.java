package com.meteor.test.common.config;

import com.meteor.common.config.annotation.MtProvider;
import com.meteor.common.core.BaseBean;
import com.meteor.common.util.ClasspathPackageUtils;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MtProviderAnnotationTest {
    //MtProviderAnnotation annotation = new MtProviderAnnotation();

    @Test
    public void getAnnotationClass() throws IOException {
        String packageName = "com.meteor.test";
        String filePath = ClasspathPackageUtils.getAbsPath(packageName);
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
     * @throws IOException
     */
    @Test
    public void testFileScanner() throws IOException {
        Set<Class> res = new HashSet<>();
        String packageName = "com.meteor.service";
        String filePath = ClasspathPackageUtils.getAbsPath(packageName);
        if (filePath == null) return;
        File dir = new File(filePath);
        String[] list = dir.list();
        if (list == null) return;
        for (String classPath : list) {
            if (classPath.endsWith(".class")) {
                classPath = classPath.replace(".class", "");
                try {
                    Class<?> aClass = Class.forName(packageName + "." + classPath);
                    res.add(aClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                res.addAll(getClasses(packageName + "." + classPath));
            }
        }
        System.out.println(res.toString());
    }

    //从包路径下扫描
    public static Set<Class> getClasses(String packagePath) {
        Set<Class> res = new HashSet<>();
        String path = packagePath.replace(".", "/");
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if (url == null) {
            System.out.println(packagePath + " is not exit");
            return res;
        }
        String protocol = url.getProtocol();
        if ("jar".equalsIgnoreCase(protocol)) {
            try {
                res.addAll(getJarClasses(url, packagePath));
            } catch (IOException e) {
                e.printStackTrace();
                return res;
            }
        } else if ("file".equalsIgnoreCase(protocol)) {
            res.addAll(getFileClasses(url, packagePath));
        }
        return res;
    }

    //使用JarURLConnection类获取路径下的所有类
    private static Set<Class> getJarClasses(URL url, String packagePath) throws IOException {
        Set<Class> res = new HashSet<>();
        JarURLConnection conn = (JarURLConnection) url.openConnection();
        if (conn != null) {
            JarFile jarFile = conn.getJarFile();
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String name = jarEntry.getName();
                if (name.contains(".class") && name.replaceAll("/", ".").startsWith(packagePath)) {
                    String className = name.substring(0, name.lastIndexOf(".")).replace("/", ".");
                    try {
                        Class clazz = Class.forName(className);
                        res.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return res;
    }

    //获取file路径下的class文件
    private static Set<Class> getFileClasses(URL url, String packagePath) {
        Set<Class> res = new HashSet<>();
        String filePath = url.getFile();
        File dir = new File(filePath);
        String[] list = dir.list();
        if (list == null) return res;
        for (String classPath : list) {
            if (classPath.endsWith(".class")) {
                classPath = classPath.replace(".class", "");
                try {
                    Class<?> aClass = Class.forName(packagePath + "." + classPath);
                    res.add(aClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                res.addAll(getClasses(packagePath + "." + classPath));
            }
        }
        return res;
    }
}
