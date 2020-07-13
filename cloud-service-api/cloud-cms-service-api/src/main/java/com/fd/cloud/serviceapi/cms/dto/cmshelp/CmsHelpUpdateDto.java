package com.fd.cloud.serviceapi.cms.dto.cmshelp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel("帮助添加Dto")
@Data
public class CmsHelpUpdateDto {
    @NotNull(message = "不能为空")
    @ApiModelProperty(value = "",position = 1,required = true)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;


    @ApiModelProperty(value = "阅读次数",position = 7)
    private Integer readCount;


    @ApiModelProperty(value = "内容",position = 8)
    private String content;
}
