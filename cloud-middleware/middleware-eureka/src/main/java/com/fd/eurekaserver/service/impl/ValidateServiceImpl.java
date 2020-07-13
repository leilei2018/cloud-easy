package com.fd.eurekaserver.service.impl;

import com.fd.eurekaserver.service.ValidateService;
import com.fd.eurekaserver.vo.LogVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "books")
public class ValidateServiceImpl implements ValidateService {

    @Override
    @Cacheable( key="#uuid.name",value = "vad") //spel expression
    /**
     * 如果是基本类型，则直接#key   --> String aa  #aa
     * 如果是对象，则#key.pros         Log vo     #vo.name
     */
    public LogVo vad1( LogVo uuid) {
        System.out.println("查询key:"+uuid.getName());

        LogVo qq = new LogVo();
        qq.setName(uuid.getName());
        return qq;
    }

    @Override
    public boolean vad2(LogVo logVo) {
        return false;
    }

    @Override
    public boolean vad3(LogVo logVo) {
        return false;
    }

    @Override
    public boolean vad4(String uuid) {
        return false;
    }

    public static void main(String[] args) {
        ValidateServiceImpl tt = new ValidateServiceImpl();
    }

}
