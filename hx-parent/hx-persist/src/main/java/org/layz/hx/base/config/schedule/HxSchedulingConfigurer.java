package org.layz.hx.base.config.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.PostConstruct;

@EnableScheduling
public abstract class HxSchedulingConfigurer implements SchedulingConfigurer, Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HxSchedulingConfigurer.class);
    private String cron;

    @PostConstruct
    public final void initData(){
        this.cron = cron();
        init();
    }

    @Override
    public final void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(this,this.cron);
    }

    @Override
    public final void run() {
        long begin = System.currentTimeMillis();
        try {
            LOGGER.debug("task execute begin...");
            execute();
            LOGGER.debug("task execute end, execute time: {}...",System.currentTimeMillis() - begin);
        } catch (Exception e) {
            LOGGER.error("task execute error, execute time: {}...",(System.currentTimeMillis() - begin), e);
        }
    }

    /**
     * 定时任务执行逻辑
     */
    protected abstract void execute();

    /**
     * 初始化操作
     */
    protected abstract void init();
    /**
     * 定时任务Cron表达式
     * @return
     */
    protected abstract String cron();
}
