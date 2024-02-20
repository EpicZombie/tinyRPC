import registry.DefaultServiceRegistry;
import registry.ServiceRegistry;
import server.NettyServer;
import server.RpcServer;
import service.RpcService;
import service.RpcServiceImpl;

public class NettyTestServer {

    public static void main(String[] args) {
        RpcService rpcService = new RpcServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(rpcService);
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(9999);
    }
}
