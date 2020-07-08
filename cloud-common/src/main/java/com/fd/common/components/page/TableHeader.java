package com.fd.common.components.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 返回给前端的自定义列
 * @Author: WFZ
 * @CreateDate: 2019/7/5 10:42
 * @Version: 1.0
 */
@Data
public class TableHeader implements Serializable {

	private static final long serialVersionUID = -1669426780638887422L;

	@ApiModelProperty(name = "title", value = "列名")
	private String title;

	@ApiModelProperty(name = "field", value = "列对应的字段")
	private String field;

	public TableHeader() {
	}
	
	public TableHeader(String field, String title) {
		super();
		this.field = field;
		this.title = title;
	}

	/**
	 * 通过titles数组与fields数组构造一个List<TableHeader>
	 * @param titles 标题数组
	 * @param fields 属性名称数组
	 * @return List<TableHeader>
	 */
	public static List<TableHeader> crateTableHeaders(String[] titles, String[] fields){
		List<TableHeader> headers = new ArrayList<>();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			headers.add(new TableHeader(fields[i], titles[i]));
		}
		return headers;
	}
	
	/**
	 * 生成表头
	 * @param header
	 * @return
	 */
	public static List<TableHeader> crateTableHeaders(String[][] header){
		if(header==null || header.length==0) {
			return null;
		}
		List<TableHeader> headers = new ArrayList<>();
		for(int i=0;i<header.length;i++) {
			//字段
			String field="";
			//名称
			String title="";
			for(int j=0;j<header[i].length;j++) {
				if(j==0) {
					field=header[i][j];
				}else if(j==1) {
					title=header[i][j];
				}
			}
			headers.add(new TableHeader(field, title));
		}
		return headers;
	}
}
