package service;
import Object.HelloObject;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;


public class RpcServiceImpl implements RpcService{

    public static final Logger logger = LoggerFactory.getLogger(RpcServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.debug("接收到:{}",object.getMessage());
        return "调用的返回值:id=, " + object.getId();
    }
}
