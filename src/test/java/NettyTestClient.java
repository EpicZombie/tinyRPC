import RpcObject.HelloObject;
import client.NettyClient;
import client.RpcClient;
import client.RpcClientProxy;
import service.RpcService;
import service.RpcServiceImpl;

public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient rpcClient = new NettyClient("127.0.0.1",9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient);
        RpcService rpcService = rpcClientProxy.getProxy(RpcService.class);
        HelloObject helloObject = new HelloObject(12,"netty style");
        String res = rpcService.hello(helloObject);
        System.out.println(res);
    }
}
