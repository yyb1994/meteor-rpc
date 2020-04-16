package com.meteor.common.network.protocol;

/**
 * 协议编号
 *
 * @author SuperMu
 * @time 2019-07-21
 */
public enum PacketType {

    //核心协议
    OFFLINE_NOTICE_PUSH((byte) 0x00, "下线通知"),
    PLAYER_LOGIN((byte) 0x01, "玩家登录"),
    TOURIST_LOGIN((byte) 0x02, "游客登录"),
    HEARTBEAT((byte) 0x03, "心跳包"),

    MESSAGE_PUSH((byte) 0x05, "消息推送"),


    ;


    private byte value;
    private String name;

    PacketType(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public static PacketType getPacketType(byte value) {
        for (PacketType packetType : PacketType.values()) {
            if (packetType.getValue() == value) {
                return packetType;
            }
        }
        return null;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
