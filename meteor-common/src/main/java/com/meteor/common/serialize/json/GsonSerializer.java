package com.meteor.common.serialize.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meteor.common.core.StandardCharsets;
import com.meteor.common.serialize.Serializer;
import org.apache.commons.lang3.reflect.TypeUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Gson序列化方案
 * 性能测试证明，gson在数据量小和短的时候性能略微良好
 *
 * @author SuperMu
 * @time 2019-07-07
 */
public class GsonSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return gson.toJson(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) {
        return new Gson().fromJson(new String(data, StandardCharsets.UTF_8), cls);
    }

    @Override
    public <T> byte[] serializeList(List<T> obj, Class<T> cls) {
        return new Gson().toJson(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> List<T> deserializeList(byte[] data, Class<T> cls) {
        Type type = new ParameterizedTypeImpl(List.class, null, new Type[]{cls});
        return new Gson().fromJson(new String(data, StandardCharsets.UTF_8), type);
    }

    @Override
    public <K, V> byte[] serializeMap(Map<K, V> obj, Class<K> keyClas, Class<V> valCls) {
        return new Gson().toJson(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <K, V> Map<K, V> deserializeMap(byte[] data, Class<K> keyCls, Class<V> valCls) {
        Type type = new ParameterizedTypeImpl(Map.class, null, new Type[]{keyCls, valCls});
//        type = TypeBuilder
//                .newInstance(HashMap.class)
//                .addTypeParam(keyCls)
//                .addTypeParam(valCls)
//                .build();
        return (Map<K, V>) new Gson().fromJson(new String(data, StandardCharsets.UTF_8), type);
    }

    /**
     * 重新定义泛型实现
     */
    private static final class ParameterizedTypeImpl implements ParameterizedType {
        private final Class<?> raw;
        private final Type useOwner;
        private final Type[] typeArguments;

        /**
         * Constructor
         *
         * @param rawClass      原始类型
         * @param useOwner      要使用的所有者类型（如果有）
         * @param typeArguments 实际的泛型参数类型
         */
        private ParameterizedTypeImpl(final Class<?> rawClass, final Type useOwner, final Type[] typeArguments) {
            this.raw = rawClass;
            this.useOwner = useOwner;
            this.typeArguments = typeArguments;
        }


        @Override
        public Type getRawType() {
            return raw;
        }


        @Override
        public Type getOwnerType() {
            return useOwner;
        }


        @Override
        public Type[] getActualTypeArguments() {
            return typeArguments;
        }


        @Override
        public String toString() {
            return TypeUtils.toString(this);
        }


        @Override
        public boolean equals(final Object obj) {
            return obj == this || obj instanceof ParameterizedType && TypeUtils.equals(this, ((ParameterizedType) obj));
        }


        @Override
        public int hashCode() {
            int result = 71 << 4;
            result |= raw.hashCode();
            result <<= 4;
            result |= Objects.hashCode(useOwner);
            result <<= 8;
            result |= Arrays.hashCode(typeArguments);
            return result;
        }
    }
}