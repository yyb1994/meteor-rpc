package com.meteor.common.util;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.reflections.util.ClasspathHelper;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClasspathScannerUtils {
    private static final Log log = LogFactory.get(ClasspathScannerUtils.class);

    public static Set<Class> getClassList(String... scanPackages) {
        Set<Class> classSetList = new HashSet<>();
        for (String packageName : scanPackages) {
            URL url = ClasspathScannerUtils.getAbsPathUrl(packageName);
            if (url == null) {
                continue;
            }
            String filePath = url.getPath();
            if (filePath == null) {
                continue;
            }
            classSetList.addAll(ClasspathScannerUtils.getFileClasses(url, packageName));
        }
        return classSetList;
    }

    public static URL getAbsPathUrl(String path) {
        System.out.println(ClasspathHelper.forPackage(path));
//        final ClassLoader[] loaders = ClasspathHelper.classLoaders();
//        if (loaders.length == 0) {
//            return null;
//        }
//        ClassLoader classLoader = loaders[0];
        ClassLoader classLoader = ClasspathHelper.contextClassLoader();
        final Enumeration<URL> urls;
        try {
            urls = classLoader.getResources(resourceName(path));
            URL enumeration = urls.nextElement();
            //String filePath = enumeration.getPath();
            return enumeration;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String resourceName(String name) {
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
    public static Set<Class> getJarClasses(URL url, String packagePath) throws IOException {
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
    public static Set<Class> getFileClasses(URL url, String packagePath) {
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
