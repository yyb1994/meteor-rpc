package com.meteor.common.network.exchange;

import com.meteor.common.network.netty.NettyClient;

import java.util.concurrent.CompletableFuture;

public class ExchangeChannelOperation implements ChannelOperation {
    NettyClient nettyClient;

    public ExchangeChannelOperation(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    public CompletableFuture<Object> send(Object request) {
        Request req = new Request();
        req.setVersion("1.0.0");
        req.setTwoWay(true);
        req.setData(request);
        DefaultFuture future = DefaultFuture.newFuture(req);
        nettyClient.send(req);
        return future;
    }
}
