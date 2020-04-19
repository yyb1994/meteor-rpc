package com.meteor.client.netty.channel;

import com.meteor.client.netty.handler.ClientHandler;
import com.meteor.common.network.codec.ExchangeCodec;
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