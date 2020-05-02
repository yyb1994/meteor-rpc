package com.meteor.common.test.serialize;


import com.meteor.common.core.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class Person extends BaseBean {
    private String name;
    private int age;
    private String remark;
    private List<Person> sonList;

    public Person() {
    }

    public Person(String name, int age, String remark) {
        this.name = name;
        this.age = age;
        this.remark = remark;
    }


}
