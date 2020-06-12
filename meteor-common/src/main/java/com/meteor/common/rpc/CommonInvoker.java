package com.meteor.common.rpc;

import com.meteor.common.core.CommonConstants;
import com.meteor.common.log.LogUtils;
import com.meteor.common.log.Logger;
import com.meteor.common.network.exchange.RpcInfo;
import io.netty.channel.Channel;

import java.lang.reflect.Method;

public class CommonInvoker {
    private static final Logger log = LogUtils.getLogger(CommonInvoker.class);

    public static class SingletonHolder {
        private static final CommonInvoker INSTANCE = new CommonInvoker();
    }

    public static CommonInvoker getInstance() {
        return CommonInvoker.SingletonHolder.INSTANCE;
    }

    public void invoker(Channel channel, RpcInfo rpcInfo) {
        String serviceName = rpcInfo.getServiceName();
        Class cls = CommonConstants.Server.REGISTER_SERVICE_MAP.get(serviceName);
        try {
            Method method = cls.getMethod(rpcInfo.getMethodName(), null);
            method.invoke(cls.newInstance());
            System.out.println();
        } catch (Exception e) {
            log.error(e);
        }
    }
}
