package com.fd.cloud.basecomponent.ddl;

import java.util.List;

public interface IBaseMapper<T,K> {
    T findById(K id);
    List<T> findByIds(List<K> id);

    List<T> findList(T t);

    K insert(T t);

    int update(T t);

    int deleteById(K K);
    int deleteByIds(List<K> ids);

    T queryList(T t);
}
