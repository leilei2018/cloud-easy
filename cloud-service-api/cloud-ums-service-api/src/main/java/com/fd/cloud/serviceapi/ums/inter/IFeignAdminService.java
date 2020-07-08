package com.fd.cloud.serviceapi.ums.inter;

import com.fd.cloud.serviceapi.common.support.FacadeConstants;
import com.fd.cloud.serviceapi.ums.dto.AdminDto;
import com.fd.cloud.serviceapi.ums.vo.AdminVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ums-service"+FacadeConstants.SERVICE_ENV)
public interface IFeignAdminService {

    @PostMapping("/admin/list")
    List<AdminVo> list(@Validated @RequestBody AdminDto dto);

    @GetMapping("/hello")
    String list(String uuid);

}
