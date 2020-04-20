package com.meteor.common.test.exchange;

import static com.meteor.common.network.codec.ExchangeCodec.*;

public class CodecTest {
    public static void main(String[] args) {
        System.out.println(FLAG_REQUEST);
        System.out.println(FLAG_REQUEST>>5);
        System.out.println(FLAG_REQUEST | FLAG_TWOWAY);
        byte t = FLAG_REQUEST | FLAG_TWOWAY | FLAG_EVENT;
        System.out.println(t & FLAG_REQUEST);

    }
}
