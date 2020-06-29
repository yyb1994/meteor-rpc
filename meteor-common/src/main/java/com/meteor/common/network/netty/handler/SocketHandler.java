package com.meteor.common.network.netty.handler;

import com.meteor.common.log.LogUtils;
import com.meteor.common.log.Logger;
import com.meteor.common.network.exchange.Request;
import com.meteor.common.rpc.CommonInvoker;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


@ChannelHandler.Sharable
public class SocketHandler extends ChannelInboundHandlerAdapter implements BaseHandler<Object> {
    private static final Logger log = LogUtils.getLogger(SocketHandler.class);

    public static class SingletonHolder {
        private static final SocketHandler INSTANCE = new SocketHandler();
    }

    public static SocketHandler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private CommonInvoker commonInvoker = CommonInvoker.getInstance();


    //接收消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        if (msg instanceof Request) {
            Request request = (Request) msg;
            System.out.println(channel.remoteAddress() + ": " + "服务端收到的消息： " + request.getData().toString());

            commonInvoker.invoker(channel, request);
        }

    }


    //链接加入
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        initWhenConnected(ctx);
    }


    //链接关闭
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        cleanWhenClosed(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }


    //处理消息
    @Override
    public void decodeMsg(ChannelHandlerContext ctx, Object msg) {

    }


    @Override
    public void initWhenConnected(ChannelHandlerContext ctx) {

    }

    @Override
    public void cleanWhenClosed(ChannelHandlerContext ctx) {

    }


}