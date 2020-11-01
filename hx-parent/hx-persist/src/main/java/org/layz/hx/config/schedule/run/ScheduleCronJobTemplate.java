package org.layz.hx.config.schedule.run;

import org.layz.hx.base.type.ScheduleStatusEnum;
import org.layz.hx.config.entity.schedule.ScheduleLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public final class ScheduleCronJobTemplate extends ScheduleJobTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleCronJobTemplate.class);
    private String jobService;
    private String param1;
    private String param2;
    private String remark;

    public void setJobService(String jobService) {
        this.jobService = jobService;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected List<ScheduleLog> onBefore() {
        ScheduleLog scheduleLog = new ScheduleLog();
        scheduleLog.setCreatedBy(super.lastModifiedBy);
        scheduleLog.setScanTypeName(super.scanTypeName);
        scheduleLog.setJobService(this.jobService);
        scheduleLog.setStatus(ScheduleStatusEnum.HANDING.getValue());
        scheduleLog.setParam1(this.param1);
        scheduleLog.setParam2(this.param2);
        scheduleLog.setRemark(this.remark);
        super.scheduleLogWrapper.persistEntity(scheduleLog);
        return Collections.singletonList(scheduleLog);
    }
}
