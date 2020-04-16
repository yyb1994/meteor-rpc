package com.meteor.common.network.command;


public interface Cmd<KEY, DATA> {
    public void execute(DATA data);

    public KEY getKey();
}
