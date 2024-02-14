package server;


import message.RpcRequest;
import message.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import registry.ServiceRegistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class RpcServer {

    public static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ExecutorService threadLocal;
    private RequestHandler requestHandler = new RequestHandler();
    private final ServiceRegistry serviceRegistry;
    public RpcServer(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        BlockingQueue<Runnable> workingQ = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadLocal = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE_TIME,TimeUnit.SECONDS,workingQ,threadFactory);
    }

    public void start (Object service,int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            logger.info("服务器启动...等待客户端连接...");
            Socket socket;
            while ((socket = serverSocket.accept()) != null){
                System.out.printf("客户端连接,IP:"+socket.getInetAddress()+":"+socket.getPort()+"\n");
                threadLocal.execute(new RequestHandler(socket,service));
            }
        } catch (IOException e) {
            System.out.printf("连接时有错误发生\n"+e);
        }

    }


}
