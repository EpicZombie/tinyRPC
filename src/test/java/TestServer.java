import registry.DefaultServiceRegistry;
import registry.ServiceRegistry;
import server.RpcServer;
import service.RpcService;
import service.RpcServiceImpl;

public class TestServer {

    public static void main(String[] args) {
//        RpcService rpcService = new RpcServiceImpl();
//        RpcServer rpcServer = new RpcServer();
//        rpcServer.register(rpcService,9000);
//
//
        RpcService rpcService = new RpcServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(rpcService);
        RpcServer rpcServer2 = new RpcServer(serviceRegistry);
        rpcServer2.start(9000);
    }
}
