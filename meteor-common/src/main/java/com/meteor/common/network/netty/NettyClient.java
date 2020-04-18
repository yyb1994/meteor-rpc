package com.meteor.common.network.netty;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.meteor.common.core.GlobalConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;

import java.net.InetSocketAddress;

import static com.meteor.common.network.netty.NettyEventLoopFactory.eventLoopGroup;
import static com.meteor.common.network.netty.NettyEventLoopFactory.socketChannelClass;

/**
 * netty client 客户端
 *
 * @author SuperMu
 * @time 2020-04-18
 */
public class NettyClient {
    private static final Log log = LogFactory.get(NettyClient.class);
    private Bootstrap bootstrap;
    private volatile Channel channel;
    private final ChannelHandler serverChannel;

    private final InetSocketAddress inetSocketAddress;

    public NettyClient(ChannelHandler serverChannel, InetSocketAddress inetSocketAddress) {
        this.serverChannel = serverChannel;
        this.inetSocketAddress = inetSocketAddress;
    }

    public void doOpen() throws Throwable {
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup(GlobalConstant.DEFAULT_IO_THREADS, "NettyClientWorker"))
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .channel(socketChannelClass())
                .handler(serverChannel);
    }

    public void doConnect() throws Throwable {
        ChannelFuture future = bootstrap.connect(inetSocketAddress);
        channel = future.channel();
        log.info("Successed connect to server " + channel.remoteAddress() + " from " + getClass().getSimpleName() + " "
                + ", channel is " + this.getChannel());
        channel.closeFuture().sync();
    }

    public boolean isConnected() {
        Channel channel = getChannel();
        if (channel == null) {
            return false;
        }
        return channel.isActive();
    }

    Channel getChannel() {
        return channel;
    }

}
