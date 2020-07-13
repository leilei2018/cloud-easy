package config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerProperties implements Serializable {

    private static final long serialVersionUID = 6671447915042314317L;
    private String basePackage;

    public static void main(String[] args) {
        KyroRedisSerializer k = new KyroRedisSerializer(false,SwaggerProperties.class);
        SwaggerProperties p = new SwaggerProperties();
        p.setBasePackage("qaz");

        byte[] bytes = k.serialize(p);
        SwaggerProperties deserialize = (SwaggerProperties)k.deserialize(bytes);
        System.out.println(deserialize);
    }
}
