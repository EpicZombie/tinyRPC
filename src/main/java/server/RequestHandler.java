package server;

import enumeration.ResponseCode;
import lombok.extern.java.Log;
import message.RpcRequest;
import message.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class RequestHandler implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    Socket socket;
    Object service;

    public RequestHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
//            //反射找到远程需要调用的方法
//            Method method = service.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParameterTypes());
//            //invoke
//            Object returnObject  = method.invoke(service,rpcRequest.getParameters());
            Object returnObject = invokeMethod(rpcRequest);
            objectOutputStream.writeObject(RpcResponse.success(returnObject));
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private Object invokeMethod(RpcRequest request) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = Class.forName(request.getInterfaceName());//确保request是RpcRuest对象
        //判断是否为同一类型或者存在父子、接口关系
        if(!clazz.isAssignableFrom(service.getClass())){
            return RpcResponse.fail(ResponseCode.ClASS_NOT_FOUND);
        }
        Method method;
        try {
            method = service.getClass().getMethod(request.getMethodName(),request.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
        }
        return method.invoke(service,request.getParameters());
    }

}
