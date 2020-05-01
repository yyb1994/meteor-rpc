package com.meteor.common.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * KryoFactory抽象类
 * https://www.jianshu.com/p/f56c9360936d
 * https://www.cnblogs.com/benwu/articles/4826268.html
 * @author SuperMu
 * @time 2020-04-28
 */
public abstract class AbstractKryoFactory implements KryoFactory {

    public abstract Kryo borrow() ;
    public abstract void release(Kryo kryo);

    @Override
    public Kryo create() {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);

        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(
                new StdInstantiatorStrategy()));
        return kryo;
    }
}
