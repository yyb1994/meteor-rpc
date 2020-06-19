package com.meteor.demo.client;

import com.meteor.common.network.exchange.ExchangeChannelOperation;
import com.meteor.common.network.exchange.RpcInfo;
import com.meteor.common.network.netty.NettyClient;
import com.meteor.common.network.netty.channel.SocketClinetChannel;
import com.meteor.common.rpc.DataResult;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

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

        boolean falg = false;
        while (!falg) {
            Thread.sleep(500);
            System.out.print("...");
            if (nettyClient.getChannel() != null && nettyClient.getChannel().isOpen()) {
                falg = true;
            }
        }


        RpcInfo rpcInfo = new RpcInfo();
        rpcInfo.setServiceName("ServiceBean:com.meteor.demo.service.goods.GoodsBatchViewService:1.0.0:mt");
        rpcInfo.setMethodName("goodsBatchQuery");
        rpcInfo.setParameterTypes(new Class[]{String.class});
        rpcInfo.setArguments(new Object[]{"It's,me"});
        ExchangeChannelOperation exchangeChannelOperation = new ExchangeChannelOperation(nettyClient);
        CompletableFuture<Object> future = exchangeChannelOperation.send(rpcInfo);

        Object result = future.get();
        if (result instanceof DataResult) {
            DataResult<?> dataResult = (DataResult<?>) result;
            System.out.println(dataResult.toString());
        }
    }
}
