
package message;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;

import java.io.Serializable;
import java.util.Arrays;

/**
 * RpcRequest represents the request for a remote procedure call.
 * It includes the necessary information for the server to identify
 * the method to be invoked and the parameters to be passed.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable {

    public static RpcRequestBuilder builder;
    public static RpcRequestBuilder builder(){
        builder = new RpcRequestBuilder();
        return builder;
    }

    // The name of the interface the remote method belongs to
    private String interfaceName;

    // The name of the method to be called
    private String methodName;

    // The types of the method parameters
    private Class<?>[] parameterTypes;

    // The actual parameters of the method call
    private Object[] parameters;

    // Unique identifier for the request
    private String requestId;

}
