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
public class RpcResponse implements Serializable {

        private static final long serialVersionUID = 1L;

        // Unique identifier for the request
        private String requestId;

        private int statusCode;

        // The result of the method invocation
        private Object result;

        // Exception thrown by the method invocation
        private Exception exception;

}
