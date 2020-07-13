package com.fd.swagger.controller;

import com.fd.cloud.serviceapi.cms.dto.cmshelp.CmsHelpAddDto;
import com.fd.cloud.serviceapi.cms.dto.cmshelp.CmsHelpDto;
import com.fd.cloud.serviceapi.cms.dto.cmshelp.CmsHelpUpdateDto;
import com.fd.cloud.serviceapi.cms.inter.IFeignCmsHelpService;
import com.fd.cloud.serviceapi.cms.vo.CmsHelpVo;
import com.fd.cloud.serviceapi.common.support.BaseResult;
import com.fd.cloud.serviceapi.ums.inter.IFeignAdminService;
import com.fd.common.util.CloudBeanUtils;
import com.fd.swagger.dao.model.entity.CmsHelp;
import com.fd.swagger.service.CmsHelpService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cmsHelp")
@Api(tags = "CMS帮助管理")
public class CmsHelpController implements IFeignCmsHelpService {
    @Autowired
    private CmsHelpService cmsHelpService;
    @Autowired
    private IFeignAdminService adminService;
    @Autowired
    private ApplicationContext ctx;

    @Override
    public BaseResult list(CmsHelpDto dto) {
        int a = 1/0;
        return null;
    }

    @Override
    public BaseResult insert(CmsHelpAddDto dto) {
        return null;
    }

    @Override
    public BaseResult findById(Long id) {
        CmsHelp byId = cmsHelpService.findById(id);
        return BaseResult.success(CloudBeanUtils.copyProperties(byId, CmsHelpVo.class));
    }

    @Override
    public BaseResult update(CmsHelpUpdateDto dto) {
        CmsHelp cmsHelp = CloudBeanUtils.copyProperties(dto, CmsHelp.class);
        return BaseResult.success(cmsHelpService.put(cmsHelp));
    }

    @Override
    public BaseResult delete(List<Long> ids) {
        return BaseResult.success(cmsHelpService.deleteById(ids.get(0)));
    }

    @GetMapping("/change")
    public void envChange(){
        ConfigurableEnvironment environment = (ConfigurableEnvironment)ctx.getEnvironment();
        Map<String, Object> maps = new HashMap<>();
        maps.put("logging.level.org.springframework","debug");
        environment.getPropertySources().addFirst(new MapPropertySource("test",maps));
        ctx.publishEvent(new EnvironmentChangeEvent(Collections.singleton("qaz")));
    }
}
