package com.meteor.common.network.netty.handler;


import com.meteor.common.log.LogUtils;
import com.meteor.common.log.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 消息网关
 *
 * @author SuperMu
 * @Date: 2018/8/22  16:01
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LogUtils.getLogger(ClientHandler.class);

    private static class SingletonHolder {
        private static final ClientHandler INSTANCE = new ClientHandler();
    }

    public static ClientHandler getInstance() {
        return SingletonHolder.INSTANCE;

    }

    public ClientHandler() {
    }

    /*连接成功发送登录消息*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接成功");
    }

    /*接收消息*/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}