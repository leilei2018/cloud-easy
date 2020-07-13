package com.fd.cloud.basecomponent.ddl.impl;

import com.fd.cloud.basecomponent.ddl.IBaseMapper;
import com.fd.cloud.basecomponent.ddl.IBaseService;
import com.fd.cloud.basecomponent.util.BeanConvertUtils;
import com.fd.common.components.page.PagedResult;
import com.fd.common.util.ReflectUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBaseServiceImpl<T,K,M extends IBaseMapper<T,K>> implements IBaseService<T,K> {

    static Map<Class,Object> returnTypeRegistry = new HashMap<>();

    static{
        returnTypeRegistry.put(Long.class,1L);
        returnTypeRegistry.put(Integer.class,1);
    }

    @Autowired
    public M baseMapper;

    Class<T> clazz;
    Class clazzPk;

    public AbstractBaseServiceImpl() {
        ParameterizedType parameterizedType = (ParameterizedType)getClass().getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        clazz = (Class)actualTypeArguments[0];
        clazzPk = (Class)actualTypeArguments[1];
    }

    @Override
    public K insert(T t)  {
        baseMapper.insert(t);

        Field pkField = ReflectUtil.getPkField(clazz, Primary.class);
        if (pkField!=null) {
            Object fieldValue = ReflectUtil.getFieldValue(t, pkField.getName(), pkField.getType());
            return (K)fieldValue;
        }
        return (K)returnTypeRegistry.get(clazz);
    }

    @Override
    public Integer update(T t)  {
        return baseMapper.update(t);
    }

    @Override
    public PagedResult<T> queryPageData(T t)  {
        PagedResult<T> pageBeanDto = new PagedResult<T>();

        Integer pageNo = ReflectUtil.getFieldValue(t, "pageNo", Integer.class);
        Integer pageSize = ReflectUtil.getFieldValue(t, "pageSize", Integer.class);

        // STEP1:设置分页参数
        PageHelper.startPage(pageNo,pageSize);
        // STEP2:执行查询语句
        List<T> list = baseMapper.findList(t);
        // STEP3:获取分页的详细参数
        //PageInfo pageInfo = new PageInfo(list);
        PagedResult<T> pagedResult = BeanConvertUtils.toPagedResult(list);
        return pageBeanDto;
    }

    @Override
    public List<T> findList(T t)  {
        List<T> list = baseMapper.findList(t);
        return list;
    }

    @Override
    public T findById(K id)  {
        return baseMapper.findById(id);
    }

    @Override
    public int deleteById(K id) {
        return baseMapper.deleteById(id);
    }
}
