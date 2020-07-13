package config;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;
import lombok.extern.slf4j.Slf4j;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class KyroRedisSerializer implements RedisSerializer {

    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static CopyOnWriteArrayList<Class> registry = new CopyOnWriteArrayList<>();

    private boolean generic;

    Class clazz;

    public KyroRedisSerializer(){
        this(true,null);
    }
    public <T> KyroRedisSerializer(boolean generic,Class<T> clazz){
        this.generic = generic;
        this.clazz = clazz;
    }
    private static final ThreadLocal<Kryo> kryos = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            Kryo kryo = new Kryo();
            //引用，对A对象序列化时，默认情况下kryo会在每个成员对象第一次序列化时写入一个数字，
            // 该数字逻辑上就代表了对该成员对象的引用，如果后续有引用指向该成员对象，
            // 则直接序列化之前存入的数字即可，而不需要再次序列化对象本身。
            // 这种默认策略对于成员存在互相引用的情况较有利，否则就会造成空间浪费
            // （因为没序列化一个成员对象，都多序列化一个数字），
            // 通常情况下可以将该策略关闭，kryo.setReferences(false);
            kryo.setReferences(false);


            //不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）
            kryo.setRegistrationRequired(false);
            UnmodifiableCollectionsSerializer.registerSerializers(kryo);

            //设置初始化策略，如果没有默认无参构造器，那么就需要设置此项,使用此策略构造一个无参构造器
            kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }
    };

    public void regist(Class clazz){
        registry.add(clazz);
    }

    @Override
    public byte[] serialize(Object t) throws SerializationException {
        if (t == null) {
            return EMPTY_BYTE_ARRAY;
        }

        Kryo kryo = kryos.get();
        kryo.register(t.getClass());
        registry.add(t.getClass());

        try {
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Output output = new Output(baos);
             if(generic){
                 kryo.writeClassAndObject(output, t);
             }else{
                 kryo.writeObject(output,t);
             }
            output.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_BYTE_ARRAY;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }

        Kryo kryo = kryos.get();

        try (Input input = new Input(bytes)) {
            //return kryo.readClassAndObject(input);

            if(generic){
                for (Class type : registry){
                    kryo.register(type);
                }
                return kryo.readClassAndObject(input);
            }else{
                kryo.register(clazz);
                return kryo.readObject(input,clazz);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;

    }
}
