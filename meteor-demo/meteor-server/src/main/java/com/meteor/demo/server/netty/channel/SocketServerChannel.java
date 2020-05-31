package com.meteor.demo.server.netty.channel;

import com.meteor.common.network.codec.ExchangeCodec;
import com.meteor.demo.server.netty.handler.SocketHandler;
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