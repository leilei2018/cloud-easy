package com.fd.cloud.serviceapi.cms.dto.cmshelp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel("帮助添加Dto")
@Data
public class CmsHelpAddDto {
    private Long id;

    @NotNull(message = "所属分类id不能为空")
    @ApiModelProperty(value = "所属分类id",position = 2,required = true)
    private Long categoryId;


    @ApiModelProperty(value = "小图标icon",position = 3)
    private String icon;


    @ApiModelProperty(value = "标题",position = 4)
    private String title;


    @ApiModelProperty(value = "状态",position = 5)
    private Integer showStatus;


    @ApiModelProperty(value = "创建时间",position = 6)
    private Date createTime;


    @ApiModelProperty(value = "阅读次数",position = 7)
    private Integer readCount;


    @ApiModelProperty(value = "内容",position = 8)
    private String content;

    private String version;
    private Integer unlock;
}
