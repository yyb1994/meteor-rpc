package com.meteor.test.common.rpc;

import com.meteor.demo.server.impl.GoodsBatchViewServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServiceInvokerTest {
    @Test
    public void methodInvoker() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class cls = GoodsBatchViewServiceImpl.class;
        Object o = cls.newInstance();
        Method method = cls.getMethod("goodsBatchQuery", null);
        Object result = method.invoke(o);
        System.out.println(result);
    }
}
