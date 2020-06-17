package com.meteor.common.network.exchange;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * https://blog.csdn.net/e8714614luan/article/details/104049800
 *
 * @author SuperMu
 * @time 2020-06-01
 */
public class DefaultFuture extends CompletableFuture<Object> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultFuture.class);


    private static final Map<Long, DefaultFuture> FUTURES = new ConcurrentHashMap<>();

    private static final Map<Long, ChannelOperation> CHANNELS = new ConcurrentHashMap<>();


    private final Request request;

    private ChannelOperation channelOperation;

    public DefaultFuture(Request request) {
        this.request = request;
        FUTURES.put(request.getId(), this);
    }

    public DefaultFuture(Request request, ChannelOperation channelOperation) {
        this.request = request;
        this.channelOperation = channelOperation;
        FUTURES.put(request.getId(), this);
        CHANNELS.put(request.getId(), channelOperation);
    }

    public void doReceived(Response res) {
        if (res == null) {
            throw new IllegalStateException("response cannot be null");
        }
        if (res.getStatus() == Response.OK) {
            this.complete(res.getResult());
        }
    }

    public static DefaultFuture getFuture(long id) {
        return FUTURES.get(id);
    }

    public static DefaultFuture newFuture(Request request, ChannelOperation channelOperation) {
        final DefaultFuture future = new DefaultFuture(request, channelOperation);
        return future;
    }
}
