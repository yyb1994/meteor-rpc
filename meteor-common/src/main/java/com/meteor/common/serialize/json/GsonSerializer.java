package com.meteor.common.serialize.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.meteor.common.serialize.Serializer;

import com.meteor.common.core.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Gson序列化方案
 * 性能测试证明，gson在数据量小和短的时候性能略微良好
 *
 * @author SuperMu
 * @time 2019-07-07
 */
public class GsonSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) throws Exception {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return gson.toJson(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) throws Exception {
        return new Gson().fromJson(new String(data, StandardCharsets.UTF_8), cls);
    }

    @Override
    public <T> byte[] serializeList(List<T> obj, Class<T> cls) throws Exception {
        return new Gson().toJson(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> List<T> deserializeList(byte[] data, Class<T> cls) throws Exception {
        return new Gson().fromJson(new String(data, StandardCharsets.UTF_8), new TypeToken<List<T>>() {
        }.getType());
    }

    @Override
    public <K, V> byte[] serializeMap(Map<K, V> obj, Class<K> keyClas, Class<V> valCls) throws Exception {
        return new Gson().toJson(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <K, V> Map<K, V> deserializeMap(byte[] data, Class<K> keyCls, Class<V> valCls) throws Exception {
        return (Map<K, V>) new Gson().fromJson(new String(data, StandardCharsets.UTF_8), new TypeToken<Map<K, V>>() {
        }.getType());
    }

}