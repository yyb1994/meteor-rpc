package com.meteor.common.core;

/**
 * 公共字典/常量
 * 接口定义常量与class 定义常量区别 https://www.cnblogs.com/EasonJim/p/7871753.html
 * @author SuperMu
 * @time 2019-07-06
 */
public interface CommonConstants {

    String PROVIDER = "provider";

    String CONSUMER = "consumer";


    int DEFAULT_IO_THREADS = Math.min(Runtime.getRuntime().availableProcessors() + 1, 32);

    String HEARTBEAT_EVENT = null;
}
