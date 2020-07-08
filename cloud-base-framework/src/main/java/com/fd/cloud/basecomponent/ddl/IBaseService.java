package com.fd.cloud.basecomponent.ddl;


import com.fd.common.components.page.PagedResult;

import java.util.List;

public interface IBaseService<T,PK> {
    /**
     * @param entity
     * @return
     * @
     */
    public PK insert(T entity) ;

    public Integer update(T entity);

    /**
     * 按实体查询
     * @param entity
     * @return
     */
    public PagedResult<T> queryPageData(T entity) ;
    /**
     * 按实体查询
     * @param entity
     * @return
     */
    public List<T> findList(T entity) ;
    /**
     * 根据主健查询
     * @param id
     * @return
     */
    public T findById(PK id) ;

}
