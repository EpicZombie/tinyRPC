package server;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.IOException;
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
            logger.debug("服务器正在启动..");
            Socket socket;
            while ((socket = serverSocket.accept()) != null){
                logger.debug("客户端连接,IP"+socket.getInetAddress());
                threadLocal.execute(new WorkerThread(socket,service));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    class WorkerThread implements Runnable{
        Socket socket;
        Object service;
        public WorkerThread(Socket socket, Object service) {

        }

        @Override
        public void run() {

        }
    }
}
