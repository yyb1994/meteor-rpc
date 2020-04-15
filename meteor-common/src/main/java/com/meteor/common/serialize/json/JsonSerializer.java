package com.meteor.common.serialize.json;

import com.alibaba.fastjson.JSON;
import com.meteor.common.serialize.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * fastjson 序列化和反序列化
 *
 * @author SuperMu
 * @Date 2018/10/18  22:53
 */
public class JsonSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) throws Exception {
        return JSON.toJSONString(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) throws Exception {
        return JSON.parseObject(new String(data, StandardCharsets.UTF_8), cls);
    }

    @Override
    public <T> byte[] serializeList(List<T> obj, Class<T> cls) throws Exception {
        return JSON.toJSONString(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> List<T> deserializeList(byte[] data, Class<T> cls) throws Exception {
        return JSON.parseArray(new String(data, StandardCharsets.UTF_8), cls);
    }

    @Override
    public <K, V> byte[] serializeMap(Map<K, V> obj, Class<K> keyClas, Class<V> valCls) throws Exception {
        return JSON.toJSONString(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <K, V> Map<K, V> deserializeMap(byte[] data, Class<K> keyCls, Class<V> valCls) throws Exception {
        return (Map<K, V>) JSON.parseObject(new String(data, StandardCharsets.UTF_8), Map.class);
    }

}

