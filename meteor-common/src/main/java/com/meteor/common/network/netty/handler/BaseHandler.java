package com.meteor.common.network.netty.handler;


import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicInteger;


public abstract interface BaseHandler<T> {

    //自增Session_id
    final static AtomicInteger SESSION_ID_ATOMIC_INTEGER = new AtomicInteger(0);

    //处理消息
    void decodeMsg(ChannelHandlerContext ctx, T msg);

    //连接加入处理操作
    void initWhenConnected(ChannelHandlerContext ctx);

    //连接关闭操作
    void cleanWhenClosed(ChannelHandlerContext ctx);
}
