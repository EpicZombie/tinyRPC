package server;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

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
}
