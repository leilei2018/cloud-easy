package com.fd.cloud.serviceapi.common.support.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@ApiModel("分页参数DTO")
@Data
public class PageQueryDto implements Serializable {
    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码(默认1)", dataType = "int")
    protected Integer pageNo;

    /**
     * 每页记录数
     */
    @ApiModelProperty(value = "每页记录数(默认10)", dataType = "int")
    protected Integer pageSize;
}
