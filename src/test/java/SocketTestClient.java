import RpcObject.HelloObject;
import client.RpcClientProxy;
import client.SocketClient;
import service.RpcService;


public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.01",9000);
        RpcClientProxy proxy = new RpcClientProxy(client);
        RpcService rpcService = proxy.getProxy(RpcService.class);
        HelloObject helloObject = new HelloObject(1,"HelloObjectMessage");
        String res = rpcService.hello(helloObject);
        System.out.printf(res+"\n");
    }
}