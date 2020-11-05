package org.layz.hx.config.schedule.job;

import org.layz.hx.base.util.Assert;
import org.layz.hx.core.pojo.response.JsonResponse;
import org.layz.hx.core.support.schedule.AbstractJobExecuteHandler;
import org.layz.hx.core.util.ResponseUtil;
import org.layz.hx.spring.util.SpringContextUtil;

import java.lang.reflect.Method;

public class MethodInvokeJob extends AbstractJobExecuteHandler {
    private static final String split = "\\.";
    @Override
    public JsonResponse doTask(String param1, String param2) throws Exception {
        Assert.isNotBlank(param1, "method param1 is blank");
        String[] arr = param1.split(MethodInvokeJob.split);
        Assert.isTrue(arr.length == 2, "param must be class.method(String.class)");
        Object bean = SpringContextUtil.getBean(arr[0]);
        Assert.isNotNull(bean, arr[0] + " bean not found");
        Method method = bean.getClass().getDeclaredMethod(arr[1], String.class);
        method.setAccessible(true);
        method.invoke(bean, param2);
        return ResponseUtil.getSuccessResponse();
    }
}
