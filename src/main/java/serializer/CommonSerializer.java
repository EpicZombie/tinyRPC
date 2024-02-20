package serializer;

import com.esotericsoftware.kryo.KryoSerializable;
import com.fasterxml.jackson.databind.JsonSerializable;

public interface CommonSerializer {
    byte[] serialize(Object obj);
    Object deserialize(byte[] bytes,Class<?> clazz);
    int getCode();
    static CommonSerializer getByCode(int code){
        switch (code){
            case 1:
                return new JsonSerializer();
            case 0:
                return new KryoSerializer();
            case 2:
                return new HessianSerializer();
            default:
                return null;
        }
    }
}
