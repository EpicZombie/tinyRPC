package serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import enumeration.SerializerCode;
import exception.SerializeException;
import message.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonSerializer implements CommonSerializer{

    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            logger.error("序列化时有错误发生：{}", e.getMessage());
            throw new SerializeException("序列化有错误发生");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        Object object = null;
        try {
            object = objectMapper.readValue(bytes,clazz);
            if(object instanceof RpcRequest){
                object = handleRequest(object);
            }
            return object;
        } catch (IOException e) {
            logger.error("反序列化时有错误发生：{}", e.getMessage());
            throw new SerializeException("反序列化时错误发生");
        }
    }
    /**
     * @description 由于使用JSON序列化和反序列化Object数组，无法保证反序列化后仍然为原实例类，需要重新判断处理
     * 因为都是Object类型，分不清
     * @param obj
     * @return [java.lang.Object]
     */
    private Object handleRequest(Object obj) throws IOException {
        RpcRequest rpcRequest = (RpcRequest)obj;
        for(int i = 0; i < rpcRequest.getParameterTypes().length;i++){
            Class<?> clazz = rpcRequest.getParameterTypes()[i];
            if(!clazz.isAssignableFrom(rpcRequest.getParameters()[i].getClass())){
                byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getParameters()[i]);
                rpcRequest.getParameters()[i] = objectMapper.readValue(bytes,clazz);//把不能赋值的按照clazz处理一下，防止int变成string之类的
            }
        }
        return rpcRequest;
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("JSON").getCode();
    }
}
