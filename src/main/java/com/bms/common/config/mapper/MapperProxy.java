package com.bms.common.config.mapper;

import com.bms.common.dao.IDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * IDao的动态代理类.
 *
 * @author luojimeng
 * @date 2020/4/19
 */
public class MapperProxy<T> implements InvocationHandler {
    private final Class<T> mapperInterface;
    private final IDao dao;

    public MapperProxy(Class<T> mapperInterface, IDao dao) {
        this.mapperInterface = mapperInterface;
        this.dao = dao;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
//        Method proxyMethod = mapperInterface.getMethod(methodName, parameterTypes);
//        return proxyMethod.invoke(proxy, args);
        return null;
    }
}
