package server;

import enumeration.ResponseCode;
import message.RpcRequest;
import message.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    public Object handle(RpcRequest rpcRequest, Object service){
        Object result = null;
        try{
            result = invokeTargetMethod(rpcRequest, service);
            System.out.printf("服务：{%s}成功调用方法：{%s}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        }catch (IllegalAccessException | InvocationTargetException e){
            System.out.println("调用或发送时有错误发生：" + e);
        }
        return result;
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest,Object service) throws InvocationTargetException, IllegalAccessException{
        Method method = null;
        try{
            //getClass()获取的是实例对象的类型
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
        }catch (NoSuchMethodException e){
//            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
            logger.info("调用或发送时有错误发生"+e);
        }
        return method.invoke(service, rpcRequest.getParameters());
    }
}




