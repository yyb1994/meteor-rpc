package com.meteor.test.common.serialize;


import com.meteor.common.serialize.Serializer;
import com.meteor.common.serialize.json.FastJsonSerializer;
import com.meteor.common.serialize.json.GsonSerializer;
import com.meteor.common.serialize.kryo.KryoSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSerialize {
    Person person = null;

    @BeforeEach
    public void initData() {
        person = new Person("father", 31, "爱跑步",
                LocalDateTime.of(1987, 2, 27, 11, 10, 5));
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("小明", 8, "爱看书，爱运动",
                LocalDateTime.of(2010, 4, 11, 11, 10, 55)));
        personList.add(new Person("小莉", 11, "爱跳舞，活泼开朗",
                LocalDateTime.of(2007, 4, 11, 11, 10, 55)));
        person.setSonList(personList);
    }

    @Test
    public void testFastJSON() throws Exception {
        this.test(new FastJsonSerializer());
    }

    @Test
    public void testGson() throws Exception {
        this.test(new GsonSerializer());
    }


    @Test
    public void testKryo() throws Exception {
        this.test(new KryoSerializer());
    }

    private void test(Serializer serializer) {
        testSerialize(serializer);
        checkSerializeList(serializer);
        checkSerializeMap(serializer);
    }

    private void testSerialize(Serializer serializer) {
        byte[] bytes = serializer.serialize(person);
        Person father = serializer.deserialize(bytes, Person.class);
        Assertions.assertNotNull(father);
        Assertions.assertEquals(person, father);
    }

    private void checkSerializeList(Serializer serializer) {
        List<Person> beforePersonList = new ArrayList<>();
        beforePersonList.add(person);
        byte[] bytes = serializer.serializeList(beforePersonList, Person.class);
        List<Person> afterPersonList = serializer.deserializeList(bytes, Person.class);
        Assertions.assertNotNull(afterPersonList);
        Assertions.assertEquals(beforePersonList.size(), afterPersonList.size());
        Assertions.assertEquals(beforePersonList.get(0), afterPersonList.get(0));
    }

    private void checkSerializeMap(Serializer serializer) {
        Map<Integer, Person> beforeMap = new HashMap<>();
        beforeMap.put(1, person);
        byte[] bytes = serializer.serializeMap(beforeMap, Integer.class, Person.class);
        Map<Integer, Person> afterMap = serializer.deserializeMap(bytes, Integer.class, Person.class);
        Assertions.assertNotNull(afterMap);
        Assertions.assertEquals(beforeMap.size(), afterMap.size());
        Assertions.assertEquals(beforeMap.get(1), afterMap.get(1));
    }

}
