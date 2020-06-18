package com.meteor.common.rpc;

import com.meteor.common.core.CommonConstants;
import com.meteor.common.log.LogUtils;
import com.meteor.common.log.Logger;
import com.meteor.common.network.exchange.Request;
import com.meteor.common.network.exchange.Response;
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

    public void invoker(Channel channel, Request request) {
        RpcInfo rpcInfo = (RpcInfo) request.getData();
        String serviceName = rpcInfo.getServiceName();
        Class<?> cls = CommonConstants.Server.REGISTER_SERVICE_MAP.get(serviceName);
        Response response = new Response();
        response.setId(request.getId());
        response.setHeartbeat(false);
        response.setVersion(request.getVersion());
        try {
            Object o = cls.newInstance();
            Method method = cls.getMethod(rpcInfo.getMethodName(), rpcInfo.getParameterTypes());
            Object result = method.invoke(o, rpcInfo.getArguments());
            response.setResult(result);
            channel.writeAndFlush(result);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
