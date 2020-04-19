package com.meteor.common.network.codec;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.meteor.common.core.StandardCharsets;
import com.meteor.common.network.exchange.Request;
import com.meteor.common.network.exchange.Response;
import com.meteor.common.network.protocol.PacketBase;
import com.meteor.common.util.Assert;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * 自定义编解码
 * http://dubbo.apache.org/zh-cn/blog/dubbo-protocol.html
 *
 * @author SuperMu
 * @time 2020-04-19
 */
public class ExchangeCodec extends ByteToMessageCodec<Object> {
    private static final Log log = LogFactory.get(ExchangeCodec.class);


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        Assert.notNull(out, "ByteBuf == null");
        if (msg instanceof Request) {
            //发送请求编码
            encodeRequest(ctx, msg, out);
        } else if (msg instanceof Response) {
            //接收请求编码
            encodeResponse(ctx, msg, out);
        }
    }

    protected void encodeRequest(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
    }

    protected void encodeResponse(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
                          List<Object> out) throws Exception {
        try {
//            if (buffer.readableBytes() < HEADER_SIZE) {
//                return;
//            }

            buffer.markReaderIndex();
            // 1.读取协议编号
            byte packetType = buffer.readByte();
            // 2.读取状态码
            byte code = buffer.readByte();
            // 3.读取消息体长度(data的长度)
            int contentLength = buffer.readInt();

            // TODO 如果dataLength过大，可能导致问题
            // TODO 对于拆包这种场景,由于还未读取到完整的消息,bufferIn.readableBytes() 会小于length,并重置bufferIn的readerIndex为0,然后退出,ByteToMessageDecoder会乖乖的等待下个包的到来。
            // TODO 由于第一次调用中readerIndex被重置为0,那么decode方法被调用第二次的时候,beginIndex还是为0的。
            // TODO https://blog.csdn.net/linsongbin1/article/details/77915686
            if (buffer.readableBytes() < contentLength) {
                buffer.resetReaderIndex();
                return;
            }

            // 4.读取消息体
            byte[] body = new byte[contentLength];
            buffer.readBytes(body);
            String content = new String(body, StandardCharsets.UTF_8);


            PacketBase packetBase = new PacketBase(packetType, code, content);
            out.add(packetBase);

        } catch (Exception e) {
            log.error(e);
        }

    }
}
