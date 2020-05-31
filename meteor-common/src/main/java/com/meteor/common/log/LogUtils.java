package com.meteor.common.log;

import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 * @author SuperMu
 * @time 2020-05-31
 */
public class LogUtils {
    /**
     * Get logger provider
     * @see io.netty.util.internal.logging.InternalLoggerFactory
     * @param clazz the returned logger will be named after clazz
     * @return logger
     */
    public static Logger getLogger(Class<?> clazz) {
        return new Slf4jLogger(LoggerFactory.getLogger(clazz));
    }
}
