package server;

public interface RpcServer {
    void start();
    void stop();
    void registerService(Object service);
    int getPort();
    boolean isRunning();
}
