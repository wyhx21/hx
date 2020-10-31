package org.layz.hx.core.service;

import org.layz.hx.base.type.ScheduleStatusEnum;
import org.layz.hx.config.entity.schedule.ScheduleLog;
import org.layz.hx.core.pojo.response.JsonResponse;

import java.text.MessageFormat;
import java.util.Date;

public class JobResultHandlerImpl implements JobResultHandler {

    @Override
    public void jobSuccHandle(ScheduleLog scheduleLog, JsonResponse result) {
        String msg = obtainMsg(result);
        scheduleLog.setHandleResult(msg); // 成功
        scheduleLog.setEndRunTime(new Date());
        scheduleLog.setStatus(ScheduleStatusEnum.HANDLE_SUCCESS.getValue());
        scheduleLog.setLastModifiedDate(new Date());
    }

    @Override
    public void jobFailHandle(ScheduleLog scheduleLog, Object result) {
        String msg = obtainMsg(result);
        handleFailJob(scheduleLog,msg);
    }

    @Override
    public void jobErrorHandle(ScheduleLog scheduleLog, Throwable e) {
        String msg = obtainMsg(e);
        handleFailJob(scheduleLog,msg);
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
     * @param scheduleLog
     * @param msg
     */
    private void handleFailJob(ScheduleLog scheduleLog, String msg){
        scheduleLog.setHandleResult(msg);
        scheduleLog.setEndRunTime(new Date());
        scheduleLog.setProcessNo(null);
        int failCount = scheduleLog.getFailCount() == null ? 0 :scheduleLog.getFailCount();
        scheduleLog.setFailCount(failCount + 1);
        scheduleLog.setStatus(ScheduleStatusEnum.HANDLE_FAIL.getValue());
        scheduleLog.setLastModifiedDate(new Date());
    }
}
