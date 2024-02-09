package client;

import message.RpcRequest;

public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);

}
