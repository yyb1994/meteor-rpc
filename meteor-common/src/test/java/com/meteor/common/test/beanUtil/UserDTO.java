package com.meteor.common.test.beanUtil;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserDTO {
    private String name;
    private Integer age;
    private String remark;
    private List<Ability> abilityList;
    private UserDTO father;
    private Date birthday;

    @Data
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


}
