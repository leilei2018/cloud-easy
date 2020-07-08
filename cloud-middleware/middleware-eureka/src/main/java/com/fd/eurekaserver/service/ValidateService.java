package com.fd.eurekaserver.service;

import com.fd.eurekaserver.vo.LogVo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 在service层的校验：
 *
 * 1 @Validated和@Valid  都可以对基本类型进行校验
 *
 * 2：但是对象校验，只有@Valid支持校验，且需要定义在接口
 * @Validated的作用是可以提供分组校验
 *
 */
public interface ValidateService {

    public boolean vad1( LogVo uuid);
    public boolean vad4(@Valid @NotNull(message = "uuid不能为空d4") String uuid);
    public boolean vad2(@Valid LogVo logVo);
    public boolean vad3(@Validated LogVo logVo);

}
