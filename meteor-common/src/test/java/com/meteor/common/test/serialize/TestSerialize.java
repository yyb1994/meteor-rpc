package com.meteor.common.test.serialize;


import com.meteor.common.serialize.kryo.KryoSerializer;
import com.meteor.common.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestSerialize {
    Person person = null;

    @BeforeEach
    public void initData() {
        Person person = new Person("father", 31, "爱跑步");
        Person son = new Person("son", 8, "爱看书");
        person.setSon(son);
    }


    @Test
    public void tesgSerialize() throws Exception {
        String jsonString = new String(new KryoSerializer().serialize(person));
        Person ser = JsonUtil.toObject(jsonString, Person.class);
        System.out.println(jsonString);

        Map<Integer, Person> personMap = new HashMap<>();
        personMap.put(1, person);
        String jsonMapString = JsonUtil.toJsonString(personMap);
        personMap = JsonUtil.toMap(jsonMapString, Integer.class, Person.class);
        System.out.println(jsonMapString);
    }
}
