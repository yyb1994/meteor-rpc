package com.meteor.common.network.codec;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.meteor.common.network.protocol.PacketBase;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import com.meteor.common.core.StandardCharsets;
import java.util.List;

/**
 * netty 自定义协议
 *
 * @author SuperMu
 * @Date 2019/2/13  16:12
 */
public class SelfDefinedCodec extends ByteToMessageCodec<PacketBase> {
    private static final Log log = LogFactory.get();
    //头部信息的大小应该是 byte+byte+int = 1+1+4 = 6
    private static final int HEADER_SIZE = 6;

    @Override
    protected void encode(ChannelHandlerContext ctx, PacketBase msg, ByteBuf out) {
        try {
            if (msg == null) {
                throw new Exception("msg is null");
            }
            // 1.写入协议编号(byte类型)
            out.writeByte(msg.getPacketType());
            // 2.写入状态码(byte 类型)
            out.writeByte(msg.getCode());
            // 3.写入消息长度(int 类型)
            out.writeInt(msg.getContentLength());
            // 4.写入消息的内容(byte[]类型)
            if (msg.getData() != null) {
                byte[] data = msg.getData().getBytes(StandardCharsets.UTF_8);
                out.writeBytes(data);
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
                          List<Object> out) throws Exception {
        try {
            if (buffer.readableBytes() < HEADER_SIZE) {
                return;
            }

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
