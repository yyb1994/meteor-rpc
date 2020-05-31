package com.meteor.test.common.exception;

import com.meteor.common.util.StrFormatter;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

public class CommonExceptionTest {
    @Test
    public void testFormat() {
        String errorMsg = "今天星期：{0},天气：{1},气温：{2}°";
        String formatMsg = MessageFormat.format(errorMsg, "天", "晴", "29");
        System.out.println(formatMsg);
        errorMsg = "今天星期：{},天气：{},气温：{}°";
        formatMsg= StrFormatter.format(errorMsg,"天", "晴", "29");
        System.out.println(formatMsg);
    }
}
