package org.layz.hx.core.support.schedule;

import org.layz.hx.config.entity.schedule.ScheduleLog;
import org.layz.hx.core.pojo.response.JsonResponse;

public class JobResultHandlerDirector implements JobResultHandler {
    private JobResultHandler jobResultHandler;
    @Override
    public void jobSuccHandle(ScheduleLog scheduleLog, JsonResponse result) {
        this.jobResultHandler.jobSuccHandle(scheduleLog, result);
    }

    @Override
    public void jobFailHandle(ScheduleLog scheduleLog, Object result) {
        this.jobResultHandler.jobFailHandle(scheduleLog, result);
    }

    @Override
    public void jobErrorHandle(ScheduleLog scheduleLog, Throwable e) {
        this.jobResultHandler.jobErrorHandle(scheduleLog, e);
    }

    public void setJobResultHandler(JobResultHandler jobResultHandler) {
        this.jobResultHandler = jobResultHandler;
    }

    public JobResultHandler getJobResultHandler() {
        return jobResultHandler;
    }
}
