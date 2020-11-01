package org.layz.hx.config.schedule;

import org.layz.hx.base.type.DeletedEnum;
import org.layz.hx.config.entity.schedule.ScheduleScan;
import org.layz.hx.config.service.schedule.ScheduleScanService;
import org.layz.hx.core.service.JobResultHandlerImpl;
import org.layz.hx.core.wrapper.schedule.ScheduleLogWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.CollectionUtils;

import java.util.List;

@EnableScheduling
public final class ScheduleScanConfig implements SchedulingConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleScanConfig.class);
    @Autowired
    private ScheduleLogWrapper scheduleLogWrapper;
    @Autowired
    private ScheduleScanService scheduleScanService;
    @Autowired(required = false)
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<ScheduleScan> list = findEnable();
        if(CollectionUtils.isEmpty(list)) {
            LOGGER.info("configure is empty");
        }
        for (ScheduleScan scan : list) {
            Runnable runnable = getRunnable(scan);
            taskRegistrar.addCronTask(runnable, scan.getCron());
        }
    }

    /**
     * 获取配置信息
     * @return
     */
    private List<ScheduleScan> findEnable(){
        LOGGER.debug("find configure");
        ScheduleScan param = new ScheduleScan();
        param.setDeleted(DeletedEnum.ENABLE.getValue());
        return scheduleScanService.findByEntity(param);
    }

    /**
     * 获取执行器
     * @param scan
     * @return
     */
    private Runnable getRunnable(ScheduleScan scan) {
        String scanTypeName = scan.getScanTypeName();
        String cron = scan.getCron();
        String remark = scan.getRemark();
        Integer single = scan.getSingle();
        JobTemplate template = new JobTemplate();
        template.setScheduleLogWrapper(this.scheduleLogWrapper);
        template.setTaskExecutor(this.taskExecutor);
        template.setJobResultHandler(new JobResultHandlerImpl());
        template.setScanTypeName(scanTypeName);
        template.setSingleThread(single);
        template.setTaskLoopCount(scan.getTaskLoopCount());
        LOGGER.info("ScheduleScanConfig:\n\tscanTypeName: {}\n\tcron: {}\n\tsingle: {}\n\tremark: {}",
                scanTypeName,cron,single,remark);
        return template;
    }
}
