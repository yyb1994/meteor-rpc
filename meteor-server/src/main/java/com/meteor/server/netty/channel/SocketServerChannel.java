package com.meteor.server.netty.channel;

import com.meteor.common.network.codec.SelfDefinedCodec;
import com.meteor.server.netty.handler.SocketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class SocketServerChannel extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new SelfDefinedCodec());
        pipeline.addLast(SocketHandler.getInstance());
    }
}