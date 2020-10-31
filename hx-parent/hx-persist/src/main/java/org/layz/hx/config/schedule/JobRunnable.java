package org.layz.hx.config.schedule;

import org.layz.hx.base.inte.ResponseEnum;
import org.layz.hx.config.entity.schedule.ScheduleLog;
import org.layz.hx.config.service.schedule.ScheduleLogService;
import org.layz.hx.core.pojo.response.JsonResponse;
import org.layz.hx.core.service.JobExecuteHandler;
import org.layz.hx.core.service.JobResultHandler;
import org.layz.hx.spring.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Date;

public class JobRunnable implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobRunnable.class);
    private ScheduleLogService scheduleLogService;
    private ScheduleLog scheduleLog;
    private JobResultHandler jobResultHandler;

    @Override
    public void run() {
        String serviceName = this.scheduleLog.getJobService();
        JobExecuteHandler jobExecuteHandler = SpringContextUtil.getBean(serviceName);
        try {
            scheduleLog.setStartRunTime(new Date());
            jobExecuteHandler.onBefore();

            JsonResponse response = jobExecuteHandler.doTask(this.scheduleLog.getParam1(), this.scheduleLog.getParam2());
            LOGGER.debug("handle end, id: {}: code: {}, msg:{}", scheduleLog.getId(), response.getRespCode(), response.getRespMsg());
            if (ResponseEnum.SUCC.equals(response.getSuccess())) {
                jobResultHandler.jobSuccHandle(scheduleLog, response);
                scheduleLogService.update(scheduleLog);
            } else {
                jobResultHandler.jobFailHandle(scheduleLog,response);
                scheduleLogService.update(scheduleLog);
            }
            scheduleLogService.updateNextJob(Collections.singletonList(scheduleLog.getId()));
        } catch (Exception e) {
            LOGGER.error("run error, serviceName", serviceName, e);
            jobResultHandler.jobErrorHandle(scheduleLog,e);
            scheduleLogService.update(scheduleLog);
        } finally {
            jobExecuteHandler.onAfter();
        }
    }

    public void setScheduleLogService(ScheduleLogService scheduleLogService) {
        this.scheduleLogService = scheduleLogService;
    }

    public void setScheduleLog(ScheduleLog scheduleLog) {
        this.scheduleLog = scheduleLog;
    }

    public void setJobResultHandler(JobResultHandler jobResultHandler) {
        this.jobResultHandler = jobResultHandler;
    }

}
