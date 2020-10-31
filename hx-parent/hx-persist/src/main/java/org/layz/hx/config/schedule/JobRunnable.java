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
        Long id = scheduleLog.getId();
        String param1 = scheduleLog.getParam1();
        String param2 = scheduleLog.getParam2();
        String remark = scheduleLog.getRemark();
        long begin = System.currentTimeMillis();
        LOGGER.info("{} execute begin, param1: {}: param2: {}, remark:{}, id:{}", serviceName, param1, param2, remark, id);
        try {
            scheduleLog.setStartRunTime(new Date());
            jobExecuteHandler.onBefore();
            JsonResponse response = jobExecuteHandler.doTask(param1, param2);
            LOGGER.info("{} execute end, id:{}, time: {} ms...", serviceName, id, (System.currentTimeMillis() - begin));
            if (ResponseEnum.SUCC.equals(response.getSuccess())) {
                jobResultHandler.jobSuccHandle(scheduleLog, response);
                scheduleLogService.update(scheduleLog);
            } else {
                jobResultHandler.jobFailHandle(scheduleLog,response);
                scheduleLogService.update(scheduleLog);
            }
            scheduleLogService.updateNextJob(Collections.singletonList(scheduleLog.getId()));
        } catch (Exception e) {
            LOGGER.error("{} execute error, id:{}, time: {} ms...", serviceName, id, (System.currentTimeMillis() - begin), e);
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
