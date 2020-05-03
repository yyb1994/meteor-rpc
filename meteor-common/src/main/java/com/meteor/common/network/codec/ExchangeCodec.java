package com.meteor.common.network.codec;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.meteor.common.core.StandardCharsets;
import com.meteor.common.network.exchange.Request;
import com.meteor.common.network.exchange.Response;
import com.meteor.common.network.protocol.PacketBase;
import com.meteor.common.serialize.Serializer;
import com.meteor.common.serialize.kryo.KryoSerializer;
import com.meteor.common.util.Assert;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * 自定义编解码
 * http://dubbo.apache.org/zh-cn/blog/dubbo-protocol.html
 * 协议长度14位，flag(1),status(1),invoke id(8),body length(4),body
 * flag 从高到低，第一位表示request请求，第二位表示是心跳事件
 *
 * @author SuperMu
 * @time 2020-04-19
 */
public class ExchangeCodec extends ByteToMessageCodec<Object> {
    private static final Log log = LogFactory.get(ExchangeCodec.class);
    // header length.
    protected static final int HEADER_LENGTH = 14;
    // message flag.
    // request请求
    public static final byte FLAG_REQUEST = (byte) 0x80;
    // 心跳
    public static final byte FLAG_EVENT = (byte) 0x40;

    private static final Serializer serializer = new KryoSerializer();


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        Assert.notNull(out, "ByteBuf == null");
        if (msg instanceof Request) {
            //发送请求编码
            encodeRequest(ctx, (Request) msg, out);
        } else if (msg instanceof Response) {
            //接收请求编码
            encodeResponse(ctx, (Response) msg, out);
        }
    }

    protected void encodeRequest(ChannelHandlerContext ctx, Request msg, ByteBuf out) {
        // flag
        byte flag = 0;
        flag |= FLAG_REQUEST;
        if (msg.isEvent()) {
            flag |= FLAG_EVENT;
        }
        out.writeByte(flag);
        // status
        out.writeByte(0);
        //invoke id
        out.writeLong(msg.getId());
        //data
        if (msg.isEvent()) {
            out.writeInt(0);
        } else {
            //序列化消息体
            byte[] body = serializer.serialize(msg.getData());
            out.writeInt(body.length);
            out.writeBytes(body);
        }
    }

    protected void encodeResponse(ChannelHandlerContext ctx, Response res, ByteBuf out) {
        // flag
        byte flag = 0;
        if (res.isEvent()) {
            flag |= FLAG_EVENT;
        }
        out.writeByte(flag);
        // status
        // set response status.
        byte status = res.getStatus();
        out.writeByte(status);
        //invoke id
        out.writeLong(res.getId());
        if (res.getStatus() == Response.OK) {
            //data
            if (res.isEvent()) {
                out.writeInt(0);
            } else {
                //序列化消息体
                byte[] body = serializer.serialize(res.getResult());
                out.writeInt(body.length);
                out.writeBytes(body);
            }
        } else {
            //序列化消息体
            byte[] body = res.getErrorMessage().getBytes(StandardCharsets.UTF_8);
            out.writeInt(body.length);
            out.writeBytes(body);
        }

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
