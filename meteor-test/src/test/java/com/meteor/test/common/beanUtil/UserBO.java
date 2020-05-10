package com.meteor.test.common.beanUtil;


import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Data
public class UserBO {
    private String name;
    private Integer age;
    private List<Ability> abilityList;
    private UserBO father;
    private LocalDateTime birthday;

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
