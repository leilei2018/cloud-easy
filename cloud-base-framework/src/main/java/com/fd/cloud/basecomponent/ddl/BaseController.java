package com.fd.cloud.basecomponent.ddl;

import com.fd.common.components.page.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 作为一个模板吧
 * @param <T>
 * @param <PK>
 * @param <S>
 */
@Api(tags = "")
public class BaseController<T,PK,S extends IBaseService<T,PK>> {
    @Autowired
    private S service;

    @ApiOperation("分页列表查询")
    @PostMapping("/list")
    public JsonResult list(@Validated @RequestBody T dto) {
        return JsonResult.ok(service.findList(dto));
    }
}
