import RpcObject.HelloObject;
import client.RpcClientProxy;
import service.RpcService;

import java.lang.reflect.Proxy;


public class TestClient {

    public static void main(String[] args) {

        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1",9000);
        RpcService rpcService = proxy.getProxy(RpcService.class);
        HelloObject helloObject = new HelloObject(1,"HelloObjectMessage");
        String res = rpcService.hello(helloObject);
        System.out.printf(res+"\n");

    }
}