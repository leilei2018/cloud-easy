package com.fd.cloud.serviceapi.cms.inter;


import com.fd.cloud.serviceapi.cms.dto.cmshelp.CmsHelpAddDto;
import com.fd.cloud.serviceapi.cms.dto.cmshelp.CmsHelpDto;
import com.fd.cloud.serviceapi.cms.dto.cmshelp.CmsHelpUpdateDto;
import com.fd.cloud.serviceapi.common.support.BaseResult;
import com.fd.cloud.serviceapi.common.support.FacadeConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "cms-service"+ FacadeConstants.SERVICE_ENV, path = "/cmsHelp",configuration = {})
public interface IFeignCmsHelpService {
    /**
     * 列表查询
     *
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public BaseResult list(@Validated @RequestBody CmsHelpDto dto);

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @PostMapping("/insert")
    public BaseResult insert(@Validated @RequestBody CmsHelpAddDto dto);

    /**
     * 根据ID查询详情信息
     *
     * @return
     */
    @GetMapping("/info/{id}")
    public BaseResult findById(@PathVariable("id") Long id);

    /**
     * 修改更新
     *
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public BaseResult update(@Validated @RequestBody CmsHelpUpdateDto dto);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public BaseResult delete(@RequestBody List<Long> ids);
}
