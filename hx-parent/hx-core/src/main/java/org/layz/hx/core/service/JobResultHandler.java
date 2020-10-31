package org.layz.hx.core.service;

import org.layz.hx.config.entity.schedule.ScheduleLog;
import org.layz.hx.core.pojo.response.JsonResponse;

public interface JobResultHandler {
    /**
     * @param scheduleLog
     * @param result
     */
    void jobSuccHandle(ScheduleLog scheduleLog, JsonResponse result);

    /**
     * @param scheduleLog
     * @param result
     */
    void jobFailHandle(ScheduleLog scheduleLog,Object result);

    /**
     *
     * @param scheduleLog
     * @param e
     */
    void jobErrorHandle(ScheduleLog scheduleLog, Throwable e);
}
