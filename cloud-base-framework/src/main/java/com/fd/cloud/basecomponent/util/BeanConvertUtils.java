package com.fd.cloud.basecomponent.util;



import com.fd.common.components.page.PagedResult;
import com.fd.common.components.page.TableHeader;
import com.github.pagehelper.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean转换工具类
 *
 * @author pan
 * @since 2017-11-16
 */
public class BeanConvertUtils {

    public static <T> PagedResult<T> toPagedResult(List<T> datas) {
        PagedResult<T> result = new PagedResult<T>();
        if (datas instanceof Page) {
            // 前端工程没有映入mybatis， page这个类没有，这里是否可以转成arraylist
            Page<T> page = (Page<T>) datas;
            result.setPageNo(page.getPageNum());// 当前页
            result.setPageSize(page.getPageSize());// 每页行数
            result.setTotal(page.getTotal());// 总记录数
            result.setTotalPage(page.getPages());// 总页数
            List<T> list = new ArrayList<>();
            list.addAll(page.getResult());
            result.setRecords(list);
            page = null;
        } else {
            result.setPageNo(1);// 当前页
            result.setPageSize(datas.size());// 每页行数
            result.setTotal(datas.size());// 总记录数
            result.setTotalPage(1);// 总页数
            result.setRecords(datas);
        }
        return result;
    }

    public static <T> PagedResult<T> toPagedResult(List<T> datas, List<TableHeader> tableHeader) {
        PagedResult<T> result = new PagedResult<T>();
        if (datas instanceof Page) {
            // 前端工程没有映入mybatis， page这个类没有，这里是否可以转成arraylist
            Page<T> page = (Page<T>) datas;
            result.setPageNo(page.getPageNum());// 当前页
            result.setPageSize(page.getPageSize());// 每页行数
            result.setTotal(page.getTotal());// 总记录数
            result.setTotalPage(page.getPages());// 总页数
            List<T> list = new ArrayList<>();
            list.addAll(page.getResult());
            result.setRecords(list);
            result.setTableHeaders(tableHeader);
            page = null;
        } else {
           // result.setCurrentPage(1);// 当前页
            result.setPageSize(datas.size());// 每页行数
            result.setTotal(datas.size());// 总记录数
            result.setTotalPage(1);// 总页数
            result.setRecords(datas);
            result.setTableHeaders(tableHeader);
        }
        return result;
    }
}
