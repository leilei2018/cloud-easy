package com.fd.cloud.serviceapi.cms.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CmsHelpVo implements Serializable {
    /**  */
    private Long id;

    /** 所属分类id */
    private Long categoryId;

    /** 小图标icon */
    private String icon;

    /** 标题 */
    private String title;

    /** 状态 */
    private Integer showStatus;

    /** 创建时间 */
    private Date createTime;

    /** 阅读次数 */
    private Integer readCount;

    /** 内容 */
    private String content;
}
