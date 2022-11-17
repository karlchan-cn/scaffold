package com.scaffold.service.aop;

import com.scaffold.service.dubbo.protocol.AdDubboInvoker;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static org.apache.dubbo.common.constants.CommonConstants.TIMEOUT_KEY;

/**
 * Created by Karl on 2022/10/18
 * ref:https://docs.spring.io/spring-framework/docs/2.5.5/reference/aop.html
 *
 * @author Karl
 **/
@Component
@Aspect
@Slf4j
public class EnableTynamicDubboInvokeTimeoutAspect implements InitializingBean {

    //@Pointcut(value = "@annotation(com.scaffold.service.aop.EnableDynamicDubboInvokeTimeout)")
    private void pointcut() {
        //切点定义使用
    }

    //@Around(value = "pointcut() && @annotation(anno)", argNames = "anno")
    @Around("@annotation(com.scaffold.service.aop.EnableDynamicDubboInvokeTimeout)")
    public Object enableDynamicDubboInvokeTimeout(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnObject = null;
        long timestamp = System.currentTimeMillis();
        final RpcContext context = RpcContext.getContext();
        log.info("dynamic-timeout with 2500ms");
        // 动态设置超时时间
        context.setAttachment(AdDubboInvoker.DYNAMIC_TIMEOUT_KEY, Long.valueOf(2500));
        returnObject = joinPoint.proceed();
        Duration costTime = Duration.ofMillis(System.currentTimeMillis() - timestamp);

        log.info("invoke cost:{}", costTime);
        return returnObject;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("initialized");
    }
}
