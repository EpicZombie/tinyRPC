package client;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import message.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);
    public Object sendRequest(RpcRequest rpcRequest,String host,int port){
        try(Socket socket = new Socket(host,port)){
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeObject(rpcRequest);
            outputStream.flush();
            return inputStream.readObject();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用时有错误发生"+e);
            return null;
        }
    }


}
