package org.layz.hx.config.schedule;

import org.layz.hx.base.type.DeletedEnum;
import org.layz.hx.config.entity.schedule.ScheduleCron;
import org.layz.hx.config.schedule.run.ScheduleCronJobTemplate;
import org.layz.hx.config.service.schedule.ScheduleCronService;
import org.layz.hx.core.util.factory.JobResultHandlerFactory;
import org.layz.hx.core.wrapper.schedule.ScheduleLogWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.List;

@EnableScheduling
public class ScheduleCronConfig implements SchedulingConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleCronConfig.class);
    @Autowired
    private ScheduleCronService scheduleCronService;
    @Autowired
    private ScheduleLogWrapper scheduleLogWrapper;
    @Autowired(required = false)
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<ScheduleCron> list = findEnable();
        if(null == list || list.isEmpty()) {
            LOGGER.info("cron configure is empty");
            return;
        }
        for (ScheduleCron scheduleCron : list) {
            Runnable runnable = getRunnable(scheduleCron);
            taskRegistrar.addCronTask(runnable, scheduleCron.getCron());
        }
    }

    /**
     * 获取配置信息
     * @return
     */
    private List<ScheduleCron> findEnable(){
        try {
            LOGGER.debug("find configure");
            ScheduleCron param = new ScheduleCron();
            param.setDeleted(DeletedEnum.ENABLE.getValue());
            return scheduleCronService.findByEntity(param);
        } catch (Exception e) {
            LOGGER.error("find configure error", e);
            return null;
        }
    }

    /**
     * 获取执行器
     * @param scheduleCron
     * @return
     */
    private Runnable getRunnable(ScheduleCron scheduleCron) {
        String scanTypeName = scheduleCron.getScanTypeName();
        String jobService = scheduleCron.getJobService();
        String cron = scheduleCron.getCron();
        Integer single = scheduleCron.getSingle();
        String param1 = scheduleCron.getParam1();
        String param2 = scheduleCron.getParam2();
        String remark = scheduleCron.getRemark();
        Long createdBy = scheduleCron.getCreatedBy();
        LOGGER.info("cron configure\n\tscanTypeName:{}\n\tjobService:{}\n\tcron:{}\n\tsingle:{}\n\tparam1:{}\n\tparam2:{}\n\tremark:{}",
                scanTypeName, jobService, cron, single, param1, param2, remark);
        // 组装日志信息

        ScheduleCronJobTemplate jobTemplate = new ScheduleCronJobTemplate();
        jobTemplate.setScheduleLogWrapper(scheduleLogWrapper);
        jobTemplate.setTaskExecutor(taskExecutor);
        jobTemplate.setJobResultHandler(JobResultHandlerFactory.getHandler());
        jobTemplate.setJobService(jobService);
        jobTemplate.setScanTypeName(scanTypeName);
        jobTemplate.setSingleThread(single);
        jobTemplate.setParam1(param1);
        jobTemplate.setParam2(param2);
        jobTemplate.setRemark(remark);
        jobTemplate.setLastModifiedBy(createdBy);
        return jobTemplate;
    }
}
