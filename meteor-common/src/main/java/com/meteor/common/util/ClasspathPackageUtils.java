package com.meteor.common.util;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.reflections.util.ClasspathHelper;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class ClasspathPackageUtils {
    private static final Log log = LogFactory.get(ClasspathPackageUtils.class);

    public static String getAbsPath(String path) {
        System.out.println(ClasspathHelper.forPackage(path));
//        final ClassLoader[] loaders = ClasspathHelper.classLoaders();
//        if (loaders.length == 0) {
//            return null;
//        }
//        ClassLoader classLoader = loaders[0];
        ClassLoader classLoader= ClasspathPackageUtils.class.getClassLoader();
        final Enumeration<URL> urls;
        try {
            urls = classLoader.getResources(resourceName(path));
            URL enumeration = urls.nextElement();
            String filePath = enumeration.getPath();
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
