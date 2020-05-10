package com.meteor.test.common.beanUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodDemo {
    private int num = 2;
    private String str = "xixi";

    public String getStr() {
        return str;
    }

    public int addResult(int addNum) {
        return num + addNum;
    }

    @Override
    public String toString() {
        return "MethodDemo [num=" + num + ", str=" + str + "]";
    }

    public static void main(String[] args) throws Exception {
        //使用反射第一步:获取操作类MethodDemoFieldDemo所对应的Class对象
        Class<?> cls = Class.forName("com.seals.common.test.MethodDemo");
        //使用MethodDemo类的class对象生成 实例
        Object obj = cls.newInstance();

        //获取public int addResult(int addNum)方法
        Method addMethod = cls.getMethod("addResult", new Class[]{int.class});
        System.out.println("修饰符: " + Modifier.toString(addMethod.getModifiers()));
        System.out.println("返回值: " + addMethod.getReturnType());
        System.out.println("方法名称: " + addMethod.getName());
        System.out.println("参数列表: " + addMethod.getParameterTypes());
        int result = (int) addMethod.invoke(obj, 2);
        System.out.println("调用addResult后的运行结果:" + result);

        System.out.println("--------------------------------");

        //获取public String toString() 方法
        Method toStringMethod = cls.getMethod("toString", new Class[]{});
        System.out.println("修饰符: " + Modifier.toString(toStringMethod.getModifiers()));
        System.out.println("返回值: " + toStringMethod.getReturnType());
        System.out.println("方法名称: " + toStringMethod.getName());
        System.out.println("参数列表: " + toStringMethod.getParameterTypes());
        String msg = (String) toStringMethod.invoke(obj);
        System.out.println("调用toString后的运行结果:" + msg);

        //
        Method get = cls.getMethod("getStr", new Class[]{});
        String x = (String) get.invoke(obj);
        System.out.println(x);
    }
}