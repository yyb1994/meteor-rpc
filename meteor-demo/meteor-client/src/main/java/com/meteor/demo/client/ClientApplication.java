package com.meteor.demo.client;

import com.meteor.client.netty.channel.SocketClinetChannel;
import com.meteor.common.network.exchange.Request;
import com.meteor.common.network.exchange.RpcInfo;
import com.meteor.common.network.netty.NettyClient;

import java.net.InetSocketAddress;

public class ClientApplication {
    public static void main(String[] args) throws Throwable {
        SocketClinetChannel clinetChannel = new SocketClinetChannel();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 12311);
        final NettyClient nettyClient = new NettyClient(clinetChannel, inetSocketAddress);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    nettyClient.doOpen();
                    nettyClient.doConnect();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        //thread.setDaemon(true);
        thread.start();

        Thread.sleep(2000L);
        Request request = new Request();
        RpcInfo rpcInfo = new RpcInfo();
        request.setData(rpcInfo);
        rpcInfo.setServiceName("com.meteor.service.goods.GoodsBatchViewService");
        rpcInfo.setMethodName("goodsBatchQuery");
        nettyClient.getChannel().writeAndFlush(request);
    }
}
