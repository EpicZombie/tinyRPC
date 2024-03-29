package client;

import message.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcClientProxy implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);
    private final RpcClient client;

    public RpcClientProxy(RpcClient client) {
        this.client = client;
    }

    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz},this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("调用方法:{}#",method.getDeclaringClass().getName(),method.getName());
        RpcRequest request = new RpcRequest(method.getDeclaringClass().getName(),method.getName(), method.getParameterTypes(),args,"");
//        return ((RpcResponse)rpcClient.sendRequest(rpcRequest,host,port)).getData();
        return client.sendRequest(request);
    }

}
