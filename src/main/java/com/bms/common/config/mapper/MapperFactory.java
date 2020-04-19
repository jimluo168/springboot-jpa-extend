package com.bms.common.config.mapper;


import com.bms.common.dao.IDao;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 接口实例工厂，这里主要是用于提供接口的实例对象.
 *
 * @author luojimeng
 * @date 2020/4/19
 */
public class MapperFactory<T> implements FactoryBean<T> {
    private Class<T> mapperInterface;
    private IDao dao;

    public MapperFactory(Class<T> mapperInterface, IDao dao) {
        this.mapperInterface = mapperInterface;
        this.dao = dao;
    }

    @Override
    public T getObject() throws Exception {
        InvocationHandler handler = new MapperProxy(mapperInterface, dao);

        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(),
                new Class[]{mapperInterface}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


}
