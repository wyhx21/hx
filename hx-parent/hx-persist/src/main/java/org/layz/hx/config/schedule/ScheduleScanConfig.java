package org.layz.hx.config.schedule;

import org.layz.hx.config.entity.schedule.ScheduleScan;
import org.layz.hx.config.schedule.run.ScheduleScanJobTemplate;
import org.layz.hx.config.service.schedule.ScheduleScanService;
import org.layz.hx.core.util.factory.JobResultHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public final class ScheduleScanConfig extends AbstractSchedulingConfigurer<ScheduleScan> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleScanConfig.class);
    @Autowired
    private ScheduleScanService scheduleScanService;

    @Override
    protected List<ScheduleScan> findConfig() {
        return this.scheduleScanService.findAll();
    }

    @Override
    protected void setJobMap(ScheduleScan config) {
        String scanTypeName = config.getScanTypeName();
        String cron = config.getCron();
        String remark = config.getRemark();
        Integer single = config.getSingle();
        ScheduleScanJobTemplate template = (ScheduleScanJobTemplate)super.jobMap.get(config.getId());
        if(null == template) {
            template = new ScheduleScanJobTemplate();
            super.jobMap.put(config.getId(), template);
        }
        template.setJobResultHandler(JobResultHandlerFactory.getHandler());
        template.setScanTypeName(scanTypeName);
        template.setSingleThread(single);
        template.setDeleted(config.getDeleted());
        template.setCron(config.getCron());
        template.setTaskLoopCount(config.getTaskLoopCount());
        template.setLastModifiedBy(config.getCreatedBy());
        LOGGER.info("ScheduleScanConfig:\n\tscanTypeName: {}\n\tcron: {}\n\tsingle: {}\n\tremark: {}",
                scanTypeName,cron,single,remark);
    }
}
