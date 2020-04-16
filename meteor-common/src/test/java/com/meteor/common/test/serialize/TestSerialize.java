package com.meteor.common.test.serialize;


import com.meteor.common.serialize.json.FastJsonSerializer;
import com.meteor.common.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class TestSerialize {
    public static void main(String[] args) throws Exception {
        tesgFastJson();
    }

    public static Person init() {
        Person person = new Person("father", 31, "爱跑步");
        Person son = new Person("son", 8, "爱看书");
        person.setSon(son);
        return person;
    }


    public static void tesgFastJson() throws Exception {
        Person person = init();
        String jsonString = new String(new FastJsonSerializer().serialize(person));
        Person ser = JsonUtil.toObject(jsonString, Person.class);
        System.out.println(jsonString);

        Map<Integer, Person> personMap = new HashMap<>();
        personMap.put(1, person);
        String jsonMapString = JsonUtil.toJsonString(personMap);
        personMap=JsonUtil.toMap(jsonMapString,Integer.class, Person.class);
        System.out.println(jsonMapString);
    }
}
