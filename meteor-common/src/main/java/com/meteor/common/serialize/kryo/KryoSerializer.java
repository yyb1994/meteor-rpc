package com.meteor.common.serialize.kryo;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import com.meteor.common.serialize.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kryo序列化和反序列化
 *
 * @author SuperMu
 * @Date 2018/10/18  22:53
 */
public class KryoSerializer implements Serializer {

    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL =
            new ThreadLocal<Kryo>() {
                @Override
                protected Kryo initialValue() {
                    return new Kryo();
                }
            };


    @Override
    public <T> byte[] serialize(T obj) {
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        kryo.setReferences(false);
        kryo.register(obj.getClass(), new JavaSerializer());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, obj);
        output.flush();
        output.close();

        KRYO_THREAD_LOCAL.remove();

        byte[] b = baos.toByteArray();
        return b;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        kryo.setReferences(false);
        kryo.register(clazz, new JavaSerializer());

        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Input input = new Input(bais);
        Object o = kryo.readClassAndObject(input);
        KRYO_THREAD_LOCAL.remove();
        return (T) o;
    }

    @Override
    public <T> byte[] serializeList(List<T> obj, Class<T> cls) {
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(cls, new JavaSerializer());
        serializer.setElementsCanBeNull(false);

        kryo.register(cls, new JavaSerializer());
        kryo.register(ArrayList.class, serializer);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.flush();
        output.close();
        KRYO_THREAD_LOCAL.remove();
        byte[] b = baos.toByteArray();
        return b;
    }

    @Override
    public <T> List<T> deserializeList(byte[] data, Class<T> cls) {
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(cls, new JavaSerializer());
        serializer.setElementsCanBeNull(false);

        kryo.register(cls, new JavaSerializer());
        kryo.register(ArrayList.class, serializer);

        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Input input = new Input(bais);
        Object o = kryo.readObject(input, ArrayList.class, serializer);
        KRYO_THREAD_LOCAL.remove();

        return (List<T>) o;
    }

    @Override
    public <K, V> byte[] serializeMap(Map<K, V> obj, Class<K> keyClas, Class<V> valCls) {
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        MapSerializer serializer = new MapSerializer();
        serializer.setKeyClass(keyClas, new JavaSerializer());
        serializer.setKeysCanBeNull(false);
        serializer.setValueClass(valCls, new JavaSerializer());
        serializer.setValuesCanBeNull(true);

        kryo.register(valCls, new JavaSerializer());
        kryo.register(HashMap.class, serializer);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.flush();
        output.close();

        KRYO_THREAD_LOCAL.remove();

        byte[] b = baos.toByteArray();
        return b;
    }

    @Override
    public <K, V> Map<K, V> deserializeMap(byte[] data, Class<K> keyCls, Class<V> valCls) {
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        MapSerializer serializer = new MapSerializer();
        serializer.setKeyClass(keyCls, new JavaSerializer());
        serializer.setKeysCanBeNull(false);
        serializer.setValueClass(valCls, new JavaSerializer());
        serializer.setValuesCanBeNull(true);

        kryo.register(valCls, new JavaSerializer());
        kryo.register(HashMap.class, serializer);

        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Input input = new Input(bais);

        Object o = kryo.readObject(input, HashMap.class,
                serializer);

        KRYO_THREAD_LOCAL.remove();

        return (Map<K, V>) o;
    }

}
