package com.fd.swagger.service.impl;

import com.fd.cloud.basecomponent.ddl.impl.AbstractBaseServiceImpl;
import com.fd.swagger.dao.mapper.CmsHelpMapper;
import com.fd.swagger.dao.model.entity.CmsHelp;
import com.fd.swagger.service.CmsHelpService;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


import java.util.Random;

@Service
@CacheConfig(cacheNames = "cmshelp")
public class CmsHelpServiceImpl extends AbstractBaseServiceImpl<CmsHelp,Long, CmsHelpMapper>  implements CmsHelpService {
    private static Random r = new Random();
    @Autowired
    ApplicationContext applicationContext;
    @Autowired(required = false)
    private RedissonClient redissonClient;

    @Override
    @Cacheable(key = "#id")
    public CmsHelp findById(Long id) {
        return super.findById(id);
    }

    @Override
    @CacheEvict(key = "#id")
    public int deleteById(Long id) {
        //return super.deleteById(id);
        return 1;
    }

    @Override
    @CachePut(key = "#t.id")
    public CmsHelp put(CmsHelp t) {
        baseMapper.update(t);
        CmsHelp result = baseMapper.findById(t.getId());
        return result;
    }
}
