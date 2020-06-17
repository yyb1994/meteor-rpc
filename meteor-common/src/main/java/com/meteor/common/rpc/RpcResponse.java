package com.meteor.common.rpc;

public class RpcResponse {
    private Object result;

    @Override
    public String toString() {
        return "RpcResponse{" +
                "result=" + result +
                '}';
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
