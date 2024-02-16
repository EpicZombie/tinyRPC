package client;


import enumeration.ResponseCode;
import enumeration.RpcError;
import exception.RpcException;
import message.RpcRequest;
import message.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class SocketClient implements RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private final String host;
    private final int port;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object sendRequest(RpcRequest rpcRequest){
        try(Socket socket = new Socket(host,port)){
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeObject(rpcRequest);
            outputStream.flush();
            RpcResponse rpcResponse = (RpcResponse) inputStream.readObject();
            if(rpcResponse == null){
                logger.error("服务调用失败,service:{}" + rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE,"service:"+rpcRequest.getInterfaceName());
            }
            if(rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()){
                logger.error("服务调用失败,service:{},response:{}" + rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE,"service:"+rpcRequest.getInterfaceName());
            }
//            return inputStream.readObject();
            return rpcResponse.getData();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("调用时有错误发生"+e);
            throw new RpcException("服务调用失败:",e);
        }
    }
}
