package com.meteor.common.test.exchange;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.meteor.common.network.codec.ExchangeCodec;
import org.junit.jupiter.api.Test;

import static com.meteor.common.network.codec.ExchangeCodec.FLAG_EVENT;
import static com.meteor.common.network.codec.ExchangeCodec.FLAG_REQUEST;

public class ExchangeCodecTest {
    private static final Log log = LogFactory.get(ExchangeCodec.class);

    @Test
    public void testEncode() {
        byte flag = 0;
        flag |= FLAG_REQUEST;
        flag |= FLAG_EVENT;
        log.debug("flag:{}", flag);
        System.out.println(FLAG_REQUEST);
        int offset = 1;
        log.debug("{}右移{}位：{}", FLAG_REQUEST, offset, (FLAG_REQUEST >> offset));
        int d = flag & FLAG_REQUEST;
        System.out.println(d);
        System.out.println(d == 0);

    }
}
