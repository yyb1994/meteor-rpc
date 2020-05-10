package com.meteor.test.common.beanUtil;

import com.alibaba.fastjson.JSON;
import com.meteor.common.util.BeanUtil;

import java.util.*;

import static com.meteor.common.util.BeanUtil.copyListProperties;


public class BeanUtilTest {
    public static void main(String[] args) {
        UserDTO son = new UserDTO();
        son.setAge(10);
        son.setName("小明");
        son.setRemark("小明是个好孩子");
        son.setBirthday(new Date(1496547005000L));
        UserDTO.Ability ability = new UserDTO.Ability();
        ability.setAbilityName("绘画");
        ability.setLevel(5);
        son.setAbilityList(Collections.singletonList(ability));

        UserDTO father = new UserDTO();
        father.setRemark("我是小明爸爸");
        father.setAge(30);
        father.setName("大明");
        father.setBirthday(new Date(793827005000L));
        UserDTO.Ability fatherAbility = new UserDTO.Ability();
        fatherAbility.setAbilityName("计算机");
        fatherAbility.setLevel(10);
        father.setAbilityList(Collections.singletonList(fatherAbility));
        son.setFather(father);


        Map<String, Class> classMap = new HashMap<>();
        classMap.put("abilityList", UserBO.Ability.class);
        classMap.put("father", UserBO.class);
        UserBO bo = BeanUtil.createFromProperties(son, UserBO.class, classMap);

        System.out.println(JSON.toJSONString(bo));

        List<UserBO> list1 = new ArrayList<>();
        UserBO userBO = new UserBO();
        userBO.setAge(10);
        list1.add(userBO);
        List<UserBO> list2 = copyListProperties(list1, UserBO.class);

        System.out.println(JSON.toJSONString(list2));
    }

}
