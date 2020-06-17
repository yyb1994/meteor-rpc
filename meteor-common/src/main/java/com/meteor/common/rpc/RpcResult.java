package com.meteor.common.rpc;

import com.meteor.common.network.exchange.RpcInfo;

import java.util.concurrent.CompletableFuture;

public class RpcResult {

    private RpcInfo rpcInfo;

    private CompletableFuture<RpcResponse> responseFuture;

    public CompletableFuture<RpcResponse> getResponseFuture() {
        return responseFuture;
    }

    public void setResponseFuture(CompletableFuture<RpcResponse> responseFuture) {
        this.responseFuture = responseFuture;
    }

    public RpcInfo getRpcInfo() {
        return rpcInfo;
    }

    public void setRpcInfo(RpcInfo rpcInfo) {
        this.rpcInfo = rpcInfo;
    }

    @Override
    public String toString() {
        return "RpcResult{" +
                "rpcInfo=" + rpcInfo +
                ", responseFuture=" + responseFuture +
                '}';
    }
}
