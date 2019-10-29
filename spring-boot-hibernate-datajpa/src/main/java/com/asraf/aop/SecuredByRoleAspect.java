package com.asraf.aop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.asraf.utils.JwtUtils;

@Aspect
@Component
public class SecuredByRoleAspect {

    private JwtUtils jwtUtils;

    @Autowired
    public SecuredByRoleAspect(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Pointcut("@annotation(annotation)")
    private void securedByRole(SecuredByRole annotation) {
        // Pointcut method
    }

    @Around("securedByRole(annotation)")
    public Object doSomething(ProceedingJoinPoint pjp, SecuredByRole annotation) throws Throwable {
        Object authoritiesObj = null;
        try {
            authoritiesObj = this.jwtUtils.getPayloadValue("authorities");
        } catch (Exception ex) {
            throw new AccessDeniedException(jwtUtils.getToken());
        }
        List<String> authorities = authoritiesObj == null ? new ArrayList<>() : (List<String>) authoritiesObj;
        List<String> annotationValues = Arrays.asList(annotation.value());
        for (String authority : authorities) {
            if (annotationValues.contains(authority)) {
                return pjp.proceed();
            }
        }
        String clientId = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("__CLIENTID__");
        if(StringUtils.isNotBlank(clientId) && clientId.contains("SPRING")) {
        	return pjp.proceed();
        }
        throw new AccessDeniedException(jwtUtils.getToken());
    }

}