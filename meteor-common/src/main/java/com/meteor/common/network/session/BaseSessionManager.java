package com.meteor.common.network.session;


import com.meteor.common.network.protocol.PacketBase;

/**
 * session管理 基类
 *
 * @author SuperMu
 * @Date 2018/8/28  18:00
 */
public abstract class BaseSessionManager<T> {
    /**
     * 发送单个用户信息
     *
     * @param playerId 玩家Id/
     * @param message  消息内容
     */
    public abstract void write(Object playerId, Object message);

    /**
     * 发送全体信息
     *
     * @param message 消息内容
     */
    public abstract void writeToAllPlayer(PacketBase message);


    /**
     * 发送单个用户信息
     *
     * @param session 用户session
     * @param msg     消息内容
     */
    public abstract void sendMessage(T session, PacketBase msg);
}
