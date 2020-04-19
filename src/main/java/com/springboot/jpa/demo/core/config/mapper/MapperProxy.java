package com.springboot.jpa.demo.core.config.mapper;

import com.springboot.jpa.demo.core.dao.DaoCmd;
import com.springboot.jpa.demo.core.dao.HibernateDao;
import com.springboot.jpa.demo.core.dao.IDao;
import com.springboot.jpa.demo.core.domain.PageList;
import com.springboot.jpa.demo.core.domain.PageRequest;
import com.springboot.jpa.demo.core.util.BeanMapper;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.*;

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
        Class<?> returnType = method.getReturnType();
        String queryKey = mapperInterface.getName() + HibernateDao.dot + methodName;
        Map<String, Object> params = null;
        if (args != null && args.length > 0) {
            params = new HashMap<>();
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < args.length; i++) {
                params.put(parameters[i].getName(), args[i]);
            }
        }
        Class<?> resultClass = (Class<?>) ((ParameterizedTypeImpl) method.getGenericReturnType()).getActualTypeArguments()[0];
        if (resultClass.isPrimitive() ||
                Integer.class.isAssignableFrom(resultClass) ||
                Long.class.isAssignableFrom(resultClass) ||
                String.class.isAssignableFrom(resultClass) ||
                BigDecimal.class.isAssignableFrom(resultClass) ||
                Date.class.isAssignableFrom(resultClass) ||
                Object[].class.isAssignableFrom(resultClass) ||
                Float.class.isAssignableFrom(resultClass) ||
                Double.class.isAssignableFrom(resultClass) ||
                Boolean.class.isAssignableFrom(resultClass) ||
                Character.class.isAssignableFrom(resultClass)) {
            resultClass = null;
        }
        if (PageList.class.isAssignableFrom(returnType)
                && parameterTypes.length > 0
                && PageRequest.class.isAssignableFrom(parameterTypes[0])) {
            if (args.length > 1) {
                params = BeanMapper.toMap(args[1]);
            }
            return dao.findAll((PageRequest) args[0], new DaoCmd(queryKey, params, resultClass));
        }
        if (List.class.isAssignableFrom(returnType) ||
                Set.class.isAssignableFrom(returnType) ||
                Iterable.class.isAssignableFrom(returnType) ||
                Collection.class.isAssignableFrom(returnType)) {
            return dao.getList(new DaoCmd(queryKey, params, resultClass));
        }
        if (methodName.startsWith("count") && Long.class.isAssignableFrom(returnType)) {
            return dao.getCount(new DaoCmd(queryKey, params));
        }
        if (methodName.startsWith("update") ||
                methodName.startsWith("delete")) {
            return dao.executeUpdate(new DaoCmd(queryKey, params));
        }
        return dao.getSingle(new DaoCmd(queryKey, params, returnType));
    }
}
