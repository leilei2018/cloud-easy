package com.fd.starter;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import config.KyroRedisSerializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
@ConditionalOnClass(RedisCacheConfiguration.class)
@Conditional(RedisCacheCustomizerConfiguration.CacheOnRedisCondition.class)
/**
 * 自定义redis缓存配置，默认的是序列化jdkSerial,性能不佳，所以自定义(压缩大小10倍于jdk序列，3-5倍jackson/fastjson)
 */
public class RedisCacheCustomizerConfiguration {

    @Bean
    public RedisCacheConfiguration rccg(CacheProperties cacheProperties,
                                        ObjectProvider<RedisSerializer> serial){
        RedisCacheConfiguration rg =  RedisCacheConfiguration.defaultCacheConfig();
        rg = rg.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                //RedisSerializer.json()  用jkson通用的序列化，在redis数据和对象属性字段不相等时候，就会报错，但是用fastjson通用版就不会
                //new GenericFastJsonRedisSerializer()
                serial.getIfAvailable(()->new KyroRedisSerializer())
        ));

        Duration timeToLive = cacheProperties.getRedis().getTimeToLive();
        rg =  rg.entryTtl(timeToLive);
        //默认key的前缀是，缓存cache::,  即cache::${key}
        //rg = rg.prefixKeysWith("qaz");
        //如果设置了prefix,则变成  qaz${key} ,没什么必要而且不太好，默认使用对应的cacheName就好
        return rg;
    }

    @ConditionalOnProperty(prefix = "spring.cache.redis",name = "type",havingValue = "jdk")
    public JdkSerializationRedisSerializer jdkRedisSerial(){
        return new JdkSerializationRedisSerializer();
    }

    @ConditionalOnProperty(prefix = "spring.cache.redis",name = "type",havingValue = "jackson")
    public GenericJackson2JsonRedisSerializer genJacksonRedisSerial(){
        return new GenericJackson2JsonRedisSerializer();
    }

    @ConditionalOnProperty(prefix = "spring.cache.redis",name = "type",havingValue = "fastjson")
    public GenericFastJsonRedisSerializer genFastjsonRedisSerial(){
        return new GenericFastJsonRedisSerializer();
    }



    static class CacheOnRedisCondition extends AnyNestedCondition{
        public CacheOnRedisCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }
        @ConditionalOnProperty(prefix = "spring.cache",name = "type",havingValue = "redis")
        static class redis{

        }
        @ConditionalOnProperty(prefix = "spring.cache",name = "type",havingValue = "REDIS")
        static class upper_redis{

        }
    }
}
