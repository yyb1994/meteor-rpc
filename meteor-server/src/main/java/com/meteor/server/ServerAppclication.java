package com.meteor.server;

import com.meteor.common.config.annotation.impl.MtProviderAnnotation;
import com.meteor.common.network.netty.NettyServer;
import com.meteor.server.netty.channel.SocketServerChannel;

/**
 * 启动器
 *
 * @author SuperMu
 * @time 2020-04-17
 */
public class ServerAppclication {
    public static void main(String[] args) {
        //扫描注解
        String sc = "com.meteor.service";
        MtProviderAnnotation.scan(sc);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SocketServerChannel serverChannel = new SocketServerChannel();
                NettyServer server = new NettyServer(12311, serverChannel);
                try {
                    server.doOpen();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        //thread.setDaemon(true);
        thread.start();
        while (true) {

        }
    }
}
