package com.fd.common.components.page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hepan
 */
@Api(tags = "分页结果集统一封装")
@Data
public class PagedResult<T> {
	/**
	 * 当前页
	 */
	@ApiModelProperty(name = "pageNo", value = "当前页")
	private int pageNo;
	/**
	 * 每页条数
	 */
	@ApiModelProperty(name = "pageSize", value = "每页条数")
	private int pageSize;
	/**
	 * 列表数据
	 */
	@ApiModelProperty(name = "records", value = "列表数据")
	private List<T> records;

	/**
	 * 总页面数
	 */
	@ApiModelProperty(name = "totalPage", value = "总页面数")
	private long totalPage;

	/**
	 * 总条数
	 */
	@ApiModelProperty(name = "total", value = "总条数")
	private long total;

	@ApiModelProperty(name = "tableHeaders", value = "表头field和name")
	private List<TableHeader> tableHeaders;

	public PagedResult() {

	}
}
