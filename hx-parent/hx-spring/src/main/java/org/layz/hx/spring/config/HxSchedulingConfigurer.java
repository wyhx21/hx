package org.layz.hx.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;

@EnableScheduling
public abstract class HxSchedulingConfigurer implements SchedulingConfigurer, Trigger {
    private static final Logger LOGGER = LoggerFactory.getLogger(HxSchedulingConfigurer.class);
    private CronTrigger cronTrigger;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(()->{
            LOGGER.debug("task excute begin...");
            long begin = System.currentTimeMillis();
            excute();
            LOGGER.debug("task excute end, excute time: {}...",System.currentTimeMillis() - begin);
        },this);
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        String cron = cron();
        if(null == cronTrigger || !cron.equals(cronTrigger.getExpression())) {
            cronTrigger = new CronTrigger(cron);
        }
        return cronTrigger.nextExecutionTime(triggerContext);
    }

    /**
     * 定时任务执行逻辑
     */
    public abstract void excute();

    /**
     * 定时任务Cron表达式
     * @return
     */
    public abstract String cron();
}
