package org.layz.hx.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.layz.hx.base.annotation.HxValid;
import org.layz.hx.core.util.ValidUtil;
import org.springframework.core.annotation.Order;

@Aspect
@Order(100)
public class HxValidAspect {

    @Pointcut("@annotation(org.layz.hx.base.annotation.HxValid)")
    public void pointCount(){}

    @Before(value = "pointCount() && @annotation(valid)")
    public void doBefore(JoinPoint joinPoint, HxValid valid) throws Throwable{
        Object[] args = joinPoint.getArgs();
        int[] paramIndex = valid.index();
        for (int index : paramIndex) {
            if(args.length < index) {
                continue;
            }
            Object param = args[index];
            if(null == param) {
                continue;
            }
            ValidUtil.validParam(param,valid);
        }
    }

}
