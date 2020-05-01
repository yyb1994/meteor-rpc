package com.meteor.common.test.serialize;


import com.meteor.common.core.StandardCharsets;
import com.meteor.common.serialize.Serializer;
import com.meteor.common.serialize.json.FastJsonSerializer;
import com.meteor.common.serialize.kryo.KryoSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestSerialize {
    Person person = null;
    Serializer serializer = null;

    @BeforeEach
    public void initData() {
        person = new Person("father", 31, "爱跑步");
        Person son = new Person("son", 8, "爱看书");
        person.setSon(son);
    }

    @Test
    public void tesgFastJSON() throws Exception {
        serializer = new FastJsonSerializer();
        String str = new String(serializer.serialize(person));
        Person ser = serializer.deserialize(str.getBytes(), Person.class);
        System.out.println(str);

        Map<Integer, Person> personMap = new HashMap<>();
        personMap.put(1, person);
        byte[] bytes = serializer.serializeMap(personMap, Integer.class, Person.class);
        Map<Integer, Person> personMap2 = serializer.deserializeMap(bytes, Integer.class, Person.class);

    }


    @Test
    public void tesgKryo() throws Exception {
        serializer = new KryoSerializer();
        byte[] bytes = serializer.serialize(person);
        String s = new String(bytes, StandardCharsets.UTF_8);
        Person ser = serializer.deserialize(bytes, Person.class);
        System.out.println(ser);

        Map<Integer, Person> personMap = new HashMap<>();
        personMap.put(1, person);
        byte[] bytes2 = serializer.serializeMap(personMap, Integer.class, Person.class);
        Map<Integer, Person> personMap2 = serializer.deserializeMap(bytes2, Integer.class, Person.class);

    }


}
