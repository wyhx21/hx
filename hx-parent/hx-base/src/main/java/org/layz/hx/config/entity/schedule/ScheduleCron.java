package org.layz.hx.config.entity.schedule;

import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.annotation.HxTable;
import org.layz.hx.base.entity.AutoLongBaseEntity;

@HxTable(value = "schedule_cron", definition = "COMMENT='定时任务定时执行配置'")
public class ScheduleCron extends AutoLongBaseEntity {
    @HxColumn(sort = 1,definition = "varchar(32) COMMENT '扫描字段,用于失败重复执行(需配置schedule_scan)'")
    private String scanTypeName;
    @HxColumn(sort = 2,definition = "varchar(32) COMMENT '执行类'")
    private String jobService;
    @HxColumn(sort = 3,definition = "varchar(32) COMMENT '表达式'")
    private String cron;
    @HxColumn(sort = 4,definition = "tinyint(2) COMMENT '0同步执行1异步执行'")
    private Integer single;
    @HxColumn(sort = 11,definition = "varchar(64) COMMENT '参数1'")
    private String param1;
    @HxColumn(sort = 12,definition = "varchar(64) COMMENT '参数2'")
    private String param2;
    @HxColumn(sort = 15,definition = "varchar(64) COMMENT '备注'")
    private String remark;

    public String getScanTypeName() {
        return scanTypeName;
    }

    public void setScanTypeName(String scanTypeName) {
        this.scanTypeName = scanTypeName;
    }

    public String getJobService() {
        return jobService;
    }

    public void setJobService(String jobService) {
        this.jobService = jobService;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getSingle() {
        return single;
    }

    public void setSingle(Integer single) {
        this.single = single;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
