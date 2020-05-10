package com.meteor.test.common.beanUtil;


import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;


public class UserDO {
    private String name;
    private Integer age;
    private List<Ability> abilityList;
    private UserDO father;
    private LocalDateTime birthday;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static class Ability {
        private String abilityName;
        private Integer level;

        public Ability() {
        }

        public Ability(String abilityName, Integer level) {
            this.abilityName = abilityName;
            this.level = level;
        }
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
       UserDO userDO = UserDO.class.getConstructor().newInstance();
        userDO.setAge(10);
    }
}
