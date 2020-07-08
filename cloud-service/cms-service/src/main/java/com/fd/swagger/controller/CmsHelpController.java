package com.fd.swagger.controller;

import com.fd.cloud.serviceapi.cms.dto.cmshelp.CmsHelpAddDto;
import com.fd.cloud.serviceapi.cms.dto.cmshelp.CmsHelpDto;
import com.fd.cloud.serviceapi.cms.dto.cmshelp.CmsHelpUpdateDto;
import com.fd.cloud.serviceapi.cms.inter.IFeignCmsHelpService;
import com.fd.cloud.serviceapi.common.support.BaseResult;
import com.fd.cloud.serviceapi.ums.inter.IFeignAdminService;
import com.fd.swagger.service.CmsHelpService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cmsHelp")
@Api(tags = "CMS帮助管理")
public class CmsHelpController implements IFeignCmsHelpService {
    @Autowired
    private CmsHelpService cmsHelpService;
    @Autowired
    private IFeignAdminService adminService;

    @Override
    public BaseResult list(CmsHelpDto dto) {
        adminService.list("");
        return null;
    }

    @Override
    public BaseResult insert(CmsHelpAddDto dto) {
        return null;
    }

    @Override
    public BaseResult findById(Long id) {
        return null;
    }

    @Override
    public BaseResult update(CmsHelpUpdateDto dto) {
        return null;
    }

    @Override
    public BaseResult delete(List<Long> ids) {
        return null;
    }
}
