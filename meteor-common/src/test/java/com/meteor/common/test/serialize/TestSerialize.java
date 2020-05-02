package com.meteor.common.test.serialize;


import com.meteor.common.serialize.Serializer;
import com.meteor.common.serialize.json.FastJsonSerializer;
import com.meteor.common.serialize.kryo.KryoSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSerialize {
    Person person = null;
    Serializer serializer = null;

    @BeforeEach
    public void initData() {
        person = new Person("father", 31, "爱跑步");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("小明", 8, "爱看书，爱运动"));
        personList.add(new Person("小莉", 11, "爱跳舞，活泼开朗"));
        person.setSonList(personList);
    }

    @Test
    public void testFastJSON() throws Exception {
        serializer = new FastJsonSerializer();
        String str = new String(serializer.serialize(person));
        Person ser = serializer.deserialize(str.getBytes(), Person.class);
        System.out.println(str);

        Map<Integer, Person> personMap = new HashMap<>();
        personMap.put(1, person);
        byte[] bytes = serializer.serializeMap(personMap, Integer.class, Person.class);
        Map<Integer, Person> personMap2 = serializer.deserializeMap(bytes, Integer.class, Person.class);
        System.out.println();
    }


    @Test
    public void testKryo() throws Exception {
        serializer = new KryoSerializer();
        {
            byte[] bytes = serializer.serialize(person);
            Person father = serializer.deserialize(bytes, Person.class);
            checkSerialize(person, father);
        }

        {
            Map<Integer, Person> personMap = new HashMap<>();
            personMap.put(1, person);
            byte[] bytes2 = serializer.serializeMap(personMap, Integer.class, Person.class);
            Map<Integer, Person> personMap2 = serializer.deserializeMap(bytes2, Integer.class, Person.class);
            Assertions.assertNotNull(personMap2);
            Assertions.assertEquals(personMap.size(), personMap2.size());
            Assertions.assertEquals(personMap.get(0), personMap2.get(0));
        }
    }

    private void checkSerialize(Object before, Object after) {
        Assertions.assertNotNull(after);
        Assertions.assertEquals(before, after);
    }

}
