package com.meteor.demo.server;

import com.meteor.common.config.annotation.impl.MtProviderAnnotation;
import com.meteor.common.network.netty.NettyServer;
import com.meteor.common.network.netty.channel.SocketServerChannel;

/**
 * 启动器
 *
 * @author SuperMu
 * @time 2020-04-17
 */
public class ServerAppclication {
    public static void main(String[] args) {
        //扫描注解
        String sc = "com.meteor.demo.server.impl";
        MtProviderAnnotation.registerBean(sc);

        SocketServerChannel serverChannel = new SocketServerChannel();
        NettyServer server = new NettyServer(12311, serverChannel);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
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
