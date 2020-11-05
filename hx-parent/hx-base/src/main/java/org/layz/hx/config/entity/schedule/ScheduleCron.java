package org.layz.hx.config.entity.schedule;

import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.annotation.HxTable;
import org.layz.hx.base.entity.ScheduleEntity;

@HxTable(value = "schedule_cron", definition = "COMMENT='定时任务定时执行配置'")
public class ScheduleCron extends ScheduleEntity {
    @HxColumn(sort = 4,definition = "varchar(32) COMMENT '执行类'")
    private String jobService;
    @HxColumn(sort = 11,definition = "varchar(64) COMMENT '参数1'")
    private String param1;
    @HxColumn(sort = 12,definition = "varchar(64) COMMENT '参数2'")
    private String param2;

    public String getJobService() {
        return jobService;
    }

    public void setJobService(String jobService) {
        this.jobService = jobService;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

}
