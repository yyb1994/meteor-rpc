package com.meteor.test.common.exchange;


import com.meteor.common.log.LogUtils;
import com.meteor.common.log.Logger;
import com.meteor.common.network.netty.codec.ExchangeCodec;
import com.meteor.common.network.exchange.Request;
import com.meteor.test.common.serialize.Person;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.meteor.common.network.netty.codec.ExchangeCodec.FLAG_HEART_BEAT_EVENT;
import static com.meteor.common.network.netty.codec.ExchangeCodec.FLAG_REQUEST;

public class ExchangeCodecTest {
    private static final Logger log = LogUtils.getLogger(ExchangeCodecTest.class);

    ExchangeCodec codec = new ExchangeCodec();

    @BeforeEach
    public void setUp() throws Exception {
        codec = new ExchangeCodec();
    }

    @Test
    public void testBinary() {
        byte flag = 0;
        flag |= FLAG_REQUEST;
        flag |= FLAG_HEART_BEAT_EVENT;
        log.debug("flag:{}", flag);
        System.out.println(FLAG_REQUEST);
        int offset = 1;
        log.debug("{}右移{}位：{}", FLAG_REQUEST, offset, (FLAG_REQUEST >> offset));
        int d = flag & FLAG_REQUEST;
        System.out.println(d);
        System.out.println(d == 0);

    }

    @Test
    public void test_Encode_Request() throws Exception {
        ChannelHandlerContext ctx = null;
        ByteBuf buf = Unpooled.buffer(1024);
        Request request = new Request();
        Person person = new Person();
        request.setData(person);

        codec.encode(ctx, request, buf);

        byte[] data = new byte[buf.writerIndex()];
        buf.readBytes(data);
        System.out.println();
    }
}
