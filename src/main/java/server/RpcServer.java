package server;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import message.RpcRequest;
import message.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class RpcServer {
    private final ExecutorService threadLocal;
    public static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    public RpcServer() {
        int corePoolSize = 5;
        int maxPoolSize = 50;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQ = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadLocal = new ThreadPoolExecutor(corePoolSize,maxPoolSize,keepAliveTime,TimeUnit.SECONDS,workingQ,threadFactory);
    }

    public void register (Object service,int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.printf("服务器正在启动..\n");
            Socket socket;
            while ((socket = serverSocket.accept()) != null){
                System.out.printf("客户端连接,IP:"+socket.getInetAddress()+":"+socket.getPort()+"\n");
                threadLocal.execute(new RequestHandler(socket,service));
            }
        } catch (IOException e) {
            System.out.printf("连接时有错误发生\n");
        }

    }


}
