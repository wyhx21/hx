package org.layz.hx.config.schedule;

import org.layz.hx.config.entity.schedule.ScheduleCron;
import org.layz.hx.config.schedule.run.ScheduleCronJobTemplate;
import org.layz.hx.config.service.schedule.ScheduleCronService;
import org.layz.hx.core.util.factory.JobResultHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ScheduleCronConfig extends AbstractSchedulingConfigurer<ScheduleCron> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleCronConfig.class);
    @Autowired
    private ScheduleCronService scheduleCronService;

    @Override
    protected List<ScheduleCron> findConfig() {
        return this.scheduleCronService.findAll();
    }

    @Override
    protected void setJobMap(ScheduleCron config) {
        String scanTypeName = config.getScanTypeName();
        String jobService = config.getJobService();
        String cron = config.getCron();
        Integer single = config.getSingle();
        String param1 = config.getParam1();
        String param2 = config.getParam2();
        String remark = config.getRemark();
        Long createdBy = config.getCreatedBy();
        LOGGER.info("cron configure\n\tscanTypeName:{}\n\tjobService:{}\n\tcron:{}\n\tsingle:{}\n\tparam1:{}\n\tparam2:{}\n\tremark:{}",
                scanTypeName, jobService, cron, single, param1, param2, remark);

        ScheduleCronJobTemplate jobTemplate = (ScheduleCronJobTemplate) super.jobMap.get(config.getId());
        if(null == jobTemplate) {
            jobTemplate = new ScheduleCronJobTemplate();
            super.jobMap.put(config.getId(), jobTemplate);
        }
        jobTemplate.setJobResultHandler(JobResultHandlerFactory.getHandler());
        jobTemplate.setDeleted(config.getDeleted());
        jobTemplate.setCron(config.getCron());
        jobTemplate.setJobService(jobService);
        jobTemplate.setScanTypeName(scanTypeName);
        jobTemplate.setSingleThread(single);
        jobTemplate.setParam1(param1);
        jobTemplate.setParam2(param2);
        jobTemplate.setRemark(remark);
        jobTemplate.setLastModifiedBy(createdBy);
    }
}
