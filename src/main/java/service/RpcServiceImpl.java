package service;
import RpcObject.HelloObject;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;


public class RpcServiceImpl implements RpcService{

    public static final Logger logger = LoggerFactory.getLogger(RpcServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        System.out.printf("接收到:"+object.getMessage()+"\n");
        return "调用的返回值:id=" + object.getId();
    }
}
