package com.meteor.common.serialize;

import java.util.List;
import java.util.Map;

/**
 * 序列化和反序列化API
 *
 * @author SuperMu
 * @Date 2018/10/18  22:54
 */
public interface Serializer {
    public <T> byte[] serialize(T obj);

    public <T> T deserialize(byte[] data, Class<T> cls);

    public <T> byte[] serializeList(List<T> obj, Class<T> cls);

    public <T> List<T> deserializeList(byte[] data, Class<T> cls);

    public <K, V> byte[] serializeMap(Map<K, V> obj, Class<K> keyClas, Class<V> valCls);

    public <K, V> Map<K, V> deserializeMap(byte[] data, Class<K> keyCls, Class<V> valCls);
}
