package com.meteor.common.network;


import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通用Handler处理
 * @author SuperMu
 * @time 2020-04-16
 */
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
