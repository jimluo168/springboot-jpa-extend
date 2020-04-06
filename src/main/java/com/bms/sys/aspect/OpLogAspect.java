package com.bms.sys.aspect;

import com.bms.common.config.session.SessionInfo;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.entity.OperationLog;
import com.bms.sys.service.OpLogService;
import com.bms.sys.service.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 操作日志AOP.
 *
 * @author luojimeng
 * @date 2020/3/14
 */
@Aspect
@Component
public class OpLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(OpLogAspect.class);

    @Autowired
    private OpLogService opLogService;
    @Autowired
    private OrganizationService organizationService;

    @Around("@annotation(com.bms.common.web.annotation.OpLog)")
    public Object log(ProceedingJoinPoint point) throws Throwable {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        OpLogModule module = method.getDeclaringClass().getAnnotation(OpLogModule.class);
        OpLog func = method.getAnnotation(OpLog.class);
        Object[] args = point.getArgs();

        SessionInfo info = SessionInfo.getCurrentSession();
        if (info != null &&
                ((module != null && func != null)
                        || (func != null && StringUtils.isNotBlank(func.module())))) {
            try {
                OperationLog log = new OperationLog();
                BeanUtils.copyProperties(info, log);
                log.setRealName(info.getName());
                log.setModule(Optional.ofNullable(func.module()).orElse(module.value()));
                log.setFuncName(func.value());
                log.setOrgName(info.getOrgName());
                log.setUrl(info.getRequestUrl());
                log.setParams(info.getRequestParams());
                opLogService.insert(log);
            } catch (Exception e) {
                logger.error("记录操作日志出错:" + e.getMessage(), e);
            }
        }

        return point.proceed(args);
    }
}
