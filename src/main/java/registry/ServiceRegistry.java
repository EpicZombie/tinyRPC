package registry;

public interface ServiceRegistry {

    /**
     * @description 把一个服务注册进注册表
     * @param service 带注册的服务实体
     * @param <T> 服务实体类
     */
    <T> void register(T service);

    Object getService(String serviceName);

    //TODO 这里改为T可以吗
}
