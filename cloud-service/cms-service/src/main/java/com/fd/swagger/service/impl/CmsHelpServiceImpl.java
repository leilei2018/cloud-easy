package com.fd.swagger.service.impl;

import com.fd.cloud.basecomponent.ddl.impl.AbstractBaseServiceImpl;
import com.fd.swagger.dao.mapper.CmsHelpMapper;
import com.fd.swagger.dao.model.entity.CmsHelp;
import com.fd.swagger.service.CmsHelpService;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


import java.util.Random;

@Service
public class CmsHelpServiceImpl extends AbstractBaseServiceImpl<CmsHelp,Long, CmsHelpMapper>  implements CmsHelpService {
    private static Random r = new Random();
    @Autowired
    ApplicationContext applicationContext;
    @Autowired(required = false)
    private RedissonClient redissonClient;
}
