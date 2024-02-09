package message;

public class RpcRequestBuilder {
    private RpcRequest rpcRequest;

    public RpcRequestBuilder() {
        rpcRequest = new RpcRequest();
    }

    public RpcRequestBuilder getRpcRequestBuilder(){
        return this;
    }


    public RpcRequestBuilder interfaceName(String interfaceName) {
        rpcRequest.setInterfaceName(interfaceName);
        return this;
    }

    public RpcRequestBuilder methodName(String methodName) {
        rpcRequest.setMethodName(methodName);
        return this;
    }

    public RpcRequestBuilder parameterTypes(Class<?>[] parameterTypes) {
        rpcRequest.setParameterTypes(parameterTypes);
        return this;
    }

    public RpcRequestBuilder parameters(Object[] parameters) {
        rpcRequest.setParameters(parameters);
        return this;
    }

    public RpcRequestBuilder requestId(String requestId) {
        rpcRequest.setRequestId(requestId);
        return this;
    }

    public RpcRequest build() {
        return rpcRequest;
    }
}
