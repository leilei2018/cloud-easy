package com.fd.common.components.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 分页参数DTO
 * @author liubaoying
 * @date 2020/6/12
 */
@ApiModel("分页参数DTO")
@Data
public class PageParamDTO implements Serializable {

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

	public Integer getPageNo() {
		return pageNo == null ? 1:pageNo;
	}

	public Integer getPageSize() {
		return pageSize == null ? 10:pageSize;
	}
}
