package com.meteor.common.util;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.meteor.common.serialize.json.GsonSerializer;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * JSON 工具类封装
 *
 * @author SuperMu
 * @time 2019-07-07
 */
public class JsonUtil {
    private static final Log log = LogFactory.get();

    /**
     * 对象转成String
     *
     * @param obj 对象
     * @param <T> 对象类型
     * @return String
     */
    public static <T> String toJsonString(T obj) {
        try {
            return new String(Objects.requireNonNull(toJsonByte(obj)));
        } catch (Exception e) {
            log.error(e);
        }

        return null;
    }

    /**
     * 对象转成byte[]
     *
     * @param obj 对象
     * @param <T> 对象类型
     * @return String
     */
    public static <T> byte[] toJsonByte(T obj) {
        try {
            return new GsonSerializer().serialize(obj);
        } catch (Exception e) {
            log.error(e);
        }

        return null;
    }


    /**
     * String 转成 Object
     *
     * @param data 原对象
     * @param cls  原对象class
     * @return T
     */
    public static <T> T toObject(String data, Class<T> cls) {
        return toObject(data.getBytes(StandardCharsets.UTF_8), cls);
    }

    /**
     * byte[] 转成 Object
     *
     * @param data 原对象
     * @param cls  原对象class
     * @return T
     */
    public static <T> T toObject(byte[] data, Class<T> cls) {
        try {
            return new GsonSerializer().deserialize(data, cls);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }


    /**
     * String 转成 Map
     *
     * @param data   原对象
     * @param keyCls 原对象key class
     * @param valCls 原对象val class
     * @return T
     */
    public static <K, V> Map<K, V> toMap(String data, Class<K> keyCls, Class<V> valCls) {
        return toMap(data.getBytes(StandardCharsets.UTF_8), keyCls, valCls);
    }

    /**
     * byte[] 转成 Map
     *
     * @param data   原对象
     * @param keyCls 原对象key class
     * @param valCls 原对象val class
     * @return T
     */
    public static <K, V> Map<K, V> toMap(byte[] data, Class<K> keyCls, Class<V> valCls) {
        try {
            return new GsonSerializer().deserializeMap(data, keyCls, valCls);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    /**
     * String 转成 List
     *
     * @param data 原对象
     * @param cls  原对象class
     * @return T
     */
    public static <T> List<T> toList(String data, Class<T> cls) {
        return toList(data.getBytes(StandardCharsets.UTF_8), cls);
    }

    /**
     * byte[] 转成 List
     *
     * @param data 原对象
     * @param cls  原对象class
     * @return T
     */
    public static <T> List<T> toList(byte[] data, Class<T> cls) {
        try {
            return new GsonSerializer().deserializeList(data, cls);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }


}
