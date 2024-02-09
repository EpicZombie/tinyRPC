package message;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {

        // Unique identifier for the request
        private String requestId;

        private int statusCode;

        // The result of the method invocation
        private Object message;

        private T data;

        // Exception thrown by the method invocation
        private Exception exception;

        public static <T> RpcResponse<T> success(T data){
                RpcResponse<T> response = new RpcResponse<>();
                response.setStatusCode(200);
                response.setData(data);
                return response;
        }



}
