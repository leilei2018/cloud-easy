package com.fd.userservice.controller;

import com.fd.cloud.serviceapi.cms.dto.cmshelp.CmsHelpDto;
import com.fd.cloud.serviceapi.cms.inter.IFeignCmsHelpService;
import com.fd.cloud.serviceapi.common.support.BaseResult;
import com.fd.cloud.serviceapi.ums.dto.AdminDto;
import com.fd.cloud.serviceapi.ums.inter.IFeignAdminService;
import com.fd.cloud.serviceapi.ums.support.exception.UmsException;
import com.fd.cloud.serviceapi.ums.vo.AdminVo;
import com.fd.userservice.dao.model.entity.Admin;
import com.fd.userservice.support.enums.UmsEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@Slf4j
public class AdminController implements IFeignAdminService {
    @Autowired
    IFeignCmsHelpService feignCmsHelpService;

    @Override
    public List<AdminVo> list(AdminDto dto) {
        //就是获取Admin的可读写字段，然后从dto中获取，并设置
        BeanUtils.copyProperties(dto,new Admin());
        //JSON.parseObject("",Admin.class);
        //adminService.findList(dto);

        CmsHelpDto cmsdto = new CmsHelpDto();
        cmsdto.setStartTime(dto.getStartTime());
        cmsdto.setTitle(dto.getName());
        BaseResult result = feignCmsHelpService.list(cmsdto);
        BaseResult.getResult(result);
        return null;
    }

    @Override
    public String list(String uuid) {
        if (uuid==null){
            throw new UmsException(UmsEnum.REQ_MESSAGE_NOT_READ);
        }
        return null;
    }
}
