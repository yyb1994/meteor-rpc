package com.meteor.common.serialize.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meteor.common.core.StandardCharsets;
import com.meteor.common.serialize.Serializer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * fastjson 序列化和反序列化
 *
 * @author SuperMu
 * @Date 2018/10/18  22:53
 */
public class FastJsonSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        return JSON.toJSONString(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) {
        return JSON.parseObject(new String(data, StandardCharsets.UTF_8), cls);
    }

    @Override
    public <T> byte[] serializeList(List<T> obj, Class<T> cls) {
        return JSON.toJSONString(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> List<T> deserializeList(byte[] data, Class<T> cls) {
        return JSON.parseArray(new String(data, StandardCharsets.UTF_8), cls);
    }

    @Override
    public <K, V> byte[] serializeMap(Map<K, V> obj, Class<K> keyClas, Class<V> valCls) {
        return JSON.toJSONString(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <K, V> Map<K, V> deserializeMap(byte[] data, Class<K> keyCls, Class<V> valCls) {
        Map<K, V> re = JSON.parseObject(new String(data, StandardCharsets.UTF_8), Map.class);
        return re.entrySet().stream().collect(Collectors.toMap(
                kvEntry -> {
                    return kvEntry.getKey();
                }, kvEntry -> {
                    return JSONObject.toJavaObject((JSONObject) kvEntry.getValue(), valCls);
                }
        ));
    }

}

