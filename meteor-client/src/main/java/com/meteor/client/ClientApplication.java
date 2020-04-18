package com.meteor.client;

import com.meteor.client.netty.channel.SocketClinetChannel;
import com.meteor.common.network.netty.NettyClient;

import java.net.InetSocketAddress;

public class ClientApplication {
    public static void main(String[] args) throws Throwable {
        SocketClinetChannel clinetChannel = new SocketClinetChannel();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 12311);
        NettyClient nettyClient = new NettyClient(clinetChannel, inetSocketAddress);
        nettyClient.doOpen();
        nettyClient.doConnect();
    }
}
