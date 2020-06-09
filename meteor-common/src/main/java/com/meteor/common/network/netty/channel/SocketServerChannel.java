package com.meteor.common.network.netty.channel;

import com.meteor.common.network.netty.codec.ExchangeCodec;
import com.meteor.common.network.netty.handler.SocketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class SocketServerChannel extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new ExchangeCodec());
        pipeline.addLast(SocketHandler.getInstance());
    }
}