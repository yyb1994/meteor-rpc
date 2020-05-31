package com.meteor.common.log;

import com.meteor.common.util.StrFormatter;

/**
 * 抽象日志类
 */
public abstract class AbstractLog {
    protected String appendMessage(String msg) {
        return " [METEOR] " + msg;
    }

    protected String appendMessage(String format, Object... arguments) {
        return appendMessage(StrFormatter.format(format, arguments));
    }
}
