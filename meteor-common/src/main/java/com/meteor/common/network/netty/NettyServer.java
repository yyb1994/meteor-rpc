package com.meteor.common.network.netty;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.meteor.common.core.GlobalConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.InetSocketAddress;

/**
 * netty server启动类
 * @author SuperMu
 * @time 2020-04-17
 */
public class NettyServer {

    private static final Log log = LogFactory.get(NettyServer.class);

    private int socketPort;

    private ChannelHandler serverChannel;

    private io.netty.channel.Channel channel;

    private ServerBootstrap bootstrap;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;


    public void doOpen() throws Throwable {
        bossGroup = NettyEventLoopFactory.eventLoopGroup(1, "NettyServerBoss");
        workerGroup = NettyEventLoopFactory.eventLoopGroup(GlobalConstant.DEFAULT_IO_THREADS, "NettyServerWorker");
        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NettyEventLoopFactory.serverSocketChannelClass())
                .localAddress(new InetSocketAddress(socketPort))
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)//TCP/IP协议中针对TCP默认开启了Nagle算法。Nagle算法通过减少需要传输的数据包，来优化网络。在内核实现中，数据包的发送和接受会先做缓存，分别对应于写缓存和读缓存。 启动TCP_NODELAY，就意味着禁用了Nagle算法，允许小包的发送。
                .option(ChannelOption.SO_BACKLOG, 2048) // 服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(serverChannel);
        // 绑定端口，开始接收进来的连接
        ChannelFuture future = bootstrap.bind(socketPort).sync();

        log.info("Start " + getClass().getSimpleName() + " listen at " + socketPort);

        future.syncUninterruptibly();
        channel.closeFuture().sync();

    }

    public void doClose() {
        try {
            if (channel != null) {
                // unbind.
                channel.close();
            }
            if (bootstrap != null) {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        } catch (Throwable e) {
            log.warn(e.getMessage(), e);
        }

    }

    public boolean isBound() {
        return channel.isActive();
    }

}
