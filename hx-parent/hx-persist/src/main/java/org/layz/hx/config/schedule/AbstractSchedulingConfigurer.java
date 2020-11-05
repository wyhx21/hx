package org.layz.hx.config.schedule;

import org.layz.hx.base.entity.ScheduleEntity;
import org.layz.hx.config.schedule.run.ScheduleJobTemplate;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EnableScheduling
public abstract class AbstractSchedulingConfigurer<T extends ScheduleEntity> implements SchedulingConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSchedulingConfigurer.class);
    protected Map<Long, ScheduleJobTemplate> jobMap = new ConcurrentHashMap<>();
    @Autowired
    private ScheduleLogWrapper scheduleLogWrapper;
    @Autowired(required = false)
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public final void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        try {
            List<T> list = findConfig();
            if(null == list || list.isEmpty()) {
                return;
            }
            for (T config : list) {
                setJobMap(config);
                ScheduleJobTemplate template = jobMap.get(config.getId());
                template.setTaskExecutor(this.taskExecutor);
                template.setScheduleLogWrapper(this.scheduleLogWrapper);
                taskRegistrar.addTriggerTask(template, template);
            }
        } catch (Exception e) {
            LOGGER.error("configureTasks error", e);
        }
    }

    /**
     * 刷新配置信息
     */
    public final void refreshConfig(){
        List<T> list = this.findConfig();
        if(null == list || list.isEmpty()) {
            return;
        }
        for (T config : list) {
            this.setJobMap(config);
        }
    }

    /**
     * 设置缓存
     * @param config
     */
    protected abstract void setJobMap(T config);

    /**
     * 读取配置信息
     * @return
     */
    protected abstract List<T> findConfig();
}
