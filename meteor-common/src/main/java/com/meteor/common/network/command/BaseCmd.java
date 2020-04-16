package com.meteor.common.network.command;

import org.springframework.beans.factory.InitializingBean;


public abstract class BaseCmd<KEY, DATA> implements Cmd<KEY, DATA>, InitializingBean {
    @Override
    public void afterPropertiesSet() {
        getCmdMapper().register(this);
    }

    public abstract CmdMapper<KEY, DATA> getCmdMapper();

}
