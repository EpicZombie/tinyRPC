import registry.DefaultServiceRegistry;
import registry.ServiceRegistry;
import server.SocketServer;
import service.RpcService;
import service.RpcServiceImpl;

public class SocketTestServer {

    public static void main(String[] args) {
//        RpcService rpcService = new RpcServiceImpl();
//        RpcServer rpcServer = new RpcServer();
//        rpcServer.register(rpcService,9000);
//
//
        RpcService rpcService = new RpcServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(rpcService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);
    }
}
