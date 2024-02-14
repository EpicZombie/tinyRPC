import registry.DefaultServiceRegistry;
import server.RpcServer;
import service.RpcService;
import service.RpcServiceImpl;

public class TestServer {

    public static void main(String[] args) {
        RpcService rpcService = new RpcServiceImpl();
        RpcServer rpcServer = new RpcServer(new DefaultServiceRegistry());
        rpcServer.start(rpcService,9000);
    }
}
