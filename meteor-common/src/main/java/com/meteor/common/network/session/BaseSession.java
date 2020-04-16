package com.meteor.common.network.session;

import io.netty.channel.Channel;

/**
 * session 基类
 *
 * @author SuperMu
 * @Date 2018/8/28  18:00
 */
public abstract class BaseSession {
    public long sessionId;

    public long initTime;

    public long lastActiveTime;

    public transient Channel channel;

    public boolean isWebSocket = false;

}
