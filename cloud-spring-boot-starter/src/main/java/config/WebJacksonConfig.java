package config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author pan
 * @since 2017-11-24
 */

@Configuration
@ConditionalOnProperty(prefix = "global",name = "webjackson",havingValue = "true",matchIfMissing = true)
public class WebJacksonConfig {

    private static String pattern;

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    public void setPattern(String pattern) {
        WebJacksonConfig.pattern = pattern;
    }

    @JsonComponent
    static class LocalDateTimeSerial extends  JsonSerializer<LocalDateTime>{
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(DateTimeFormatter.ofPattern(pattern)));
        }

    }



    @JsonComponent
    static class LocalDateTimeDeserial extends  JsonDeserializer<LocalDateTime>{
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(pattern));
        }
    }
}
