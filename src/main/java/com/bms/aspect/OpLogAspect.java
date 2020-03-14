package com.bms.aspect;

import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 操作日志AOP.
 *
 * @author luojimeng
 * @date 2020/3/14
 */
@Aspect
@Component
public class OpLogAspect {

    @Around("@annotation(com.bms.common.web.annotation.OpLog)")
    public Object log(ProceedingJoinPoint point) throws Throwable {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        OpLogModule opLogModule = method.getClass().getAnnotation(OpLogModule.class);
        OpLog oplog = method.getAnnotation(OpLog.class);
        Object[] args = point.getArgs();

        return point.proceed(args);
    }
}
