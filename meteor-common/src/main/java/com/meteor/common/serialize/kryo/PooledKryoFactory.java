package com.meteor.common.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoPool;

public class  PooledKryoFactory extends AbstractKryoFactory {
    private final KryoPool pool;

    public PooledKryoFactory() {
        pool = new KryoPool.Builder(this).softReferences().build();
    }

    /**
     * 借/创建
     * @return
     */
    @Override
    public Kryo borrow() {
        return pool.borrow();
    }

    //释放
    @Override
    public void release(Kryo kryo) {
        pool.release(kryo);
    }

}
