package com.meteor.common.network.netty.channel;

import com.meteor.common.network.netty.codec.ExchangeCodec;
import com.meteor.common.network.netty.handler.ClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class SocketClinetChannel extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new ExchangeCodec());
        pipeline.addLast(ClientHandler.getInstance());
    }
}