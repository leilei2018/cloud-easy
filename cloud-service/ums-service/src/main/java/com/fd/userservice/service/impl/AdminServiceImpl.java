package com.fd.userservice.service.impl;

import com.fd.cloud.basecomponent.ddl.impl.AbstractBaseServiceImpl;
import com.fd.userservice.dao.mapper.AdminMapper;
import com.fd.userservice.dao.model.entity.Admin;
import com.fd.userservice.service.AdminService;
import org.springframework.stereotype.Service;
@Service
public class AdminServiceImpl extends AbstractBaseServiceImpl<Admin,Long, AdminMapper> implements AdminService {

}
