package cn.silently9527.coupons.core.aop;

import cn.silently9527.coupons.rest.common.Result;
import cn.silently9527.coupons.rest.common.enums.ApiEnum;
import cn.silently9527.coupons.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 演示环境不允许操作的切面
 * @author starBlues
 * @version 1.0
 */
@Aspect
@Component
@ConditionalOnProperty(name = "demo", havingValue = "true")
@Lazy(false)
@Slf4j
public class NoOptionAspect {

    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    private void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = HttpUtils.getRequest();
        if(request == null){
            throw new Exception("没有发现HttpServletRequest");
        }
        String method = request.getMethod();
        if("get".equalsIgnoreCase(method)){
            return joinPoint.proceed();
        } else {
            return Result.response(ApiEnum.OPERATE_SUCCESS, "演示环境不允许操作");
        }
    }



}
