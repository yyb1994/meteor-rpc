package com.meteor.common.network.protocol;

import com.meteor.common.core.StandardCharsets;

/**
 * @author SuperMu
 * @description 协议标准格式
 * @Date 2018/5/11  16:32
 */
public class PacketBase {

    private byte packetType;//协议编号

    private byte code;//状态码 0x01 表示成功,默认为0x01,具体由服务端定义

    private int contentLength;//data utf-8格式下的长度

    private String data;//数据体

    public static final byte SUCCESS = 0x01;

    public static final byte ERROR = 0x02;


    public PacketBase() {
    }

    public PacketBase(PacketType packetType) {
        this.packetType = packetType.getValue();
        this.code = 0x01;
        this.contentLength = 0;
    }
    public PacketBase(PacketType packetType, String data) {
        this.packetType = packetType.getValue();
        this.data = data;
        this.code = 0x01;
        this.contentLength = data.getBytes(StandardCharsets.UTF_8).length;
    }

    public PacketBase(byte packetType, byte code, String data) {
        this.contentLength = data.getBytes(StandardCharsets.UTF_8).length;
        this.packetType = packetType;
        this.code = code;
        this.data = data;
    }

    public byte getPacketType() {
        return packetType;
    }

    public void setPacketType(byte packetType) {
        this.packetType = packetType;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }


    @Override
    public String toString() {
        return "PacketBase{" +
                "contentLength=" + contentLength +
                ", packetType=" + packetType +
                ", code=" + code +
                ", data='" + data + '\'' +
                '}';
    }
}
