package com.meteor.common.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义ThreadFactory
 *
 * @author SuperMu
 * @time 2019-07-05
 */
public class NameThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    protected final boolean mDaemon;

    public NameThreadFactory() {
        this("pool-" + poolNumber.getAndIncrement(), false);
    }

    public NameThreadFactory(String threadName) {
        this(threadName, false);
    }

    public NameThreadFactory(String threadName, boolean daemon) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = threadName + "-" +
                poolNumber.getAndIncrement() +
                "-thread-";
        mDaemon = daemon;
    }

    public Thread newThread(Runnable runnable) {
        Thread t = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
        t.setDaemon(mDaemon);
        return t;
    }
}
