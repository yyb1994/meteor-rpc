package com.meteor.demo.server.netty.handler;

import com.meteor.common.log.LogUtils;
import com.meteor.common.log.Logger;
import com.meteor.common.network.handler.BaseHandler;
import com.meteor.demo.server.netty.session.HallSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;


@ChannelHandler.Sharable
public class SocketHandler extends ChannelInboundHandlerAdapter implements BaseHandler<Object> {
    private static final Logger log = LogUtils.getLogger(SocketHandler.class);

    public static class SingletonHolder {
        private static final SocketHandler INSTANCE = new SocketHandler();
    }

    public static SocketHandler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * channel 存储
     */
    public final static AttributeKey<HallSession> SESSION_ATTRIBUTE_KEY = AttributeKey.newInstance("SocketHandler.HallSessionKey");


    //接收消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //log.debug("thread.name={}", Thread.currentThread().getName());
        Channel channel = ctx.channel();
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] bytes = new byte[buf.readableBytes()];
//        buf.readBytes(bytes);
//        String message = new String(bytes, "UTF-8");
        System.out.println(channel.remoteAddress() + ": " + "服务端收到的消息： " + msg);


        decodeMsg(ctx, msg);

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
        ctx.channel().close();
    }


    //处理消息
    @Override
    public void decodeMsg(ChannelHandlerContext ctx, Object msg) {

    }


    @Override
    public void initWhenConnected(ChannelHandlerContext ctx) { HallSession session = new HallSession();
        session.channel = ctx.channel();
        session.initTime = System.currentTimeMillis();
        session.sessionId = SESSION_ID_ATOMIC_INTEGER.incrementAndGet();

        ctx.channel().attr(SESSION_ATTRIBUTE_KEY).set(session);
    }

    @Override
    public void cleanWhenClosed(ChannelHandlerContext ctx) {
        HallSession session = ctx.channel().attr(SESSION_ATTRIBUTE_KEY).get();
        if (session != null) {
            onSessionClosed(session, ctx);
            ctx.channel().attr(SESSION_ATTRIBUTE_KEY).set(null);
        }
    }

    private void onSessionClosed(HallSession session, ChannelHandlerContext ctx) {

    }


}