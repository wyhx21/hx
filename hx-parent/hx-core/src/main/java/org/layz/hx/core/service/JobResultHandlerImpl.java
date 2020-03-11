package org.layz.hx.core.service;

import org.layz.hx.base.entity.BaseJobEntity;
import org.layz.hx.base.type.JobStatusEnum;
import org.layz.hx.core.pojo.response.JsonResponse;

import java.text.MessageFormat;
import java.util.Date;

public class JobResultHandlerImpl implements JobResultHandler<BaseJobEntity> {

    @Override
    public void jobSuccHandle(BaseJobEntity entity, JsonResponse result) {
        String msg = obtainMsg(result);
        entity.setHandleResult(msg); // 成功
        entity.setEndRunTime(new Date());
        entity.setStatus(JobStatusEnum.HANDLE_SUCCESS.getValue());
        entity.setLastModifiedDate(new Date());
    }

    @Override
    public void jobFailHandle(BaseJobEntity entity, Object result) {
        String msg = obtainMsg(result);
        handleFailJob(entity,msg);
    }

    @Override
    public void jobErrorHandle(BaseJobEntity entity, Throwable e) {
        String msg = obtainMsg(e);
        handleFailJob(entity,msg);
    }

    /**
     * @param obj
     * @return
     */
    private String obtainMsg(Object obj){
        String msg = null;
        if(obj instanceof JsonResponse) {
            msg = MessageFormat.format("code:{0},msg: {1}", ((JsonResponse)obj).getRespCode(), ((JsonResponse)obj).getRespMsg());
        } else if (obj instanceof Throwable){
            msg = ((Throwable)obj).getMessage();
        }
        if(null == msg) {
            return null;
        }
        if (msg != null && msg.length() >= 3500) {
            msg = msg.substring(0, 3500);
        }
        return msg;
    }
    /**
     * @param entity
     * @param msg
     */
    private void handleFailJob(BaseJobEntity entity, String msg){
        entity.setHandleResult(msg);
        entity.setEndRunTime(new Date());
        entity.setProcessNo(null);
        int failCount = entity.getFailCount() == null ? 0 :entity.getFailCount();
        entity.setFailCount(failCount + 1);
        entity.setStatus(JobStatusEnum.HANDLE_FAIL.getValue());
        entity.setLastModifiedDate(new Date());
    }
}
